package reserve;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TicketDao {
	
	final String driver = "oracle.jdbc.driver.OracleDriver";
	final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	final String id = "movie";
	final String pw = "1234";

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public ArrayList<Ticket> selectTicketByOdno(int odno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql =  "select * " 
						+ "from ticket " 
						+ "where odno = ? " 
						+ "order by odno, seno";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, odno);

			rs = pstmt.executeQuery();

			ArrayList<Ticket> list = new ArrayList<>();

			while (rs.next()) {
				int tkno = rs.getInt("tkno");
				int fno = rs.getInt("fno");
				String ftitle = rs.getString("ftitle");
				String runningtime = rs.getString("runningtime");
				int scrdate = rs.getInt("scrdate");
				String location = rs.getString("location");
				String tname = rs.getString("tname");
				String sname = rs.getString("sname");
				String sttime = rs.getString("sttime");
				String seatname = rs.getString("seatname");
				int stno = rs.getInt("stno");
				long seno = rs.getLong("seno");
				
				Ticket dto = new Ticket(tkno, fno,ftitle,runningtime, scrdate, location, 
								tname, sname,sttime, seatname, stno, seno, odno);
				
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
	
	public ArrayList<Ticket> selectTicketByStnoAndScrdate(int stno, int scrdate) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql =  "select * " 
						+ "from ticket " 
						+ "where stno = ? " 
						+ "and scrdate = ? "
						+ "order by seno";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, stno);
			pstmt.setInt(2, scrdate);

			rs = pstmt.executeQuery();

			ArrayList<Ticket> list = new ArrayList<>();

			while (rs.next()) {
				int tkno = rs.getInt("tkno");
				int fno = rs.getInt("fno");
				int scrdate1 = rs.getInt("scrdate");
				String location = rs.getString("location");
				String tname = rs.getString("tname");
				String sname = rs.getString("sname");
				int stno1 = rs.getInt("stno");
				long seno = rs.getLong("seno");
				int odno = rs.getInt("odno");

				Ticket dto = new Ticket(tkno, fno, scrdate1, location, tname, sname, stno1
										,seno, odno);
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
	
	public int countTicketSeatByStnoAndScrdate(int stno, int scrdate) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql =  "select count(*) " 
						+ "from ticket " 
						+ "where stno = ? " 
						+ "and scrdate = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, stno);
			pstmt.setInt(2, scrdate);

			rs = pstmt.executeQuery();

			int cnt = 0;
			
			while (rs.next()) {
				cnt = rs.getInt("count(*)");
			}
			return cnt;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
		return 0;
	}
	
	public ArrayList<Ticket> selectTicketByScrDate(int scrdate) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql =  "select * " 
						+ "from ticket "  
						+ "where scrdate = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, scrdate);

			rs = pstmt.executeQuery();

			ArrayList<Ticket> list = new ArrayList<>();

			while (rs.next()) {
				int tkno = rs.getInt("tkno");
				int fno = rs.getInt("fno");
				int scrdate1 = rs.getInt("scrdate");
				String location = rs.getString("location");
				String tname = rs.getString("tname");
				String sname = rs.getString("sname");
				int stno1 = rs.getInt("stno");
				long seno = rs.getLong("seno");
				int odno = rs.getInt("odno");

				Ticket dto = new Ticket(tkno, fno, scrdate1, location, tname, sname, stno1
										,seno, odno);
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

	public int insertTicket(int fno, int scrDate, String location, String tname, 
						String sname, int stno, long seno, int odno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "insert into ticket(tkno,fno,scrdate,location,tname,sname,stno,seno,odno) "
					+ " values (ticket1.nextval,?,?,?,?,?,?,?,?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, fno);
			pstmt.setInt(2, scrDate);
			pstmt.setString(3, location);
			pstmt.setString(4, tname);
			pstmt.setString(5, sname);
			pstmt.setInt(6, stno);
			pstmt.setLong(7, seno);
			pstmt.setInt(8, odno);

			int count = pstmt.executeUpdate();
		
			if (count > 0) System.out.println("ticket 입력성공");
			return count;

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			closeAll(null, pstmt, conn);
		}
		return 0;
	}
	
	public int insertTicket(Ticket ticket) {
		
		try {

			String sql = "insert into ticket(tkno,fno,scrdate,location,tname,sname,stno,seno,odno) "
					+ " values (ticket1.nextval,?,?,?,?,?,?,?,?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, ticket.getFno());
			pstmt.setInt(2, ticket.getScrDate());
			pstmt.setString(3, ticket.getLocation());
			pstmt.setString(4, ticket.getTname());
			pstmt.setString(5, ticket.getSname());
			pstmt.setInt(6, ticket.getStno());
			pstmt.setLong(7, ticket.getSeno());
			pstmt.setInt(8, ticket.getOdno());

			int count = pstmt.executeUpdate();
			
			return count;
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
		
	}
	
	public void insertTicketExceptOdno(int fno, int scrDate, String location, 
							String tname, String sname, int stno, long seno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "insert into ticket(tkno,fno,scrdate,location,tname,sname,stno,seno) "
					+ " values (ticket1.nextval,?,?,?,?,?,?,?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, fno);
			pstmt.setInt(2, scrDate);
			pstmt.setString(3, location);
			pstmt.setString(4, tname);
			pstmt.setString(5, sname);
			pstmt.setInt(6, stno);
			pstmt.setLong(7, seno);

			int count = pstmt.executeUpdate();
			System.out.println("tickCntExOd "+count);


		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			closeAll(null, pstmt, conn);
		}
	}
	
	public void deleteTicketByOdno(int odno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "delete ticket "
					  + " where odno = ? ";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, odno);
		
			int count = pstmt.executeUpdate();
			
			if (count > 0) System.out.println("delTicket Sucess"); 

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(null, pstmt, conn);
		}
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
		
		TicketDao td = new TicketDao();
		
		ArrayList<Ticket> tckList = new ArrayList<>();
		
		long start1 = System.currentTimeMillis();
		
		tckList = td.selectTicketByStnoAndScrdate(3400107, 20161017);
		
		long end1 = System.currentTimeMillis();
		
		System.out.println(tckList.size() +"," + (end1 - start1));
		
		
		long start = System.currentTimeMillis();
		
		int cnt = td.countTicketSeatByStnoAndScrdate(3400107, 20161017);
		
		long end = System.currentTimeMillis();
		
		System.out.println(cnt +"," + (end - start));
		
		
		
		
		
//		td.insertScrSeat(20001, 20160927, "서울", "강남", "1관", 3400002,  350000113);
		
//		for (Ticket a : tckList) {
//			System.out.println(a.getSeno());
//		}
	
//		td.insertScrSeat(20001,20160925,"서울","강남","1관",3400002,350000112);
	}

}
