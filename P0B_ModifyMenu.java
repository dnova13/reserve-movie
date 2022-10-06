package reserve;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import javax.naming.LimitExceededException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ComboBoxEditor;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.basic.BasicComboBoxUI.ComboBoxLayoutManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import oracle.net.aso.d;
import reserve.P0A_AddMenu.ScrTimeTable;

public class P0B_ModifyMenu extends JPanel {

	FilmDao flDao = new FilmDao();
	TheaterLocationDao tLocDao = new TheaterLocationDao();
	TheaterDao theaterDao = new TheaterDao();
	ScreenDao scrDao = new ScreenDao();
	ScreenTimeDao scrTimeDao = new ScreenTimeDao();
	ScreenSeatDao scrSeatDao = new ScreenSeatDao();

	ArrayList<Film> filmList;
	ArrayList<Integer> filmNumes = new ArrayList<>();
	ArrayList<TheaterLocation> tLocList = tLocDao.selectTheaterLoc();
	ArrayList<Theater> theaterList;
	ArrayList<Screen> screenList;
	ArrayList<ScreenTime> scrTimeList;
	ArrayList<ScreenSeat> scrseatList;

	int tbCnt = 0;
	int filmNum;
	int ModifyfilmNum;
	int filmOrder;
	int locationNum;
	int theaterNum;
	int screenNum;
	int scrTimeNum;

