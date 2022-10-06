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

import javax.swing.*;

public class P4_SelectSeat extends JDialog {
		
	ScreenSeatDao scrSeatDao = new ScreenSeatDao();
	TicketDao tckDao = new TicketDao();
	
	ArrayList<ScreenSeat> scrSeatList = scrSeatDao.selectScrSeatByScrTime(scrTimeNum);;
	static ArrayList<ScreenSeat> purchasingSeno = new ArrayList<>();
	
	String cbPeopleNum[] = new String[11];
	
	static int scrTotalColSeat;
	static int scrTimeNum;
	static int PeopleNum;
	static int price;
	static boolean completePayment;
	static String filmName;
	
	int totalSeat = scrSeatList.size();
	int prAdult = 8000;
	int prTeen = 6000;
	int ppmin;
	int ppdiff;
	int adNum = 0;
	int tnNum = 0;
	int addSeat;
	int diffSeat;
	int click = 0;

	// 구입한 좌석은 true하여 변경 못하게 막음
	boolean purchased[] = new boolean[scrSeatList.size()];
	// checkSeat check일때 선택한거고, !check 선택하지 않은거
	boolean checkSeat[] = new boolean[scrSeatList.size()];
	boolean restrictSeat[] = new boolean[scrSeatList.size()];
	boolean checkAddMax;
	
	Container c = getContentPane();

	JPanel plN = new JPanel();
	JPanel plS = new JPanel();
	JPanel plC = new JPanel();
	JPanel plPeople = new JPanel();
	JPanel plSeat = new JPanel();
	JPanel plSeatEmpty = new JPanel();
	JPanel plScreen = new JPanel();
	JPanel plPayment = new JPanel();
	JPanel plPrice = new JPanel();
	JPanel line1 = new JPanel();
	JPanel line2 = new JPanel();

	JLabel lbSeat[] = new JLabel[scrSeatList.size()];
	JLabel lbAdult = new JLabel("성인");
	JLabel lbTeen = new JLabel("청소년");
	JLabel lbScreen = new JLabel("SCREEN", JLabel.CENTER);
	JLabel lbMovieTitle = new JLabel(); // 나중에 제목 지울거임
	JLabel lbPrice = new JLabel("0원", JLabel.RIGHT);

	JButton btBack = new JButton("뒤로");
	JButton btPurchase = new JButton("결제");
	JComboBox<String> cbNum[] = new JComboBox[2];
	
//	public P4_SelectSeat() {
	public P4_SelectSeat(JDialog dial, String title, boolean isModal) {
		
		super(dial, title, true);
		
		System.out.println(scrTimeNum);
		System.out.println(filmName);
		lbMovieTitle.setText(filmName);
		
		cbPeopleNum[0] = "선택";

		// 콤보박스 사람수 설정
		for (int i = 1; i < cbPeopleNum.length; i++) {
			
				cbPeopleNum[i] = i + "";
		}
		
		// 콤보박스 설정
		for (int i = 0; i < 2; i++) {

			cbNum[i] = new JComboBox<>(cbPeopleNum);
			cbNum[i].setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
			cbNum[i].setBackground(Color.white);
			cbNum[i].setAlignmentX(RIGHT_ALIGNMENT);
			cbNum[i].addMouseListener(new MouseAdapter() { // 커서바꿈

				@Override
				public void mouseEntered(MouseEvent e) {
					super.mouseEntered(e);

					if (e.getSource() == cbNum[0])
						setCursor(new Cursor(Cursor.HAND_CURSOR));

					else
						setCursor(new Cursor(Cursor.HAND_CURSOR));

				}

				@Override
				public void mouseExited(MouseEvent e) {
					super.mouseExited(e);
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			});
			
		//1. 인원수 콤보박스 액션 리스너 좌석수 입력-----------------------
			cbNum[i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					if (cbNum[0] == e.getSource())
						adNum = cbNum[0].getSelectedIndex();
					else
						tnNum = cbNum[1].getSelectedIndex();

					// 초기화
					PeopleNum = adNum + tnNum;

					if (adNum < tnNum) {

						ppmin = adNum;
					} else {
						ppmin = tnNum;
					}

					ppdiff = (int) Math.abs((adNum - tnNum));

					resetAll();
					
//					if (PeopleNum > 1)
//						 restrictChoose2Seat();
//					else 
//						restrictChoose1Seat();

					System.out.println(PeopleNum);
				}
			});
		}

