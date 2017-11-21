package ussdApp.webcontent;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import ussdApp.mappers.LotteryMapper;
import ussdApp.model.Lottery;
import ussdApp.util.Utility;

public class GetNLBWebContent {
	
	public void getWebContent () throws Exception {
		LotteryMapper getLotteryMapper = new LotteryMapper();
		ArrayList<Lottery> lotteryList = getLotteryMapper.getNLBLotteries();
		
		for (int i = 0; i < lotteryList.size(); i++) {
			GetNLBWebContent updateContent = new GetNLBWebContent();
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
			
			GetNLBWebContent getContent = new GetNLBWebContent();
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
