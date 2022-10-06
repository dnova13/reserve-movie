package reserve;

import java.sql.*;
import java.util.*;

public class OrderDataDao {
	
	final String driver = "oracle.jdbc.driver.OracleDriver";
	final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	final String id = "movie";
	final String pw = "1234";

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public ArrayList<OrderData> selectOrderDataByID(String mid) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "select * " 
						+ "from orderdata " 
						+ "where mid = ? "
						+ "order by odno desc ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mid);

			rs = pstmt.executeQuery();

			ArrayList<OrderData> list = new ArrayList<>();

			while (rs.next()) {
				int odno = rs.getInt("odno");
				String mid1 = rs.getString("mid");
				String orderdate = rs.getString("orderdate");
				int peopleNum = rs.getInt("peoplenum");
				String paymentMethod = rs.getString("paymentmethod");
				int price = rs.getInt("price");

				OrderData dto = new OrderData(odno, mid1, orderdate, peopleNum, paymentMethod, price);
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
	
	public ArrayList<OrderData> selectOrderDataBefore10ByID(String mid) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "select * " 
						+ "from orderdata " 
						+ "where mid = ? "
						+ "and odno in (select odno "
									  + "from ticket "
									  + "where to_date(scrdate,'yyyymmdd') >= sysdate-11)"
						+ "order by odno desc ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mid);

			rs = pstmt.executeQuery();

			ArrayList<OrderData> list = new ArrayList<>();
						
			while (rs.next()) {

				int odno = rs.getInt("odno");
				String mid1 = rs.getString("mid");
				String orderdate = rs.getString("orderdate");
				int peopleNum = rs.getInt("peoplenum");
				String paymentMethod = rs.getString("paymentmethod");
				int price = rs.getInt("price");

				OrderData dto = new OrderData(odno, mid1, orderdate, peopleNum, paymentMethod, price);
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
	
	public ArrayList<OrderData> selectOrderDataBefore10ByID(String mid, int start, int end) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "select * " 
						+ "from orderdata " 
						+ "where mid = ? "
						+ "and odno in (select odno "
									  + "from ticket "
									  + "where to_date(scrdate,'yyyymmdd') >= sysdate-11)"
						+ "order by odno desc ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mid);

			rs = pstmt.executeQuery();

			ArrayList<OrderData> list = new ArrayList<>();
			
			int cnt = 0;
//			System.out.println("st " + start);
			
			while (rs.next()) {
				cnt++;
				
				if (cnt >= start && cnt <= end) {

					int odno = rs.getInt("odno");
					String mid1 = rs.getString("mid");
					String orderdate = rs.getString("orderdate");
					int peopleNum = rs.getInt("peoplenum");
					String paymentMethod = rs.getString("paymentmethod");
					int price = rs.getInt("price");

					OrderData dto = new OrderData(odno, mid1, orderdate, peopleNum, paymentMethod, price);
					list.add(dto);
				}
				else if (cnt > end) break;	
			}
			return list;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
		return null;
	}
	
	public int getOrderTotalNum(String mid) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "select count(*) " 
						+ "from orderdata " 
						+ "where mid = ? "
						+ "and odno in (select odno "
									  + "from ticket "
									  + "where to_date(scrdate,'yyyymmdd') >= sysdate-11)"
						+ "order by odno desc ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mid);

			rs = pstmt.executeQuery();
			
			int num = 0;
						
			while (rs.next()) {

				num = rs.getInt("count(*)");
			}
			
			return num;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
		return 0;
	}
	
	public OrderData selectOredrDataByOdno(int odno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "select * " 
						+ "from orderdata " 
						+ "where odno = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, odno);

			rs = pstmt.executeQuery();

			OrderData dto = null;

			while (rs.next()) {
				String mid1 = rs.getString("mid");
				String orderdate = rs.getString("orderdate");
				int peopleNum = rs.getInt("peoplenum");
				String paymentMethod = rs.getString("paymentmethod");
				int price = rs.getInt("price");

				dto = new OrderData(odno, mid1, orderdate, peopleNum, paymentMethod, price);
			}
			return dto;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
		return null;
	}
	
