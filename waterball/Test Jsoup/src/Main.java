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
			Elements definitions = doc.select("h3.definition");  //查詢所有 class 屬性為 definition 的  h3 標籤
			System.out.println("單字："  + WORD);
			
			int i = 1;
			for (Element element : definitions)
				System.out.println("Part of speech：" + element.text().charAt(0) + " Definition" + 
						i++ + ": " + element.text().substring(1));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
