package xjx;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Stack;

public class MainWindow extends JFrame {
    private JTextArea result;
    private JTextArea history_result;
    private final int btn_nums = 20;
    private final JButton[] btn_sets = new JButton[btn_nums];
    private boolean done = true;

    public MainWindow(){
        super("计算器");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Image img = Toolkit.getDefaultToolkit().getImage("title.png");//窗口图标
        setIconImage(img);

        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
            e1.printStackTrace();
        }

        setLayout(new GridLayout(1,2,2,0));
        JPanel workSpace = new JPanel();
        workSpace.setBackground(Color.white);
        add(workSpace);
        JPanel historySpace = new JPanel();
        historySpace.setBackground(Color.white);
        add(historySpace);

        //上边显示结果的区域
        workSpace.setLayout(new BorderLayout());
        result = new JTextArea(8,10);
        result.setFont(new Font("微软雅黑",Font.BOLD,25));
        result.setEditable(false);
        result.setLineWrap(true);
        workSpace.add(result, BorderLayout.NORTH);

        //下边按钮区域
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(5,4));
        for(int i = 0; i < btn_nums; i++) {
            String[] btn_text = {
                    "(", ")", "C", "÷",
                    "7", "8", "9", "x",
                    "4", "5", "6", "-",
                    "1", "2", "3", "+",
                    "±", "0", ".", "="
            };
            btn_sets[i] = new JButton(btn_text[i]);
            btn_sets[i].addActionListener(new ButtonMonitor());
            btn_sets[i].setFont(new Font("微软雅黑",Font.PLAIN,20));
            btn_sets[i].setFocusable(false);

            btnPanel.add(btn_sets[i]);
        }
        workSpace.add(btnPanel);

        //右边历史区域
        historySpace.setLayout(new BorderLayout());

        JLabel history_title = new JLabel("历史记录");
        history_title.setFont(new Font("微软雅黑",Font.BOLD,20));
        historySpace.add(history_title, BorderLayout.NORTH);

        JButton clear_all = new JButton("clear all");
        clear_all.setFont(new Font("微软雅黑",Font.BOLD,15));
        clear_all.setFocusable(false);
        clear_all.addActionListener(e -> history_result.setText(""));

        history_result = new JTextArea();
        history_result.setFont(new Font("微软雅黑",Font.BOLD,20));
        history_result.setEditable(false);
        history_result.setLineWrap(true);

        JScrollPane history_scroll = new JScrollPane(history_result);
        historySpace.add(history_scroll, BorderLayout.CENTER);

        setSize(800,600);
        setMinimumSize(new Dimension(800,600));
        setLocationRelativeTo(null);//显示在屏幕中央
        setFocusable(true);
        setVisible(true);
    }

    public static void main(String[] args){
        MainWindow wnd = new MainWindow();
//        String exp = "((1+2x4)÷3-2)÷(1-1)";
//        exp = "(9x5)";
//        ExpRes res = wnd.calculateExp(exp);
//        System.out.println(res.tag + " " + res.msg + " " + res.res);
    }

    private void addNegative(){
        String exp = result.getText();

        //添加负号
        int ptr = exp.length()-1;
        while (ptr >= 0 && exp.charAt(ptr) >= 48 && exp.charAt(ptr) <= 57){
            ptr--;
        }
        String tmp = exp.substring(0, ptr+1) + "(" + "-" + exp.substring(ptr+1) + ")";
        result.setText(tmp);
    }

    private boolean checkLegality(char pre_char, char this_char){
        //pre_char为前一个位置的字符，this_char为当前输入的字符
        switch (this_char) {
            case '(' -> {
                return pre_char == ' ' || pre_char == '(' || pre_char == '+' || pre_char == '-' ||
                        pre_char == 'x' || pre_char == '÷';
            }
            case ')', '+', '-', 'x', '÷' -> {
                return pre_char == ')' || (pre_char >= 48 && pre_char <= 57);
            }
            case '.', '_' -> {
                return pre_char >= 48 && pre_char <= 57;
            }
            default -> {
                return pre_char == ' ' || pre_char == '.' || pre_char == '(' || pre_char == '+' || pre_char == '-' ||
                        pre_char == 'x' || pre_char == '÷' || (pre_char >= 48 && pre_char <= 57);
            }
        }
    }

    private int checkPriority(char a, char b){
        //检查运算符a和b的优先级,a为符号栈栈顶运算符，b为待插入运算符
        int[][] priority = new int[][]{
                /* + */{-1, -1, 1, 1},
                /* - */{-1, -1, 1, 1},
                /* x */{-1, -1, -1, -1},
                /* ÷ */{-1, -1, -1, -1},
                /* ( */{1, 1, 1, 1}};
                    /*  +  -  x  ÷  */
        HashMap<Character, Integer> char2idx = new HashMap<>();
        char2idx.put('+', 0);
        char2idx.put('-', 1);
        char2idx.put('x', 2);
        char2idx.put('÷', 3);
        char2idx.put('(', 4);

        return priority[char2idx.get(a)][char2idx.get(b)];
    }

    private void getResult(){
        String exp = result.getText();
        ExpRes expRes = calculateExp(exp);

        int res_int = (int)expRes.res;
        String res_str = "";
        if(expRes.tag.equals("OK")){
            if(expRes.res == 0) res_str += "0";
            else{
                if(expRes.res/res_int != 1){
                    res_str += expRes.res;
                }else{
                    res_str += res_int;//去掉整数结果后面的小数
                }
            }
        }

        System.out.println(expRes.tag + " " + expRes.msg + " " + expRes.res);

        if(expRes.tag.equals("OK")){
            result.setText(exp + " =\n" + res_str);
            String old_history = history_result.getText();
            history_result.setText(old_history + exp + " =\n" + res_str + "\n\n");
        }else if(expRes.tag.equals("ERROR")){
            result.setText(exp + "\n" + expRes.tag + " : " + expRes.msg);
        }

        done = true;
    }

    private ExpRes calculateExp(String exp){
        //计算表达式的值
        Stack<Character> operatorStack = new Stack<>();
        Stack<Double> numberStack = new Stack<>();
        int ptr = 0;
        while(ptr != exp.length()){
            char cur = exp.charAt(ptr++);
            if(cur >= 48 && cur <= 57){//当前字符为数字
                String numBuffer = "" + cur;
                while(ptr < exp.length() && ((exp.charAt(ptr) >= 48 &&  exp.charAt(ptr) <= 57) || exp.charAt(ptr) == '.')){
                    numBuffer += exp.charAt(ptr++);
                }
                double value = Double.parseDouble(numBuffer);
                numberStack.add(value);
            }else if(cur == '('){
                if(ptr < exp.length() && exp.charAt(ptr) == '-'){//左括号右边紧接-号，证明这是一个负数
                    String numBuffer = "-";
                    while(ptr < exp.length() && exp.charAt(ptr) != ')'){
                        numBuffer += exp.charAt(ptr++);
                    }
                    double value = Double.parseDouble(numBuffer);
                    numberStack.add(value);
                    ptr++;
                }else{
                    operatorStack.add(cur);
                }
            }else if(cur == ')'){
                char top = 0;
                //操作符栈非空
                if(!operatorStack.empty()) top = operatorStack.peek();
                while(top != '('){
                    double num1 = numberStack.pop();
                    double num2 = numberStack.pop();
                    double res = 0;
                    char operator = operatorStack.pop();
                    switch (operator) {
                        case '+' -> res = num2 + num1;
                        case '-' -> res = num2 - num1;
                        case 'x' -> res = num2 * num1;
                        case '÷' -> {
                            if (num1 != 0) {
                                res = num2 / num1;
                            } else {
                                return new ExpRes("ERROR", "Divided by 0", 0);
                            }
                        }
                    }
                    numberStack.add(res);
                    if(!operatorStack.empty()) top = operatorStack.peek();
                    if(top == '(') top = operatorStack.pop();
                }
            }else if(cur == '+' || cur == '-' || cur == 'x' || cur == '÷'){
                if(operatorStack.empty()) operatorStack.add(cur);
                else {
                    char top = operatorStack.peek();
                    if(checkPriority(top, cur) == -1){
                        double num1 = numberStack.pop();
                        double num2 = numberStack.pop();
                        double res = 0;
                        char operator = operatorStack.pop();
                        switch (operator) {
                            case '+' -> res = num2 + num1;
                            case '-' -> res = num2 - num1;
                            case 'x' -> res = num2 * num1;
                            case '÷' -> {
                                if (num1 != 0) {
                                    res = num2 / num1;
                                } else {
                                    return new ExpRes("ERROR", "Divided by 0", 0);
                                }
                            }
                        }
                        operatorStack.add(cur);
                        numberStack.add(res);
                    }else{
                        operatorStack.add(cur);
                    }
                }
            }
        }

        if(!operatorStack.empty()){
            while (!operatorStack.empty()){
                double num1 = numberStack.pop();
                double num2 = numberStack.pop();
                double res = 0;
                char operator = operatorStack.pop();
                switch (operator) {
                    case '+' -> res = num2 + num1;
                    case '-' -> res = num2 - num1;
                    case 'x' -> res = num2 * num1;
                    case '÷' -> {
                        if (num1 != 0) {
                            res = num2 / num1;
                        } else {
                            return new ExpRes("ERROR", "Divided by 0", 0);
                        }
                    }
                }
                numberStack.add(res);
            }
        }

        double exp_res = numberStack.pop();
        return new ExpRes("OK", "", exp_res);
    }

    class ButtonMonitor implements ActionListener{
        public void actionPerformed(ActionEvent e){
            JButton clickBtn = (JButton)e.getSource();
            for(int i = 0; i < btn_nums; i++){
                if(clickBtn == btn_sets[i]){
                    if(done) result.setText("");

                    if(i == 2){//按下C(Clear)
                        result.setText("");
                        done = true;
                    }else if(i == 16){//按下负号
                        String exp = result.getText();
                        if(!exp.isEmpty() && checkLegality(exp.charAt(exp.length()-1), '_')){
                            addNegative();
                            done = false;
                        }
                    }else if(i == 19){//按下等号
                        System.out.println("Exp: " + result.getText());
                        getResult();
                    }else {
                        String exp = result.getText();
                        char pre_char = ' ';
                        if(!exp.isEmpty()){
                            pre_char = exp.charAt(exp.length()-1);
                        }
                        if(checkLegality(pre_char, btn_sets[i].getText().charAt(0))){
                            result.setText(exp + btn_sets[i].getText());
                            done = false;
                        }
                    }
                }
            }
        }
    }

    class ExpRes{
        String tag;
        String msg;
        double res;

        public ExpRes(String tag, String msg, double res){
            this.tag = tag;
            this.msg = msg;
            this.res = res;
        }
    }
}
