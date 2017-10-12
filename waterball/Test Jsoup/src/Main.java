import java.io.IOException;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

	public static void main(String[] args) {
		final String WORD = "asdasndln";  //selected word
		final String LINK = "https://www.vocabulary.com/dictionary/" + WORD; // the link the htmlpage to crawl
		try {
			Document doc = Jsoup.connect(LINK).get();
			Elements definitions = doc.select("h3.definition");  //�d�ߩҦ� class �ݩʬ� definition ��  h3 ����
			System.out.println("��r�G"  + WORD);
			
			int i = 1;
			for (Element element : definitions)
				System.out.println("Part of speech�G" + element.text().charAt(0) + " Definition" + 
						i++ + ": " + element.text().substring(1));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
