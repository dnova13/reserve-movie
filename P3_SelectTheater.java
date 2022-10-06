package reserve;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicSliderUI.ScrollListener;
import javax.swing.plaf.synth.SynthSeparatorUI;

public class P3_SelectTheater extends JDialog {
	
	DateManager dm = new DateManager();
	FilmDao filmDao = new FilmDao();
	TheaterLocationDao thLocDao = new TheaterLocationDao();
	TheaterDao theaterDao = new TheaterDao();
	ScreenDao scrDao = new ScreenDao();
	ScreenTimeDao scrTimeDao = new ScreenTimeDao();
	ScreenSeatDao scrSeatDao = new ScreenSeatDao();
	TicketDao tckDao = new TicketDao();
	OrderDataDao ordDao = new OrderDataDao();
	
	ArrayList<TheaterLocation> thLocList = thLocDao.selectTheaterLoc();
	ArrayList<Theater> theaterList = theaterDao.selectTheaterByLoc(thLocList.get(0).getTlNo());
	ArrayList<Screen> scrList;
	ArrayList<ScreenTime> scrTimeList;
	ArrayList<Ticket> tckList;
			
	// 1. 날짜 선택메뉴
	JPanel plNorthBorder = new JPanel();
	JPanel plCalendar = new JPanel();
	JPanel plToday = new JPanel();
	JPanel plDayOfWeek = new JPanel();
	JPanel plDay = new JPanel();
	
	Day lbDay[] = new Day[10];
	DayOfWeek lbDayOfWeek[] = new DayOfWeek[lbDay.length];
	JLabel lbToday = new JLabel();
	
	Date reserveDate[] = new Date[lbDay.length];
	
	//2. 지역 선택메뉴
	JPanel plWestBorder = new JPanel();
	JPanel plWestBorderBox = new JPanel();
	JPanel plLocation[] = new JPanel[thLocList.size()];
	JLabel lbLocation[] =  new JLabel[thLocList.size()];
	
	//3. 극장 선택메뉴
	JPanel plCenterBorder = new JPanel();
	JPanel plTheater = new JPanel();
	JPanel plChooseTheater = new JPanel();
	JPanel plComboBox = new JPanel();
	
	JLabel lbTheater = new JLabel("관람할 영화관을 선택하세요.");
	JComboBox<String> cbTheater = new JComboBox();
	
	//4. 상영관 선택메뉴
	JPanel plCenterNorthBorder = new JPanel();
	JPanel plScreenMenu = new JPanel();
	JPanel[] plScreen;
	JLabel[] lbScreen;
	
	//5. 상영시간 선택메뉴
	JPanel plSouthMenu = new JPanel();
	JPanel plChooseTime = new JPanel();
	JPanel plChooseTimeEast = new JPanel();
	JPanel plChooseTimeCenter = new JPanel();	
	JPanel plScrTimeMenu = new JPanel();		
	JPanel plleftSeat = new JPanel();
	
	JLabel lbScrTime = new JLabel("상영시간");
	JLabel lbleftSeat = new JLabel("잔여좌석",JLabel.CENTER);
	JLabel lbleftSeatNum = new JLabel("",JLabel.CENTER);
	
	DefaultListModel<String> model = new DefaultListModel<>();
	JList<String> ltScrTime = new JList<>();
	
	JScrollPane scScrTime = new JScrollPane();
	JButton btGoSeat = new JButton("좌석 선택");
	
	final int unlimtedPeriod  = 99999999;
	static int filmNum;
	static int reserveDateInt;
	static boolean completePayment;
	
	static String locationName;
	static String thName;
	static String scrName;
	static String scrTime;
	
	int releseDate = filmDao.selectFilmByFno(filmNum).getReleaseDate();
	int difDate;
	int previousDate;
	int locationNum;
	int theaterNum;
	int scrNum;
	int scrPeriod = unlimtedPeriod;
	int scrTotalSeat;
	int scrTotalColSeat;
	int scrTimeNum;
	int purchasedSeatNum;
	int errorCnt = 0;
		
