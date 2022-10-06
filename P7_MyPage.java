package reserve;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImagingOpException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import reserve.P1_MovieMenu.ChangeMenu;

public class P7_MyPage extends JFrame {
	
	final int maxOrdInfo = 3;
	final int pageLength = 5; 
	
	static boolean complementCancel = false;
	String ID = P1_MovieMenu.ID;
//	String ID = "d1";
	
	DateManager dm = new DateManager();

	OrderDataDao ordDao = new OrderDataDao();
	TicketDao tckDao = new TicketDao();
	FilmDao filmDao = new FilmDao();
	ScreenTimeDao scrTimeDao = new ScreenTimeDao();
	ScreenSeatDao scrSeatDao = new ScreenSeatDao();
	
	int ordTotalNum = ordDao.getOrderTotalNum(ID);
		
	Container c = getContentPane();

	JPanel nMenu = new JPanel();
	JPanel cMenu = new JPanel();
	JPanel wMenu = new JPanel();
	
	JPanel cCenterBorder = new JPanel();
	JPanel cCBNorthMenu = new JPanel();

	// 1. 상단메뉴
	JPanel plMainMenu = new JPanel();
	JPanel plLogin = new JPanel();
	JPanel plMyPage = new JPanel();

	JLabel lbHome = new JLabel("HOME", JLabel.CENTER);
	JLabel lbLogin = new JLabel(ID, JLabel.RIGHT);
	JLabel lbLogout = new JLabel("로그아웃", JLabel.CENTER);
	JLabel lbBar = new JLabel("ㅣ", JLabel.CENTER);
	JLabel lbMyPage = new JLabel("My Page", JLabel.LEFT);

	// 2. 중단메뉴
	JPanel plTitle = new JPanel();
	JLabel lbTitle = new JLabel("결제 내역", JLabel.CENTER);
	
	int ordNum[];
	int fno[];
	int scrDate[];
	int scrTimeNum[];
	int scrSeatNum[][];
	
	String fTitle[];
	String runTime[];
	String location[];
	String thName[];
	String scrName[];
	String scrTime[];
	String scrSeatName[][];
	String scrSeatNames[][];
	String peopleNum[];
	String payment[];
	String price[];

	Film[] film;
	
	OrderInfo ordInfo = new OrderInfo(1, maxOrdInfo);
	
	JPanel plOrdInfo = new JPanel();
	JPanel plOrData[];
	JPanel plOrDataEmpty[];
	JPanel plOrdContent[];
	
	JLabel lbFilmThum[];
	JLabel lbOrdContent[][];
	
