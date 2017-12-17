package model.words;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import factory.ComponentAbstractFactory;
import model.Secret;
import model.sprite.BasicMapBuilder;
import model.sprite.BasicMapDirector;
import ui.MainView;
import utils.SoapHelper;
import utils.SoundPlayer;

/**
 * @author Waterball
 * The web service API referencing to http://tts.itri.org.tw/development/web_service_api.php#step2 which
 * provided by ITRI TTS in SOAP protocol. There might be several sequential actions within a TTS request:
 * 1. Send a request for synthesizing a TTS file from a vocabulary in SOAP.
 * 2. Get the convert Id of the synthesized file.
 * 3. Repeatedly query the convert Id to see if the synthesis done.
 * 4. Download the file from the url returned if done.
 * 5. Save the file by its directoryPath and the word given.
 * 6. Return the file absolute path.
 */
public class ITRI_TTS implements TTS{
	public static final String ENDPOINT = "http://tts.itri.org.tw/TTSService/Soap_1_3.php?wsdl";
	public static final String ACTION_BASE = "http://tts.itri.org.tw/TTSService/";
	public static final String ACTION_CONVERTSIMPLE = "ConvertSimple";
	public static final String ACTION_GET_CONVERT_STATUS = "GetConvertStatus";
	private static final String KEY_TTS_TEXT = "TTStext";
	private static final String KEY_ACCOUNT_ID = "accountID";
	private static final String KEY_PASSWORD = "password";
	private static final String KEY_CONVERT_ID = "password";
	private final String TTS_ACCOUNT;
	private final String TTS_PASSWORD;
	
	public ITRI_TTS(String ttsAccount, String ttsPassword){
		this.TTS_ACCOUNT = ttsAccount;
		this.TTS_PASSWORD = ttsPassword;
	}
	
	@Override
	public String saveWordTTS(String directoryPath, String word) throws TTSException {
		if (word == null || word.isEmpty())
			throw new NullPointerException("The word is not valid.");
		word = word.trim();
		int convertId = getConvertId(word);
		String resourceUrl = repeatedlyAskingForTheTTSProduct(convertId);
		File file = parseAndSaveFileFromUrl(resourceUrl, directoryPath, word);
		return file.getPath();
	}
	

	private int getConvertId(String word) throws TTSException{
		String resultTxt = null;
		try {
			final String actionEndpoint = ACTION_BASE + ACTION_CONVERTSIMPLE;
			Map<String, String> properties = createBaseProperties();
			properties.put(SoapHelper.NAMESPACE, ACTION_CONVERTSIMPLE);
			properties.put(KEY_TTS_TEXT, word);
			SOAPMessage response = SoapHelper.callSoapWebService(properties, ENDPOINT, actionEndpoint, false);
			resultTxt = getTextFromTheResultElement(response);
			if (resultTxt.charAt(0) != '0') // '0' defines a success
				throw new TTSException(resultTxt);
		} catch (SOAPException e) {
			e.printStackTrace();
			
		}
		// for instance result will be: 0&success&4494487, the number 4494487 is the convertId for further request.
		return Integer.parseInt(resultTxt.split("&")[2]); 
	}
	
	private String getTextFromTheResultElement(SOAPMessage response) throws SOAPException{
		SOAPBody body = response.getSOAPBody();
		NodeList nodeList = body.getElementsByTagName("Result");
		Element resuleElm = (Element) nodeList.item(0);
		return resuleElm.getTextContent().trim();
	}

    
	private String repeatedlyAskingForTheTTSProduct(int convertId) throws TTSException{
		String resourceUrl = null;
		while(true)  // repeatedly asking for the status until it's completed.
		{
			try{
				resourceUrl = getFileUrlByConvertId(convertId); 
				break;
			}catch (TTSProcessingException e) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		return resourceUrl;
	}
	
	private String getFileUrlByConvertId(int convertId) throws TTSException{
		final String actionEndpoint = ACTION_BASE + ACTION_GET_CONVERT_STATUS; // now we are going to get the convert status.
		String[] results = null;
		String fileUrl = null;
		Map<String, String> properties = createBaseProperties();
		properties.put(SoapHelper.NAMESPACE, ACTION_GET_CONVERT_STATUS);
		properties.put("convertID", String.valueOf(convertId));
		try {
			SOAPMessage response = SoapHelper.callSoapWebService(properties, ENDPOINT, actionEndpoint, false);
			results = getTextFromTheResultElement(response).split("&"); // for instance 0&success&0&queued
			if (!results[0].equals("0")) // result code
				throw new TTSException(Arrays.toString(results));
			if (!results[2].equals("2") ) // status code
				throw new TTSProcessingException(); // the TTS wav file is queued and waiting for processing
			if (results[2].equals("2")) // if succeed, the result will be like 0&Success&2&completed&http://tts.itri.org.tw/TTSService/download/Account/1111.wav
				fileUrl = results[4];
		} catch (SOAPException e) {
			e.printStackTrace();
		}
		return fileUrl;
	}
	
	private File parseAndSaveFileFromUrl(String url, String directoryPath, String word){
		URL resource;
		String name = word.length() > 15 ? word.substring(0, 15) :word;
		String fileName = directoryPath + "/" + name + ".wav";
		File file = new File(fileName);
		try {
			resource = new URL(url);
			ReadableByteChannel rbc = Channels.newChannel(resource.openStream());
			FileOutputStream fos = new FileOutputStream(file);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
	
	private Map<String, String> createBaseProperties(){
		Map<String, String> properties = new HashMap<>();
		properties.put(SoapHelper.NAMESPACE_URL, ACTION_BASE);
		properties.put(KEY_ACCOUNT_ID, TTS_ACCOUNT);
		properties.put(KEY_PASSWORD, TTS_PASSWORD);
		return properties;
	}

	public static void main(String[] argv){
		try {
			TTS tts = new ITRI_TTS(Secret.TTS_ACCOUNT, Secret.TTS_PASSWORD);
			String path = tts.saveWordTTS("sounds/vocabulary", "Question one, listen carefully.");
			System.out.println(path);
			new MainView(new ComponentAbstractFactory(new CrawlerVocabularycom(),
					new ITRI_TTS(Secret.TTS_ACCOUNT, Secret.TTS_PASSWORD), new WordXMLRepository("wordwarehouse"),
					new BasicMapBuilder(), new BasicMapDirector(new BasicMapBuilder()))).setVisible(true);
			SoundPlayer.getInstance().playSound(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
