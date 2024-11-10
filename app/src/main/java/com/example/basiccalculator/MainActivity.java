package com.example.basiccalculator;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private String currentInput = "";
    private String operator = "";
    private double firstOperand = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);

        // Set up number button listeners
        setNumberButtonListeners();

        // Set up operator button listeners
        setOperatorButtonListeners();
    }

    private void setNumberButtonListeners() {
        int[] numberButtonIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnDecimal
        };

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                currentInput += b.getText().toString();
                display.setText(currentInput); // Update display to show current input
            }
        };

        for (int id : numberButtonIds) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setOperatorButtonListeners() {
        findViewById(R.id.btnAdd).setOnClickListener(new OperatorClickListener("+"));
        findViewById(R.id.btnSubtract).setOnClickListener(new OperatorClickListener("-"));
        findViewById(R.id.btnMultiply).setOnClickListener(new OperatorClickListener("*"));
        findViewById(R.id.btnDivide).setOnClickListener(new OperatorClickListener("/"));
        findViewById(R.id.btnEqual).setOnClickListener(new EqualClickListener());
        findViewById(R.id.btnClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentInput = "";
                operator = "";
                firstOperand = 0;
                display.setText("0");
            }
        });
    }

    private class OperatorClickListener implements View.OnClickListener {
        private String op;

        public OperatorClickListener(String op) {
            this.op = op;
        }

        @Override
        public void onClick(View v) {
            try {
                firstOperand = Double.parseDouble(currentInput);
                operator = op;
                currentInput += " " + operator + " "; // Add operator to currentInput string
                display.setText(currentInput); // Update display with the full expression
            } catch (NumberFormatException e) {
                display.setText("Error");
            }
        }
    }

    private class EqualClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            try {
                double secondOperand = Double.parseDouble(currentInput.split(" ")[2]); // Parse second operand
                double result = 0;
                switch (operator) {
                    case "+":
                        result = firstOperand + secondOperand;
                        break;
                    case "-":
                        result = firstOperand - secondOperand;
                        break;
                    case "*":
                        result = firstOperand * secondOperand;
                        break;
                    case "/":
                        if (secondOperand != 0) {
                            result = firstOperand / secondOperand;
                        } else {
                            display.setText("Error");
                            return;
                        }
                        break;
                }
                currentInput = String.valueOf(result);
                display.setText(currentInput); // Display the result
                operator = "";
                firstOperand = result; // Update firstOperand in case of further operations
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                display.setText("Error");
            }
        }
    }
}

