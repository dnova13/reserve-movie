package reserve;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import oracle.net.aso.s;

public class ScreenDao {
	final String driver = "oracle.jdbc.driver.OracleDriver";
	final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	final String id = "movie";
	final String pw = "1234";

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	public ArrayList<Screen> selectScreenByTheater(int tno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "select * "
					   + "from screen "
					   + "where tno = ?"
					   + "order by to_number(trim('관' from sname))";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tno);
			
			rs = pstmt.executeQuery();

			ArrayList<Screen> list = new ArrayList<>();

			while (rs.next()) {
				int sno = rs.getInt("sno"); 
				String sname = rs.getString("sname");
				int totalseat = rs.getInt("totalseat");
				int totalcolseat = rs.getInt("totalcolseat");
				int period = rs.getInt("period");
				int fno = rs.getInt("fno");
				int tno1 = rs.getInt("tno");
								
				Screen dto = new Screen(sno, sname, totalseat, totalcolseat, period, fno, tno1);
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
	
	public ArrayList<Screen> selectScrByTheaterAndFilm(int fno, int tno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "select * "
					   + "from screen "
					   + "where fno = ? "
					   + "and tno = ? "
					   + "order by to_number(trim('관' from sname))";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, fno);
			pstmt.setInt(2, tno);
			
			rs = pstmt.executeQuery();

			ArrayList<Screen> list = new ArrayList<>();

			while (rs.next()) {
				int sno = rs.getInt("sno"); 
				String sname = rs.getString("sname");
				int totalseat = rs.getInt("totalseat");
				int totalcolseat = rs.getInt("totalcolseat");
				int period = rs.getInt("period");
				int fno1 = rs.getInt("fno");
				int tno1 = rs.getInt("tno");
								
				Screen dto = new Screen(sno, sname, totalseat, totalcolseat, period, fno1, tno1);
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
	
	public ArrayList<Screen> selectScreenBySnameAndTno (String sname, int tno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "select * "
					   + "from screen "
					   + "where tno = ? "
					   + "and sname = ? "
					   + "order by fno ";	

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tno);
			pstmt.setString(2, sname);
			
			rs = pstmt.executeQuery();
			
			ArrayList<Screen> list = new ArrayList<>();
			
			while (rs.next()) {
			
				int sno = rs.getInt("sno"); 
				int totalseat = rs.getInt("totalseat");
				int totalcolseat = rs.getInt("totalcolseat");
				int period = rs.getInt("period");
				int fno = rs.getInt("fno");
								
				Screen scr = new Screen(sno, sname, totalseat, totalcolseat, period, fno, tno);
				list.add(scr);
			}
			
			return list;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
		return null;
	}
	
	public Screen checkDupScrNameAndGetScreenBySnameAndTno (String sname, int tno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "select * "
					   + "from screen "
					   + "where tno = ? "
					   + "and sname = ? "
					   + "order by sno ";	

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tno);
			pstmt.setString(2, sname);
			
			rs = pstmt.executeQuery();
			
			Screen scr = null;
			
			if (rs.next()) {
			
				int sno = rs.getInt("sno"); 
				int totalseat = rs.getInt("totalseat");
				int totalcolseat = rs.getInt("totalcolseat");
				int period = rs.getInt("period");
				int fno = rs.getInt("fno");
								
				scr = new Screen(sno, sname, totalseat, totalcolseat, period, fno, tno);
			}
			
			return scr;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
		return null;
	}
	
	public Screen selectScreenBySname_Tno_fno (String sname, int tno, int fno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "select * "
					   + "from screen "
					   + "where tno = ? "
					   + "and sname = ? "
					   + "and nvl(fno,0) = ? "
					   + "order by sno";
						
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tno);
			pstmt.setString(2, sname);
			pstmt.setInt(3, fno);
			
			rs = pstmt.executeQuery();
			
			Screen scr = null;
			
			if (rs.next()) {
			
				int sno = rs.getInt("sno"); 
				int totalseat = rs.getInt("totalseat");
				int totalcolseat = rs.getInt("totalcolseat");
				int period = rs.getInt("period");
								
				scr = new Screen(sno, sname, totalseat, totalcolseat, period, fno, tno);
				
			}
			