	// 선택시 선택패널이 빨갛게 보이도록 하기위해서
	boolean chooseDate[] = new boolean[lbDay.length];
	boolean chooseLoc[] = new boolean[lbLocation.length];
	boolean chooseScreen[];

	public P3_SelectTheater(JFrame frame, String title, boolean isModal) {
//	public P3_SelectTheater() {
		
		super(frame, title, true);
		
		locationName = "서울";
		
		//0.1 지역 라벨 설정
		for (int i = 0; i < lbLocation.length; i++) {
			
			lbLocation[i] = new JLabel(thLocList.get(i).getLocaction(),JLabel.CENTER);
		}
		
	//1. 날짜 선택 메뉴		
		//1.1 현재날짜
		plNorthBorder.setLayout(new BorderLayout());
		plNorthBorder.add(plCalendar, BorderLayout.NORTH);
	
		plCalendar.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 10));

		plCalendar.setLayout(new BoxLayout(plCalendar,BoxLayout.Y_AXIS));
		plCalendar.add(Box.createVerticalStrut(5));
		plCalendar.add(plToday);
		plCalendar.add(Box.createVerticalStrut(5));
		plCalendar.add(plDayOfWeek);
		plCalendar.add(Box.createVerticalStrut(5));
		plCalendar.add(plDay);
		plCalendar.add(Box.createVerticalStrut(10));
		
		plToday.add(lbToday);
		plToday.setAlignmentX(CENTER_ALIGNMENT);
		
		plCalendar.setBackground(Color.BLACK);
		plToday.setBackground(Color.BLACK);
		
		lbToday.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		lbToday.setForeground(Color.white);
		
		//1.2 10일간 날짜 설정
		plDayOfWeek.setLayout(new GridLayout(1, 10,10 ,0));
		plDay.setLayout(new GridLayout(1, 10, 10 ,0));
		
		for (int i = 0; i < lbDay.length; i++) {
			//요일
			lbDayOfWeek[i] = new DayOfWeek(dm.today.get(Calendar.DAY_OF_WEEK));		
			
			//일
			lbDay[i] = new Day();
			lbDay[i].setText(dm.today.get(Calendar.DATE)+"");
			lbDay[i].resetColor(lbDay[i], dm.today);
			
			plDayOfWeek.add(lbDayOfWeek[i]);
			plDay.add(lbDay[i]);
			
			reserveDate[i] = new Date(dm.today.get(Calendar.YEAR), 
							dm.today.get(Calendar.MONTH), dm.today.get(Calendar.DATE));
						
			//날짜를 1씩 증가함
			dm.today.add(Calendar.DATE, 1);
			
			lbDayOfWeek[i].setHorizontalAlignment(JLabel.CENTER);
			lbDayOfWeek[i].setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 16));
			lbDayOfWeek[0].setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
			
			lbDay[i].addMouseListener(new CalendarEvent());
			lbDay[i].setHorizontalAlignment(JLabel.CENTER);
			lbDay[i].setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 16));
			lbDay[i].setBorder(BorderFactory.createEmptyBorder(3, 0, 10, 0));
		}
		plDayOfWeek.setBackground(Color.black);
		plDay.setBackground(Color.black);
		dm.today.add(Calendar.DATE, -lbDay.length);// 날짜 리셋
		
		// 개봉일이 기준으로 초기 선택되는 날짜 설정
		// 1. 개봉일이 오늘날짜와 같거나 작을때
		if (dm.todayToInteger() >= releseDate) {

			lbToday.setText(dm.showDate(dm.today));
			lbDay[0].setOpaque(true);
			lbDay[0].setForeground(Color.white);
			lbDay[0].setBackground(Color.RED);
			chooseDate[0] = true;
			reserveDateInt = reserveDate[0].showDateToInteger();
			System.out.println(reserveDateInt);
			System.out.println(dm.showDateAndTime(dm.today));
		}
		// 개봉일이 오늘날짜보다 클때
		else {
			Calendar date = Calendar.getInstance();
			
			int year = releseDate/10000;
			int month = Integer.parseInt((releseDate+"").substring(4, 6));
			int day = Integer.parseInt((releseDate+"").substring(6, 8));
			
			date.set(year, month-1, day);
			
			int convert = 24*60*60*1000;
			
			difDate = (int) ((date.getTimeInMillis() - dm.today.getTimeInMillis())/convert);
			
			previousDate = difDate;
			
			lbToday.setText(dm.showDate(date));
			lbDay[difDate].setOpaque(true);
			lbDay[difDate].setForeground(Color.white);
			lbDay[difDate].setBackground(Color.RED);
			chooseDate[difDate] = true;
			reserveDateInt = reserveDate[difDate].showDateToInteger();
			System.out.println(reserveDateInt);
			System.out.println("diffDate " + difDate);
		}

	//2. 지역 선택 센터 좌측 메뉴
		chooseLoc[0] = true;
			
		plWestBorder.setLayout(new BorderLayout());		
		plWestBorder.add(plWestBorderBox, BorderLayout.NORTH);
				
		plWestBorderBox.setLayout(new BoxLayout(plWestBorderBox, BoxLayout.Y_AXIS));
				
		for (int i = 0; i < lbLocation.length; i++) {
			
			plLocation[i] = new JPanel();
			plLocation[i].setPreferredSize(new Dimension(150, 40));
			plLocation[i].add(lbLocation[i]);
			
			plWestBorderBox.add(plLocation[i]);
			plWestBorderBox.add(Box.createVerticalStrut(3));
			
			plLocation[i].addMouseListener(new LocationEvent());
			plLocation[0].setBackground(Color.red);
			plLocation[i].setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 25));
			plLocation[i].setBackground(Color.darkGray);
			
			lbLocation[i].setForeground(Color.white);
			lbLocation[i].setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 20));
		}
		
		plWestBorder.setBorder(BorderFactory.createEmptyBorder(3, 3, 0, 3));
		
		//  윤관선을 줌
		plWestBorder.setBackground(Color.black);
		plWestBorderBox.setBackground(Color.black);
		
		//2.1. 스크롤 설정 // grid는 한 화면을 꽉채우기 때문에 불가 layout은 불가	
		//WestBorder 패널 그자체
		JScrollPane scrollSingle = 
				new JScrollPane(plWestBorder, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
						ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		scrollSingle.setPreferredSize(new Dimension(180, 480));
		scrollSingle.getVerticalScrollBar().setUnitIncrement(30); // 스크롤 속도 조절 
		
	// 3. 상영관 선택 센터 우측메뉴
		// 3.1 상단 메뉴 : 영화관 선택
		plCenterBorder.setLayout(new BorderLayout());		
		plCenterBorder.setBackground(Color.white);
		
		plCenterBorder.add(plTheater, BorderLayout.NORTH);
		
		plTheater.setLayout(new BorderLayout());
		
		plChooseTheater.add(lbTheater);
		
		// 콤보박스들 추가
		// 영화관 선택 콤보박스 설정	
		cbTheater.addItem("극장을 선택해주세요");
		for (int i = 0; i < theaterList.size(); i++) {
			cbTheater.addItem(theaterList.get(i).getTname());
		}
				
		plComboBox.add(cbTheater);

		cbTheater.setBackground(Color.white);
		cbTheater.setForeground(Color.BLACK);
		cbTheater.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		cbTheater.setPreferredSize(new Dimension(180, 30));

		// 콤보박스 강제로 가운데 정렬
		cbTheater.setRenderer(new DefaultListCellRenderer() {
			public int getHorizontalAlignment() {
				return CENTER;
			}
		});

		cbTheater.addActionListener(new TheaterCBoxEvent());
		cbTheater.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				setCursor(new Cursor(Cursor.HAND_CURSOR));

			}

			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});

		plTheater.add(plChooseTheater, BorderLayout.NORTH);
		plTheater.add(plComboBox, BorderLayout.SOUTH);
		
		//백그라운드 컬러설정
		plChooseTheater.setBackground(Color.black);
		plComboBox.setBackground(Color.black);
		
		lbTheater.setForeground(Color.white);
		lbTheater.setFont(new Font("D2coding",Font.CENTER_BASELINE,16));
		
		//여백
		plChooseTheater.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
		plComboBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
		//상하측 구분선
		plTheater.setBorder(BorderFactory.createEmptyBorder(0, 0, 1, 0));
		plTheater.setBackground(Color.DARK_GRAY);
		
	//3.2 중단메뉴 : 상영관 선택 메뉴
		plCenterBorder.add(plCenterNorthBorder, BorderLayout.CENTER);
		
		plCenterNorthBorder.setLayout(new BorderLayout());
		plCenterNorthBorder.add(plScreenMenu, BorderLayout.NORTH);
		
		plScreenMenu.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 5));
		
		plScreenMenu.setBackground(Color.BLACK);
		plScreenMenu.setBorder(BorderFactory.createEmptyBorder(1, 2, 1, 2));
		
		plCenterBorder.setBackground(Color.darkGray);
		plCenterBorder.setBorder(BorderFactory.createEmptyBorder(0, 2, 2, 2));
		
	//3.3 하단메뉴 : 상영시간 선택 메뉴 ===============================
		plScrTimeMenu.add(lbScrTime);
		plCenterNorthBorder.add(plSouthMenu,BorderLayout.CENTER);
		
		plSouthMenu.setLayout(new BorderLayout());
		plSouthMenu.add(plChooseTime,BorderLayout.CENTER);
		
		plScreenMenu.setVisible(false);

		plChooseTime.setLayout(new BorderLayout());
		plChooseTime.add(plChooseTimeCenter, BorderLayout.CENTER);
		plChooseTime.add(plChooseTimeEast, BorderLayout.EAST);

		plChooseTimeCenter.setLayout(new BorderLayout(0, 4));
		plChooseTimeCenter.add(plScrTimeMenu, BorderLayout.NORTH);

		scScrTime = new JScrollPane(ltScrTime);

		ltScrTime.setBackground(Color.white);
		ltScrTime.setFont(new Font("맑은고딕", Font.CENTER_BASELINE, 20));
		ltScrTime.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 10));

		// list 가운데 정렬
		ltScrTime.setCellRenderer(new DefaultListCellRenderer() {
			public int getHorizontalAlignment() {
				return CENTER;
			}
		});

		// 상영시간 선택시 이벤트 설정
		ltScrTime.addMouseListener(new ScTimeEvent());	
			
		plChooseTimeEast.setLayout(new BorderLayout(0,3));
		
		plleftSeat.setLayout(new BorderLayout());
		plleftSeat.add(lbleftSeat,BorderLayout.CENTER);
		plleftSeat.add(lbleftSeatNum,BorderLayout.SOUTH);
				
		plleftSeat.setPreferredSize(new Dimension(150, 120));
		
		plChooseTimeEast.add(plleftSeat,BorderLayout.NORTH);
		plChooseTimeEast.add(btGoSeat,BorderLayout.CENTER);
		
