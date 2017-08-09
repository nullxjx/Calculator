package 计算器;

import javax.swing.JOptionPane;

class NumberStack{
	final int STACK_INIT_SIZE = 10;
	final int STACKINCREASE = 5;
	
	private double[] Array; // 数字栈
	private int ptr = 0;    //指向栈顶元素的下一个元素的数组下标
	
	public NumberStack(){
		Array = new double[STACK_INIT_SIZE];
		for(int i = 0;i < STACK_INIT_SIZE;i++ )
		{
			Array[i] = 0;
		}
	}
	
	public void Push(double e){
		Array[ptr++] = e;
	}
	public double Pop(){
		if(ptr < 1)
		{
			JOptionPane.showMessageDialog(null, "栈为空！", "Error",JOptionPane.WARNING_MESSAGE);
			return 0;
		}
		else return Array[--ptr];
	}
	public double GetTop(){
		if(ptr < 1)
		{
			JOptionPane.showMessageDialog(null, "栈为空！", "Error",JOptionPane.WARNING_MESSAGE); 
			return 0;
		}
		else return Array[ptr-1];
	}
	public boolean EmptyStack(){
		if(ptr < 1) return true;
		else return false;
	}
}

class CharStack{
	final int STACK_INIT_SIZE = 40;
	final int STACKINCREASE = 10;
	
	private char[] Array; // 字符栈
	private int ptr = 0;    //指向栈顶元素的下一个元素的数组下标
	
	public CharStack(){
		Array = new char[STACK_INIT_SIZE];
		for(int i = 0;i < STACK_INIT_SIZE;i++ )
		{
			Array[i] = '\0';
		}
	}
	
	public void Push(char e){
		Array[ptr++] = e;
	}
	public char Pop(){
		if(ptr < 1)
		{
			JOptionPane.showMessageDialog(null, "栈为空！", "Error",JOptionPane.WARNING_MESSAGE); 
			return '\0';
		}
		else return Array[--ptr];
	}
	public char GetTop(){
		if(ptr < 1)
		{
			JOptionPane.showMessageDialog(null, "栈为空！", "Error",JOptionPane.WARNING_MESSAGE); 
			return '\0';
		}
		else return Array[ptr-1];
	}
	public boolean EmptyStack(){
		if(ptr < 1) return true;//为空
		else return false;
	}
}


class Expression{
	String s = "";
	public Expression(String s){
		this.s = s;
	}
	
	public boolean Precede(char a,char b)//a为符号栈栈顶元素,b为待插入的元素
	{
	    boolean i = true;//i=true入栈,i=false弹出操作符以及操作数进行计算
	    if(( a == '+' || a == '-' ) && ( b == '*' || b == '/' )) i = true;
	    if(( a == '+' || a == '-' ) && ( b == '+' || b == '-' )) i = false;
	    if(( a == '*' || a == '/' ) && ( b == '*' || b == '/' )) i = false;
	    if(( a == '*' || a == '/' ) && ( b == '+' || b == '-' )) i = false;
	    if(( a == '+' || a == '-' || a == '*' || a == '/') && b == '^' ) i=true;
	    if(a == '^' && ( b == '+' || b == '-' || b == '*' || b == '/' )) i=false;
	    if( a == '^' && b == '^' ) i = false;
	    if( a == '(' ) i = true;
	    return i;
	}
	
	public double Decimal(int n)//计算10的n次方
	{
		double s = 1.0;
		if( n >= 0)
		{
			for(int i = 0; i < n; i++)
			{
				//s *= 10; 注意直接相乘会造成精度损失，不能用这种方法,以下所有涉及浮点数计算的都不能直接计算
				s = Arith.mul(s, 10);
			}
		}
		else
		{
			for(int i = 0; i < -n; i++)
			{
				s = Arith.mul(s, 0.1);
			}
		}
		return s;
	}
	
