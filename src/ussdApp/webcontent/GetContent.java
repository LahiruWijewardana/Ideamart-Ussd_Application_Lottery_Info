package ussdApp.webcontent;

import ussdApp.model.Lottery;
import ussdApp.mappers.LotteryMapper;

import java.util.ArrayList;
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
		//Elements numberList = doc.getElementsByClass("number_shanida number_circle");
		Elements lotteryNameList = doc.getElementsByClass("lottery_n_d");
		//Elements lotteryCharacterList = doc.getElementsByClass("eng_letter");
		
		Elements resultBlockList = doc.getElementsByClass("result_detail");
		
		//System.out.println(numberList.size());
		System.out.println(resultBlockList.size());
		
		ArrayList<Lottery> lotteryList = new ArrayList<Lottery>();
		
		for (int i=0; i < lotteryNameList.size(); i++) {
			
			String name = lotteryNameList.get(i).text().split(" \\| ")[0].split(" - ")[0];
			String draw = lotteryNameList.get(i).text().split(" \\| ")[0].split(" - ")[1];
			
			Lottery lottery = new Lottery();
			lottery.setLotteryName(name);
			lottery.setDrawNumber(Integer.parseInt(draw));
			
			lotteryList.add(lottery);
		}
		
		LotteryMapper lotteryMapper = new LotteryMapper();
		lotteryMapper.setCounts(lotteryList);
		
		for (int i = 0; i < resultBlockList.size(); i++) {
			
			String blockName = resultBlockList.get(i).getElementsByTag("img").get(0).attr("alt");
			
			for (int j = 0; j < lotteryList.size(); j++) {
				
				if (lotteryList.get(j).getLotteryName().equalsIgnoreCase(blockName)) {					
					
					Lottery lottery = lotteryList.get(j);
					
					if (lottery.getBonusNumCount() == 0) {
						Elements numberList = resultBlockList.get(i).getElementsByClass("number_shanida number_circle");						
						String lotteryNumbers = null;
						
						//Create number list string with spaces
						for (int x = 0; x < numberList.size(); x++) {
							if (x == 0) {
								lotteryNumbers = numberList.get(x).text();
							} else {
								lotteryNumbers = lotteryNumbers + " " + numberList.get(x).text();
							}							
						}
						
						lottery.setLotteryNumbers(lotteryNumbers);
						
						//Set lottery letter if there is one
						if (lottery.getLetterCount() != 0) {
							Elements lotteryletterList = resultBlockList.get(i).getElementsByClass("eng_letter");							
							lottery.setLotteryLetter(lotteryletterList.get(0).text());							
						}
						
					} else {
						Elements numberList = resultBlockList.get(i).getElementsByClass("number_shanida number_circle");						
						String lotteryNumbers = null;
						
						lottery.setLotteryBonus(numberList.last().text());
						
						//Create number list string with spaces
						for (int x = 0; x < (numberList.size() - lottery.getBonusNumCount()); x++) {
							if (x == 0) {
								lotteryNumbers = numberList.get(x).text();
							} else {
								lotteryNumbers = lotteryNumbers + " " + numberList.get(x).text();
							}							
						}
						
						lottery.setLotteryNumbers(lotteryNumbers);
						
						//Set lottery letter if there is one
						if (lottery.getLetterCount() != 0) {
							Elements lotteryletterList = resultBlockList.get(i).getElementsByClass("eng_letter");							
							lottery.setLotteryLetter(lotteryletterList.get(0).text());							
						}
						
					}
				}
			}
		}
		
		for (int i=0; i < lotteryList.size(); i++) {
			
			lotteryMapper.updateLottery(lotteryList.get(i));
			
			System.out.println(lotteryList.get(i).getLotteryName());
			System.out.println("num count"+lotteryList.get(i).getNumCount());
			System.out.println(lotteryList.get(i).getLotteryNumbers());
			System.out.println("letter count"+lotteryList.get(i).getLetterCount());
			System.out.println(lotteryList.get(i).getLotteryLetter());
			System.out.println("bonus count"+lotteryList.get(i).getBonusNumCount());
			System.out.println(lotteryList.get(i).getLotteryBonus());
		}
		
		System.out.println("Data succesfully updated");
	}
	
	

}