//------- 좌석선택화면으로 가는 버튼 액션 리스너---------------------------------
		btGoSeat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//남은 좌석이 있을시 조건에 들어가 실행
				if(purchasedSeatNum < scrTotalSeat) {
					
					int seatCnt;
					
					// 선택한 상연관 seat-purchased 값이 false로 잘 변경됫는지 성공여부 판단
					seatCnt = scrSeatDao.resetSeatByStno(scrTimeNum);
					
					// 해당상영시간에 구매한 좌석의 정보가 db 입력이 성공여부판단
					for (Ticket a : tckList) {
						seatCnt = seatCnt*scrSeatDao.updatePurchasedSeatByScrTime
														(a.getSeno(), scrTimeNum);
						if (seatCnt == 0) break;				
					}
					System.out.println(seatCnt);
										
					// db입력 성공시 p4로 감
					if (seatCnt > 0) goToP4();
					
					// 실패시. 5회 버튼 누를시 오류로 인해 프로그램을 강제 종료시킨다
					else {
						errorCnt++;
						System.out.println("err " + errorCnt);
						if (errorCnt == 5) {
							p3InformMsg(plCenterBorder, "시스템 오류 : \n" 
									+ "프로그램을 종료합니다. 재실행해주세요");
							System.exit(0);
						}
					}
				}
				
				else {
					p3InformMsg(plCenterBorder,"잔여 좌석이 없습니다.");
				}				
			}
		});
