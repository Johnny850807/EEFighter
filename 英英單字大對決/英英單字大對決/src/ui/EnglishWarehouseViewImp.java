package ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.Timer;

import controller.EnglishWarehouseController;
import model.Secret;
import model.words.CrawlerVocabularycom;
import model.words.ITRI_TTS;
import model.words.ReadWordFailedException;
import model.words.Word;
import model.words.WordRepository;
import model.words.WordRepositoryImp;
import model.words.WordXMLRepository;

public class EnglishWarehouseViewImp extends JFrame implements EnglishWarehouseView, ActionListener {
	private Button addWordBtn;
	private Button removeWordBtn;
	private TextField searchAndAddWordEd;
	private DefaultListModel<Word> wordDefaultListModel;
	private JList<Word> wordList;
	private JScrollPane wordListScrollPane;
	private List<Word> words = new ArrayList<>();
	private WordRepository wordRepository;
	private EnglishWarehouseController englishWarehouseController;

	public EnglishWarehouseViewImp() {
		wordRepository = new WordXMLRepository("words");
		englishWarehouseController = new EnglishWarehouseController(wordRepository, new CrawlerVocabularycom(), new ITRI_TTS(Secret.TTS_ACCOUNT, Secret.TTS_PASSWORD));
		try {
			words = wordRepository.readAllWord(); 
		} catch (ReadWordFailedException e) {
			e.printStackTrace();
		}
	}
	
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
		for (Word word : words)
			wordDefaultListModel.addElement(word);
		wordList = new JList<>(wordDefaultListModel);
		wordList.setCellRenderer(new WordCellRenderer());
		wordListScrollPane = new JScrollPane(wordList);

	}

	public void start() {
		EventQueue.invokeLater(() -> {
			setBounds(500, 200, 400, 650);
			setupViews();
			setupLayout();
			addButtonsActionListener(this);
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Button button = (Button) e.getSource();
		if (button == addWordBtn) {
			String text = searchAndAddWordEd.getText();
			englishWarehouseController.addWord(text);
			wordDefaultListModel.addElement(new Word(text));
		} else if (button == removeWordBtn) {
			String text = searchAndAddWordEd.getText();
			for (int i = 0; i < wordDefaultListModel.size(); i++)
				if (wordDefaultListModel.getElementAt(i).getWord().equals(text)) {
					englishWarehouseController.removeWord(new Word(text));
					wordDefaultListModel.remove(i);
				}
		}
	}

	@Override
	public void onWordCreateSuccessfully(Word word) {
		JOptionPane.showMessageDialog(null, word.getWord() + " create successfuly"); //TODO 把等待環消除
	}

	@Override
	public void onWordRemoveSuccessfully(Word word) {
		JOptionPane.showMessageDialog(null, word.getWord() + " remove successfuly"); //TODO 把等待環消除
	}
	
	public void onWordReadSuccessfully(Word word) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWordCreateFailed(String word, Exception exception) {
		JOptionPane.showMessageDialog(null, word + " create failed");
	}

	@Override
	public void onWordReadFailed(String word, Exception exception) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWordReadSuccessfully(List<Word> words) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWordReadFailed(Exception exception) {
		// TODO Auto-generated method stub
		
	}
}

class WordCellRenderer extends JPanel implements ListCellRenderer {
	private static final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);
	private static Map<JPanel, JProgressBar> barMap = new HashMap<>();
	private JLabel label;
	public WordCellRenderer() {
		setOpaque(true);
		setLayout(new BorderLayout());
		label = new JLabel();
		add(label, BorderLayout.WEST);
	}

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		Word word = (Word) value;
		JProgressBar progressBar;
		if (barMap.containsKey(this))
			progressBar = barMap.get(this);
		else
		{
			progressBar = createProgressBar();
			add(progressBar, BorderLayout.EAST);
			barMap.put(this, progressBar);
		}
		label.setText(word.getWord());
		if (isSelected) {
			setBackground(HIGHLIGHT_COLOR);
			label.setForeground(Color.white);
		} else {
			setBackground(Color.white);
			label.setForeground(Color.black);
		}
		return this;
	}
	
	private JProgressBar createProgressBar() {
		JProgressBar progressBar = new JProgressBar();
		progressBar.setUI(new ProgressCircleBar());
		progressBar.setBackground(Color.WHITE);
		progressBar.setForeground(Color.ORANGE);
		progressBar.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		progressBar.setSize(50, 50);
		(new Timer(50, e -> {
		      int iv = Math.min(100, (progressBar.getValue() + 1) % 100);
		      progressBar.setValue(iv);
		    })).start();
		return progressBar;
	}
	
}
