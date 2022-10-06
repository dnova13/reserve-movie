package reserve;

public class Screen{
	
	private int sno;
	private String sname;
	private int totalSeat;
	private int totalColSeat;
	private int period;
	private int fno;
	private int tno;
	
	public Screen(int period, int fno) {
		super();
		this.period = period;
		this.fno = fno;
	}

	public Screen(int sno, String sname,  int totalSeat, int totalColseat, int fno, int tno) {
		
		this.sno = sno;
		this.sname = sname;
		this.totalSeat = totalSeat;
		this.totalColSeat = totalColseat;
		this.fno = fno;
		this.tno = tno;
	}
	
	public Screen(int sno, String sname,  int totalSeat, int totalColseat, int period, int fno, int tno) {
	
		this.sno = sno;
		this.sname = sname;
		this.totalSeat = totalSeat;
		this.totalColSeat = totalColseat;
		this.period = period;
		this.fno = fno;
		this.tno = tno;
	}

	public int getSno() {
		return sno;
	}

	public void setSno(int sno) {
		this.sno = sno;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public int getFno() {
		return fno;
	}

	public void setFno(int fno) {
		this.fno = fno;
	}

	public int getTno() {
		return tno;
	}

	public void setTno(int tno) {
		this.tno = tno;
	}

	public int getTotalSeat() {
		return totalSeat;
	}

	public void setTotalSeat(int totalSeat) {
		this.totalSeat = totalSeat;
	}

	public int getTotalColSeat() {
		return totalColSeat;
	}

	public void setTotalColSeat(int totalColseat) {
		this.totalColSeat = totalColseat;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}
	
	
}
