/**
 * Created by Akash on 02-Aug-16.
 */
public class Main {

    public static void main(String[] args){
        //new JugantorCrawling().start("http://www.jugantor.com/online/archive/2016/08/01");
        //new ProthomAloCrawling().start("http://www.prothom-alo.com/archive/2016-08-03");
        //new SomokalCrawling().start("http://bangla.samakal.net/todays-print-edition/2016/08/03");
    	new LiteratureCrawling().start("http://www.rabindra-rachanabali.nltr.org/node/2");
    	System.out.println("Total words "+CommonOperations.n);
    }
}
