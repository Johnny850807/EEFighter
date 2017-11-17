package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import model.words.Word;

public class EnglishWarehouseViewImp extends JFrame implements EnglishWarehouseView, ActionListener {
	private Button addWordBtn;
	private Button removeWordBtn;
	private TextField searchAndAddWordEd;
	DefaultListModel<String> words;
	JList<String> wordList;
	JScrollPane wordListScrollPane;

	private void setupLayout() {
		getContentPane().setBackground(new Color(55, 55, 55));
		getContentPane().setLayout(new FlowLayout());
		add(searchAndAddWordEd);
		add(addWordBtn);
		add(removeWordBtn);
		add(wordListScrollPane);

	}

	private void addButtonsActionListener(ActionListener actionListener) {
		addWordBtn.addActionListener(actionListener);
		removeWordBtn.addActionListener(actionListener);
	}

	private void setupViews() {
		initializeAllComponents();
		setViewsFont(new Font("微軟正黑體", Font.BOLD, 20));
		setViewsSize(new Dimension(50, 40));
	}

	private void initializeAllComponents() {
		addWordBtn = new Button("加入");
		removeWordBtn = new Button("刪除");
		searchAndAddWordEd = new TextField();
		words = new DefaultListModel<>();
		wordList = new JList<>(words);
		wordListScrollPane = new JScrollPane(wordList);
	}

	private void setViewsFont(Font font) {
		addWordBtn.setFont(font);
		removeWordBtn.setFont(font);
		searchAndAddWordEd.setFont(font);
		wordListScrollPane.setFont(font);
	}

	private void setViewsSize(Dimension dimension) {
		addWordBtn.setPreferredSize(dimension);
		removeWordBtn.setPreferredSize(dimension);
		searchAndAddWordEd.setPreferredSize(new Dimension(230, 40));
		wordListScrollPane.setPreferredSize(new Dimension(340, 500));
	}

	public void showWordList() {
		for (int i = 0; i < 4; i++) {
			words.addElement("Apple");
			words.addElement("Banana");
			words.addElement("Cat");
			words.addElement("Dog");
			words.addElement("Egg");
			words.addElement("Fuck");
			words.addElement("Gun");
			words.addElement("Hack");
		}
		wordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	public void start() {
		setBounds(500, 200, 400, 650);
		setupViews();
		setupLayout();
		addButtonsActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Button button = (Button) e.getSource();
		if (button.equals(addWordBtn)) {
			String text = searchAndAddWordEd.getText().toString();
			words.addElement(text);
		} else if (button.equals(removeWordBtn)) {
			String text = searchAndAddWordEd.getText().toString();
			int index = words.indexOf(text);
			words.remove(index);
		}
	}
}
