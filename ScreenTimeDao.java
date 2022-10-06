package reserve;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ScreenTimeDao {
	
	final String driver = "oracle.jdbc.driver.OracleDriver";
	final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	final String id = "movie";
	final String pw = "1234";

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	public ArrayList<ScreenTime> selectScrTimeByScreen(int sno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "select * " 
					   + "from screentime " 
					   + "where sno = ?" 
					   + "order by sttime";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sno);

			rs = pstmt.executeQuery();

			ArrayList<ScreenTime> list = new ArrayList<>();

			while (rs.next()) {
				int stno = rs.getInt("stno");
				String sttime = rs.getString("sttime");
				int sno1 = rs.getInt("sno");

				ScreenTime dto = new ScreenTime(stno, sttime, sno1);
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
	
	public int getStnoByScrTimeAndSno(String scrTime, int sno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "select stno " 
					   + "from screentime " 
					   + "where sno = ? " 
					   + "and sttime = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sno);
			pstmt.setString(2, scrTime);

			rs = pstmt.executeQuery();

			int stno = 0;

			if (rs.next()) {
				stno = rs.getInt("stno");

			}
			return stno;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
		return 0;
	}
	
	public String getScrTime(int stno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "select sttime " 
					   + "from screentime " 
					   + "where stno = ? "; 

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, stno);


			rs = pstmt.executeQuery();

			String scrtime = null;

			if (rs.next()) {
				scrtime = rs.getString("sttime");

			}
			return scrtime;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
		return null;
	}
	
	public ArrayList<ScreenTime> getScrTimesInScreen(String sname, int tno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "select * " 
					   + "from screentime " 
					   + "where sno in ( select sno "
					   					+ "from screen "
					   					+ "where sname = ?  "
					   					+ "and tno = ?) "
					   					+ "order by sttime"; 

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sname);
			pstmt.setInt(2, tno);

			rs = pstmt.executeQuery();

			ArrayList<ScreenTime> list = new ArrayList<>();

			while (rs.next()) {
				
				int stno = rs.getInt("stno");
				String sttime = rs.getString("sttime");
				int sno = rs.getInt("sno");
				
				ScreenTime st = new ScreenTime(stno, sttime, sno);
				list.add(st);
			}
			return list;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
		return null;
	}
	
	public boolean checkDupScrTime(ArrayList<Integer> snoList, String sttime) {
		
		String addSql = " or sno=?";
			
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "select sttime " 
					   + "from screentime " 
					   + "where sttime = ? " 
					   + "and sno in (select sno "  
								   + "from screentime "
						           + "where sno =? "; 
			
			for (int i = 1; i < snoList.size(); i++) {
				sql += addSql;				
			}
			
			sql += ")";
			
			System.out.println(sql);
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sttime);
			pstmt.setInt(2, snoList.get(0));
			
			for (int i = 1; i < snoList.size(); i++) {
				pstmt.setInt(i+2, snoList.get(i));
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				return true;

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
		return false;
	}
	
	public int checkScrTimeCount(ArrayList<Integer> snoList) {
		
		String addSql = " or sno=?";
			
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "select sttime " 
					   + "from screentime " 
					   + "where sno in (select sno "  
								   + "from screentime "
						           + "where sno =? "; 
			
			for (int i = 1; i < snoList.size(); i++) {
				sql += addSql;				
			}
			
			sql += ")";
			
			System.out.println(sql);
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, snoList.get(0));
			
			for (int i = 1; i < snoList.size(); i++) {
				pstmt.setInt(i+1, snoList.get(i));
			}

			rs = pstmt.executeQuery();
			
			int count = 0;
			
			while (rs.next()) {
				count++;
			}
			
			return count;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
		return 0;
	}
	
	public int insertScrTime(String sttime, int sno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "insert into screentime (stno,sttime, sno) " 
					  + " values (screentime1.nextval,?,?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, sttime);
			pstmt.setInt(2, sno);

			int count = pstmt.executeUpdate();

			return count;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(null, pstmt, conn);
		}
		return 0;
	}
	
	public int updateScreenTime(String sttime, int stno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "update screentime " 
					  + " set sttime = ? "
					  + " where stno = ? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, sttime);
			pstmt.setInt(2, stno);

			int count = pstmt.executeUpdate();
			System.out.println("count " + count);
			return count;

		} catch (Exception e) {
			System.out.println("데이터 입력 실패");
			e.printStackTrace();
		} finally {
			closeAll(null, pstmt, conn);
		}
		return 0;
	}
	
	public int DeleteScreenTime(int stno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "delete screentime " 
					  + " where stno = ? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, stno);

			int count = pstmt.executeUpdate();
			System.out.println("count " + count);
			return count;

		} catch (Exception e) {
			System.out.println("데이터 입력 실패");
			e.printStackTrace();
		} finally {
			closeAll(null, pstmt, conn);
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
		
		ScreenTimeDao scrTimeDao = new ScreenTimeDao();
		
		System.out.println(scrTimeDao.getScrTimesInScreen("1관", 32002));
		
		ArrayList<Integer> scrNumList = new ArrayList<>();
		
		scrNumList.add(2);
		scrNumList.add(8);

		System.out.println(scrTimeDao.checkScrTimeCount(scrNumList));
		
//		
		
//		System.out.println(scrTimeDao.checkDupScrTime(scrNumList, "13:00"));
//		
//		String addSql = " or sno=?";
//
//		String sql = "select sttime " + "from screentime " + "where sttime = ? " + "and sno in (select sno "
//				+ "from screentime " + "where sno =? ";
//
//		for (int i = 1; i < 3; i++) {
//			sql += addSql;
//			
//			if (i == 2) 
//				sql += ")";
//		}
		
		
		
//		scrTimeDao.insertScrTime("10:00", 330001);
//		System.out.println(scrTimeDao.selectSeno("10:00", 330001));
		
	}

	
	
}