//	-----------------------------------------------------
		
		unVisibleBottomMenu(); 
		
		//여백설정
		plChooseTimeCenter.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		plChooseTimeEast.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
		plleftSeat.setBorder(BorderFactory.createEmptyBorder(20, 10, 30, 10));
		
		//백그라운드 설정
		plChooseTimeCenter.setBackground(Color.black);
		plChooseTimeEast.setBackground(Color.black);
		btGoSeat.setBackground(Color.red);
		plleftSeat.setBackground(Color.darkGray);
		plScrTimeMenu.setBackground(Color.darkGray);
		
		btGoSeat.setForeground(Color.white);
		btGoSeat.setFont(new Font("맑은 고딕",Font.CENTER_BASELINE, 25));
		lbScrTime.setForeground(Color.white);
		lbScrTime.setFont(new Font("맑은 고딕",Font.CENTER_BASELINE, 25));
		lbleftSeat.setForeground(Color.white);
		lbleftSeatNum.setForeground(Color.white);
		lbleftSeat.setFont(new Font("맑은 고딕",Font.CENTER_BASELINE, 25));
		lbleftSeatNum.setFont(new Font("맑은 고딕",Font.CENTER_BASELINE, 20));
		
		// 구분선1 // 맨위 구분선
		JPanel line1 = new JPanel();
		plNorthBorder.add(line1, BorderLayout.SOUTH); 
		line1.setBackground(Color.white);
		line1.setPreferredSize(new Dimension(0, 1));
			
		//구분선2
		JPanel line2 = new JPanel();
		plSouthMenu.add(line2,BorderLayout.NORTH);
		line2.setBackground(Color.darkGray);
		line2.setPreferredSize(new Dimension(0, 1));
				
		// 팬에 삽입
		add(plNorthBorder,BorderLayout.NORTH);
		//패널정보를 담은 스크롤을 c West에 넣음
		add(scrollSingle,BorderLayout.WEST);  
		add(plCenterBorder,BorderLayout.CENTER);
		
		// 창이 출력되는 위치 지정
		Dimension frameSize = getSize();
		// 스크린 사이즈를 구함
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// 스크린위치지정
		setLocation((screenSize.width - frameSize.width) / 3, (screenSize.height - frameSize.height) / 10);
		
		// 화면을 보인다.
		setSize(550,600);
		setResizable(false);
		setVisible(false);	
	}
		
	public void goToP4() {
		
		P4_SelectSeat.scrTimeNum = scrTimeNum;
		P4_SelectSeat.scrTotalColSeat = scrTotalColSeat;
		System.out.println(scrTotalColSeat);
		P4_SelectSeat p4 = new P4_SelectSeat(this, "좌석선택", true);
		p4.setVisible(true);
		P4_SelectSeat.PeopleNum = 0;
		
		if(completePayment) 
			setVisible(false);
	}
	
	// 1. 날짜 선택 이벤트---------------------------------
	public class CalendarEvent extends MouseAdapter {
		
		@Override 
		public void mouseClicked(MouseEvent e) { 
			super.mouseClicked(e);
			
			btGoSeat.setVisible(false);
			lbleftSeatNum.setText("");	
			
			//1.1 날짜 클릭시 database 연결 중요 날짜 데이터를 반환한다.
			for (int i = 0; i < lbDay.length; i++) {
				
				
				if (e.getSource() == lbDay[i]) {
					
					if (i < difDate) {
						i = previousDate;
						p3InformMsg(plCenterBorder,"개봉일이 아닙니다.");
					}
					else 
						previousDate = i;
					
					System.out.println("sel " + i);
					
					lbToday.setText(reserveDate[i].showDate());
					lbDay[i].setOpaque(true);
					lbDay[i].setForeground(Color.white);
					lbDay[i].setBackground(Color.red);

					chooseDate[i] = true;

					reserveDateInt = reserveDate[i].showDateToInteger();

					System.out.println(reserveDateInt);
					System.out.println(dm.showDateAndTime(dm.today));

					setScrTimeList();
				}

				else {
					Calendar date = reserveDate[i].dateToCal();

					lbDay[i].setOpaque(false);
					lbDay[i].resetColor(lbDay[i], date);
					lbDay[i].setBackground(Color.black);

					chooseDate[i] = false;
				}

			}
		}// overClick

		@Override
		public void mouseEntered(MouseEvent e) {
			super.mouseEntered(e);
			
			for (int i = 0; i < lbDay.length; i++) {
				
				if(e.getSource() == lbDay[i]) {
					lbDay[i].setOpaque(true);
					lbDay[i].setBackground(Color.RED);
					lbDay[i].setForeground(Color.white);
				}
			}
		}//overenter

		@Override
		public void mouseExited(MouseEvent e) {
			super.mouseExited(e);

			for (int i = 0; i < lbDay.length; i++) {

				if (e.getSource() == lbDay[i]) {
					
					if (!chooseDate[i]) {
						lbDay[i].setOpaque(false);
						lbDay[i].setBackground(Color.BLACK);

						Calendar date = reserveDate[i].dateToCal();												
						lbDay[i].resetColor(lbDay[i], date);
					}
				}
			}
			
		}//overexit
	}//CalendarEv
	
	//2. 지역 선택----------------------------------
	public class LocationEvent extends MouseAdapter {
				
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);

			// 2.지역 선택----------------------------------------
			for (int i = 0; i < lbLocation.length; i++) {

				if (e.getSource() == plLocation[i]) {
					
					if (!chooseLoc[i]) {

						chooseLoc[i] = true;
						plLocation[i].setBackground(Color.red);

						locationNum = thLocList.get(i).getTlNo();
						locationName = thLocList.get(i).getLocaction();
						theaterList = theaterDao.selectTheaterByLoc(locationNum);
						System.out.println(locationNum);

						unVisibleBottomMenu();
						setCbTheater();
					}
				}

				else {
					chooseLoc[i] = false;
					plLocation[i].setBackground(Color.darkGray);
				}
			} // for
		}// click

		@Override
		public void mouseEntered(MouseEvent e) {
			super.mouseEntered(e);

			for (int i = 0; i < plLocation.length; i++) {

				if (e.getSource() == plLocation[i]) {
					plLocation[i].setBackground(Color.red);
				}
			} // for
		}// enter

		@Override
		public void mouseExited(MouseEvent e) {

			super.mouseExited(e);

			for (int i = 0; i < plLocation.length; i++) {

				if (e.getSource() == plLocation[i]) {
					if (!chooseLoc[i]) {
						plLocation[i].setBackground(Color.darkGray);
					}
				}
			} // for
		}// exit
	}// locEv
		
	//3. 극장 영화관 선택하는 화면 콤보 박스-----------------------------------
	public class TheaterCBoxEvent implements ActionListener {

		@Override //3. 극장 영화관 선택하는 화면 콤보 박스
		public void actionPerformed(ActionEvent e) {

			unVisibleBottomMenu();

			// 해당 지점에 개봉하는 극장관을 정보를 얻는다. 1관,2관등등
			int idx = cbTheater.getSelectedIndex() - 1;

			if (idx >= 0) {
				theaterNum = theaterList.get(idx).getTno();
				thName = theaterList.get(idx).getTname();
				System.out.println(theaterNum);
				plScreenMenu.setVisible(true);
			} else {
				theaterNum = 0;
			}

			scrList = scrDao.selectScrByTheaterAndFilm(filmNum, theaterNum);

			lbScreen = new JLabel[scrList.size()];
			plScreen = new JPanel[scrList.size()];
			chooseScreen = new boolean[scrList.size()];

			plScreenMenu.removeAll();
			
			for (int i = 0; i < scrList.size(); i++) {
					setScreenBox(i);
			}
		}
		
		private void setScreenBox(int i) {
			
			lbScreen[i] = new JLabel(scrList.get(i).getSname(), JLabel.CENTER);
			plScreen[i] = new JPanel();

			plScreen[i].add(lbScreen[i]);
			plScreenMenu.add(plScreen[i]);
			
			// 셋팅
			lbScreen[i].setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
			lbScreen[i].setForeground(Color.white);

			plScreen[i].addMouseListener(new ScreenEvent());
			plScreen[i].setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
			plScreen[i].setBackground(Color.darkGray);
		}

	}// 3
		
	// 4. 상영관 스크린관 선택시 나타나는 이벤트
	public class ScreenEvent extends MouseAdapter {

		@Override 	// 4.1.상영관 선택시 무엇할지 결정
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);

			// 4.1.상영관 선택시 무엇할지 결정
			for (int i = 0; i < plScreen.length; i++) {

				if (e.getSource() == plScreen[i]) {
					
					if (!chooseScreen[i]) {
						//4.1.1 상영관 선택시 나타나는 이벤트
						chooseScreen[i] = true;
						plScreen[i].setBackground(Color.red);
						
						scrNum = scrList.get(i).getSno();
						scrName = scrList.get(i).getSname();
						scrPeriod = scrList.get(i).getPeriod();
						scrTotalSeat = scrList.get(i).getTotalSeat();
						scrTotalColSeat = scrList.get(i).getTotalColSeat();
						
						if(scrPeriod == 0) scrPeriod = unlimtedPeriod;
						
						System.out.println(scrNum);
						System.out.println(scrPeriod);
						
						visibleBottomMenu();
						
						//4.1.2. 선택시 상영시간 셋팅 동작
						setScrTimeList();
						
					} else { // 토글 기능
						chooseScreen[i] = false;
						plScreen[i].setBackground(Color.darkGray);
						plScrTimeMenu.setVisible(false);
						plChooseTimeCenter.remove(scScrTime);
						btGoSeat.setVisible(false); // 버튼
						plleftSeat.setVisible(false);// 좌석
					}
					
				}
				else {
					chooseScreen[i] = false;
					plScreen[i].setBackground(Color.darkGray);
				}
			} // for
		}// click

		@Override
		public void mouseEntered(MouseEvent e) {
			super.mouseEntered(e);

			for (int i = 0; i < plScreen.length; i++) {

				if (e.getSource() == plScreen[i]) {
					plScreen[i].setBackground(Color.red);
				}
			} // for
		}// enter

		@Override
		public void mouseExited(MouseEvent e) {

			super.mouseExited(e);

			for (int i = 0; i < plScreen.length; i++) {

				if (e.getSource() == plScreen[i]) {
					if (!chooseScreen[i]) {
						plScreen[i].setBackground(Color.darkGray);
					}
				}
			} // for
		}// exit
	}// thEv
	
	private void setScrTimeList() {
		// 상영관 선택시 상영시간 셋팅 동작
		scrTimeList = scrTimeDao.selectScrTimeByScreen(scrNum);
		model.removeAllElements();
			
		if (scrPeriod >= reserveDateInt) {
			
			DateManager dm = new DateManager();
			int todayTimePlus10 = dm.todayTimeToIntPlus10();
			System.out.println(todayTimePlus10);

			for (int j = 0; j < scrTimeList.size(); j++) {

				if (chooseDate[0]) { // today

					// 상영시간 10분 전에는 예약못하게 시간을 추가하지 않음
					if (dm.timeToInteger(scrTimeList.get(j).getSttime()) >= todayTimePlus10) {
						model.addElement(scrTimeList.get(j).getSttime());
					}
				} else
					model.addElement(scrTimeList.get(j).getSttime());
			}
		}
		
		ltScrTime.setModel(model);		
	}
	
	// 5. 상영시간 list 선택시 나타나는 이벤트
	public class ScTimeEvent extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			super.mouseClicked(arg0);
			
			if (ltScrTime.getSelectedValue() == null) {
				btGoSeat.setVisible(false);
			}
			
			else {
				btGoSeat.setVisible(true);
				
				scrTime = ltScrTime.getSelectedValue();
				
				scrTimeNum = scrTimeDao.getStnoByScrTimeAndSno(scrTime, scrNum);
				System.out.println(scrTimeNum);
				
				long start = System.currentTimeMillis();
				
				// 티켓 정보에서 좌석 구매정보 반환
				tckList = tckDao.selectTicketByStnoAndScrdate(scrTimeNum, reserveDateInt);
				purchasedSeatNum = tckList.size();
				
				long end = System.currentTimeMillis();
								
				System.out.println("purchasedSeats" + (end - start));
				
				lbleftSeatNum.setText(purchasedSeatNum + " / " + scrTotalSeat);
			}
		}
			
		@Override
		public void mouseEntered(MouseEvent e) {
			super.mouseEntered(e);
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			super.mouseExited(e);
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
	private void setCbTheater() {
		
		cbTheater.removeAllItems();
		cbTheater.addItem("극장을 선택해주세요");

		for(Theater a : theaterList) {
			cbTheater.addItem(a.getTname());
		}
	}
	
	private void visibleBottomMenu() {

		// 중단메뉴 콤보박스 선택시 나타나야할 메뉴
		plScrTimeMenu.setVisible(true); // 상영시간메뉴가 중요 나중에 사라질 메뉴

		// 상영관에 맞게 lsit menu 추가
		plChooseTimeCenter.add(scScrTime, BorderLayout.CENTER); // 나중에 추가해야함
		plleftSeat.setVisible(true);// 좌석
		btGoSeat.setVisible(false); // 버튼
		lbleftSeatNum.setText("");
	}
	
	private void unVisibleBottomMenu() {
		
		plScreenMenu.setVisible(false);
		plScrTimeMenu.setVisible(false);
		plChooseTimeCenter.remove(scScrTime);
		btGoSeat.setVisible(false); // 버튼
		plleftSeat.setVisible(false);// 좌석
	}
	
	public void p3InformMsg(Component Component, String error) {
		UIManager.put("OptionPane.messageFont", new Font("맑은 고딕", Font.CENTER_BASELINE, 13));
		UIManager.put("OptionPane.buttonFont", new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);

		JOptionPane.showMessageDialog(Component, error, "Inform", JOptionPane.INFORMATION_MESSAGE);		
	}
	
	public static void main(String[] args) {
		
//		new P3_SelectTheater();
		
//		DateManager dm = new DateManager();
//		Calendar date = Calendar.getInstance();
				
//		System.out.println(dm.showDateTime(date));
		
	}
	
	public class Day extends JLabel {

		public void resetColor(JLabel label, Calendar date) {

			switch ((date.get(Calendar.DAY_OF_WEEK) - 1) % 7 + 1) {

			case Calendar.SUNDAY:
				setForeground(Color.red);
				break;
			case Calendar.MONDAY:
				setForeground(Color.white);
				break;
			case Calendar.TUESDAY:
				setForeground(Color.white);
				break;
			case Calendar.WEDNESDAY:
				setForeground(Color.white);
				break;
			case Calendar.THURSDAY:
				setForeground(Color.white);
				break;
			case Calendar.FRIDAY:
				setForeground(Color.white);
				break;
			case Calendar.SATURDAY:
				setForeground(Color.cyan);
				break;

			}
		}// colorReset

	}// day
	
	public class DayOfWeek extends JLabel {
				
		DayOfWeek(int dayOfWeek) { // 요일을 문자로 변환
			
			switch((dayOfWeek-1)%7+1) {
			
			case Calendar.SUNDAY :
				setText("일");
				setForeground(Color.red);
				break;
			case Calendar.MONDAY :
				setText("월");
				setForeground(Color.white);
				break;
			case Calendar.TUESDAY :
				setText("화");
				setForeground(Color.white);
				break;
			case Calendar.WEDNESDAY :
				setText("수");
				setForeground(Color.white);
				break;
			case Calendar.THURSDAY :
				setText("목");
				setForeground(Color.white);
				break;
			case Calendar.FRIDAY:
				setText("금");
				setForeground(Color.white);
				break;
			case Calendar.SATURDAY:
				setText("토");
				setForeground(Color.cyan);
				break;
			}
		}
	}//Dayofweek
		
	private void dayReset() {

	}
	
	private void locReset() {

	}
	
	private void Reset() {

	}

}//selecth
