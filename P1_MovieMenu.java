package reserve;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImagingOpException;
import java.io.IOException;
import java.nio.channels.ShutdownChannelGroupException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class P1_MovieMenu extends JFrame {

	DateManager dm = new DateManager();
	MemberDao md = new MemberDao();
	FilmDao fm = new FilmDao();

	// 개봉일이 5일전인 영화만 출력
	ArrayList<Film> filmList = fm.selectFilmfromReleseDateBefore5Days();

	final static String bar = "ㅣ";
	static int lgCnt;
	static int adCnt;
	static int p7Cnt;
	static int p0Cnt;
	static String ID;
	static boolean checkLogin; // 로그인을 햇나 안햇나 체크, 햇으면 예매, 아니면 로그인
	static boolean completePayment;

	int filmIdx = 0;
	int filmNum;
	String filmName;

	boolean goToLogin;
	boolean goToMyPage;
	boolean goToReservePage;

	Container c = getContentPane();

	JPanel nMenu = new JPanel();
	JPanel plMainMenu = new JPanel();
	JPanel sMenu = new JPanel();
	JPanel cMenu = new JPanel();
	JPanel cSouthMenu = new JPanel();
	JPanel cMovieInfo = new JPanel();
	JPanel plLogin = new JPanel();
	JPanel plMyPage = new JPanel();

	static JLabel lbLogin = new JLabel("로그인", JLabel.RIGHT);
	static JLabel lbLogout = new JLabel("", JLabel.CENTER);
	static JLabel lbAdminBar = new JLabel(bar, JLabel.CENTER);
	static JLabel lbBar1 = new JLabel(bar, JLabel.CENTER);
	static JLabel lbBar2 = new JLabel(bar, JLabel.CENTER);
	static JLabel lbRecent = new JLabel("최신 개봉작");

	JLabel lbMyPage = new JLabel("My Page", JLabel.LEFT);
	JLabel lbHome = new JLabel("HOME", JLabel.CENTER);
	JLabel lbMoviePic = new JLabel();
	JLabel lbMovieInfo = new JLabel(filmList.get(0).getFilmTitle());
	JLabel lbFilmRate = new JLabel();

	JButton btReserve = new JButton("지금 예매");
	JButton[] btChange = new JButton[2];

	ImageIcon[] changeIcon = {

			new ImageIcon("images/left.png"), new ImageIcon("images/right.png") };

	ArrayList<ImageIcon> filmPost = new ArrayList<>(); // film list postImage
	ArrayList<ImageIcon> filmRateImage = new ArrayList<>(); // film list
															// rankImage

	public P1_MovieMenu() {

		for (int i = 0; i < filmList.size(); i++) {

			filmPost.add(new ImageIcon("images/resize_movies/" + filmList.get(i).getFilmNo() + ".jpg"));
			filmRateImage.add(new ImageIcon("images/" + filmList.get(i).getFilmRate() + ".png"));
		}

		setTitle("영화 예매");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		filmName = filmList.get(0).getFilmTitle();
		lbMoviePic.setIcon(filmPost.get(0));
		lbFilmRate.setIcon(filmRateImage.get(0));
		lbMoviePic.setHorizontalAlignment(JLabel.CENTER);

		// 1. nMenu설정 / 상단메뉴 설정
		JPanel line1 = new JPanel(); // 구분선
		JPanel line2 = new JPanel();
		JPanel line3 = new JPanel();

		nMenu.setLayout(new BorderLayout());
		nMenu.add(lbHome, BorderLayout.WEST);
		nMenu.add(lbLogout, BorderLayout.EAST);
		nMenu.add(plMainMenu, BorderLayout.CENTER);
		nMenu.add(line1, BorderLayout.SOUTH);

		line1.setLayout(new GridLayout(1, 1));
		line1.add(line2);

		plMainMenu.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 7));

		plLogin.add(lbLogin);
		plMyPage.add(lbMyPage);

		plMainMenu.add(plLogin);
		plMainMenu.add(lbBar1);
		plMainMenu.add(lbAdminBar);
		plMainMenu.add(lbBar2);
		plMainMenu.add(plMyPage);

		hideBar();

		line2.setPreferredSize(new Dimension(0, 2));
		lbHome.setPreferredSize(new Dimension(90, 35));
		lbLogout.setPreferredSize(new Dimension(90, 35));

		// 여백
		nMenu.setBorder(BorderFactory.createEmptyBorder(5, 0, 2, 0));

		nMenu.setBackground(Color.white);
		plMainMenu.setBackground(Color.white);
		plLogin.setBackground(Color.white);
		plMyPage.setBackground(Color.white);
		line2.setBackground(Color.red);
		line3.setBackground(Color.white);

		lbHome.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		lbLogin.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		lbLogout.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		lbMyPage.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		lbAdminBar.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		lbBar1.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		lbBar2.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));

		lbHome.addMouseListener(new ChangeMenu());
		lbLogin.addMouseListener(new ChangeMenu());
		lbLogout.addMouseListener(new ChangeMenu());
		lbMyPage.addMouseListener(new ChangeMenu());
		lbAdminBar.addMouseListener(new ChangeMenu());

		// 2. cMenu설정 / 중단 메뉴 설정
		cMenu.setLayout(new BorderLayout());
		cMenu.add(lbRecent, BorderLayout.NORTH);
		cMenu.add(lbMoviePic, BorderLayout.CENTER);
		cMenu.add(cSouthMenu, BorderLayout.SOUTH);
		cMenu.setBackground(Color.white);

		lbRecent.setHorizontalAlignment(JLabel.CENTER);
		lbRecent.setBackground(Color.black);
		lbRecent.setOpaque(true);
		lbRecent.setForeground(Color.white);
		lbRecent.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		lbRecent.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 20));

		lbMoviePic.addMouseListener(new ChangeMenu());
		// 사진클릭시 다음화면으로 넘어감

		// 2.1. cmenu에서 하단메뉴 버튼 설정
		cSouthMenu.setLayout(new BoxLayout(cSouthMenu, BoxLayout.X_AXIS));
		cSouthMenu.add(cMovieInfo);
		cSouthMenu.add(btReserve);
		cSouthMenu.setBackground(Color.white);

		btReserve.setPreferredSize(new Dimension(261, 0));

		btReserve.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 16));
		btReserve.setBackground(Color.red);
		btReserve.setForeground(Color.white);

		btReserve.addActionListener(new ChangePicture());

		// 2.2.cmenu에서 하단메뉴 좌측 cMovie 설정
		cMovieInfo.setLayout(new BorderLayout());
		cMovieInfo.add(lbMovieInfo, BorderLayout.WEST);
		cMovieInfo.add(lbFilmRate);
		cMovieInfo.setBackground(Color.white);

		lbMovieInfo.setBorder(BorderFactory.createEmptyBorder(6, 25, 6, 0));
		lbFilmRate.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

		changeFontSizeIfLength();

		// 3. sMenu 설정 / 하단 메뉴 설정
		sMenu.setLayout(new GridLayout(1, 2));

		for (int i = 0; i < btChange.length; i++) {
			btChange[i] = new JButton(changeIcon[i]);
			btChange[i].addActionListener(new ChangePicture());
			btChange[i].setBackground(Color.black);
			sMenu.add(btChange[i]);
		}

		// 컨테이너에 패널 추가
		c.add(nMenu, BorderLayout.NORTH);
		c.add(cMenu, BorderLayout.CENTER);
		c.add(sMenu, BorderLayout.SOUTH);
		c.setBackground(Color.white);

		// 창이 출력되는 위치 지정
		Dimension frameSize = getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width) / 3, (screenSize.height - frameSize.height) / 10);

		setSize(550, 660);
		setVisible(true);
		setResizable(false);
	} // p1 movie menu

	private void changeFontSizeIfLength() {

		if (filmName.length() > 24) {
			lbMovieInfo.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 11));
		} else
			lbMovieInfo.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 14));
	}

	public void p1InformrMsg(Component parentComponent, String error) {
		UIManager.put("OptionPane.messageFont", new Font("맑은 고딕", Font.CENTER_BASELINE, 13));
		UIManager.put("OptionPane.buttonFont", new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);

		JOptionPane.showMessageDialog(parentComponent, error, "Inform", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void hideBar() {
		lbBar1.setVisible(false);
		lbBar2.setVisible(false);
	}

	public static void showBar() {
		lbBar1.setVisible(true);
		lbBar2.setVisible(true);
	}

	private void showMenuByAge() {

		int mAge = dm.getKoreanAge(md.getBirth(ID));
		int filmRate = filmList.get(filmIdx).getFilmRate();

		if (mAge >= filmRate) {

			goToP3();
			System.out.println("영화선택메뉴로");
		}

		else {
			p1InformrMsg(this, filmRate + "세 이상만 관람 가능합니다.");
		}
	}

	private void resetFilm() {
		
		filmIdx = 0;
		filmList = fm.selectFilmfromReleseDateBefore5Days();
		
//		filmPost.removeAll(filmPost);
//		filmRateImage.removeAll(filmRateImage);
		
		ArrayList<ImageIcon> mdFilmPost = new ArrayList<>();
		ArrayList<ImageIcon> mdFilmRateImage = new ArrayList<>();
		
		ImageIcon img = new ImageIcon();
		
		for (int i = 0; i < filmList.size(); i++) {
			img = new ImageIcon("images/resize_movies/" + filmList.get(i).getFilmNo() + ".jpg");
			mdFilmPost.add(img);
			mdFilmRateImage.add(new ImageIcon("images/" + filmList.get(i).getFilmRate() + ".png"));
			
			System.out.println("filmnum " + filmList.get(i).getFilmNo());
		}		
		
		filmPost = mdFilmPost;
		filmRateImage = mdFilmRateImage;
		
//		System.out.println(filmPost.get(16));
		System.out.println(filmList.get(0).getFilmNo());
		System.out.println("filmIdx " + filmIdx);
		System.out.println("filmPost.size " + filmPost.size());

//		lbMoviePic.setIcon(filmPost.get(16));
		lbMoviePic.setIcon(filmPost.get(0));
		
		lbMovieInfo.setText(filmList.get(0).getFilmTitle());
		lbFilmRate.setIcon(filmRateImage.get(0));
		
		changeFontSizeIfLength();
	}

	public void goToP0() {

		P0_MovieManager p0 = new P0_MovieManager(this, "관리자 모드", true);
		p0.setVisible(true);
		
		resetFilm();
	}

	public void goToP2() {
		P2_LoginMenu P2 = new P2_LoginMenu(this, "로그인", true);
		P2.setVisible(true);
	}

	public void goToP3() {

		filmNum = filmList.get(filmIdx).getFilmNo();

		P1_MovieMenu.completePayment = false;
		P3_SelectTheater.completePayment = false;
		P4_SelectSeat.completePayment = false;

		P3_SelectTheater.filmNum = filmNum;
		P4_SelectSeat.filmName = filmName;
		P3_SelectTheater P3 = new P3_SelectTheater(this, "상영관 선택", true);
		P3.setVisible(true);

		if (completePayment) {
			dispose();
			goToP7();
		}
	}

	public void goToP7() {
		P7_MyPage p7 = new P7_MyPage();
		// P7_MyPage p7 = P7_MyPage.getInstance();
		p7.lbLogin.setText(ID);
	}

	public static void Login() {
		checkLogin = true;
		lbLogin.setText(ID);
		lbLogout.setText("로그아웃");

		try {
			if (ID.equals("admin")) {
				lbAdminBar.setText("Go Manager Mode");
				showBar();
			}
		} catch (Exception e) {
		}
	}

	public static void Logout() {
		checkLogin = false;
		lbLogin.setText("로그인");
		lbLogout.setText("");
		lbAdminBar.setText(bar);
		hideBar();
	}

	public static void main(String[] args) {
		new P1_MovieMenu();
	} // main

	// 버튼누를시 그림을 변경하는 리스너
	public class ChangePicture implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton bt = (JButton) e.getSource();

			// 지금 예메 메뉴
			if (bt == btReserve) {

				if (checkLogin) { // 로그인 일때
					showMenuByAge();
				}

				else { // 로그인이 아닐때
					goToP2();
					System.out.println("로그인");
					;
				}
			}

			else if (bt == btChange[0]) { // db데이터로 변경한 부분
				filmIdx--;
				System.out.println("filmIdx " + filmIdx);
				if (filmIdx < 0)
					filmIdx = filmPost.size() - 1;
				
				filmName = filmList.get(filmIdx).getFilmTitle();

				lbMoviePic.setIcon(filmPost.get(filmIdx));
				lbMovieInfo.setText(filmName);
				changeFontSizeIfLength();

				lbFilmRate.setIcon(filmRateImage.get(filmIdx));
			}

			else {
				filmIdx++;
				System.out.println("filmIdx " + filmIdx);
				if (filmIdx == filmPost.size())
					filmIdx = 0;
								
				filmName = filmList.get(filmIdx).getFilmTitle();

				lbMoviePic.setIcon(filmPost.get(filmIdx));
				lbMovieInfo.setText(filmName);
				changeFontSizeIfLength();

				lbFilmRate.setIcon(filmRateImage.get(filmIdx));
			}
		}// overide
	}// changePicture

	public class ChangeMenu extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {

			// 1. 영화 그림을 선택햇을때
			if (e.getSource() == lbMoviePic) {

				System.out.println(filmList.get(filmIdx).getFilmTitle());

				if (checkLogin) { // 로그인 일때
					showMenuByAge();
				}

				else { // 로그인이 아닐때
					goToP2();
					System.out.println("로그인");
					;
				}
			}
			// 2. 로그인 클자를 - 로그인 페이지 불러들인다.
			else if (e.getSource() == lbLogin) {

				System.out.println("p7cnt : " + (p7Cnt + 1));
				if (!checkLogin) {

					lgCnt++;

					if (lgCnt % (p7Cnt + 1) == 0) {
						goToP2();
					}
					// static 선언으로 인해 p7에서 new p1이 창이 새로 형성되면서
					// lb에 액션리스너를 통해 같이 종속된 p2창이 같이 형성된다
					// 그래서 이 창을 형성되는거 막지 못하지만
					// 화면에나타내는걸 막기위해서 위해서 아래 식을 사용함
					// cnt는 클릭할 때 new 창들이 카운터수
					// p7cnt는 p7에 new p1을 선언한 획수
					System.out.println("lgCnt : " + lgCnt);
				}
			}

			// 3. 로그아웃 글자를 택할시 - 로그아웃한다.
			else if (e.getSource() == lbLogout) {

				if (checkLogin) { // 로그인 일때 / 로그아웃한다.
					p1InformrMsg(lbRecent, "로그아웃이 되었습니다.");
					Logout();
					// lbBar.setText(bar);
				}
			}

			else if (e.getSource() == lbAdminBar) {

				if (checkLogin) {

					adCnt++;

					if (adCnt % (p7Cnt + 1) == 0) {
						goToP0();
					}
					System.out.println("adCnt : " + adCnt);
				}
			}

			// 4. 마이 페이지 누를시
			else if (e.getSource() == lbMyPage) {

				if (checkLogin) { // 로그인 일때

					goToP7();
					dispose(); // 창은 닫지만 소스를 다읽고 난후 닫는다.

					System.out.println("mypage로");
				}

				else { // 로그인이 아닐때
					goToP2();
					System.out.println("로그인");
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {

			if (e.getSource() == lbLogin) {

				if (!checkLogin) { // 로그아웃일때
					lbLogin.setForeground(Color.blue);
					setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
			}

			else if (e.getSource() == lbLogout) {

				if (checkLogin) { // 로그인 일때
					lbLogout.setForeground(Color.blue);
					setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
			}

			else if (e.getSource() == lbAdminBar) {

				if (checkLogin) { // 로그인 일때
					lbAdminBar.setForeground(Color.blue);
					setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
			}

			else if (e.getSource() == lbMyPage) {
				lbMyPage.setForeground(Color.blue);
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			else if (e.getSource() == lbHome) {
				lbHome.setForeground(Color.blue);
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			else if (e.getSource() == lbMoviePic) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {

			lbLogin.setForeground(Color.BLACK);
			lbMyPage.setForeground(Color.BLACK);
			lbAdminBar.setForeground(Color.BLACK);
			lbHome.setForeground(Color.BLACK);
			lbLogout.setForeground(Color.black);
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

		}
	}
}// movieMenu
