package reserve;

public class ScreenTime {
	
	private int stno;
	private String sttime;
	private int sno;
	
	public ScreenTime(int stno, String sttime, int sno) {
	
		this.stno = stno;
		this.sttime = sttime;
		this.sno = sno;
	}

	public int getStno() {
		return stno;
	}

	public void setStno(int stno) {
		this.stno = stno;
	}

	public String getSttime() {
		return sttime;
	}

	public void setSttime(String sttime) {
		this.sttime = sttime;
	}

	public int getSno() {
		return sno;
	}

	public void setSno(int sno) {
		this.sno = sno;
	}
	
	
	
}
