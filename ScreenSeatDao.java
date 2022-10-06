package reserve;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ScreenSeatDao {
	
	final String driver = "oracle.jdbc.driver.OracleDriver";
	final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	final String id = "movie";
	final String pw = "1234";

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	public ArrayList<ScreenSeat> selectScrSeatByScrTime(int stno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "select * " 
					   + "from screenseat " 
					   + "where stno = ? " 
					   + "order by seatnum";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, stno);

			rs = pstmt.executeQuery();

			ArrayList<ScreenSeat> list = new ArrayList<>();

			while (rs.next()) {
				long seno = rs.getLong("seno");
				int seatNum = rs.getInt("seatnum");
				String rowSeat = rs.getString("rowseat");
				int colSeat = rs.getInt("colseat");
				boolean purchased = Boolean.parseBoolean(rs.getString("purchased"));

				ScreenSeat dto = 
						new ScreenSeat(seno, seatNum, rowSeat, colSeat, purchased, stno);
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
	
	public ArrayList<ScreenSeat> selectPuchasedSeat(int stno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "select * " 
					   + "from screenseat " 
					   + "where stno = ? "
					   + "and purchased = ? "
					   + "order by seatnum";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, stno);
			pstmt.setString(2, "true");

			rs = pstmt.executeQuery();

			ArrayList<ScreenSeat> list = new ArrayList<>();

			while (rs.next()) {
				long seno = rs.getLong("seno");
				int seatNum = rs.getInt("seatnum");
				String rowSeat = rs.getString("rowseat");
				int colSeat = rs.getInt("colseat");
				boolean purchased = Boolean.parseBoolean(rs.getString("purchased"));

				ScreenSeat dto = 
						new ScreenSeat(seno, seatNum, rowSeat, colSeat, purchased, stno);
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
	
	public int getTotalColSeatFromStno(int stno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "select max(colseat) " 
					   + "from screenseat " 
					   + "where stno = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, stno);
			rs = pstmt.executeQuery();
			
			int totalCol = 0;
			
			while (rs.next()) {
				totalCol = rs.getInt("max(colseat)");
			}
			
			return totalCol;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
		return 0;
	}
	
	public int getSeatNum(long seno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "select seatnum " 
					   + "from screenseat " 
					   + "where seno = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, seno);


			rs = pstmt.executeQuery();

			int seatnum = 0;
	
			while (rs.next()) {
				seatnum = rs.getInt("seatnum");
			}
			
			return seatnum;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
		return 0;
	}
	
	public String getSeatName(long seno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "select rowseat, colseat " 
					   + "from screenseat " 
					   + "where seno = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, seno);


			rs = pstmt.executeQuery();

			String rowSeat = null;
			int colSeat = 0;
	
			while (rs.next()) {
				rowSeat = rs.getString("rowseat");
				colSeat = rs.getInt("colseat");
			}
			
			return  "[" + rowSeat + colSeat+ "]";

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
		return null;
	}
	
	public int insertScrSeat(int seatnum, String rowSeat, int colSeat, int stno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "insert into screenseat(seno,seatnum, rowseat, colseat, purchased, stno) " 
					  + " values (screenseat1.nextval,?,?,?,?,?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, seatnum);
			pstmt.setString(2, rowSeat);
			pstmt.setInt(3, colSeat);
			pstmt.setString(4, "false");
			pstmt.setInt(5, stno);

			int count = pstmt.executeUpdate();
			
			System.out.println("cnt "+count);
			
			return count;

		} catch (Exception e) {
						
			e.printStackTrace();
		} finally {
			closeAll(null, pstmt, conn);
		}
		return 0;
	}
	
	public boolean insertAllSeat(int totalSeat, int colSeatLength, int stno) {

		String rowSeat[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
				"S", "T", "U", "W", "V", "X", "Y", "Z" };

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			conn.setAutoCommit(false);

			for (int i = 0; i < totalSeat; i++) {

				String sql = "insert into screenseat(seno,seatnum, rowseat, colseat, purchased, stno) "
						+ " values (screenseat1.nextval,?,?,?,?,?)";

				pstmt = conn.prepareStatement(sql);

				pstmt.setInt(1, i + 1);
				pstmt.setString(2, rowSeat[i / colSeatLength]);
				pstmt.setInt(3, (i % colSeatLength) + 1);
				pstmt.setString(4, "false");
				pstmt.setInt(5, stno);

				int count = pstmt.executeUpdate();

				if (count == 0) {
					throw new Exception();
				}
			}
			
			conn.commit();
			return true;

		} catch (Exception e) {
			try {
				System.out.println("롤백성공");
				conn.rollback();
			} catch (Exception e2) {}
			
			e.printStackTrace();
		} finally {
			try { 
				conn.setAutoCommit(true); 
			} catch (Exception e) {}
			
			closeAll(null, pstmt, conn);
		}
		return false;
	}
	
	public boolean insertScrTimeAndAllSeat(String scrTime, int sno, int totalSeat, int colSeatLength) {
		
		PreparedStatement selPstmt = null;
		PreparedStatement seatPstmt = null;
		
		String rowSeat[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
				"S", "T", "U", "W", "V", "X", "Y", "Z" };
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			conn.setAutoCommit(false);
			
			String sql1 = "insert into screentime (stno,sttime, sno) " 
					    + " values (screentime1.nextval,?,?)";

			pstmt = conn.prepareStatement(sql1);

			pstmt.setString(1, scrTime);
			pstmt.setInt(2, sno);

			int stTimeCount = pstmt.executeUpdate();

			if (stTimeCount > 0) {

				// 2. selcet 입력한 상영시간 번호 검색
				System.out.println("stTime 입력성공");

				String sql2 = "select screentime1.currval " 
							+ "from dual ";
				selPstmt = conn.prepareStatement(sql2);

				rs = selPstmt.executeQuery();

				int stno = 0;

				if (rs.next()) {
					stno = rs.getInt("currval");
				}
				
				System.out.println("stno " + stno);
				
				// 3. screenseat 정보 삽입
				for (int i = 0; i < totalSeat; i++) {
					
					String sql3 = "insert into screenseat(seno,seatnum, rowseat, colseat, purchased, stno) "
							+ " values (screenseat1.nextval,?,?,?,?,?)";

					seatPstmt = conn.prepareStatement(sql3);

					seatPstmt.setInt(1, i + 1);
					seatPstmt.setString(2, rowSeat[i / colSeatLength]);
					seatPstmt.setInt(3, (i % colSeatLength) + 1);
					seatPstmt.setString(4, "false");
					seatPstmt.setInt(5, stno);

					int seatCount = seatPstmt.executeUpdate();
										
					if (seatCount == 0) {
						System.out.println("seatCount " + seatCount);
						throw new Exception();
					}
				}
			}

			conn.commit();
			return true;

		} catch (Exception e) {
			try {
				System.out.println("롤백성공");
				conn.rollback();
			} catch (Exception e2) {
			}
			System.out.println("입력실패");
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (Exception e) {
			}

			closeAll(null, seatPstmt, null);
			closeAll(rs, selPstmt, null);
			closeAll(null, pstmt, conn);
		}
		return false;
	}

	public int updatePurchasedSeatByScrTime(long seno, int stno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "update screenseat "
					   + " set purchased = ? "
					   + " where stno = ? "
					   + " and seno = ? ";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "true");
			pstmt.setInt(2, stno);
			pstmt.setLong(3, seno);

			return pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			closeAll(null, pstmt, conn);
		}
		return 0;
	}
	
	public int updatePurchasedSeat(long seno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "update screenseat "
					   + " set purchased = ? "
					   + " where seno = ? ";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "true");
			pstmt.setLong(2, seno);

			int count = pstmt.executeUpdate();

			return count;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(null, pstmt, conn);
		}
		return 0;
	}
		
	public int updateCancelSeat(int seatnum, int stno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "update screenseat "
					   + " set purchased = ? "
					   + " where stno = ? "
					   + " and seno = ? ";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "false");
			pstmt.setInt(2, stno);
			pstmt.setInt(3, seatnum);

			int count = pstmt.executeUpdate();

			return count;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(null, pstmt, conn);
		}
		return 0;
	}
	
	public int resetSeatByStno(int stno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "update screenseat "
					   + "set purchased = 'false' "
					   + "where stno = ? "
					   + "and purchased = 'true' ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, stno);
			
			int count = pstmt.executeUpdate();		
			if (count > 0) System.out.println("resetSeat 입력 성공");
			
			return 1;
			
		} catch (Exception e) {
			System.out.println("입력실패");
			e.printStackTrace();
		} finally {
			closeAll(null, pstmt, conn);
		}
		return 0;
	}
	
	public int deleteScrSeat(int stno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "delete screenseat "
					  + " where stno = ? ";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, stno);
		
			int count = pstmt.executeUpdate();

			return count;

		} catch (Exception e) {
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
		
		ScreenSeatDao scrSeatDao = new ScreenSeatDao();
		
		System.out.println(scrSeatDao.resetSeatByStno(3400001));
		
//		System.out.println(scrSeatDao.getTotalColSeatFromStno(3400064));
		
//		System.out.println(scrSeatDao.insertAllSeat(60, 10, 3400002));
		

	}
	
}
