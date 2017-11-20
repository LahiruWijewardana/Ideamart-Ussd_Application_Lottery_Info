package ussdApp.webcontent;

import ussdApp.model.Lottery;
import ussdApp.mappers.LotteryMapper;

import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetContent {
	
	public void getDLBWebContent() throws Exception {
		
		Document doc = Jsoup.connect("http://www.dlb.today/").userAgent("Mozilla/5.0").get();		
		Elements lotteryNameList = doc.getElementsByClass("lottery_n_d");		
		Elements resultBlockList = doc.getElementsByClass("result_detail");
		
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
						//Set lottery symbol if there is one
						if (lottery.getHasSymbol() && resultBlockList.get(i).getElementsByTag("img").size() > 1) {
							String symbolAddress = resultBlockList.get(i).getElementsByAttributeValueContaining("alt", "zodiac_sign").attr("src");
							
							if(symbolAddress.split("/").length > 0) {
								int symbolSize = symbolAddress.split("/").length;
								String symbol = symbolAddress.split("/")[symbolSize - 1].split("\\.")[0].replaceAll("[^A-Za-z]+", "");
								symbol = new GetContent().lotterySymbolMatch(symbol);
								
								lottery.setLotterySymbol(symbol);
							}				
							
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
						//Set symbol if there is one
						if (lottery.getHasSymbol() && resultBlockList.get(i).getElementsByTag("img").size() > 1) {
							String symbolAddress = resultBlockList.get(i).getElementsByAttributeValueContaining("alt", "zodiac_sign").attr("src");
							
							if(symbolAddress.split("/").length > 0) {
								int symbolSize = symbolAddress.split("/").length;
								String symbol = symbolAddress.split("/")[symbolSize - 1].split("\\.")[0].replaceAll("[^A-Za-z]+", "");
								symbol = new GetContent().lotterySymbolMatch(symbol);
								
								lottery.setLotterySymbol(symbol);
							}				
							
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
			System.out.println("Has a symbol" + lotteryList.get(i).getHasSymbol());
			System.out.println(lotteryList.get(i).getLotterySymbol());
		}
		
		System.out.println("Data succesfully updated");
	}
	
	public String lotterySymbolMatch (String symbol) {
		
		if (symbol.length() <= 3) {
			
			switch (symbol) {
			case "jan":
				symbol = "JANUARY";
				break;
				
			case "feb":
				symbol = "FEBRUARY";
				break;
				
			case "mar":
				symbol = "MARCH";
				break;
				
			case "sun":
				symbol = "SUNDAY";
				break;
				
			case "mon":
				symbol = "MONDAY";
				break;
				
			case "tue":
				symbol = "TUESDAY";
				break;
				
			case "wed":
				symbol = "WEDNESDAY";
				break;
				
			case "thu":
				symbol = "THURSDAY";
				break;
				
			case "fri":
				symbol = "FRIDAY";
				break;
				
			case "sat":
				symbol = "SATURDAY";
				break;
				
			}
				
		} else {
			symbol = symbol.toUpperCase();
		}
		
		return symbol;
	} 
	
//	public void getNLBWebContent () {
//		try {
//
//            String url = "http://www.nlb.lk/show-results.php";
//
//            URL obj = new URL(url);
//            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
//            conn.setReadTimeout(5000);
//            conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
//            conn.addRequestProperty("User-Agent", "Mozilla");
//
//            conn.setDoOutput(true);
//
//            OutputStreamWriter w = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
//
//            w.write("lott=1&dno=3537&date=");
//            w.close();
//
//            System.out.println("Request URL ... " + url);
//
//            int status = conn.getResponseCode();
//
//            System.out.println("Response Code ... " + status);
//
//            BufferedReader in = new BufferedReader(new InputStreamReader(
//                    conn.getInputStream()));
//            String inputLine;
//            StringBuffer html = new StringBuffer();
//
//            while ((inputLine = in.readLine()) != null) {
//                html.append(inputLine);
//            }
//
//            in.close();
//            conn.disconnect();
//            
//            Document doc = Jsoup.;
//            Elements resultBlockList = doc.getElementsByClass("lottery-numbers");	
//            Elements nameList = doc.getElementsByClass("title2");
//            
//            for (int i = 0; i < resultBlockList.size(); i++) {
//            		System.out.println(resultBlockList.get(i).text());
//            }
//            
//            for (int i = 0; i < nameList.size(); i++) {
//        			System.out.println(resultBlockList.get(i).text());
//            }
//
//            //System.out.println("URL Content... \n" + html.toString());
//            System.out.println("Done");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//	}

}
