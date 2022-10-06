package reserve;

public class Member {
	
	private String ID;
	private String PW;
	private String mname;
	private int birth;
	private String phoneNum;
	
	public Member() {
		
	}
		
	public Member(String iD, String pW) {

		ID = iD;
		PW = pW;
	}

	public Member(String iD, String pw, String mname, int birth, String phoneNum) {
		
		this.ID = iD;
		this.PW = pw;
		this.mname = mname;
		this.birth = birth;
		this.phoneNum = phoneNum;
	}
	

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getPW() {
		return PW;
	}

	public void setPW(String pW) {
		PW = pW;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public int getBirth() {
		return birth;
	}

	public void setBirth(int birth) {
		this.birth = birth;
	}

	public String getPhoneNumber() {
		return phoneNum;
	}

	public void setPhoneNumber(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	
}
