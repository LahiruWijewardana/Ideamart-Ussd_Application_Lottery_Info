package ussdApp.model;

public class Lottery {
	private String lotteryName;
	private int drawNumber;
	private int numCount;
	private int letterCount;
	private int bonusNumCount;
	private String lotteryNumbers;
	private String lotteryLetter;
	private String lotteryBonus;
	
	public String getLotteryName() {
		return lotteryName;
	}
	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}
	public int getDrawNumber() {
		return drawNumber;
	}
	public void setDrawNumber(int drawNumber) {
		this.drawNumber = drawNumber;
	}
	public int getNumCount() {
		return numCount;
	}
	public void setNumCount(int numCount) {
		this.numCount = numCount;
	}
	public int getLetterCount() {
		return letterCount;
	}
	public void setLetterCount(int letterCount) {
		this.letterCount = letterCount;
	}
	public int getBonusNumCount() {
		return bonusNumCount;
	}
	public void setBonusNumCount(int bonusNumCount) {
		this.bonusNumCount = bonusNumCount;
	}
	public String getLotteryNumbers() {
		return lotteryNumbers;
	}
	public void setLotteryNumbers(String lotteryNumbers) {
		this.lotteryNumbers = lotteryNumbers;
	}
	public String getLotteryLetter() {
		return lotteryLetter;
	}
	public void setLotteryLetter(String lotteryLetter) {
		this.lotteryLetter = lotteryLetter;
	}
	public String getLotteryBonus() {
		return lotteryBonus;
	}
	public void setLotteryBonus(String lotteryBonus) {
		this.lotteryBonus = lotteryBonus;
	}
}
