package reserve;

public class TheaterLocation {
	
	private int tlNo;
	private String locaction;
	private int locNum;
	
	public TheaterLocation(int tlNo, String locaction, int locNum) {
		super();
		this.tlNo = tlNo;
		this.locaction = locaction;
		this.locNum = locNum;
	}


	public int getTlNo() {
		return tlNo;
	}


	public void setTlNo(int tlNo) {
		this.tlNo = tlNo;
	}


	public int getLocNum() {
		return locNum;
	}


	public void setLocNum(int locNum) {
		this.locNum = locNum;
	}

	public String getLocaction() {
		return locaction;
	}


	public void setLocaction(String locaction) {
		this.locaction = locaction;
	}
}
