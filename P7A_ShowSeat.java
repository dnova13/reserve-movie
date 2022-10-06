package reserve;

import java.awt.BorderLayout;
import java.awt.Color;
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
import java.util.LinkedList;

import javax.swing.*;

public class P7A_ShowSeat extends JDialog {
	
	ScreenSeatDao scrSeatDao = new ScreenSeatDao();
	TicketDao tckDao = new TicketDao();
	OrderDataDao ordDao = new OrderDataDao();
	
	static int ordNum;
	static int scrTimeNum;
	static long scrDayAndTime;
	
	static String PeopleNum; 
	static String price;
	static String filmName;
	
	int scrTotalColSeat = scrSeatDao.getTotalColSeatFromStno(scrTimeNum);
	
	OrderData ordData = ordDao.selectOredrDataByOdno(ordNum);	
	
//	ArrayList<Ticket> tckList = tckDao.selectTicketByOdno(ordNum);	
	ArrayList<ScreenSeat> scrSeatList = scrSeatDao.selectScrSeatByScrTime(scrTimeNum);
	static LinkedList<Integer> scrSeatNumList = new LinkedList<>();
		
	Container c = getContentPane();

	JPanel plN = new JPanel();
	JPanel plS = new JPanel();
	JPanel plC = new JPanel();
	JPanel plSeat = new JPanel();
	JPanel plSeatEmpty = new JPanel();
	JPanel plScreen = new JPanel();
	JPanel plPayment = new JPanel();
	JPanel plPrice = new JPanel();
	JPanel line1 = new JPanel();
	JPanel line2 = new JPanel();

	JLabel lbSeat[] = new JLabel[scrSeatList.size()];
	JLabel lbScreen = new JLabel("SCREEN", JLabel.CENTER);
	JLabel lbMovieTitle = new JLabel(filmName); 
	JLabel lbPrice = new JLabel(price, JLabel.RIGHT);

	JButton btBack = new JButton("뒤로");
	JButton btPurchase = new JButton("예매취소");
	
