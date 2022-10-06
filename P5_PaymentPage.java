package reserve;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

import javax.swing.*;

public class P5_PaymentPage extends JDialog {
		
	OrderDataDao odDao = new OrderDataDao();
			
	static String paymentMethod;
	
	int price;
	int cbCardIndex;
	boolean checkCard = false;
	String[] cardCompany = { "카드를 선택해주세요", "국민", "BC", "신한", "우리", "부산" };	

	JPanel plN = new JPanel();
	JPanel plE = new JPanel();
	JPanel plW = new JPanel();
	JPanel plS = new JPanel();
	JPanel plC = new JPanel();

	JPanel plselectPayment = new JPanel();
	JPanel plRbButton = new JPanel();
	JPanel plPayment = new JPanel();
	JPanel plCash = new JPanel();
	JPanel plCard = new JPanel();
	JPanel plCardCompany = new JPanel();
	JPanel plCardInput = new JPanel();
	JPanel plButton = new JPanel();

	JRadioButton[] rbSelectPayment = { new JRadioButton("현금", false), new JRadioButton("카드", false) };
	ButtonGroup rbGroup = new ButtonGroup();

	JLabel lbNtitle = new JLabel("결제수단을 선택해주세요.");
	JLabel lbPrice[] = new JLabel[2];
	JLabel lbPayment = new JLabel("지불금액", JLabel.RIGHT);
	JLabel lbCard = new JLabel("카드번호", JLabel.RIGHT);
	JLabel lbCvc = new JLabel("cvc", JLabel.RIGHT);
	JComboBox<String> cbCard = new JComboBox<>(cardCompany);

	JTextField txPrice[] = new JTextField[2];
	JTextField txPayment = new JTextField(10);
	JTextField txCard = new JTextField(23);
	JTextField txCvc = new JTextField(5);

	JButton[] btMenu = { new JButton("결제"), new JButton("취소") };
	
