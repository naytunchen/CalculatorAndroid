package nay.calculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

import nay.calculator.helpers.Helpers;

public class MainActivity extends AppCompatActivity {

    public static final char ZERO = '0';
    public static final char ONE = '1';
    public static final char TWO = '2';
    public static final char THREE = '3';
    public static final char FOUR = '4';
    public static final char FIVE = '5';
    public static final char SIX = '6';
    public static final char SEVEN = '7';
    public static final char EIGHT = '8';
    public static final char NINE = '9';

    public static final char DEC = '.';
    public static final char DIVIDE = '/';
    public static final char MULTIPLY = '*';
    public static final char ADD = '+';
    public static final char SUBTRACT = '-';

    // to store all the operators
    Stack<Character> operators = new Stack<Character>();

    StringBuilder builder = new StringBuilder();
    TextView display_panel;
    boolean decExist = false;   // used to prevent multiple decimal point in a number
    boolean operatorFlag = false;   // used to prevent consecutive operators

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve TextView from View
        display_panel = (TextView)findViewById(R.id.display_panel);

        /*** Number Buttons ***/
        Button button0 = (Button)findViewById(R.id.button0);
        Button button1 = (Button)findViewById(R.id.button1);
        Button button2 = (Button)findViewById(R.id.button2);
        Button button3 = (Button)findViewById(R.id.button3);
        Button button4 = (Button)findViewById(R.id.button4);
        Button button5 = (Button)findViewById(R.id.button5);
        Button button6 = (Button)findViewById(R.id.button6);
        Button button7 = (Button)findViewById(R.id.button7);
        Button button8 = (Button)findViewById(R.id.button8);
        Button button9 = (Button)findViewById(R.id.button9);

        /*** operators buttons ***/
        Button button_dec = (Button)findViewById(R.id.button_dec);
        Button button_equal = (Button)findViewById(R.id.button_equal);
        final Button button_del = (Button)findViewById(R.id.button_del);
        Button button_div = (Button)findViewById(R.id.button_div);
        Button button_mult = (Button)findViewById(R.id.button_mult);
        Button button_sub = (Button)findViewById(R.id.button_sub);
        Button button_add = (Button)findViewById(R.id.button_add);


