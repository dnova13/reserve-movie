package reserve;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TheaterLocationDao {
	
	final String driver		= "oracle.jdbc.driver.OracleDriver";
	final String url		= "jdbc:oracle:thin:@localhost:1521:xe";
	final String id 		= "movie";
	final String pw			= "1234";
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public ArrayList<TheaterLocation> selectTheaterLoc() { 
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			
			// sql select 설정
			String sql = "select * "
					   + "from theaterlocation "
					   + "order by locnum, location"; 
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			ArrayList<TheaterLocation> list = new ArrayList<>();
			
			while (rs.next()) {
				int tlno = rs.getInt("tlno"); 
				String location = rs.getString("location");
				int locnum = rs.getInt("locnum"); 
				
				TheaterLocation dto = new TheaterLocation(tlno, location, locnum);
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
	
	public int insertTheaterLoc(int locnum, String location) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "insert into theaterlocation (tlno,location,locnum) "
					+ " values (theaterlocation1.nextval,?,?)";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, location);
			pstmt.setInt(2, locnum);
			

			int count = pstmt.executeUpdate();
			
			return count;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(null, pstmt, conn);
		}
		return 0;
	}

	public int insertTheaterLoc(String location) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "insert into theaterlocation (tlno,location) "
					+ " values (theaterlocation1.nextval,?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, location); // ? 입력될 정보를 설정

			int count = pstmt.executeUpdate();
			
			return count;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(null, pstmt, conn);
		}
		return 0;
	}
	
	public int updateThLocationBythNum(int tlno,int modifyLocNum, String location) {

		try {

			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			
			String sql = "";
			
			if (modifyLocNum <= 0) {
								   
				 sql = "	update theaterlocation " 
							 + "set location = ? " 
						     + "where tlno = ?";

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, location);
				pstmt.setInt(2, tlno);
			}
			
			else if (location.length() == 0) {
				
				sql = "	update theaterlocation " 
						 + "set locnum = ? " 
					     + "where tlno = ?";

				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, modifyLocNum);
				pstmt.setInt(2, tlno);
			}
			
			else {
				sql = "	update theaterlocation "
							 + "set locnum = ?, "
							 + "    location = ? " 
							 + "where tlno = ?";

				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, modifyLocNum);
				pstmt.setString(2, location);
				pstmt.setInt(3, tlno);
			}
			 
			int count = pstmt.executeUpdate();
			
			if (count > 0) {
				System.out.println("데이터 입력 성공");
				return count;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(null,pstmt, conn);
		}
		
		return 0;
	}
	
	
	public int deleteLocByLocNum(int tlno) {

		try {

			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "	delete 	theaterlocation " + "	where	tlno = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tlno); // ? 입력될 정보를 설정

			int count = pstmt.executeUpdate();
			
			if (count > 0) {
				System.out.println("데이터 삭제 성공");
				return count;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(null,pstmt, conn);
		}
		
		return 0;
	}
	
	private void closeAll(ResultSet rs, 
			PreparedStatement pstmt, Connection conn) {
		if (rs != null) try { rs.close(); } catch (Exception e) { }
		if (pstmt != null) try { pstmt.close(); } catch (Exception e) { }
		if (conn != null) try { conn.close(); } catch (Exception e) { }
	}
	
	public static void main(String[] args) {
		
		
	}
	
	
}
