package model.words;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Node;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Waterball
 */
public class WordXMLRepository implements WordRepository{
	private static final String ROOT_TAG = "EnglishWarehouse";
	private static final String WORD = "Word";
	private static final String WORDNAME = "name";
	private static final String SOUNDPATH = "SoundPath";
	private static final String DEFINITION = "Definition";
	private static final String PARTOFSPEECH = "partOfSpeech";
	private DocumentBuilder documentBuilder;
	private String fileName;
	private File file;
	
	public WordXMLRepository(String fileName) {
		this.fileName = fileName + ".xml";
		init();
	}
	
	private void init(){
		try {
			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			this.file = new File(this.fileName);
			if (!file.exists())
				file.createNewFile();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void addWord(Word word) {
		
	}
	
	@Override
	public List<Word> readAllWord() throws ReadWordFailedException {
		try {
			Document document = documentBuilder.parse(file);
			List<Element> wordElements = filterElements(document.getElementsByTagName(WORD));
			return wordElements.stream()
								.map(e -> elementToWord(e))
								.collect(Collectors.toList());
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	@Override
	public Word readWord(String wordtext) throws ReadWordFailedException {
		return null;
	}
	
	private List<Element> filterElements(NodeList nodeList){
		List<Element> elements = new ArrayList<>();
		for(int i = 0 ; i < nodeList.getLength() ; i ++)
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE)
				elements.add((Element)nodeList.item(i));
		return elements;
	}
	
	private Word elementToWord(Element element){
		Word word = new Word(element.getAttribute(WORDNAME));
		word.setSoundPath(getSoundPathFromWordElement(element));
		word.setSentences(getDefinitionMapFromWordElement(element));
		return word;
	}
	
	private String getSoundPathFromWordElement(Element wordElement){
		List<Element> soundPathElements = filterElements(wordElement.getElementsByTagName(SOUNDPATH));
		return soundPathElements.get(0).getTextContent();
	}
	
	private Map<String, List<String>> getDefinitionMapFromWordElement(Element wordElement){
		Map<String, List<String>> definitionMap = new HashMap<>();
		List<Element> definitionElements = filterElements(wordElement.getElementsByTagName(DEFINITION));
		for(Element element : definitionElements)
		{
			String partOfSpeech = element.getAttribute(PARTOFSPEECH).toUpperCase();
			if (!definitionMap.containsKey(partOfSpeech))
				definitionMap.put(partOfSpeech, new ArrayList<>());
			definitionMap.get(partOfSpeech).add(element.getTextContent());
		}
		return definitionMap;
	}

	@Override
	public void removeWord(Word word) {
		// TODO Auto-generated method stub
		
	}

	
	public static void main(String[] argv) throws Exception{
		WordXMLRepository repository = new WordXMLRepository("words");
		Word apple = new CrawlerVocabularycom().crawlWordAndGetSentence("apple");
		System.out.println(apple);
		repository.readAllWord()
					.stream()
					.forEach(w -> System.out.println(w));
	}
}
