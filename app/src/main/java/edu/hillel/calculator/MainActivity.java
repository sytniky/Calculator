package edu.hillel.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView screen;
    private String expression = "";
    private String currnetOperator = "";
    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screen = (TextView) findViewById(R.id.tvScreen);

        findViewById(R.id.btn0).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
        findViewById(R.id.btnMultiply).setOnClickListener(this);
        findViewById(R.id.btnDivide).setOnClickListener(this);
        findViewById(R.id.btnMinus).setOnClickListener(this);
        findViewById(R.id.btnPlus).setOnClickListener(this);
        findViewById(R.id.btnClean).setOnClickListener(this);
        findViewById(R.id.btnEqual).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMultiply:
            case R.id.btnDivide:
            case R.id.btnMinus:
            case R.id.btnPlus:
                onClickOperator(v);
                break;
            case R.id.btnClean:
                onClickClean(v);
                break;
            case R.id.btnEqual:
                onClickEqual(v);
                break;
            default:
                onClickNumber(v);
        }
    }

    private void onClickNumber(View v) {
        Button btn = (Button) v;
        Log.d("MainActivity", "onClickNumber: " + btn.getText().toString());

        if (result != "") {
            clean();
        }

        expression += btn.getText();
        updateScreen();
    }

    private void onClickOperator(View v) {
        Button btn = (Button) v;
        Log.d("MainActivity", "onClickOperator: " + btn.getText().toString());

        if (result != "" || getResult()) {
            String tmpResult = result;
            clean();
            expression += tmpResult;
        }

        char lastChar = expression.charAt(expression.length()-1);
        if (isOperator(lastChar)) {
            expression = expression.replace(lastChar, btn.getText().charAt(0));
            Log.d("MainActivity", "onClickOperator: expression: " + expression);
        } else {
            expression += btn.getText();
        }

        currnetOperator = btn.getText().toString();
        updateScreen();
    }

    private void onClickClean(View v) {
        Button btn = (Button) v;
        Log.d("MainActivity", "onClickClean: " + btn.getText().toString());
        clean();
        updateScreen();
    }

    private boolean isOperator(char c) {
        switch (c) {
            case '+':
            case '-':
            case 'x':
            case '/':
                return true;
            default: return false;
        }
    }

    private void clean() {
        expression = "";
        currnetOperator = "";
        result = "";
    }

    private void onClickEqual(View v) {
        Button btn = (Button) v;
        Log.d("MainActivity", "onClickEqual: " + btn.getText().toString());
        if (!getResult()) return;
        expression += "\n" + result;
        updateScreen();
    }

    private void updateScreen() {
        screen.setText(expression);
    }

    private boolean getResult() {
        if (currnetOperator == "") return false;
        String[] operands = expression.split(Pattern.quote(currnetOperator));
        if (operands.length < 2) {
            return false;
        }

        try {
            result = calculate(
                    Double.valueOf(operands[0]),
                    Double.valueOf(operands[1]),
                    currnetOperator).toString();
        } catch (Exception e) {
            result = e.getMessage();
        }
        Log.d("MainActivity", "getResult: " + result);
        return true;
    }

    private Double calculate(double firstOperand, double secondOperand, String currnetOperator) {
        switch (currnetOperator) {
            case "+": return firstOperand + secondOperand;
            case "-": return firstOperand - secondOperand;
            case "x": return firstOperand * secondOperand;
            case "/": return firstOperand / secondOperand;
            default: return null;
        }
    }
}
