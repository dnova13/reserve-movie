package reserve;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class P0A_AddMenu extends JPanel {

	FilmDao flDao = new FilmDao();
	TheaterLocationDao tLocDao = new TheaterLocationDao();
	TheaterDao theaterDao = new TheaterDao();
	ScreenDao scrDao = new ScreenDao();
	ScreenTimeDao scrTimeDao = new ScreenTimeDao();
	ScreenSeatDao scrSeatDao = new ScreenSeatDao();

	ArrayList<Film> filmList = flDao.selectFilm();
	ArrayList<Integer> filmNumes = new ArrayList<>();
	ArrayList<TheaterLocation> tLocList = tLocDao.selectTheaterLoc();
	ArrayList<Theater> theaterList;
	ArrayList<Screen> screenList;
	ArrayList<ScreenTime> scrTimeList;
	ArrayList<ScreenSeat> scrseatList;
	
	int tbCnt = 0;
	int filmNum;
	int filmOrder;
	int releaseDate;
	int locationNum;
	int theaterNum;
	int totalSeatNum;
	int totalColSeatNum;
	int colseatNum;
	int screenNum;
	int scrTimeNum;
	
	boolean checkDupScreen = false;
	boolean checkDupTime = false;
	
	String filmRate = "";
	String reYear;
	String reMonth="";
	String reDay="";
	String filmTitle;
	String diretor;
	String runningTime;
	String locName = "";
	String theatherName = "";
	String screenName = "";
	String scrHourNum = "";
	String scrMinNum = "";
	String scrTime;
	
	String[] filmRates = {"선택","전체","12","15","19"};
	String[] Month = { "선택", "01", "02", "03", "04", "05", "06","07","08","09","10","11","12" };
	String[] Day = { "선택","01","02","03","04","05","06","07","08", "09", "10", "11", "12",
					"13", "14","15", "16", "17", "18", "19", "20", "21","22","23","24","25"
					,"26","27","28","29","30","31"};
	String[] location = new String[tLocList.size() + 1];
	String[] screen = {"선택", "1관","2관","3관","4관","5관","6관","7관","8관","9관","10관",
					"11관","12관","13관","14관","15관","16관","17관","18관","19관","20관",
					"21관","22관","23관","24관","25관","26관","27관","28관","29관","30관"
					};
	String[] totalSeat = { "선택", "100", "110", "120", "130", "140", "150","160","170","180","190","200" };
	String[] totalColSeat = { "선택", "10","11","12","13","14","15","16"};
	String[] scrHour = { "선택","00","01","02","03","04","05","06","07","08", "09", "10", "11", "12", "13", "14",
						"15", "16", "17", "18", "19", "20", "21","22","23"};
	String[] scrMin = { "선택", "00", "10", "20", "30", "40", "50" };
	
	JTabbedPane tbAddMenu = new JTabbedPane(JTabbedPane.LEFT);
	
	//1.영화추가 메뉴 패널 설정
	JPanel plAddFilm= new JPanel();
	JPanel plAddFilmNorth = new JPanel();
	JPanel plAddFilmNNorth = new JPanel();
	JPanel plAddFilmNCenter = new JPanel();
	JPanel plAddFilmNCNorth = new JPanel();
	JPanel plAddFilmNCSouth = new JPanel();
	JPanel plAddFilmNSouth = new JPanel();
	
	FilmTable plFilmTable = new FilmTable();
	LocationTable plLocTable = new LocationTable();
	TheatherTable plTheatherTable = new TheatherTable();
	ScreenTable plScrTable = new ScreenTable();
	ScrTimeTable plScrTimeTable = new ScrTimeTable();

	JLabel lbFilmNum = new JLabel("영화번호", JLabel.CENTER);
	JLabel lbFilmTitle = new JLabel("영화제목", JLabel.CENTER);
	JLabel lbDirector = new JLabel("감독", JLabel.CENTER);
	JLabel lbRunningTime = new JLabel("상영시간", JLabel.CENTER);
	JLabel lbMin = new JLabel("분", JLabel.CENTER);
	JLabel lbFilmRate = new JLabel("영화등급", JLabel.CENTER);
	JLabel lbReDate = new JLabel("개봉일", JLabel.CENTER);
	JLabel lbReYear = new JLabel("년", JLabel.CENTER);
	JLabel lbReMonth = new JLabel("월", JLabel.CENTER);
	JLabel lbReDay = new JLabel("일", JLabel.CENTER);
	
	JComboBox<String> cbFilmRate = new JComboBox(filmRates);
	JComboBox<String> cbReMonth = new JComboBox<>(Month);
	JComboBox<String> cbReDay = new JComboBox<>(Day);
		
	JTextField txFilmNum = new JTextField(4);
	JTextField txFilmTitle = new JTextField(16);
	JTextField txDirector = new JTextField(16);
	JTextField txRunningTime = new JTextField(3);
	JTextField txReYear = new JTextField(4);

	JButton btAddFilm = new JButton("추가");
	JButton btCheckFilm = new JButton("조회");
	
	// 지역추가 메뉴
	JPanel plLocNorth = new JPanel();
	JPanel plTheaterNorth = new JPanel();
	JPanel plScreenNorth = new JPanel();
	JPanel plScreenNNorth = new JPanel();
	JPanel plScreenNCenter = new JPanel();
	JPanel plScreenNSouth = new JPanel();
	JPanel plScrTimeNorth = new JPanel();
	JPanel plScrTimeNNorth = new JPanel();
	JPanel plScrTimeNSouth = new JPanel();

	JPanel plLoc = new JPanel();
	JPanel plTheater = new JPanel();
	JPanel plScreen = new JPanel();
	JPanel plScrTime = new JPanel();
	
	JLabel lbLocNum = new JLabel("지역번호", JLabel.CENTER);
	JLabel lbLoc[] = new JLabel[4];
	JLabel lbTheater[] = new JLabel[3];
	JLabel lbFilm1 = new JLabel("영화", JLabel.CENTER);
	JLabel lbFilm2 = new JLabel("영화", JLabel.CENTER);
	JLabel lbTotalSeat = new JLabel("좌석개수", JLabel.CENTER);
	JLabel lbTotalColSeat = new JLabel("좌석열의 개수", JLabel.CENTER);
	JLabel lbScreen[] = new JLabel[2];
	JLabel lbScrTime = new JLabel("상영시간", JLabel.CENTER);
	JLabel lbScrHour = new JLabel("시", JLabel.CENTER);
	JLabel lbScrMin = new JLabel("분", JLabel.CENTER);

	JComboBox<String>[] cbLoc = new JComboBox[3];
	JComboBox<String>[] cbTheater = new JComboBox[2];
	JComboBox<String> cbFilm1 = new JComboBox<>();
	JComboBox<String> cbFilm2 = new JComboBox<>();
	JComboBox<String> cbTotalSeat = new JComboBox<>(totalSeat);
	JComboBox<String> cbTotalColSeat = new JComboBox<>(totalColSeat);
	JComboBox<String> cbScreen1 = new JComboBox<>(screen);
	JComboBox<String> cbScreen2 = new JComboBox<>();
	JComboBox<String> cbScrHour = new JComboBox<>(scrHour);
	JComboBox<String> cbScrMin = new JComboBox<>(scrMin);

	JTextField txLoc = new JTextField(6);
	JTextField txLocNum = new JTextField(4);
	JTextField txTheater = new JTextField(5);

	JButton btLoc = new JButton("추가");
	JButton btTheater = new JButton("추가");
	JButton btScreen = new JButton("추가");
	JButton btScrTime = new JButton("추가");
	
	JButton btCheckLoc = new JButton("조회");
	JButton btCheckTheater = new JButton("조회");
	JButton btCheckScreen = new JButton("조회");
	JButton btCheckScrTime = new JButton("조회");

	public P0A_AddMenu() {

		setLayout(new BorderLayout());

		// 0.1 array 라벨 설정
		Font gothic = new Font("맑은 고딕", Font.CENTER_BASELINE, 15);

		for (int i = 0; i < lbLoc.length; i++) {

			lbLoc[i] = new JLabel("지역", JLabel.CENTER);
			lbLoc[i].setFont(gothic);

			if (i < 3) {
				lbTheater[i] = new JLabel("극장", JLabel.CENTER);
				lbTheater[i].setFont(gothic);
				lbTheater[i].setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
			}

			if (i < 2) {
				lbScreen[i] = new JLabel("상영관", JLabel.CENTER);
				lbScreen[i].setFont(gothic);
			}
		}

		// 0.2.1 지역 콤보박스 설정
		location[0] = "선택";

		for (int i = 1; i < tLocList.size() + 1; i++) {

			location[i] = tLocList.get(i - 1).getLocaction();
		}

		for (int i = 0; i < cbLoc.length; i++) {

			cbLoc[i] = new JComboBox<>(location);

			cbLoc[i].addActionListener(new CbLocItemEv());

			cbLoc[i].setBackground(Color.white);
			cbLoc[i].setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 13));

			comboBoxSetAlignCenter(cbLoc[i]);
		}

		// 0.2.2 극장 콤보박스 설정
		for (int i = 0; i < cbTheater.length; i++) {

			cbTheater[i] = new JComboBox<>();
			initializeComboBox(cbTheater[i]);

			cbTheater[i].addActionListener(new CbTheaterItemEv());
		}

		// 0.2.3 시트개수 콤보박스 설정 / 리스트가 고정되서 set
		cbTotalSeat.addActionListener(new CbSingleItemEv());
		setComboBox(cbTotalSeat);
		
		// 0.2.4 시트개수 콤보박스 설정 / 리스트가 고정되서 set
		cbTotalColSeat.addActionListener(new CbSingleItemEv());
		setComboBox(cbTotalColSeat);

		// 0.2.5. 영화 콤보박스 설정 / 리스트가 변하므로 초기화
		cbFilm1.addActionListener(new CbSingleItemEv());
		initializeComboBox(cbFilm1);
		
		cbFilm2.addActionListener(new CbSingleItemEv());
		initializeComboBox(cbFilm2);
		
		// 0.2.6.2 상영관 추가메뉴에서 상영관 콤보박스 설정
		cbScreen1.addActionListener(new CbSingleItemEv());
		setComboBox(cbScreen1);
		
		// 0.2.6.2 상영시간 추가메뉴에서 상영관 콤보박스 설정
		cbScreen2.addActionListener(new CbSingleItemEv());
		initializeComboBox(cbScreen2);

		// 0.2.7 상영시간 시간 설정
		cbScrHour.addActionListener(new CbSingleItemEv());
		setComboBox(cbScrHour);

		// 0.2.8 상영시간 분 설정
		cbScrMin.addActionListener(new CbSingleItemEv());
		setComboBox(cbScrMin);

		// 0.3 tab 설정 추가
		tbAddMenu.addTab("영화 추가", plAddFilm);
		tbAddMenu.addTab("지역 추가", plLoc);
		tbAddMenu.addTab("극장 추가", plTheater);
		tbAddMenu.addTab("상영관 추가", plScreen);
		tbAddMenu.addTab("상영시간 추가", plScrTime);

		tbAddMenu.setBackground(Color.white);
		tbAddMenu.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 14));

		tbAddMenu.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
					
				resetMenu(tbAddMenu.getSelectedIndex());
			}
		});
		
		Font font = new Font("맑은 고딕", Font.CENTER_BASELINE, 15);
		
		// 1. 극장추가 메뉴 / locdao insert
		plAddFilm.setLayout(new BorderLayout());
		plAddFilm.add(plAddFilmNorth, BorderLayout.NORTH);
		plAddFilm.setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 15));

		plAddFilmNorth.setLayout(new BorderLayout());
		plAddFilmNorth.setBorder(BorderFactory.createTitledBorder(null, "영화", TitledBorder.LEFT,
				TitledBorder.DEFAULT_POSITION, gothic(15)));

		plAddFilmNorth.add(plAddFilmNNorth, BorderLayout.NORTH);
		plAddFilmNorth.add(plAddFilmNCenter, BorderLayout.CENTER);
		plAddFilmNorth.add(plAddFilmNSouth, BorderLayout.SOUTH);

		// 1.1 제 1 메뉴
		plAddFilmNNorth.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));

		plAddFilmNNorth.add(lbFilmNum);
		plAddFilmNNorth.add(txFilmNum);
		plAddFilmNNorth.add(lbFilmTitle);
		plAddFilmNNorth.add(txFilmTitle);

		lbFilmNum.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		lbFilmTitle.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));

		txFilmNum.setPreferredSize(new Dimension(0, 30));
		txFilmTitle.setPreferredSize(new Dimension(0, 30));

		lbFilmNum.setFont(gothic(15));
		lbFilmTitle.setFont(gothic(15));

		txFilmNum.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 13));
		txFilmTitle.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 13));

		plAddFilm.setBackground(Color.white);
		plAddFilmNorth.setBackground(Color.white);
		plAddFilmNNorth.setBackground(Color.white);

		// 1.2. 제 2 메뉴
		plAddFilmNCenter.setLayout(new BorderLayout());
		plAddFilmNCenter.add(plAddFilmNCNorth, BorderLayout.PAGE_START);
		plAddFilmNCenter.add(plAddFilmNCSouth, BorderLayout.PAGE_END);

		plAddFilmNCNorth.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));

		plAddFilmNCNorth.add(lbDirector);
		plAddFilmNCNorth.add(txDirector);
		plAddFilmNCNorth.add(lbRunningTime);
		plAddFilmNCNorth.add(txRunningTime);
		plAddFilmNCNorth.add(lbMin);
		
		limitText(txRunningTime, 3);
		
		lbDirector.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		lbRunningTime.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));

		txDirector.setPreferredSize(new Dimension(0, 30));
		txRunningTime.setPreferredSize(new Dimension(0, 30));

		lbDirector.setFont(gothic(15));
		lbRunningTime.setFont(gothic(15));
		lbMin.setFont(gothic(15));

		txRunningTime.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 13));
		txDirector.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 13));

		plAddFilmNCNorth.setBackground(Color.white);

		// 1.3. 제3 메뉴
		plAddFilmNCSouth.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));

		plAddFilmNCSouth.add(lbReDate);
		plAddFilmNCSouth.add(txReYear);
		plAddFilmNCSouth.add(lbReYear);
		plAddFilmNCSouth.add(cbReMonth);
		plAddFilmNCSouth.add(lbReMonth);
		plAddFilmNCSouth.add(cbReDay);
		plAddFilmNCSouth.add(lbReDay);
		
		limitText(txReYear, 4);
		
		lbReDate.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 5));
		cbReMonth.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		cbReDay.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

		txReYear.setPreferredSize(new Dimension(0, 30));

		lbReDate.setFont(gothic(15));
		lbReYear.setFont(gothic(15));
		lbReMonth.setFont(gothic(15));
		lbReDay.setFont(gothic(15));

		txReYear.setFont(gothic(13));
		cbReMonth.setFont(gothic(13));
		cbReDay.setFont(gothic(13));

		comboBoxSetAlignCenter(cbReMonth);
		comboBoxSetAlignCenter(cbReDay);

		plAddFilmNCSouth.setBackground(Color.white);
		cbReMonth.setBackground(Color.white);
		cbReDay.setBackground(Color.white);
		
		//1. 영화추가 액션리스너
		btAddFilm.addActionListener(new ActionEv());
		btCheckFilm.addActionListener(new ActionEv());

		cbFilmRate.addActionListener(new ActionEv());
		cbReMonth.addActionListener(new ActionEv());
		cbReDay.addActionListener(new ActionEv());
		
		// 1.4 제 4 메뉴
		plAddFilmNSouth.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));

		plAddFilmNSouth.add(lbFilmRate);
		plAddFilmNSouth.add(cbFilmRate);
		plAddFilmNSouth.add(Box.createHorizontalStrut(10));
		plAddFilmNSouth.add(btAddFilm);
		plAddFilmNSouth.add(btCheckFilm);

		lbFilmRate.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
