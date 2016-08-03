import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Akash on 03-Aug-16.
 */
public class SomokalCrawling {
    String bodyText="";
    public void start(String url){

        Document htmlDocument=CommonOperations.connect(url);
        if (htmlDocument!=null){
            Elements MenuItems=htmlDocument.select("[id=menu_title]");

            for (Element menu:MenuItems){
                crawlToPage(menu.getElementsByTag("a").first().absUrl("href"));
            }
            CommonOperations.WriteToFile(bodyText,"somokal.txt");
        }
    }

    public void crawlToPage(String url){
        Document htmlDocument=CommonOperations.connect(url);
        if (htmlDocument!=null){
            Elements newsDiv1=htmlDocument.select("[id=hl2]");
            Elements newsDiv2=htmlDocument.select("[id=groupArea]");
            for (Element div:newsDiv1){
                crawlToArticle(div.getElementsByTag("a").first().absUrl("href"));
            }
            for (Element div:newsDiv2){
                crawlToArticle(div.getElementsByTag("a").first().absUrl("href"));
            }
        }
    }

    public void crawlToArticle(String url){
        Document htmlDocument=CommonOperations.connect(url);
        if (htmlDocument!=null){
            Element title=htmlDocument.getElementById("hl2");
            Element details=htmlDocument.getElementById("newsDtl");
            if(title!=null){
                bodyText+=title.text();
            }
            if (details!=null){
                bodyText+=details.text();
            }
        }
    }
}
