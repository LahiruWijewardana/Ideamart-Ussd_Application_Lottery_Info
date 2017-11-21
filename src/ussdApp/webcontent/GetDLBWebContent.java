package ussdApp.webcontent;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import ussdApp.mappers.LotteryMapper;
import ussdApp.model.Lottery;

public class GetDLBWebContent {
	
	public void getWebContent() throws Exception {
		
		Document doc = Jsoup.connect("http://www.dlb.today/").userAgent("Mozilla/5.0").get();		
		Elements lotteryNameList = doc.getElementsByClass("lottery_n_d");		
		Elements resultBlockList = doc.getElementsByClass("result_detail");
		
		System.out.println(resultBlockList.size());
		
		LotteryMapper lotteryMapper = new LotteryMapper();
		ArrayList<Lottery> lotteryList = new ArrayList<Lottery>();
		
		for (int i=0; i < lotteryNameList.size(); i++) {
			
			String name = lotteryNameList.get(i).text().split(" \\| ")[0].split(" - ")[0];
			String draw = lotteryNameList.get(i).text().split(" \\| ")[0].split(" - ")[1];
			
			Lottery lottery = new Lottery();
			lottery.setLotteryName(name);
			lottery.setDrawNumber(Integer.parseInt(draw));
			lottery = lotteryMapper.setCounts(lottery);
			
			if (!lottery.getLatest()) {	
				lotteryList.add(lottery);
			}
		}
		
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
								symbol = new GetDLBWebContent().lotterySymbolMatch(symbol);
								
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
								symbol = new GetDLBWebContent().lotterySymbolMatch(symbol);
								
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
}
