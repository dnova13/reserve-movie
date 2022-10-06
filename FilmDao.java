package reserve;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class FilmDao { // db data access object : 데이터접속 객체 형성
	// driver, url, id, pw
	final String driver = "oracle.jdbc.driver.OracleDriver";
	final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	final String id = "movie";
	final String pw = "1234";

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public ArrayList<Film> selectFilm() { // db에 입력된 여기로 모든 데이터를 반환한다.

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			// sql select 설정
			String sql = "select * "
					   + "from film "
					   + "order by filmorder";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			ArrayList<Film> list = new ArrayList<>();

			while (rs.next()) {
				int fno = rs.getInt("fno"); // db에서 숫자를 얻어온다.
				String ftitle = rs.getString("ftitle");
				String director = rs.getString("director");
				int releasedate = rs.getInt("releasedate");
				String runningtime = rs.getString("runningtime");
				int filmrate = rs.getInt("filmrate");
				int filmorder = rs.getInt("filmorder");
				int paycount = rs.getInt("paycount");

				Film dto = new Film(fno, ftitle, director, releasedate, runningtime, filmrate, filmorder,paycount);
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
	
	public ArrayList<Film> selectFilmUnti(int untilOrder) { // db에 입력된 여기로 모든 데이터를 반환한다.

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			// sql select 설정
			String sql = "select * "
					   + "from film "
					   + "where filmorder < ? "
					   + "order by filmorder";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, untilOrder + 1);
			rs = pstmt.executeQuery();

			ArrayList<Film> list = new ArrayList<>();

			while (rs.next()) {
				int fno = rs.getInt("fno"); // db에서 숫자를 얻어온다.
				String ftitle = rs.getString("ftitle");
				String director = rs.getString("director");
				int releasedate = rs.getInt("releasedate");
				String runningtime = rs.getString("runningtime");
				int filmrate = rs.getInt("filmrate");
				int filmorder = rs.getInt("filmorder");
				int paycount = rs.getInt("paycount");

				Film dto = new Film(fno, ftitle, director, releasedate, runningtime,
						filmrate, filmorder,paycount);

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
	
	public ArrayList<Film> selectFilmfromReleseDateBefore5Days() { // db에 입력된 여기로 모든 데이터를 반환한다.

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			// sql select 설정
			String sql = "select * "
					   + "from film "
					   + "where to_date(RELEASEDATE,'yyyymmdd') < sysdate+5 "
					   + "order by filmorder";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			ArrayList<Film> list = new ArrayList<>();

			while (rs.next()) {
				int fno = rs.getInt("fno"); // db에서 숫자를 얻어온다.
				String ftitle = rs.getString("ftitle");
				String director = rs.getString("director");
				int releasedate = rs.getInt("releasedate");
				String runningtime = rs.getString("runningtime");
				int filmrate = rs.getInt("filmrate");
				int filmorder = rs.getInt("filmorder");
				int paycount = rs.getInt("paycount");

				Film dto = new Film(fno, ftitle, director, releasedate, runningtime,
						filmrate, filmorder,paycount);

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
		
	public ArrayList<Film> selectFilmUntilOrderByFno() { // db에 입력된 여기로 모든 데이터를
															// 반환한다.

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			// sql select 설정
			String sql = "select * " + "from film " + "where filmorder < 16 " + "order by fno";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			ArrayList<Film> list = new ArrayList<>();

			while (rs.next()) {
				int fno = rs.getInt("fno"); // db에서 숫자를 얻어온다.
				String ftitle = rs.getString("ftitle");
				String director = rs.getString("director");
				int releasedate = rs.getInt("releasedate");
				String runningtime = rs.getString("runningtime");
				int filmrate = rs.getInt("filmrate");
				int filmorder = rs.getInt("filmorder");
				int paycount = rs.getInt("paycount");

				Film dto = new Film(fno, ftitle, director, releasedate, runningtime, filmrate, filmorder, paycount);

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

	public ArrayList<Film> selectFilmOrderByFTitleAsc() { // db에 입력된 여기로 모든 데이터를 반환한다.

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			// sql select 설정
			String sql = "select * "
					   + "from film "
					   + "order by ftitle,nvl(paycount,0) desc,releasedate desc";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			ArrayList<Film> list = new ArrayList<>();

			while (rs.next()) {
				int fno = rs.getInt("fno"); // db에서 숫자를 얻어온다.
				String ftitle = rs.getString("ftitle");
				String director = rs.getString("director");
				int releasedate = rs.getInt("releasedate");
				String runningtime = rs.getString("runningtime");
				int filmrate = rs.getInt("filmrate");
				int filmorder = rs.getInt("filmorder");
				int paycount = rs.getInt("paycount");

				Film dto = new Film(fno, ftitle, director, releasedate, runningtime, filmrate, filmorder,paycount);
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
	
	public ArrayList<Film> selectFilmOrderByFTitleDesc() { // db에 입력된 여기로 모든 데이터를 반환한다.

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			// sql select 설정
			String sql = "select * "
					   + "from film "
					   + "order by ftitle desc,nvl(paycount,0) desc,releasedate desc";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			ArrayList<Film> list = new ArrayList<>();

			while (rs.next()) {
				int fno = rs.getInt("fno"); // db에서 숫자를 얻어온다.
				String ftitle = rs.getString("ftitle");
				String director = rs.getString("director");
				int releasedate = rs.getInt("releasedate");
				String runningtime = rs.getString("runningtime");
				int filmrate = rs.getInt("filmrate");
				int filmorder = rs.getInt("filmorder");
				int paycount = rs.getInt("paycount");

				Film dto = new Film(fno, ftitle, director, releasedate, runningtime, filmrate, filmorder,paycount);
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
	
	public ArrayList<Film> selectFilmOrderByPaymentDesc() { // db에 입력된 여기로 모든 데이터를 반환한다.

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			// sql select 설정
			String sql = "select * "
					   + "from film "
					   + "order by nvl(paycount,0) desc,releasedate desc,ftitle";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			ArrayList<Film> list = new ArrayList<>();

			while (rs.next()) {
				int fno = rs.getInt("fno"); // db에서 숫자를 얻어온다.
				String ftitle = rs.getString("ftitle");
				String director = rs.getString("director");
				int releasedate = rs.getInt("releasedate");
				String runningtime = rs.getString("runningtime");
				int filmrate = rs.getInt("filmrate");
				int filmorder = rs.getInt("filmorder");
				int paycount = rs.getInt("paycount");

				Film dto = new Film(fno, ftitle, director, releasedate, runningtime, filmrate, filmorder,paycount);
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
	
	public ArrayList<Film> selectFilmOrderByPaymentAsc() { // db에 입력된 여기로 모든 데이터를 반환한다.

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			// sql select 설정
			String sql = "select * "
					   + "from film "
					   + "order by nvl(paycount,0),releasedate,ftitle";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			ArrayList<Film> list = new ArrayList<>();

			while (rs.next()) {
				int fno = rs.getInt("fno"); // db에서 숫자를 얻어온다.
				String ftitle = rs.getString("ftitle");
				String director = rs.getString("director");
				int releasedate = rs.getInt("releasedate");
				String runningtime = rs.getString("runningtime");
				int filmrate = rs.getInt("filmrate");
				int filmorder = rs.getInt("filmorder");
				int paycount = rs.getInt("paycount");

				Film dto = new Film(fno, ftitle, director, releasedate, runningtime, filmrate, filmorder,paycount);
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
	
	public ArrayList<Film> selectFilmOrderByReleaseDateAsc() { // db에 입력된 여기로 모든 데이터를 반환한다.

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			// sql select 설정
			String sql = "select * "
					   + "from film "
					   + "order by releasedate, nvl(paycount,0), ftitle";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			ArrayList<Film> list = new ArrayList<>();

			while (rs.next()) {
				int fno = rs.getInt("fno"); // db에서 숫자를 얻어온다.
				String ftitle = rs.getString("ftitle");
				String director = rs.getString("director");
				int releasedate = rs.getInt("releasedate");
				String runningtime = rs.getString("runningtime");
				int filmrate = rs.getInt("filmrate");
				int filmorder = rs.getInt("filmorder");
				int paycount = rs.getInt("paycount");

				Film dto = new Film(fno, ftitle, director, releasedate, runningtime,
						filmrate, filmorder,paycount);


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
	
	public ArrayList<Film> selectFilmOrderByReleaseDateDesc() { // db에 입력된 여기로 모든 데이터를 반환한다.

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			// sql select 설정
			String sql = "select * "
					   + "from film "
					   + "order by releasedate desc, nvl(paycount,0) desc, ftitle";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			ArrayList<Film> list = new ArrayList<>();

			while (rs.next()) {
				int fno = rs.getInt("fno"); // db에서 숫자를 얻어온다.
				String ftitle = rs.getString("ftitle");
				String director = rs.getString("director");
				int releasedate = rs.getInt("releasedate");
				String runningtime = rs.getString("runningtime");
				int filmrate = rs.getInt("filmrate");
				int filmorder = rs.getInt("filmorder");
				int paycount = rs.getInt("paycount");

				Film dto = new Film(fno, ftitle, director, releasedate, runningtime,
						filmrate, filmorder,paycount);


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

	public Film selectFilmByFno(int fno) { 

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			// sql select 설정
			String sql =  "select * "
						+ "from film "
						+ "where fno = ? ";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, fno);
			
			rs = pstmt.executeQuery();

			Film dto = null;

			while (rs.next()) {
				String ftitle = rs.getString("ftitle");
				String director = rs.getString("director");
				int releasedate = rs.getInt("releasedate");
				String runningtime = rs.getString("runningtime");
				int filmrate = rs.getInt("filmrate");
				int filmorder = rs.getInt("filmorder");
				int paycount = rs.getInt("paycount");

				dto = new Film(fno, ftitle, director, releasedate, runningtime,
						filmrate, filmorder,paycount);

			}

			return dto;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}

		return null;
	}
	
	public String getFilmTitle(int fno) { 

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			// sql select 설정
			String sql =  "select ftitle "
						+ "from film "
						+ "where fno = ? ";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, fno);
			
			rs = pstmt.executeQuery();

			String dto = "";

			while (rs.next()) {
				String ftitle = rs.getString("ftitle");

				dto = ftitle;
			}

			return dto;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}

		return null;
	}
	
	public boolean checkDupFilm(int fno) { 

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			// sql select 설정
			String sql =  "select * "
						+ "from film "
						+ "where fno = ? ";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, fno);
			
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

	// 데이터를 db로 반환한다.
	public int insertFilm(int filmNo, String filmTitle, String diretor, int releaseDate, String runningtime,
			int filmRate, int filmorder) {

		try {

			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "insert into film (fno, ftitle, director, releasedate," + " runningtime ,filmrate,filmorder)"
					+ " values (?,?,?,?,?,?,?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, filmNo); // ? 입력될 정보를 설정
			pstmt.setString(2, filmTitle.trim());
			pstmt.setString(3, diretor.trim());
			pstmt.setInt(4, releaseDate);
			pstmt.setString(5, runningtime);
			pstmt.setInt(6, filmRate);
			pstmt.setInt(7, filmorder);

			int count = pstmt.executeUpdate();
			if (count > 0) {
				System.out.println("데이터 입력 성공");
			}
			return count;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt, conn);
		}
		
		return 0;
	}
	
	public void insertExceptFno(String filmTitle, String diretor, int releaseDate, String runningtime, int filmRate,
			int filmorder) {

		try {

			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "insert into film (fno, ftitle, director, releasedate," 
											+ " runningtime ,filmrate,filmorder)"
					+ " values (film1.nextval,?,?,?,?,?,?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, filmTitle.trim());
			pstmt.setString(2, diretor.trim());
			pstmt.setInt(3, releaseDate);
			pstmt.setString(4, runningtime);
			pstmt.setInt(5, filmRate);
			pstmt.setInt(6, filmorder);

			int count = pstmt.executeUpdate();
			if (count > 0) {
				System.out.println("데이터 입력 성공");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt, conn);
		}
	}
	
	public void insertExceptOrder(String filmTitle, String diretor, int releaseDate,
								String runningtime , int filmRate) {

	try {

		Class.forName(driver);
		conn = DriverManager.getConnection(url, id, pw);

		String sql = "insert into film (fno, ftitle, director, releasedate,"
								+ " runningtime ,filmrate)" 
					+ " values (film1.nexval,?,?,?,?,?)";

		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, filmTitle.trim());
		pstmt.setString(2, diretor.trim());
		pstmt.setInt(3, releaseDate);
		pstmt.setString(4, runningtime);
		pstmt.setInt(5, filmRate);
		
		int count = pstmt.executeUpdate();
		if (count > 0) {
			System.out.println("데이터 입력 성공");
		}

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		close(pstmt, conn);
	}
}

	// 해당 영화 넘버를 통해서 데이터를 수정한다.
	public int updateFilmByNum(int filmNo, int modifyFilmNo,String filmTitle, String director, int releaseDate, String runningtime,
			int filmRate) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			
			String sql = "	update 	film " 
					  + "	set fno = ?, "
					  		 + "ftitle = ?," 
					         + "director = ?,"
					    	+ "	releasedate = ?," 
					         + "runningtime = ?," 
					    	 + "filmrate = ? "
						+ "	where fno = ?";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, modifyFilmNo);
			pstmt.setString(2, filmTitle.trim());
			pstmt.setString(3, director.trim());
			pstmt.setInt(4, releaseDate);
			pstmt.setString(5, runningtime);
			pstmt.setInt(6, filmRate);
			pstmt.setInt(7, filmNo); // ? 입력될 정보를 설정

			int count = pstmt.executeUpdate();
			if (count > 0) {
				System.out.println("데이터 변경 성공");
				return count;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt, conn);
		}
		return 0;
	}

	public void updateByName(String filmTitle, String diretor, int releaseDate, String runningtime, int filmRate) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "	update 	film " + "	set		director = ?," + "			releasedate = ?,"
					+ "			runningtime = ?," + "			filmrate = ?," + "	where	ftitle = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, diretor.trim());
			pstmt.setInt(2, releaseDate);
			pstmt.setString(3, runningtime);
			pstmt.setInt(4, filmRate);
			pstmt.setString(5, filmTitle);

			int count = pstmt.executeUpdate();
			System.out.println(count);
			if (count > 0) {
				System.out.println("데이터 변경 성공");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt, conn);
		}
	}
	
	public void resetFilmOrder(int delOrder, int setOrder) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "	update 	film " 
						+ "	set filmorder = ? "
						+ " where filmorder = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, setOrder);
			pstmt.setInt(2, delOrder);

			int count = pstmt.executeUpdate();
			if (count > 0) {
				System.out.println("데이터 변경 성공");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt, conn);
		}
	}
	
	public int updateFilmOrder(int fno, int Order) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "	update 	film " 
						+ "	set filmorder = ? "
						+ " where fno = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, Order);
			pstmt.setInt(2, fno);

			int count = pstmt.executeUpdate();
			if (count > 0) {
				System.out.println("데이터 변경 성공");
				return count;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt, conn);
		}
		return 0;
	}
	
	public void incresePayCount(int fno) {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "	update 	film " 
						+ "	set paycount = nvl(paycount,0) + 1 "
						+ " where fno = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, fno);

			int count = pstmt.executeUpdate();
			if (count > 0) {
				System.out.println("데이터 변경 성공");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt, conn);
		}
	}
	
	public boolean changeOrder(int orderNum, int filmNum) {

		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			conn.setAutoCommit(false);
			
			String sql1 = "	update film "
						 + "set filmorder = (select filmorder "
						 				 + "from film "
						 				 + "where fno = ?) "
						 + "where filmorder = ?";

			pstmt1 = conn.prepareStatement(sql1);
			pstmt1.setInt(1, filmNum);
			pstmt1.setInt(2, orderNum);
			
			int count1 = pstmt1.executeUpdate();

			String sql2 = "update film "
						+ "set filmorder = ? "
						+ "where fno = ?";
			
			pstmt2 = conn.prepareStatement(sql2);
			pstmt2.setInt(1, orderNum);
			pstmt2.setInt(2, filmNum);
			
			int count2 = pstmt2.executeUpdate();
			
			System.out.println("cnt" + count2);	
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
			} catch (Exception e2) {}
			
			close(pstmt1, null);
			close(pstmt2, conn);
		}
		return false;
	}

	public int deleteByNum(int fno) {

		try {

			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

			String sql = "	delete 	film " + "	where	fno = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, fno); // ? 입력될 정보를 설정

			int count = pstmt.executeUpdate();
			
			if (count > 0) {
				System.out.println("데이터 삭제 성공");
				return count;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt, conn);
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

	private void close(PreparedStatement pstmt, Connection conn) {
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

		FilmDao fm = new FilmDao();
		
		System.out.println(fm.selectFilm());
		System.out.println("sfdfsd sd dsf\n sfsdfsdfsdf");
		
//		System.out.println(fm.selectFilm().get(1).getFilmOrder());
		
//		fm.resetOrder(14, 11);
//		fm.incresePayCount(20001);
		
//		System.out.println(fm.checkDupFilm(20001));
//		ArrayList<Film> filmList = fm.selectFilmUntil();
		
//		filmList.get(0).getPaymentCnt();
		
//		System.out.println(filmList.size());
//		
//		
		// fm.insert(20009, "fsdfs", "dsffsd", "12344", "52분", 15);

	}

}
