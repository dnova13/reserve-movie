package reserve;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MemberDao {
	// driver, url, id, pw
	final String driver = "oracle.jdbc.driver.OracleDriver";
	final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	final String id = "movie";
	final String pw = "1234";

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	// select 맴버 데이터 조회
	public ArrayList<Member> selectMember() { 

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "select * from member";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			ArrayList<Member> list = new ArrayList<>();

			while (rs.next()) {
				 
				String ID = rs.getString("mid");
				String PW = rs.getString("mpw");
				String mname = rs.getString("mname");
				int birth = rs.getInt("birth");
				String phoneNum = rs.getString("phonnum");

				Member dto = new Member(ID, PW, mname, birth, phoneNum);
				list.add(dto);
			}

			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}

		return null;
	}

	// checkId 맴버아이디 중복 체크
	public boolean checkDuplictedID(String mID) { 

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "select mid " + "from member " + "where mid = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mID);

			rs = pstmt.executeQuery();

			String id = null;
			
			while (rs.next()) {

				id = rs.getString("mid");
			}
			if (mID.equals(id)) {
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
		return false;
	}

	// 맴버 아이디 비번 확인. 로그인 메뉴
	public boolean checkMember(Member member) { 

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "select mid,mpw " + "from member " + "where mid = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getID());

			rs = pstmt.executeQuery();
			
			String password = null;
			
			while (rs.next()) {

				password = rs.getString("mpw");
			}
			
			if (member.getPW().equals(password)) {
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
		return false;
	}
	
	// 생일을 얻음
	public int getBirth(String mID) { 

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "select birth " + "from member " + "where mid = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mID);

			rs = pstmt.executeQuery();

			int birth = -1;
			
			while (rs.next()) {

				birth = rs.getInt("birth");

			}

			return birth;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
		return -1;
	}
	
	// insert 맴버 추가
	public int insertMember(String ID, String PW, String mname, int birth, String phoneNum,String eMail) {

		try {

			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "insert into member (mid, mpw, mname, birth, phoneNum,email)" + " values (?,?,?,?,?,?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, ID.trim());
			pstmt.setString(2, PW.trim());
			pstmt.setString(3, mname.trim());
			pstmt.setInt(4, birth);
			pstmt.setString(5, phoneNum);
			pstmt.setString(6, eMail);

			int count = pstmt.executeUpdate();
			
			return count;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(null, pstmt, conn);
		}
		
		return -1;
	}
	
	private void closeAll(ResultSet rs, PreparedStatement pstmt, Connection conn) {
		if (rs != null)
			try {
				rs.close();
			} catch (Exception e) {
			}
		if (pstmt != null)
			try {
				pstmt.close();
			} catch (Exception e) {
			}
		if (conn != null)
			try {
				conn.close();
			} catch (Exception e) {
			}
	}

	public static void main(String[] args) {

		MemberDao md = new MemberDao();

//		md.insertMember("dno", "4234", "fsds", 11, 111);

//		System.out.println(md.checkID("dnova"));
//		System.out.println(md.checkID("manager"));
//		System.out.println(md.checkID("dnova13"));
//		System.out.println(md.getBirth("dnoava13"));
//		System.out.println(md.getBirth("dnova13"));

	}
}
