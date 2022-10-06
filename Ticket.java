package reserve;

public class Ticket {

	private int tkno;
	private int fno;
	private String ftitle;
	private String runningtime;
	private int scrDate;
	private String location;
	private String tname;
	private String sname;
	private String sttime;
	private String seatName;
	private int stno;
	private long seno;
	private int odno;
	
	public Ticket(int tkno, int fno, int scrDate, String location, String tname, String sname, int stno, long seno,
			int odno) {

		this.tkno = tkno;
		this.fno = fno;
		this.scrDate = scrDate;
		this.location = location;
		this.tname = tname;
		this.sname = sname;
		this.stno = stno;
		this.seno = seno;
		this.odno = odno;
	}
	
	public Ticket(int tkno, int fno, String ftitle, String runningtime, int scrDate, String location, String tname,
			String sname, String sttime, String seatName, int stno, long seno, int odno) {
		super();
		this.tkno = tkno;
		this.fno = fno;
		this.ftitle = ftitle;
		this.runningtime = runningtime;
		this.scrDate = scrDate;
		this.location = location;
		this.tname = tname;
		this.sname = sname;
		this.sttime = sttime;
		this.seatName = seatName;
		this.stno = stno;
		this.seno = seno;
		this.odno = odno;
	}

	public String getFtitle() {
		return ftitle;
	}

	public void setFtitle(String ftitle) {
		this.ftitle = ftitle;
	}

	public String getRunningtime() {
		return runningtime;
	}

	public void setRunningtime(String runningtime) {
		this.runningtime = runningtime;
	}

	public int getTkno() {
		return tkno;
	}


	public void setTkno(int tkno) {
		this.tkno = tkno;
	}


	public int getFno() {
		return fno;
	}


	public void setFno(int fno) {
		this.fno = fno;
	}


	public int getScrDate() {
		return scrDate;
	}


	public void setScrDate(int scrDate) {
		this.scrDate = scrDate;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getTname() {
		return tname;
	}


	public void setTname(String tname) {
		this.tname = tname;
	}


	public String getSname() {
		return sname;
	}


	public void setSname(String sname) {
		this.sname = sname;
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


	public long getSeno() {
		return seno;
	}


	public void setSeno(long seno) {
		this.seno = seno;
	}


	public String getSeatName() {
		return seatName;
	}


	public void setSeatName(String seatName) {
		this.seatName = seatName;
	}


	public int getOdno() {
		return odno;
	}


	public void setOdno(int odno) {
		this.odno = odno;
	}	
}
