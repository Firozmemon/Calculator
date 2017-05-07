package com.firozmemon.mycalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_text;
    Button btn_memoryClear, btn_addToMemory, btn_subtractFromMemory, btn_recallMemory;
    Button btn_clear, btn_toggleSign;
    Button btn_minus, btn_plus, btn_divide, btn_multiplication;
    Button btn_dot, btn_equal;

    // Check https://github.com/Gperez88/CalculatorInputView
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_text = (TextView) findViewById(R.id.tv_text);

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


    }

    @Override
    public void onClick(View view) {
        String buttonText = ((Button) view).getText().toString();
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
                // if text already contains 0
                // than remove preceding 0 and display numbers accordingly
                if (buttonText.length() == 1 && buttonText.equalsIgnoreCase(getString(R.string.zero)))
                    tv_text.setText("");

                tv_text.append(buttonText);
                break;

            case R.id.btn_dot:
                // Check if text already contains decimal
                if (!tv_text.getText().toString().contains(getString(R.string.dot))) {
                    tv_text.append(buttonText);
                }
                // else do not add more decimal dots
                break;

            case R.id.btn_clear:
                tv_text.setText("");
                break;
        }
    }
}
