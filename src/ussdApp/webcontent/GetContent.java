package ussdApp.webcontent;

import ussdApp.model.Lottery;
import ussdApp.util.Utility;
import ussdApp.mappers.LotteryMapper;

import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;

public class GetContent {
	
	public void getDLBWebContent() throws Exception {
		
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
	
	public void getNLBContent () throws Exception {
		LotteryMapper getLotteryMapper = new LotteryMapper();
		ArrayList<Lottery> lotteryList = getLotteryMapper.getNLBLotteries();
		
		for (int i = 0; i < lotteryList.size(); i++) {
			GetContent updateContent = new GetContent();
			updateContent.updateNLBWebContent(lotteryList.get(i).getIdValue(), lotteryList.get(i).getDrawNumber());
		}
		
		System.out.println("All NLB lottories updated");
	}
	
	public void updateNLBWebContent (int Idvalue, int drawNum) throws Exception {
		
		String requestBody = "lott=" + Idvalue + "&dno=" + (drawNum + 1) + "&date=";
		System.out.println(requestBody);
		
		Document doc = Jsoup.connect(Utility.NLB_URL).requestBody(requestBody).userAgent("Mozilla/5.0").postDataCharset("UTF-8").data("Content-type", "application/x-www-form-urlencoded").post();
		
		Elements resultBlockList = doc.getElementsByClass("lottery-numbers");
		
		System.out.println(resultBlockList.size());
		
		if (resultBlockList.size() != 0) {
			Elements nameList = doc.getElementsByClass("title2");
			
			String name = nameList.get(0).text().split(" Results")[0];
			int len = nameList.get(0).text().split(" ").length;
			String draw = nameList.get(0).text().split(" ")[len - 1];
			
			System.out.println(name + " " + draw);
			
			Lottery lottery = new Lottery();
			lottery.setLotteryName(name);
			lottery.setDrawNumber(Integer.parseInt(draw));
			
			LotteryMapper lotteryMapper = new LotteryMapper();
			lottery = lotteryMapper.setCounts(lottery);
			
			System.out.println("Name: " + lottery.getLotteryName());
			System.out.println("Draw: " + lottery.getDrawNumber());
			System.out.println("letterCount: " + lottery.getLetterCount());
			System.out.println("BonusCount: " + lottery.getBonusNumCount());
			System.out.println("NumCount: " + lottery.getNumCount());
			System.out.println("IdValue: " + lottery.getIdValue());
			
			String results = resultBlockList.get(0).text();
			
			GetContent getContent = new GetContent();
			lottery = getContent.setNLBLottery(lottery, results);
			
			lotteryMapper.updateLottery(lottery);
			
			System.out.println(lottery.getLotteryName() + "Updated");
		} else {
			System.out.println("Lottery is upto date");
		}
		
	}
	
	public Lottery setNLBLottery(Lottery lottery, String results) {
		
		if (lottery.getBonusNumCount() == 0) {
			
			if(lottery.getLetterCount() == 0) {
				
				if (lottery.getHasSymbol()) {
					String symbol = results.replaceAll("[^A-Za-z]+", "");
					String [] numberList = results.split(" ");
					
					String numbers = null;
					
					for (int i = numberList.length - lottery.getNumCount(); i < numberList.length; i++) {
						
						if (i == numberList.length - lottery.getNumCount()) {
							numbers = numberList[i];
						} else {
							numbers = numbers + " " + numberList[i];
						}
						
					}
					
					lottery.setLotterySymbol(symbol);
					lottery.setLotteryNumbers(numbers);
					
				} else {
					lottery.setLotteryNumbers(results);
				}
				
			} else {
				
				String letterSet = results.replaceAll("[^A-Za-z]+", "");
				
				String [] numberList = results.split(" ");
				String numbers = null;
				
				for (int i = numberList.length - lottery.getNumCount(); i < numberList.length; i++) {
					
					if (i == numberList.length - lottery.getNumCount()) {
						numbers = numberList[i];
					} else {
						numbers = numbers + " " + numberList[i];
					}
					
				}
				
				lottery.setLotteryNumbers(numbers);
				lottery.setLotteryLetter(letterSet);
			}
			
		} else {
			
			if(lottery.getLetterCount() == 0) {
				
				String [] numberList = results.split(" ");
				String bonusNumbers = null;
				
				for (int i = 0; i < (numberList.length - lottery.getNumCount()); i++) {
					if (i == 0) {
						bonusNumbers = numberList[i];
					} else {
						bonusNumbers = bonusNumbers + " " + numberList[i];
					}
				}
							
				String numbers = null;
				
				for (int i = numberList.length - lottery.getNumCount(); i < numberList.length; i++) {
					
					if (i == numberList.length - lottery.getNumCount()) {
						numbers = numberList[i];
					} else {
						numbers = numbers + " " + numberList[i];
					}
					
				}
				
				lottery.setLotteryNumbers(numbers);
				lottery.setLotteryBonus(bonusNumbers);
				
			} else {
				
				//String letterSet = results.replaceAll("[^A-Za-z]+", "");
				//letterSet = letterSet.replaceAll("", " ").trim();
				
				String [] resultList = results.split(" ");
				
				String letterSet = null;
				for (int i = (resultList.length - (lottery.getNumCount() + lottery.getLetterCount())); i < (resultList.length - lottery.getNumCount()); i++) {
					if (i == (resultList.length - (lottery.getNumCount() + lottery.getLetterCount()))) {
						letterSet = resultList[i];
					} else {
						letterSet = letterSet + " " + resultList[i];
					}
				}
				
				String bonusNumbers = null;				
				for (int i = 0; i < (resultList.length - (lottery.getNumCount() + lottery.getLetterCount())); i++) {
					if (i == 0) {
						bonusNumbers = resultList[i];
					} else {
						bonusNumbers = bonusNumbers + " " + resultList[i];
					}
				}
				
				String numbers = null;
				for (int i = resultList.length - lottery.getNumCount(); i < resultList.length; i++) {
					
					if (i == resultList.length - lottery.getNumCount()) {
						numbers = resultList[i];
					} else {
						numbers = numbers + " " + resultList[i];
					}
					
				}
				
				lottery.setLotteryLetter(letterSet);
				lottery.setLotteryBonus(bonusNumbers);
				lottery.setLotteryNumbers(numbers);;
				
			}
			
		}
		
		return lottery;
	}

}
