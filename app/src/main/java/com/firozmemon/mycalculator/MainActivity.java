package com.firozmemon.mycalculator;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    TextView tv_text;
    Button btn_memoryClear, btn_addToMemory, btn_subtractFromMemory, btn_recallMemory;
    Button btn_clear, btn_toggleSign;

    DecimalFormat decimalFormat = new DecimalFormat("#.##########");

    private static String operand1, operand2;
    private static Operation operationType;
    private static boolean newOperand;

    public enum Operation {
        ADD, SUBTRACT, MULTIPLY, DIVIDE, NONE
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_text = (TextView) findViewById(R.id.tv_text);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/digital.ttf");
        tv_text.setTypeface(font);

        // Default operation
        operationType = Operation.NONE;

        // Operand Click
        findViewById(R.id.btn_one).setOnClickListener(this);
        findViewById(R.id.btn_two).setOnClickListener(this);
        findViewById(R.id.btn_three).setOnClickListener(this);
        findViewById(R.id.btn_four).setOnClickListener(this);
        findViewById(R.id.btn_five).setOnClickListener(this);
        findViewById(R.id.btn_six).setOnClickListener(this);
        findViewById(R.id.btn_seven).setOnClickListener(this);
        findViewById(R.id.btn_eight).setOnClickListener(this);
        findViewById(R.id.btn_nine).setOnClickListener(this);
        findViewById(R.id.btn_zero).setOnClickListener(this);
        findViewById(R.id.btn_dot).setOnClickListener(this);

        // Clear Text
        findViewById(R.id.btn_clear).setOnClickListener(this);

        // Operation
        findViewById(R.id.btn_minus).setOnClickListener(this);
        findViewById(R.id.btn_plus).setOnClickListener(this);
        findViewById(R.id.btn_multiplication).setOnClickListener(this);
        findViewById(R.id.btn_divide).setOnClickListener(this);
        findViewById(R.id.btn_equal).setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        String buttonText = ((Button) view).getText().toString();
        String textDisplay = tv_text.getText().toString();
        switch (view.getId()){
            case R.id.btn_one:
            case R.id.btn_two:
            case R.id.btn_three:
            case R.id.btn_four:
            case R.id.btn_five:
            case R.id.btn_six:
            case R.id.btn_seven:
            case R.id.btn_eight:
            case R.id.btn_nine:
            case R.id.btn_zero:
                if (!newOperand) {

                    // if text already contains 0
                    // than remove preceding 0 and display numbers accordingly
                    if (textDisplay.length() == 1 && textDisplay.equalsIgnoreCase(getString(R.string.zero)))
                        tv_text.setText("");

                    tv_text.append(buttonText);
                } else {
                    // Set new operand text
                    tv_text.setText(buttonText);
                    newOperand = false;
                }
                break;

            case R.id.btn_dot:
                if (!newOperand) {
                    // Check if text already contains decimal
                    if (!textDisplay.contains(getString(R.string.dot))) {
                        if (textDisplay.length() == 0)
                            tv_text.append("0" + buttonText);
                        else
                            tv_text.append(buttonText);
                    }
                    // else do not add more decimal dots
                } else {
                    tv_text.setText("0" + buttonText);
                    newOperand = false;
                }
                break;

            case R.id.btn_clear:
                tv_text.setText("0");
                // Reset all operations and operands
                operand1 = operand2 = null;
                operationType = Operation.NONE;
                newOperand = false;
                break;

            case R.id.btn_minus:
            case R.id.btn_plus:
            case R.id.btn_divide:
            case R.id.btn_multiplication:
                if (!newOperand) {
                    newOperand = true;

                    performOperation(textDisplay);
                }
                if (operationType == Operation.NONE)
                    operand1 = textDisplay;
                setupOperator(buttonText);
                break;

            case R.id.btn_equal:
                if (operationType != Operation.NONE) {
                    performOperation(textDisplay);
                    operand1 = operand2 = null;
                    operationType = Operation.NONE;
                    newOperand = true;
                }
                break;
        }
    }

    private void setupOperator(String buttonText) {
        switch (buttonText){
            case "+":
                operationType = Operation.ADD;
                break;
            case "-":
                operationType = Operation.SUBTRACT;
                break;
            case "*":
                operationType = Operation.MULTIPLY;
                break;
            case "/":
                operationType = Operation.DIVIDE;
                break;
            default:
                operationType = Operation.NONE;
                break;
        }
    }

    private void performOperation(String text) {
        if (operationType == Operation.NONE) {
            // Operation Type not specified then do not do anything
            operand1 = text;
        } else {
            // Performing Operation
            double op1 = Double.parseDouble(operand1);
            double op2 = Double.parseDouble(text);
            if (operationType == Operation.ADD) {
                op1 = op1 + op2;
            } else if (operationType == Operation.SUBTRACT) {
                op1 = op1 - op2;
            } else if (operationType == Operation.MULTIPLY) {
                op1 = op1 * op2;
            } else if (operationType == Operation.DIVIDE) {
                op1 = op1 / op2;    // Todo: Check if op2 is 0 to prevent DivideByZero Exception
            }
            operand1 = String.valueOf(op1);
            op1 = op2 = Double.NaN; // Resetting for GC
            tv_text.setText(operand1);
        }
    }
}
