package nay.calculator.helpers;

import android.util.Log;

import nay.calculator.MainActivity;

/**
 * Created by naytun on 8/12/15.
 */
public class Helpers {

    public static boolean lastCharIsOperator(CharSequence text) {
        char last_char = text.charAt(text.length()-1);
        return (last_char == '-' || last_char == '*' || last_char == '/' || last_char == '+');
    }

    public static int getLastOperatorIndex(String text) {
        int div_index = text.toString().lastIndexOf('/');
        int mult_index = text.toString().lastIndexOf('*');
        int add_index = text.toString().lastIndexOf('+');
        int sub_index = text.toString().lastIndexOf('-');

        int max = div_index;
        if(mult_index > max) {
            max = mult_index;
        }

        if(add_index > max) {
            max = add_index;
        }

        if(sub_index > max) {
            max = sub_index;
        }

        return max;

    }

    public static int getFirstHighOperatorIndex(String text) {
        int div_index = text.toString().indexOf('/');
        int mult_index = text.toString().indexOf('*');

        return (mult_index <= div_index) ? mult_index : div_index;
    }

    public static int getFirstLowOperatorIndex(String text) {
        int add_index = text.toString().indexOf('+');
        int sub_index = text.toString().indexOf('-');

        return (add_index <= sub_index) ? add_index : sub_index;
    }

    public static boolean isHighPrecedence(String op) {
        if(op.equals("*") || op.equals("/")) {
            return true;
        }
        return false;
    }

    public static float compute(String op1, String op2, char operator) {

        float o1 = Float.parseFloat(op1);
        float o2 = Float.parseFloat(op2);

        Log.e("Operand 1", op1);
        Log.e("Operand 2", op2);
        Log.e("Operator", operator + "");

        switch(operator) {
            case '-':
                return (o1 - o2);
            case '+':
                return (o1 + o2);
            case '/':
                return (o1 / o2);
            case '*':
                return (o1 * o2);
            default: break;
        }
        return -1;
    }
}