	public P5_PaymentPage(JDialog dial, String title , boolean isModal) {
//	
		super(dial, title, true);
			
		plN.add(lbNtitle);
		lbNtitle.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));

		plC.setLayout(new BorderLayout());
		
		// 1. 결제수단 선택 라디오 버튼
		plselectPayment.setLayout(new BorderLayout());
	
		
		for (int i = 0; i < rbSelectPayment.length; i++) {

			rbGroup.add(rbSelectPayment[i]);
			plRbButton.add(rbSelectPayment[i]);
			rbSelectPayment[i].addItemListener(new SelectPayment());
			rbSelectPayment[i].setBackground(Color.white);
			rbSelectPayment[i].setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 14));
		}
		
		plRbButton.setLayout(new FlowLayout(FlowLayout.CENTER, 16, 6));
		
		JPanel line = new JPanel(); // 일부러 패널을 추가하여 구분선을 만듬		
		line.setLayout(null);
		
		plselectPayment.add(plRbButton,BorderLayout.NORTH);
		plselectPayment.add(line,BorderLayout.SOUTH);
		
		line.setBackground(Color.LIGHT_GRAY);
		
		plC.add(plselectPayment, BorderLayout.NORTH);
		plC.add(plPayment, BorderLayout.CENTER);
		
		// 2. 가격 설정
		for (int i = 0; i < lbPrice.length; i++) {
			lbPrice[i] = new JLabel("결제금액", JLabel.RIGHT);
			txPrice[i] = new JTextField(15);
			
			txPrice[i].setEditable(false);
			txPrice[i].setBackground(Color.white);
			txPrice[i].setFont(new Font("D2coding", Font.CENTER_BASELINE, 15));
			txPrice[i].setHorizontalAlignment(JTextField.CENTER);
			lbPrice[i].setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));
			// 라벨에 일부러 여백을 주어서 크기를 늘림
			lbPrice[i].setFont(new Font("D2coding", Font.CENTER_BASELINE, 15));
			lbPrice[i].setForeground(Color.red);;
			txPrice[i].setFont(new Font("D2coding", Font.CENTER_BASELINE, 15));
			
			//가격 ,000 붙임
			String price = "18000";		
			txPrice[i].setText(price.substring(0, price.length()-3) + ",000");
		}
		
		// 2.1. 현금결제
		plCash.setLayout(new GridLayout(2, 2, 30, 10));

		plCash.add(lbPrice[0]);
		plCash.add(txPrice[0]);
		plCash.add(lbPayment);
		plCash.add(txPayment);

		plPayment.add(plCash, BorderLayout.NORTH);
		
		lbPayment.setFont(new Font("D2coding", Font.CENTER_BASELINE, 15));

		plCash.setVisible(false);

		// 2.2. 카드결제
		plCard.setLayout(new BorderLayout());

		plCard.add(plCardCompany, BorderLayout.NORTH);
		plCard.add(plCardInput, BorderLayout.CENTER);

		plCardCompany.add(cbCard);
		
		// 가운데 정렬
		cbCard.setRenderer(new DefaultListCellRenderer() {
			public int getHorizontalAlignment() {
				return CENTER;
			}
		});

		cbCard.setBackground(Color.white);
		cbCard.setFont(new Font("D2coding", Font.CENTER_BASELINE, 13));
		cbCard.addActionListener(new ReadCardCompany());
		
		plCardInput.setLayout(new GridLayout(3, 2, 30, 10));
		plCardInput.add(lbCard);
		plCardInput.add(txCard);
		plCardInput.add(lbCvc);
		plCardInput.add(txCvc);
		plCardInput.add(lbPrice[1]);
		plCardInput.add(txPrice[1]);
				
		plPayment.add(plCard, BorderLayout.NORTH);
		
		lbCard.setFont(new Font("D2coding", Font.CENTER_BASELINE, 15));
		lbCvc.setFont(new Font("D2coding", Font.CENTER_BASELINE, 15));

		plCard.setVisible(false);

		// 3.버튼 설정
		plButton.setLayout(new GridLayout(1, 2, 20, 0));

		for (int i = 0; i < btMenu.length; i++) {

			plButton.add(btMenu[i]);
			btMenu[i].addActionListener(new ChangeMenu());
			btMenu[i].setFont(new Font("D2coding", Font.CENTER_BASELINE, 15));
			btMenu[i].setBackground(Color.darkGray);
			btMenu[i].setForeground(Color.white);
		}

		plC.add(plButton, BorderLayout.SOUTH);
		
		limitText(txCvc, 3);
		limitText(txCard, 16);
		
		//txfield, 설정 가운데 정렬
		txPayment.setFont(new Font("D2coding", Font.CENTER_BASELINE, 15));
		txCard.setFont(new Font("D2coding", Font.CENTER_BASELINE, 14));
		txCvc.setFont(new Font("D2coding", Font.CENTER_BASELINE, 14));
		txPayment.setHorizontalAlignment(JTextField.CENTER);
		txCard.setHorizontalAlignment(JTextField.CENTER);
		txCvc.setHorizontalAlignment(JTextField.CENTER);
		
		//cusorchange 추가
		txPayment.addMouseListener(new ChangeCursor());
		txCard.addMouseListener(new ChangeCursor());
		txCvc.addMouseListener(new ChangeCursor());
		cbCard.addMouseListener(new ChangeCursor());
		rbSelectPayment[0].addMouseListener(new ChangeCursor());
		rbSelectPayment[1].addMouseListener(new ChangeCursor());
		
		// 여백 설정
		plN.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		plW.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		plS.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		plE.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		plCash.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 40));
		plCard.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 50));
		plCardCompany.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 100));
		plButton.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		
		// backgroud 색깔
		plN.setBackground(Color.white);
		plW.setBackground(Color.white);
		plS.setBackground(Color.white);
		plE.setBackground(Color.white);
		plselectPayment.setBackground(Color.white);
		plRbButton.setBackground(Color.white);
		plPayment.setBackground(Color.white);
		plCash.setBackground(Color.white);
		plCard.setBackground(Color.white);
		plCardInput.setBackground(Color.white);
		plCardCompany.setBackground(Color.white);
		plButton.setBackground(Color.white);
		
		// 여백주기
		add(plN, BorderLayout.NORTH);
		add(plW, BorderLayout.WEST);
		add(plE, BorderLayout.EAST);
		add(plS, BorderLayout.SOUTH);
		add(plC, BorderLayout.CENTER);
		
		// 창이 출력되는 위치 지정
		Dimension frameSize = getSize();
		// 스크린 사이즈를 구함
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// 스크린위치지정
		setLocation((screenSize.width - frameSize.width) /3+90, 
					(screenSize.height - frameSize.height) / 10+60);
		
		setSize(380, 340);
		setVisible(false);
		setResizable(false);

	} //P5_PaymentPage()
	
	public JTextField[] getTxPrice() {
		return txPrice;
	}

	public void setTxPrice(JTextField[] txPrice) {
		this.txPrice = txPrice;
	}
	
	private void resetText() {
		
//		cbCard.setSelectedIndex(0);
		txCard.setText("");
		txCvc.setText("");
		txPayment.setText("");
	}
	
	// 카드 선택메뉴
	class ReadCardCompany implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			JComboBox<String> cb = (JComboBox) e.getSource();
			cbCardIndex = cb.getSelectedIndex();
			System.out.println(cb.getSelectedItem());
			System.out.println(cb.getSelectedIndex());
		}
		
	}
	
	//현금 or 카드?
	class SelectPayment implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {

			boolean select = false;
			
			if (e.getStateChange() == ItemEvent.SELECTED)
				select = true;

			if (rbSelectPayment[0].isSelected()) { // 현금
				checkCard = false;
				
				paymentMethod = "현금";
				plCash.setVisible(select);
				plCard.setVisible(!select);
				resetText();
			} 
			
			else { //카드
				checkCard = true;
				
				paymentMethod = "카드";
				plCash.setVisible(!select);
				plCard.setVisible(select);
				resetText();
				cbCard.setSelectedIndex(0);
			}

		}// over
	}// selectPayment
	
	private boolean checkInt(String num) {
		try {

			long stringToInt = Long.parseLong(num);
			
			if (stringToInt < 0) throw new Exception();

		} catch (Exception e2) {
			return false;
		}
		return true;
	}
	
	// 구매 취소 버튼 설정
	class ChangeMenu implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == btMenu[0]) { // 결제	
				
				if (!checkCard) { //현금결제 일때
					//결제 성공
					if (txPayment.getText().equals(String.valueOf(price)) ) {
						// 이거 수정, 결제 성공시 주문정보 반환						
						goToP6();
					}
					else { //결제 실패
						paymentErrorMsg("금액을 잘못 입력하였습니다.");
						resetText();
						}
				}
				
				else { // 카드결제 일때
					//결제성공
					if ( cbCardIndex > 0 && txCvc.getText().length() == 3 
							&& txCard.getText().length() == 16) {
						// 이거 수정, 결제 성공시 주문정보 반환
						goToP6();
					}
					else if (cbCard.getSelectedIndex() == 0){ // 결제 실패
						paymentErrorMsg("카드를 선택해주세요");
					}
					
					else if (!checkInt(txCard.getText())) {
						resetText();
						paymentErrorMsg("카드번호를 잘못 입력하였습니다.\n" + 
									"'-'뺀 나머지 번호를 입력해주세요.");
					}
					
					else if (txCard.getText().length() < 16) {
						resetText();
						paymentErrorMsg("카드번호를 잘못 입력하였습니다");
					}
					
					else if (!checkInt(txCvc.getText())) {
						txCvc.setText("");
						paymentErrorMsg("숫자만 입력하세요");
					}
					
					else if (txCvc.getText().length() < 3) {
						txCvc.setText("");
						paymentErrorMsg("cvc 번호를 잘못 입력하였습니다.");
					}
				}
				
			}

			else {
				setVisible(false);
			}
		}// over
	}// Actionlis
	
	private void goToP6() {
		
		P6_InProgressPayment p6 = new P6_InProgressPayment(this, "결제중", true);
		p6.setVisible(true);
		
		setVisible(false);
	}
	
	private void limitText(JTextField JText, int limitwords) {

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
	}
	
	private void paymentErrorMsg(String error) {
		UIManager.put("OptionPane.messageFont", new Font("맑은 고딕",Font.CENTER_BASELINE,13));
		UIManager.put("OptionPane.buttonFont", new Font("맑은 고딕",Font.CENTER_BASELINE,15));
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);
		
		JOptionPane.showMessageDialog(this, error,"결제 오류",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	class ChangeCursor extends MouseAdapter {
		
		@Override
		public void mouseEntered(MouseEvent e) {
			
			if (e.getSource() == txPayment) { 
				setCursor(new Cursor(Cursor.TEXT_CURSOR));
			}
			
			else if (e.getSource() == txCard){  
				setCursor(new Cursor(Cursor.TEXT_CURSOR));
			}
			
			else if (e.getSource() == txCvc) {  
				setCursor(new Cursor(Cursor.TEXT_CURSOR));
			}
			
			else if (e.getSource() == cbCard) {  
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
			else if (e.getSource() == rbSelectPayment[0]) {  
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		
			else {  
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}	
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}

	}//changecusor
	
	public static void main(String[] args) {
//		System.out.println(peopleNum);
		
	}
}