	String scrYear = "";
	String scrMonth = "";
	String scrDay = "";
	String screenName = "";
	String[] Month = { "선택", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
	String[] Day = { "선택", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
			"16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };
	String[] location = new String[tLocList.size() + 1];

	JTabbedPane tbModifyMenu = new JTabbedPane(JTabbedPane.LEFT);
	
	ModifyFilm plModifyFilm = new ModifyFilm();
	ModifyFilmOrder plModifyFmOrder = new ModifyFilmOrder();
	ModifyThLocation plModifyThLocation = new ModifyThLocation();
	ModifyTheater plModifyTheater = new ModifyTheater();
	ModifyFilmTitle plModifyFmTitle = new ModifyFilmTitle();
	ModifyFmPeriod plModifyFmPeriod = new ModifyFmPeriod();
	ModifyScrTime plModifyScrTime = new ModifyScrTime();

	public P0B_ModifyMenu() {

		setLayout(new BorderLayout());
		
		tbModifyMenu.addTab("영화 변경", plModifyFilm);
		tbModifyMenu.addTab("영화순서 변경", plModifyFmOrder);
		tbModifyMenu.addTab("지역 변경", plModifyThLocation);
		tbModifyMenu.addTab("극장 변경", plModifyTheater);
		tbModifyMenu.addTab("상영영화 변경", plModifyFmTitle);
		tbModifyMenu.addTab("상영기간 설정", plModifyFmPeriod);
		tbModifyMenu.addTab("상영시간 변경", plModifyScrTime);

		tbModifyMenu.setBackground(Color.white);
		tbModifyMenu.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 14));

		tbModifyMenu.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {

				resetMenu(tbModifyMenu.getSelectedIndex());
			}
		});

		setBackground(Color.white);
		add(tbModifyMenu, BorderLayout.CENTER);
	}

	public void resetMenu(int i) {
		
		if (i == 0) {
			plModifyFilm.resetFilm();
			plModifyFilm.resetmenu();
		}
		
		if (i == 1) {
			plModifyFmOrder.resetMenu();
			plModifyFmOrder.resetFilm();
		}
		else if (i == 2) {
			plModifyThLocation.resetLocList();
		}

		else if (i == 3) {
			plModifyTheater.resetMenu();
			plModifyTheater.resetLoc();
		}

		else if (i == 4) {
			plModifyFmTitle.resetMenu();
			plModifyFmTitle.resetFilm();
			plModifyFmTitle.resetLoc();
		}

		else if (i == 5) {
			plModifyFmPeriod.resetMenu();
			plModifyFmPeriod.resetLoc();
		}

		else {
			plModifyScrTime.resetMenu();
			plModifyScrTime.resetLoc();
		}
	}

	// 1. 영화 수정
	public class ModifyFilm extends JPanel {
		
		int releaseDate;
		
		String filmRate = "";
		String reYear;
		String reMonth="";
		String reDay="";
		String filmTitle;
		String diretor;
		String runningTime;
		
		String[] filmRates = {"선택","전체","12","15","19"};
		
		JPanel plAddFilm= new JPanel();
		JPanel plAddFilmNorth = new JPanel();
		JPanel plAddFilmNNorth = new JPanel();
		JPanel plAddFilmNCenter = new JPanel();
		JPanel plAddFilmNCNorth = new JPanel();
		JPanel plAddFilmNCSouth = new JPanel();
		JPanel plAddFilmNSouth = new JPanel();
		FilmTable plFilmTable = new FilmTable();
		
		JLabel lbFilm = new JLabel("영화", JLabel.CENTER);
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
		
		JComboBox<String> cbFilm = new JComboBox<>();
		JComboBox<String> cbFilmRate = new JComboBox(filmRates);
		JComboBox<String> cbReMonth = new JComboBox<>(Month);
		JComboBox<String> cbReDay = new JComboBox<>(Day);
		
		JTextField txFilmNum = new JTextField(4);
		JTextField txFilmTitle = new JTextField(16);
		JTextField txDirector = new JTextField(16);
		JTextField txRunningTime = new JTextField(3);
		JTextField txReYear = new JTextField(4);

		JButton btModifyFilm = new JButton("변경");
		JButton btCheckFilm = new JButton("조회");
		
		public ModifyFilm() {

			// 1. 영화수정 메뉴 / locdao insert
			setLayout(new BorderLayout());
			add(plAddFilmNorth, BorderLayout.NORTH);
			setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 15));

			plAddFilmNorth.setLayout(new BorderLayout());
			plAddFilmNorth.setBorder(BorderFactory.createTitledBorder(null, "영화", TitledBorder.LEFT,
					TitledBorder.DEFAULT_POSITION, gothic(15)));

			plAddFilmNorth.add(plAddFilmNNorth, BorderLayout.NORTH);
			plAddFilmNorth.add(plAddFilmNCenter, BorderLayout.CENTER);
			plAddFilmNorth.add(plAddFilmNSouth, BorderLayout.SOUTH);
			
			JPanel PlAddFilmNNNorth1 = new JPanel();
			JPanel PlAddFilmNNNorth2 = new JPanel();
			
			plAddFilmNNorth.setLayout(new BorderLayout());
			
			plAddFilmNNorth.add(PlAddFilmNNNorth1,BorderLayout.NORTH);
			plAddFilmNNorth.add(PlAddFilmNNNorth2,BorderLayout.SOUTH);
			
			// 1.1 제 1 메뉴
			PlAddFilmNNNorth1.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 7));
			PlAddFilmNNNorth1.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
			
			initializeComboBox(cbFilm);
			resetFilm();
									
			PlAddFilmNNNorth1.add(lbFilm);
			PlAddFilmNNNorth1.add(cbFilm);
			
			lbFilm.setFont(gothic(15));
			cbFilm.setFont(gothic(13));
			
			PlAddFilmNNNorth1.setBackground(Color.white);
			
			// 1.2 제 2 메뉴
			PlAddFilmNNNorth2.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 7));
			
			PlAddFilmNNNorth2.add(lbFilmNum);
			PlAddFilmNNNorth2.add(txFilmNum);
			PlAddFilmNNNorth2.add(lbFilmTitle);
			PlAddFilmNNNorth2.add(txFilmTitle);
			
			txFilmNum.setEditable(false);
			txFilmNum.setHorizontalAlignment(JTextField.CENTER);
			
			lbFilmNum.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
			lbFilmTitle.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
			
			txFilmNum.setPreferredSize(new Dimension(0, 30));
			txFilmTitle.setPreferredSize(new Dimension(0, 30));

			lbFilmNum.setFont(gothic(15));
			lbFilmTitle.setFont(gothic(15));

			txFilmNum.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 13));
			txFilmTitle.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 13));
			
			setBackground(Color.white);
			plAddFilmNorth.setBackground(Color.white);
			plAddFilmNNorth.setBackground(Color.white);
			PlAddFilmNNNorth1.setBackground(Color.white);
			PlAddFilmNNNorth2.setBackground(Color.white);

			// 1.3. 제 3 메뉴
			plAddFilmNCenter.setLayout(new BorderLayout());
			plAddFilmNCenter.add(plAddFilmNCNorth, BorderLayout.PAGE_START);
			plAddFilmNCenter.add(plAddFilmNCSouth, BorderLayout.PAGE_END);

			plAddFilmNCNorth.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 7));

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
			plAddFilmNCSouth.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 7));

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

			// 1. 영화추가 액션리스너
			btModifyFilm.addActionListener(new ActionEv());
			btCheckFilm.addActionListener(new ActionEv());
			
			cbFilm.addActionListener(new ActionEv());
			cbFilmRate.addActionListener(new ActionEv());
			cbReMonth.addActionListener(new ActionEv());
			cbReDay.addActionListener(new ActionEv());

			// 1.4 제 4 메뉴
			plAddFilmNSouth.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 7));

			plAddFilmNSouth.add(lbFilmRate);
			plAddFilmNSouth.add(cbFilmRate);
			plAddFilmNSouth.add(Box.createHorizontalStrut(10));
			plAddFilmNSouth.add(btModifyFilm);
			plAddFilmNSouth.add(btCheckFilm);

			lbFilmRate.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
			// cbFilmRate.setBorder(BorderFactory.createEmptyBorder(0, 0, 0,0));

			comboBoxSetAlignCenter(cbFilmRate);

			lbFilmRate.setFont(gothic(15));
			cbFilmRate.setFont(gothic(13));
			btModifyFilm.setFont(gothic(13));
			btCheckFilm.setFont(gothic(13));

			plAddFilmNSouth.setBackground(Color.white);
			cbFilmRate.setBackground(Color.white);

			add(plFilmTable, BorderLayout.CENTER);
			
		}
		
		public class ActionEv implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {

				//1. 영화 추가 메뉴
				if (e.getSource() == cbFilm) {

					int num = cbFilm.getSelectedIndex() - 1;

					if (num >= 0) {

						filmNum = filmList.get(num).getFilmNo();
						System.out.println(filmNum);
						
						filmTitle = filmList.get(num).getFilmTitle();
						diretor = filmList.get(num).getDirector();
						releaseDate = filmList.get(num).getReleaseDate();
						runningTime = filmList.get(num).getRunningTime();
						filmRate = filmList.get(num).getFilmRate()+"";
						if (filmRate.equals("0")) filmRate = "전체";
						
						int year = releaseDate/10000;
						String month = (releaseDate+"").substring(4, 6);
						String day = (releaseDate+"").substring(6, 8);
						
						txFilmNum.setText(filmNum+"");
						txFilmTitle.setText(filmTitle);
						txDirector.setText(diretor);						
						txRunningTime.setText(runningTime.substring(0,runningTime.length()-1));
						txReYear.setText(year +"");
						
						cbFilmRate.setSelectedItem(filmRate);
						cbReMonth.setSelectedItem(month);
						cbReDay.setSelectedItem(day);
						

					} else {
						resetmenu();
					}
				}
				
				
				else if (e.getSource() == cbReMonth) {
						
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
				
				if (e.getSource() == btModifyFilm) {
					
					DateManager dm =  new DateManager();
					
					boolean checkInsert = false;
					String sFilmNum = txFilmNum.getText();
					String sRunTime =  txRunningTime.getText();
					reYear = txReYear.getText();
					diretor = txDirector.getText();
					filmTitle = txFilmTitle.getText();
					
					int checkEmpty = sFilmNum.length()*sRunTime.length()*reYear.length()
							*diretor.length()*filmTitle.length()*reDay.length()
							*reMonth.length()*filmRate.length();
									
					if (checkEmpty == 0) {
						p0InformrMsg(plAddFilmNorth, "빈칸없이 데이터를 입력해주세요");
					}
//					else if (!checkInt(sFilmNum)) {
//						p0InformrMsg(plAddFilmNorth, "영화번호는 0이상의 숫자만 입력하세요");
//						txFilmNum.setText("");
//					}
//					else if (flDao.checkDupFilm(Integer.parseInt(sFilmNum)) && 
//										filmNum != Integer.parseInt(sFilmNum) ) {
//						p0InformrMsg(plAddFilmNorth, "중복된 번호입니다.");
//						txFilmNum.setText("");
//					}
					
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
									+ "예) " + dm.todayToInteger());
					} else
						checkInsert = true;
					
					if (checkInsert) {
						
						int modifyFilmNum = Integer.parseInt(sFilmNum);
	 					runningTime = sRunTime + "분"; 
						releaseDate =  Integer.parseInt(reYear + reMonth + reDay);
						
						System.out.println(filmNum);
						
						int checkIn = flDao.updateFilmByNum(filmNum,modifyFilmNum, filmTitle, diretor, releaseDate, 
											runningTime,Integer.parseInt(filmRate));
						
						System.out.println(checkIn);
						
						if (checkIn > 0) {
					
							String loadFile = "images/movies/"+modifyFilmNum+".jpg";
							String saveFile1 = "images/resize_movies/"+modifyFilmNum+".jpg";
							String saveFile2 = "images/thumb/"+modifyFilmNum+".jpg";
					
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
					
							p0InformrMsg(plAddFilmNorth, "성공적으로 변경되었습니다.");
							resetmenu();
							resetFilm();
//							cbFilm.setSelectedIndex(0);
							plFilmTable.setTable();
													
						} else {
							p0InformrMsg(plAddFilmNorth, "데이터 변경이 실패하였습니다./n");
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

		public class FilmTable extends JPanel {

			DateManager dm = new DateManager();

			JPanel plTable = new JPanel();

			ArrayList<Film> filmList;

			DefaultTableModel model;
			JTable tbSearch = new JTable(model);
			JScrollPane scrollTable = new JScrollPane(tbSearch);
			DefaultTableCellRenderer dtcr;

			String colNames[] = { "", "영화번호", "영화제목", "감독", "상영시간", "개봉일", "등급" };
			String[][] rowData;

			public FilmTable() {

				setLayout(new BorderLayout());

				add(plTable);
				plTable.add(scrollTable);

				plTable.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
				// plTable.setLayout(new BorderLayout());
				plTable.setVisible(false);

				// 열 셀높이 설정
				tbSearch.getTableHeader().setPreferredSize(new Dimension(1000, 30));
				tbSearch.setRowHeight(25);

				// tbSearch.getTableHeader().setReorderingAllowed(false); //순서
				// 변경불가
				// tbSearch.getTableHeader().setResizingAllowed(false); //크기 고정
				// tbSearch.getTableHeader().resizeAndRepaint();

				tbSearch.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

				// 스크롤 크기설정
				scrollTable.setPreferredSize(new Dimension(460, 240));

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

				for (int i = 0; i < tcm.getColumnCount(); i++) {
					tcm.getColumn(i).setCellRenderer(dtcr);
				}

				plTable.setVisible(true);
			}
		}
		
		public void resetmenu() {
			
			tbCnt = 0;
			filmRate = "";
			filmNum = 0;
			filmTitle = "";
			diretor = "";
			releaseDate = 0;
			runningTime = "";
			
			plFilmTable.plTable.setVisible(false);
			txFilmNum.setText("");
			txDirector.setText("");
			txFilmTitle.setText("");
			txReYear.setText("");
			txRunningTime.setText("");
			
			cbReDay.setSelectedIndex(0);
			cbReMonth.setSelectedIndex(0);
			cbFilmRate.setSelectedIndex(0);
		}
		
		public void resetFilm() {

			filmList = flDao.selectFilm();
			
			String str = "  선택";

			cbFilm.removeAllItems();
			cbFilm.addItem(str);
			
			comboBoxSetAlignLeft(cbFilm);
			
			String title = "";

			for (Film a : filmList) {
				
				title = a.getFilmTitle();
				cbFilm.addItem("  " + title +" ");
			}
			
			System.out.println(cbFilm.getPreferredSize());
			
			if (cbFilm.getPreferredSize().getWidth() >= 370) {
				cbFilm.setPreferredSize(new Dimension(370, 28));
				
			} else {
				cbFilm.setPreferredSize(null);
			}

			Object comp = cbFilm.getUI().getAccessibleChild(cbFilm, 0);
			
		    JPopupMenu popup = (JPopupMenu) comp;
			JScrollPane scrollPane = (JScrollPane) popup.getComponent(0);

			scrollPane.setHorizontalScrollBar(new JScrollBar(JScrollBar.HORIZONTAL));
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		}
	}

	// 2. 영화순서 설정
	public class ModifyFilmOrder extends JPanel {

		// 1. 영화순서 수정
		JPanel plModifyFmNorth = new JPanel();
		JPanel plModifyFmNNorth = new JPanel();
		JPanel plModifyFmNCenter = new JPanel();
		JPanel plModifyFmNSouth = new JPanel();

		JLabel lbFilm = new JLabel("영화", JLabel.CENTER);
		JLabel lbFmOrder = new JLabel("순서", JLabel.CENTER);
		JLabel lbFmOrderNum = new JLabel("번", JLabel.CENTER);
		JLabel lbArrow = new JLabel("-->", JLabel.CENTER);

		JComboBox<String> cbFilm = new JComboBox<>();
		JComboBox<String> cbFilmOrder = new JComboBox<>();

		JTextField txFmOrder = new JTextField(3);

		JButton btModifyFmOrder = new JButton("순서변경");
		JButton btCheckModifyFm = new JButton("조회");
		JButton btModifyOrderByPay = new JButton("구매순으로");
		JButton btModifyOrderByDate = new JButton("개봉일순으로");
		JButton btModifyOrderByFtitle = new JButton("이름순으로");

		// 2. 테이블 메뉴
		DateManager dm = new DateManager();

		CreateJTable jTable = new CreateJTable();

		ArrayList<Screen> scrPerList;

		JTable tbsearchPeriod = new JTable();
		JScrollPane scrollTable = new JScrollPane(tbsearchPeriod);
		DefaultTableModel model;
		DefaultTableCellRenderer dtcr;

		String colNames[] = { "영화", "상영기간" };
		String[][] rowData;

		boolean orderByPayDesc = true;
		boolean orderByDateDesc = true;
		boolean orderByFtitleAsc = true;

		public ModifyFilmOrder() {

			filmList = flDao.selectFilm();

			// 1. 영화순서 수정
			setLayout(new BorderLayout());

			add(plModifyFmNorth, BorderLayout.NORTH);
			setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 15));

			plModifyFmNorth.setLayout(new BorderLayout());
			plModifyFmNorth.setBorder(BorderFactory.createTitledBorder(null, "영화", TitledBorder.LEFT,
					TitledBorder.DEFAULT_POSITION, gothic(15)));

			plModifyFmNorth.add(plModifyFmNNorth, BorderLayout.NORTH);
			plModifyFmNorth.add(plModifyFmNCenter, BorderLayout.CENTER);
			plModifyFmNorth.add(plModifyFmNSouth, BorderLayout.SOUTH);

			// 1.1 상단메뉴
			plModifyFmNNorth.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));
			plModifyFmNNorth.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

			plModifyFmNNorth.add(lbFilm);
			plModifyFmNNorth.add(cbFilm);

			initializeComboBox(cbFilm);
			cbFilm.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

			resetFilm();

			lbFilm.setFont(gothic(15));
			cbFilm.setFont(gothic(13));

			plModifyFmNorth.setBackground(Color.white);
			plModifyFmNNorth.setBackground(Color.white);

			// 1.2 중단메뉴
			plModifyFmNCenter.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));
			plModifyFmNCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

			plModifyFmNCenter.add(lbFmOrder);
			plModifyFmNCenter.add(txFmOrder);
			plModifyFmNCenter.add(lbArrow);
			plModifyFmNCenter.add(cbFilmOrder);

			initializeComboBox(cbFilmOrder);
			cbFilmOrder.setFont(gothic(15));

			lbFmOrder.setFont(gothic(15));
			txFmOrder.setFont(gothic(15));
			lbArrow.setFont(new Font("d2coding", Font.CENTER_BASELINE, 20));

			txFmOrder.setEditable(false);
			txFmOrder.setHorizontalAlignment(JTextField.CENTER);
			txFmOrder.setPreferredSize(new Dimension(0, 30));
			txFmOrder.setBorder(BorderFactory.createLineBorder(Color.darkGray, 1));

			lbArrow.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

			txFmOrder.setBackground(Color.white);
			plModifyFmNCenter.setBackground(Color.white);

			// 1.3 하단메뉴
			plModifyFmNSouth.setLayout(new BorderLayout());

			JPanel plModifyFmSouth1 = new JPanel();
			JPanel plModifyFmSouth2 = new JPanel();

			plModifyFmNSouth.add(plModifyFmSouth1, BorderLayout.NORTH);
			plModifyFmNSouth.add(plModifyFmSouth2, BorderLayout.SOUTH);

			// 1.3 하단메뉴
			plModifyFmSouth1.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
			plModifyFmSouth1.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));

			plModifyFmSouth1.add(btModifyFmOrder);
			plModifyFmSouth1.add(btCheckModifyFm);

			btModifyFmOrder.setFont(gothic(13));
			btCheckModifyFm.setFont(gothic(13));

			plModifyFmSouth1.setBackground(Color.white);

			// 1.3 하단메뉴2
			plModifyFmSouth2.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
			plModifyFmSouth2.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

			plModifyFmSouth2.add(btModifyOrderByPay);
			plModifyFmSouth2.add(btModifyOrderByDate);
			plModifyFmSouth2.add(btModifyOrderByFtitle);

			btModifyOrderByPay.setFont(gothic(13));
			btModifyOrderByDate.setFont(gothic(13));
			btModifyOrderByFtitle.setFont(gothic(13));

			plModifyFmSouth2.setBackground(Color.white);

			// 1.4 액션리스너 추가
			btModifyFmOrder.addActionListener(new ModifyFmOrderEv());
			btCheckModifyFm.addActionListener(new ModifyFmOrderEv());
			btModifyOrderByDate.addActionListener(new ModifyFmOrderEv());
			btModifyOrderByPay.addActionListener(new ModifyFmOrderEv());
			btModifyOrderByFtitle.addActionListener(new ModifyFmOrderEv());

			cbFilm.addActionListener(new ModifyFmOrderEv());
			cbFilmOrder.addActionListener(new ModifyFmOrderEv());

			// 2. 테이블
			add(jTable, BorderLayout.CENTER);

			setBackground(Color.white);
		}

		public class CreateJTable extends JPanel {

			JPanel plTable = new JPanel();

			ArrayList<Film> filmList;

			DefaultTableModel model;
			JTable tbOrder = new JTable(model);
			JScrollPane scrollTable = new JScrollPane(tbOrder);
			DefaultTableCellRenderer dtcr;

			String colNames[] = { "", "영화", "개봉일", "구매수" };
			String[][] rowData;

			public CreateJTable() {

				setLayout(new BorderLayout());

				add(plTable);
				plTable.add(scrollTable);

				plTable.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
				plTable.setVisible(false);

				tbOrder.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

				// 열 셀높이 설정
				tbOrder.getTableHeader().setPreferredSize(new Dimension(1000, 30));
				tbOrder.setRowHeight(25);

				// 스크롤 크기설정
				scrollTable.setPreferredSize(new Dimension(460, 260));

				tbOrder.getTableHeader().setFont(gothic(15));
				tbOrder.setFont(gothic(13));
				scrollTable.setFont(gothic(13));

				tbOrder.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.black));
				tbOrder.setBorder(BorderFactory.createLineBorder(Color.black));
				scrollTable.setBorder(BorderFactory.createLineBorder(Color.white));

				setBackground(Color.white);
				plTable.setBackground(Color.white);
				tbOrder.setBackground(Color.white);
				scrollTable.getViewport().setBackground(Color.white);
				scrollTable.setBackground(Color.white);
			}

			public void setTable() {

				DateManager dm = new DateManager();

				tbCnt = 1;

				filmList = flDao.selectFilm();
				rowData = new String[filmList.size()][colNames.length];

				for (int i = 0; i < filmList.size(); i++) {

					rowData[i][0] = filmList.get(i).getFilmOrder() + "";
					rowData[i][1] = filmList.get(i).getFilmTitle();
					rowData[i][2] = dm.IntegerToDateSlash(filmList.get(i).getReleaseDate());
					rowData[i][3] = filmList.get(i).getPaymentCnt() + "";
				}

				// 셀 수정 못하게 막음
				model = new DefaultTableModel(rowData, colNames) {

					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};

				tbOrder.setModel(model);

				// 열 셀넓이, 정렬, 설정
				dtcr = new DefaultTableCellRenderer(); // 디폴트테이블셀렌더러를 생성
				dtcr.setHorizontalAlignment(SwingConstants.CENTER);

				TableColumnModel tcm = tbOrder.getColumnModel();

				tcm.getColumn(0).setPreferredWidth(40);
				tcm.getColumn(1).setPreferredWidth(236);
				tcm.getColumn(2).setPreferredWidth(90);

				for (int i = 0; i < tcm.getColumnCount(); i++) {
					tcm.getColumn(i).setCellRenderer(dtcr);
				}

				plTable.setVisible(true);
			}
		}

		// 1. 영화순서 수정 액션리스터
		public class ModifyFmOrderEv implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {

				// 1. 영화순서 수정
				if (e.getSource() == cbFilm) {

					int num = cbFilm.getSelectedIndex() - 1;

					if (num >= 0) {

						filmNum = filmList.get(num).getFilmNo();
						int orderNum = filmList.get(num).getFilmOrder();
						txFmOrder.setText(orderNum + "");
						System.out.println(filmNum);

						resetOrder(filmList, orderNum);

					} else
						filmNum = 0;
				}

				else if (e.getSource() == cbFilmOrder) {

					int num = cbFilmOrder.getSelectedIndex() - 1;

					if (num >= 0) {
						filmOrder = Integer.parseInt((String) cbFilmOrder.getSelectedItem());
						System.out.println(filmOrder);
					} else
						filmOrder = 0;

				}

				if (e.getSource() == btModifyFmOrder) {

					String scrDate = scrYear + scrMonth + scrDay;
					System.out.println("scrDate " + scrDate);

					if (cbFilmOrder.getSelectedIndex() == 0 || filmNum == 0) {
						p0InformrMsg(plModifyFmNorth, "데이터를 입력하거나 박스를 선택해주세요");
					}

					else {

						System.out.println(filmOrder + " " + filmNum);

						if (flDao.changeOrder(filmOrder, filmNum)) {
							p0InformrMsg(plModifyFmNorth, "성공적으로 변경되었습니다.");

							ArrayList<Film> flist = flDao.selectFilm();

							txFmOrder.setText(filmOrder + "");
							jTable.setTable();
							resetOrder(flist, filmOrder);
							cbFilmOrder.setSelectedIndex(0);

							orderByPayDesc = true;
							orderByDateDesc = true;
							orderByFtitleAsc = true;

						} else {
							p0InformrMsg(plModifyFmNorth, "데이터 변경이 실패하였습니다.");
						}
					}

				}

				if (e.getSource() == btCheckModifyFm) {

					tbCnt++;

					if (tbCnt % 2 == 1) {
						jTable.setTable();
					} else
						jTable.plTable.setVisible(false);
				}
				// 구매순으로 정렬 처음은 내림차순
				if (e.getSource() == btModifyOrderByPay) {

					if (orderByPayDesc) {

						orderByPayDesc = false;
						orderByDateDesc = true;
						orderByFtitleAsc = true;

						ArrayList<Film> flist = flDao.selectFilmOrderByPaymentDesc();

						setFilmOrder(flist, orderByPayDesc, true);
					} else {
						orderByPayDesc = true;
						orderByDateDesc = true;
						orderByFtitleAsc = true;

						ArrayList<Film> flist = flDao.selectFilmOrderByPaymentAsc();
						setFilmOrder(flist, orderByPayDesc, false);
					}
				}

				// 개봉일으로 정렬, 처음은 내림차순 최신일로
				if (e.getSource() == btModifyOrderByDate) {

					if (orderByDateDesc) {

						orderByDateDesc = false;
						orderByFtitleAsc = true;
						orderByPayDesc = true;

						ArrayList<Film> flist = flDao.selectFilmOrderByReleaseDateDesc();
						setFilmOrder(flist, orderByDateDesc, true);
					} else {
						orderByDateDesc = true;
						orderByPayDesc = true;
						orderByFtitleAsc = true;

						ArrayList<Film> flist = flDao.selectFilmOrderByReleaseDateAsc();
						setFilmOrder(flist, orderByDateDesc, false);
					}

				}

				// 영화 제목순으로 정렬. 처음은 오름차순
				if (e.getSource() == btModifyOrderByFtitle) {

					if (orderByFtitleAsc) {

						orderByFtitleAsc = false;
						orderByDateDesc = true;
						orderByPayDesc = true;

						ArrayList<Film> flist = flDao.selectFilmOrderByFTitleAsc();
						setFilmOrder(flist, orderByFtitleAsc, true);
					} else {
						orderByFtitleAsc = true;
						orderByDateDesc = true;
						orderByPayDesc = true;

						ArrayList<Film> flist = flDao.selectFilmOrderByFTitleDesc();
						setFilmOrder(flist, orderByFtitleAsc, false);
					}
				}

			}
		}

		protected void setFilmOrder(ArrayList<Film> flist, boolean order, boolean trueOrFalse) {

			for (int i = 0; i < flist.size(); i++) {

				int cnt = flDao.updateFilmOrder(flist.get(i).getFilmNo(), i + 1);
				if (cnt == 0) {
					order = trueOrFalse;
					System.out.println("cnt" + cnt);
					break;
				}
			}
			jTable.setTable();
			resetFilm();
		}

		protected void resetMenu() {

			orderByFtitleAsc = true;
			orderByDateDesc = true;
			orderByPayDesc = true;
			tbCnt = 0;
			jTable.plTable.setVisible(false);
			cbFilm.setSelectedIndex(0);
			txFmOrder.setText("");
			cbFilmOrder.setSelectedIndex(0);
			resetComboBox(cbFilmOrder);
		}

		public void resetFilm() {

			filmList = flDao.selectFilm();
			
			String str = "  선택";

			cbFilm.removeAllItems();
			cbFilm.addItem(str);
			
			comboBoxSetAlignLeft(cbFilm);
			
			String title = "";

			for (Film a : filmList) {
				
				title = a.getFilmTitle();
				cbFilm.addItem("  " + title);
			}
			
			System.out.println(cbFilm.getPreferredSize());
						
			if (cbFilm.getPreferredSize().getWidth() >= 320) {
				cbFilm.setPreferredSize(new Dimension(320, 28));
				
			} else {
				cbFilm.setPreferredSize(null);
			}

			Object comp = cbFilm.getUI().getAccessibleChild(cbFilm, 0);
			
		    JPopupMenu popup = (JPopupMenu) comp;
			JScrollPane scrollPane = (JScrollPane) popup.getComponent(0);

			scrollPane.setHorizontalScrollBar(new JScrollBar(JScrollBar.HORIZONTAL));
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		}

		protected void resetOrder(ArrayList list, int orderNum) {

			resetComboBox(cbFilmOrder);
			cbFilmOrder.setFont(gothic(15));

			for (Film a : filmList) {

				if (orderNum != a.getFilmOrder())
					cbFilmOrder.addItem(String.valueOf(a.getFilmOrder()));
			}

		}
	}

	// 3. 지역 변경
	public class ModifyThLocation extends JPanel {

		// 1. 영화 상영기간 수정
		JPanel plModifyFmNorth = new JPanel();
		JPanel plModifyFmNNorth = new JPanel();
		JPanel plModifyFmNSouth = new JPanel();

		JLabel lbLoc = new JLabel("지역", JLabel.CENTER);
		JLabel lbLoc1 = new JLabel("지역", JLabel.CENTER);
		JLabel lbLocNum = new JLabel("지역번호", JLabel.CENTER);
		
		JTextField txLoc = new JTextField(6);
		JTextField txLocNum = new JTextField(4);
		
		DefaultListModel<String> model = new DefaultListModel<>();
		JList<String> ltLoc = new JList<>();
		JScrollPane scLoc = new JScrollPane();

		JButton btModLoc = new JButton("수정");

		public ModifyThLocation() {

			setLayout(new BorderLayout());

			add(plModifyFmNorth, BorderLayout.NORTH);
			setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 15));

			plModifyFmNorth.setLayout(new BorderLayout());
			plModifyFmNorth.setBorder(BorderFactory.createTitledBorder(null, "극장", TitledBorder.LEFT,
					TitledBorder.DEFAULT_POSITION, gothic(15)));

			plModifyFmNorth.add(plModifyFmNNorth, BorderLayout.NORTH);
			plModifyFmNorth.add(plModifyFmNSouth, BorderLayout.SOUTH);

			plModifyFmNSouth.setLayout(new BorderLayout());

			// 1.1 상단메뉴
			scLoc = new JScrollPane(ltLoc);

			resetLocList();
			setList(ltLoc, 14, "LEFT");
			ltLoc.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

			JPanel plBtMod = new JPanel();
			JPanel plTxMod = new JPanel();

			plBtMod.setLayout(new BorderLayout());
			plTxMod.setLayout(new BorderLayout());

			scLoc.setPreferredSize(new Dimension(270, 150));
			
			lbLoc.setFont(gothic(15));

			plModifyFmNNorth.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));
			plModifyFmNNorth.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

			plModifyFmNNorth.add(lbLoc);
			plModifyFmNNorth.add(scLoc);
			
			lbLoc.setBorder(BorderFactory.createEmptyBorder(0, 0, 130, 10));
			
			plModifyFmNorth.setBackground(Color.white);
			plModifyFmNNorth.setBackground(Color.white);
			
			//하단메뉴
			plModifyFmNSouth.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));
			plModifyFmNSouth.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
			
			plModifyFmNSouth.add(lbLocNum);
			plModifyFmNSouth.add(txLocNum);
			plModifyFmNSouth.add(Box.createHorizontalStrut(5));
			plModifyFmNSouth.add(lbLoc1);
			plModifyFmNSouth.add(plTxMod);
			plModifyFmNSouth.add(plBtMod);
			
			plTxMod.add(txLoc);
			plBtMod.add(btModLoc);
			
			txLoc.setFont(gothic(13));
			txLocNum.setFont(gothic(13));
			lbLoc1.setFont(gothic(15));
			lbLocNum.setFont(gothic(15));
			btModLoc.setFont(gothic(13));
			
			txLoc.setPreferredSize(new Dimension(0, 30));
			txLocNum.setPreferredSize(new Dimension(0, 30));
			
			plBtMod.setBackground(Color.white);
			plTxMod.setBackground(Color.white);
			txLocNum.setBackground(Color.white);
			plModifyFmNSouth.setBackground(Color.white);
			
			//글자수 제한
			txLocNum.addKeyListener(new KeyAdapter() {

				@Override
				public void keyPressed(KeyEvent e) {
					String str = txLocNum.getText();

					if(str.length() > 2) {
						String newstr = str.substring(0, 2);
						txLocNum.setText(newstr);
					}
				}
			});
			txLocNum.setHorizontalAlignment(JTextField.CENTER);
			
			// 액션 리스너
			btModLoc.addActionListener(new BtActionEv());
			ltLoc.addListSelectionListener(new ListActionEv());
			
			setBackground(Color.white);
		}

		public class ListActionEv implements ListSelectionListener {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				if (ltLoc.getSelectedValue() == null) {
					locationNum = 0;
					System.out.println("locationNum " + locationNum);
				}

				else {
					int num = ltLoc.getSelectedIndex();

					locationNum = tLocList.get(num).getTlNo();
					System.out.println("locationNum " + locationNum);
				}
			}

		}

		//
		public class BtActionEv implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String tlname = txLoc.getText();
				String modLocNum = txLocNum.getText(); 
				
				if (locationNum == 0 || 
						(tlname.length() == 0 && modLocNum.length() == 0)) {
					p0InformrMsg(plModifyFmNorth, "데이터를 입력하거나 박스를 선택해주세요");
				}
				
				else if(!checkInt(modLocNum) && modLocNum.length() != 0) {
					p0InformrMsg(plModifyFmNorth, "번호는 0이상의 숫자를 입력해주세요");
				}

				else  {
					int modNum = 0;
					
					if (modLocNum.length() > 0)
						modNum = Integer.parseInt(modLocNum);
					
					System.out.println("moditlno " + modNum);
					System.out.println("locnum " + locationNum);
					System.out.println("tlname  " + tlname);

					if (tLocDao.updateThLocationBythNum(locationNum, modNum, tlname) > 0) {
						p0InformrMsg(plModifyFmNorth, "성공적으로 변경되었습니다.");
						resetLocList();

					} else {
						p0InformrMsg(plModifyFmNorth, "데이터 변경이 실패하였습니다.");
					}

				}
			}
		}

		protected void resetLocList() {
			
			txLoc.setText("");
			txLocNum.setText("");
			tLocList = tLocDao.selectTheaterLoc();

			model.removeAllElements();

			for (TheaterLocation a : tLocList) {
				
				String checkLength = a.getLocNum() + "";
				
				if (checkLength.length() == 1) {
					checkLength = "00" + checkLength; 
				}
				else if (checkLength.length() == 2) {
					checkLength = "0" + checkLength; 
				}
				
				model.addElement("   "+ checkLength +"  ｜  " + a.getLocaction());
			}
			ltLoc.setModel(model);
		}
	}
	
	// 4. 극장이름 변경
	public class ModifyTheater extends JPanel {

		// 1. 극장이름 수정
		JPanel plModifyThNorth = new JPanel();
		JPanel plModifyThNNorth = new JPanel();
		JPanel plModifyThNCenter = new JPanel();
		JPanel plModifyThNSouth = new JPanel();

		JLabel lbLoc = new JLabel("지역", JLabel.CENTER);
		JLabel lbTheater = new JLabel("극장", JLabel.CENTER);
		
		JTextField txTheater = new JTextField(5);
		
		JComboBox<String> cbLoc;

		DefaultListModel<String> model = new DefaultListModel<>();
		JList<String> ltTheater = new JList<>();
		JScrollPane scTheater = new JScrollPane();

		JButton btModifyTheater = new JButton("수정");

		public ModifyTheater() {

			// 1. 극장이름 수정
			setLayout(new BorderLayout());

			add(plModifyThNorth, BorderLayout.NORTH);
			setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 15));

			plModifyThNorth.setLayout(new BorderLayout());
			plModifyThNorth.setBorder(BorderFactory.createTitledBorder(null, "극장", TitledBorder.LEFT,
					TitledBorder.DEFAULT_POSITION, gothic(15)));

			plModifyThNorth.add(plModifyThNNorth, BorderLayout.NORTH);
			plModifyThNorth.add(plModifyThNCenter, BorderLayout.CENTER);
			
			plModifyThNSouth.setLayout(new BorderLayout());

			for (int i = 1; i < tLocList.size() + 1; i++) {
				location[i] = tLocList.get(i - 1).getLocaction();
			}

			cbLoc = new JComboBox<>(location);
			setComboBox(cbLoc);

			scTheater = new JScrollPane(ltTheater);
			scTheater.setPreferredSize(new Dimension(100, 150));

			JPanel plcbModify = new JPanel();
			plcbModify.setLayout(new BorderLayout());
			
			//1. 상단 메뉴
			plModifyThNNorth.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));
			plModifyThNNorth.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

			plModifyThNNorth.add(lbLoc);
			plModifyThNNorth.add(plcbModify);
			
			plcbModify.add(cbLoc);
				
			lbLoc.setFont(gothic(15));

			setBackground(Color.white);
			plcbModify.setBackground(Color.white);
			plModifyThNorth.setBackground(Color.white);
			plModifyThNNorth.setBackground(Color.white);
			
			//2. 하단 메뉴
			JPanel plbtModify = new JPanel();
			JPanel plTx = new JPanel();
			
			plbtModify.setLayout(new BorderLayout());
			plTx.setLayout(new BorderLayout());
			
			plModifyThNCenter.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));
			plModifyThNCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
			
			plModifyThNCenter.add(lbTheater);
			plModifyThNCenter.add(scTheater);
			plModifyThNCenter.add(Box.createHorizontalStrut(1));
			plModifyThNCenter.add(plTx);
			plModifyThNCenter.add(Box.createHorizontalStrut(1));
			plModifyThNCenter.add(plbtModify);

			plbtModify.add(btModifyTheater);
			plTx.add(txTheater);
			
			txTheater.setFont(gothic(15));
			lbTheater.setFont(gothic(15));
			btModifyTheater.setFont(gothic(13));
			
			setList(ltTheater, 14, "CENTER");
			
			txTheater.setPreferredSize(new Dimension(0, 30));
			
			lbTheater.setBorder(BorderFactory.createEmptyBorder(0, 0, 130, 0));
			plTx.setBorder(BorderFactory.createEmptyBorder(0, 0, 120, 0));
			plbtModify.setBorder(BorderFactory.createEmptyBorder(0, 0, 120, 0));
			
			plTx.setBackground(Color.white);
			plbtModify.setBackground(Color.white);
			plModifyThNCenter.setBackground(Color.white);
			
			// 액션리스너 추가
			btModifyTheater.addActionListener(new ModifyThActionEv());
			ltTheater.addListSelectionListener(new ListEv());
			cbLoc.addActionListener(new ModifyThActionEv());

		}

		public class ListEv implements ListSelectionListener {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				if (ltTheater.getSelectedValue() == null) {
					theaterNum = 0;
					System.out.println("theaterNum " + theaterNum);
				}

				else {
					int num = ltTheater.getSelectedIndex();

					theaterNum = theaterList.get(num).getTno();
					System.out.println("theaterNum " + theaterNum);
				}
			}
		}

		// 1. 상영기간 수정 액션리스터
		public class ModifyThActionEv implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {

				// 1. 극장에 상영기간 수정
				// 1. 지역 콤보박스 이벤트
				if (e.getSource() == cbLoc) {
					cbLocEvSetting(cbLoc);
					resetList();
				}

				if (e.getSource() == btModifyTheater) {
					
					String tname = txTheater.getText();
					
					if (theaterNum == 0 || txTheater.getText().length()== 0) {
						p0InformrMsg(plModifyThNorth, "데이터를 입력하거나 박스를 선택해주세요");
					}

					else {

						if (theaterDao.undateTheaterBythNum(theaterNum, tname) > 0) {
							p0InformrMsg(plModifyThNorth, "성공적으로 변경되었습니다.");
							resetList();
							txTheater.setText("");

						} else {
							p0InformrMsg(plModifyThNorth, "데이터 변경이 실패하였습니다.");
						}

					}
				}

			}
		}

		protected void cbLocEvSetting(JComboBox<?> cbLoc) {
			// 선택한 지역 번호 반환
			int num = cbLoc.getSelectedIndex() - 1;

			if (num >= 0) {
				locationNum = tLocList.get(num).getTlNo();
				System.out.println(locationNum);
			} else
				locationNum = 0;
		}

		protected void resetList() {

			theaterList = theaterDao.selectTheaterByLoc(locationNum);

			model.removeAllElements();

			for (Theater a : theaterList) {
				model.addElement(a.getTname());
			}
			ltTheater.setModel(model);
		}

		protected void resetMenu() {

			cbLoc.setSelectedIndex(0);
			txTheater.setText("");
		}

		protected void resetLoc() {

			tLocList = tLocDao.selectTheaterLoc();

			resetComboBox(cbLoc);
			for (TheaterLocation a : tLocList) {
				cbLoc.addItem(a.getLocaction());
			}
		}
	}

	// 5. 극장에서 상영할 영화 변경
	public class ModifyFilmTitle extends JPanel {

		// 1. 극장 상영될 영화 변경
		JPanel plModifyFmNorth = new JPanel();
		JPanel plModifyFmNNorth = new JPanel();
		JPanel plModifyFmNCenter = new JPanel();
		JPanel plModifyFmNSouth = new JPanel();

		JLabel lbLoc = new JLabel("지역", JLabel.CENTER);
		JLabel lbTheater = new JLabel("극장", JLabel.CENTER);
		JLabel lbScreen = new JLabel("상영관", JLabel.CENTER);
		JLabel lbScrFilm = new JLabel("영화", JLabel.CENTER);
		JLabel lbScrFilm2 = new JLabel("변경할 영화", JLabel.CENTER);

		JComboBox<String> cbLoc;
		JComboBox<String> cbTheater = new JComboBox();
		JComboBox<String> cbScreen = new JComboBox<>();
		JComboBox<String> cbFilm = new JComboBox<>();
		JComboBox<String> cbFilm2 = new JComboBox<>();

		JButton btModifyFm = new JButton("변경");
		JButton btCheckModifyFm = new JButton("조회");
		
		ScreenTable plScrTable = new ScreenTable();
		
		String theatherName;

		public ModifyFilmTitle() {

			// 1. 극장 상영될 영화 변경 
			setLayout(new BorderLayout());

			add(plModifyFmNorth, BorderLayout.NORTH);
			setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 15));

			plModifyFmNorth.setLayout(new BorderLayout());
			plModifyFmNorth.setBorder(BorderFactory.createTitledBorder(null, "극장", TitledBorder.LEFT,
					TitledBorder.DEFAULT_POSITION, gothic(15)));

			plModifyFmNorth.add(plModifyFmNNorth, BorderLayout.NORTH);
			plModifyFmNorth.add(plModifyFmNCenter, BorderLayout.CENTER);
			plModifyFmNorth.add(plModifyFmNSouth, BorderLayout.SOUTH);

			plModifyFmNSouth.setLayout(new BorderLayout());

			JPanel plModifyFmSouth1 = new JPanel();
			JPanel plModifyFmSouth2 = new JPanel();

			plModifyFmNSouth.add(plModifyFmSouth1, BorderLayout.NORTH);
			plModifyFmNSouth.add(plModifyFmSouth2, BorderLayout.SOUTH);

			// 1.1 상단메뉴
			location[0] = "선택";

			for (int i = 1; i < tLocList.size() + 1; i++) {
				location[i] = tLocList.get(i - 1).getLocaction();
			}

			cbLoc = new JComboBox<>(location);

			plModifyFmNNorth.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));
			plModifyFmNNorth.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

			plModifyFmNNorth.add(lbLoc);
			plModifyFmNNorth.add(cbLoc);
			plModifyFmNNorth.add(lbTheater);
			plModifyFmNNorth.add(cbTheater);
			plModifyFmNNorth.add(lbScreen);
			plModifyFmNNorth.add(cbScreen);

			setComboBox(cbLoc);
			initializeComboBox(cbTheater);
			initializeComboBox(cbScreen);

			lbTheater.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
			lbScreen.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

			lbLoc.setFont(gothic(15));
			lbTheater.setFont(gothic(15));
			lbScreen.setFont(gothic(15));

			setBackground(Color.white);
			plModifyFmNorth.setBackground(Color.white);
			plModifyFmNNorth.setBackground(Color.white);

			// 중단메뉴
			plModifyFmNCenter.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));
			plModifyFmNCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

			plModifyFmNCenter.add(lbScrFilm);
			plModifyFmNCenter.add(cbFilm);

			initializeComboBox(cbFilm);

			lbScrFilm.setFont(gothic(15));
			comboBoxSetAlignLeft(cbFilm);
