package ui;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import controller.EnglishWarehouseController;
import factory.ComponentAbstractFactory;
import factory.ReleasedComponentAbstractFactory;
import model.Secret;
import model.words.CrawlerVocabularycom;
import model.words.ITRI_TTS;
import model.words.WordXMLRepository;

/*
 * The Main view where contains the main function buttons.
 */
public class MainView extends JFrame implements ActionListener {
	private static final int BUTTON_WIDTH = 360;
	private static final int BUTTON_HEIGHT = 105;
	private static final String LOGO_PATH = "pic/logo.png";
	private Panel logoPanel;
	private Button playBtn;
	private Button connectBtn;
	private Button waitBtn;
	private Button englishWareBtn;
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
		playBtn = new Button("開始遊戲");
		connectBtn = new Button("連接對手");
		waitBtn = new Button("等待對手");
		englishWareBtn = new Button("前往單字庫");
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
		Button button = (Button) e.getSource();
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

	}

	private void connectToPlayer() {

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
