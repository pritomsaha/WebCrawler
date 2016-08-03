import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;

/**
 * Created by Akash on 03-Aug-16.
 */
public class CommonOperations {

    public static Document connect(String url){
        String USER_AGENT =
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
        Document document=null;
        Connection connection= Jsoup.connect(url).userAgent(USER_AGENT);
        try {
            document=connection.get();
            if(connection.response().statusCode()==200){
                System.out.println("\n**Visiting** Received web page at " + url);
            }
            if(!connection.response().contentType().contains("text/html"))
            {
                System.out.println("**Failure** Retrieved something other than HTML");
                return null;
            }

        } catch (IOException e) {
            System.out.println("Problem to connect the URL: "+url);
        }
        return document;
    }

    public static void WriteToFile(String bodyText,String path){

        try
        {
            File file=new File(path);
            if(!file.exists()){
                file.createNewFile();
            }
            PrintWriter writer=new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF8"));
            String words[]=bodyText.split("[ ]+");
            for (String word:words){
                writer.println(word);
            }
            writer.close();
        }
        catch ( IOException e)
        {
            e.printStackTrace();
        }
    }
}
