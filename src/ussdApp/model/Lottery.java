package ussdApp.model;

public class Lottery {
	private String lotteryName;
	private int drawNumber;
	private int numCount;
	private int letterCount;
	private int bonusNumCount;
	private Boolean hasSymbol;
	private int IdValue;
	private String lotteryNumbers;
	private String lotteryLetter;
	private String lotteryBonus;
	private String lotterySymbol;
	private Boolean latest;
	
	public Boolean getLatest() {
		return latest;
	}
	public void setLatest(Boolean latest) {
		this.latest = latest;
	}
	public Boolean getHasSymbol() {
		return hasSymbol;
	}
	public void setHasSymbol(Boolean hasSymbol) {
		this.hasSymbol = hasSymbol;
	}
	public String getLotterySymbol() {
		return lotterySymbol;
	}
	public void setLotterySymbol(String lotterySymbol) {
		this.lotterySymbol = lotterySymbol;
	}
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
	public int getIdValue() {
		return IdValue;
	}
	public void setIdValue(int idValue) {
		IdValue = idValue;
	}
}
