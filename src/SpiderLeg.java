
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * Created by Akash on 02-Aug-16.
 */
public class SpiderLeg {
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    String bodyText="";
    public boolean crawlPageLink(String url){
        List<String> pageLinks = new ArrayList<>();
        try {
            Connection connection=Jsoup.connect(url).userAgent(USER_AGENT);
            Document htmlDocument=connection.get();

            if(connection.response().statusCode()==200){
                System.out.println("\n**Visiting** Received web page at " + url);
            }
            if(!connection.response().contentType().contains("text/html"))
            {
                System.out.println("**Failure** Retrieved something other than HTML");
                return false;
            }
            if (htmlDocument!=null){
                Elements divs=htmlDocument.getElementsByTag("div");
                Element targetDiv=null;
                for (Element div:divs){
                    if(div.className().contains("p_d")){
                        targetDiv=div;break;
                    }
                }
                if(targetDiv!=null){
                    divs=targetDiv.getElementsByTag("div");
                    Element menuDiv=null;
                    for (Element div:divs){
                        if(div.className().contains("the_menu_container mb5")){
                            menuDiv=div;break;
                        }
                    }

                    if(menuDiv!=null){
                        Elements menuLinks=menuDiv.getElementsByTag("a");
                        for(Element menu: menuLinks){
                            pageLinks.add(menu.absUrl("href"));
                        }

                        for (String link:pageLinks){
                            crawlNewsLink(link);
                        }
                        WriteToFile();
                    }
                }
            }
            return true;
        }
        catch (IOException ioe){
            return false;
        }
    }

    public void crawlNewsLink(String url){
        Connection connection=Jsoup.connect(url).userAgent(USER_AGENT);
        Document htmlDocument= null;
        List<String> newsLinks=new ArrayList<>();
        try {
            htmlDocument = connection.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(connection.response().statusCode()==200){
            System.out.println("\n**Visiting** Received web page at " + url);
        }

        if(htmlDocument!=null){
            Elements divs=htmlDocument.getElementsByTag("div");
            Element targetDiv=null;
            for (Element div:divs){
                if(div.className().contains("p_d")){
                    targetDiv=div;break;
                }
            }
            if (targetDiv!=null){
                Elements detailsLink=targetDiv.getElementsByTag("a");
                for(Element link:detailsLink){
                    if(link.className().contains("more_link"))
                        newsLinks.add(link.absUrl("href"));
                }

                for (String link:newsLinks){
                    crawlToArticle(link);
                }
            }
        }

    }

    public void crawlToArticle(String url){

        Connection connection=Jsoup.connect(url).userAgent(USER_AGENT);
        Document htmlDocument= null;
        try {
            htmlDocument = connection.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(connection.response().statusCode()==200){
            System.out.println("\n**Visiting** Received web page at " + url);
        }

        if(htmlDocument!=null){
            Elements divs=htmlDocument.getElementsByTag("div");
            Element targetDiv=null;
            for (Element div:divs){
                if(div.className().contains("p_d")){
                    targetDiv=div;break;
                }
            }
            if(targetDiv!=null){
                divs=targetDiv.getElementsByTag("div");
                for (Element div:divs){
                    if(div.className().contains("title_container")){
                        bodyText+="\n\n"+div.text()+"\n";break;
                    }
                }

                Elements articles = targetDiv.getElementsByTag("article");

                for(Element article : articles)
                {
                    bodyText += article.text();
                }
            }
        }
    }

    public void WriteToFile(){
        try
        {
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("result.txt"), "UTF8"));
            String words[]=bodyText.split(" ");

            writer.append(bodyText);
            writer.close();

        }
        catch ( IOException e)
        {
        }
    }

}