	JScrollPane scrollSingle = new JScrollPane(cCenterBorder, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	//3. 페이지 색인 메뉴
	int maxPage;
	int maxPageSize;
	int pageSizeCnt;
	boolean[] choosePage;
	JLabel[] lbPage;
	
	JPanel cCBSouthMenu = new JPanel(); 
	JPanel plPageNumes = new JPanel();
	JPanel plShowPageInfo = new JPanel();
	
	JLabel lbFirst = new JLabel("<<", JLabel.CENTER);
	JLabel lbPreviousPageSize = new JLabel("<", JLabel.CENTER);
	JLabel lbNextPageSize = new JLabel(">", JLabel.CENTER);
	JLabel lbLast = new JLabel(">>", JLabel.CENTER);
	
	public P7_MyPage() {
				
		setTitle("My Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//		wMenu.setPreferredSize(new Dimension(150, 0));
		wMenu.setPreferredSize(new Dimension(0, 0));

		// 1. nMenu설정
		JPanel line1 = new JPanel(); // 구분선
		JPanel line2 = new JPanel();

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
		plMainMenu.add(lbBar);
		plMainMenu.add(plMyPage);

		line2.setPreferredSize(new Dimension(0, 2));
		lbHome.setPreferredSize(new Dimension(90, 35));
		lbLogout.setPreferredSize(new Dimension(90, 35));

		// 여백
		nMenu.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

		nMenu.setBackground(Color.white);
		plMainMenu.setBackground(Color.white);
		plLogin.setBackground(Color.white);
		plMyPage.setBackground(Color.white);
		line1.setBackground(Color.black);
		line2.setBackground(Color.red);

		lbHome.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		lbLogin.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		lbLogout.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		lbMyPage.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		lbBar.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));

		lbHome.addMouseListener(new ChangeMenu());
		lbLogin.addMouseListener(new ChangeMenu());
		lbLogout.addMouseListener(new ChangeMenu());
		lbMyPage.addMouseListener(new ChangeMenu());

		// 2. 주문정보 메뉴 설정
		// 2.1 타이틀
		JPanel cNline = new JPanel();

		cMenu.setLayout(new BorderLayout());
		cNline.setLayout(new BorderLayout());

		cNline.add(plTitle);
		cNline.setBorder(BorderFactory.createLineBorder(Color.lightGray));

		cMenu.add(cNline, BorderLayout.NORTH);

		plTitle.add(lbTitle);

		lbTitle.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 22));
		lbTitle.setForeground(Color.white);

		plTitle.setBorder(BorderFactory.createMatteBorder(6, 5, 6, 5, Color.white));
		plTitle.setBackground(Color.black);

		// 2.2 결제 내역, 주문정보 설정,
		scrollSingle.getVerticalScrollBar().setUnitIncrement(30); // 스크롤팬 속도 조절
		scrollSingle.setBorder(BorderFactory.createLineBorder(Color.lightGray));

		cMenu.add(scrollSingle, BorderLayout.CENTER);
		
		cCenterBorder.setLayout(new BorderLayout());
		cCenterBorder.add(plOrdInfo, BorderLayout.NORTH);
//		cCenterBorder.add(ordInfo, BorderLayout.NORTH);
		
		plOrdInfo.setLayout(new BorderLayout());
		plOrdInfo.add(ordInfo);
		
		cCenterBorder.setBackground(Color.white);
		plOrdInfo.setBackground(Color.white);
		wMenu.setBackground(Color.white);
				
		// 3. 페이지 수 메뉴
		// 페이지의 최고치를 구함
		if (ordTotalNum == 0) {
			maxPage = 1;
		} 
		
		else if (ordTotalNum % maxOrdInfo > 0) {
			maxPage = ordTotalNum / maxOrdInfo + 1;
		} 
		
		else
			maxPage = ordTotalNum / maxOrdInfo;

		System.out.println("maxPage " + maxPage);

		// 페이지 사이즈의 최고치를 구함
		maxPageSize = maxPage / pageLength;

		if (maxPage % pageLength == 0)
			maxPageSize -= 1;

		JPanel line = new JPanel();
				
		cCenterBorder.add(cCBSouthMenu, BorderLayout.SOUTH);
		
		cCBSouthMenu.setLayout(new BorderLayout());
		
		cCBSouthMenu.add(line,BorderLayout.NORTH);
		cCBSouthMenu.add(plShowPageInfo,BorderLayout.SOUTH);
		
//		plShowPageInfo.setLayout(new BorderLayout());
//		plShowPageInfo.add(new ShowPageInfo(0));
		
		plShowPageInfo.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
		
		plShowPageInfo.add(lbFirst);
		plShowPageInfo.add(Box.createHorizontalStrut(10));
		plShowPageInfo.add(lbPreviousPageSize);
		plShowPageInfo.add(plPageNumes);
		plShowPageInfo.add(lbNextPageSize);
		plShowPageInfo.add(Box.createHorizontalStrut(10));
		plShowPageInfo.add(lbLast);
		
		plPageNumes.add(new PageNumesInfo(0));
		
		lbFirst.addMouseListener(new ChangeMenu());
		lbPreviousPageSize.addMouseListener(new ChangeMenu());
		lbNextPageSize.addMouseListener(new ChangeMenu());
		lbLast.addMouseListener(new ChangeMenu());
			
		lbFirst.setFont(new Font("D2coding", Font.CENTER_BASELINE, 17));
		lbPreviousPageSize.setFont(new Font("D2coding", Font.CENTER_BASELINE, 17));
		lbNextPageSize.setFont(new Font("D2coding", Font.CENTER_BASELINE, 17));
		lbLast.setFont(new Font("D2coding", Font.CENTER_BASELINE, 17));
		
		cCBSouthMenu.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		plShowPageInfo.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		line.setPreferredSize(new Dimension(0, 1));
						
		cCBSouthMenu.setBackground(Color.white);
		plShowPageInfo.setBackground(Color.white);
		plPageNumes.setBackground(Color.white);
		line.setBackground(Color.lightGray);
		
		// 컨테이너에 패널 추가
		c.add(nMenu, BorderLayout.NORTH);
		c.add(wMenu, BorderLayout.WEST);
		c.add(cMenu, BorderLayout.CENTER);

		// 창이 출력되는 위치 지정
		Dimension frameSize = getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width) / 3+178, (screenSize.height - frameSize.height) / 10 + 68);
		
