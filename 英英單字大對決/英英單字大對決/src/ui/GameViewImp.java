package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Panel;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * The game view where showing the playing game.
 */
public class GameViewImp extends JPanel implements GameView {

	private Label player1Lab;
	private Label player2Lab;
	private Label wordDefinitionLab;
	private Panel mapPanel;
	private GridBagConstraints gbc;

	public GameViewImp() {
		draw();
	}

	@Override
	public void draw() {
		setBounds(0, 0, 1100, 700);
		setupViews();
		setupLayout();
	}

	private void setupLayout() {
		setLayout(new GridBagLayout());
		setupViewsLocation();
		setupMapPanel();
		setupViewsBackground();
	}

	private void setupViewsBackground() {
		player1Lab.setBackground(Color.gray);
		player2Lab.setBackground(Color.gray);
		wordDefinitionLab.setBackground(new Color(155, 55, 55));
	}

	private void setupMapPanel() {
		mapPanel.setPreferredSize(new Dimension(1050, 500));
		mapPanel.setBackground(Color.BLACK);
	}

	private void setupViewsLocation() {
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		addComponent(player1Lab, 1.5, 2.0, 0, 0, 1, 2);
		addComponent(wordDefinitionLab, 3.0, 2.0, 3, 0, 2, 2);
		addComponent(player2Lab, 1.5, 2.0, 5, 0, 1, 2);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.WEST;
		addComponent(mapPanel, 1.0, 2.0, 0, 5, 7, 7);
	}

	private void setupViews() {
		initializeAll();
		setViewsFont(new Font("·L³n¥¿¶ÂÅé", Font.BOLD, 20));
		setViewsSize(new Dimension(400, 100));
		setViewsText();
	}

	private void setViewsText() {
		player1Lab.setText("Player1: ");
		wordDefinitionLab.setText("Word defintion : ");
		player2Lab.setText("Player2: ");
	}

	private void setViewsSize(Dimension dimension) {
		player1Lab.setSize(dimension);
		player2Lab.setSize(dimension);
		wordDefinitionLab.setSize(new Dimension(100, 700));
	}

	private void setViewsFont(Font font) {
		player1Lab.setFont(font);
		player2Lab.setFont(font);
		wordDefinitionLab.setFont(font);
	}

	private void initializeAll() {
		player1Lab = new Label();
		player2Lab = new Label();
		wordDefinitionLab = new Label();
		mapPanel = new Panel();
		gbc = new GridBagConstraints();
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
	public void close() {
		// TODO Auto-generated method stub

	}

}
