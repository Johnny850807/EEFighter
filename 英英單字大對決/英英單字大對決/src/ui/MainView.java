package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import controller.EnglishWarehouseController;
import controller.p2p.EEFighterP2PServer;
import factory.ComponentAbstractFactory;
import factory.P2PClientAbstractFactory;
import factory.P2PServerAbstractFactory;
import factory.ReleasedComponentAbstractFactory;

/*
 * The Main view where contains the main function buttons.
 */
public class MainView extends JFrame implements ActionListener {
	private static final int BUTTON_WIDTH = 360;
	private static final int BUTTON_HEIGHT = 105;
	private static final String LOGO_PATH = "pic/logo.png";
	private Panel logoPanel;
	private JButton playBtn;
	private JButton connectBtn;
	private JButton waitBtn;
	private JButton englishWareBtn;
	private ComponentAbstractFactory componentAbstractFactory;

	public MainView(ComponentAbstractFactory componentAbstractFactory) throws IOException {
		super("英英單字大對決");
		setBounds(300, 300, 400, 650);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setupViews();
		setupLayout();
		this.componentAbstractFactory = componentAbstractFactory;
	}

	private void setupViews() throws IOException {
		initializeAllComponents();
		setupLogoPanel();
		setButtonsFont(FontHelper.microsoftJhenghei(Font.BOLD, 25));
		setButtonsSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		addButtonsActionListener(this);
	}

	private void setupLogoPanel() throws IOException {
		logoPanel.setPreferredSize(new Dimension(360, 130));
		Image image = ImageIO.read(new File(LOGO_PATH));
		JLabel picLabel = new JLabel(new ImageIcon(image));
		logoPanel.add(picLabel);
	}

	private void initializeAllComponents() {
		logoPanel = new Panel();
		playBtn = new JButton("開始遊戲");
		connectBtn = new JButton("連接對手");
		waitBtn = new JButton("等待對手");
		englishWareBtn = new JButton("前往單字庫");
	}

	private void setButtonsFont(Font font) {
		playBtn.setFont(font);
		connectBtn.setFont(font);
		waitBtn.setFont(font);
		englishWareBtn.setFont(font);
	}

	private void setButtonsSize(Dimension dimension) {
		playBtn.setPreferredSize(dimension);
		connectBtn.setPreferredSize(dimension);
		waitBtn.setPreferredSize(dimension);
		englishWareBtn.setPreferredSize(dimension);
	}

	private void addButtonsActionListener(ActionListener actionListener) {
		playBtn.addActionListener(actionListener);
		connectBtn.addActionListener(actionListener);
		waitBtn.addActionListener(actionListener);
		englishWareBtn.addActionListener(actionListener);
	}

	private void setupLayout() {
		getContentPane().setBackground(ColorHelper.getPrimaryDark());
		getContentPane().setLayout(new FlowLayout());
		add(logoPanel);
		add(playBtn);
		add(connectBtn);
		add(waitBtn);
		add(englishWareBtn);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		if (button == playBtn)
			startPlayingPanel();
		else if (button == connectBtn)
			connectToPlayer();
		else if (button == waitBtn)
			waitingConnectFromPlayer();
		else if (button == englishWareBtn)
			goToEnglishWarehouse();
	}

	private void startPlayingPanel() {
		GameStartView gameStartView = new GameStartView(componentAbstractFactory);
		gameStartView.setVisible(true);
	}

	private void waitingConnectFromPlayer() {
		JDialog dialog = createDialogForWaitingClient();
		dialog.addWindowListener(new DefaultWindowListener() {
			@Override
			public void windowOpened(WindowEvent event) {
				new Thread(()->{
					try {
						ServerSocket serverSocket = new ServerSocket(EEFighterP2PServer.PORT);
						Socket clientSocket = serverSocket.accept();
						GameStartView gameStartView = new GameStartView(
								new P2PServerAbstractFactory(serverSocket, clientSocket));;
						gameStartView.setVisible(true);
						dialog.setVisible(false);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(MainView.this, "無法連線，請確認網路狀態。");
					}
				}).start();
			}
		});
		dialog.setVisible(true);
	}
	
	private JDialog createDialogForWaitingClient(){
		final JDialog dialog = new JDialog(this, "等待對方連線", true);
	    JProgressBar progressBar = new JProgressBar();
	    JLabel ipLabel = new JLabel("IP: " + getOutboundHost());
	    ipLabel.setFont(new Font("微軟正黑體", Font.BOLD, 22));
	    progressBar.setIndeterminate(true);
	    dialog.setMinimumSize(new Dimension(200, 100));
	    dialog.add(BorderLayout.CENTER, progressBar);
	    dialog.add(BorderLayout.NORTH, ipLabel);
	    dialog.setLocationRelativeTo(this);
	    return dialog;
	}
	/**
	 * This way works well when there are multiple network interfaces. 
	 * It always returns the preferred outbound IP. 
	 * The destination 8.8.8.8 is not needed to be reachable.
	 */
	private String getOutboundHost() {
		try (final DatagramSocket socket = new DatagramSocket()) {
			socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
			return socket.getLocalAddress().getHostAddress();
		} catch (SocketException | UnknownHostException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void connectToPlayer() {
		String address = JOptionPane.showInputDialog("請輸入對方的IP位址: ");
		address = address == null ? null : address.trim();
		if (address != null && !Pattern.matches("^\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}$", address))
			JOptionPane.showMessageDialog(this, "IP 格式錯誤");
		else if (address != null)
		{
			try {
				Socket clientSocket = new Socket(address, EEFighterP2PServer.PORT);
				GameStartView gameStartView = new GameStartView(new P2PClientAbstractFactory(clientSocket));
				gameStartView.setVisible(true);
			} catch (UnknownHostException e) {
				JOptionPane.showMessageDialog(this, "尋找不到此IP位址，請確認對方已等待連線且允許此程式通過防火牆。");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "無法連線，請確認網路狀態。");
			}
		}
	}

	private void goToEnglishWarehouse() {
		EnglishWarehouseViewImp englishWarehouseViewImp = new EnglishWarehouseViewImp(
				new EnglishWarehouseController(componentAbstractFactory));
		englishWarehouseViewImp.setVisible(true);
		englishWarehouseViewImp.start();
	}

	public static void main(String[] argv) {
		MainView mainView;
		ComponentAbstractFactory componentAbstractFactory = new ReleasedComponentAbstractFactory();
		try {
			mainView = new MainView(componentAbstractFactory);
			mainView.setVisible(true);
			// SoundPlayer.getInstance().playLoopMusic("sounds/after-journey.wav");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
