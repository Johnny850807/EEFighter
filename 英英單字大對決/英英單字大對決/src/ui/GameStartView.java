package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

import controller.EEFighterImp;
import factory.ComponentAbstractFactory;
import factory.ComponentAbstractFactoryImp;
import model.Question;
import model.sprite.Sprite;

/**
 * @author Lin
 *
 */

public class GameStartView extends JFrame implements IGameStartView {

	private JLabel player1Lab;
	private JLabel player2Lab;
	private JLabel wordDefinitionLab;
	private GridBagConstraints gbc;
	private Question question;
	private GameViewImp gameViewPanel;
	private ComponentAbstractFactory componentAbstractFactory;
	private EEFighterImp eeFighterImp;

	public GameStartView(ComponentAbstractFactory componentAbstractFactory) {
		super("英英單字大對決");
		eeFighterImp = new EEFighterImp(componentAbstractFactory);
		EventQueue.invokeLater(() -> {
			setBounds(215, 80, 1105, 715);
			setupViews();
			setupLayout();
			addWindowListener(new CloseHandler(eeFighterImp));
		});
		this.componentAbstractFactory = componentAbstractFactory;
	}

	private void setupLayout() {
		getContentPane().setBackground(Color.GRAY);
		getContentPane().setLayout(new GridBagLayout());
		getContentPane().add(gameViewPanel);
		setupViewsLocation();
		setupViewsBackground();
	}

	private void setupViewsBackground() {
		player1Lab.setBackground(ColorHelper.getPrimaryDark());
		player1Lab.setOpaque(true);
		player2Lab.setBackground(ColorHelper.getPrimaryDark());
		player2Lab.setOpaque(true);
		wordDefinitionLab.setOpaque(true);
		wordDefinitionLab.setBackground(ColorHelper.getPrimaryBlue());
	}

	private void setupViewsLocation() {
		getContentPane().setLayout(new GridBagLayout());
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		addComponent(player1Lab, 2.0, 1.0, 0, 0, 1, 2);
		addComponent(wordDefinitionLab, 4.0, 1.0, 2, 0, 1, 2);
		addComponent(player2Lab, 2.0, 1.0, 5, 0, 1, 2);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.WEST;
		addComponent(gameViewPanel, 1.0, 10.0, 0, 5, 7, 7);
	}

	private void setupViews() {
		initializeAll();
		setupGameViewPanel();
		Font font = new Font("微軟正黑體", Font.BOLD, 20);
		setViewsFont(font);
		setViewsSize(new Dimension(50, 50));
		setViewsText();
	}

	private void setViewsText() {
		player1Lab.setText("[0] Player1: ");
		wordDefinitionLab.setText("Word defintion : ");
		player2Lab.setText("[0] Player2: ");
	}

	private void setViewsSize(Dimension dimension) {
		player1Lab.setMinimumSize(dimension);
		player2Lab.setMinimumSize(dimension);
		wordDefinitionLab.setMinimumSize(new Dimension(170, 50));
	}

	private void setViewsFont(Font font) {
		player1Lab.setFont(font);
		player1Lab.setForeground(Color.white);
		player2Lab.setFont(font);
		player2Lab.setForeground(Color.white);
		wordDefinitionLab.setFont(font);
		wordDefinitionLab.setForeground(Color.white);
	}

	private void setupGameViewPanel() {
		gameViewPanel.setPreferredSize(new Dimension(1050, 650));
	}

	private void initializeAll() {
		player1Lab = new JLabel();
		player2Lab = new JLabel();
		wordDefinitionLab = new JLabel();
		gbc = new GridBagConstraints();
		gameViewPanel = new GameViewImp(this, eeFighterImp, componentAbstractFactory);
		gameViewPanel.start();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		String definition = question.getNumber() + ". ( " + question.getPartOfSpeech() + ". ) " + createLine()
				+ question.getDefinition();
		StringBuilder strBuilder = new StringBuilder("<html>");
		strBuilder.append(definition);
		strBuilder.append("</p></html>");
		if (question != null)
			wordDefinitionLab.setText(strBuilder.toString());
		// modifyLayout();
	}

	private void modifyLayout() {
		int height = wordDefinitionLab.getHeight();
		setBounds(215, 80, 1105, 704 + (height));
	}

	private String createLine() {
		String line = " ";
		String[] lineamount = question.getWord().split("");
		for (int i = 0; i < lineamount.length; i++)
			line += "_ ";
		return line;
	}

	@Override
	public void onNextQuestion(Question question) {
		this.question = question;
		repaint();

	}

	public void addComponent(Component c, Double weightX, Double weightY, int row, int column, int width, int height) {
		gbc.weightx = weightX;
		gbc.weighty = weightY;
		gbc.gridx = row;
		gbc.gridy = column;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		add(c, gbc);
	}

	public void showPlayerBarInfo(String player, int score, List<Sprite> letter) {
		if (player.equals("player1")) {
			StringBuilder strBuilder = new StringBuilder("[" + score + "] Player1: ");
			for (int i = 0; i < letter.size(); i++)
				strBuilder.append(letter.get(i).getSpriteName() + " ");
			player1Lab.setText("<html>" + strBuilder.toString() + " </html>");
		} else if (player.equals("player2")) {
			StringBuilder strBuilder = new StringBuilder("[" + score + "] Player2: ");
			for (int i = 0; i < letter.size(); i++)
				strBuilder.append(letter.get(i).getSpriteName() + " ");
			player2Lab.setText("<html>" + strBuilder.toString() + " </html>");
		}
	}

	@Override
	public void onAnswerCorrectCleanLettersView() {
		player1Lab.setText("[0] Player1: ");
		player2Lab.setText("[0] Player2: ");
	}

	@Override
	public void showPlayerScore(String player, int Score) {
		if (player.equals("player1"))
			player1Lab.setText("[" + Score + "] Player1: ");
		else if (player.equals("player2"))
			player2Lab.setText("[" + Score + "] Player2: ");
	}

	@Override
	public void onPlayerEatLetter(String player, int score, List<Sprite> letter) {
		showPlayerBarInfo(player, score, letter);

	}

	@Override
	public void onPlayerPopedLetter(String player, int score, List<Sprite> letter) {
		showPlayerBarInfo(player, score, letter);
	}

	protected static class CloseHandler extends WindowAdapter {
		private EEFighterImp eeFighter;
		public CloseHandler(EEFighterImp eeFighterImp) {
			
		}

		public void windowClosing(final WindowEvent event) {
			eeFighter.windowClosed();
		}
	}

}