//		cbFilmRate.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		
		comboBoxSetAlignCenter(cbFilmRate);
		
		lbFilmRate.setFont(gothic(15));
		cbFilmRate.setFont(gothic(13));
		btAddFilm.setFont(gothic(13));
		btCheckFilm.setFont(gothic(13));

		plAddFilmNSouth.setBackground(Color.white);
		cbFilmRate.setBackground(Color.white);
		
		plAddFilm.add(plFilmTable, BorderLayout.CENTER);
		
		// 2. 지역추가 메뉴 / locdao insert
		plLoc.setLayout(new BorderLayout());
		plLoc.setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 15));
		
		plLocNorth.setLayout(new FlowLayout(FlowLayout.CENTER, 7, 15));
		
		plLocNorth.add(lbLocNum);
		plLocNorth.add(txLocNum);
		plLocNorth.add(lbLoc[0]);
		plLocNorth.add(txLoc);
		plLocNorth.add(btLoc);
		plLocNorth.add(btCheckLoc);
		
		plLocNorth.setBorder(BorderFactory.createTitledBorder(null, "지역",
					TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, font));
	
		//2. 지역추가 버튼 액션리스너
		btLoc.addActionListener(new AddBtEvent());
		btCheckLoc.addActionListener(new AddBtEvent());
		
		lbLoc[0].setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		txLocNum.setPreferredSize(new Dimension(0, 30));
		txLoc.setPreferredSize(new Dimension(0, 30));
		
		lbLocNum.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		txLocNum.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 13));
		txLoc.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 13));
		btLoc.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 13));
		btCheckLoc.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 13));
		
		limitText(txLocNum, 3);
		
		plLoc.setBackground(Color.white);
		plLocNorth.setBackground(Color.white);

		plLoc.add(plLocNorth, BorderLayout.NORTH);
		
		//2.2 테이블 추가
		plLoc.add(plLocTable, BorderLayout.CENTER);

		// 3.극장 추가/ theater insert ----------------------------------------
		plTheater.setLayout(new BorderLayout());
		plTheater.setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 15));

		plTheaterNorth.setLayout(new FlowLayout(FlowLayout.CENTER, 7, 15));
		plTheaterNorth.setBorder(BorderFactory.createTitledBorder(null, "극장",
				TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, font));

		plTheaterNorth.add(lbLoc[1]);
		plTheaterNorth.add(cbLoc[0]);
		plTheaterNorth.add(lbTheater[0]);
		plTheaterNorth.add(txTheater);
		plTheaterNorth.add(btTheater);
		plTheaterNorth.add(btCheckTheater);

		btTheater.addActionListener(new AddBtEvent());
		btCheckTheater.addActionListener(new AddBtEvent());

		txTheater.setPreferredSize(new Dimension(0, 30));

		txTheater.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 14));
		btTheater.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 13));
		btCheckTheater.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 13));

		plTheater.setBackground(Color.white);
		plTheaterNorth.setBackground(Color.white);

		plTheater.add(plTheaterNorth, BorderLayout.NORTH);
		// 테이블 추가
		plTheater.add(plTheatherTable, BorderLayout.CENTER);

		// 4. 상영관 추가/ screen insert -------------------------------
		plScreen.setLayout(new BorderLayout());
		plScreen.add(plScreenNorth, BorderLayout.NORTH);
		plScreen.setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 15));

		plScreenNorth.setLayout(new BorderLayout());
		plScreenNorth.setBorder(BorderFactory.createTitledBorder(null, "상영관",
				TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, font));

		plScreenNorth.add(plScreenNNorth, BorderLayout.NORTH);
		plScreenNorth.add(plScreenNCenter, BorderLayout.CENTER);
		plScreenNorth.add(plScreenNSouth, BorderLayout.SOUTH);

		// 4.1 상단메뉴
		plScreenNNorth.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));

		plScreenNNorth.add(lbLoc[2]);
		plScreenNNorth.add(cbLoc[1]);
		plScreenNNorth.add(lbTheater[1]);
		plScreenNNorth.add(cbTheater[0]);
		plScreenNNorth.add(lbScreen[0]);
		plScreenNNorth.add(cbScreen1);
		
		lbLoc[2].setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		lbScreen[0].setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		
		//4.2 중단메뉴1
		plScreenNCenter.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));
		
		plScreenNCenter.add(lbTotalSeat);
		plScreenNCenter.add(cbTotalSeat);
		plScreenNCenter.add(lbTotalColSeat);
		plScreenNCenter.add(cbTotalColSeat);
		
		lbTotalSeat.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		lbTotalColSeat.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
				
		// 4.3 중단 메뉴2
		JPanel plScreenNSNorth = new JPanel();
		JPanel plScreenNSSouth = new JPanel();
		
		plScreenNSouth.setLayout(new BorderLayout());
		plScreenNSouth.add(plScreenNSNorth, BorderLayout.PAGE_START);
		plScreenNSouth.add(plScreenNSSouth, BorderLayout.PAGE_END);
		
		plScreenNSNorth.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));
		
		plScreenNSNorth.add(lbFilm1);
		plScreenNSNorth.add(cbFilm1);

		lbFilm1.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		
		lbTotalSeat.setFont(gothic);
		lbTotalColSeat.setFont(gothic);
		lbFilm1.setFont(gothic);
		
		// 4.4 하단 메뉴		
		plScreenNSSouth.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));
		
		plScreenNSSouth.add(btScreen);
		plScreenNSSouth.add(btCheckScreen);

		btScreen.addActionListener(new AddBtEvent());
		btCheckScreen.addActionListener(new AddBtEvent());
		
		plScreenNSSouth.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
		
		btScreen.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 13));
		btCheckScreen.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 13));
		
		plScreen.setBackground(Color.white);
		plScreenNorth.setBackground(Color.white);
		plScreenNNorth.setBackground(Color.white);
		plScreenNCenter.setBackground(Color.white);
		plScreenNSouth.setBackground(Color.white);
		plScreenNSNorth.setBackground(Color.white);
		plScreenNSSouth.setBackground(Color.white);
		
		// 테이블 추가
		plScreen.add(plScrTable, BorderLayout.CENTER);

		// 5. 상영시간 추가/ srTime & srSeat insert ------------------
		JPanel plScrTimeNCenter = new JPanel();
		
		plScrTime.setLayout(new BorderLayout());

		plScrTime.add(plScrTimeNorth, BorderLayout.NORTH);
		plScrTime.setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 15));

		plScrTimeNorth.setLayout(new BorderLayout());
		plScrTimeNorth.setBorder(BorderFactory.createTitledBorder(null, "상영시간",
				TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, font));
		
		plScrTimeNorth.add(plScrTimeNNorth, BorderLayout.NORTH);
		plScrTimeNorth.add(plScrTimeNCenter, BorderLayout.CENTER);
		plScrTimeNorth.add(plScrTimeNSouth, BorderLayout.SOUTH);

		// 5.1 상단메뉴
		plScrTimeNNorth.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));
		plScrTimeNNorth.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

		plScrTimeNNorth.add(lbLoc[3]);
		plScrTimeNNorth.add(cbLoc[2]);
		plScrTimeNNorth.add(lbTheater[2]);
		plScrTimeNNorth.add(cbTheater[1]);
		plScrTimeNNorth.add(lbScreen[1]);
		plScrTimeNNorth.add(cbScreen2);
		
		lbScreen[1].setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		
		//5.2. 중단메뉴
		plScrTimeNCenter.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));
		plScrTimeNCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		
		plScrTimeNCenter.add(lbFilm2);
		plScrTimeNCenter.add(cbFilm2);
		
		lbFilm2.setFont(gothic);
		
		plScrTimeNCenter.setBackground(Color.white);
		
		//5.3 하단메뉴
		plScrTimeNSouth.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));

		plScrTimeNSouth.add(lbScrTime);
		plScrTimeNSouth.add(cbScrHour);
		plScrTimeNSouth.add(lbScrHour);
		plScrTimeNSouth.add(cbScrMin);
		plScrTimeNSouth.add(lbScrMin);
		plScrTimeNSouth.add(btScrTime);
		plScrTimeNSouth.add(btCheckScrTime);

		btScrTime.addActionListener(new AddBtEvent());
		btCheckScrTime.addActionListener(new AddBtEvent());

		lbScrTime.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 5));
		lbScrHour.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
		lbScrMin.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 7));

		lbScrTime.setFont(gothic);
		lbScrHour.setFont(gothic);
		lbScrMin.setFont(gothic);
		btScrTime.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 13));
		btCheckScrTime.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 13));
		
		plScrTime.setBackground(Color.white);
		plScrTimeNorth.setBackground(Color.white);
		plScrTimeNNorth.setBackground(Color.white);
		plScrTimeNSouth.setBackground(Color.white);
		
		//테이블 추가
		plScrTime.add(plScrTimeTable, BorderLayout.CENTER);
		
		// 마무리
		setBackground(Color.white);
		add(tbAddMenu, BorderLayout.CENTER);
	}// addtheater.Productor
	
	public class ScrTimeTable extends JPanel {
		
		DateManager dm = new DateManager();
		
		JPanel plTable = new JPanel();
		
		ArrayList<ScreenTime> scrTimeList;
		
		DefaultTableModel model;
		JTable tbSearch = new JTable(model);
		JScrollPane scrollTable = new JScrollPane(tbSearch);
		DefaultTableCellRenderer dtcr;
		
		String colNames[] = {"상영시간","영화", "상영관"};
		String[][] rowData;
		
		public ScrTimeTable() {
			
			setLayout(new BorderLayout());
						
			add(plTable);
			plTable.add(scrollTable);
						
			plTable.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
			plTable.setVisible(false);
			
			// 열 셀높이 설정
			tbSearch.getTableHeader().setPreferredSize(new Dimension(1000, 30));
			tbSearch.setRowHeight(25);
			
//			tbSearch.getTableHeader().setReorderingAllowed(false); //순서 변경불가
//			tbSearch.getTableHeader().setResizingAllowed(false); //크기 고정
			tbSearch.getTableHeader().resizeAndRepaint();
			
//			tbSearch.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			
			//스크롤 크기설정
			scrollTable.setPreferredSize(new Dimension(460, 280));
			
			tbSearch.getTableHeader().setFont(gothic(14));
			tbSearch.setFont(gothic(12));
			scrollTable.setFont(gothic(12));	
			
			tbSearch.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.black));
			tbSearch.setBorder(BorderFactory.createLineBorder(Color.black));
			scrollTable.setBorder(BorderFactory.createLineBorder(Color.white));
			
			setBackground(Color.white);
			plTable.setBackground(Color.white);
			tbSearch.setBackground(Color.white);
			scrollTable.getViewport().setBackground(Color.white);
			scrollTable.setBackground(Color.white);
		}
		
		public void setTable() {
			
			tbCnt = 1;
			
			scrTimeList = scrTimeDao.getScrTimesInScreen(screenName, theaterNum);
			rowData = new String[scrTimeList.size()][colNames.length];
			
			for (int i = 0; i < scrTimeList.size(); i++) {
				
				Screen sr = scrDao.selectScreenByScrNum(scrTimeList.get(i).getSno());
				int fno = sr.getFno();
				
				rowData[i][0] = scrTimeList.get(i).getSttime();
				rowData[i][1] = flDao.selectFilmByFno(fno).getFilmTitle(); 
				rowData[i][2] = screenName;
			}
			
			// 셀 수정 못하게 막음
			model = new DefaultTableModel(rowData, colNames) {

				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			
			tbSearch.setModel(model);			
		
			// 열 셀너비, 정렬, 설정 
			dtcr = new DefaultTableCellRenderer(); // 디폴트테이블셀렌더러를 생성
			dtcr.setHorizontalAlignment(SwingConstants.CENTER);
			
			TableColumnModel tcm = tbSearch.getColumnModel();
			tcm.getColumn(1).setPreferredWidth(180);
			
			for(int i = 0 ; i < tcm.getColumnCount() ; i++){
				tcm.getColumn(i).setCellRenderer(dtcr);
			}
						
			plTable.setVisible(true);
		}	
	}
	
	public class ScreenTable extends JPanel {
		
		DateManager dm = new DateManager();
		
		JPanel plTable = new JPanel();
		
		ArrayList<Screen> scrList;
		
		DefaultTableModel model;
		JTable tbSearch = new JTable(model);
		JScrollPane scrollTable = new JScrollPane(tbSearch);
		DefaultTableCellRenderer dtcr;
		
		String colNames[] = {"상영관","영화","좌석개수","좌석열", "극장"};
		String[][] rowData;
		
		public ScreenTable() {
			
			setLayout(new BorderLayout());
						
			add(plTable);
			plTable.add(scrollTable);
						
			plTable.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
			plTable.setVisible(false);
			
			// 열 셀높이 설정
			tbSearch.getTableHeader().setPreferredSize(new Dimension(1000, 30));
			tbSearch.setRowHeight(25);
			
//			tbSearch.getTableHeader().setReorderingAllowed(false); //순서 변경불가
//			tbSearch.getTableHeader().setResizingAllowed(false); //크기 고정
			tbSearch.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			
			//스크롤 크기설정
			scrollTable.setPreferredSize(new Dimension(460, 250));
			
			tbSearch.getTableHeader().setFont(gothic(14));
			tbSearch.setFont(gothic(12));
			scrollTable.setFont(gothic(12));	
			
			tbSearch.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.black));
			tbSearch.setBorder(BorderFactory.createLineBorder(Color.black));
			scrollTable.setBorder(BorderFactory.createLineBorder(Color.white));
			
			setBackground(Color.white);
			plTable.setBackground(Color.white);
			tbSearch.setBackground(Color.white);
			scrollTable.getViewport().setBackground(Color.white);
			scrollTable.setBackground(Color.white);
		}
		
		public void setTable(int tno) {
			
			tbCnt = 1;
			
			scrList = scrDao.selectScreenByTheater(tno);
			rowData = new String[scrList.size()][colNames.length];
			
			for (int i = 0; i < scrList.size(); i++) {
				
				rowData[i][0] = scrList.get(i).getSname();
				rowData[i][1] = flDao.getFilmTitle(scrList.get(i).getFno());
				rowData[i][2] = scrList.get(i).getTotalSeat()+"";
				rowData[i][3] = scrList.get(i).getTotalColSeat()+"";
				rowData[i][4] = theatherName;
			}
			
			// 셀 수정 못하게 막음
			model = new DefaultTableModel(rowData, colNames) {

				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			
			tbSearch.setModel(model);			
		
			// 열 셀너비, 정렬, 설정 
			dtcr = new DefaultTableCellRenderer(); // 디폴트테이블셀렌더러를 생성
			dtcr.setHorizontalAlignment(SwingConstants.CENTER);
			
			TableColumnModel tcm = tbSearch.getColumnModel();
			
			tcm.getColumn(0).setPreferredWidth(60);
			tcm.getColumn(1).setPreferredWidth(181);
			tcm.getColumn(3).setPreferredWidth(60);
			tcm.getColumn(4).setPreferredWidth(65);
			
			for(int i = 0 ; i < tcm.getColumnCount() ; i++){
				tcm.getColumn(i).setCellRenderer(dtcr);
			}
						
			plTable.setVisible(true);
		}	
	}
	
	public class TheatherTable extends JPanel {
		
		DateManager dm = new DateManager();
		
		JPanel plTable = new JPanel();
		
		ArrayList<Theater> thList;
		
		DefaultTableModel model;
		JTable tbSearch = new JTable(model);
		JScrollPane scrollTable = new JScrollPane(tbSearch);
		DefaultTableCellRenderer dtcr;
		
		String colNames[] = {"극장","지역"};
		String[][] rowData;
		
		public TheatherTable() {
			
			setLayout(new BorderLayout());
						
			add(plTable);
			plTable.add(scrollTable);
						
			plTable.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
			plTable.setVisible(false);
			
			// 열 셀높이 설정
			tbSearch.getTableHeader().setPreferredSize(new Dimension(1000, 30));
			tbSearch.setRowHeight(25);
			
//			tbSearch.getTableHeader().setReorderingAllowed(false); //순서 변경불가
//			tbSearch.getTableHeader().setResizingAllowed(false); //크기 고정
//			tbSearch.getTableHeader().resizeAndRepaint();
			
//			tbSearch.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			
			//스크롤 크기설정
			scrollTable.setPreferredSize(new Dimension(460, 320));
			
			tbSearch.getTableHeader().setFont(gothic(14));
			tbSearch.setFont(gothic(12));
			scrollTable.setFont(gothic(12));	
			
			tbSearch.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.black));
			tbSearch.setBorder(BorderFactory.createLineBorder(Color.black));
			scrollTable.setBorder(BorderFactory.createLineBorder(Color.white));
			
			setBackground(Color.white);
			plTable.setBackground(Color.white);
			tbSearch.setBackground(Color.white);
			scrollTable.getViewport().setBackground(Color.white);
			scrollTable.setBackground(Color.white);
		}
		
		public void setTable(int locNum) {
			
			tbCnt = 1;
			
			thList = theaterDao.selectTheaterByLoc(locNum);
			rowData = new String[thList.size()][colNames.length];
			
			for (int i = 0; i < thList.size(); i++) {
				
				rowData[i][0] = thList.get(i).getTname();
				rowData[i][1] = locName;
			}
			
			// 셀 수정 못하게 막음
			model = new DefaultTableModel(rowData, colNames) {

				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			
			tbSearch.setModel(model);			
		
			// 열 셀너비, 정렬, 설정 
			dtcr = new DefaultTableCellRenderer(); // 디폴트테이블셀렌더러를 생성
			dtcr.setHorizontalAlignment(SwingConstants.CENTER);
			
			TableColumnModel tcm = tbSearch.getColumnModel();
						
			for(int i = 0 ; i < tcm.getColumnCount() ; i++){
				tcm.getColumn(i).setCellRenderer(dtcr);
			}
						
			plTable.setVisible(true);
		}	
	}

	public class LocationTable extends JPanel {
		
		DateManager dm = new DateManager();
		
		JPanel plTable = new JPanel();
		
		ArrayList<TheaterLocation> locList;
		
		DefaultTableModel model;
		JTable tbSearch = new JTable(model);
		JScrollPane scrollTable = new JScrollPane(tbSearch);
		DefaultTableCellRenderer dtcr;
		
		String colNames[] = {"지역번호", "지역"};
		String[][] rowData;
		
		public LocationTable() {
			
			setLayout(new BorderLayout());
						
			add(plTable);
			plTable.add(scrollTable);
						
			plTable.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
			plTable.setVisible(false);
			
			// 열 셀높이 설정
			tbSearch.getTableHeader().setPreferredSize(new Dimension(1000, 30));
			tbSearch.setRowHeight(25);
			
//			tbSearch.getTableHeader().setReorderingAllowed(false); //순서 변경불가
//			tbSearch.getTableHeader().setResizingAllowed(false); //크기 고정
			tbSearch.getTableHeader().resizeAndRepaint();
			
//			tbSearch.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			
			//스크롤 크기설정
			scrollTable.setPreferredSize(new Dimension(460, 320));
			
			tbSearch.getTableHeader().setFont(gothic(14));
			tbSearch.setFont(gothic(12));
			scrollTable.setFont(gothic(12));	
			
			tbSearch.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.black));
			tbSearch.setBorder(BorderFactory.createLineBorder(Color.black));
			scrollTable.setBorder(BorderFactory.createLineBorder(Color.white));
			
			setBackground(Color.white);
			plTable.setBackground(Color.white);
			tbSearch.setBackground(Color.white);
			scrollTable.getViewport().setBackground(Color.white);
			scrollTable.setBackground(Color.white);
		}
		
		public void setTable() {
			
			tbCnt = 1;
			
			locList = tLocDao.selectTheaterLoc();
			rowData = new String[locList.size()][colNames.length];
			
			for (int i = 0; i < locList.size(); i++) {
				
				rowData[i][0] = locList.get(i).getLocNum()+ "";
				rowData[i][1] = locList.get(i).getLocaction();
			}
			
			// 셀 수정 못하게 막음
			model = new DefaultTableModel(rowData, colNames) {

				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			
			tbSearch.setModel(model);			
		
			// 열 셀너비, 정렬, 설정 
			dtcr = new DefaultTableCellRenderer(); // 디폴트테이블셀렌더러를 생성
			dtcr.setHorizontalAlignment(SwingConstants.CENTER);
			
			TableColumnModel tcm = tbSearch.getColumnModel();
			
			for(int i = 0 ; i < tcm.getColumnCount() ; i++){
				tcm.getColumn(i).setCellRenderer(dtcr);
			}
						
			plTable.setVisible(true);
		}	
	}
		
	public class FilmTable extends JPanel {
		
		DateManager dm = new DateManager();
		
		JPanel plTable = new JPanel();
		
		ArrayList<Film> filmList;
		
		DefaultTableModel model;
		JTable tbSearch = new JTable(model);
		JScrollPane scrollTable = new JScrollPane(tbSearch);
		DefaultTableCellRenderer dtcr;
		
		String colNames[] = {"","영화번호", "영화제목","감독","상영시간","개봉일","등급"};
		String[][] rowData;
		
		public FilmTable() {
			
			setLayout(new BorderLayout());
						
			add(plTable);
			plTable.add(scrollTable);
						
			plTable.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
//			plTable.setLayout(new BorderLayout());
			plTable.setVisible(false);
			
			// 열 셀높이 설정
			tbSearch.getTableHeader().setPreferredSize(new Dimension(1000, 30));
			tbSearch.setRowHeight(25);
			
//			tbSearch.getTableHeader().setReorderingAllowed(false); //순서 변경불가
//			tbSearch.getTableHeader().setResizingAllowed(false); //크기 고정
//			tbSearch.getTableHeader().resizeAndRepaint();
			
			tbSearch.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			
			//스크롤 크기설정
			scrollTable.setPreferredSize(new Dimension(460, 250));
			
			tbSearch.getTableHeader().setFont(gothic(14));
			tbSearch.setFont(gothic(12));
			scrollTable.setFont(gothic(12));	
			
			tbSearch.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.black));
			tbSearch.setBorder(BorderFactory.createLineBorder(Color.black));
			scrollTable.setBorder(BorderFactory.createLineBorder(Color.white));
			
			setBackground(Color.white);
			plTable.setBackground(Color.white);
			tbSearch.setBackground(Color.white);
			scrollTable.getViewport().setBackground(Color.white);
			scrollTable.setBackground(Color.white);
		}
		
		public void setTable() {
			
			filmList = flDao.selectFilm();
			rowData = new String[filmList.size()][colNames.length];
			
			for (int i = 0; i < filmList.size(); i++) {
				
				rowData[i][0] = filmList.get(i).getFilmOrder() + "";
				rowData[i][1] = filmList.get(i).getFilmNo() + "";
				rowData[i][2] = filmList.get(i).getFilmTitle();
				rowData[i][3] = filmList.get(i).getDirector();
				rowData[i][4] = filmList.get(i).getRunningTime();
				rowData[i][5] = dm.IntegerToDateSlash(filmList.get(i).getReleaseDate());
				rowData[i][6] = filmList.get(i).getFilmRate() + "";
			}
			
			// 셀 수정 못하게 막음
			model = new DefaultTableModel(rowData, colNames) {

				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			
			tbSearch.setModel(model);			
		
			// 열 셀너비, 정렬, 설정 
			dtcr = new DefaultTableCellRenderer(); // 디폴트테이블셀렌더러를 생성
			dtcr.setHorizontalAlignment(SwingConstants.CENTER);
			
			TableColumnModel tcm = tbSearch.getColumnModel();
			
			tcm.getColumn(0).setPreferredWidth(30);
			tcm.getColumn(2).setPreferredWidth(120);
			tcm.getColumn(3).setPreferredWidth(90);
			tcm.getColumn(5).setPreferredWidth(90);
			tcm.getColumn(6).setPreferredWidth(40);
			
			for(int i = 0 ; i < tcm.getColumnCount() ; i++){
				tcm.getColumn(i).setCellRenderer(dtcr);
			}
						
			plTable.setVisible(true);
		}	
	}
	
	public class ActionEv implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			//1. 영화 추가 메뉴
			if (e.getSource() == cbReMonth) {
					
				if (cbReMonth.getSelectedIndex() > 0) {
					reMonth = (String) cbReMonth.getSelectedItem();
					System.out.println(reMonth);
				} else
					reMonth = "";
			}
			
			else if (e.getSource() == cbReDay) {
				
				if (cbReDay.getSelectedIndex() > 0) {
					reDay = (String) cbReDay.getSelectedItem();
					System.out.println(reDay);
				} else
					reDay = "";
			}
			
			else if (e.getSource() == cbFilmRate) {
				
				if (cbFilmRate.getSelectedIndex() > 0) {
					
					if (cbFilmRate.getSelectedIndex() == 1) 		
						filmRate = "0";
					else 
						filmRate = (String)cbFilmRate.getSelectedItem();
					
					System.out.println(filmRate);
				} else
					filmRate = "";
			}
			
			if (e.getSource() == btAddFilm) {
				
				DateManager dm =  new DateManager();
				
				boolean checkInsert = false;
				String sFilmNum = txFilmNum.getText();
				String sRunTime =  txRunningTime.getText();
				reYear = txReYear.getText();
				diretor = txDirector.getText();
				filmTitle = txFilmTitle.getText();

				filmList = flDao.selectFilm();	
				
				int checkEmpty = sFilmNum.length()*sRunTime.length()*reYear.length()
								*diretor.length()*filmTitle.length()*reDay.length()
								*reMonth.length()*filmRate.length();
								
				if (checkEmpty == 0) {
					p0InformrMsg(plAddFilmNorth, "빈칸없이 데이터를 입력해주세요");
				}
				else if (!checkInt(sFilmNum)) {
					p0InformrMsg(plAddFilmNorth, "영화번호는 0이상의 숫자만 입력하세요");
					txFilmNum.setText("");
				}
				else if (flDao.checkDupFilm(Integer.parseInt(sFilmNum))) {
					p0InformrMsg(plAddFilmNorth, "중복된 번호입니다.");
					txFilmNum.setText("");
				}
				else if (!checkInt(sRunTime)) {
					p0InformrMsg(plAddFilmNorth, "상영시간은 0이상의 숫자만 입력하세요");
					txRunningTime.setText("");
				}
				else if (!checkInt(reYear)) {
					txReYear.setText("");
					p0InformrMsg(plAddFilmNorth, "년도는 0이상의 4자리 숫자로 입력하세요\n"
								+ "예) " + dm.todayYear);
				}
				else if (!(reYear.length() == 4)) {
					txReYear.setText("");
					p0InformrMsg(plAddFilmNorth, "년도는 4자리 YYYY로 입력해주세요\n "
								+ "예) " + dm.todayYear);
				} else
					checkInsert = true;
				
				if (checkInsert) {
					
					filmNum = Integer.parseInt(sFilmNum);
 					runningTime = sRunTime + "분"; 
					releaseDate =  Integer.parseInt(reYear + reMonth + reDay);
					 
					System.out.println(filmList.size());
					
					int maxOrder = filmList.size()+1;
					
//					//순서 체크,
//					for (int i = 0; i < filmList.size(); i++) {
//						
//						if (i+1 != filmList.get(i).getFilmOrder()){
//							maxOrder = i+1;
//							break;
//						}
//					}
					
					filmOrder = maxOrder;
					
					System.out.println("maxor " + maxOrder);
					
					int checkIn = flDao.insertFilm(filmNum, filmTitle, diretor, releaseDate, 
										runningTime,Integer.parseInt(filmRate), filmOrder);
					
					if (checkIn > 0) {
						
						String loadFile = "images/movies/"+filmNum+".jpg";
						String saveFile1 = "images/resize_movies/"+filmNum+".jpg";
						String saveFile2 = "images/thumb/"+filmNum+".jpg";
						
						try {
							Thumbnail.createImage(310,443, loadFile, saveFile1, 10);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						
						try {
							Thumbnail.createImage(150,200, loadFile, saveFile2, 10);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						
						p0InformrMsg(plAddFilmNorth, "성공적으로 입력되었습니다.");
						resetMenu(0);
						plFilmTable.setTable();
						
					} else {
						p0InformrMsg(plAddFilmNorth, "데이터 입력이 실패하였습니다.");
					}
					
				}
			}
			
			if (e.getSource() == btCheckFilm) {
				tbCnt++;

				if (tbCnt % 2 == 1) { 
					plFilmTable.setTable();
				} else
					plFilmTable.plTable.setVisible(false);
			}
		}
	}

	// 1. 버튼 추가 이벤트
	public class AddBtEvent implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			// 1.2. 지역추가 버튼 이벤트
			if (e.getSource() == btLoc) {
				
				if (txLocNum.getText().length() == 0 || txLoc.getText().length() == 0) {
					p0InformrMsg(plLocNorth, "데이터를 입력해주세요");
				} 
				
				else if (!checkInt(txLocNum.getText())) {
					p0InformrMsg(plLocNorth, "번호는 0이상의 숫자를 입력해주세요");
				}
				
				else {
					
					int locnum = Integer.parseInt(txLocNum.getText());
					String Loc = txLoc.getText();
					
					if (tLocDao.insertTheaterLoc(locnum,Loc) > 0) {
						
						plLocTable.setTable();
						p0InformrMsg(plLocNorth, "성공적으로 입력되었습니다.");					
						txLocNum.setText("");
						resetLoc(0);
						
					} else {
						p0InformrMsg(plLocNorth, "이름이 중복되거나 시스템 오류로 인해 \n" + "데이터 입력이 실패하였습니다.");
					}
				}

				txLoc.setText("");
				System.out.println(tLocList.get(4).getTlNo());
			}
			
			if (e.getSource() == btCheckLoc) {

				tbCnt++;
				if (tbCnt % 2 == 1) { 
					plLocTable.setTable();
				} else
					plLocTable.plTable.setVisible(false);
			}
			
			// 1.3 극장 추가 이벤트
			if (e.getSource() == btTheater) {

				if (txTheater.getText().length() > 0 && locationNum > 0) {

					if (theaterDao.insertTheater(txTheater.getText(), locationNum) > 0) {
						plTheatherTable.setTable(locationNum);
						p0InformrMsg(plTheaterNorth, "성공적으로 입력되었습니다.");

					} else {
						p0InformrMsg(plTheaterNorth, "이름이 중복되거나 시스템 오류로 인해 \n"
											+ "데이터 입력이 실패하였습니다.");
					}
					txTheater.setText("");
				} else
					p0InformrMsg(plTheaterNorth, "데이터를 입력하거나 박스를 선택해주세요");
			}
			
			if (e.getSource() == btCheckTheater) {
				
				if (locationNum > 0) {
				
				tbCnt++;
				if (tbCnt % 2 == 1) { 
					plTheatherTable.setTable(locationNum);
				} else
					plTheatherTable.plTable.setVisible(false);
				}
				else 
					p0InformrMsg(plTheaterNorth, "지역박스를 선택해주세요");
			}

			// 1.4 상영관 추가 이벤트
			if (e.getSource() == btScreen) {
				
				if (totalSeatNum == 0 || totalColSeatNum == 0 || filmNum == 0 
						|| theaterNum == 0 || screenName.length() == 0) {
					p0InformrMsg(plScreenNorth, "데이터를 입력하거나 박스를 선택해주세요");
				}
				else if (checkDupScreen) {
					p0InformrMsg(plScreenNorth, "중복된 상영관입니다.");
				
				} else {
					if (scrDao.insertScreen(screenName, totalSeatNum, totalColSeatNum, filmNum, theaterNum) > 0) {
						plScrTable.setTable(theaterNum);
						p0InformrMsg(plScreenNorth, "성공적으로 입력되었습니다.");
						cbScreen1.setSelectedIndex(0);
					} 
					
					else {
						p0InformrMsg(plScreenNorth, "데이터 입력이 실패하였습니다.");
					}
				}
			}
			
			if (e.getSource() == btCheckScreen) {
				
				if (theaterNum > 0) {
					tbCnt++;
					if (tbCnt % 2 == 1) {
						plScrTable.setTable(theaterNum);
					} else
						plScrTable.plTable.setVisible(false);
				}
				else 
					p0InformrMsg(plScreenNorth, "극장박스를 선택해주세요");
			}

			// 1.5 상영시간 추가 이벤트
			if (e.getSource() == btScrTime) {
				
				System.out.println("checkDup " + checkDupTime);
				
				if (theaterNum == 0 || screenNum == 0 || scrHourNum.length() == 0 
						|| scrMinNum.length() == 0|| filmNum == 0) {
					p0InformrMsg(plScrTimeNorth, "데이터를 입력하거나 박스를 선택해주세요");
				}
				
				else if (checkDupTime) {
					p0InformrMsg(plScrTimeNorth, "중복된 시간입니다.");
				}
				
				else {
					
					if (scrSeatDao.insertScrTimeAndAllSeat(scrTime, screenNum, 
								           totalSeatNum, totalColSeatNum)) {
						
						p0InformrMsg(plScrTimeNorth, "성공적으로 입력되었습니다.");
						plScrTimeTable.setTable();
						cbScrMin.setSelectedIndex(0);
						tbCnt = 1;
					} else {
						p0InformrMsg(plScrTimeNorth, "데이터 입력이 실패하였습니다.");
					}
					
					scrTimeNum = scrTimeDao.getStnoByScrTimeAndSno(scrTime, screenNum);
					System.out.println(scrTime);
					System.out.println(scrTimeNum);
				}
			}
			
			if (e.getSource() == btCheckScrTime) {
				
				if (cbScreen2.getSelectedIndex() > 0) {
				
					tbCnt++;
					if (tbCnt % 2 == 1) {
						plScrTimeTable.setTable();
					} else
						plScrTimeTable.plTable.setVisible(false);
				} else 
					p0InformrMsg(plScrTimeNorth, "상영관 박스를 선택해주세요");
			}
		}
	}// AddEvent

	// 2. 콤보박스 추가 이벤트
	// 2.1 지역 콤보박스
	public class CbLocItemEv implements ActionListener {

		@Override
	public void actionPerformed(ActionEvent e) {

			// 지역콤보박스 인덱스 번호 리턴
			for (int i = 0; i < cbLoc.length; i++) {
				if (e.getSource() == cbLoc[i]) {
					
					tbCnt = 0;
					
					// 선택한 지역 번호 반환
					int num = cbLoc[i].getSelectedIndex() - 1;

					if (num >= 0) {
						locationNum = tLocList.get(num).getTlNo();
						locName = (String) cbLoc[i].getSelectedItem();
						System.out.println(locationNum);
					} else {
						locationNum = 0;
						locName = "";
					}

					// 선택한 지역에 해당하는 극장들 출력
					if (i > 0) {
						theaterList = theaterDao.selectTheaterByLoc(locationNum);

						for (int a = 0; a < cbTheater.length; a++) {

							cbTheater[a].removeAllItems();
							cbTheater[a].addItem("선택");

							for (int b = 0; b < theaterList.size(); b++) {

								cbTheater[a].addItem(theaterList.get(b).getTname());
							}
						}
					}
				} // e.get
			} // for
		}// override
	}// CbLocItemEv

	// 2.2 극장 영화관 콤보박스 이벤트
	public class CbTheaterItemEv implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			// 극장콤보박스 인덱스 번호 리턴
			for (int i = 0; i < cbTheater.length; i++) {
				if (e.getSource() == cbTheater[i]) {
					
					tbCnt = 0;
					
					// 선택한 극장 번호 반환
					int num = cbTheater[i].getSelectedIndex() - 1;

					if (num >= 0) {
						theaterNum = theaterList.get(num).getTno();
						theatherName = (String) cbTheater[i].getSelectedItem();
						System.out.println(theaterNum);
					} else {
						theaterNum = 0;
						theatherName = "";
						initializeComboBox(cbFilm1);
						cbFilm1.setPreferredSize(null);
					}
					
					// 상영관 추가 메뉴에서 선택시 영화 목록 정보 콤보박스로 반환
					if (theaterNum > 0 && i == 0) {

						cbFilm1.removeAllItems();
						cbFilm1.addItem("  선택");
						
						comboBoxSetAlignLeft(cbFilm1);
						
						filmList = flDao.selectFilm();
						
						for (int b = 0; b < filmList.size(); b++) {

							cbFilm1.addItem("  "+filmList.get(b).getFilmTitle()+ " ");
						}
						
						System.out.println(cbFilm1.getPreferredSize());
						
						if (cbFilm1.getPreferredSize().getWidth() >= 370) {
							cbFilm1.setPreferredSize(new Dimension(370, 28));
							
						} else {
							cbFilm1.setPreferredSize(null);
						}
						
						Object comp = cbFilm1.getUI().getAccessibleChild(cbFilm1, 0);
						
					    JPopupMenu popup = (JPopupMenu) comp;
						JScrollPane scrollPane = (JScrollPane) popup.getComponent(0);

						scrollPane.setHorizontalScrollBar(new JScrollBar(JScrollBar.HORIZONTAL));
						scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);	
					} // theater >0
					
					//  상영시간 추가 메뉴에서 선택시 스크린 상영관 정보 반환
					if (i > 0) {
						
						ArrayList<String> scrNames = scrDao.getScrNameNotDup(theaterNum);
		
						cbScreen2.removeAllItems();
						cbScreen2.addItem("선택");
						
						for (int b = 0; b < scrNames.size(); b++) {
							cbScreen2.addItem(scrNames.get(b));
						}					
						
					}
				} // e.get
			} // for
		}// override
	}// CbTheaterItemEv

	// 2.3 배열이 아닌 콤보박스 이벤트
	public class CbSingleItemEv implements ActionListener {

		@Override
	public void actionPerformed(ActionEvent e) {

			// 2.3.1.1 좌석개수 시트 콤보박스 이벤트
			if (e.getSource() == cbTotalSeat) {
								
				// 시트 개수 반환
				try {
					totalSeatNum = Integer.parseInt((String) cbTotalSeat.getSelectedItem());
					System.out.println(totalSeatNum);
				} catch (Exception e2) {
					totalSeatNum = 0;
				}
			}
			
			// 2.3.1.2 좌석열의 개수 시트 콤보박스 이벤트
			else if (e.getSource() == cbTotalColSeat) {
								
				// 시트 개수 반환
				try {
					totalColSeatNum = Integer.parseInt((String) cbTotalColSeat.getSelectedItem());
					System.out.println(totalColSeatNum);
				} catch (Exception e2) {
					totalColSeatNum = 0;
				}
			}
			
			// 2.3.2 영화 콤보박스 이벤트
			else if (e.getSource() == cbFilm1) {
								
				int num = cbFilm1.getSelectedIndex() - 1;
				
				if (num >= 0) {
					
					int Num = filmList.get(num).getFilmNo();
					
					boolean a = scrDao.checkDupScrNameAndFilmNum(theaterNum, Num, screenName); 
					System.out.println(a);
					
					if(a) {
						filmNum = 0;
						checkDupScreen = true;
						p0InformrMsg(plScreenNorth, "중복된 영화입니다.");
						cbFilm1.setSelectedIndex(0);
						
					}
					else {
						filmNum = Num;
						checkDupScreen = false;
					}
					System.out.println("film: " + filmNum);
					
				} else
					filmNum = 0;
			}
						
			//2.3.3.1 상영관 추가 스크린 콤보박스 이벤트
			else if (e.getSource() == cbScreen1) {
								
				String sname = (String) cbScreen1.getSelectedItem();
				
				if (sname.equals(cbScreen1.getItemAt(0))) {
					screenName = "";
					System.out.println("scName " + screenName);
					
				} else {
					
					System.out.println("snam : " + sname);
					System.out.println(filmNum);
					System.out.println(theaterNum);
					

					Screen scr = scrDao.checkDupScrNameAndGetScreenBySnameAndTno(sname, theaterNum);
					
					if (scr == null) { // 상영관이 아닐 때
						
						cbTotalColSeat.setEnabled(true);
						cbTotalSeat.setEnabled(true);
						
					} else { // 상영관이 중복이 일 때
						
						cbTotalColSeat.setSelectedItem(scr.getTotalColSeat()+"");
						cbTotalSeat.setSelectedItem(scr.getTotalSeat()+"");
						
						cbTotalColSeat.setEnabled(false);
						cbTotalSeat.setEnabled(false);
												
						System.out.println("totalCol " + totalColSeatNum);
						System.out.println("totalSeat " + totalSeatNum);
					}
					
					
//					cbTotalColSeat.setEditable(false);
//					cbTotalSeat.d
					
					boolean a = scrDao.checkDupScrNameAndFilmNum(theaterNum, filmNum, sname); 
					System.out.println(a);
					
					if(a) {
						screenName = "";
						checkDupScreen = true;
						p0InformrMsg(plScreenNorth, "중복된 영화입니다.");
						cbScreen1.setSelectedIndex(0);
					}
					else {
						screenName = sname;
						checkDupScreen = false;
					}
					System.out.println(screenName);
				}
			}

			// 2.3.3.2 상영시간추가 스크린 콤보박스 이벤트
			else if (e.getSource() == cbScreen2) {
					
				tbCnt = 0;
				
				int num = cbScreen2.getSelectedIndex();
				
				if (num > 0) {
					
					screenName = (String) cbScreen2.getSelectedItem();
					screenList = scrDao.selectScreenBySnameAndTno(screenName, theaterNum);
					
					ArrayList<Integer> Numes = new ArrayList<>();
					
					System.out.println(screenName);
					
					String str = "  선택";

					cbFilm2.removeAllItems();
					cbFilm2.addItem(str);
					
					comboBoxSetAlignLeft(cbFilm2);
					
					for (Screen a: screenList) {
						
						Numes.add(a.getFno());
	
						Film film = flDao.selectFilmByFno(a.getFno());
						
						if (film != null) 
							cbFilm2.addItem("  "+film.getFilmTitle()+ " ");	
					}
					
					System.out.println(cbFilm2.getPreferredSize());

					if (cbFilm2.getPreferredSize().getWidth() >= 370) {
						cbFilm2.setPreferredSize(new Dimension(370, 28));
						
					} else {
						cbFilm2.setPreferredSize(null);
					}
					
					Object comp = cbFilm2.getUI().getAccessibleChild(cbFilm2, 0);
					
				    JPopupMenu popup = (JPopupMenu) comp;
					JScrollPane scrollPane = (JScrollPane) popup.getComponent(0);

					scrollPane.setHorizontalScrollBar(new JScrollBar(JScrollBar.HORIZONTAL));
					scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					
					filmNumes = Numes;
				} else {
					screenNum = 0;
					resetComboBox(cbFilm2);
					comboBoxSetAlignCenter(cbFilm2);
				}
			}
			
			// 2.3.3. 상영시간 영화 콤보박스
			else if (e.getSource() == cbFilm2) {
								
				int num = cbFilm2.getSelectedIndex() - 1;

				if (num >= 0) {
					
					System.out.println();
					filmNum = filmNumes.get(num);
					
					Screen scr = scrDao.selectScreenBySname_Tno_fno(screenName, theaterNum, filmNum);
					
					System.out.println(filmNum);
					
					screenNum = scr.getSno();
					totalSeatNum = scr.getTotalSeat();
					totalColSeatNum = scr.getTotalColSeat();
//					
					System.out.println(screenNum);
					System.out.println(totalSeatNum);
					System.out.println(totalColSeatNum);
					
					cbScrHour.setSelectedIndex(0);
					cbScrMin.setSelectedIndex(0);
//					
				} else
					filmNum = 0;
			}
			
			// 2.3.4 상영시간 시간 콤보박스 이벤트
			else if (e.getSource() == cbScrHour) {
								
				String hour = (String) cbScrHour.getSelectedItem();
					
				cbScrMin.setSelectedIndex(0);
				
				if (hour.equals(cbScrHour.getItemAt(0))) {
					scrHourNum = "";
				} else 
					scrHourNum = hour;
			}

			// 2.3.5 상영시간 분 콤보박스 이벤트
			else if (e.getSource() == cbScrMin) {
								
				String min = (String) cbScrMin.getSelectedItem();

				if (min.equals(cbScrMin.getItemAt(0))) {
					scrMinNum = "";
				} else {
					scrMinNum = min;
					scrTime = scrHourNum + ":" + scrMinNum;
					
					if (cbFilm2.getSelectedIndex() > 0) {

						// 시간 중복 체크
						ArrayList<Integer> scrNumList = new ArrayList<>();

						for (int a : filmNumes) {
							scrNumList.add(scrDao.selectScreenBySname_Tno_fno(screenName, theaterNum, a).getSno());
						}

						int scrTimeSize = scrTimeDao.checkScrTimeCount(scrNumList);

						System.out.println("scrTimeSize" + scrTimeSize);

						if (scrTimeDao.checkDupScrTime(scrNumList, scrTime) && scrTimeSize > 0) {
							scrTime = "";
							p0InformrMsg(plScrTimeNorth, "중복된 시간입니다.");
							// cbScrMin.setSelectedIndex(0);
							checkDupTime = true;

						} else {
							checkDupTime = false;
						}
						System.out.println("checkDupTime " + checkDupTime);
					}
				}
			}
		}// override
	}// CbSingleItemEv

	
	public void limitText(JTextField JText, int limitwords) {

		JText.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

				String str = JText.getText();

				if (str.length() > limitwords-1) {
					String newstr = str.substring(0, limitwords-1);
					JText.setText(newstr);
				}
			}

		});
		JText.setHorizontalAlignment(JTextField.CENTER);
	}
	
	private void setComboBox(JComboBox<String> cb) {

		cb.setBackground(Color.white);
		cb.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 13));

		comboBoxSetAlignCenter(cb);
	}

	private void resetComboBox(JComboBox<String> cb) {

		String a = "선택";

		cb.removeAllItems();
		cb.addItem(a);
	}

	private void initializeComboBox(JComboBox<String> cb) {

		resetComboBox(cb);
		cb.setBackground(Color.white);
		cb.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 13));

		comboBoxSetAlignCenter(cb);
	}

	protected void resetAll() {

		txLoc.setText("");
		txTheater.setText("");

		for (int i = 0; i < cbLoc.length; i++) {

			cbLoc[i].setSelectedIndex(0);

			if (i < 2)
				cbTheater[i].setSelectedIndex(0);
		}

		resetComboBox(cbFilm1);
		// cbFilm.setSelectedIndex(0);
		cbTotalSeat.setSelectedIndex(0);
		cbScreen1.setSelectedIndex(0);
		cbScreen2.setSelectedIndex(0);
		cbScrHour.setSelectedIndex(0);
		cbScrMin.setSelectedIndex(0);
	}
	
	protected void resetMenu(int i) {
		
		tbCnt = 0;
		
		if (i == 0) {
			plFilmTable.plTable.setVisible(false);
			txDirector.setText("");
			txFilmNum.setText("");
			txFilmTitle.setText("");
			txReYear.setText("");
			txRunningTime.setText("");
			
			cbReDay.setSelectedIndex(0);
			cbReMonth.setSelectedIndex(0);
			cbFilmRate.setSelectedIndex(0);
		}
		if (i == 1) {
			plLocTable.plTable.setVisible(false);
			txLoc.setText("");
			txLocNum.setText("");
		}
		if (i == 2) {
			plTheatherTable.plTable.setVisible(false);
			resetLoc(i-2);
			txTheater.setText("");
		}
		if (i == 3) {
			plScrTable.plTable.setVisible(false);
			resetLoc(i-2);
			cbTheater[i-3].setSelectedIndex(0);
			cbTotalColSeat.setSelectedIndex(0);
			cbTotalSeat.setSelectedIndex(0);
			cbScreen1.setSelectedIndex(0);
			resetComboBox(cbFilm1);
		}
		if (i == 4){
			plScrTimeTable.plTable.setVisible(false);
			resetLoc(i-2);
			cbTheater[i-3].setSelectedIndex(0);
			cbScreen2.setSelectedIndex(0);
			cbScrHour.setSelectedIndex(0);
			cbScrMin.setSelectedIndex(0);
			resetComboBox(cbFilm2);
		}
	}

	private void comboBoxSetAlignCenter(JComboBox cb) {

		cb.setRenderer(new DefaultListCellRenderer() {
			public int getHorizontalAlignment() {
				return CENTER;
			}
		});
	}
	
	private void comboBoxSetAlignLeft(JComboBox cb) {

		cb.setRenderer(new DefaultListCellRenderer() {
			public int getHorizontalAlignment() {
				return LEFT;
			}
		});
	}
	
	private boolean checkInt(String num) {
		try {

			int stringToInt = Integer.parseInt(num);
			
			if (stringToInt < 0) throw new Exception();

		} catch (Exception e2) {
			return false;
		}
		return true;
	}
	
	private Font gothic(int size) {
		return new Font("맑은 고딕", Font.CENTER_BASELINE, size);
	}
	
	public void resetLoc(int i) {

		tLocList = tLocDao.selectTheaterLoc();

		resetComboBox(cbLoc[i]);
		for (TheaterLocation a : tLocList) {
			cbLoc[i].addItem(a.getLocaction());
		}
	}
	
	private void p0InformrMsg(Component parentComponent, String error) {
		UIManager.put("OptionPane.messageFont", new Font("맑은 고딕", Font.CENTER_BASELINE, 13));
		UIManager.put("OptionPane.buttonFont", new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);

		JOptionPane.showMessageDialog(parentComponent, error, "Inform", JOptionPane.INFORMATION_MESSAGE);
	}

}
