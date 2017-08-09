package 计算器;
/*计算器
**
**
*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Calculator {
	public static void main(String args[]){
		new MainWindow();
	}
}

class MainWindow extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2080463030646428155L;
	private boolean flag = false;
	private String s,J1temp = "",J4temp = "";
	private JTextArea J1,J4;
	private Point point;//记录父窗体在屏幕的坐标
	private int width,height;//父窗体的宽度和高度
	
	public MainWindow(){
		super("Calculator By XJX");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Image img = Toolkit.getDefaultToolkit().getImage("title.png");//窗口图标
		setIconImage(img);
		
		String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		
		addKeyListener(new KeyMonitor());//注册键盘事件监听器
		setFocusable(true);
		setLayout(new GridLayout(1,2,2,0));
		JPanel workspace = new JPanel();
		add(workspace);
		JPanel historyspace = new JPanel();
		historyspace.setBackground(Color.white);
		add(historyspace);
		JMenuBar bar = new JMenuBar();
		bar.setBackground(Color.white);
		setJMenuBar(bar);
		JMenu fileMenu = new JMenu("File");
		fileMenu.setFont(new Font("微软雅黑",Font.PLAIN,15));
		bar.add(fileMenu);
		JMenuItem helpItem = new JMenuItem("Help");
		JMenuItem exitItem = new JMenuItem("Exit");
		JMenuItem aboutItem = new JMenuItem("About");
		fileMenu.add(exitItem);
		fileMenu.add(aboutItem);
		fileMenu.add(helpItem);
		JDialog dialog2 = new JDialog();
		dialog2.setModal(true);//设置为模态窗口
		dialog2.setIconImage(img);
		dialog2.setTitle("使用须知");
		dialog2.setSize(450, 500);
		JTextArea J8 = new JTextArea("* 本程序是一个计算器,可以实现windows自带的科学计算器上的大多数基本运算和复合运算\n\n"
				+ "* 由于在中文输入法中键盘上除号(反斜杠)的使用有时候会出现一个未知的问题,建议调成英文输入法使用\n\n"
				+ "* 关于负号的使用:负号是界面左下角的 ±,在你输入完一个数字时,点击这个按钮,会直接生成负数,注意只限于以这种方式使用,以下1/x以及根号也是如此\n\n"
				+ "* 关于1/x的使用：依照负号使用规则,生成一个数的倒数\n\n"
				+ "* 关于根号的使用：依照负号使用规则,生成一个数的二次根\n\n"
				+ "* C按钮是清空输入区的所有内容,对应键盘的DELETE键;<--按钮是删除输入的最后一个字符,对应键盘的BACKSPACE键\n\n"
				+ "* 键盘上的等号和回车都相当于程序界面的 =号\n\n"
				+ "* 程序阻断了输入阶段的绝大多数非法输入,所以当你非法输入时,会没有响应\n\n"
				+ "* 本程序的主要操作逻辑仿照win10自带的计算器实现\n\n"
				+ "                                 Copyright @2017XJX");
		J8.setFont(new Font("微软雅黑",Font.PLAIN,13));
		J8.setEditable(false);
		J8.setLineWrap(true);//自动换行
		dialog2.add(J8);
	
		helpItem.addActionListener(
				new ActionListener(){
				public void actionPerformed(ActionEvent e){
					point = MainWindow.this.getLocation();//获得主窗体在屏幕的坐标
					width = MainWindow.this.getWidth();
					height = MainWindow.this.getHeight();
					dialog2.setLocation(
					        point.x + width/2 - dialog2.getWidth()/2, 
					        point.y + height/2 - dialog2.getHeight()/2);
					dialog2.setVisible(true);
				}
			}
		);
		
		aboutItem.addActionListener(
				new ActionListener(){
				public void actionPerformed(ActionEvent e){
					new About();
				}
			}
		);
		
		exitItem.addActionListener(
				new ActionListener(){
				public void actionPerformed(ActionEvent e){
					setVisible(false);
					System.exit(0);
				}
			}
		);
		
		workspace.setLayout(new BorderLayout());
		J1 = new JTextArea(8,10);
		J1.addKeyListener(new KeyMonitor());
		J1.setFont(new Font("微软雅黑",Font.BOLD,20));
		J1.setVisible(true);
		J1.setEditable(false);
		J1.setLineWrap(true);//自动换行
		J1.setText("");
		workspace.add(J1,BorderLayout.NORTH);
		JPanel J2 = new JPanel();
		J2.setLayout(new GridLayout(6,4));
		J2.addKeyListener(new KeyMonitor());
		
		ButtonMonitor buttonMonitor = new ButtonMonitor();
		JButton[] button = new JButton[24];
		button[0] = new JButton("√");
		button[1] = new JButton("x^"); 
		button[2] = new JButton("1/x");
		button[3] = new JButton("C");
		button[4] = new JButton("(");
		button[5] = new JButton(")");
		button[6] = new JButton("<--");
		button[7] = new JButton("/");
		button[8] = new JButton("7");
		button[9] = new JButton("8");
		button[10] = new JButton("9");
		button[11] = new JButton("*");
		button[12] = new JButton("4");
		button[13] = new JButton("5");
		button[14] = new JButton("6");
		button[15] = new JButton("-");
		button[16] = new JButton("1");
		button[17] = new JButton("2");
		button[18] = new JButton("3");
		button[19] = new JButton("+");
		button[20] = new JButton("±");
		button[21] = new JButton("0");
		button[22] = new JButton(".");
		button[23] = new JButton("=");
		for(int i = 0;i < 24;i++)
		{
			button[i].addActionListener(buttonMonitor);
			button[i].addKeyListener(new KeyMonitor());
			button[i].setFont(new Font("微软雅黑",Font.PLAIN,20));
			J2.add(button[i]);
		}
		workspace.add(J2);
		
		historyspace.setLayout(new BorderLayout());
		JLabel J3 = new JLabel("历史记录");
		J3.setFont(new Font("微软雅黑",Font.BOLD,13));
		J3.addKeyListener(new KeyMonitor());
		historyspace.add(J3,BorderLayout.NORTH);
		J4 = new JTextArea();
		J4.addKeyListener(new KeyMonitor());
		J4.setFont(new Font("宋体",Font.BOLD,20));
		J4.setEditable(false);
		J4.setVisible(true);
		J4.setLineWrap(true);//自动换行
		J4.setText("");
		JScrollPane J4Scroll = new JScrollPane(J4);
		J4Scroll.addKeyListener(new KeyMonitor());
		historyspace.add(J4Scroll,BorderLayout.CENTER);
		JButton J5 = new JButton("清除所有历史记录");
		J5.setFont(new Font("微软雅黑",Font.BOLD,13));
		J5.addKeyListener(new KeyMonitor());
		J5.addActionListener(
				new ActionListener(){
				public void actionPerformed(ActionEvent e){
				J4.setText("");
			}
		}
		);
		historyspace.add(J5,BorderLayout.SOUTH);
		setSize(750,600);
		setMinimumSize(new Dimension(400,300));
		setLocationRelativeTo(null);//显示在屏幕中央
		setVisible(true);
		point = MainWindow.this.getLocation();//获得主窗体在屏幕的坐标
		width = MainWindow.this.getWidth();
		height = MainWindow.this.getHeight();
		dialog2.setLocation(
		        point.x + width/2 - dialog2.getWidth()/2, 
		        point.y + height/2 - dialog2.getHeight()/2);
		dialog2.setVisible(true);
	}
	//括号匹配函数
	boolean Match(String s)
	{
		CharStack S = new CharStack();
		int ptr = 0;
		while (ptr != s.length())
		{
			if (s.charAt(ptr) == '(')
			{
				S.Push(s.charAt(ptr));
				ptr++;
			}
			else if (s.charAt(ptr) == ')')
			{
				if (S.EmptyStack())
				{
					return false;
				}
				else
				{
					S.Pop();
					ptr++;
				}
			}
			else
			{
				ptr++;
			}
		}
		if (S.EmptyStack())  
			return true; 
		else
			return false;
	}
	
	//按钮监听器,该监听器要考虑多种容错处理
	class ButtonMonitor implements ActionListener{	
				public void actionPerformed(ActionEvent e){
					JButton J = (JButton)e.getSource();
					int ptr = 0;
					boolean flag3 = false;
					double t;
					String res = "";
					if(flag) 
					{
						J1.setText("");
						flag = false;
					}
								
					if(J.getText() == "<--") 
					{
						J1temp = J1.getText();
						if(J1temp==null || J1temp.equals("")){}
						else 
						{
							J1temp = J1temp.substring(0,J1temp.length()-1);
							J1.setText(J1temp);
						}
					}
					else if(J.getText() == "=") 
					{
						J1temp = J1.getText();
						if(J1temp==null || J1temp.equals("")){}
						else
						{
							if(J1temp.charAt(J1temp.length()-1) == '/'||
							   J1temp.charAt(J1temp.length()-1) == '*'||
							   J1temp.charAt(J1temp.length()-1) == '-'||
							   J1temp.charAt(J1temp.length()-1) == '+'||
							   J1temp.charAt(J1temp.length()-1) == '.'||
							   J1temp.charAt(J1temp.length()-1) == '(')
							{
								JOptionPane.showMessageDialog(null, "表达式存在逻辑错误！", "Error",JOptionPane.WARNING_MESSAGE);
								J4temp = J4.getText();
								J4temp = J4temp + "\n" + J1.getText() + " = " + "ERROR!";
								J4.setText(J4temp);
							}
							else
							{
								J4temp = J4.getText();
								J4temp = J4temp + "\n" + J1.getText() + " = ";
								J4.setText(J4temp);
								s = J1.getText();
								s += '=';//'='为s结尾标志
								if(!Match(s))
								{
									JOptionPane.showMessageDialog(null, "表达式括号不匹配！", "Error",JOptionPane.WARNING_MESSAGE);
									J4temp = J4.getText();
									J4temp += "ERROR!";
									J4.setText(J4temp);
								}
								else
								{
									res = new Expression(s).EvaluateExpression();//每次按下=把计算结果显示在J1区域并且把添加一条历史记录到J4区域
									if(res == "ERROR!")
									{
										J1temp = J1.getText();
										J1temp += " = " + res;
										J1.setText(J1temp);
										J4temp = J4.getText();
										J4temp += res;
										J4.setText(J4temp);
										flag = true;
									}
									else
									{
										t = Double.valueOf(res);
										if( res.charAt(res.length()-1) == '0' && res.charAt(res.length()-2) == '.')
										{
											J1temp = J1.getText();
											J1temp += " = " + (int)t;
											J1.setText(J1temp);
											J4temp = J4.getText();
											J4temp += (int)t;
											J4.setText(J4temp);
										}
										else
										{
											J1temp = J1.getText();
											J1temp += " = " + t;
											J1.setText(J1temp);
											J4temp = J4.getText();
											J4temp += t;
											J4.setText(J4temp);
										}
										flag = true;
										s = "";
									}
								}
							}
						}
					}
					else if(J.getText() == "±")//按下的是负号
					{
						J1temp = J1.getText();
						if(J1temp==null || J1temp.equals("")){}
						else
						{
							ptr = J1temp.length()-1;
							if(J1temp.charAt(ptr)=='('||
							   J1temp.charAt(ptr)==')'||
							   J1temp.charAt(ptr)=='/'||
							   J1temp.charAt(ptr)=='*'||
							   J1temp.charAt(ptr)=='-'||
							   J1temp.charAt(ptr)=='+'||
							   J1temp.charAt(ptr)=='.'){}
							else
							{
								while( (J1temp.charAt(ptr)=='0'||
										J1temp.charAt(ptr)=='1'||
										J1temp.charAt(ptr)=='2'||
										J1temp.charAt(ptr)=='3'||
										J1temp.charAt(ptr)=='4'||
										J1temp.charAt(ptr)=='5'||
										J1temp.charAt(ptr)=='6'||
										J1temp.charAt(ptr)=='7'||
										J1temp.charAt(ptr)=='8'||
										J1temp.charAt(ptr)=='9'||
										J1temp.charAt(ptr)=='.') && ptr >=0 )
							{
								ptr--;
								if( ptr < 0){break;}
							}
							J1temp = J1temp.substring(0, ptr+1) + "(" + "-" + J1temp.substring(ptr+1, J1temp.length()) + ")";
							J1.setText(J1temp);
							}
						}
					}
					else if(J.getText() == "/"||
							J.getText() == "*"||
							J.getText() == "-"||
							J.getText() == "+")
					{
						J1temp = J1.getText();
						if(J1temp==null || J1temp.equals("")){}
						else
						{
							if(J1temp.charAt(J1temp.length()-1) == '/'||
							   J1temp.charAt(J1temp.length()-1) == '*'||
							   J1temp.charAt(J1temp.length()-1) == '-'||
							   J1temp.charAt(J1temp.length()-1) == '+'||
							   J1temp.charAt(J1temp.length()-1) == '('||
							   J1temp.charAt(J1temp.length()-1) == '.')
							{}//不能在这些符号后面输入运算符
							else
							{
								J1temp = J1.getText();
								J1temp += J.getText();
								J1.setText(J1temp);
							}
						}
					}
					else if(J.getText() == ".")
					{
						J1temp = J1.getText();
						if(J1temp==null || J1temp.equals("")){}
						else
						{
							ptr = J1temp.length()-1;
							while( (J1temp.charAt(ptr)=='0'||
									J1temp.charAt(ptr)=='1'||
									J1temp.charAt(ptr)=='2'||
									J1temp.charAt(ptr)=='3'||
									J1temp.charAt(ptr)=='4'||
									J1temp.charAt(ptr)=='5'||
									J1temp.charAt(ptr)=='6'||
									J1temp.charAt(ptr)=='7'||
									J1temp.charAt(ptr)=='8'||
									J1temp.charAt(ptr)=='9') && ptr >= 0)
									{
										ptr--;
										if(ptr >= 0)
										{
											if(J1temp.charAt(ptr)=='.')
											{
												flag3 = true;
												break;
											}
										}
										else
											break;
									}	
								
							J1temp = J1.getText();
							if(J1temp.charAt(J1temp.length()-1) == '/'||
							   J1temp.charAt(J1temp.length()-1) == '*'||
							   J1temp.charAt(J1temp.length()-1) == '-'||
							   J1temp.charAt(J1temp.length()-1) == '+'||
							   J1temp.charAt(J1temp.length()-1) == '('||
							   J1temp.charAt(J1temp.length()-1) == ')'||
							   J1temp.charAt(J1temp.length()-1) == '.'||flag3)
							{}
							else
							{
								J1temp = J1.getText();
								J1temp += J.getText();
								J1.setText(J1temp);
							}
						}
					}
					else if(J.getText() == "(")
					{
						J1temp = J1.getText();
						if(J1temp==null || J1temp.equals(""))
						{
							J1.setText("(");
						}
						else
						{
							if(J1temp.charAt(J1temp.length()-1) == '0' ||
							   J1temp.charAt(J1temp.length()-1) == '1' ||
							   J1temp.charAt(J1temp.length()-1) == '2' ||
							   J1temp.charAt(J1temp.length()-1) == '3' ||
							   J1temp.charAt(J1temp.length()-1) == '4' ||
							   J1temp.charAt(J1temp.length()-1) == '5' ||
							   J1temp.charAt(J1temp.length()-1) == '6' ||
							   J1temp.charAt(J1temp.length()-1) == '7' ||
							   J1temp.charAt(J1temp.length()-1) == '8' ||
							   J1temp.charAt(J1temp.length()-1) == '9' ||
							   J1temp.charAt(J1temp.length()-1) == '.' ||
							   J1temp.charAt(J1temp.length()-1) == ')' ){}
							else
							{
								J1temp += J.getText();
								J1.setText(J1temp);
							}
						}
					}
					else if(J.getText() == ")")
					{
						J1temp = J1.getText();
						if(J1temp==null || J1temp.equals("")){}
						else
						{
							if(J1temp.charAt(J1temp.length()-1) == '.' ||
							   J1temp.charAt(J1temp.length()-1) == '/' ||
							   J1temp.charAt(J1temp.length()-1) == '*' ||
							   J1temp.charAt(J1temp.length()-1) == '-' ||
							   J1temp.charAt(J1temp.length()-1) == '+' ||
							   J1temp.charAt(J1temp.length()-1) == '(' ||
							   J1temp == ""){}
							else
							{
								J1temp += J.getText();
								J1.setText(J1temp);
							}
						}
					}
					else if(J.getText() == "√")
					{
						J1temp = J1.getText();
						if(J1temp==null || J1temp.equals("")){}
						else
						{
							ptr = J1temp.length()-1;
							if(J1temp.charAt(ptr)=='('||
							   J1temp.charAt(ptr)==')'||
							   J1temp.charAt(ptr)=='/'||
							   J1temp.charAt(ptr)=='*'||
							   J1temp.charAt(ptr)=='-'||
							   J1temp.charAt(ptr)=='+'||
							   J1temp.charAt(ptr)=='.'){}
							else
							{
								while( (J1temp.charAt(ptr)=='0'||
										J1temp.charAt(ptr)=='1'||
										J1temp.charAt(ptr)=='2'||
										J1temp.charAt(ptr)=='3'||
										J1temp.charAt(ptr)=='4'||
										J1temp.charAt(ptr)=='5'||
										J1temp.charAt(ptr)=='6'||
										J1temp.charAt(ptr)=='7'||
										J1temp.charAt(ptr)=='8'||
										J1temp.charAt(ptr)=='9'||
										J1temp.charAt(ptr)=='.') && ptr >=0 )
							{
								ptr--;
								if( ptr < 0){break;}
							}
							J1temp = J1temp.substring(0, ptr+1) + "√" + "(" + J1temp.substring(ptr+1, J1temp.length()) + ")";
							J1.setText(J1temp);
							}
						}
					}
					else if(J.getText() == "x^")
					{
						J1temp = J1.getText();
						if(J1temp==null || J1temp.equals("")){}
						else
						{
							ptr = J1temp.length()-1;
							if(J1temp.charAt(ptr)=='('||
							   J1temp.charAt(ptr)==')'||
							   J1temp.charAt(ptr)=='/'||
							   J1temp.charAt(ptr)=='*'||
							   J1temp.charAt(ptr)=='-'||
							   J1temp.charAt(ptr)=='+'||
							   J1temp.charAt(ptr)=='.'){}
							else
							{
								while( (J1temp.charAt(ptr)=='0'||
										J1temp.charAt(ptr)=='1'||
										J1temp.charAt(ptr)=='2'||
										J1temp.charAt(ptr)=='3'||
										J1temp.charAt(ptr)=='4'||
										J1temp.charAt(ptr)=='5'||
										J1temp.charAt(ptr)=='6'||
										J1temp.charAt(ptr)=='7'||
										J1temp.charAt(ptr)=='8'||
										J1temp.charAt(ptr)=='9'||
										J1temp.charAt(ptr)=='.') && ptr >=0 )
							{
								ptr--;
								if( ptr < 0){break;}
							}
							J1temp = J1temp + "^";
							J1.setText(J1temp);
							}
						}
					}
					else if(J.getText() == "1/x")
					{
						J1temp = J1.getText();
						if(J1temp==null || J1temp.equals("")){}
						else
						{
							ptr = J1temp.length()-1;
							if(J1temp.charAt(ptr)=='('||
							   J1temp.charAt(ptr)==')'||
							   J1temp.charAt(ptr)=='/'||
							   J1temp.charAt(ptr)=='*'||
							   J1temp.charAt(ptr)=='-'||
							   J1temp.charAt(ptr)=='+'||
							   J1temp.charAt(ptr)=='.'){}
							else
							{
								while( (J1temp.charAt(ptr)=='0'||
										J1temp.charAt(ptr)=='1'||
										J1temp.charAt(ptr)=='2'||
										J1temp.charAt(ptr)=='3'||
										J1temp.charAt(ptr)=='4'||
										J1temp.charAt(ptr)=='5'||
										J1temp.charAt(ptr)=='6'||
										J1temp.charAt(ptr)=='7'||
										J1temp.charAt(ptr)=='8'||
										J1temp.charAt(ptr)=='9'||
										J1temp.charAt(ptr)=='.') && ptr >=0 )
							{
								ptr--;
								if( ptr < 0){break;}
							}
								J1temp = J1temp.substring(0, ptr+1) + "(1/" + J1temp.substring(ptr+1, J1temp.length()) + ")";
								J1.setText(J1temp);
							}
						}
					}
					else if(J.getText() == "C")
					{
						J1.setText("");
						s = "";
					}
					else
					{
						J1temp = J1.getText();
						if(J1temp==null || J1temp.equals(""))
						{
							J1temp += J.getText();
							J1.setText(J1temp);
						}
						else
						{
							ptr = J1temp.length()-1;
							if(J1temp.charAt(ptr) == ')'){}
							else
							{
								J1temp += J.getText();
								J1.setText(J1temp);
							}
						}
					}
				}
	}
	//键盘监听器
	class KeyMonitor extends KeyAdapter{
				private int ptr = 0;
				private boolean flag3 = false;
				double t ;
				String res = "";
				public void keyReleased(KeyEvent e){
					int key = e.getKeyCode();
					if(flag) 
					{
						J1.setText("");
						flag = false;
					}
					if(!e.isShiftDown())
					{
						if(key == KeyEvent.VK_BACK_SPACE)
						{
							J1temp = J1.getText();
							if(J1temp==null || J1temp.equals("")){}
							else 
							{
								J1temp = J1temp.substring(0,J1temp.length()-1);
								J1.setText(J1temp);
							}
						}
						if(key == KeyEvent.VK_EQUALS || key == KeyEvent.VK_ENTER)
						{
							J1temp = J1.getText();
							if(J1temp==null || J1temp.equals("")){}
							else
							{
								if(J1temp.charAt(J1temp.length()-1) == '/'||
								   J1temp.charAt(J1temp.length()-1) == '*'||
								   J1temp.charAt(J1temp.length()-1) == '-'||
								   J1temp.charAt(J1temp.length()-1) == '+'||
								   J1temp.charAt(J1temp.length()-1) == '.'||
								   J1temp.charAt(J1temp.length()-1) == '(')
								{
									JOptionPane.showMessageDialog(null, "表达式存在逻辑错误！", "Error",JOptionPane.WARNING_MESSAGE);
									J4temp = J4.getText();
									J4temp = J4temp + "\n" + J1.getText() + " = " + "ERROR!";
									J4.setText(J4temp);
								}
								else
								{
									J4temp = J4.getText();
									J4temp = J4temp + "\n" + J1.getText() + " = ";
									J4.setText(J4temp);
									s = J1.getText();
									s += '=';//'='为s结尾标志
									if(!Match(s))
									{
										JOptionPane.showMessageDialog(null, "表达式括号不匹配！", "Error",JOptionPane.WARNING_MESSAGE);
										J4temp = J4.getText();
										J4temp += "ERROR!";
										J4.setText(J4temp);
									}
									else
									{
										res = new Expression(s).EvaluateExpression();//每次按下=把计算结果显示在J1区域并且把添加一条历史记录到J4区域
										if(res == "ERROR!")
										{
											J1temp = J1.getText();
											J1temp += " = " + res;
											J1.setText(J1temp);
											J4temp = J4.getText();
											J4temp += res;
											J4.setText(J4temp);
											flag = true;
										}
										else
										{
											t = Double.valueOf(res);
											if( res.charAt(res.length()-1) == '0' && res.charAt(res.length()-2) == '.')
											{
												J1temp = J1.getText();
												J1temp += " = " + (int)t;
												J1.setText(J1temp);
												J4temp = J4.getText();
												J4temp += (int)t;
												J4.setText(J4temp);
											}
											else
											{
												J1temp = J1.getText();
												J1temp += " = " + t;
												J1.setText(J1temp);
												J4temp = J4.getText();
												J4temp += t;
												J4.setText(J4temp);
											}
											flag = true;
											s = "";
										}
									}
								}
							}
						}
						if( key == KeyEvent.VK_0 ||
							key == KeyEvent.VK_1 ||
							key == KeyEvent.VK_2 ||
							key == KeyEvent.VK_3 ||
							key == KeyEvent.VK_4 ||
							key == KeyEvent.VK_5 ||
							key == KeyEvent.VK_6 ||
							key == KeyEvent.VK_7 ||
							key == KeyEvent.VK_8 ||
							key == KeyEvent.VK_9 ||
							(key >= 96 && key <= 105) )
						{
							if(key >= 96 && key <= 105)
							{
								J1temp = J1.getText();
								J1temp += (char)(key-48);
								J1.setText(J1temp);
							}
							else
							{
								J1temp = J1.getText();
								J1temp += (char)key;
								J1.setText(J1temp);
							}
						}
						if(key == KeyEvent.VK_PERIOD || key == 110)   //小数点
						{
							J1temp = J1.getText();
							if(J1temp==null || J1temp.equals("")){}
							else
							{
								J1temp = J1.getText();
								ptr = J1temp.length()-1;
								while( (J1temp.charAt(ptr)=='0'||
										J1temp.charAt(ptr)=='1'||
										J1temp.charAt(ptr)=='2'||
										J1temp.charAt(ptr)=='3'||
										J1temp.charAt(ptr)=='4'||
										J1temp.charAt(ptr)=='5'||
										J1temp.charAt(ptr)=='6'||
										J1temp.charAt(ptr)=='7'||
										J1temp.charAt(ptr)=='8'||
										J1temp.charAt(ptr)=='9') && ptr >= 0)
										{
											ptr--;
											if(ptr >= 0)
											{
												if(J1temp.charAt(ptr)=='.')
												{
													flag3 = true;
													break;
												}
											}
											else
												break;
										}	
						
								J1temp = J1.getText();
								if(J1temp.charAt(J1temp.length()-1) == '/'||
								   J1temp.charAt(J1temp.length()-1) == '*'||
								   J1temp.charAt(J1temp.length()-1) == '-'||
								   J1temp.charAt(J1temp.length()-1) == '+'||
								   J1temp.charAt(J1temp.length()-1) == '('||
								   J1temp.charAt(J1temp.length()-1) == ')'||
								   J1temp.charAt(J1temp.length()-1) == '.'||flag3)
								{}
								else
								{
									J1temp = J1.getText();
									if(key == 110)
									{
										J1temp += (char)(key - 64);
									}
									else
									{
										J1temp += (char)key;
									}
									J1.setText(J1temp);
								}
							}
						}
						if( key == 106 || key == 107 || key == 109 || key == 111 || key == 45 || key == 47 )//小键盘上的加减乘除
						{																//以及大键盘上的减号和除号(不依赖组合键)
							J1temp = J1.getText();
							if(J1temp==null || J1temp.equals("")){}
							else
							{
								if(J1temp.charAt(J1temp.length()-1) == '/'||
								   J1temp.charAt(J1temp.length()-1) == '*'||
								   J1temp.charAt(J1temp.length()-1) == '-'||
								   J1temp.charAt(J1temp.length()-1) == '+'||
								   J1temp.charAt(J1temp.length()-1) == '('||
								   J1temp.charAt(J1temp.length()-1) == '.')
								{}//不能在这些符号后面输入运算符
								else
								{
									J1temp = J1.getText();
									if(key == 45 || key == 47 )
									{
										J1temp += (char)key;
									}
									else
									{
										J1temp += (char)(key-64);
									}
									J1.setText(J1temp);
								}
							}
						}
						if(key == KeyEvent.VK_DELETE) //点击此按钮清空J1和s)
						{
							J1.setText("");
							s = "";
						}
						else{}
					}
					else
					{
						if(key == KeyEvent.VK_EQUALS) //组合键加号'shift' + '='
 						{
							J1temp = J1.getText();
							if(J1temp==null || J1temp.equals("")){}
							else
							{
								if(J1temp.charAt(J1temp.length()-1) == '/'||
								   J1temp.charAt(J1temp.length()-1) == '*'||
								   J1temp.charAt(J1temp.length()-1) == '-'||
								   J1temp.charAt(J1temp.length()-1) == '+'||
								   J1temp.charAt(J1temp.length()-1) == '('||
								   J1temp.charAt(J1temp.length()-1) == '.')
								{}//不能在这些符号后面输入运算符
								else
								{
									J1temp = J1.getText();
									J1temp += '+';
									J1.setText(J1temp); 
								}
							}
						}
						if(key == KeyEvent.VK_9)//组合键左括号'shift' + '9'
						{
							J1temp = J1.getText();
							if(J1temp==null || J1temp.equals(""))
							{
								J1.setText("(");
							}
							else
							{
								if(J1temp.charAt(J1temp.length()-1) == '0' ||
								   J1temp.charAt(J1temp.length()-1) == '1' ||
								   J1temp.charAt(J1temp.length()-1) == '2' ||
								   J1temp.charAt(J1temp.length()-1) == '3' ||
								   J1temp.charAt(J1temp.length()-1) == '4' ||
								   J1temp.charAt(J1temp.length()-1) == '5' ||
								   J1temp.charAt(J1temp.length()-1) == '6' ||
								   J1temp.charAt(J1temp.length()-1) == '7' ||
								   J1temp.charAt(J1temp.length()-1) == '8' ||
								   J1temp.charAt(J1temp.length()-1) == '9' ||
								   J1temp.charAt(J1temp.length()-1) == '.' ||
								   J1temp.charAt(J1temp.length()-1) == ')' ){}
								else
								{
									J1temp += '(';
									J1.setText(J1temp);
								}
							}
						}
						if(key == KeyEvent.VK_0)//组合键右括号'shift' + '0'
						{
							J1temp = J1.getText();
							if(J1temp==null || J1temp.equals("")){}
							else
							{
								if(J1temp.charAt(J1temp.length()-1) == '.' ||
								   J1temp.charAt(J1temp.length()-1) == '/' ||
								   J1temp.charAt(J1temp.length()-1) == '*' ||
								   J1temp.charAt(J1temp.length()-1) == '-' ||
								   J1temp.charAt(J1temp.length()-1) == '+' ||
								   J1temp.charAt(J1temp.length()-1) == '(' ||
								   J1temp == ""){}
								else
								{
									J1temp += ')';
									J1.setText(J1temp);
								}
							}
						}
					}
				}
			}
}	
