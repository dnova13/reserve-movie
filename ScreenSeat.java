package reserve;

public class ScreenSeat {
	
	 private long seno;
	 private int seatNum;
	 private String rowSeat;
	 private int colSeat;
	 private boolean purchased;
	 private int stno;

		public ScreenSeat(long seno, String rowSeat, int colSeat) {

		this.seno = seno;
		this.rowSeat = rowSeat;
		this.colSeat = colSeat;

	}	 
	 
	public ScreenSeat(long seno, int seatNum, String rowSeat, int colSeat, 
						boolean purchased, int stno) {

		this.seno = seno;
		this.seatNum = seatNum;
		this.rowSeat = rowSeat;
		this.colSeat = colSeat;
		this.purchased = purchased;
		this.stno = stno;
	}
	
	public long getSeno() {
		return seno;
	}

	public void setSeno(long seno) {
		this.seno = seno;
	}

	public int getSeatNum() {
		return seatNum;
	}

	public void setSeatNum(int seatNum) {
		this.seatNum = seatNum;
	}

	public String getRowSeat() {
		return rowSeat;
	}

	public void setRowSeat(String rowSeat) {
		this.rowSeat = rowSeat;
	}

	public int getColSeat() {
		return colSeat;
	}

	public void setColSeat(int colSeat) {
		this.colSeat = colSeat;
	}

	public boolean isPurchased() {
		return purchased;
	}

	public void setPurchased(boolean purchased) {
		this.purchased = purchased;
	}

	public int getStno() {
		return stno;
	}

	public void setStno(int stno) {
		this.stno = stno;
	}
	
	@Override
	public String toString() {
		return "["+ rowSeat + colSeat + "]";
	}

	public static void main(String[] args) {
		
		System.out.println(Boolean.parseBoolean("true"));
		System.out.println(Boolean.parseBoolean("false"));
		System.out.println(Boolean.parseBoolean("sdfds"));
		System.out.println(Boolean.parseBoolean("true"));
		
	}
	
}
