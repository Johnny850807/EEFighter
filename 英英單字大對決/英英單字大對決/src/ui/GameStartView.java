package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

import controller.EEFighterImp;
import model.Question;
import model.sprite.BasicMapBuilder;
import model.sprite.BasicMapDirector;
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

	public GameStartView() {
		super("英英單字大對決");
		EventQueue.invokeLater(() -> {
			setBounds(215, 80, 1105, 704);
			setupViews();
			setupLayout();
		});
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

		add(player1Lab);
		add(wordDefinitionLab);
		add(player2Lab);

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
		setViewsSize(new Dimension(100, 35));
		setViewsText();
	}

	private void setViewsText() {
		player1Lab.setText(" Player1: ");
		wordDefinitionLab.setText(" Word defintion : ");
		player2Lab.setText(" Player2: ");
	}

	private void setViewsSize(Dimension dimension) {
		player1Lab.setSize(dimension);
		player1Lab.setMinimumSize(dimension);
		player2Lab.setSize(dimension);
		player2Lab.setMinimumSize(dimension);
		wordDefinitionLab.setSize(new Dimension(700, 100));
		wordDefinitionLab.setMinimumSize(new Dimension(150, 35));
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
		gameViewPanel = new GameViewImp(new EEFighterImp(new BasicMapDirector(new BasicMapBuilder())), this);
		gameViewPanel.start();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		String definition = "1. ( " + question.getPartOfSpeech() + ". ) " + question.getDefinition();
		StringBuilder strBuilder = new StringBuilder("<html>");
		strBuilder.append(definition);
		strBuilder.append("</html>");
		if (question != null)
			wordDefinitionLab.setText(strBuilder.toString());
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

	@Override
	public void onPlayerEatLetter(String player, List<Sprite> letter) {
		showPlayerLetter(player, letter);
	}

	@Override
	public void onPlayerPopedLetter(String player, List<Sprite> letter) {
		showPlayerLetter(player, letter);
	}

	public void showPlayerLetter(String player, List<Sprite> letter) {
		if (player.equals("player1")) {
			StringBuilder strBuilder = new StringBuilder("Player1: ");
			for (int i = 0; i < letter.size(); i++) 
				strBuilder.append(letter.get(i).getSpriteName() + " ");
			player1Lab.setText("<html>" + strBuilder.toString() + " </html>");
		} else if (player.equals("player2")) {
			StringBuilder strBuilder = new StringBuilder("Player2: ");
			for (int i = 0; i < letter.size(); i++) 
				strBuilder.append(letter.get(i).getSpriteName() + " ");
			player2Lab.setText("<html>" + strBuilder.toString() + " </html>");
		}
	}
}