	public String EvaluateExpression()
	{
	    String temp = s , res = "";
	    String Numbuff = "";//缓存读到的数字字符和小数点，用以把读到的数字字符转换成相应的数字
	    char c,d,e = '\0';
	    int ptr = 0,ptrtemp = 0;
	    int flag = 0;
	    double a,b,j = 0;
	    double Numtemp = 0;
	    boolean l;
	    CharStack S1 = new CharStack();	//S1为操作符栈，S2为操作数栈
	    NumberStack S2 = new NumberStack();
	    
	    if(temp == "") 
	    {
	    	JOptionPane.showMessageDialog(null, "表达式为空！", "Error",JOptionPane.WARNING_MESSAGE);
	    	return "ERROR!";
	    }
	    c=temp.charAt(ptr++);
	    while(c != '=')//传进来的String s 以'='结束
	    {
	         if(c>=48&&c<=57) //读到数字
	         {
	        	 ptrtemp = ptr;
	        	 Numbuff += c;
	        	 //判断数字字符后面一个字符是否为数字字符或者小数点
	        	 while( ( temp.charAt(ptrtemp) >= 48 && temp.charAt(ptrtemp ) <= 57) || temp.charAt(ptrtemp) == '.')
	        	 {
	        		 Numbuff += temp.charAt(ptrtemp);
	        		 if(temp.charAt(ptrtemp) == '.')
	        		 {
	        			 flag = Numbuff.length()-1;//记录小数点在Numbuff中的位置
	        		 }
	        		 ptrtemp++;
	        	 }
	        	 if(Numbuff.length() > 1)//temp中不是单个数字字符
	        	 {
	        		 if(flag != 0) //包含小数点
	        		 {
	        			 for(int i = 0; i < flag ;i++)//处理整数部分
	        			 {
	        				 //Numtemp += ( (int)Numbuff.charAt(i) - 48 ) * Decimal(flag-1-i);//此处代码需修改,以下依次类推
	        				 Numtemp = Arith.add(Numtemp, Arith.mul( ( (int)Numbuff.charAt(i) - 48 ) ,Decimal(flag-1-i) ) );
	        			 }
	        			 for(int i = flag+1;i < Numbuff.length();i++)//处理小数部分
	        			 {
	        				 //Numtemp += ( (int)Numbuff.charAt(i) - 48 ) * Decimal(flag-i);
	        				 Numtemp = Arith.add(Numtemp, Arith.mul( ( (int)Numbuff.charAt(i) - 48 ) ,Decimal(flag-i) ) );
	        			 }
	        		 }
	        		 else
	        		 {
	        			 for(int i = 0;i < Numbuff.length(); i++)
	        			 {
	        				 //Numtemp += ( (int)Numbuff.charAt(i) - 48 ) * Decimal(Numbuff.length()-1-i);
	        				 Numtemp = Arith.add(Numtemp, Arith.mul( ( (int)Numbuff.charAt(i) - 48 ) ,Decimal(Numbuff.length()-1-i) ) );
	        			 }
	        		 }
	        	 }
	        	 else
	        	 {
	        		 //Numtemp = ( (int)Numbuff.charAt(0) - 48 );
	        		 Numtemp = Arith.sub((int)Numbuff.charAt(0) ,48 );
	        	 }
	        	 S2.Push(Numtemp);//把得到的浮点数压入操作数栈
	        	 //注意每次判断处理完数字,以下这些变量要重置为初始值
	        	 Numtemp = 0;
	        	 flag = 0;
	        	 Numbuff = "";
	        	 ptr = ptrtemp;//处理完一串数字字符，记得把ptr移到数字字符串的下一个位置
	         }
	         if(c=='(') //输入为左括号
	         {
	        	 ptrtemp = ptr;
	        	 if(temp.charAt(ptrtemp) == '-')//如果左括号后面一个符号为'-'，那么这个符号一定是负号，接下来要提取括号中的负数
	        	 {
	        		 ptrtemp++;
	        		 while(temp.charAt(ptrtemp) != ')')
	        		 {
	        			 Numbuff += temp.charAt(ptrtemp);
	        			 if(temp.charAt(ptrtemp) == '.')
	        			 {
	        				 flag = Numbuff.length()-1;//记录小数点在Numbuff中的位置
	        			 }
	        			 ptrtemp++;
	        		 }
	        		 if(Numbuff.length() > 1)//temp中不是单个数字字符
		        	 {
		        		 if(flag != 0) //包含小数点
		        		 {
		        			 for(int i = 0; i < flag ;i++)//处理整数部分
		        			 {
		        				 //Numtemp += ( (int)Numbuff.charAt(i) - 48 ) * Decimal(flag-1-i);
		        				 Numtemp = Arith.add(Numtemp, Arith.mul(( (int)Numbuff.charAt(i) - 48 ) , Decimal(flag-1-i)));
		        			 }
		        			 for(int i = flag+1;i < Numbuff.length();i++)//处理小数部分
		        			 {
		        				 //Numtemp += ( (int)Numbuff.charAt(i) - 48 ) * Decimal(flag-i);
		        				 Numtemp = Arith.add(Numtemp, Arith.mul(( (int)Numbuff.charAt(i) - 48 ) , Decimal(flag-i)));
		        			 }
		        		 }
		        		 else
		        		 {
		        			 for(int i = 0;i < Numbuff.length(); i++)
		        			 {
		        				 //Numtemp += ( (int)Numbuff.charAt(i) - 48 ) * Decimal(Numbuff.length()-1-i);
		        				 Numtemp = Arith.add(Numtemp, Arith.mul(( (int)Numbuff.charAt(i) - 48 ) , Decimal(Numbuff.length()-1-i)));
		        			 }
		        		 }
		        	 }
		        	 else
		        	 {
		        		 Numtemp = ( (int)Numbuff.charAt(0) - 48 );
		        	 }
	        		 Numtemp = 0 - Numtemp;//取相反数
		        	 S2.Push(Numtemp);//把得到的浮点数压入操作数栈
		        	 ptr += Numbuff.length() + 2;//处理完一串数字字符，记得把ptr移到数字字符串的下一个位置
		        	 //注意每次判断处理完数字,以下这些变量要重置为初始值
		        	 Numtemp = 0;
		        	 flag = 0;
		        	 Numbuff = "";
	        	 }
	        	 else
	        	 {
	        		 S1.Push(c);
	        	 }
	         }
	         if(c==')')//输入为右括号
	         {
	             if(!S1.EmptyStack()) e = S1.GetTop();
	             if(e == '(') { e = S1.Pop(); }
	             while(e!='(')
	             {
	                 a = S2.Pop();
	                 b = S2.Pop();
	                 d = S1.Pop();
	                 if(d=='+') j= Arith.add(b, a);
	                 if(d=='-') j= Arith.sub(b, a);
	                 if(d=='*') j= Arith.mul(b, a);
	                 if(d=='/')
	                 {
	                     if(a != 0)  j = b/a;//j= Arith.div(b, a, 30);//精度取30
	                     else 
	                     {
	                    	 JOptionPane.showMessageDialog(null, "计算过程出现除数为零的错误！", "Error",JOptionPane.WARNING_MESSAGE);
	                    	 return "ERROR!";
	                     }
	                 }
	                 if(d== '^') j = Math.pow(b, a);
	                 S2.Push(j);
	                 if(!S1.EmptyStack()) e = S1.GetTop();//直到遇到左括号
	                 if(e=='(') e = S1.Pop();
	             }
	         }
	         if(c=='+'||c=='-'||c=='*'||c=='/'||c=='^')
	         {
	             if(S1.EmptyStack())  S1.Push(c);
	             else
	             {
	                 e = S1.GetTop();
	                 l=Precede(e,c);
	                 if(!l)
	                 {
	                     a = S2.Pop();
	                     b = S2.Pop();
	                     d = S1.Pop();
	                     if(d=='+') j= Arith.add(b, a);
		                 if(d=='-') j= Arith.sub(b, a);
		                 if(d=='*') j= Arith.mul(b, a);
		                 if(d=='/')
		                 {
		                     if(a != 0) j = b/a;//j= Arith.div(b, a, 30);//精度取30
		                     else 
		                     {
		                    	 JOptionPane.showMessageDialog(null, "计算过程出现除数为零的错误！", "Error",JOptionPane.WARNING_MESSAGE);
		                    	 return "ERROR!";
		                     }
		                 }
		                 if(d== '^') j = Math.pow(b, a);
	                     S1.Push(c);
	                     S2.Push(j);
	                 }
	                 else S1.Push(c);
	             }
	         }
	         if(c == '√')
	         {
	        	 ptrtemp = ptr;
	        	 ptrtemp++;
        		 while(temp.charAt(ptrtemp) != ')')
        		 {
        			 Numbuff += temp.charAt(ptrtemp);
        			 if(temp.charAt(ptrtemp) == '.')
        			 {
        				 flag = Numbuff.length()-1;//记录小数点在Numbuff中的位置
        			 }
        			 ptrtemp++;
        		 }
        		 if(Numbuff.length() > 1)//temp中不是单个数字字符
	        	 {
	        		 if(flag != 0) //包含小数点
	        		 {
	        			 for(int i = 0; i < flag ;i++)//处理整数部分
	        			 {
	        				 //Numtemp += ( (int)Numbuff.charAt(i) - 48 ) * Decimal(flag-1-i);
	        				 Numtemp = Arith.add(Numtemp, Arith.mul(( (int)Numbuff.charAt(i) - 48 ) , Decimal(flag-1-i)));
	        			 }
	        			 for(int i = flag+1;i < Numbuff.length();i++)//处理小数部分
	        			 {
	        				 //Numtemp += ( (int)Numbuff.charAt(i) - 48 ) * Decimal(flag-i);
	        				 Numtemp = Arith.add(Numtemp, Arith.mul(( (int)Numbuff.charAt(i) - 48 ) , Decimal(flag-i)));
	        			 }
	        		 }
	        		 else
	        		 {
	        			 for(int i = 0;i < Numbuff.length(); i++)
	        			 {
	        				 //Numtemp += ( (int)Numbuff.charAt(i) - 48 ) * Decimal(Numbuff.length()-1-i);
	        				 Numtemp = Arith.add(Numtemp, Arith.mul(( (int)Numbuff.charAt(i) - 48 ) , Decimal(Numbuff.length()-1-i)));
	        			 }
	        		 }
	        	 }
	        	 else
	        	 {
	        		 Numtemp = ( (int)Numbuff.charAt(0) - 48 );
	        	 }
        		 Numtemp = Math.sqrt(Numtemp);//取相反数
	        	 S2.Push(Numtemp);//把得到的浮点数压入操作数栈
	        	 ptr += Numbuff.length() + 2;//处理完一串数字字符，记得把ptr移到数字字符串的下一个位置
	        	 //注意每次判断处理完数字,以下这些变量要重置为初始值
	        	 Numtemp = 0;
	        	 flag = 0;
	        	 Numbuff = "";
	         }
	         c = temp.charAt(ptr++);
	    }
	    
	    if(!S1.EmptyStack())
	    {
	        while(!S1.EmptyStack())
	        {
	             a = S2.Pop();
	             b = S2.Pop();
	             d = S1.Pop();
	             if(d=='+') j= Arith.add(b, a);
                 if(d=='-') j= Arith.sub(b, a);
                 if(d=='*') j= Arith.mul(b, a);
                 if(d=='/')
                 {
                     if(a != 0) j = b/a;//j= Arith.div(b, a, 30);//精度取30
                     else 
                     {
                    	 JOptionPane.showMessageDialog(null, "计算过程出现除数为零的错误！", "Error",JOptionPane.WARNING_MESSAGE);
                    	 return "ERROR!";
                     }
                 }
                 if(d== '^') j = Math.pow(b, a);
	             S2.Push(j);
	        }
	    }
	    //最后返回结果
	    j = S2.Pop();
	    res += j;
	    return res;
	}
}