		// 1. 인원수
		// 선택-------------------------------------------------------------

		JPanel plNorhMenu = new JPanel();
		JPanel plEastEmpty = new JPanel();

		plN.setLayout(new BorderLayout());
		plN.add(plNorhMenu, BorderLayout.NORTH);
		plN.add(line1, BorderLayout.SOUTH);

		plNorhMenu.setLayout(new BorderLayout());

		plNorhMenu.add(plEastEmpty, BorderLayout.EAST);
		plNorhMenu.add(btBack, BorderLayout.WEST);
		plNorhMenu.add(plPeople, BorderLayout.CENTER);

		plPeople.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 8));

		plPeople.add(lbAdult);
		plPeople.add(cbNum[0]);
		plPeople.add(lbTeen);
		plPeople.add(cbNum[1]);

		// 뒤로 버튼 이벤트 설정-----------------------
		btBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		btBack.setPreferredSize(new Dimension(70, 5));
		line1.setPreferredSize(new Dimension(0, 2));
		lbTeen.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
		plEastEmpty.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 0));
		lbAdult.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		lbTeen.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		btBack.setFont(new Font("D2coding", Font.CENTER_BASELINE, 14));
		btBack.setBackground(Color.red);
		btBack.setForeground(Color.white);

		plEastEmpty.setBackground(Color.white);
		plPeople.setBackground(Color.white);
		line1.setBackground(Color.red);

		// 2. 좌석 선택----------------------------------------------------------
		plC.setLayout(new BorderLayout());

		plC.add(plScreen, BorderLayout.NORTH);
		plC.add(plSeatEmpty, BorderLayout.CENTER);

		plScreen.setLayout(new BorderLayout());
		plScreen.add(lbScreen);

		plSeatEmpty.setLayout(new BorderLayout());

		plSeatEmpty.add(plSeat, BorderLayout.CENTER);
		