//			cbFilm.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));

			plModifyFmNCenter.setBackground(Color.white);

			// 1.3 하단메뉴
			plModifyFmSouth1.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));
			plModifyFmSouth1.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

			plModifyFmSouth1.add(lbScrFilm2);
			plModifyFmSouth1.add(cbFilm2);

			initializeComboBox(cbFilm2);
			resetFilm();

			lbScrFilm2.setFont(gothic(15));

			plModifyFmSouth1.setBackground(Color.white);

			// 1.3 하단메뉴2
			plModifyFmSouth2.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));
			plModifyFmSouth2.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

			plModifyFmSouth2.add(btModifyFm);
			plModifyFmSouth2.add(btCheckModifyFm);

			btModifyFm.setFont(gothic(13));
			btCheckModifyFm.setFont(gothic(13));

			plModifyFmSouth2.setBackground(Color.white);
			
			add(plScrTable,BorderLayout.CENTER);

			// 1.4 액션리스너 추가
			btModifyFm.addActionListener(new ModifyFilmTitleEv());
			btCheckModifyFm.addActionListener(new ModifyFilmTitleEv());

			cbFilm.addActionListener(new ModifyFilmTitleEv());
			cbFilm2.addActionListener(new ModifyFilmTitleEv());
			cbLoc.addActionListener(new ModifyFilmTitleEv());
			cbScreen.addActionListener(new ModifyFilmTitleEv());
			cbTheater.addActionListener(new ModifyFilmTitleEv());
		}
		
		public class ScreenTable extends JPanel {
			
			DateManager dm = new DateManager();
			
			JPanel plTable = new JPanel();
			
			ArrayList<Screen> scrList;
			
			DefaultTableModel model;
			JTable tbSearch = new JTable(model);
			JScrollPane scrollTable = new JScrollPane(tbSearch);
			DefaultTableCellRenderer dtcr;
			
			String colNames[] = {"상영관","영화", "극장"};
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
				
//				tbSearch.getTableHeader().setReorderingAllowed(false); //순서 변경불가
//				tbSearch.getTableHeader().setResizingAllowed(false); //크기 고정
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
					rowData[i][2] = theatherName;
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
				
				tcm.getColumn(0).setPreferredWidth(80);
				tcm.getColumn(1).setPreferredWidth(261);
				tcm.getColumn(2).setPreferredWidth(100);
				
				for(int i = 0 ; i < tcm.getColumnCount() ; i++){
					tcm.getColumn(i).setCellRenderer(dtcr);
				}
							
				plTable.setVisible(true);
			}	
		}

		// 극장에서 상영할 영화 변경
		public class ModifyFilmTitleEv implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {

				// 1. 극장에 상영기간 수정
				// 1. 지역 콤보박스 이벤트
				if (e.getSource() == cbLoc) {
					cbLocEvSetting(cbLoc);
				}

				// 2. 극장 영화관 콤보박스 이벤트
				else if (e.getSource() == cbTheater) {
					cbTheaterEvSetting(cbTheater);
					tbCnt = 0;
				}
				// 3. 스크린 콤보박스 이벤트
				else if (e.getSource() == cbScreen) {
					cbScreenEvSetting(cbScreen);
				}

				else if (e.getSource() == cbFilm) {					
					cbFilmEvSetting(cbFilm);
				}

				else if (e.getSource() == cbFilm2) {

					int num = cbFilm2.getSelectedIndex() - 1;

					if (num >= 0) {

						ModifyfilmNum = filmList.get(num).getFilmNo();
						System.out.println(ModifyfilmNum);
						System.out.println(screenName);
						
						boolean a = scrDao.checkDupScrNameAndFilmNum(theaterNum, ModifyfilmNum, screenName); 
						System.out.println(a);
						
						if(a) {
							p0InformrMsg(plModifyFmNorth, "중복된 영화입니다.");
							cbFilm2.setSelectedIndex(0);
						}

					} else
						ModifyfilmNum = 0;
				}

				if (e.getSource() == btModifyFm) {

					String scrDate = scrYear + scrMonth + scrDay;
					System.out.println("scrDate " + scrDate);

					if (theaterNum == 0 || screenNum == 0 || ModifyfilmNum == 0 || filmNum < 0) {
						p0InformrMsg(plModifyFmNorth, "데이터를 입력하거나 박스를 선택해주세요");
					}

					else {

						if (scrDao.updateScreenFno(ModifyfilmNum, filmNum, screenNum) > 0) {
							p0InformrMsg(plModifyFmNorth, "성공적으로 변경되었습니다.");
							plScrTable.setTable(theaterNum);
							resetComboBox(cbFilm);
							cbScreen.setSelectedIndex(0);
							System.out.println(screenName);
							cbFilm2.setSelectedIndex(0);

						} else {
							p0InformrMsg(plModifyFmNorth, "데이터 변경이 실패하였습니다.");
						}
					}

				}

				if (e.getSource() == btCheckModifyFm) {
					
					if (theaterNum > 0) {
						tbCnt++;
						if (tbCnt % 2 == 1) {
							plScrTable.setTable(theaterNum);
						} else
							plScrTable.plTable.setVisible(false);
					}
					else 
						p0InformrMsg(plModifyFmNorth, "극장박스를 선택해주세요");
				}
			}
		}

		protected void cbLocEvSetting(JComboBox<?> cbLoc) {
			// 선택한 지역 번호 반환
			int num = cbLoc.getSelectedIndex() - 1;

			if (num >= 0) {
				locationNum = tLocList.get(num).getTlNo();
				System.out.println(locationNum);
			} else
				locationNum = 0;

			// 선택한 지역에 해당하는 극장들 출력
			theaterList = theaterDao.selectTheaterByLoc(locationNum);

			cbTheater.removeAllItems();
			cbTheater.addItem("선택");

			for (int b = 0; b < theaterList.size(); b++) {
				cbTheater.addItem(theaterList.get(b).getTname());
			}
		}

		protected void cbTheaterEvSetting(JComboBox<?> cbTheater) {
			// 선택한 극장 번호 반환
			int num = cbTheater.getSelectedIndex() - 1;

			if (num >= 0) {
				theaterNum = theaterList.get(num).getTno();
				theatherName = theaterList.get(num).getTname();
				System.out.println(theaterNum);
			} else
				theaterNum = 0;

			// 상영시간 추가 메뉴에서 선택시 스크린 상영관 정보 반환
			ArrayList<String> scrNames = scrDao.getScrNameNotDup(theaterNum);

			cbScreen.removeAllItems();
			cbScreen.addItem("선택");

			for (int b = 0; b < scrNames.size(); b++) {
				cbScreen.addItem(scrNames.get(b));
			}
		}

		protected void cbScreenEvSetting(JComboBox<?> cbScreen) {

			int num = cbScreen.getSelectedIndex();

			if (num > 0) {

				screenName = (String) cbScreen.getSelectedItem();
				screenList = scrDao.selectScreenBySnameAndTno(screenName, theaterNum);

				ArrayList<Integer> Numes = new ArrayList<>();
				
				System.out.println(screenName);
				System.out.println(ModifyfilmNum);
				System.out.println(theaterNum);
				
				boolean checkDup = scrDao.checkDupScrNameAndFilmNum(theaterNum, ModifyfilmNum, screenName); 
				
				if(checkDup) {
					screenName = "";
					p0InformrMsg(plModifyFmNorth, "중복된 영화입니다.");
					cbScreen.setSelectedIndex(0);
				}
				
				else {

					// 영화 콤보박스 출력
					String str = "  선택";

					cbFilm.removeAllItems();
					cbFilm.addItem(str);

					comboBoxSetAlignLeft(cbFilm);

					for (Screen a : screenList) {

						Numes.add(a.getFno());

						Film film = flDao.selectFilmByFno(a.getFno());

						if (film != null)
							cbFilm.addItem("  " + film.getFilmTitle());
						else 
							cbFilm.addItem("  " + "삭제 됨" + " ");
					}

					System.out.println(cbFilm.getPreferredSize());

					if (cbFilm.getPreferredSize().getWidth() >= 370) {
						cbFilm.setPreferredSize(new Dimension(370, 28));

					} else {
						cbFilm.setPreferredSize(null);
					}

					Object comp = cbFilm.getUI().getAccessibleChild(cbFilm, 0);

					JPopupMenu popup = (JPopupMenu) comp;
					JScrollPane scrollPane = (JScrollPane) popup.getComponent(0);

					scrollPane.setHorizontalScrollBar(new JScrollBar(JScrollBar.HORIZONTAL));
					scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

					filmNumes = Numes;

				}
			} else {
				screenNum = 0;
				screenName = "";
				resetComboBox(cbFilm);
			}
		}

		protected void cbFilmEvSetting(JComboBox<?> cbFilm) {

			int num = cbFilm.getSelectedIndex() - 1;

			if (num >= 0) {

				System.out.println();
				filmNum = filmNumes.get(num);

				Screen scr = scrDao.selectScreenBySname_Tno_fno(screenName, theaterNum, filmNum);

				System.out.println(filmNum);

				screenNum = scr.getSno();
				System.out.println(screenNum);

			} else
				filmNum = -1;
		}

		protected void resetMenu() {
			
			tbCnt = 0;
			ModifyfilmNum = 0;
			screenName = "";
			cbLoc.setSelectedIndex(0);
			cbTheater.setSelectedIndex(0);
			cbScreen.setSelectedIndex(0);
			plScrTable.plTable.setVisible(false);
			resetComboBox(cbFilm);
		}

		protected void resetLoc() {

			tLocList = tLocDao.selectTheaterLoc();

			resetComboBox(cbLoc);
			for (TheaterLocation a : tLocList) {
				cbLoc.addItem(a.getLocaction());
			}
		}

		public void resetFilm() {

			filmList = flDao.selectFilm();
			
			String str = "  선택";

			cbFilm2.removeAllItems();
			cbFilm2.addItem(str);
			
			comboBoxSetAlignLeft(cbFilm2);
			
			String title = "";

			for (Film a : filmList) {
				
				title = a.getFilmTitle();
				cbFilm2.addItem("  " + title + " ");
			}
			
			System.out.println(cbFilm2.getPreferredSize());
						
			if (cbFilm2.getPreferredSize().getWidth() >= 320) {
				cbFilm2.setPreferredSize(new Dimension(320, 28));
				
			} else {
				cbFilm2.setPreferredSize(null);
			}
			
			Object comp = cbFilm2.getUI().getAccessibleChild(cbFilm2, 0);
			
		    JPopupMenu popup = (JPopupMenu) comp;
			JScrollPane scrollPane = (JScrollPane) popup.getComponent(0);

			scrollPane.setHorizontalScrollBar(new JScrollBar(JScrollBar.HORIZONTAL));
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		}
	}

	// 6. 극장에서 상영할 영화 상영기간 수정
	public class ModifyFmPeriod extends JPanel {

		// 1. 영화 상영기간 수정
		JPanel plModifyFmPeriodNorth = new JPanel();
		JPanel plModifyFmPeriodNNorth = new JPanel();
		JPanel plModifyFmPeriodNCenter = new JPanel();
		JPanel plModifyFmPeriodNSouth = new JPanel();

		JLabel lbScrPeriod = new JLabel("상영기간", JLabel.CENTER);
		JLabel lbScrYear = new JLabel("년", JLabel.CENTER);
		JLabel lbScrMonth = new JLabel("월", JLabel.CENTER);
		JLabel lbScrDay = new JLabel("일", JLabel.CENTER);
		JLabel lbScrFilm = new JLabel("영화", JLabel.CENTER);
		JLabel lbLoc = new JLabel("지역", JLabel.CENTER);
		JLabel lbTheater = new JLabel("극장", JLabel.CENTER);

		JComboBox<String> cbLoc;
		JComboBox<String> cbTheater = new JComboBox();
		
		JComboBox<String> cbScrYear;
		JComboBox<String> cbScrMonth = new JComboBox<>(Month);
		JComboBox<String> cbScrDay = new JComboBox<>(Day);
		JComboBox<String> cbFilm = new JComboBox<>();

		JButton btModifyFmPeriod = new JButton("변경");
		JButton btCheckModifyFmPeriod = new JButton("조회");
		
		String theaterName;
		
		// 2. 테이블 메뉴
		DateManager dm = new DateManager();

		CreateJTable jTable = new CreateJTable();

		ArrayList<Screen> scrPerList;


		int tbCnt = 0;
		String colNames[] = { "영화", "상영기간" };
		String[][] rowData;

		public ModifyFmPeriod() {

			// 1. 상영기간 수정
			setLayout(new BorderLayout());

			add(plModifyFmPeriodNorth, BorderLayout.NORTH);
			setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 15));

			plModifyFmPeriodNorth.setLayout(new BorderLayout());
			plModifyFmPeriodNorth.setBorder(BorderFactory.createTitledBorder(null, "극장", TitledBorder.LEFT,
					TitledBorder.DEFAULT_POSITION, gothic(15)));

			plModifyFmPeriodNorth.add(plModifyFmPeriodNNorth, BorderLayout.NORTH);
			plModifyFmPeriodNorth.add(plModifyFmPeriodNCenter, BorderLayout.CENTER);
			plModifyFmPeriodNorth.add(plModifyFmPeriodNSouth, BorderLayout.SOUTH);
			
			JPanel plModifyFmPeriodNNNorth = new JPanel();
			JPanel plModifyFmPeriodNNCenter = new JPanel();
			
			
			plModifyFmPeriodNNorth.setLayout(new BorderLayout());
			plModifyFmPeriodNNorth.add(plModifyFmPeriodNNNorth,BorderLayout.NORTH);
			plModifyFmPeriodNNorth.add(plModifyFmPeriodNNCenter,BorderLayout.CENTER);
			
			//1.0 상단메뉴1
			location[0] = "선택";

			for (int i = 1; i < tLocList.size() + 1; i++) {
				location[i] = tLocList.get(i - 1).getLocaction();
			}

			cbLoc = new JComboBox<>(location);

			plModifyFmPeriodNNNorth.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));
			plModifyFmPeriodNNNorth.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

			plModifyFmPeriodNNNorth.add(lbLoc);
			plModifyFmPeriodNNNorth.add(cbLoc);
			plModifyFmPeriodNNNorth.add(lbTheater);
			plModifyFmPeriodNNNorth.add(cbTheater);

			setComboBox(cbLoc);
			initializeComboBox(cbTheater);

			lbTheater.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
			lbLoc.setFont(gothic(15));
			lbTheater.setFont(gothic(15));

			setBackground(Color.white);
			plModifyFmPeriodNorth.setBackground(Color.white);
			plModifyFmPeriodNNNorth.setBackground(Color.white);
			plModifyFmPeriodNNorth.setBackground(Color.white);
			
			// 1.1 상단메뉴2
			plModifyFmPeriodNNCenter.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));
			plModifyFmPeriodNNCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

			plModifyFmPeriodNNCenter.add(lbScrFilm);
			plModifyFmPeriodNNCenter.add(cbFilm);

			initializeComboBox(cbFilm);
