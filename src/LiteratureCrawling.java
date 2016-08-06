import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class LiteratureCrawling {
	Set<String> Visitedlinks=new HashSet<String>();
	public void start(String url){
		List<String> MenuLinks=new ArrayList<String>();
		Document htmlDocument=CommonOperations.connect(url);
		if(htmlDocument!=null){
			Element form=htmlDocument.getElementsByTag("form").first();
			Elements menus=form.getElementsByTag("a");
			for (Element link : menus) {
				MenuLinks.add(link.absUrl("href"));
			}
			for (String link : MenuLinks) {
				crawl(link);
			}
		}
	}
	public void crawl(String url){
		Document htmlDocument=CommonOperations.connect(url);
		String text="";
		if(htmlDocument!=null){
			Elements tables=htmlDocument.getElementsByClass("list");
			if(tables!=null){
				for(Element table:tables){
					Elements links=table.getElementsByTag("a");
					for(Element link:links){
						String URL=link.absUrl("href");
						if(URL!="#"){
							if(!Visitedlinks.contains(URL)){
								Visitedlinks.add(URL);
								crawl(URL);
							}
						}
					}
				}
			}
			Element MainContent=htmlDocument.select("div.content.clear-block").first();
			if (MainContent!=null) {
				/*Elements divs=MainContent.getElementsByTag("div");
				for(Element div: divs){
					text+=div.text()+"\r\n";
				}*/
				CommonOperations.WriteToFile(MainContent.text(), "literature.txt");
			}
			Element nextPageDiv=htmlDocument.getElementById("rightnavigation");
			if (nextPageDiv!=null) {
				String nextPagelink=nextPageDiv.getElementsByTag("a").first().absUrl("href");
				if(nextPagelink!=null)crawl(nextPagelink);
			}
			
			/*if(!Visitedlinks.contains(nextPagelink)){
				Visitedlinks.add(nextPagelink);*/
				
			//}
		}
	}
}
