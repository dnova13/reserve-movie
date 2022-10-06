package reserve;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
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
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class P0C_DeleteMenu extends JPanel {
		
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
	
	int filmNum;
	int filmOrder;
	int locationNum;
	int theaterNum;
	int screenNum;
	int scrTimeNum;
	
	String scrYear="";
	String scrMonth="";
	String scrDay="";
	String screenName = "";
	String[] Month = { "선택", "01", "02", "03", "04", "05", "06","07","08","09","10","11","12" };
	String[] Day = { "선택","01","02","03","04","05","06","07","08", "09", "10", "11", "12",
					"13", "14","15", "16", "17", "18", "19", "20", "21","22","23","24","25"
					,"26","27","28","29","30","31"};
	String[] location = new String[tLocList.size() + 1];
	
	JTabbedPane tbDeleteMenu = new JTabbedPane(JTabbedPane.LEFT);
	
	DeleteFilm plDelFm = new DeleteFilm();
	DeleteLocation plDelLoc = new DeleteLocation();
	DeleteTheater plDelTheater = new DeleteTheater();
	DeleteScreen plDelScreen = new DeleteScreen();
	DeleteScrTime plDelScrTime = new DeleteScrTime();
	
	public P0C_DeleteMenu() {

		setLayout(new BorderLayout());
		
		tbDeleteMenu.addTab("영화 삭제", plDelFm);
		tbDeleteMenu.addTab("지역 삭제", plDelLoc);
		tbDeleteMenu.addTab("극장 삭제", plDelTheater);
		tbDeleteMenu.addTab("상영관 삭제", plDelScreen);
		tbDeleteMenu.addTab("상영시간 삭제", plDelScrTime);
		
		tbDeleteMenu.setBackground(Color.white);
		tbDeleteMenu.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 14));

		tbDeleteMenu.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				
				resetMenu(tbDeleteMenu.getSelectedIndex());
			}
		});
		
		setBackground(Color.white);
		add(tbDeleteMenu, BorderLayout.CENTER);
	}
	
	public void resetMenu(int i) {
		
		if (i == 0) {
			plDelFm.resetFilmList();
		}
		
		else if (i == 1) {
			plDelLoc.resetLocList();
		} 
		
		else if (i == 2){
			plDelTheater.resetLoc();
		}
		
		else if (i == 3){
			plDelScreen.resetLoc();
			plDelScreen.resetMenu();
		}
		
		else {
			plDelScrTime.resetMenu();
			plDelScrTime.resetLoc();

		}
	}
	
	//영화 삭제
	public class DeleteFilm extends JPanel {
		
		//1. 영화 상영기간 수정
		JPanel plDelFmNorth = new JPanel();
		JPanel plDelFmNNorth = new JPanel();
		JPanel plDelFmNSouth = new JPanel();
		
		JLabel lbFilm = new JLabel("영화", JLabel.CENTER);
		
		DefaultListModel<String> model = new DefaultListModel<>();
		JList<String> ltFilm = new JList<>();
		JScrollPane scFilm = new JScrollPane();
		
		JButton btDelFm = new JButton("삭제");
			
		public DeleteFilm() {
			
			setLayout(new BorderLayout());
			
			add(plDelFmNorth, BorderLayout.NORTH);
			setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 15));
			
						plDelFmNorth.setLayout(new BorderLayout());
			plDelFmNorth.setBorder(BorderFactory.createTitledBorder(null, "영화",
					TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, gothic(15)));
			
			plDelFmNorth.add(plDelFmNNorth, BorderLayout.NORTH);
			plDelFmNorth.add(plDelFmNSouth, BorderLayout.SOUTH);
			
			plDelFmNSouth.setLayout(new BorderLayout());
			
			// 1.1 상단메뉴
			scFilm = new JScrollPane(ltFilm);
			
			resetFilmList();
			setList(ltFilm,13,"LEFT");
			
			JPanel plBtDelFm = new JPanel();
			
			plBtDelFm.setLayout(new BorderLayout());
			
			scFilm.setPreferredSize(new Dimension(340, 180));
						
			plDelFmNNorth.setLayout(new FlowLayout(FlowLayout.CENTER, 7, 10));
			plDelFmNNorth.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
			
			plDelFmNNorth.add(scFilm);
			plDelFmNNorth.add(Box.createHorizontalStrut(1));
			plDelFmNNorth.add(plBtDelFm);
			
			plBtDelFm.add(btDelFm);
			
			lbFilm.setBorder(BorderFactory.createEmptyBorder(0, 0, 150, 10));
			plBtDelFm.setBorder(BorderFactory.createEmptyBorder(0, 0, 150, 0));
			
			lbFilm.setFont(gothic(15));
			btDelFm.setFont(gothic(13));
			
			setBackground(Color.white);
			plBtDelFm.setBackground(Color.white);
			plDelFmNorth.setBackground(Color.white);
			plDelFmNNorth.setBackground(Color.white);
		
			//1.4 액션리스너 추가
			btDelFm.addActionListener(new btActionEv());
			ltFilm.addListSelectionListener(new ListActionEv());
		}
		
		public class ListActionEv implements ListSelectionListener {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				if (ltFilm.getSelectedValue() == null) {
					filmNum = 0;
					System.out.println("filmNum " + filmNum);
				}

				else {
					int num = ltFilm.getSelectedIndex();

					filmNum = filmList.get(num).getFilmNo();
					filmOrder = filmList.get(num).getFilmOrder();
					System.out.println("filmNum " + filmNum);
					System.out.println("filmorder " + filmOrder);
				}				
			}
			
		}
		
		//
		public class btActionEv implements ActionListener {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				if (filmNum == 0) {
					p0InformrMsg(plDelFmNorth, "데이터를 입력하거나 박스를 선택해주세요");
				}

				else {
					
					if (p0C_CancelMsg(plDelFmNorth) == JOptionPane.YES_OPTION) {

						if (flDao.deleteByNum(filmNum) > 0) {
							p0InformrMsg(plDelFmNorth, "성공적으로 삭제되었습니다.");
							
							// 필름 순서를 재설정
							for (int i = filmOrder; i < filmList.size(); i++) {
								flDao.resetFilmOrder(i + 1, i);
							}
							
							resetFilmList();

						} else {
							p0InformrMsg(plDelFmNorth, "데이터 삭제가 실패하였습니다.");
						}
					}
				}

			}
		}
		
		protected void resetFilmList() {

			filmList = flDao.selectFilm();

			model.removeAllElements();

			for (Film a : filmList) {
				model.addElement(a.getFilmOrder()+". " + a.getFilmTitle());
			}
			ltFilm.setModel(model);
		}
		
		
	}
		
	//지역 삭제
	public class DeleteLocation extends JPanel {
		
		//1. 영화 상영기간 수정
		JPanel plDleteFmNorth = new JPanel();
		JPanel plDleteFmNNorth = new JPanel();
		JPanel plDleteFmNSouth = new JPanel();
		
		JLabel lbLoc = new JLabel("지역", JLabel.CENTER);
		
		DefaultListModel<String> model = new DefaultListModel<>();
		JList<String> ltLoc = new JList<>();
		JScrollPane scLoc = new JScrollPane();
		
		JButton btDelFm = new JButton("삭제");
			
		public DeleteLocation() {
			
			setLayout(new BorderLayout());
			
			add(plDleteFmNorth, BorderLayout.NORTH);
			setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 15));
			
						plDleteFmNorth.setLayout(new BorderLayout());
			plDleteFmNorth.setBorder(BorderFactory.createTitledBorder(null, "극장",
					TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, gothic(15)));
			
			plDleteFmNorth.add(plDleteFmNNorth, BorderLayout.NORTH);
			plDleteFmNorth.add(plDleteFmNSouth, BorderLayout.SOUTH);
			
			plDleteFmNSouth.setLayout(new BorderLayout());
			
			// 1.1 상단메뉴
			scLoc = new JScrollPane(ltLoc);
			
			resetLocList();
			setList(ltLoc,14,"CENTER");
			
			JPanel plBtDelFm = new JPanel();
			
			plBtDelFm.setLayout(new BorderLayout());
			
			scLoc.setPreferredSize(new Dimension(160, 150));
			
			lbLoc.setFont(gothic(15));
			
			plDleteFmNNorth.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));
			plDleteFmNNorth.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
			
			plDleteFmNNorth.add(lbLoc);
			plDleteFmNNorth.add(scLoc);
			plDleteFmNNorth.add(Box.createHorizontalStrut(5));
			plDleteFmNNorth.add(plBtDelFm);
			
			plBtDelFm.add(btDelFm);
			
			lbLoc.setBorder(BorderFactory.createEmptyBorder(0, 0, 130, 10));
			plBtDelFm.setBorder(BorderFactory.createEmptyBorder(0, 0, 120, 0));
			
			lbLoc.setFont(gothic(15));
			btDelFm.setFont(gothic(13));
			
			setBackground(Color.white);
			plBtDelFm.setBackground(Color.white);
			plDleteFmNorth.setBackground(Color.white);
			plDleteFmNNorth.setBackground(Color.white);
		
			//1.4 액션리스너 추가
			btDelFm.addActionListener(new BtActionEv());
			ltLoc.addListSelectionListener(new ListActionEv());
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

				if (locationNum == 0) {
					p0InformrMsg(plDleteFmNorth, "데이터를 입력하거나 박스를 선택해주세요");
				}

				else {
					
					if (p0C_CancelMsg(plDleteFmNorth) == JOptionPane.YES_OPTION) {

						if (tLocDao.deleteLocByLocNum(locationNum) > 0) {
							p0InformrMsg(plDleteFmNorth, "성공적으로 삭제되었습니다.");
							resetLocList();

						} else {
							p0InformrMsg(plDleteFmNorth, "데이터 삭제가 실패하였습니다.");
						}
					}
				}
			}
		}
		
		protected void resetLocList() {

			tLocList = tLocDao.selectTheaterLoc();

			model.removeAllElements();

			for (TheaterLocation a : tLocList) {
				model.addElement(a.getLocaction());
			}
			ltLoc.setModel(model);
		}
	}
		
	//극장 삭제
	public class DeleteTheater extends JPanel {
		
		//1. 영화 상영기간 수정
		JPanel plDelThNorth = new JPanel();
		JPanel plDelThNNorth = new JPanel();
		JPanel plDelThNCenter = new JPanel();
		JPanel plDelThNSouth = new JPanel();
		
		JLabel lbLoc = new JLabel("지역", JLabel.CENTER);
		JLabel lbTheater = new JLabel("극장", JLabel.CENTER);
		
		JComboBox<String> cbLoc;
	
		DefaultListModel<String> model = new DefaultListModel<>();
		JList<String> ltTheater = new JList<>();
		JScrollPane scTheater = new JScrollPane();
		
		JButton btDelTheater = new JButton("삭제");

		public DeleteTheater() {
			
			//1. 상영기간 수정		
			setLayout(new BorderLayout());
			
			add(plDelThNorth, BorderLayout.NORTH);
			setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 15));
						
			plDelThNorth.setLayout(new BorderLayout());
			plDelThNorth.setBorder(BorderFactory.createTitledBorder(null, "극장",
					TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, gothic(15)));
			
			plDelThNorth.add(plDelThNNorth, BorderLayout.NORTH);
			
			plDelThNSouth.setLayout(new BorderLayout());

			for (int i = 1; i < tLocList.size() + 1; i++) {
				location[i] = tLocList.get(i - 1).getLocaction();
			}
			
			cbLoc = new JComboBox<>(location);
			setComboBox(cbLoc);
			
			scTheater = new JScrollPane(ltTheater);
			scTheater.setPreferredSize(new Dimension(100, 120)); 
			
			JPanel plcbDel = new JPanel();
			plcbDel.setLayout(new BorderLayout());
			
			JPanel plbtDel = new JPanel();
			plbtDel.setLayout(new BorderLayout());
			
			plDelThNNorth.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));
			plDelThNNorth.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
			
			plDelThNNorth.add(lbLoc);
			plDelThNNorth.add(plcbDel);
			plDelThNNorth.add(lbTheater);
			plDelThNNorth.add(scTheater);
			plDelThNNorth.add(plbtDel);
			
			plcbDel.add(cbLoc);
			plbtDel.add(btDelTheater);
			
			lbLoc.setBorder(BorderFactory.createEmptyBorder(0, 0, 100, 0));
			plcbDel.setBorder(BorderFactory.createEmptyBorder(0, 0, 90, 0));
			lbTheater.setBorder(BorderFactory.createEmptyBorder(0, 10, 100, 0));
			plbtDel.setBorder(BorderFactory.createEmptyBorder(0, 0, 90, 0));

			lbLoc.setFont(gothic(15));
			lbTheater.setFont(gothic(15));
			btDelTheater.setFont(gothic(13));
			
			setList(ltTheater, 14, "CENTER");
			
			setBackground(Color.white);
			plbtDel.setBackground(Color.white);
			plcbDel.setBackground(Color.white);
			plDelThNorth.setBackground(Color.white);
			plDelThNNorth.setBackground(Color.white);
			
			//1.4 액션리스너 추가
			btDelTheater.addActionListener(new DelThActionEv());
			ltTheater.addListSelectionListener(new ListEv());
			cbLoc.addActionListener(new DelThActionEv());
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
		
		//1. 상영기간 수정 액션리스터
		public class DelThActionEv implements ActionListener {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				//1. 극장에 상영기간 수정
				//1. 지역 콤보박스 이벤트
				if (e.getSource() == cbLoc) {
					cbLocEvSetting(cbLoc);
					resetList();
				}
				
				
				if (e.getSource() == btDelTheater) {
						
					if (theaterNum == 0) {
						p0InformrMsg(plDelThNorth, "데이터를 입력하거나 박스를 선택해주세요");
					}
					
					else {
						
						if (p0C_CancelMsg(plDelThNorth) == JOptionPane.YES_OPTION) {

							if (theaterDao.deleteTheaterBythNum(theaterNum) > 0) {
								p0InformrMsg(plDelThNorth, "성공적으로 삭제되었습니다.");
								resetList();

							} else {
								p0InformrMsg(plDelThNorth, "데이터 삭제가 실패하였습니다.");
							}
						}
					}
				}

			}
		}
		
		protected void cbLocEvSetting (JComboBox<?> cbLoc) {
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
			
			for(Theater a:theaterList) {
				model.addElement(a.getTname());
			}
			ltTheater.setModel(model);
		}
		
		protected void resetMenu() {

			cbLoc.setSelectedIndex(0);
		}
		
		protected void resetLoc() {

			tLocList = tLocDao.selectTheaterLoc();

			resetComboBox(cbLoc);
			for (TheaterLocation a : tLocList) {
				cbLoc.addItem(a.getLocaction());
			}
		}		
	}
	
	//상영관 삭제
	public class DeleteScreen extends JPanel {
		
		//1. 영화 상영기간 수정
		JPanel plScreenNorth = new JPanel();
		JPanel plScreenNNorth = new JPanel();
		JPanel plScreenNCenter = new JPanel();
		JPanel plScreenNSouth = new JPanel();
		
		JLabel lbLoc = new JLabel("지역", JLabel.CENTER);
		JLabel lbTheater = new JLabel("극장", JLabel.CENTER);
		JLabel lbScreen = new JLabel("상영관", JLabel.CENTER);
		JLabel lbScrFilm = new JLabel("영화", JLabel.CENTER);
		
		JComboBox<String> cbLoc;
		JComboBox<String> cbTheater = new JComboBox();
		JComboBox<String> cbFilm = new JComboBox<>();
		
		DefaultListModel<String> model = new DefaultListModel<>();
		JList<String> ltScreen = new JList<>();
		JScrollPane scScreen = new JScrollPane(ltScreen);
		
		JButton btScreen = new JButton("삭제");

		public DeleteScreen() {
			
			setLayout(new BorderLayout());
			
			add(plScreenNorth, BorderLayout.NORTH);
			setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 15));
			
			plScreenNorth.setLayout(new BorderLayout());
			plScreenNorth.setBorder(BorderFactory.createTitledBorder(null, "극장",
					TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, gothic(15)));
			
			plScreenNorth.add(plScreenNNorth, BorderLayout.NORTH);
			plScreenNorth.add(plScreenNCenter, BorderLayout.CENTER);
			plScreenNorth.add(plScreenNSouth, BorderLayout.SOUTH);
			
			plScreenNSouth.setLayout(new BorderLayout());
			
			JPanel plScreenSNorth = new JPanel();
			JPanel plScreenSSouth = new JPanel();
			
			plScreenNSouth.add(plScreenSNorth, BorderLayout.NORTH);
			plScreenNSouth.add(plScreenSSouth, BorderLayout.SOUTH);
			
			// 1.1 상단메뉴
			location[0] = "선택";

			for (int i = 1; i < tLocList.size() + 1; i++) {
				location[i] = tLocList.get(i - 1).getLocaction();
			}
			
			cbLoc = new JComboBox<>(location);
			
			plScreenNNorth.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));
			plScreenNNorth.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
			
			plScreenNNorth.add(lbLoc);
			plScreenNNorth.add(cbLoc);
			plScreenNNorth.add(lbTheater);
			plScreenNNorth.add(cbTheater);
			
			setComboBox(cbLoc);
			initializeComboBox(cbTheater);
			
			lbTheater.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
			lbScreen.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
					
			lbLoc.setFont(gothic(15));
			lbTheater.setFont(gothic(15));
			
			setBackground(Color.white);
			plScreenNorth.setBackground(Color.white);
			plScreenNNorth.setBackground(Color.white);
			
			// 중단메뉴
			plScreenNCenter.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));
			plScreenNCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
			
			plScreenNCenter.add(lbScreen);
			plScreenNCenter.add(scScreen);
			
			scScreen.setPreferredSize(new Dimension(100, 120));
			lbScreen.setBorder(BorderFactory.createEmptyBorder(0, 0, 100, 5));
			
			lbScreen.setFont(gothic(15));
			ltScreen.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 14));
		
			ltScreen.setCellRenderer(new DefaultListCellRenderer() {
				public int getHorizontalAlignment() {
					return CENTER;
				}
			});
			
			ltScreen.setBackground(Color.white);
			plScreenNCenter.setBackground(Color.white);
			
			//1.3 하단메뉴
			//1.3.1 상단메뉴
			plScreenSNorth.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));
			plScreenSNorth.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
			
			plScreenSNorth.add(lbScrFilm);
			plScreenSNorth.add(cbFilm);
			
			initializeComboBox(cbFilm);
			
			lbScrFilm.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
			
			lbScrFilm.setFont(gothic(15));
			plScreenSNorth.setBackground(Color.white);
			
			//1.3.2. 하단메뉴
			plScreenSSouth.setLayout(new BorderLayout());
			plScreenSSouth.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));
			plScreenSSouth.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
			
			plScreenSSouth.add(btScreen);
			
			btScreen.setFont(gothic(13));
			
			plScreenSSouth.setBackground(Color.white);
			plScreenSNorth.setBackground(Color.white);
			
			//1.4 액션리스너 추가
			btScreen.addActionListener(new ScreenEv());
			cbFilm.addActionListener(new ScreenEv());
			cbLoc.addActionListener(new ScreenEv());
			cbTheater.addActionListener(new ScreenEv());
			
			ltScreen.addListSelectionListener(new ListEv());
		}
		
		public class ListEv implements ListSelectionListener {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				if (ltScreen.getSelectedValue() == null) {
					screenNum = 0;
					System.out.println("screenNum " + screenNum);
				} 
				
				else {
					screenName = ltScreen.getSelectedValue();
					screenList = scrDao.selectScreenBySnameAndTno(screenName, theaterNum);

					ArrayList<Integer> Numes = new ArrayList<>();

					System.out.println(screenName);

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
			}
		}
		
		// 상영관 삭제 액션리스터
		public class ScreenEv implements ActionListener {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				//1. 극장에 상영기간 수정
				//1. 지역 콤보박스 이벤트
				if (e.getSource() == cbLoc) {
					cbLocEvSetting(cbLoc);
				}
				
				//2. 극장 영화관 콤보박스 이벤트
				else if (e.getSource() == cbTheater) {
					cbTheaterEvSetting(cbTheater);
					resetList();
				}
				
				else if (e.getSource() == cbFilm) {
					cbFilmEvSetting(cbFilm);
				}
				
				if (e.getSource() == btScreen) {
					
					if (theaterNum == 0 || screenNum == 0 ||  filmNum < 0) {
						p0InformrMsg(plScreenNorth, "데이터를 입력하거나 박스를 선택해주세요");
					}
					
					else {
						
						if (p0C_CancelMsg(plScreenNorth) == JOptionPane.YES_OPTION) {
						
						if (scrDao.DeleteScreen(screenNum) > 0) {
							p0InformrMsg(plScreenNorth, "성공적으로 삭제되었습니다.");
							resetList();
							resetComboBox(cbFilm);
							
						} else {
							p0InformrMsg(plScreenNorth, "데이터 삭제가 실패하였습니다.");
						}	
					}
					}
					
				}
				
			}
		}
		
		protected void cbLocEvSetting (JComboBox<?> cbLoc) {
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
		
		protected void cbTheaterEvSetting (JComboBox<?> cbTheater) {
			// 선택한 극장 번호 반환
			int num = cbTheater.getSelectedIndex() - 1;

			if (num >= 0) {
				theaterNum = theaterList.get(num).getTno();
				System.out.println(theaterNum);
			} else
				theaterNum = 0;

			resetList();
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
		
		protected void resetList() {
			
			ArrayList<String> scrNames = scrDao.getScrNameNotDup(theaterNum);
			
			model.removeAllElements();
			
			for(String a:scrNames) {
				model.addElement(a);
			}
			ltScreen.setModel(model);
		}
		
		protected void resetMenu() {

			cbLoc.setSelectedIndex(0);
			cbTheater.setSelectedIndex(0);
			
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
	
	//상영시간 삭제
	public class DeleteScrTime extends JPanel {
		
		//1. 영화 상영시간 수정
		JPanel plDelScrTimeNorth = new JPanel();
		JPanel plDelScrTimeNNorth = new JPanel();
		JPanel plDelScrTimeNCenter = new JPanel();
		JPanel plDelScrTimeNSouth = new JPanel();
		
		JLabel lbLoc = new JLabel("지역", JLabel.CENTER);
		JLabel lbTheater = new JLabel("극장", JLabel.CENTER);
		JLabel lbScreen = new JLabel("상영관", JLabel.CENTER);
		JLabel lbScrFilm = new JLabel("영화", JLabel.CENTER);
		JLabel lbScrTime = new JLabel("상영시간", JLabel.CENTER);
		
		JComboBox<String> cbLoc;
		JComboBox<String> cbTheater = new JComboBox();
		JComboBox<String> cbScreen = new JComboBox<>();
		JComboBox<String> cbFilm = new JComboBox<>();
		
		DefaultListModel<String> model = new DefaultListModel<>();
		JList<String> ltScrTime = new JList<>();
		JScrollPane scScrTime = new JScrollPane();
		
		JButton btDelScrTime = new JButton("삭제");

		public DeleteScrTime() {
			
			//1. 상영시간 삭제	
			setLayout(new BorderLayout());
			
			add(plDelScrTimeNorth, BorderLayout.NORTH);
			setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 15));
			
			
			plDelScrTimeNorth.setLayout(new BorderLayout());
			plDelScrTimeNorth.setBorder(BorderFactory.createTitledBorder(null, "극장",
					TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, gothic(15)));
			
			plDelScrTimeNorth.add(plDelScrTimeNNorth, BorderLayout.NORTH);
			plDelScrTimeNorth.add(plDelScrTimeNCenter, BorderLayout.CENTER);
			plDelScrTimeNorth.add(plDelScrTimeNSouth, BorderLayout.SOUTH);
			
			plDelScrTimeNSouth.setLayout(new BorderLayout());
			
			JPanel plDelScrTimeSLeft = new JPanel();
			JPanel plDelScrTimeSRight = new JPanel();
			
			plDelScrTimeNSouth.add(plDelScrTimeSLeft, BorderLayout.NORTH);
			
			// 1.1 상단메뉴
			location[0] = "선택";

			for (int i = 1; i < tLocList.size() + 1; i++) {
				location[i] = tLocList.get(i - 1).getLocaction();
			}
			
			cbLoc = new JComboBox<>(location);
			
			plDelScrTimeNNorth.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));
			plDelScrTimeNNorth.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
			
			plDelScrTimeNNorth.add(lbLoc);
			plDelScrTimeNNorth.add(cbLoc);
			plDelScrTimeNNorth.add(lbTheater);
			plDelScrTimeNNorth.add(cbTheater);
			plDelScrTimeNNorth.add(lbScreen);
			plDelScrTimeNNorth.add(cbScreen);
			
			setComboBox(cbLoc);
			initializeComboBox(cbTheater);
			initializeComboBox(cbScreen);
			
			lbTheater.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
			lbScreen.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
					
			lbLoc.setFont(gothic(15));
			lbTheater.setFont(gothic(15));
			lbScreen.setFont(gothic(15));
			
			setBackground(Color.white);
			plDelScrTimeNorth.setBackground(Color.white);
			plDelScrTimeNNorth.setBackground(Color.white);
			
			// 중단메뉴
			plDelScrTimeNCenter.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));
			plDelScrTimeNCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
			
			plDelScrTimeNCenter.add(lbScrFilm);
			plDelScrTimeNCenter.add(cbFilm);
			
			initializeComboBox(cbFilm);
			
			lbScrFilm.setFont(gothic(15));
			
			plDelScrTimeNCenter.setBackground(Color.white);
			
			//1.3 하단메뉴
			//1.3.1 좌측메뉴
			plDelScrTimeSLeft.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));
			plDelScrTimeSLeft.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
			
			scScrTime = new JScrollPane(ltScrTime);
			
			plDelScrTimeSLeft.add(lbScrTime);
			plDelScrTimeSLeft.add(scScrTime);
			plDelScrTimeSLeft.add(plDelScrTimeSRight);
			
			ltScrTime.setFont(new Font("맑은고딕", Font.CENTER_BASELINE, 15));
			lbScrTime.setBorder(BorderFactory.createEmptyBorder(0, 0, 105, 5));

			scScrTime.setPreferredSize(new Dimension(100, 120));
			
			ltScrTime.setCellRenderer(new DefaultListCellRenderer() {
				public int getHorizontalAlignment() {
					return CENTER;
				}
			});
			
			ltScrTime.setBackground(Color.white);
			plDelScrTimeSLeft.setBackground(Color.white);
			
			//1.3.2. 우측메뉴
			plDelScrTimeSRight.setLayout(new BorderLayout());
			
			JPanel plDelScrTimeSRNorth = new JPanel();
			JPanel plBtDel = new JPanel();
			
			plDelScrTimeSRight.add(plDelScrTimeSRNorth,BorderLayout.NORTH);
			
			//1.3.2.1 우측 상단메뉴
			plDelScrTimeSRNorth.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));
			
			plDelScrTimeSRNorth.add(plBtDel);
			
			plBtDel.setLayout(new BorderLayout());
			plBtDel.add(btDelScrTime);
			
			plBtDel.setBorder(BorderFactory.createEmptyBorder(0, 0, 90, 0));
			
			lbScrTime.setFont(gothic(15));
			btDelScrTime.setFont(gothic(13));
			
			plBtDel.setBackground(Color.white);
			plDelScrTimeSRight.setBackground(Color.white);
			plDelScrTimeSLeft.setBackground(Color.white);
			plDelScrTimeSRNorth.setBackground(Color.white);
			
			//1.4 액션리스너 추가
			btDelScrTime.addActionListener(new DelScrTimeEv());
			cbFilm.addActionListener(new DelScrTimeEv());
			cbLoc.addActionListener(new DelScrTimeEv());
			cbScreen.addActionListener(new DelScrTimeEv());
			cbTheater.addActionListener(new DelScrTimeEv());
			
			ltScrTime.addListSelectionListener(new ListEv());
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
		
		//1. 상영시각 삭제 액션리스터
		public class DelScrTimeEv implements ActionListener {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				//1. 극장에 상영기간 수정
				//1. 지역 콤보박스 이벤트
				if (e.getSource() == cbLoc) {
					cbLocEvSetting(cbLoc);
				}
				
				//2. 극장 영화관 콤보박스 이벤트
				else if (e.getSource() == cbTheater) {
					cbTheaterEvSetting(cbTheater);
				}
				//3. 스크린 콤보박스 이벤트
				else if (e.getSource() == cbScreen) {
					cbScreenEvSetting(cbScreen);
				}
				
				else if (e.getSource() == cbFilm) {
					cbFilmEvSetting(cbFilm);
					resetList();
				}
					
				if (e.getSource() == btDelScrTime) {

					if (theaterNum == 0 || screenNum == 0 || scrTimeNum == 0 || filmNum == 0) {
						p0InformrMsg(plDelScrTimeNorth, "데이터를 입력하거나 박스를 선택해주세요");
					}

					else {

						if (p0C_CancelMsg(plDelScrTimeNorth) == JOptionPane.YES_OPTION) {

							if (scrTimeDao.DeleteScreenTime(scrTimeNum) > 0) {
								p0InformrMsg(plDelScrTimeNorth, "성공적으로 삭제되었습니다.");
								resetList();

							} else {
								p0InformrMsg(plDelScrTimeNorth, "데이터 삭제가 실패하였습니다.");
							}
						}
					}
				}
			}
		}
		
		protected void cbLocEvSetting (JComboBox<?> cbLoc) {
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
		
		protected void cbTheaterEvSetting (JComboBox<?> cbTheater) {
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

				screenName = (String) cbScreen.getSelectedItem();
				screenList = scrDao.selectScreenBySnameAndTno(screenName, theaterNum);

				ArrayList<Integer> Numes = new ArrayList<>();

				System.out.println(screenName);

				String str = "  선택";

				cbFilm.removeAllItems();
				cbFilm.addItem(str);
				
				for (Screen a : screenList) {

					Numes.add(a.getFno());
					
					Film film = flDao.selectFilmByFno(a.getFno());

					if (film != null)
						cbFilm.addItem("  " + film.getFilmTitle());
				}
				
				comboBoxSetAlignLeft(cbFilm);
				
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
			} else
				screenNum = 0;
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
			
			for(ScreenTime a:scrTimeList) {
				model.addElement(a.getSttime());
			}
			ltScrTime.setModel(model);
		}
		
		protected void resetMenu() {

			cbLoc.setSelectedIndex(0);
			cbTheater.setSelectedIndex(0);
			cbScreen.setSelectedIndex(0);
			
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
		
		String cbItem ="";
		
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

		String a = "선택";

		cb.removeAllItems();
		cb.addItem(a);
	}

	protected void initializeComboBox(JComboBox<String> cb) {

		resetComboBox(cb);
		cb.setBackground(Color.white);
		cb.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 13));

		comboBoxSetAlignCenter(cb);
	}
			
	protected boolean checkInt(String num) {
		try {

			int stringToInt = Integer.parseInt(num);
			
			if (stringToInt < 0) throw new Exception();

		} catch (Exception e2) {
			return false;
		}
		return true;
	}
	
	protected void setList(JList<String> list,int fontSize, String Align) {
		
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
	
	protected void p0InformrMsg(Component parentComponent, String error) {
		UIManager.put("OptionPane.messageFont", new Font("맑은 고딕", Font.CENTER_BASELINE, 13));
		UIManager.put("OptionPane.buttonFont", new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);

		JOptionPane.showMessageDialog(parentComponent, error, "Inform", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private int p0C_CancelMsg(Component parentComponent) {
		UIManager.put("OptionPane.messageFont", new Font("맑은 고딕",Font.CENTER_BASELINE,13));
		UIManager.put("OptionPane.buttonFont", new Font("맑은 고딕",Font.CENTER_BASELINE,15));
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);
		
		return JOptionPane.showConfirmDialog(parentComponent, "정말로 삭제하겠습니까?","삭제", JOptionPane.YES_NO_OPTION);
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
