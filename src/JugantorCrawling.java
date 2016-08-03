import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akash on 03-Aug-16.
 */
public class JugantorCrawling {
    String bodyText="";
    public void start(String url){
        List<String> newsLinks=new ArrayList<>();
        Document htmlDocument=CommonOperations.connect(url);
        if (htmlDocument!=null){
            Elements ArchDivs=htmlDocument.getElementsByClass("archMenuPanel");

            for (Element div: ArchDivs){
                Elements newsHeadlines=div.getElementsByTag("a");
                for (Element link: newsHeadlines){
                    newsLinks.add(link.absUrl("href"));
                }
            }
            for(String link:newsLinks){
                crawlToArticle(link);
            }
            CommonOperations.WriteToFile(bodyText, "jugantor.txt");
        }

    }
    public void crawlToArticle(String url){
        Document htmlDocument=CommonOperations.connect(url);
        if (htmlDocument!=null){
            Element newsTitle=htmlDocument.getElementById("hl2");
            Element newsDetails=htmlDocument.getElementById("myText");
            if(newsTitle!=null){
                bodyText+=newsTitle.text();
            }
            if(newsDetails!=null){
                bodyText+=newsDetails.text();
            }
        }
    }
}