	public int insertOrderData (String mid, String orderDate, int peopleNum, 
								String paymentMethod, int price) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "insert into orderdata(odno,mid, orderdate, peoplenum, paymentmethod,price) " 
						+ "values(orderdata1.nextval,?,?,?,?,?)"; 

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mid);
			pstmt.setString(2, orderDate);
			pstmt.setInt(3, peopleNum);
			pstmt.setString(4, paymentMethod);
			pstmt.setInt(5, price);
			
			int count = pstmt.executeUpdate();		
			if (count > 0) System.out.println("ord 입력성공");
			
			return count;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(null, pstmt, conn);
		}
		return 0;
	}
	
	public boolean insertOrderAndTicket (OrderData order, Ticket ticket, ArrayList<ScreenSeat> seno) {

		PreparedStatement selPstmt = null;
		PreparedStatement tckPstmt = null;
			
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			conn.setAutoCommit(false);
			
			// 1. insert 주문정보
			String ordSql = "insert into orderdata(odno,mid, orderdate, peoplenum, paymentmethod,price) "
					+ "values(orderdata1.nextval,?,?,?,?,?)";

			pstmt = conn.prepareStatement(ordSql);
			pstmt.setString(1, order.getmID());
			pstmt.setString(2, order.getOrderDate());
			pstmt.setInt(3, order.getPeopleNum());
			pstmt.setString(4, order.getPaymentMethod());
			pstmt.setInt(5, order.getPrice());

			int orderCount = pstmt.executeUpdate();
			
			if (orderCount > 0) {
				
				//-----2. select 입력한 주문번호 검색
				System.out.println("ord 입력성공");
				
				String sql = "select orderdata1.currval " 
						   + "from dual "; 

				selPstmt = conn.prepareStatement(sql);

				rs = selPstmt.executeQuery();
				
				int odno = -1;

				while (rs.next()) {
					odno = rs.getInt("currval");
				}		
				System.out.println("odno " + odno);
				
				//3. insetTicket 티켓 정보 삽입
				int tckCount = 0;
				
				for (ScreenSeat a : seno) {
					
					String tckSql = "insert into ticket(tkno,fno,ftitle,runningtime,scrdate,"
									+ "location,tname,sname,sttime,seatname,stno,seno,odno) "
									+ " values (ticket1.nextval,?,?,?,?,?,?,?,?,?,?,?,?)";
					
					tckPstmt = conn.prepareStatement(tckSql);

					tckPstmt.setInt(1, ticket.getFno());
					tckPstmt.setString(2, ticket.getFtitle());
					tckPstmt.setString(3, ticket.getRunningtime());
					tckPstmt.setInt(4, ticket.getScrDate());
					tckPstmt.setString(5, ticket.getLocation());
					tckPstmt.setString(6, ticket.getTname());
					tckPstmt.setString(7, ticket.getSname());
					tckPstmt.setString(8, ticket.getSttime());
					tckPstmt.setString(9, a.getRowSeat() + a.getColSeat());
					tckPstmt.setInt(10, ticket.getStno());
					tckPstmt.setLong(11, a.getSeno());
					tckPstmt.setInt(12, odno);

					tckCount = tckPstmt.executeUpdate();
					
					if (tckCount == 0) {
						System.out.println("tckCount " + tckCount);
						throw new Exception();
					}
				}
				
				System.out.println("tckCount " + tckCount);
			}
			conn.commit();
			return true;
			
		} catch (Exception e) {
			try {
				System.out.println("롤백성공");
				conn.rollback();
			} catch (Exception e2) {}
			
			System.out.println("최종입력실패");
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (Exception e) {}
			
			closeAll(null, pstmt, null);			
			closeAll(rs, selPstmt, null);
			closeAll(null, tckPstmt, conn);
		}
		return false;
	}
	
	public void deleteOrderData(int odno) {
		
		try {
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			
			String sql = 
					"	delete 	orderdata "
					+ "	where	odno = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, odno); // ? 입력될 정보를 설정
			
			int count = pstmt.executeUpdate();
			
			if (count > 0) System.out.println("delOrdData Sucess"); 
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(null,pstmt, conn);
		}		
	}
	
	public int deleteOrderAndTicket(int odno) {
		
		PreparedStatement orPstmt = null;
		
		try {
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
//			conn.setAutoCommit(false);
						
			String sql1 = "	delete 	orderdata " + "	where	odno = ?";

			orPstmt = conn.prepareStatement(sql1);
			orPstmt.setInt(1, odno); // ? 입력될 정보를 설정
			
			int count = orPstmt.executeUpdate();
			
			if (count > 0) {
				System.out.println("delOrdData Sucess");
				conn.commit();
			}
			
			return count;
			
		} catch (Exception e) {
			System.out.println("최종입력실패");
			e.printStackTrace();
		} finally {						
			closeAll(null, orPstmt, conn);
		}		
		return 0;
	}
	
	
	public int deleteOrderAndTicketTemp(int odno) {
		
		PreparedStatement orPstmt = null;
		PreparedStatement tkPstmt = null;
		
		try {
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			conn.setAutoCommit(false);
			
			String sql = 
					"	delete 	ticket "
					+ "	where	odno = ?";
			
			tkPstmt = conn.prepareStatement(sql);
			tkPstmt.setInt(1, odno); // ? 입력될 정보를 설정
			
			int cnt = tkPstmt.executeUpdate();
			
			if (cnt > 0) {

				String sql1 = "	delete 	orderdata " 
							+ "	where	odno = ?";

				orPstmt = conn.prepareStatement(sql1);
				orPstmt.setInt(1, odno); // ? 입력될 정보를 설정
			}			
			
			int count = orPstmt.executeUpdate();
			
			if (count > 0) {
				System.out.println("delOrdData Sucess");
				conn.commit();
			}
			
			return count;
			
		} catch (Exception e) {
			try {
				System.out.println("롤백성공");
				conn.rollback();
			} catch (Exception e2) {}
			System.out.println("최종입력실패");
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (Exception e) {}
			
			closeAll(null, tkPstmt, null);			
			closeAll(null, orPstmt, conn);
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
		OrderDataDao od = new OrderDataDao();
		
//		ArrayList<OrderData> ordDataList = od.selectOrderDataBefore10ByID("d1",3,6);
		
//		System.out.println(ordDataList);
		
//		od.deleteOrderAndTicket(52);
	}
	
}
