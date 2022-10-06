package reserve;

import javax.swing.ImageIcon;

public class Film {
	
	private int filmNo;
	private String filmTitle;
	private String director;
	private int releaseDate; //데이터형식으로 받자 귀찮다
	private String runningTime;
	private int filmRate;
	private int filmOrder;
	private int paymentCnt;
	
	public Film() {
		// TODO Auto-generated constructor stub
	}
	
//	public Film(int filmNo, String filmTitle, String diretor, 
//				int releaseDate,  String runningtime , int filmRate, int filmOrder) {
//		
//		this.filmNo = filmNo;
//		this.filmTitle = filmTitle;
//		this.director = diretor;
//		this.releaseDate = releaseDate;
//		this.runningTime = runningtime;
//		this.filmRate = filmRate;
//		this.filmOrder = filmOrder;
//	}

	public Film(int filmNo, String filmTitle, String director, int releaseDate,String runningTime, int filmRate, 
			int filmOrder, int paymentCnt) {
		
		this.filmNo = filmNo;
		this.filmTitle = filmTitle;
		this.director = director;
		this.releaseDate = releaseDate;
		this.filmRate = filmRate;
		this.runningTime = runningTime;
		this.filmOrder = filmOrder;
		this.paymentCnt = paymentCnt;
	}

	public int getPaymentCnt() {
		return paymentCnt;
	}

	public void setPaymentCnt(int paymentCnt) {
		this.paymentCnt = paymentCnt;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public int getFilmOrder() {
		return filmOrder;
	}

	public void setFilmOrder(int filmOrder) {
		this.filmOrder = filmOrder;
	}

	public int getFilmNo() {
		return filmNo;
	}

	public void setFilmNo(int filmNo) {
		this.filmNo = filmNo;
	}

	public String getFilmTitle() {
		return filmTitle;
	}

	public void setFilmTitle(String filmTitle) {
		this.filmTitle = filmTitle;
	}

	public int getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(int releaseDate) {
		this.releaseDate = releaseDate;
	}

	public int getFilmRate() {
		return filmRate;
	}

	public void setFilmRate(int filmRate) {
		this.filmRate = filmRate;
	}

	public String getRunningTime() {
		return runningTime;
	}

	public void setRunningTime(String runningTime) {
		this.runningTime = runningTime;
	}
	
}