//		int rowNum = scrSeatList.size()/scrTotalColSeat;
//		
//		if (scrSeatList.size()%scrTotalColSeat > 0) {
//			rowNum += 1;
//		} 
		
		plSeat.setLayout(new GridLayout(0, scrTotalColSeat, 5, 5));
		
		// 좌석 라벨 설정-----------------------------------
		for (int i = 0; i < lbSeat.length; i++) {

			lbSeat[i] = new JLabel(scrSeatList.get(i).getRowSeat() + scrSeatList.get(i).getColSeat(), JLabel.CENTER);
			
			plSeat.add(lbSeat[i]);
			plSeat.setAlignmentX(JPanel.CENTER_ALIGNMENT);

			lbSeat[i].addMouseListener(new SeatEvent());
			lbSeat[i].setOpaque(true);
			lbSeat[i].setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 13));
			lbSeat[i].setBackground(Color.gray);
			lbSeat[i].setForeground(Color.BLACK);
			
			purchased[i] = scrSeatList.get(i).isPurchased();
			
			if (purchased[i]) {
				setColor(lbSeat[i]);
			}
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

		plPayment.setLayout(new BorderLayout());
		plPayment.add(btPurchase, BorderLayout.EAST);
		plPayment.add(plPrice, BorderLayout.CENTER);

		plPrice.setLayout(new BoxLayout(plPrice, BoxLayout.Y_AXIS));

		plPrice.add(Box.createVerticalStrut(5));
		plPrice.add(lbMovieTitle);
		plPrice.add(Box.createVerticalStrut(10));
		plPrice.add(lbPrice);

		plPrice.setBorder(BorderFactory.createEmptyBorder(3, 20, 3, 20));
		lbPrice.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));
	
		//3. 구매,결제 버튼 이벤트 설정========= check를 purchase로===============
		btPurchase.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("ppl " + PeopleNum);
				
				ArrayList<ScreenSeat> selectedSeat = new ArrayList<>(); // 선택된 seno를 받음

				// 선택한 시트번호와 개수를 저장
				for (int i = 0; i < checkSeat.length; i++) {

					if (checkSeat[i]) { // i 좌석의 번호이자 scrlist의 인덱스번호

						selectedSeat.add(scrSeatList.get(i));
					}
				}

				// 구매 결정
				if (selectedSeat.size() > 0 && selectedSeat.size() == PeopleNum) {

					purchasingSeno = selectedSeat;
					goToP5();

				} else
					p4_InformMsg("좌석을 선택해주세요.");
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
		btPurchase.setBackground(Color.RED);
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
		setResizable(true);
	}// produtor
	
	//2. 좌석표시 이벤트------------------------------------
	public class SeatEvent extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);

			for (int i = 0; i < lbSeat.length; i++) {

				// ------------------------해당 박스 클릭시 하는 동작들---------
				if (e.getSource() == lbSeat[i]) {
					if (i < totalSeat - 1) {
						// 2.1. 2좌석 선택시
						// 2좌석 선택시 마지막 열 선택못하게 막음
						if (i % scrTotalColSeat < scrTotalColSeat - 1) {
							// 2개 선택시 2좌석이 0 0 일때 1로
							if (!(checkSeat[i] || checkSeat[i + 1])) { 
								if (!(purchased[i] || purchased[i + 1])) {
									if (addSeat > 1) {
										// 클릭수가 선택한 수를 넣을때 색깔 못칠하게 막음(선택시만 잇음)
										if (click < PeopleNum) {

											selectedSeat(i, lbSeat[i]);
											selectedSeat(i + 1, lbSeat[i + 1]);

											// 좌석이 짝수일때
											if (adNum % 2 == 1 && tnNum % 2 == 1 || adNum % 2 == 0 && tnNum % 2 == 0) {
												if (click < ppmin * 2) {
													System.out.println(ppmin);
													System.out.println(adNum);
													price += prAdult + prTeen;
													lbPrice.setText(showMoney(String.valueOf(price)) + "원");
												} else if (click < PeopleNum) {

													if (adNum > tnNum) {

														price += prAdult * 2;
														lbPrice.setText(showMoney(String.valueOf(price)) + "원");
													} else {
														price += prTeen * 2;
														lbPrice.setText(showMoney(String.valueOf(price)) + "원");
													}
												}
											}
											// 좌석이 홀수 일때
											else if (adNum % 2 == 0) { // adNum이 짝수 일때

												// 짝 -> 홀
												if (click < adNum) {
													price += prAdult * 2;
													lbPrice.setText(showMoney(String.valueOf(price)) + "원");
												}
												// 그리고 홀수 처리
												else if (click < PeopleNum) {
													price += prTeen * 2;
													lbPrice.setText(showMoney(String.valueOf(price)) + "원");
												}
											}

											else {
												// 짝 -> 홀
												if (click < tnNum) {
													price += prTeen * 2;
													lbPrice.setText(showMoney(String.valueOf(price)) + "원");
												}

												else if (click < PeopleNum) {
													price += prAdult * 2;
													lbPrice.setText(showMoney(String.valueOf(price)) + "원");
												}
											}

											addSeat -= 2;
											diffSeat += 2;
											click += 2;// 여기까지

											System.out.println(price);

										} // click < ppnum
									} // addseat
								} // purchased
							} // if check ||

							// 2.2. 2좌석 취소시 ---------------------------------
							else if ((checkSeat[i] && checkSeat[i + 1])) {// 2좌석
								if (!(purchased[i] || purchased[i + 1])) { // 예약취소
									if (diffSeat > 1) {
										// 셋한 박스가 짝수인지 아닌지 구별, 짝수일 경우 그 열을 선택
										// 못하게 막음
										if (!checkBoxEven(i)) {

											deselectedSeat(i, lbSeat[i]);
											deselectedSeat(i + 1, lbSeat[i + 1]);

											System.out.println((adNum % 2));
											System.out.println(adNum);

											// 좌석이 짝수일때
											if (adNum % 2 == 1 && tnNum % 2 == 1 || adNum % 2 == 0 && tnNum % 2 == 0) {

												if (click > ppmin * 2 && click <= PeopleNum) {

													if (adNum > tnNum) {
														price -= prAdult * 2;
														lbPrice.setText(showMoney(String.valueOf(price)) + "원");
													} else {
														price -= prTeen * 2;
														lbPrice.setText(showMoney(String.valueOf(price)) + "원");

													}
												} else if (click <= ppmin * 2) {
													price -= prAdult + prTeen;
													lbPrice.setText(showMoney(String.valueOf(price)) + "원");
												}
											}
											// 좌석이 홀수 일때
											else if (adNum % 2 == 0) { // adNum이 짝수일때
												if (click > adNum && click <= PeopleNum) {

													if (click == adNum + 1) {
														price -= prAdult * 2;
													}

													else
														price -= prTeen * 2;

													lbPrice.setText(showMoney(String.valueOf(price)) + "원");
												}

												else if (click <= adNum) {
													price -= prAdult * 2;
													lbPrice.setText(showMoney(String.valueOf(price)) + "원");
												}
											}

											else { // teen 이 짝수 일때

												if (click > tnNum && click <= PeopleNum) {

													if (click == tnNum + 1) {
														price -= prTeen * 2;

													} else
														price -= prAdult * 2;

													lbPrice.setText(showMoney(String.valueOf(price)) + "원");
												}

												else if (click <= tnNum) {
													price -= prTeen * 2;
													lbPrice.setText(showMoney(String.valueOf(price)) + "원");
												}
											}

											System.out.println(price);
											addSeat += 2;
											diffSeat -= 2;
											click -= 2;
										}
									} // diffseat
								} // purched
							} // else check
						} // if( i % colseat.length)
					}// if( i < totalseat-1)
					
					// 2.3. 1좌석 예약시
					if (!(purchased[i])) {
						if (!checkSeat[i]) { // 1자석
							if (addSeat < 2) {// 1좌석예약
								if (click < PeopleNum) {

									selectedSeat(i, lbSeat[i]);
									
									System.out.println("click++ : " + click);

									price = price + prAdult * (adNum % 2) + prTeen * (tnNum % 2);
									lbPrice.setText(showMoney(String.valueOf(price)) + "원");

									addSeat--;
									diffSeat++;
									click++;
									System.out.println(price);
								} // click < select
							} // addSeat
						} // !check 1좌서 예약
						
						//2.4. 1좌석 예약취소---------------
						else if (checkSeat[i]) { 
							if (diffSeat < 2) {

								if (click > 0 && click <= PeopleNum) { // 4.

									deselectedSeat(i, lbSeat[i]);

									price = price - prAdult * (adNum % 2) - prTeen * (tnNum % 2);
									lbPrice.setText(showMoney(String.valueOf(price)) + "원");

									addSeat++; // 빨강시트
									diffSeat--; // 회색시트
									click--;
									System.out.println(price);
								} // 4
							}
						} // else checkseat 1좌석 예약취소
						System.out.println("add : " + addSeat + "  dif: " + diffSeat + " clik : " + click);
					} // !purchased
					
				} //e.getsouce
			} // for
		}// mouseClick overwrite

		// --------------------- mouse 클릭시 끝 ---------

		@Override
		public void mouseEntered(MouseEvent e) {
			super.mouseEntered(e);

			for (int i = 0; i < lbSeat.length; i++) {

				if (e.getSource() == lbSeat[i]) {
					from2boxTo1box(i, true); //true setting
				} // lbSeat
			}
		}// mouseEnter

		@Override
		public void mouseExited(MouseEvent e) {
			super.mouseExited(e);

			for (int i = 0; i < lbSeat.length; i++) {

				if (e.getSource() == lbSeat[i]) {
					from2boxTo1box(i, false); //false resetting
				} // lbSeat // lbSeat
			} // for
		}// mousedxit

		private void from2boxTo1box(int i, boolean setting) {

			if (addSeat == PeopleNum)
				checkAddMax = true;
			else if (addSeat == 0)
				checkAddMax = false;

			// 2좌석
			if (i < totalSeat - 1) {
				if (i % scrTotalColSeat < scrTotalColSeat - 1) {
					if (!(checkSeat[i] || checkSeat[i + 1])) {
						if (!(purchased[i] || purchased[i + 1])) {
							if (checkAddMax) {
								if (addSeat > 1) {
									if (setting) {
										setColor(lbSeat[i]);
										setColor(lbSeat[i + 1]);
									} else {
										resetColor(lbSeat[i]);
										resetColor(lbSeat[i + 1]);
									}
								}
							}

							else {
								if (diffSeat > 1) {

									if (setting) {
										setColor(lbSeat[i]);
										setColor(lbSeat[i + 1]);
									} else {
										resetColor(lbSeat[i]);
										resetColor(lbSeat[i + 1]);
									}
								}
							}
						}
					}
				}
			}
			if (!checkSeat[i]) {
				if (!(purchased[i])) {

					if (checkAddMax) {
						if (addSeat < 2) {

							if (setting) {
								setColor(lbSeat[i]);

							} else {
								resetColor(lbSeat[i]);
							}
						}
					}

					else {
						if (diffSeat < 2) {
							if (setting) {
								setColor(lbSeat[i]);

							} else {
								resetColor(lbSeat[i]);
							}
						}
					}

				}
			}
		}// from2box
		
		// 셋한 박스가 짝수인지 아닌지 구별, 짝수일 경우 그 열을 선택 못하게 막음
		private boolean checkBoxEven(int i) {

			int leftCnt = 0;
			int rightCnt = 0;
			int rightNum = i;
			int leftNum = i;
			int leftColNum = i % scrTotalColSeat;
			int rightColNum = i % scrTotalColSeat;
			boolean checkEven = false;

			System.out.println("--------------------");
			System.out.println("박스번호 :" + i);
			System.out.println("열번호" + leftColNum);

			System.out.println("선택한 열 값 : " + checkSeat[leftNum]);

			for (int c = 0; c < scrTotalColSeat; c++) {

				if (leftNum % scrTotalColSeat < 1)
					break;

				if (checkSeat[--leftNum]) {
					leftCnt++;
				} else
					break;
			}

			System.out.println("left " + leftCnt);

			for (int c = 0; c < scrTotalColSeat; c++) {
				
				if (rightNum < totalSeat) {

					if (rightNum % scrTotalColSeat > scrTotalColSeat-1)
						break;
					
					try {
						if (checkSeat[++rightNum]) {
							rightCnt++;
						} else
							break;
					} catch (Exception e) {
						e.printStackTrace();
						break;
					}
					
				}
			}

			System.out.println("right" + rightCnt);

			int boxLength = rightCnt + leftCnt + 1;
			int intEven = leftCnt + 1;

			System.out.println("박스길이 " + boxLength);
			System.out.println("짝수 " + intEven);

			if (boxLength % 2 == 0 && boxLength >= 4) {
				if (intEven % 2 == 0) {
					return true;
				}
			}
			return false;
		}//checkboxEven
	}// seatMouseAction

	public void goToP5() {
		P5_PaymentPage p5 = new P5_PaymentPage(this, "결제", true);

		p5.txPrice[0].setText(lbPrice.getText());
		p5.txPrice[1].setText(lbPrice.getText());
		p5.price = price;
		p5.setVisible(true);
		
		System.out.println("p4 "+completePayment);
		if(completePayment) {	
			setVisible(false);
		}
	}
	
	public static void main(String[] args) {
//		new P4_SelectSeat();
	}
	
	private void resetColor(JLabel lb) {

		lb.setBackground(Color.GRAY);
		lb.setForeground(Color.BLACK);
	}

	private void setColor(JLabel lb) {

		lb.setBackground(Color.RED);
		lb.setForeground(Color.white);
	}

	public void selectedSeat(int i, JLabel lb) {

		checkSeat[i] = true;
		setColor(lb);
	}

	public void purchasedSeat(int i, JLabel lb) {

		purchased[i] = true;
		selectedSeat(i, lb);
	}

	public void deselectedSeat(int i, JLabel lb) {

		checkSeat[i] = false;
		resetColor(lb);
	}

	public void cancelSeat(int i, JLabel lb) {

		purchased[i] = false;
		deselectedSeat(i, lb);
	}

	private void resetAll() { // 이미 구매한거 reset 되지 않는다.

		click = 0;
		price = 0;
		lbPrice.setText(0 + "원");
		addSeat = PeopleNum;
		diffSeat = 0;

		for (int i = 0; i < lbSeat.length; i++) {

			if (!purchased[i]) {
				deselectedSeat(i, lbSeat[i]);
			}
		}
	}
	
	private void p4_InformMsg(String error) {
		UIManager.put("OptionPane.messageFont", new Font("맑은 고딕",Font.CENTER_BASELINE,13));
		UIManager.put("OptionPane.buttonFont", new Font("맑은 고딕",Font.CENTER_BASELINE,15));
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);
		
		JOptionPane.showMessageDialog(plC, error,"Inform",
				JOptionPane.INFORMATION_MESSAGE);
	}

	private String showMoney(String money) { // 이거 나중에 객체만들때 추가할지도?

		if (Integer.parseInt(money) < 1000)
			return "0";

		return money.substring(0, money.length() - 3) + ",000";
	}	
	// 1개의 박스를 택할 때, 선택되어서는 안되는 공간 설정 ---------------
	private void restrictChoose1Seat() {
		
		int cnt = 0;
		int start = 0;
		int rowNum = 0;
		int colNum = scrTotalColSeat;
		
//		purchasedSeat(15, lbSeat[15]);
//		purchasedSeat(14, lbSeat[14]);
//		purchasedSeat(28, lbSeat[28]);
		
		for (int i = 0; i < lbSeat.length; i++) {

			if (i % scrTotalColSeat == 0) {

				start = 0;
				cnt = 0;
			}
			
			cnt++;

			if (checkSeat[i] || purchased[i]) {
				
				cnt -= 1;
				
				if (cnt % 2 == 1) {
					System.out.println("odd " +  cnt);
					restrictIfOddColSeatAndChoose1Seat(start, cnt, rowNum, colNum);
				} else { 
					restrictIfEvenColSeatAndChoose1Seat(start, cnt, rowNum, colNum);
				}

				start = (i % scrTotalColSeat + 1);
				cnt = 0;
			}

			if (i % scrTotalColSeat == scrTotalColSeat-1) {
				
				if (cnt % 2 == 1) {
					restrictIfOddColSeatAndChoose1Seat(start, cnt, rowNum, colNum);
				} else {
					restrictIfEvenColSeatAndChoose1Seat(start, cnt, rowNum, colNum);
				}

				cnt = 0;
				rowNum++;
			}
			
//			if (i % scrTotalColSeat == scrTotalColSeat-1) {
//				rowNum++;
//			}
		}
	}
	
	// 1개의 박스를 택할 때, 선택되어서는 안되는 공간 설정(홀수 일때)
	private void restrictIfOddColSeatAndChoose1Seat(int start, int cnt, int rowNum, int colNum) {

		if (cnt > 3) {

			for (int i = 0; i < cnt - 1; i++) {

				if (i % 2 == 1) {
					setRestrictSeat(rowNum*colNum + i + start);
				}
			}
		}
	}
	
	// 1개의 박스를 택할 때, 선택되어서는 안되는 공간 설정(짝 수 일때)
	private void restrictIfEvenColSeatAndChoose1Seat(int start, int cnt, int rowNum,int colNum) {
		
		int base = cnt/2;
		
		if (cnt >= 4) {

			// System.out.println("st" + start);
			// System.out.println("cnt " + cnt);

			for (int i = 0; i < base; i++) {

				if (i % 2 == 1) {
					setRestrictSeat(rowNum*colNum + i + start);
				}
			}
			
			for (int i = base; i < cnt - 1; i++) {

				if (i % 2 == 0) {
					setRestrictSeat(rowNum*colNum + i + start);
				}
			}

		}
	}

	//--------------------- 설정 끝
	
	
	// 2개의 박스를 택할 때, 선택되어서는 안되는 공간 설정 ---------------
	private void restrictChoose2Seat() {
		
		int cnt = 0;
		int start = 0;
		int rowNum = 0;
		int colNum = scrTotalColSeat;

		System.out.println(colNum);
		
//		purchasedSeat(15, lbSeat[15]);
//		purchasedSeat(14, lbSeat[14]);
//		purchasedSeat(28, lbSeat[28]);
		
		for (int i = 0; i < lbSeat.length; i++) {

			if (i % scrTotalColSeat == 0) {

				start = 0;
				cnt = 0;
			}
			
			cnt++;

			if (checkSeat[i] || purchased[i]) {
				
				cnt -= 1;
				
				if (cnt % 2 == 1) {
					System.out.println("odd " +  cnt);
					restrictIfOddColSeatAndChoose2Seat(start, cnt, rowNum,colNum);
				} else {
					restrictIfEvenColSeatAndChoose2Seat(start, cnt, rowNum,colNum);
				}

				start = (i % scrTotalColSeat + 1);
				cnt = 0;
			}

			if (i % scrTotalColSeat == scrTotalColSeat-1) {

				if (cnt % 2 == 1) {
					restrictIfOddColSeatAndChoose2Seat(start, cnt, rowNum,colNum);
				} else {
					restrictIfEvenColSeatAndChoose2Seat(start, cnt, rowNum,colNum);
				}

				cnt = 0;
				rowNum++;
			}
			
//			if (i % scrTotalColSeat == scrTotalColSeat-1) {
//			
//			}
		}
	}
	
	// 2개의 박스를 택할 때, 선택되어서는 안되는 공간 설정(홀수 일때)
	private void restrictIfOddColSeatAndChoose2Seat(int start, int cnt, int rowNum, int colNum) {

		if (cnt == 7) {
			
			System.out.println("cnt" + cnt);
			setRestrictSeat(rowNum*colNum + start + 1);
			setRestrictSeat(rowNum*colNum + start + 4);
		}

		if (cnt > 7) {

			int base = 0;

			base = cnt / 2;

			if (base % 2 == 0)
				base -= 1;
			else
				base -= 2;

			for (int i = 0; i <= base; i++) {

				if (i % 2 == 1) {
					setRestrictSeat(rowNum*colNum + i + start);
				}
			}

			for (int i = base + 3; i < cnt - 1; i++) {

				if (i % 2 == 0) {
					setRestrictSeat(rowNum*colNum + i + start);
				}
			}
		}
	}
	
	// 2개의 박스를 택할 때, 선택되어서는 안되는 공간 설정(짝수 일때)
	private void restrictIfEvenColSeatAndChoose2Seat(int start, int cnt, int rowNum, int colNum) {

		if (cnt >= 4) {

			// System.out.println("st" + start);
			// System.out.println("cnt " + cnt);
			
			System.out.println(cnt);
			
			for (int i = 0; i < cnt - 1; i++) {
								
				if (i % 2 == 1) {
					setRestrictSeat(rowNum*colNum + i + start);
				}
			}

		}
	}
	
	private void setRestrictSeat(int i) {
		lbSeat[i].setBackground(Color.yellow);
		restrictSeat[i] = true;
	}

	//--------------------- 설정 끝
}