package reserve;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class P0_MovieManager extends JDialog {
//public class P0_MovieManager extends JFrame {

	Container c = getContentPane();
	
	
	JPanel plNtitle = new JPanel();
	JPanel plNdownEmpty = new JPanel();

	JPanel plCMeun = new JPanel();
	
	P0A_AddMenu plAddMenu = new P0A_AddMenu();
	P0B_ModifyMenu plModifyMenu = new P0B_ModifyMenu();
	P0C_DeleteMenu plDeleteMenu = new P0C_DeleteMenu();

	JLabel lbNtitle = new JLabel("관리자 모드", JLabel.CENTER);

	JTabbedPane tbPane = new JTabbedPane(JTabbedPane.TOP);

//	public P0_MovieManager() {
		 public P0_MovieManager(JFrame frame, String title, boolean isModal) {

		 super(frame, title, true);
		
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		c.setLayout(new BorderLayout());

		// 1. title
		plNtitle.setLayout(new BorderLayout());

		plNtitle.add(lbNtitle, BorderLayout.NORTH);
		plNtitle.add(plNdownEmpty, BorderLayout.SOUTH);

		lbNtitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

		plNtitle.setBackground(Color.black);
		lbNtitle.setForeground(Color.white);
		lbNtitle.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 22));

		// empty Ndowm space
		plNdownEmpty.setBackground(Color.white);
		plNdownEmpty.setPreferredSize(new Dimension(0, 2));

		// 2. Cmenu - tap button
		tbPane.addTab("추가", plAddMenu);
		tbPane.addTab("변경", plModifyMenu);
		tbPane.addTab("삭제", plDeleteMenu);
	
		tbPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				
				if(tbPane.getSelectedIndex() == 0) {
					plAddMenu.tbAddMenu.setSelectedIndex(0);
					plAddMenu.resetMenu(0);
				}
				else if (tbPane.getSelectedIndex() == 1) {
					plModifyMenu.tbModifyMenu.setSelectedIndex(0);					
					plModifyMenu.resetMenu(0);
				}
				else {
					plDeleteMenu.tbDeleteMenu.setSelectedIndex(0);					
					plDeleteMenu.resetMenu(0);
				}
			}
		});

		tbPane.setBackground(Color.white);
		tbPane.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 14));

		plCMeun.setLayout(new BorderLayout());
		plCMeun.add(tbPane, BorderLayout.CENTER);

		plCMeun.setBackground(Color.white);

		// 창이 출력되는 위치 지정
		Dimension frameSize = getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width) / 3, (screenSize.height - frameSize.height) / 10);

		c.add(plNtitle, BorderLayout.NORTH);
		c.add(plCMeun, BorderLayout.CENTER);
		setSize(630, 650);
		setVisible(false);
	}// p0productor

	private void comboBoxSetAlignCenter(JComboBox cb) {

		cb.setRenderer(new DefaultListCellRenderer() {
			public int getHorizontalAlignment() {
				return CENTER;
			}
		});
	}

	private void p0InformrMsg(Component parentComponent, String error) {
		UIManager.put("OptionPane.messageFont", new Font("맑은 고딕", Font.CENTER_BASELINE, 13));
		UIManager.put("OptionPane.buttonFont", new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);

		JOptionPane.showMessageDialog(parentComponent, error, "Inform", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void main(String[] args) {
//		new P0_MovieManager();
	}
		
}// end
