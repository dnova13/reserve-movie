package reserve;

import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.border.Border;

public class P6_InProgressPayment extends JDialog {

	ScreenSeatDao scrSeatDao = new ScreenSeatDao();
	FilmDao filmDao = new FilmDao();
	
	int filmNum;
	int takeTime = ((int) (Math.random() * 6) + 5) * 1000;

	Container c = getContentPane();

	JPanel plInProgress = new JPanel();

	JLabel lbThreadImage = new JLabel(new ImageIcon("images/그림13.png"));
	JLabel lbThreadWord = new JLabel("", JLabel.CENTER);
	JLabel lbMsg = new JLabel("약 5-10초 걸립니다.", JLabel.CENTER);
	
//	public P6_InProgressPayment() {
	public P6_InProgressPayment(JDialog dial, String title, boolean isModal) {

		super(dial, title, isModal);

		c.requestFocus();

		setTitle("결제중");

		ThreadLabel thTI = new ThreadLabel(lbThreadImage, 30, true);
		ThreadLabel thTW = new ThreadLabel(lbThreadWord, 500, false);

		plInProgress.setLayout(new BoxLayout(plInProgress, BoxLayout.Y_AXIS));

		plInProgress.add(Box.createVerticalStrut(70));
		plInProgress.add(lbThreadImage);
		plInProgress.add(Box.createVerticalStrut(5));
		plInProgress.add(lbThreadWord);
		plInProgress.add(Box.createVerticalStrut(3));
		plInProgress.add(lbMsg);

		plInProgress.setBackground(Color.white);
		lbThreadImage.setAlignmentX(CENTER_ALIGNMENT);
		lbThreadWord.setAlignmentX(CENTER_ALIGNMENT);
		lbMsg.setAlignmentX(CENTER_ALIGNMENT);

		lbThreadWord.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		lbMsg.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));

		add(plInProgress);

		thTI.start();
		thTW.start();

		// 창이 출력되는 위치 지정
		Dimension frameSize = getSize();
		// 스크린 사이즈를 구함
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// 스크린위치지정
		setLocation((screenSize.width - frameSize.width) / 3 + 113, (screenSize.height - frameSize.height) / 10 + 66);

		setVisible(false);
		setSize(330, 330);

	}
	
	private void completePaymentMsg() {

		UIManager.put("OptionPane.messageFont", new Font("맑은 고딕", Font.CENTER_BASELINE, 13));
		UIManager.put("OptionPane.buttonFont", new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);

		JOptionPane.showMessageDialog(this, "결제가 완료됬습니다.", "결제완료", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void failPaymentMsg() {

		UIManager.put("OptionPane.messageFont", new Font("맑은 고딕", Font.CENTER_BASELINE, 13));
		UIManager.put("OptionPane.buttonFont", new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);

		JOptionPane.showMessageDialog(this, "결제가 실패했습니다.", "결제실패", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public boolean completeDataProcess() {
		
		OrderDataDao ordDao = new OrderDataDao();
		DateManager dm = new DateManager();
		Calendar orderDate = Calendar.getInstance();
		
		String ID = P1_MovieMenu.ID;
		String paymentMethod = P5_PaymentPage.paymentMethod;
		String thName = P3_SelectTheater.thName;
		String scrName = P3_SelectTheater.scrName;
		String locationName = P3_SelectTheater.locationName;
		String scrTime  = P3_SelectTheater.scrTime;
		
		filmNum = P3_SelectTheater.filmNum;
		Film film = filmDao.selectFilmByFno(filmNum);
		String fTitle = film.getFilmTitle();
		String runtime = film.getRunningTime();
		
		int reserveDateInt = P3_SelectTheater.reserveDateInt;
		int scrTimeNum = P4_SelectSeat.scrTimeNum;
		int peopleNum = P4_SelectSeat.PeopleNum;;
		int price = P4_SelectSeat.price;
		
		OrderData order = new OrderData(ID, dm.showDateAndTime(orderDate), peopleNum, paymentMethod, price);
		
		Ticket ticket = new Ticket(0, filmNum,fTitle,runtime, reserveDateInt, locationName, thName, 
									scrName, scrTime,"", scrTimeNum ,0, 0);
		
		return ordDao.insertOrderAndTicket(order, ticket, P4_SelectSeat.purchasingSeno);
	}
	
	public class ThreadLabel extends Thread {

		ImageIcon[] image = {

				new ImageIcon("images/pic1.png"), new ImageIcon("images/pic2.png"), new ImageIcon("images/pic3.png"),
				new ImageIcon("images/pic4.png"), new ImageIcon("images/pic5.png"), new ImageIcon("images/pic6.png"),
				new ImageIcon("images/pic7.png"), new ImageIcon("images/pic8.png"), new ImageIcon("images/pic9.png"),
				new ImageIcon("images/pic10.png"), new ImageIcon("images/pic11.png"), new ImageIcon("images/pic12.png"),
				new ImageIcon("images/pic13.png") };

		JLabel lbThread = new JLabel(image[12]);

		String[] word = { "결제 중 입니다.", "결제 중 입니다..", "결제 중 입니다...", "결제 중 입니다....", "결제 중 입니다.....", "결제 중 입니다......" };

		int sleepCnt;
		boolean isImage;

		ThreadLabel(JLabel label, int sleepCnt, boolean isImage) {
			this.lbThread = label;
			this.sleepCnt = sleepCnt;
			this.isImage = isImage;
		}

		@Override
		public void run() {
			super.run();
			int cnt = 0;
			int sumTime = 0;

			System.out.println(takeTime);

			while (true) {

				if (isImage)
					lbThread.setIcon(image[cnt % 13]);
				else
					lbThread.setText(word[cnt % 6]);

				cnt++;

				setCursor(new Cursor(Cursor.WAIT_CURSOR));

				try {
					Thread.sleep(sleepCnt);
					sumTime += sleepCnt;

//					if (sumTime >= 1000) { // 느려서 3초로 잡음
					if (sumTime >= takeTime) { // 종료시 모든 데이터를 전송
						if (isImage) {
							if (completeDataProcess()) {
//							if (false) {
								completePaymentMsg();								
								filmDao.incresePayCount(filmNum);

								dispose();
								
								P1_MovieMenu.completePayment = true;
								P3_SelectTheater.completePayment = true;
								P4_SelectSeat.completePayment = true;
								
								return;
							}
							else {
								failPaymentMsg();
								dispose();
								
								return;
							}
						} else // 글자일때
							return;
					} // if

				} catch (InterruptedException e) {
					//
				} // catch

			} // while

		}// run
			
	}// MoveImage
	
	public static void main(String[] args) {
//		new P6_InProgressPayment();
	}

}// InPro