//		setSize(530, 660);
//		setPreferredSize(new Dimension(530, 660));
		setVisible(true);
		setResizable(true);
	}
	
	private void changeFrameSize(int maxWidth) {
		
		System.out.println("maxW : " + maxWidth);
		
		if (maxWidth > 450) {
			setSize(maxWidth + 85, 660);

		} else
			setSize(550, 660);
	}
	
//----------- 주문정보 생성
	public class OrderInfo extends JPanel {
		
		int ordTotalNum = ordDao.getOrderTotalNum(ID);
		int maxWidth = 0;
		
		//오늘 날짜에서 10일전 데이터만 가져온다.
		ArrayList<OrderData> ordDataList;
		ArrayList<Ticket> tckList;
			
		public OrderInfo(int start, int end) {
						
			Long a = System.currentTimeMillis();

			ordDataList = ordDao.selectOrderDataBefore10ByID(ID, start, end);
			
			Long b = System.currentTimeMillis();
			
			System.out.println("ord " + (b-a));
			
			ordNum = new int[ordDataList.size()];
			fno = new int[ordDataList.size()];
			scrDate = new int[ordDataList.size()];
			scrTimeNum = new int[ordDataList.size()];
			scrSeatNum = new int[ordDataList.size()][];
			
			fTitle = new String[ordDataList.size()];
			runTime = new String[ordDataList.size()];
			location = new String[ordDataList.size()];
			thName = new String[ordDataList.size()];
			scrName = new String[ordDataList.size()];
			scrTime = new String[ordDataList.size()];
			scrSeatName = new String[ordDataList.size()][];
			scrSeatNames = new String[ordDataList.size()][];
			peopleNum = new String[ordDataList.size()];
			payment = new String[ordDataList.size()];
			price = new String[ordDataList.size()];

			film = new Film[ordDataList.size()];
			
			plOrData = new JPanel[ordDataList.size()];
			plOrDataEmpty = new JPanel[ordDataList.size()];
			plOrdContent = new JPanel[ordDataList.size()];
			
			lbFilmThum = new JLabel[ordDataList.size()];
			lbOrdContent = new JLabel[ordDataList.size()][];
			
			Long c = System.currentTimeMillis();
			
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			//2.2 주문정보 내용 설정
			Font ordFont = new Font("맑은 고딕", Font.CENTER_BASELINE, 14);
						
			for (int i = 0; i < ordDataList.size(); i++) {

				// 주문정보 변수 데이터 선언
				ordNum[i] = ordDataList.get(i).getOdno();
				tckList = tckDao.selectTicketByOdno(ordNum[i]);

				fno[i] = tckList.get(0).getFno();
				fTitle[i] = tckList.get(0).getFtitle();
				runTime[i] = tckList.get(0).getRunningtime();

				scrDate[i] = tckList.get(0).getScrDate();
				location[i] = tckList.get(0).getLocation();
				thName[i] = tckList.get(0).getTname();
				scrName[i] = tckList.get(0).getSname();
				scrTime[i] = tckList.get(0).getSttime();
				scrTimeNum[i] = tckList.get(0).getStno();
				scrSeatName[i] = new String[tckList.size()];
				scrSeatNum[i] = new int[tckList.size()];

				peopleNum[i] = ordDataList.get(i).getPeopleNum() + "명";
				payment[i] = ordDataList.get(i).getPaymentMethod();
				price[i] = showMoney(ordDataList.get(i).getPrice()) + "원";

				// 주문정보 패널 선언
				plOrData[i] = new JPanel();
				plOrDataEmpty[i] = new JPanel();
				plOrdContent[i] = new JPanel();

				lbFilmThum[i] = new JLabel();
				lbFilmThum[i].setIcon(new ImageIcon("images/thumb/" + fno[i] + ".jpg"));

				plOrData[i].setLayout(new BorderLayout());
				plOrDataEmpty[i].setLayout(new BorderLayout());

				plOrData[i].setBorder(BorderFactory.createTitledBorder(null, "구매일 : " + ordDataList.get(i).getOrderDate(),
						TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, ordFont));

				plOrData[i].add(plOrDataEmpty[i]);
				
				scrSeatNames[i] = new String[4];
				
				for (int j = 0; j < scrSeatNames[i].length; j++) {
					scrSeatNames[i][j] = "";
				}
				
				// 좌석 설정
				for (int j = 0; j < tckList.size(); j++) {

					scrSeatNum[i][j] = scrSeatDao.getSeatNum(tckList.get(j).getSeno());
					scrSeatName[i][j] = "[" + tckList.get(j).getSeatName() + "]";
			
					if (j < 5)
						scrSeatNames[i][0] += scrSeatName[i][j] + " ";
					if (j >= 5 && j < 10)
						scrSeatNames[i][1] += scrSeatName[i][j] + " ";
					if (j >= 10 && j < 15)
						scrSeatNames[i][2] += scrSeatName[i][j] + " ";
					if (j >= 15 && j < 20)
						scrSeatNames[i][3] += scrSeatName[i][j] + " ";
				}

				// 그림 삽입
				plOrDataEmpty[i].add(lbFilmThum[i], BorderLayout.WEST);
				plOrDataEmpty[i].add(plOrdContent[i], BorderLayout.CENTER);
				
				lbFilmThum[i].setPreferredSize(new Dimension(150, 200));

				plOrdContent[i].setLayout(new BoxLayout(plOrdContent[i], BoxLayout.Y_AXIS));

				// 내용 삽입
				if (tckList.size() <= 5)
					lbOrdContent[i] = new JLabel[5];
				else if (tckList.size() > 5 && tckList.size() <= 10)
					lbOrdContent[i] = new JLabel[6];
				else if (tckList.size() > 10 && tckList.size() <= 15)
					lbOrdContent[i] = new JLabel[7];
				else
					lbOrdContent[i] = new JLabel[8];

				for (int j = 0; j < lbOrdContent[i].length; j++) {

					lbOrdContent[i][j] = new JLabel();
					lbOrdContent[i][j].setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 14));

					if (j < 3) {
						
						plOrdContent[i].add(lbOrdContent[i][j]);
						plOrdContent[i].add(Box.createVerticalStrut(6));
			
						lbOrdContent[i][j].setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
					}
					
					else if (j >= 3) {
						plOrdContent[i].add(lbOrdContent[i][j]);

						if (j == 3) {
							lbOrdContent[i][j].setText("좌석번호 : " + scrSeatNames[i][0]);
							lbOrdContent[i][j].setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
						}

						else if (j > 3 && j < lbOrdContent[i].length - 1) {
							
							lbOrdContent[i][j].setText(scrSeatNames[i][j-3]);		
							lbOrdContent[i][j].setBorder(BorderFactory.createEmptyBorder(0, 85, 0, 0));
						}
					
						else {
							lbOrdContent[i][j].setText(peopleNum[i] + " ㅣ " + payment[i] + " " + price[i]);
							lbOrdContent[i][j].setBorder(BorderFactory.createEmptyBorder(6, 15, 0, 0));
						}
					}
				}
				
				lbOrdContent[i][0].setText(fTitle[i] + " ㅣ " + runTime[i]);
				lbOrdContent[i][1].setText("극장 : " + location[i] + " " + thName[i] + " ㅣ " + scrName[i]);
				lbOrdContent[i][2].setText("상영일 : " + dm.IntegerToDateExYear(scrDate[i]) + " ㅣ " + scrTime[i]);

				// 줄사이즈마다 출력되는 문단설정
				if (tckList.size() <= 5) 
					lbOrdContent[i][0].setBorder(BorderFactory.createEmptyBorder(32, 15, 0, 10));

				else if (tckList.size() > 5 && tckList.size() <= 10) 
					lbOrdContent[i][0].setBorder(BorderFactory.createEmptyBorder(23, 15, 0, 10));
				
				else if (tckList.size() > 10 && tckList.size() <= 15) 
					lbOrdContent[i][0].setBorder(BorderFactory.createEmptyBorder(15, 15, 0, 10));
				
				else 
					lbOrdContent[i][0].setBorder(BorderFactory.createEmptyBorder(6, 15, 0, 10));
				
				// 각 구매일 추가
				add(plOrData[i]);
				add(Box.createVerticalStrut(8));

				// 액션리스너 설정
				plOrData[i].addMouseListener(new CheckSeat());

				// 패널, 라벨 문단 설정
				plOrDataEmpty[i].setBorder(BorderFactory.createEmptyBorder(10, 10, 15, 10));
				plOrdContent[i].setBorder(BorderFactory.createLineBorder(Color.lightGray));

				// 패널, 라벨 폰트 컬러설정
				plOrData[i].setBackground(Color.white);
				plOrDataEmpty[i].setBackground(Color.white);
				plOrdContent[i].setBackground(Color.white);
						
				if (maxWidth < plOrData[i].getPreferredSize().getWidth()) {
					maxWidth = (int) plOrData[i].getPreferredSize().getWidth();
				}				
			}
						
			setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));
			setBackground(Color.white);
						
			Long d = System.currentTimeMillis();
			System.out.println("tck " + (d-c));
			
			changeFrameSize(maxWidth);
		}
	}
	
