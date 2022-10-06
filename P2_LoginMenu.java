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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.security.auth.login.Configuration;
import javax.swing.*;

public class P2_LoginMenu extends JDialog {
	
	static boolean login;
	
	P2A_SignUpMenu p21 = new P2A_SignUpMenu(this, "회원가입",true);
	
	MemberDao md = new MemberDao();
	
	JPanel plN = new JPanel();
	JPanel plE = new JPanel();
	JPanel plW = new JPanel();
	JPanel plS = new JPanel();

	JPanel plLogin = new JPanel();

	JLabel lbID = new JLabel("ID",JLabel.CENTER);
	JLabel lbPW = new JLabel("PASSWORD",JLabel.CENTER);
	
	JTextField txID = new JTextField(10);
	JPasswordField pfPW = new JPasswordField(10);
	
	JButton[] btMenu = {
			new JButton("회원가입"),
			new JButton("로그인"),
	};
	
//	public P2_LoginMenu() {
	public P2_LoginMenu(JFrame frame, String title, boolean isModal) {
				
		super(frame, title, true);
		
		plLogin.setLayout(new GridLayout(3, 2, 15 ,10));
		
		plLogin.add(lbID);
		plLogin.add(txID);
		plLogin.add(lbPW);
		plLogin.add(pfPW);
		
		for( int i = 0; i < 2; i++) {
			
			plLogin.add(btMenu[i]);
			btMenu[i].addActionListener(new ChangeMenu());
			btMenu[i].setFont(new Font("맑은고딕", Font.CENTER_BASELINE, 15));
			btMenu[i].setForeground(Color.white);
			btMenu[i].setBackground(Color.DARK_GRAY);
		}
		
		txID.addMouseListener(new ChangeCursor());
		pfPW.addMouseListener(new ChangeCursor());
		
		lbID.setFont(new Font("맑은고딕", Font.CENTER_BASELINE, 15));
		lbPW.setFont(new Font("맑은고딕", Font.CENTER_BASELINE, 15));
		
		add(plN,BorderLayout.NORTH);
		add(plW,BorderLayout.WEST);
		add(plE,BorderLayout.EAST);
		add(plS,BorderLayout.SOUTH);
		add(plLogin,BorderLayout.CENTER);
		
		plN.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		plW.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
		plS.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		plE.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
		
		plLogin.setBackground(Color.white);
		plN.setBackground(Color.white);
		plS.setBackground(Color.white);
		plW.setBackground(Color.white);
		plE.setBackground(Color.white);
		
		Dimension frameSize = getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
		setLocation((screenSize.width - frameSize.width) / 2-200,
				(screenSize.height - frameSize.height) / 10+50);
		
		setSize(300, 170);
		setVisible(false);
		setResizable(false);
	}
	
	public class ChangeCursor extends MouseAdapter {
		
		@Override
		public void mouseEntered(MouseEvent e) {
			
			
			if (e.getSource() == txID) { 
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
	
	// 버튼동작 이거 밖에 없음
	public class ChangeMenu implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			// 회원가입 버튼
			if(e.getSource() == btMenu[0] ) {
				System.out.println("회원가입");
				txID.setText("");
				pfPW.setText("");
				p21.resetTx();
				p21.setVisible(true);
			}
			// 로그인 버튼
			else 
				checkLogin();
			
		}//over
	}//Actionlis
	
	// 로그인
	//아이디와 비번을 확인함
	public void checkLogin() {
		
		String inputID = txID.getText();
		String inputPW = String.valueOf(pfPW.getPassword());
						
		// 아이디와 비번이 맞으면
		if(md.checkMember(new Member(inputID,inputPW))) { 
			
			P1_MovieMenu.ID = inputID;
			P1_MovieMenu.checkLogin = true;
			P1_MovieMenu.Login();
			
			txID.setText("");
			pfPW.setText("");
			
			if (inputID.equals("admin")) {
				p2SucessMsg("관리자 모드로 접속되었습니다.");
			}
			else 
				p2SucessMsg("로그인 되었습니다."); 
			
			setVisible(false);
		}
		
		//틀리면 
		else {
			
			if (inputID.length() > 0 && inputPW.length() > 0) {

				txID.setText("");
				pfPW.setText("");

				p2ErrorMsg("아이디 또는 비밀번호가 틀렸습니다.");
			}
			
			else 
				p2ErrorMsg("아이디와 비밀번호를 입력하세요.");	
		}
		System.out.println("login");
	}
	
	private void p2ErrorMsg(String error) {
		UIManager.put("OptionPane.messageFont", new Font("맑은 고딕",Font.CENTER_BASELINE,13));
		UIManager.put("OptionPane.buttonFont", new Font("맑은 고딕",Font.CENTER_BASELINE,15));
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);
				
		JOptionPane.showMessageDialog(this, error, "Error",
									  JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void p2SucessMsg(String error) {
		UIManager.put("OptionPane.messageFont", new Font("맑은 고딕",Font.CENTER_BASELINE,13));
		UIManager.put("OptionPane.buttonFont", new Font("맑은 고딕",Font.CENTER_BASELINE,15));
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);
				
		JOptionPane.showMessageDialog(this, error, "Sucess",
									  JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void main(String[] args) {
//		new P2_LoginMenu();
	}
}