//			resetFilm();

			cbFilm.setFont(gothic(13));
			lbScrFilm.setFont(gothic(15));

			plModifyFmPeriodNNCenter.setBackground(Color.white);

			// 1.2 중단메뉴
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			String years[] = { "선택", year + "", (year + 1) + "" };

			cbScrYear = new JComboBox<>(years);

			plModifyFmPeriodNCenter.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));
			plModifyFmPeriodNCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

			plModifyFmPeriodNCenter.add(lbScrPeriod);
			plModifyFmPeriodNCenter.add(cbScrYear);
			plModifyFmPeriodNCenter.add(lbScrYear);
			plModifyFmPeriodNCenter.add(cbScrMonth);
			plModifyFmPeriodNCenter.add(lbScrMonth);
			plModifyFmPeriodNCenter.add(cbScrDay);
			plModifyFmPeriodNCenter.add(lbScrDay);

			lbScrPeriod.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
			lbScrYear.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
			lbScrMonth.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

			setComboBox(cbScrYear);
			setComboBox(cbScrMonth);
			setComboBox(cbScrDay);

			lbScrPeriod.setFont(gothic(15));
			lbScrYear.setFont(gothic(15));
			lbScrMonth.setFont(gothic(15));
			lbScrDay.setFont(gothic(15));

			plModifyFmPeriodNCenter.setBackground(Color.white);

			// 1.3 하단메뉴
			plModifyFmPeriodNSouth.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));
			plModifyFmPeriodNSouth.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

			plModifyFmPeriodNSouth.add(btModifyFmPeriod);
			plModifyFmPeriodNSouth.add(btCheckModifyFmPeriod);

			btModifyFmPeriod.setFont(gothic(13));
			btCheckModifyFmPeriod.setFont(gothic(13));

			plModifyFmPeriodNSouth.setBackground(Color.white);

			// 1.4 액션리스너 추가
			btModifyFmPeriod.addActionListener(new ModifyFilmPeriodEv());
			btCheckModifyFmPeriod.addActionListener(new ModifyFilmPeriodEv());
			
			cbLoc.addActionListener(new ModifyFilmPeriodEv());
			cbTheater.addActionListener(new ModifyFilmPeriodEv());
			cbFilm.addActionListener(new ModifyFilmPeriodEv());
			cbScrDay.addActionListener(new ModifyFilmPeriodEv());
			cbScrMonth.addActionListener(new ModifyFilmPeriodEv());
			cbScrYear.addActionListener(new ModifyFilmPeriodEv());

			// 2. 테이블
			add(jTable, BorderLayout.CENTER);

			setBackground(Color.white);
		}

		public class CreateJTable extends JPanel {

			DateManager dm = new DateManager();
			
			JTable tbsearchPeriod = new JTable();
			JScrollPane scrollTable = new JScrollPane(tbsearchPeriod);
			DefaultTableModel model;
			DefaultTableCellRenderer dtcr;
			
			JPanel plTable = new JPanel();

			String colNames[] = { "영화", "상영기간", "극장" };
			String[][] rowData;

			public CreateJTable() {

				setLayout(new BorderLayout());

				add(plTable);
				plTable.add(scrollTable);

				plTable.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
				plTable.setVisible(false);
				
				tbsearchPeriod.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				
				// 열 셀높이 설정
				tbsearchPeriod.getTableHeader().setPreferredSize(new Dimension(1000, 30));
				tbsearchPeriod.setRowHeight(25);

				// 스크롤 크기설정
				scrollTable.setPreferredSize(new Dimension(460, 260));
				
				tbsearchPeriod.getTableHeader().setFont(gothic(15));
				tbsearchPeriod.setFont(gothic(13));
				scrollTable.setFont(gothic(13));

				tbsearchPeriod.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.black));
				tbsearchPeriod.setBorder(BorderFactory.createLineBorder(Color.black));
				scrollTable.setBorder(BorderFactory.createLineBorder(Color.white));

				setBackground(Color.white);
				plTable.setBackground(Color.white);
				tbsearchPeriod.setBackground(Color.white);
				scrollTable.getViewport().setBackground(Color.white);
				scrollTable.setBackground(Color.white);
			}

			public void setTable() {

				scrPerList = scrDao.selectFilmNumAndPeriodInTheather(theaterNum);
				rowData = new String[scrPerList.size()][colNames.length];

				for (int i = 0; i < scrPerList.size(); i++) {
					
					Film film = flDao.selectFilmByFno((scrPerList.get(i).getFno()));
					
					if (film == null)
						rowData[i][0] = "";
					else
						rowData[i][0] = film.getFilmTitle();
					
					rowData[i][2] = theaterName;
							
					if (scrPerList.get(i).getPeriod() == 0) {
						rowData[i][1] = "";
					} else
						rowData[i][1] = dm.IntegerToDate(scrPerList.get(i).getPeriod());
				}

				// 셀 수정 못하게 막음
				model = new DefaultTableModel(rowData, colNames) {

					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};

				tbsearchPeriod.setModel(model);

				// 열 셀넓이, 정렬, 설정
				dtcr = new DefaultTableCellRenderer(); // 디폴트테이블셀렌더러를 생성
				dtcr.setHorizontalAlignment(SwingConstants.CENTER);

				TableColumnModel tcm = tbsearchPeriod.getColumnModel();
				
				tcm.getColumn(0).setPreferredWidth(211);
				tcm.getColumn(1).setPreferredWidth(130);
				tcm.getColumn(2).setPreferredWidth(100);
				
				for (int i = 0; i < tcm.getColumnCount(); i++) {
					tcm.getColumn(i).setCellRenderer(dtcr);
				}

				plTable.setVisible(true);
			}
		}

		// 1. 상영기간 수정 액션리스터
		public class ModifyFilmPeriodEv implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {

				// 1. 상영기간 수정
				if (e.getSource() == cbLoc) {
					cbLocEvSetting(cbLoc);
				}
				
				else if (e.getSource() == cbTheater) {
					cbTheaterEvSetting(cbTheater);
					tbCnt = 0;
					
					scrPerList = scrDao.selectFilmNumAndPeriodInTheather(theaterNum);
					
					// 영화 선택
					ArrayList<Integer> numes = new ArrayList<>();
					String str = "  선택";

					cbFilm.removeAllItems();
					cbFilm.addItem(str);

					comboBoxSetAlignLeft(cbFilm);

					String title = "";
										
					for (Screen a : scrPerList) {

						numes.add(a.getFno());
						
						Film film = flDao.selectFilmByFno(a.getFno());
										
						if (film != null)
							cbFilm.addItem("  " + flDao.selectFilmByFno(a.getFno()).getFilmTitle() + " ");
					}

					System.out.println(cbFilm.getPreferredSize());

					if (cbFilm.getPreferredSize().getWidth() >= 370) {
						cbFilm.setPreferredSize(new Dimension(370, 28));

					} else {
						cbFilm.setPreferredSize(null);
					}

					Object comp = cbFilm.getUI().getAccessibleChild(cbFilm, 0);

					JPopupMenu popup = (JPopupMenu) comp;
					JScrollPane scrollPane = (JScrollPane) popup.getComponent(0);

					scrollPane.setHorizontalScrollBar(new JScrollBar(JScrollBar.HORIZONTAL));
					scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				}
				
				else if (e.getSource() == cbFilm) {

					int num = cbFilm.getSelectedIndex() - 1;

					if (num >= 0) {

						filmNum = scrPerList.get(num).getFno();
						System.out.println(filmNum);

					} else
						filmNum = 0;
				}

				else if (e.getSource() == cbScrYear) {

					scrYear = comboBoxEvSetting(cbScrYear);
					System.out.println("scrYear " + scrYear);
				}

				else if (e.getSource() == cbScrMonth) {

					scrMonth = comboBoxEvSetting(cbScrMonth);
					System.out.println("ScrMonth " + scrMonth);
				}

				else if (e.getSource() == cbScrDay) {

					scrDay = comboBoxEvSetting(cbScrDay);
					System.out.println("ScrDay " + scrDay);
				}

				if (e.getSource() == btModifyFmPeriod) {

					String scrDate = scrYear + scrMonth + scrDay;
					System.out.println("scrDate " + scrDate);

					if (scrDate.length() != 8 || filmNum == 0 || theaterNum == 0) {
						p0InformrMsg(plModifyFmPeriodNorth, "데이터를 입력하거나 박스를 선택해주세요");
					}

					else {

						if (scrDao.updateScreenPeriodByTno(Integer.parseInt(scrDate), filmNum,theaterNum) > 0) {
							p0InformrMsg(plModifyFmPeriodNorth, "성공적으로 변경되었습니다.");
							cbFilm.setSelectedIndex(0);
							jTable.setTable();
							tbCnt = 1;

						} else {
							p0InformrMsg(plModifyFmPeriodNorth, "시스템 오류나 상영관에 해당영화가 없기때문에\n데이터 변경이 실패하였습니다.");
						}
					}

				}

				if (e.getSource() == btCheckModifyFmPeriod) {
					
					if (cbTheater.getSelectedIndex() == 0) {						
						p0InformrMsg(plModifyFmPeriodNorth, "극장을 선택해 주세요");
					} else {
						tbCnt++;

						if (tbCnt % 2 == 1) {
							jTable.setTable();
						} else
							jTable.plTable.setVisible(false);
					}
				}
			}
		}
		
		protected void cbLocEvSetting(JComboBox<?> cbLoc) {
			// 선택한 지역 번호 반환
			int num = cbLoc.getSelectedIndex() - 1;

			if (num >= 0) {
				locationNum = tLocList.get(num).getTlNo();
				System.out.println(locationNum);
			} else
				locationNum = 0;

			// 선택한 지역에 해당하는 극장들 출력
			theaterList = theaterDao.selectTheaterByLoc(locationNum);

			cbTheater.removeAllItems();
			cbTheater.addItem("선택");

			for (int b = 0; b < theaterList.size(); b++) {
				cbTheater.addItem(theaterList.get(b).getTname());
			}
		}

		protected void cbTheaterEvSetting(JComboBox<?> cbTheater) {
			// 선택한 극장 번호 반환
			int num = cbTheater.getSelectedIndex() - 1;

			if (num >= 0) {
				theaterNum = theaterList.get(num).getTno();
				theaterName = theaterList.get(num).getTname();
				System.out.println(theaterNum);
			} else
				theaterNum = 0;

			// 수정
		}
		
		protected void resetLoc() {

			tLocList = tLocDao.selectTheaterLoc();

			resetComboBox(cbLoc);
			for (TheaterLocation a : tLocList) {
				cbLoc.addItem(a.getLocaction());
			}
		}

		protected void resetMenu() {

			tbCnt = 0;
			jTable.plTable.setVisible(false);
			cbScrDay.setSelectedIndex(0);
			cbScrMonth.setSelectedIndex(0);
			cbScrYear.setSelectedIndex(0);
		}

	}

	// 7. 상영시간 수정
	public class ModifyScrTime extends JPanel {

		String[] scrHours = { "선택", "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13",
				"14", "15", "16", "17", "18", "19", "20", "21", "22", "23" };
		String[] scrMins = { "선택", "00", "10", "20", "30", "40", "50" };

		String scrHour;
		String scrMin;

		// 1. 영화 상영시간 수정
		JPanel plModifyFmNorth = new JPanel();
		JPanel plModifyFmNNorth = new JPanel();
		JPanel plModifyFmNCenter = new JPanel();
		JPanel plModifyFmNSouth = new JPanel();

		JLabel lbLoc = new JLabel("지역", JLabel.CENTER);
		JLabel lbTheater = new JLabel("극장", JLabel.CENTER);
		JLabel lbScreen = new JLabel("상영관", JLabel.CENTER);
		JLabel lbScrFilm = new JLabel("영화", JLabel.CENTER);
		JLabel lbScrTime = new JLabel("상영시간", JLabel.CENTER);
		JLabel lbScrHour = new JLabel("시", JLabel.CENTER);
		JLabel lbScrMin = new JLabel("분", JLabel.CENTER);

		JComboBox<String> cbLoc;
		JComboBox<String> cbTheater = new JComboBox();
		JComboBox<String> cbScreen = new JComboBox<>();
		JComboBox<String> cbFilm = new JComboBox<>();
		JComboBox<String> cbScrHour = new JComboBox<>(scrHours);
		JComboBox<String> cbScrMin = new JComboBox<>(scrMins);

		DefaultListModel<String> model = new DefaultListModel<>();
		JList<String> ltScrTime = new JList<>();
		JScrollPane scScrTime = new JScrollPane();

		JButton btModifyFm = new JButton("변경");
		JButton btCheckModifyFm = new JButton("조회");
		
		ScrTimeTable plScrTimeTable = new ScrTimeTable();

		public ModifyScrTime() {

			// 1. 상영기간 수정
			setLayout(new BorderLayout());

			add(plModifyFmNorth, BorderLayout.NORTH);
			setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 15));

			plModifyFmNorth.setLayout(new BorderLayout());
			plModifyFmNorth.setBorder(BorderFactory.createTitledBorder(null, "극장", TitledBorder.LEFT,
					TitledBorder.DEFAULT_POSITION, gothic(15)));

			plModifyFmNorth.add(plModifyFmNNorth, BorderLayout.NORTH);
			plModifyFmNorth.add(plModifyFmNCenter, BorderLayout.CENTER);
			plModifyFmNorth.add(plModifyFmNSouth, BorderLayout.SOUTH);

			plModifyFmNSouth.setLayout(new BorderLayout());

			JPanel plModifyFmSLeft = new JPanel();
			JPanel plModifyFmSRight = new JPanel();

			plModifyFmNSouth.add(plModifyFmSLeft, BorderLayout.NORTH);

			// 1.1 상단메뉴
			location[0] = "선택";

			for (int i = 1; i < tLocList.size() + 1; i++) {
				location[i] = tLocList.get(i - 1).getLocaction();
			}

			cbLoc = new JComboBox<>(location);

			plModifyFmNNorth.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));
			plModifyFmNNorth.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

			plModifyFmNNorth.add(lbLoc);
			plModifyFmNNorth.add(cbLoc);
			plModifyFmNNorth.add(lbTheater);
			plModifyFmNNorth.add(cbTheater);
			plModifyFmNNorth.add(lbScreen);
			plModifyFmNNorth.add(cbScreen);

			setComboBox(cbLoc);
			initializeComboBox(cbTheater);
			initializeComboBox(cbScreen);

			lbTheater.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
			lbScreen.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

			lbLoc.setFont(gothic(15));
			lbTheater.setFont(gothic(15));
			lbScreen.setFont(gothic(15));

			setBackground(Color.white);
			plModifyFmNorth.setBackground(Color.white);
			plModifyFmNNorth.setBackground(Color.white);

			// 중단메뉴
			plModifyFmNCenter.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));
			plModifyFmNCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

			plModifyFmNCenter.add(lbScrFilm);
			plModifyFmNCenter.add(cbFilm);

			initializeComboBox(cbFilm);

			lbScrFilm.setFont(gothic(15));