//------------ 페이지 번호 생성
	public class PageNumesInfo extends JPanel {
		
		PageNumesInfo(int cnt10) {
						
			setLayout(new FlowLayout(FlowLayout.CENTER,7,0));
			
			// 생성되는 페이지 사이즈 구함
			int pageSize = pageLength; 
			
			if (cnt10 == maxPageSize) {
				
				pageSize = maxPage%pageLength;
				if (pageSize == 0) pageSize = pageLength;
			}
			System.out.println("m/p " +maxPage/pageLength);
			System.out.println("maxpagesize " + maxPageSize);
			System.out.println("pageSize " + pageSize);
			
			choosePage = new boolean[pageSize];
			lbPage = new JLabel[pageSize];
			
			for (int i = 0; i < pageSize; i++) {
				
				lbPage[i] = new JLabel((i+1)+pageLength*cnt10+"",JLabel.CENTER);
				
				lbPage[i].setOpaque(true);
				lbPage[i].setBackground(Color.white);
				lbPage[i].setPreferredSize(new Dimension(25, 25));
				lbPage[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
				
				lbPage[i].setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
	
				lbPage[i].addMouseListener(new pageNumEvent());
				
				if (i < 10) {
					add(lbPage[i]);
				}
				else break;
			}
			selectedPage(0);
			setBackground(Color.white);
		}	
	}
	
	private void showOrderInfo(int page) {
		
		plOrdInfo.setVisible(false);
		plOrdInfo.removeAll();
		plOrdInfo.add(new OrderInfo(maxOrdInfo*pageLength*pageSizeCnt + page*maxOrdInfo + 1, 
						maxOrdInfo*pageLength*pageSizeCnt + (page + 1) * maxOrdInfo));
		scrollTop();
		plOrdInfo.setVisible(true);
	}
	
	private void showPageNumesInfo(int page) {
		
		plPageNumes.removeAll();
		plPageNumes.setVisible(false);
		plPageNumes.add(new PageNumesInfo(page));
		plPageNumes.setVisible(true);
	}
	
	//상하단 메뉴 액션 리스너
	public class ChangeMenu extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {

			// 로그아웃하고 p1으로
			if (e.getSource() == lbLogout) {

				p7InformMsg("로그아웃이 되었습니다.");

				new P1_MovieMenu();
				P1_MovieMenu.Logout();
				P1_MovieMenu.p7Cnt++;
				P1_MovieMenu.lgCnt = 0;
				P1_MovieMenu.adCnt = 0;

				setVisible(false);

				System.out.println("로그아웃하고 영화 선택메뉴로");
			}

			// 로그아웃하지 않고 로그인 상태로 p1으로
			else if (e.getSource() == lbHome) {

				// dispose();
				setVisible(false);

				new P1_MovieMenu();
				P1_MovieMenu.Login();
				P1_MovieMenu.p7Cnt++;
				P1_MovieMenu.lgCnt = 0;
				P1_MovieMenu.adCnt = 0;

				System.out.println("창닫고 영화 선택메뉴로");
			}

			else if (e.getSource() == lbFirst) {

				if (maxPage > 1) {
					if (pageSizeCnt == 0 && choosePage[0]) {
					} else {

						pageSizeCnt = 0;
						showOrderInfo(0);
						showPageNumesInfo(0);

						System.out.println("pageSizeCnt " + pageSizeCnt);
					}
				}
			}

			else if (e.getSource() == lbPreviousPageSize) {
				
				pageSizeCnt--;
				if (pageSizeCnt < 0) pageSizeCnt = 0;
				
				showPageNumesInfo(pageSizeCnt);
				showOrderInfo(0);
				
				System.out.println("pageSizeCnt " + pageSizeCnt);
			}

			else if (e.getSource() == lbNextPageSize) {
				
				if (pageSizeCnt < maxPageSize) {
					
					pageSizeCnt++;
					if (pageSizeCnt > maxPageSize) pageSizeCnt = maxPageSize;
					
					showPageNumesInfo(pageSizeCnt);
					showOrderInfo(0);
				}
				
				System.out.println("pageSizeCnt " + pageSizeCnt);
			}

			else if (e.getSource() == lbLast) {

				if (maxPage > 1) {
					if (pageSizeCnt == maxPageSize && choosePage[(maxPage-1) - maxPageSize*pageLength]) {}
					else {
						
						pageSizeCnt = maxPageSize;
						showPageNumesInfo(maxPageSize);
						System.out.println((maxPage-1) - (maxPageSize-1)*pageLength);
						showOrderInfo((maxPage-1) - maxPageSize*pageLength);
						unSelectedPage(0);
						selectedPage((maxPage-1) - maxPageSize*pageLength);

						System.out.println("pageSizeCnt " + pageSizeCnt);
					}
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {

			// 상단메뉴
			if (e.getSource() == lbLogout) {

				lbLogout.setForeground(Color.blue);
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			else if (e.getSource() == lbMyPage) {
				lbMyPage.setForeground(Color.blue);
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			else if (e.getSource() == lbHome) {
				lbHome.setForeground(Color.blue);
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			// 하단메뉴
			else if (e.getSource() == lbFirst) {
				lbFirst.setForeground(Color.red);
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			else if (e.getSource() == lbPreviousPageSize) {
				lbPreviousPageSize.setForeground(Color.red);
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			else if (e.getSource() == lbNextPageSize) {
				lbNextPageSize.setForeground(Color.red);
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			else if (e.getSource() == lbLast) {
				lbLast.setForeground(Color.red);
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {

			lbLogin.setForeground(Color.BLACK);
			lbMyPage.setForeground(Color.BLACK);
			lbHome.setForeground(Color.BLACK);
			lbLogout.setForeground(Color.black);
			lbLast.setForeground(Color.black);
			lbNextPageSize.setForeground(Color.black);
			lbPreviousPageSize.setForeground(Color.black);
			lbFirst.setForeground(Color.black);

			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

		}
	}

	// 하단 페이지 액션리스너
	public class pageNumEvent extends MouseAdapter {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			
			for (int i = 0; i < lbPage.length; i++) {

				if (e.getSource() == lbPage[i]) {
					setCursor(new Cursor(Cursor.HAND_CURSOR));
					
					if (maxPage > 1) {
						if(!choosePage[i]) {
							showOrderInfo(i);
						}
						selectedPage(i);
					}					
				}
				else {
					unSelectedPage(i);
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			super.mouseEntered(e);
			
			for(int i = 0; i < lbPage.length; i++) {
				
				if(e.getSource() == lbPage[i]) {
					setCursor(new Cursor(Cursor.HAND_CURSOR));
					selectingPage(i);
				}
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			super.mouseExited(e);
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			
			for(int i = 0; i < lbPage.length; i++) {
				
				if(e.getSource() == lbPage[i]) {	
					
					if(!choosePage[i]) unSelectingPage(i);
				}
			}
		}
	}
	
	//구매내역 패널을 클릭시 발생하는 액션리스너
	public class CheckSeat extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {

			for (int i = 0; i < plOrData.length; i++) {

				if (e.getSource() == plOrData[i]) {
					goToP7A(i);
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {

			for (int i = 0; i < plOrData.length; i++) {

				if (e.getSource() == plOrData[i]) {
					setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
	private void scrollTop() {
		scrollSingle.getVerticalScrollBar().setValue(
				scrollSingle.getVerticalScrollBar().getMinimum());
	}

	private void selectedPage(int i) {
		
		choosePage[i] = true;
		lbPage[i].setForeground(Color.white);
		lbPage[i].setBackground(Color.darkGray);
	}
	
	private void unSelectedPage(int i) {
		
		choosePage[i] = false;
		lbPage[i].setForeground(Color.black);
		lbPage[i].setBackground(Color.white);
	}
	
	private void selectingPage(int i) {
		
		lbPage[i].setForeground(Color.white);
		lbPage[i].setBackground(Color.darkGray);
	}
	
	private void unSelectingPage(int i) {
		
		lbPage[i].setForeground(Color.black);
		lbPage[i].setBackground(Color.white);
	}
	
	public void goToP7A(int i) {
		
		complementCancel = false;
		System.out.println(scrDate[i]);
		System.out.println(dm.timeToInteger(scrTime[i]));
		
		long scrDayAndTime = (long)scrDate[i]*10000 + (long)dm.timeToInteger(scrTime[i]);
		
		System.out.println(scrDayAndTime);
		
		P7A_ShowSeat.ordNum = ordNum[i];
		P7A_ShowSeat.scrTimeNum = scrTimeNum[i];
		P7A_ShowSeat.PeopleNum = peopleNum[i];
		P7A_ShowSeat.price = price[i];
		P7A_ShowSeat.filmName = fTitle[i];
		P7A_ShowSeat.scrDayAndTime = scrDayAndTime;
		
		System.out.println("scrtime " + scrTimeNum[i]);
		
		if (scrTimeNum[i] > 0) {
			LinkedList<Integer> scrSeatNumList = new LinkedList<>();
			scrSeatNumList.removeAll(scrSeatNumList);

			for (int j = 0; j < scrSeatName[i].length; j++) {

				scrSeatNumList.add(scrSeatNum[i][j]);
			}

			P7A_ShowSeat.scrSeatNumList = scrSeatNumList;

			P7A_ShowSeat p7A = new P7A_ShowSeat(this, "예약 좌석 보기", true);
			p7A.setVisible(true);
		}
		
		if (complementCancel) {
			setVisible(false);
			new P7_MyPage();
		}
	}
	
	private void goToP1() {
		new P1_MovieMenu();
		dispose();
	}
	
	private String showMoney(int money) { 

		String money1 = String.valueOf(money);

		if (money < 1000)
			return "0";

		return money1.substring(0, money1.length() - 3) + ",000";
	}
	
	public void p7InformMsg(String error) {
		UIManager.put("OptionPane.messageFont", new Font("맑은 고딕", Font.CENTER_BASELINE, 13));
		UIManager.put("OptionPane.buttonFont", new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);

		JOptionPane.showMessageDialog(this, error, "Inform", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void main(String[] args) {
//		new P7_MyPage();
//		P7_MyPage p7 = P7_MyPage.getInstance();
	}
	
	
}// movieMenu
