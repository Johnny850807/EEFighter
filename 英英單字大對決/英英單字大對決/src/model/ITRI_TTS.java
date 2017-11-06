package model;

import java.io.File;
import java.io.IOException;

/**
 * @author Johnny850807
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
	
	@Override
	public String saveWordTTS(String directoryPath, String word) {
		if (word == null || word.isEmpty())
			throw new IllegalArgumentException("The word is not valid.");
		
		int convertId = getConvertId();
		String resourceUrl = getFileUrl(convertId);
		File file = parseFileFromUrl(resourceUrl, directoryPath, word);
		createAndSaveFile(file);
		return file.getAbsolutePath();
	}
	

	private int getConvertId(){
		return 0;
	}
	
	private String getFileUrl(int convertId){
		return "";
	}
	
	private File parseFileFromUrl(String url, String directoryPath, String word){
		return null;
	}
	
	private void createAndSaveFile(File file){
		try {
			boolean success = file.createNewFile();
			assert success : "The TTS file cannot be created.";
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
