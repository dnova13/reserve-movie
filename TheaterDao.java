package reserve;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TheaterDao {

	final String driver = "oracle.jdbc.driver.OracleDriver";
	final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	final String id = "movie";
	final String pw = "1234";

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	public ArrayList<Theater> selectTheater() {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			// sql select 설정
			String sql = "select * from theater order by tlno, tname";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			ArrayList<Theater> list = new ArrayList<>();

			while (rs.next()) {
				int tno = rs.getInt("tno"); // db에서 숫자를 얻어온다.
				String tname = rs.getString("tname");
				int tlno = rs.getInt("tlno");
				
				Theater dto = new Theater(tno, tname, tlno);
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
	
	public ArrayList<Theater> selectTheaterByLoc(int tlno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			// sql select 설정
			String sql = "select * "
					   + "from theater "
					   + "where tlno = ?"
					   + "order by tname";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tlno);
			
			rs = pstmt.executeQuery();

			ArrayList<Theater> list = new ArrayList<>();

			while (rs.next()) {
				int tno = rs.getInt("tno"); // db에서 숫자를 얻어온다.
				String tname = rs.getString("tname");
				int tlno1 = rs.getInt("tlno");
				
				Theater dto = new Theater(tno, tname, tlno1);
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

	public int insertTheater(String tname, int tlno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "insert into theater (tno, tname, tlno) " 
					  + " values (theater1.nextval,?,?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, tname);
			pstmt.setInt(2, tlno); 

			int count = pstmt.executeUpdate();

			return count;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(null, pstmt, conn);
		}
		return 0;
	}
	
	public int undateTheaterBythNum(int tno, String tname) {

		try {

			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "	update theater "
						 + "set tname = ? " 
						 + "where tno = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tname);
			pstmt.setInt(2, tno); 
			 
			int count = pstmt.executeUpdate();
			
			if (count > 0) {
				System.out.println("데이터 변경 성공");
				return count;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(null,pstmt, conn);
		}
		
		return 0;
	}
	
	public int deleteTheaterBythNum(int tno) {

		try {

			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "	delete 	theater " + "where tno = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tno); // ? 입력될 정보를 설정

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
		
		TheaterDao td = new TheaterDao();
		
		ArrayList<Theater> list = td.selectTheater();
		ArrayList<Theater> list1 = td.selectTheaterByLoc(31001);
		
//		System.out.println(list.get(1).getTname());
		System.out.println(list1.get(1).getTname());
		
//		td.insertTheater("원", 31001);
	}
	
}