			return scr;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
		return null;
	}
	
	public ArrayList<Screen> selectFilmNumAndPeriod () {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "select distinct(fno), period "
					   + " from screen "
					   + " order by fno";

			pstmt = conn.prepareStatement(sql);
	
			rs = pstmt.executeQuery();
			
			ArrayList<Screen> list = new ArrayList<>();
			
			while (rs.next()) {
				
				int fno1 = rs.getInt("fno");
				int period = rs.getInt("period");
								
				Screen dto = new Screen(period, fno1);
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
	
	public ArrayList<Screen> selectFilmNumAndPeriodInTheather(int tno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "select distinct(fno), period "
					   + " from screen "
					   + " where tno = ?"
					   + " order by fno";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tno);
	
			rs = pstmt.executeQuery();
			
			ArrayList<Screen> list = new ArrayList<>();
			
			while (rs.next()) {
				
				int fno1 = rs.getInt("fno");
				int period = rs.getInt("period");
								
				Screen dto = new Screen(period, fno1);
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
	
	public ArrayList<String> getScrNameNotDup (int tno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "select distinct(sname) "
					   + "from screen "
					   + "where tno = ? "
					   + "order by to_number(trim('관' from sname)) ";	

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tno);
			
			rs = pstmt.executeQuery();
			
			ArrayList<String> list = new ArrayList<>();
			
			while (rs.next()) {
				String sname = rs.getString("sname");
				list.add(sname);
			}
			
			return list;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
		return null;
	}
	
	public boolean checkDupScrNameAndFilmNum (int tno, int fno, String sname) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "select * "
					   + "from screen "
					   + "where tno = ? "
					   + "and fno = ? "
					   + "and sname = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tno);
			pstmt.setInt(2, fno);
			pstmt.setString(3, sname);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
		return false;
	}
	
	public Screen selectScreenByScrNum(int sno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "select * "
					   + "from screen "
					   + "where sno = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sno);
			
			rs = pstmt.executeQuery();

			Screen dto = null; 
			
			while (rs.next()) {
				
				String sname = rs.getString("sname");
				int totalseat = rs.getInt("totalseat");
				int totalcolseat = rs.getInt("totalcolseat");
				int period = rs.getInt("period");
				int fno = rs.getInt("fno");
				int tno1 = rs.getInt("tno");
								
				dto = new Screen(sno, sname, totalseat, totalcolseat, period, fno, tno1);
			}
			return dto;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
		return null;
	}

	public int insertScreen(String sname, int totalseat, int totalcolseat, int fno, int tno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "insert into screen (sno,sname, totalseat, totalcolseat, fno, tno) " 
					  + " values (screen1.nextval,?,?,?,?,?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, sname);
			pstmt.setInt(2, totalseat);
			pstmt.setInt(3, totalcolseat);
			pstmt.setInt(4, fno);
			pstmt.setInt(5, tno);

			int count = pstmt.executeUpdate();

			return count;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(null, pstmt, conn);
		}
		return 0;
	}
	
	public int updateScreenPeriod(int period, int fno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "update screen " 
					  + " set period = ? "
					  + " where fno = ? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, period);
			pstmt.setInt(2, fno);

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
	
	public int updateScreenPeriodByTno(int period, int fno,  int tno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "update screen " 
					  + " set period = ? "
					  + " where fno = ? "
					  + " and tno = ? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, period);
			pstmt.setInt(2, fno);
			pstmt.setInt(3, tno);

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
	
	public int updateScreenFno(int modifyfno, int fno,  int sno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "update screen " 
					  + " set fno = ? "
					  + " where nvl(fno,0) = ? "
					  + " and sno = ? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, modifyfno);
			pstmt.setInt(2, fno);
			pstmt.setInt(3, sno);

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
			
	public int DeleteScreen(int sno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "delete screen " 
					  + " where sno = ? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, sno);

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
		
		ScreenDao sd = new ScreenDao();
		
//		System.out.println(sd.updateScreenPeriod(20161111, 20001, 330001));
		
//		System.out.println(sd.selectFilmNumAndPeriod());
		
	
		
//		System.out.println(sd.checkDupScrName(32002, 20004, "7관"));
//		
//		ArrayList<Screen> srlist = new ArrayList<>();
	
//		srlist = sd.selectScrByTheaterAndFilm(20002,32001);
//		
		for (Screen a : sd.selectFilmNumAndPeriod()) {
			System.out.println(a.getPeriod());
		}
		
//		System.out.println(sd.insertScreen("dsfs", 100, 10, 20001, 32006));
//		System.out.println(srlist.get(1).getTno());
	}
}
