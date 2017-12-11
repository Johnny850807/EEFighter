package model.words;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Waterball
 */
public class WordXMLRepository implements WordRepository{
	private static final String ENGLISH_WAREHOUSE = "EnglishWarehouse";
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
				createNewXmlFile(file);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void createNewXmlFile(File file) throws IOException{
		file.createNewFile();
	}
	
	@Override
	public void addWord(Word word) {
		try {
			synchronized (this) {
				Document document = documentBuilder.parse(file);
				Element englishWareHouseElement = filterElements(document.getElementsByTagName(ENGLISH_WAREHOUSE)).get(0);
				removeDuplicatedWordElement(document, englishWareHouseElement, word.getWord());
				englishWareHouseElement.appendChild(wordToElement(document, word));
				outputDocument(document);
			}
		} catch (SAXException | IOException | TransformerException | XPathExpressionException e) {
			e.printStackTrace();
		}
	}
	
	private void removeDuplicatedWordElement(Document document, Element englishWareHouseElement, String wordName) throws XPathExpressionException{
		List<Element> existingSameWordElements = findWordElements(document, wordName);
		existingSameWordElements.parallelStream()
								.forEach(e -> englishWareHouseElement.removeChild(e));
	}
	
	private void outputDocument(Document document) throws TransformerException{
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(file);
		transformer.transform(source, result);
	}
	
	@Override
	public List<Word> readAllWord() throws ReadWordFailedException {
		try {
			synchronized (this) {
				Document document = documentBuilder.parse(file);
				List<Element> wordElements = filterElements(document.getElementsByTagName(WORD));
				return wordElements.stream()
									.map(e -> elementToWord(e))
									.collect(Collectors.toList());
			}
		} catch (SAXException | IOException e) {
			e.printStackTrace();
			throw new ReadWordFailedException(e);
		}
	}
	

	@Override
	public Word readWord(String wordName) throws ReadWordFailedException {
		try{
			synchronized (this) {
				Document document = documentBuilder.parse(file);
				Element wordElement = findWordElements(document, wordName).get(0);
				return elementToWord(wordElement);
			}
		}catch (XPathExpressionException | SAXException | IOException e) {
			e.printStackTrace();
			throw new ReadWordFailedException(e);
		}
	}
	
	private List<Element> filterElements(NodeList nodeList){
		List<Element> elements = new ArrayList<>();
		for(int i = 0 ; i < nodeList.getLength() ; i ++)
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE)
				elements.add((Element)nodeList.item(i));
		return elements;
	}
	
	private List<Element> findWordElements(Document document, String wordName) throws XPathExpressionException{
		String xpathExpression = String.format("//Word[@name='%s']", wordName);
		NodeList results = (NodeList) executeXPath(document, xpathExpression, XPathConstants.NODESET);
		return filterElements(results);
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
	
	private Map<PartOfSpeech, List<String>> getDefinitionMapFromWordElement(Element wordElement){
		Map<PartOfSpeech, List<String>> definitionMap = new HashMap<>();
		List<Element> definitionElements = filterElements(wordElement.getElementsByTagName(DEFINITION));
		for(Element element : definitionElements)
		{
			PartOfSpeech partOfSpeech = PartOfSpeech.valueOf(element.getAttribute(PARTOFSPEECH).toUpperCase());
			if (!definitionMap.containsKey(partOfSpeech))
				definitionMap.put(partOfSpeech, new ArrayList<>());
			definitionMap.get(partOfSpeech).add(element.getTextContent());
		}
		return definitionMap;
	}
	
	private Element wordToElement(Document document, Word word){
		Element wordElement = document.createElement(WORD);
		Element soundPathElement = document.createElement(SOUNDPATH);
		wordElement.setAttribute(WORDNAME, word.getWord());
		soundPathElement.setTextContent(word.getSoundPath());
		wordElement.appendChild(soundPathElement);
		for (PartOfSpeech partOfSpeech : word.getSentences().keySet())
			for (String definition : word.getSentences().get(partOfSpeech))
			{
				Element definitionElement = document.createElement(DEFINITION);
				definitionElement.setAttribute(PARTOFSPEECH, partOfSpeech.toString());
				definitionElement.setTextContent(definition);
				wordElement.appendChild(definitionElement);
			}
		return wordElement;
	}

	@Override
	public void removeWord(Word word) {
		try {
			Document document = documentBuilder.parse(file);
			Element englishwarehouseElement = filterElements(document.getElementsByTagName(ENGLISH_WAREHOUSE)).get(0);
			String xpathExpression = String.format("//Word[@name='%s']", word.getWord());
			Node wordNode = (Node) executeXPath(document, xpathExpression, XPathConstants.NODE);
			englishwarehouseElement.removeChild(wordNode);
			outputDocument(document);
		} catch (SAXException | IOException | XPathExpressionException | TransformerException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			throw new IllegalArgumentException("Removed word not found");
		}
	}
	
	private Object executeXPath(Document document, String xpathExpression, QName type) throws XPathExpressionException{
		XPath xpath = XPathFactory.newInstance().newXPath();
		XPathExpression expr = xpath.compile(xpathExpression);
		return expr.evaluate(document, type);
	}

	
	public static void main(String[] argv) throws Exception{
		String[] words = {"apple", "banana", "vocabulary", "subtropical", "several", "genus", "have", "terminal"};
		WordXMLRepository repository = new WordXMLRepository("words");
		for (String word : words)
		{
			Word wordProduct = new CrawlerVocabularycom().crawlWordAndGetSentence(word);
			repository.addWord(wordProduct);
		}
		/*Word wordProduct = new CrawlerVocabularycom().crawlWordAndGetSentence("have");
		System.out.println(repository.readWord("banana"));
		repository.removeWord(wordProduct);*/
		repository.readAllWord()
					.stream()
					.forEach(w -> System.out.println(w));
	}
}
