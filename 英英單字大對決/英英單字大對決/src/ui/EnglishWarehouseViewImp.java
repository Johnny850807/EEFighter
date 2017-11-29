package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import model.words.Word;

public class EnglishWarehouseViewImp extends JFrame implements EnglishWarehouseView, ActionListener {
	private Button addWordBtn;
	private Button removeWordBtn;
	private TextField searchAndAddWordEd;
	private DefaultListModel<Word> wordDefaultListModel;
	private JList<Word> wordList;
	private JScrollPane wordListScrollPane;
	private List<Word> mockWords = new ArrayList<>();

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
		showWordList();
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
	
		wordDefaultListModel = new DefaultListModel<>();
		for (Word word : mockWords)
			wordDefaultListModel.addElement(word);
		wordList = new JList<>(wordDefaultListModel);
		wordList.setCellRenderer(new WordCellRenderer());
		wordListScrollPane = new JScrollPane(wordList);

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
		if (button == addWordBtn) {
			String text = searchAndAddWordEd.getText();

			wordDefaultListModel.addElement(new Word(text));
		} else if (button == removeWordBtn) {
			String text = searchAndAddWordEd.getText();
			for (int i = 0; i < wordDefaultListModel.size(); i++)
				if (wordDefaultListModel.getElementAt(i).getWord().equals(text))
					wordDefaultListModel.remove(i);
		}
	}
}

class WordCellRenderer extends JLabel implements ListCellRenderer {
	private static final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);

	public WordCellRenderer() {
		setOpaque(true);
	}

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		Word word = (Word) value;
		setText(word.getWord());
		this.setSize(100, 200);
		if (isSelected) {
			setBackground(HIGHLIGHT_COLOR);
			setForeground(Color.white);
		} else {
			setBackground(Color.white);
			setForeground(Color.black);
		}
		return this;
	}
}
