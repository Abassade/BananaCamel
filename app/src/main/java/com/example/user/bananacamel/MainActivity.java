package com.example.user.bananacamel;

import android.content.Context;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

     int all_banana;
     int distance_away;
     int no_camel;
     int eats_per_km;

    private TextView instruction, output;
    private EditText total_banana, distance, camel, eats;
    private Button calculate;
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instruction = findViewById(R.id.txt_instruction);
        output = findViewById(R.id.txt_output);
        total_banana = findViewById(R.id.ed_total_banana);
        distance = findViewById(R.id.ed_distance);
        camel = findViewById(R.id.ed_no_camel);
        eats = findViewById(R.id.ed_eats_per_km);
        calculate =findViewById(R.id.calculate);
        linearLayout = findViewById(R.id.parent);


        // To hide keyboard when click the container
        hideKeyboard();
        // call the calculateOutput method
        calculateOutput();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.reset, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.reset_menu){

            instruction.setText("Please fill in the spaces below");
            total_banana.setText("");
            distance.setText("");
            camel.setText("");
            eats.setText("");
            output.setText("");

        }
        return super.onOptionsItemSelected(item);
    }

    public void hideKeyboard(){

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager methodManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                assert methodManager != null;
                methodManager.hideSoftInputFromWindow(total_banana.getWindowToken(), 0);
                methodManager.hideSoftInputFromWindow(distance.getWindowToken(), 1);
                methodManager.hideSoftInputFromWindow(camel.getWindowToken(), 2);
                methodManager.hideSoftInputFromWindow(eats.getWindowToken(), 3);
            }
        });
    }

    public void calculateOutput() {




        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    all_banana = Integer.parseInt(total_banana.getText().toString().trim());
                    distance_away = Integer.parseInt(distance.getText().toString().trim());
                    no_camel = Integer.parseInt(camel.getText().toString().trim());
                    eats_per_km = Integer.parseInt(eats.getText().toString().trim());

                } catch (NumberFormatException e) {

                    e.printStackTrace();

                }
                // Handling nullPointer exception for the editTexts

                if(total_banana.getText().toString().trim().equals("") ||
                        distance.getText().toString().trim().equals("")||
                        camel.getText().toString().trim().equals("")||
                        eats.getText().toString().trim().equals("")){

                    Toast.makeText(getBaseContext(), R.string.toastemptyfield, Toast.LENGTH_SHORT).show();

                }else {

                    // Establishing the constraints

                    if (all_banana < 3000) {

                        Toast.makeText(getBaseContext(), R.string.toastlessbanana, Toast.LENGTH_SHORT).show();

                    } else if (distance_away < 1000 || distance_away > 10000) {

                        Toast.makeText(getBaseContext(), R.string.toastlessdistance, Toast.LENGTH_SHORT).show();

                    } else {
                        int sum = 0;
                        final int max_carry = 1000;

                        int trips = all_banana / max_carry;

                        for (int i = 1; i <= trips; i++) {

                            sum += (max_carry / (((2 * i) - 1) * no_camel * eats_per_km));

                        }
                        sum += (all_banana % max_carry) / (((2 * trips) + 1) * no_camel * eats_per_km);

                        int max_output = sum - distance_away;

                        if(max_output<0){

                            instruction.setText(R.string.negresult);
                            output.setText(R.string.nil);

                        }
                        else{

                            instruction.setText(R.string.posresult);

                            output.setText(String.valueOf(max_output));

                        }


                    }
                }

            }
        });

    }

}
