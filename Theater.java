package reserve;

public class Theater {

	private int tNo;
	private String tname;
	private int locNo;

	Theater(int tno, String tname, int locNo) {

		this.tNo = tno;
		this.tname = tname;
		this.locNo = locNo;
	}

	public int getTno() {
		return tNo;
	}

	public void setTno(int tno) {
		this.tNo = tno;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public int getLocNo() {
		return locNo;
	}

	public void setLocNo(int locNo) {
		this.locNo = locNo;
	}

}