	public P7A_ShowSeat(JFrame frame, String title, boolean isModal) {
//	public P7A_ShowSeat() {
			
		super(frame, title, true);
	
		System.out.println(ordNum);
		lbMovieTitle.setText(filmName);
		
		JPanel plNorhMenu = new JPanel();
		JPanel plEastEmpty = new JPanel();

		plN.setLayout(new BorderLayout());
		plN.add(plNorhMenu, BorderLayout.NORTH);
		plN.add(line1, BorderLayout.SOUTH);

		plNorhMenu.setLayout(new BorderLayout());

		plNorhMenu.add(plEastEmpty, BorderLayout.EAST);
		plNorhMenu.add(btBack, BorderLayout.WEST);

		// 뒤로 버튼 이벤트 설정-----------------------
		btBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		btBack.setPreferredSize(new Dimension(70, 45));
		line1.setPreferredSize(new Dimension(0, 2));
		plEastEmpty.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 0));
		btBack.setFont(new Font("D2coding", Font.CENTER_BASELINE, 14));
		btBack.setBackground(Color.red);
		btBack.setForeground(Color.white);

		plEastEmpty.setBackground(Color.white);
		plNorhMenu.setBackground(Color.white);
		line1.setBackground(Color.red);

		// 2. 좌석 선택----------------------------------------------------------
		plC.setLayout(new BorderLayout());

		plC.add(plScreen, BorderLayout.NORTH);
		plC.add(plSeatEmpty, BorderLayout.CENTER);

		plScreen.setLayout(new BorderLayout());
		plScreen.add(lbScreen);

		plSeatEmpty.setLayout(new BorderLayout());

		plSeatEmpty.add(plSeat, BorderLayout.CENTER);
		
		plSeat.setLayout(new GridLayout(0, scrTotalColSeat, 5, 5));
		
		// 좌석 라벨 설정-----------------------------------
		for (int i = 0; i < lbSeat.length; i++) {

			lbSeat[i] = new JLabel(scrSeatList.get(i).getRowSeat() + scrSeatList.get(i).getColSeat(), JLabel.CENTER);
			
			plSeat.add(lbSeat[i]);

			lbSeat[i].setOpaque(true);
			lbSeat[i].setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 13));
			resetColor(lbSeat[i]);	
		}
				
		for (int j : scrSeatNumList) {
			setColor(lbSeat[j-1]);
		}			
		
		// 여백설정
		plSeatEmpty.setBorder(BorderFactory.createEmptyBorder(50, 30, 30, 30));
		plScreen.setBorder(BorderFactory.createEmptyBorder(0, 70, 0, 70));
		lbScreen.setPreferredSize(new Dimension(0, 50));

		// 백그라운드 컬러설정
		lbScreen.setOpaque(true);
		lbScreen.setBackground(Color.lightGray);
		plSeat.setBackground(Color.darkGray);
		plScreen.setBackground(Color.darkGray);
		plSeatEmpty.setBackground(Color.darkGray);

		// 폰트설정
		lbScreen.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 25));

		// 3. 결제금액
		plS.setLayout(new BorderLayout());
		plS.add(plPayment, BorderLayout.SOUTH);
		plS.add(line2, BorderLayout.NORTH);

		JPanel plEmpty1 = new JPanel();

		DateManager dm = new DateManager();		
		long todayDateAndTimePlus10 = dm.todayDateAndTimeToIntPlus10();
		
		System.out.println("today "+todayDateAndTimePlus10);
		System.out.println("scrday "+scrDayAndTime );

		plPayment.setLayout(new BorderLayout());
		
		if (scrDayAndTime >= todayDateAndTimePlus10) {
			plPayment.add(btPurchase, BorderLayout.EAST);
		} else {
			plPayment.remove(btPurchase);
		}
		plPayment.add(plPrice, BorderLayout.CENTER);

		plPrice.setLayout(new BoxLayout(plPrice, BoxLayout.Y_AXIS));

		plPrice.add(Box.createVerticalStrut(5));
		plPrice.add(lbMovieTitle);
		plPrice.add(Box.createVerticalStrut(10));
		plPrice.add(lbPrice);

		plPrice.setBorder(BorderFactory.createEmptyBorder(3, 20, 3, 20));
		lbPrice.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));
	
		//3. 취소 버튼 이벤트 설정========= check를 purchase로===============
		btPurchase.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
								
					if (p7A_CancelMsg() == JOptionPane.YES_OPTION) {
						
						int cnt =  ordDao.deleteOrderAndTicket(ordNum); 
						
						if (cnt > 0) {

							p7A_InformMsg("예매취소가 성공적으로 완료되었습니다.");
							P7_MyPage.complementCancel = true;
							dispose();
						} else {
							p7A_InformMsg("예매취소가 실패하였습니다.");
						}
					}
				} 	
		});// btPur actionlistener

		// 크기설정
		line2.setPreferredSize(new Dimension(0, 1));
		btPurchase.setPreferredSize(new Dimension(150, 0));
		lbPrice.setPreferredSize(new Dimension(100, 35));

		// 백그라운드 설정
		plEmpty1.setBackground(Color.white);
		plPrice.setBackground(Color.white);
		plPayment.setBackground(Color.white);
		btPurchase.setBackground(Color.RED); // 
		line2.setBackground(Color.red);

		// 폰트 설정
		lbMovieTitle.setHorizontalAlignment(JLabel.LEFT);
		lbPrice.setHorizontalAlignment(JTextField.RIGHT);
		lbPrice.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 22));
		btPurchase.setForeground(Color.white);
		btPurchase.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 22));
		lbMovieTitle.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 22));
		
		if (lbMovieTitle.getPreferredSize().width > 320) {
			lbMovieTitle.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		}
		System.out.println("lb movie " + lbMovieTitle.getPreferredSize());

		c.add(plN, BorderLayout.NORTH);
		c.add(plC, BorderLayout.CENTER);
		c.add(plS, BorderLayout.SOUTH);

		// 창이 출력되는 위치 지정
		Dimension frameSize = getSize();
		// 스크린 사이즈를 구함
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// 스크린위치지정
		setLocation((screenSize.width - frameSize.width) / 3, (screenSize.height - frameSize.height) / 10);

		setVisible(false);
		setSize(580, 780);	
		
	}// produtor
	
	protected void resetColor(JLabel lb) {

		lb.setBackground(Color.GRAY);
		lb.setForeground(Color.BLACK);
	}

	private void setColor(JLabel lb) {

		lb.setBackground(Color.RED);
		lb.setForeground(Color.white);
	}
	
	private int p7A_CancelMsg() {
		UIManager.put("OptionPane.messageFont", new Font("맑은 고딕",Font.CENTER_BASELINE,13));
		UIManager.put("OptionPane.buttonFont", new Font("맑은 고딕",Font.CENTER_BASELINE,15));
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);
		
		return JOptionPane.showConfirmDialog(plC, "예매를 취소하시겠습니까?","예매취소", JOptionPane.YES_NO_OPTION);
	}

	private void p7A_InformMsg(String error) {
		UIManager.put("OptionPane.messageFont", new Font("맑은 고딕",Font.CENTER_BASELINE,13));
		UIManager.put("OptionPane.buttonFont", new Font("맑은 고딕",Font.CENTER_BASELINE,15));
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);
		
		JOptionPane.showMessageDialog(plC, error,"Inform",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void main(String[] args) {
//		new P7A_ShowSeat();
	}	
}

