# Calculator
This is a Calculaotr program in Java

Use Guide:
This program can do some basic calculation.
The operands include  positive number and negative number as well as decimal number.
When you want to enter a negative number ,you are supposed to enter the button "_" in the left-bottom of the main window
of the calculator,it will generate a pair of brackets as well as a  minus for the number you have just entered automaticaly.
for example:
if you have just typed 12.3
then click the "_" button
in the workspace area,it will appera (-12.3)
if you want to delete the character you have just entered,you can either click the "<--" button or just type the "Backspace"
button on your keyboard.if you want to delele all the all the characters in the workspace which is located in the upper left
corner of the calculator,just click the "Delete" button on your keyboard.if you want to clear all the distory appeared on the
right ,you can click the button in the bottom right corner.At last,you can click the "Enter" button or the "=" button on your 
keyboard or the "=" button on the screen tp get the result.
This program will automatically prevent some illegal input
for example:
the first character entered can not be "." or "_" or "/" or "*" or "+" or "-" or ")"
and you can not enter a "." after a series of numbers like "12.3" or after "(" or ")" etc.
every you click "Enter" ,it will check if the last character is legal,if not ,there will be an "ERROR!" result on the history
space.
the pragram also contains bracket match,but unfortunately , this can not be processed while you are typing,in can only check
the bracket match after you finish typing and click "Enter"
for example:
every time you type a "(" ,the program can not decide if it's legal,for it can be either legal or illegal , it depends on the 
")" that you will type,but we can never predict that.
if you the expression is something like this,"((2+3)" or "(2+3))" or "()+2" or "((()",there will be an "ERROR!" result on the history
space.

About this pragram:
This program contains three parts which are appeared in three .java files separately.
the fist is Calculator.java which draws the main window and achieves the listeners.
the second is EvaluateExpression.java which is the calculation of the nifix expression,it gets the expression to be calculated
from the workspace area where you have entered the expression.
the third is Arith.java which can do precise calculations between two double numbers without any error.

there may still exist some small bugs,if you find,contact me at thexjx@gmail.com 
