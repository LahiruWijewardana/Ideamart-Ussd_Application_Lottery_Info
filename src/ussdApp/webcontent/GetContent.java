package ussdApp.webcontent;

import java.net.URL;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;

public class GetContent {
	
	public void getWebContent() throws Exception {
		
//		URL url = new URL("http://www.nlb.lk/home.php?sin=0");
//		URLConnection con = url.openConnection();
//		Pattern p = Pattern.compile("text/html;\\s+charset=([^\\s]+)\\s*");
//		Matcher m = p.matcher(con.getContentType());
//		/* If Content-Type doesn't match this pre-conception, choose default and 
//		 * hope for the best. */
//		String charset = m.matches() ? m.group(1) : "ISO-8859-1";
//		Reader r = new InputStreamReader(con.getInputStream(), charset);
//		StringBuilder buf = new StringBuilder();
//		while (true) {
//		  int ch = r.read();
//		  if (ch < 0)
//		    break;
//		  buf.append((char) ch);
//		}
//		String str = buf.toString();
//		
//		System.out.println(str);
		
		Document doc = Jsoup.connect("http://www.dlb.today/").userAgent("Mozilla/5.0").get();
		Elements temp = doc.getElementsByClass("number_shanida number_circle");
		
		System.out.println(temp.size());
		
		for (int i=0; i < temp.size(); i++) {
			System.out.println(temp.get(i).text());
		}
	}
	
	

}
