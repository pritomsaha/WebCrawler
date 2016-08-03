import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akash on 03-Aug-16.
 */
public class ProthomAloCrawling {
    String bodyText="";
    public void start(String url){
        List<String> newsLinks=new ArrayList<>();
        Document htmlDocument=CommonOperations.connect(url);
        if(htmlDocument!=null){
            Element archDiv=htmlDocument.getElementsByClass("all_titles_widget").first();
            Elements links=archDiv.getElementsByTag("a");
            for (Element link: links){
                newsLinks.add(link.absUrl("href"));
            }
            for (String link:newsLinks){
                crawlToArticle(link);
            }
            CommonOperations.WriteToFile(bodyText,"prothomAlo.txt");
        }
    }
    public void crawlToArticle(String url){
        Document htmlDocument=CommonOperations.connect(url);
        if (htmlDocument!=null){
            Element title=htmlDocument.getElementsByClass("title mb10").first();
            Element article=htmlDocument.select("[itemprop=articleBody]").first();
            if(title!=null){
                bodyText+=title.text();
            }
            if(article!=null){
                bodyText+=article.text();
            }
        }
    }
}