//			cbFilm.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));

			plModifyFmNCenter.setBackground(Color.white);

			// 1.3 하단메뉴
			// 1.3.1 좌측메뉴
			plModifyFmSLeft.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));
			plModifyFmSLeft.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

			scScrTime = new JScrollPane(ltScrTime);

			plModifyFmSLeft.add(lbScrTime);
			plModifyFmSLeft.add(scScrTime);
			plModifyFmSLeft.add(plModifyFmSRight);

			ltScrTime.setFont(new Font("맑은고딕", Font.CENTER_BASELINE, 15));
			lbScrTime.setBorder(BorderFactory.createEmptyBorder(0, 0, 105, 5));

			scScrTime.setPreferredSize(new Dimension(100, 120));

			ltScrTime.setCellRenderer(new DefaultListCellRenderer() {
				public int getHorizontalAlignment() {
					return CENTER;
				}
			});

			ltScrTime.setBackground(Color.white);
			plModifyFmSLeft.setBackground(Color.white);

			// 1.3.2. 우측메뉴
			plModifyFmSRight.setLayout(new BorderLayout());

			JPanel plModifyFmSRNorth = new JPanel();
			JPanel plModifyFmSRSouth = new JPanel();

			plModifyFmSRight.add(plModifyFmSRNorth, BorderLayout.NORTH);
			plModifyFmSRight.add(plModifyFmSRSouth, BorderLayout.SOUTH);

			// 1.3.2.1 우측 상단메뉴
			plModifyFmSRNorth.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));
			plModifyFmSRNorth.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

			plModifyFmSRNorth.add(cbScrHour);
			plModifyFmSRNorth.add(lbScrHour);
			plModifyFmSRNorth.add(cbScrMin);
			plModifyFmSRNorth.add(lbScrMin);

			// 1.3.2.1 우측 하단메뉴
			plModifyFmSRSouth.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));
			plModifyFmSRSouth.setBorder(BorderFactory.createEmptyBorder(0, 10, 40, 0));

			plModifyFmSRSouth.add(btModifyFm);
			plModifyFmSRSouth.add(btCheckModifyFm);	

			setComboBox(cbScrHour);
			setComboBox(cbScrMin);

			lbScrMin.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 7));

			lbScrTime.setFont(gothic(15));
			lbScrHour.setFont(gothic(15));
			lbScrMin.setFont(gothic(15));
			btModifyFm.setFont(gothic(13));
			btCheckModifyFm.setFont(gothic(13));

			plModifyFmSRight.setBackground(Color.white);
			plModifyFmSLeft.setBackground(Color.white);
			plModifyFmSRNorth.setBackground(Color.white);
			plModifyFmSRSouth.setBackground(Color.white);
			
			add(plScrTimeTable, BorderLayout.CENTER);

			// 1.4 액션리스너 추가
			btModifyFm.addActionListener(new ModifyFilmPeriodEv());
			btCheckModifyFm.addActionListener(new ModifyFilmPeriodEv());

			ltScrTime.addListSelectionListener(new ListEv());

			cbFilm.addActionListener(new ModifyFilmPeriodEv());
			cbLoc.addActionListener(new ModifyFilmPeriodEv());
			cbScreen.addActionListener(new ModifyFilmPeriodEv());
			cbTheater.addActionListener(new ModifyFilmPeriodEv());
			cbScrHour.addActionListener(new ModifyFilmPeriodEv());
			cbScrMin.addActionListener(new ModifyFilmPeriodEv());
		}
		
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
							
				plTable.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
				plTable.setVisible(false);
				
				// 열 셀높이 설정
				tbSearch.getTableHeader().setPreferredSize(new Dimension(1000, 30));
				tbSearch.setRowHeight(25);
				