        /*** number buttons OnClickListener Implementation ***/
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.append(ZERO);
                display_panel.setText(builder.toString());

            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.append(ONE);
                display_panel.setText(builder.toString());

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.append(TWO);
                display_panel.setText(builder.toString());

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.append(THREE);
                display_panel.setText(builder.toString());

            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.append(FOUR);
                display_panel.setText(builder.toString());

            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.append(FIVE);
                display_panel.setText(builder.toString());

            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.append(SIX);
                display_panel.setText(builder.toString());

            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.append(SEVEN);
                display_panel.setText(builder.toString());

            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.append(EIGHT);
                display_panel.setText(builder.toString());

            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.append(NINE);
                display_panel.setText(builder.toString());

            }
        });


        /*** Operators Button Implementation ***/
        // Decimal button implementation
        button_dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!decExist) {
                    builder.append(DEC);
                    display_panel.setText(builder.toString());
                    decExist = true;
                }
            }
        });

        // Delete button implementation
        button_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CharSequence text = display_panel.getText();
                if((text != null || !text.toString().isEmpty()) && text.length() != 0){
                    // remove last char of the string (last digit or operator of the string)
                    if(text.toString().charAt(text.toString().length()-1) == DEC) {
                        // if deleted character is decimal point, reset decExist flag
                        decExist = false;
                    }
                    else if(Helpers.lastCharIsOperator(text)){
                        // if deleted character is operator, pop the last element of the operators (stack)
                        // reset operatorFlag
                        operators.pop();
                        operatorFlag = false;
                    }

                    builder.delete(0, builder.length());    // clear out builder (Stringbuilder)
                    builder.append(text.toString().substring(0, text.toString().length() - 1)); // copy the whole equation
                    int last_operator_index = Helpers.getLastOperatorIndex(builder.toString()); // get the index of the last operator


                    // After deleting, business logic
                    if(last_operator_index == -1) {
                        // if only one number exist in string (no operators), then check for decmial point
                        if(builder.toString().contains(".")) {
                            decExist = true;
                        }
                        else {
                            decExist = false;
                        }
                    }
                    else{
                        // if there are numbers and operators in string
                        if(last_operator_index == builder.length()-1) {
                            // check if the last character is operator, if so, then allow decimal point
                            decExist = false;
                        }
                        else if(last_operator_index <= builder.length() - 1) {
                            // otherwise, check the last number contains decimal point or not
                            String last_operand = builder.substring(last_operator_index, builder.length()-1);   // get the number after the last operand in the string
                            if(last_operand.contains(".")) {
                                // if the number contains decimal point, set true
                                decExist = true;
                            }
                            else {
                                // if the number doesn't containt decimal point, set false
                                decExist = false;
                            }
                        }
                    }
                    display_panel.setText(builder.toString());
                }
            }
        });

        // Operator button OnClickListener Implementations
        // Logic: operator should NOT show on display panel if the panel is empty (no operand is entered)

        button_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text = display_panel.getText();
                if(text != null &&  text.length() != 0 && !Helpers.lastCharIsOperator(text)) {
                    builder.append(DIVIDE);
                    display_panel.setText(builder.toString());
                    operators.push(DIVIDE); // push '/' to stack
                    operatorFlag = true;
                    decExist = false;
                }

            }
        });

        button_mult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text = display_panel.getText();
                if(text != null &&  text.length() != 0 && !Helpers.lastCharIsOperator(text)) {
                    builder.append(MULTIPLY);
                    display_panel.setText(builder.toString());
                    operators.push(MULTIPLY); // push '*' to stack
                    operatorFlag = true;
                    decExist = false;
                }

            }
        });

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text = display_panel.getText();
                if(text != null &&  text.length() != 0 && !Helpers.lastCharIsOperator(text)) {
                    builder.append(ADD);
                    display_panel.setText(builder.toString());
                    operators.push(ADD);    // push '+' to stack
                    operatorFlag = true;
                    decExist = false;
                }
            }
        });

        button_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text = display_panel.getText();
                if(text != null &&  text.length() != 0 && !Helpers.lastCharIsOperator(text)) {
                    builder.append(SUBTRACT);
                    display_panel.setText(builder.toString());
                    operators.push(SUBTRACT);   // push '-' to stack
                    operatorFlag = true;
                    decExist = false;
                }
            }
        });


        // equal button logic implementation
        button_equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text = display_panel.getText();
                // if the display panel is empty or last character is an operator, do nothing
                if(text == null || text.length() == 0 || Helpers.lastCharIsOperator(text)) {
                    return;
                }
                String equation = text.toString();
                Iterator it = operators.iterator();


                Vector<String> string_list = new Vector<String>();
                Vector<String> new_list = new Vector<String>();
                boolean computed = false;
                float result = 0;
                int i = 0;
                char entry;
                // Tokenize the whole String by using *,/,+,- sign as delimetsr
                // To get all the operands as tokens
                StringTokenizer token = new StringTokenizer(equation, "*/+-");
                while(token.hasMoreTokens()) {

                    // Add operand and operators by turn to the string vector to recreate the
                    // equation
                    string_list.add(token.nextToken());

                    if(it.hasNext()){
                        entry = (char)it.next();
                        string_list.add(String.valueOf(entry));
                    }
                }

                StringBuilder sb2 = new StringBuilder();
                for(String s : string_list) {
                    sb2.append(s);
                }
                Log.e("THE EQUATION IS", sb2.toString());


                // Go through the vector list and check for the operators
                for(int index = 1; index < string_list.size(); index=index+2) {
                    if(!Helpers.isHighPrecedence(string_list.get(index))) {
                        // if operator is + or -, store the left operand of the current operator
                        // to a new vector
                        if(computed) {
                            // if the computed flag is enabled, then store the previously computed
                            // result as the operand
                            new_list.add(String.valueOf(result));
                        }
                        else {

                            // otherwise, store the left operand
                            new_list.add(string_list.get(index-1));
                        }
                        // then, add the current operator to the new vector to create a new equation
                        new_list.add(string_list.get(index));
                        if(index == string_list.size()-2) {
                            // if current operator is the last one, add the last element (operand)
                            // to the vector
                            new_list.add(string_list.get(index+1));
                        }
                        computed = false;
                    }
                    else{
                        // if the current operator is either * or /, then do the computation,
                        // and store the result
                        if(computed){
                            // if previously computed, then use result as the left operand to get new result
                            result = Helpers.compute(String.valueOf(result), string_list.get(index+1), string_list.get(index).charAt(0));
                        }
                        else {
                            // otherwise, use the left operand of the operator in the vector to compute the result
                            result = Helpers.compute(string_list.get(index-1), string_list.get(index+1), string_list.get(index).charAt(0));
                        }
                        if(index == string_list.size()-2) {
                            // if the current operator is last one, add the result to the vector
                            new_list.add(String.valueOf(result));
                        }
                        computed = true;
                    }
                }

                StringBuilder sb = new StringBuilder();
                for(String c : new_list) {
                    Log.e("current", c);
                    sb.append(c);
                }

                Log.e("new string", sb.toString());

                // At this point, only + and - operations left, compute
                for(int index = 1; index < new_list.size(); index=index+2) {
                    if(index == 1) {
                        // compute first operation
                        result = Helpers.compute(new_list.get(index - 1), new_list.get(index + 1), new_list.get(index).charAt(0));
                    }
                    else{
                        // compute next operation using result
                        result = Helpers.compute(String.valueOf(result), new_list.get(index + 1), new_list.get(index).charAt(0));
                    }
                }

                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("result", String.valueOf(result));
                startActivity(intent);
                MainActivity.this.finish();

            }
        });

    }
}
