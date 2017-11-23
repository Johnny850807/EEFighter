package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameStartView extends JFrame{

	private JPanel gameViewPanel;
	
	public GameStartView() {
		super("英英單字大對決");
		setBounds(215, 80, 1100, 700);
		setupViews();
		setupLayout();
	}

	private void setupLayout() {
		getContentPane().setBackground(Color.GRAY);
		getContentPane().setLayout(new GridBagLayout());
		add(gameViewPanel);
	}

	private void setupViews() {
		initializeAllComponents();
		setupGameViewPanel();
	}

	private void setupGameViewPanel() {
		gameViewPanel.setPreferredSize(new Dimension(1050, 650));
	}

	private void initializeAllComponents() {
		gameViewPanel = new GameViewImp();
	}
	
}