//				tbSearch.getTableHeader().setReorderingAllowed(false); //순서 변경불가
//				tbSearch.getTableHeader().setResizingAllowed(false); //크기 고정
				tbSearch.getTableHeader().resizeAndRepaint();
				
//				tbSearch.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				
				//스크롤 크기설정
				scrollTable.setPreferredSize(new Dimension(460, 200));
				
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
		

		public class ListEv implements ListSelectionListener {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				if (ltScrTime.getSelectedValue() == null) {
					scrTimeNum = 0;
					System.out.println("scrtime " + scrTimeNum);
				}

				else {
					String scrTime = ltScrTime.getSelectedValue();

					scrTimeNum = scrTimeDao.getStnoByScrTimeAndSno(scrTime, screenNum);
					System.out.println("scrtime " + scrTimeNum);
				}
			}
		}

		// 1. 상영시간 수정 액션리스터
		public class ModifyFilmPeriodEv implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {

				// 1. 극장에 상영기간 수정
				// 1. 지역 콤보박스 이벤트
				if (e.getSource() == cbLoc) {
					cbLocEvSetting(cbLoc);
				}

				// 2. 극장 영화관 콤보박스 이벤트
				else if (e.getSource() == cbTheater) {
					cbTheaterEvSetting(cbTheater);
				}
				// 3. 스크린 콤보박스 이벤트
				else if (e.getSource() == cbScreen) {
					cbScreenEvSetting(cbScreen);
				}

				else if (e.getSource() == cbFilm) {
					cbFilmEvSetting(cbFilm);
					resetList();
					cbScrHour.setSelectedIndex(0);
					cbScrMin.setSelectedIndex(0);
				}

				else if (e.getSource() == cbScrHour) {
					scrHour = comboBoxEvSetting(cbScrHour);
//					System.out.println("hour " + scrHour);
					
					if (scrHour.length() > 0) {
						if (cbFilm.getSelectedIndex() > 0) {
							// 시간중복 체크
							if (checkDupTime(cbScrHour)) {
								p0InformrMsg(plModifyFmNorth, "중복된 시간입니다.");
								cbScrHour.setSelectedIndex(0);
								scrHour = "";
								System.out.println("hour " + scrHour);
							}
						}
					} 
				}

				else if (e.getSource() == cbScrMin) {
					scrMin = comboBoxEvSetting(cbScrMin);

					if (scrMin.length() > 0) {
						if (cbFilm.getSelectedIndex() > 0)
							// 시간중복 체크
							if (checkDupTime(cbScrMin)) {
							p0InformrMsg(plModifyFmNorth, "중복된 시간입니다.");
							cbScrMin.setSelectedIndex(0);
							scrMin = "";
							System.out.println("min " + scrMin);
							}
					}
				}

				if (e.getSource() == btModifyFm) {

					String scrTime = scrHour + ":" + scrMin;
					System.out.println("scrDate " + scrTime);

					if (theaterNum == 0 || screenNum == 0 || scrTime.length() != 5 || scrTimeNum == 0 || filmNum == 0) {
						p0InformrMsg(plModifyFmNorth, "데이터를 입력하거나 박스를 선택해주세요");
					}

					else {

						if (scrTimeDao.updateScreenTime(scrTime, scrTimeNum) > 0) {
							plScrTimeTable.setTable();
							p0InformrMsg(plModifyFmNorth, "성공적으로 변경되었습니다.");
							resetList();
							cbScrMin.setSelectedIndex(0);

						} else {
							p0InformrMsg(plModifyFmNorth, "데이터 변경이 실패하였습니다.");
						}
					}
				}
				
				else if (e.getSource() == btCheckModifyFm) {
					
					if (cbScreen.getSelectedIndex() > 0) {
						
						tbCnt++;
						if (tbCnt % 2 == 1) {
							plScrTimeTable.setTable();
						} else
							plScrTimeTable.plTable.setVisible(false);
					} else 
						p0InformrMsg(plModifyFmNorth, "상영관 박스를 선택해주세요");
					
				}

			}
		}
		
		// 시간 중복 체크
		protected boolean checkDupTime(JComboBox<?> cbTime) {
			
			String scrTime = scrHour + ":" + scrMin;

			// 시간 중복 체크
			ArrayList<Integer> scrNumList = new ArrayList<>();

			for (int a : filmNumes) {
				scrNumList.add(scrDao.selectScreenBySname_Tno_fno(screenName, theaterNum, a).getSno());
			}
			
			int scrTimeSize = 0;
			
			if (scrNumList.size() > 0)
				scrTimeSize = scrTimeDao.checkScrTimeCount(scrNumList);

			System.out.println("scrTimeSize" + scrTimeSize);
			
			return scrTimeDao.checkDupScrTime(scrNumList, scrTime) && scrTimeSize > 0;
			
//			if (scrTimeDao.checkDupScrTime(scrNumList, scrTime) && scrTimeSize > 0) {
//				p0InformrMsg(plModifyFmNorth, "중복된 시간입니다.");
//				cbTime.setSelectedIndex(0);
//			}
		}

		protected void cbLocEvSetting(JComboBox<?> cbLoc) {
			// 선택한 지역 번호 반환
			int num = cbLoc.getSelectedIndex() - 1;

			if (num >= 0) {
				locationNum = tLocList.get(num).getTlNo();
				System.out.println(locationNum);
			} else
				locationNum = 0;

			// 선택한 지역에 해당하는 극장들 출력
			theaterList = theaterDao.selectTheaterByLoc(locationNum);

			cbTheater.removeAllItems();
			cbTheater.addItem("선택");

			for (int b = 0; b < theaterList.size(); b++) {
				cbTheater.addItem(theaterList.get(b).getTname());
			}
		}

		protected void cbTheaterEvSetting(JComboBox<?> cbTheater) {
			// 선택한 극장 번호 반환
			int num = cbTheater.getSelectedIndex() - 1;

			if (num >= 0) {
				theaterNum = theaterList.get(num).getTno();
				System.out.println(theaterNum);
			} else
				theaterNum = 0;

			// 상영시간 추가 메뉴에서 선택시 스크린 상영관 정보 반환
			ArrayList<String> scrNames = scrDao.getScrNameNotDup(theaterNum);

			cbScreen.removeAllItems();
			cbScreen.addItem("선택");

			for (int b = 0; b < scrNames.size(); b++) {
				cbScreen.addItem(scrNames.get(b));
			}
		}

		protected void cbScreenEvSetting(JComboBox<?> cbScreen) {

			int num = cbScreen.getSelectedIndex();

			if (num > 0) {
				
				tbCnt = 0;
				
				screenName = (String) cbScreen.getSelectedItem();
				screenList = scrDao.selectScreenBySnameAndTno(screenName, theaterNum);

				ArrayList<Integer> Numes = new ArrayList<>();

				System.out.println(screenName);

				String str = "  선택";

				cbFilm.removeAllItems();
				cbFilm.addItem(str);
				
				for (Screen a : screenList) {
					
					Film film = flDao.selectFilmByFno(a.getFno());

					Numes.add(a.getFno());
					
					if (film != null)
						cbFilm.addItem("  " +film.getFilmTitle());
				}
				
				System.out.println(cbFilm.getPreferredSize());
				comboBoxSetAlignLeft(cbFilm);
				
				if (cbFilm.getPreferredSize().getWidth() >= 370) {
					cbFilm.setPreferredSize(new Dimension(370, 28));
					
				} else {
					cbFilm.setPreferredSize(null);
				}
				
				Object comp = cbFilm.getUI().getAccessibleChild(cbFilm, 0);
				
			    JPopupMenu popup = (JPopupMenu) comp;
				JScrollPane scrollPane = (JScrollPane) popup.getComponent(0);

				scrollPane.setHorizontalScrollBar(new JScrollBar(JScrollBar.HORIZONTAL));
				scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				
				filmNumes = Numes;
			} else {
				screenNum = 0;
				screenName = "";
				resetComboBox(cbFilm);
			}
		}

		protected void cbFilmEvSetting(JComboBox<?> cbFilm) {

			int num = cbFilm.getSelectedIndex() - 1;

			if (num >= 0) {

				System.out.println();
				filmNum = filmNumes.get(num);

				Screen scr = scrDao.selectScreenBySname_Tno_fno(screenName, theaterNum, filmNum);

				System.out.println(filmNum);

				screenNum = scr.getSno();
				System.out.println(screenNum);

			} else
				filmNum = 0;
		}

		protected void resetList() {

			scrTimeList = scrTimeDao.selectScrTimeByScreen(screenNum);

			model.removeAllElements();

			for (ScreenTime a : scrTimeList) {
				model.addElement(a.getSttime());
			}
			ltScrTime.setModel(model);
		}

		protected void resetMenu() {
			
			tbCnt = 0;
			plScrTimeTable.plTable.setVisible(false);
			cbLoc.setSelectedIndex(0);
			cbTheater.setSelectedIndex(0);
			cbScreen.setSelectedIndex(0);
			cbScrHour.setSelectedIndex(0);
			cbScrMin.setSelectedIndex(0);

			resetComboBox(cbFilm);
		}

		protected void resetLoc() {

			tLocList = tLocDao.selectTheaterLoc();

			resetComboBox(cbLoc);
			for (TheaterLocation a : tLocList) {
				cbLoc.addItem(a.getLocaction());
			}
		}
	}

	protected String comboBoxEvSetting(JComboBox<?> comboBox) {

		String cbItem = "";

		if (comboBox.getSelectedIndex() > 0) {
			cbItem = (String) comboBox.getSelectedItem();
		} else
			cbItem = "";

		return cbItem;
	}

	protected void setComboBox(JComboBox<String> cb) {

		cb.setBackground(Color.white);
		cb.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 13));

		comboBoxSetAlignCenter(cb);
	}

	protected void resetComboBox(JComboBox<String> cb) {

		String a = " 선택 ";

		cb.removeAllItems();
		cb.addItem(a);
	}
	
	protected void initializeComboBox(JComboBox<String> cb) {

		resetComboBox(cb);
		cb.setBackground(Color.white);
		cb.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 13));

		comboBoxSetAlignCenter(cb);
	}
	
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


	protected void setList(JList<String> list, int fontSize, String Align) {

		if (Align.equals("CENTER")) {

			list.setCellRenderer(new DefaultListCellRenderer() {
				public int getHorizontalAlignment() {
					return CENTER;
				}
			});
		}

		if (Align.equals("LEFT")) {

			list.setCellRenderer(new DefaultListCellRenderer() {
				public int getHorizontalAlignment() {
					return LEFT;
				}
			});
		}

		list.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		list.setFont(gothic(fontSize));
		list.setBackground(Color.white);
	}

	protected boolean checkInt(String num) {
		try {

			int stringToInt = Integer.parseInt(num);

			if (stringToInt < 0)
				throw new Exception();

		} catch (Exception e2) {
			return false;
		}
		return true;
	}

	protected void p0InformrMsg(Component parentComponent, String error) {
		UIManager.put("OptionPane.messageFont", new Font("맑은 고딕", Font.CENTER_BASELINE, 13));
		UIManager.put("OptionPane.buttonFont", new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);

		JOptionPane.showMessageDialog(parentComponent, error, "Inform", JOptionPane.INFORMATION_MESSAGE);
	}

	protected Font gothic(int size) {
		return new Font("맑은 고딕", Font.CENTER_BASELINE, size);
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
}
