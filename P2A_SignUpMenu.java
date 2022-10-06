package reserve;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class P2A_SignUpMenu extends JDialog{
	
	MemberDao md = new MemberDao();
	
	JPanel plN = new JPanel();
	JPanel plE = new JPanel();
	JPanel plW = new JPanel();
	JPanel plS = new JPanel();
	JPanel plC = new JPanel();
	
	JPanel plSignUp = new JPanel();
	
	JLabel lbName = new JLabel("이름");
	JLabel lbID = new JLabel("ID");
	JLabel lbPW = new JLabel("PASSWORD");
	JLabel lbBirth = new JLabel("생년월일");
	JLabel lbPhone = new JLabel("전화번호/폰번호");
	JLabel lbEmail = new JLabel("E-Mail");
	
	
	JTextField txName = new JTextField(12);
	JTextField txID = new JTextField(12);
	JPasswordField pfPW = new JPasswordField(12);
	JTextField txBirth = new JTextField(12);
	JTextField txPhone = new JTextField(12);
	JTextField txEmail = new JTextField(12);
	
	JButton btCheckID = new JButton("중복확인");
	JButton btSignUp = new JButton("가입하기");
	
	public P2A_SignUpMenu(JDialog dialog,String title, boolean isModal ) {
//	public P2A_SignUpMenu() {
	
		super(dialog, title,true);
		
		plC.setLayout(new BorderLayout());
		plSignUp.setLayout(new GridLayout(0,2,0,10));
	
		plSignUp.add(lbID);
		plSignUp.add(txID);
		plSignUp.add(Box.createHorizontalStrut(50));
		plSignUp.add(btCheckID);
		plSignUp.add(lbPW);
		plSignUp.add(pfPW);
		plSignUp.add(lbName);
		plSignUp.add(txName);
		plSignUp.add(lbBirth);
		plSignUp.add(txBirth);
		plSignUp.add(lbPhone);
		plSignUp.add(txPhone);
		plSignUp.add(lbEmail);
		plSignUp.add(txEmail);
		
		plC.add(plSignUp, BorderLayout.CENTER);
		plC.add(btSignUp, BorderLayout.SOUTH);
		
		limitText(txBirth, 8);
		limitText(txPhone, 12);
		
		plSignUp.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		
		lbName.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		lbID.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		lbPW.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		lbBirth.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		lbPhone.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		lbEmail.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		
		btSignUp.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		btCheckID.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		
		btCheckID.setBackground(Color.lightGray);
		btCheckID.setForeground(Color.black);
		plSignUp.setBackground(Color.white);
		btSignUp.setBackground(Color.DARK_GRAY);
		btSignUp.setForeground(Color.white);
		
		add(plN,BorderLayout.NORTH);
		add(plE,BorderLayout.EAST);
		add(plW,BorderLayout.WEST);
		add(plS,BorderLayout.SOUTH);
		add(plC,BorderLayout.CENTER);
		
		// 아이디 중복 체크 버튼
		btCheckID.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String id = txID.getText();

				if (id.length() > 0) {

					if (md.checkDuplictedID(id)) {
						
						p21ErrorMsg("이미 사용하고 있는 아이디입니다.");
						
					} else {

						p21ErrorMsg("사용가능한 아이디입니다.");
					}
				}
			}
		});
		
		// 회원가입 버튼
		btSignUp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				boolean checkSignUp = true;
				
				DateManager dm =  new DateManager();
				
				String id = txID.getText();
				String pw = String.valueOf(pfPW.getPassword());
				String name = txName.getText();
				String birth = txBirth.getText();
				String phoneNum = txPhone.getText();
				String eMail = txEmail.getText();
				
				System.out.println(id.length());
				
				if(id.length() < 1 || pw.length() < 1 || name.length() < 1 || eMail.length() < 1 
						|| birth.length() < 1 || phoneNum.length() < 1)  {
					checkSignUp = false;
					p21ErrorMsg("빈칸없이 다 입력해주세요");
				}
				
				else if(!OnlyNumberAndAlphabet(id)) {
					checkSignUp = false;
					p21ErrorMsg("아이디는 영어와 숫자만 입력해주세요");
				}
	
				else if(md.checkDuplictedID(id)) {
					checkSignUp = false;
					p21ErrorMsg("중복된 아이디입니다. 새로운 아이디를 입력바랍니다.");
				}
				
				else if(pw.length() > 0 && pw.length() < 7) {
					checkSignUp = false;
					p21ErrorMsg("비밀번호는 8자 이상 입력해주세요");
				}
				
				else if (!checkInt(birth)) { 
					checkSignUp = false;
					p21ErrorMsg("생년월일은 문자없이 숫자로 YYYYMMDD로 입력해주세요 \n" 
								+ "예) " + 	dm.todayToInteger());
				}
				
				else if(birth.length() > 0 && birth.length() != 8) {
					checkSignUp = false;
					p21ErrorMsg("생년월일은 YYYYMMDD로 입력해주세요 \n" 
								+ "예) " + dm.todayToInteger());
				}
				
				else if (!checkInt(phoneNum)) { 
					checkSignUp = false; 
					p21ErrorMsg("번호는 '-'를 뺀 숫자만 입력해주세요");
				}
				
				else if(phoneNum.length() > 0 && phoneNum.length() < 8) {
					checkSignUp = false;
					p21ErrorMsg("번호를 끝까지 입력해주세요");
				}
				
				if (checkSignUp) {					
					if (md.insertMember(id, pw, name, Integer.parseInt(birth), phoneNum,eMail) > 0) {

						p21SucessMsg("회원가입이 완료되었습니다.");
						resetTx();
						setVisible(false);
					}
					else {
						p21SucessMsg("시스템 오류로 인해 회원가입이 실패했습니다.");
					}
				}
			}
			
			private boolean checkInt(String num) {
				try {

					long stringToInt = Long.parseLong(num);
					
					if (stringToInt < 0) throw new Exception();

				} catch (Exception e2) {
					return false;
				}
				return true;
			}
		});
		
		plN.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		plW.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		plS.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		plE.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		
		plN.setBackground(Color.white);
		plW.setBackground(Color.white);
		plS.setBackground(Color.white);
		plE.setBackground(Color.white);
			
		Dimension frameSize = getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
		setLocation((screenSize.width - frameSize.width) / 2-200,
				(screenSize.height - frameSize.height) / 10+50);
		
		setSize(300,350);
		setVisible(false);
		setResizable(false);
		
	}
	
	public class ChangeCursor extends MouseAdapter {
		
		@Override
		public void mouseEntered(MouseEvent e) {
			
			if (e.getSource() == txName) { 
				setCursor(new Cursor(Cursor.TEXT_CURSOR));
			}
			
			else if (e.getSource() == txID){  
				setCursor(new Cursor(Cursor.TEXT_CURSOR));
			}
			
			else if (e.getSource() == pfPW){  
				setCursor(new Cursor(Cursor.TEXT_CURSOR));
			}
			
			else if (e.getSource() == txBirth){  
				setCursor(new Cursor(Cursor.TEXT_CURSOR));
			}
			
			else {  
				setCursor(new Cursor(Cursor.TEXT_CURSOR));
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
	// 영문 숫자만 입력받게 하기 위한 함수 
	private boolean OnlyNumberAndAlphabet(String textInput) {

		char chrInput;

		for (int i = 0; i < textInput.length(); i++) {

			chrInput = textInput.charAt(i); // 입력받은 텍스트에서 문자 하나하나 가져와서 체크

			if (chrInput >= 0x61 && chrInput <= 0x7A) {
				// 영문(소문자) OK!
			} else if (chrInput >= 0x41 && chrInput <= 0x5A) {
				// 영문(대문자) OK!
			} else if (chrInput >= 0x30 && chrInput <= 0x39) {
				// 숫자 OK!
			} else {
				return false; // 영문자도 아니고 숫자도 아님!
			}
		}
		return true; // 영문 숫자일때 true
	}

	private void p21ErrorMsg(String error) {
		
		UIManager.put("OptionPane.messageFont", new Font("맑은 고딕",Font.CENTER_BASELINE,13));
		UIManager.put("OptionPane.buttonFont", new Font("맑은 고딕",Font.CENTER_BASELINE,15));
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);
		
		JOptionPane.showMessageDialog(this, error,"Error"
				                      ,JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void p21SucessMsg(String error) {
		UIManager.put("OptionPane.messageFont", new Font("맑은 고딕",Font.CENTER_BASELINE,13));
		UIManager.put("OptionPane.buttonFont", new Font("맑은 고딕",Font.CENTER_BASELINE,15));
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);
				
		JOptionPane.showMessageDialog(this, error, "Sucess",
									  JOptionPane.INFORMATION_MESSAGE);
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
	}
	
	public void resetTx() {
		txID.setText("");
		pfPW.setText("");
		txName.setText("");
		txPhone.setText("");
		txBirth.setText("");
	}
	
	public static void main(String[] args) {
//		new P2A_SignUpMenu();
	}
	
}
