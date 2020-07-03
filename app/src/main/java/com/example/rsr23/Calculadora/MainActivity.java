package com.example.rsr23.Calculadora;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import androidx.appcompat.app.AlertDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Vibrator;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import static android.graphics.Color.TRANSPARENT;
import static androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM;


public class MainActivity extends AppCompatActivity {

    private EditText Result_Box;

    private TextView Equation_Box;

    private Animation Bounce_Animation;

    private double mValueOne , mValueTwo ;

    private DecimalFormat df = new DecimalFormat();

    private Vibrator vibe;

    private MySQLiteDB mySQLiteDB;

    private Snackbar snackbar;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /* Custom Action Bar */
        AutoWritingText tv = new AutoWritingText(getApplicationContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setCharacterDelay(150);
        tv.animateText(getResources().getString(R.string.app_name));
        tv.setTextSize(40);
        tv.setTextColor(Color.parseColor("#FFFFFF"));
        tv.setGravity(Gravity.CENTER);
        Typeface tf = Typeface.createFromAsset(getAssets(), "alex_brush.ttf");
        tv.setTypeface(tf);
        getSupportActionBar().setDisplayOptions(DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);


        /* SQLite database */
        mySQLiteDB = new MySQLiteDB(getApplicationContext());


        /* Text Resize (Start) */
        Equation_Box = findViewById(R.id.equation_etxt);
        Equation_Box.addTextChangedListener(new TextWatcher() {

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override public void afterTextChanged(Editable s) {

                if (s.length() >= 20) {
                    Equation_Box.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                }
                else if (s.length() < 20) {
                    Equation_Box.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
                }
            }
        });


        Result_Box = findViewById(R.id.result_etxt);
        Result_Box.addTextChangedListener(new TextWatcher() {

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override public void afterTextChanged(Editable s) {

                if (s.length() >= 20) {
                    Result_Box.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
                }
                else if (s.length() >= 15 && s.length() < 20) {
                    Result_Box.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
                }
                else if (s.length() >= 10 && s.length() < 15) {
                    Result_Box.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 35);
                }
                else if (s.length() < 10) {
                    Result_Box.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 40);
                }
            }
        });


        /* Button (0-9,dot) */
        final Button button_0 = findViewById(R.id.btn_0);
        final CardView button_0_view = findViewById(R.id.btn_0_view);
        button_0.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                Equation_Box.setText(Equation_Box.getText() + "0");

                if(!Result_Box.getText().toString().equals("0")){
                    Result_Box.setText(Result_Box.getText() + "0");
                }
                else{
                    Result_Box.setText("0");
                }

                Bounce_Animation();
                button_0_view.startAnimation(Bounce_Animation);

                Vibration();
            }
        });


        final Button button_1 = findViewById(R.id.btn_1);
        final CardView button_1_view = findViewById(R.id.btn_1_view);
        button_1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                Equation_Box.setText(Equation_Box.getText()+"1");

                if(!Result_Box.getText().toString().equals("0")){
                    Result_Box.setText(Result_Box.getText() + "1");
                }
                else {
                    Result_Box.setText("1");
                }

                Bounce_Animation();
                button_1_view.startAnimation(Bounce_Animation);

                Vibration();
            }
        });


        final Button button_2 = findViewById(R.id.btn_2);
        final CardView button_2_view = findViewById(R.id.btn_2_view);
        button_2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                Equation_Box.setText(Equation_Box.getText()+"2");

                if(!Result_Box.getText().toString().equals("0")){
                    Result_Box.setText(Result_Box.getText() + "2");
                }
                else {
                    Result_Box.setText("2");
                }

                Bounce_Animation();
                button_2_view.startAnimation(Bounce_Animation);

                Vibration();
            }
        });


        final Button button_3 = findViewById(R.id.btn_3);
        final CardView button_3_view = findViewById(R.id.btn_3_view);
        button_3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                Equation_Box.setText(Equation_Box.getText()+"3");

                if(!Result_Box.getText().toString().equals("0")){
                    Result_Box.setText(Result_Box.getText() + "3");
                }
                else {
                    Result_Box.setText("3");
                }

                Bounce_Animation();
                button_3_view.startAnimation(Bounce_Animation);

                Vibration();
            }
        });


        final Button button_4 = findViewById(R.id.btn_4);
        final CardView button_4_view = findViewById(R.id.btn_4_view);
        button_4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                Equation_Box.setText(Equation_Box.getText()+"4");

                if(!Result_Box.getText().toString().equals("0")){
                    Result_Box.setText(Result_Box.getText() + "4");
                }
                else {
                    Result_Box.setText("4");
                }

                Bounce_Animation();
                button_4_view.startAnimation(Bounce_Animation);

                Vibration();
            }
        });


        final Button button_5 = findViewById(R.id.btn_5);
        final CardView button_5_view = findViewById(R.id.btn_5_view);
        button_5.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                Equation_Box.setText(Equation_Box.getText()+"5");

                if(!Result_Box.getText().toString().equals("0")){
                    Result_Box.setText(Result_Box.getText() + "5");
                }
                else {
                    Result_Box.setText("5");
                }

                Bounce_Animation();
                button_5_view.startAnimation(Bounce_Animation);

                Vibration();
            }
        });


        final Button button_6 = findViewById(R.id.btn_6);
        final CardView button_6_view = findViewById(R.id.btn_6_view);
        button_6.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                Equation_Box.setText(Equation_Box.getText() + "6");

                if(!Result_Box.getText().toString().equals("0")){
                    Result_Box.setText(Result_Box.getText() + "6");
                }
                else {
                    Result_Box.setText("6");
                }

                Bounce_Animation();
                button_6_view.startAnimation(Bounce_Animation);

                Vibration();
            }
        });


        final Button button_7 = findViewById(R.id.btn_7);
        final CardView button_7_view = findViewById(R.id.btn_7_view);
        button_7.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                Equation_Box.setText(Equation_Box.getText()+"7");

                if(!Result_Box.getText().toString().equals("0")){
                    Result_Box.setText(Result_Box.getText() + "7");
                }
                else {
                    Result_Box.setText("7");
                }

                Bounce_Animation();
                button_7_view.startAnimation(Bounce_Animation);

                Vibration();
            }
        });


        final Button button_8 = findViewById(R.id.btn_8);
        final CardView button_8_view = findViewById(R.id.btn_8_view);
        button_8.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                Equation_Box.setText(Equation_Box.getText()+"8");

                if(!Result_Box.getText().toString().equals("0")){
                    Result_Box.setText(Result_Box.getText() + "8");
                }
                else {
                    Result_Box.setText("8");
                }
                Bounce_Animation();
                button_8_view.startAnimation(Bounce_Animation);

                Vibration();
            }
        });


        final Button button_9 = findViewById(R.id.btn_9);
        final CardView button_9_view = findViewById(R.id.btn_9_view);
        button_9.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                Equation_Box.setText(Equation_Box.getText()+"9");

                if(!Result_Box.getText().toString().equals("0")){
                    Result_Box.setText(Result_Box.getText() + "9");
                }
                else {
                    Result_Box.setText("9");
                }

                Bounce_Animation();
                button_9_view.startAnimation(Bounce_Animation);

                Vibration();
            }
        });


        final Button button_Dot = findViewById(R.id.btn_Dot);
        final CardView button_Dot_view = findViewById(R.id.btn_Dot_view);
        button_Dot.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                String getValue = Result_Box.getText().toString().trim();
                String getEquation = Equation_Box.getText().toString().trim();

                if(getValue.equals("0") || getEquation.isEmpty()){
                    Result_Box.setText("0.");
                    Equation_Box.setText("0.");
                }
                else if(getValue.isEmpty()){
                    Result_Box.setText("0.");
                    Equation_Box.setText(Equation_Box.getText() + "0.");
                }
                else if(getValue.contains(".")){
                    Result_Box.setText(Result_Box.getText().toString());
                }
                else{
                    Result_Box.setText(Result_Box.getText() + ".");
                    Equation_Box.setText(Equation_Box.getText() + ".");
                }

                Bounce_Animation();
                button_Dot_view.startAnimation(Bounce_Animation);

                Vibration();
            }
        });


        /* Math Function */
        final Button button_Add = findViewById(R.id.btn_Add);
        final CardView button_Add_view = findViewById(R.id.btn_Add_view);
        button_Add.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                String getValue = Result_Box.getText().toString().trim();
                String getEquation = Equation_Box.getText().toString().trim();

                Bounce_Animation();
                button_Add_view.startAnimation(Bounce_Animation);

                Vibration();

                if (getValue.isEmpty() || getEquation.isEmpty()) {
                    Custom_Toast();
                }
                else if(getValue.equals(getValue.substring(0,getValue.length()-1)+"-")){
                    Custom_Toast();
                }
                else {
                    calculation();
                    Equation_Box.setText(Equation_Box.getText() + " + ");
                    Result_Box.setText(null);
                }
            }
        });


        final Button button_Sub = findViewById(R.id.btn_Sub);
        final CardView button_Sub_view = findViewById(R.id.btn_Sub_view);
        button_Sub.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                String getValue = Result_Box.getText().toString().trim();
                String getEquation = Equation_Box.getText().toString().trim();

                Bounce_Animation();
                button_Sub_view.startAnimation(Bounce_Animation);

                Vibration();

                if (getValue.isEmpty() || getEquation.isEmpty()) {
                    Custom_Toast();
                }
                else if(getValue.equals(getValue.substring(0,getValue.length()-1)+"-")){
                    Custom_Toast();
                }
                else {
                    calculation();
                    Equation_Box.setText(Equation_Box.getText() + " - ");
                    Result_Box.setText(null);
                }
            }
        });


        final Button button_Mul = findViewById(R.id.btn_Mul);
        final CardView button_Mul_view = findViewById(R.id.btn_Mul_view);
        button_Mul.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                String getValue = Result_Box.getText().toString().trim();
                String getEquation = Equation_Box.getText().toString().trim();

                Bounce_Animation();
                button_Mul_view.startAnimation(Bounce_Animation);

                Vibration();

                if (getValue.isEmpty() || getEquation.isEmpty()) {
                    Custom_Toast();
                }
                else if(getValue.equals(getValue.substring(0,getValue.length()-1)+"-")){
                    Custom_Toast();
                }
                else {
                    calculation();
                    Equation_Box.setText(Equation_Box.getText() + " × ");
                    Result_Box.setText(null);
                }
            }
        });


        final Button button_Div = findViewById(R.id.btn_Div);
        final CardView button_Div_view = findViewById(R.id.btn_Div_view);
        button_Div.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                String getValue = Result_Box.getText().toString().trim();
                String getEquation = Equation_Box.getText().toString().trim();

                Bounce_Animation();
                button_Div_view.startAnimation(Bounce_Animation);

                Vibration();

                if (getValue.isEmpty() || getEquation.isEmpty()) {
                    Custom_Toast();
                }
                else if(getValue.equals(getValue.substring(0,getValue.length()-1)+"-")){
                    Custom_Toast();
                }
                else {
                    calculation();
                    Equation_Box.setText(Equation_Box.getText() + " ÷ ");
                    Result_Box.setText(null);
                }
            }
        });


        final Button button_Mod = findViewById(R.id.btn_Mod);
        final CardView button_Mod_view = findViewById(R.id.btn_Mod_view);
        button_Mod.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                String getValue = Result_Box.getText().toString().trim();
                String getEquation = Equation_Box.getText().toString().trim();

                Bounce_Animation();
                button_Mod_view.startAnimation(Bounce_Animation);

                Vibration();

                if (getValue.isEmpty() || getEquation.isEmpty()) {
                    Custom_Toast();
                }
                else if(getValue.equals(getValue.substring(0,getValue.length()-1)+"-")){
                    Custom_Toast();
                }
                else {
                    calculation();
                    Equation_Box.setText(Equation_Box.getText() + " mod ");
                    Result_Box.setText(null);
                }
            }
        });


        final Button button_Factorial = findViewById(R.id.btn_Factorial);
        final CardView button_Factorial_view = findViewById(R.id.btn_Factorial_view);
        button_Factorial.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                String getValue = Result_Box.getText().toString().trim();
                String getEquation = Equation_Box.getText().toString().trim();

                Bounce_Animation();
                button_Factorial_view.startAnimation(Bounce_Animation);

                Vibration();

                if(getValue.equals("0")){
                    Equation_Box.setText("0!");
                }
                else if(getValue.equals("-")){
                    Custom_Toast();
                }
                else if (getValue.equals("Infinity") || getValue.equals("NaN") || getValue.equals("∞") || getValue.isEmpty()) {
                    Equation_Box.setText(null);
                    Result_Box.setText("0");
                }
                else if(getEquation.contains(" ")){
                    Equation_Box.setText(getEquation+"!");
                }
                else {
                    mValueOne = Double.parseDouble(getValue.replaceAll("[,]", "") + "");

                    if(mValueOne>=0 && mValueOne<=170){

                        double factorial=1;

                        for(double i = 1 ; i<=mValueOne ; i++ ){
                            factorial = factorial*i;
                        }

                        String calculation = factorial +"";
                        if(calculation.length()<=15){
                            Equation_Box.setText(df.format(mValueOne)+"!");
                            Result_Box.setText(df.format(factorial)+"");
                            Save_Result(df.format(factorial)+"");
                            mySQLiteDB.insertData(df.format(mValueOne)+"!"+" = "+df.format(factorial)+"");
                        }
                        else{
                            Equation_Box.setText(df.format(mValueOne)+"!");
                            Result_Box.setText(calculation);
                            Save_Result(calculation);
                            mySQLiteDB.insertData(df.format(mValueOne)+"!"+" = "+calculation);
                        }
                    }
                    else{
                        Equation_Box.setText(df.format(mValueOne)+"!");
                        Result_Box.setText("∞");
                        String calculation = "∞";
                        Save_Result(calculation);
                        mySQLiteDB.insertData(df.format(mValueOne)+"!"+" = "+"∞");
                    }
                }
            }
        });


        final Button button_Percent = findViewById(R.id.btn_percent);
        final CardView button_Percent_view = findViewById(R.id.btn_percent_view);
        button_Percent.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                String getValue = Result_Box.getText().toString().trim();
                String getEquation = Equation_Box.getText().toString().trim();

                Bounce_Animation();
                button_Percent_view.startAnimation(Bounce_Animation);

                Vibration();

                if (getValue.equals("Infinity") || getValue.equals("NaN") || getValue.equals("∞") || getValue.isEmpty()) {
                    Equation_Box.setText(null);
                    Result_Box.setText("0");
                }
                else if(getValue.equals("-")){
                    Custom_Toast();
                }
                else if(getEquation.contains(" ")){
                    Equation_Box.setText(getEquation+"%");
                }
                else {
                    mValueOne = Double.parseDouble(getValue.replaceAll("[,]", "") + "");
                    String calculation = df.format((mValueOne / 100) )+ "";

                    if(calculation.length()<=15){
                        Equation_Box.setText(df.format(mValueOne)+"%");
                        Result_Box.setText(calculation);
                        Save_Result(calculation);
                        mySQLiteDB.insertData(df.format(mValueOne)+"%"+" = "+calculation);
                    }
                    else{
                        Equation_Box.setText(mValueOne+"%");
                        Result_Box.setText((mValueOne / 100) +"");
                        Save_Result((mValueOne / 100) +"");
                        mySQLiteDB.insertData(df.format(mValueOne)+"%"+" = "+(mValueOne / 100) +"");
                    }
                }
            }
        });


        final Button button_Minus = findViewById(R.id.btn_Minus);
        final CardView button_Minus_view = findViewById(R.id.btn_Minus_view);
        button_Minus.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                String getValue = Result_Box.getText().toString().trim();
                String getEquation = Equation_Box.getText().toString().trim();

                Bounce_Animation();
                button_Minus_view.startAnimation(Bounce_Animation);

                Vibration();

                if(getValue.equals("0") && getEquation.isEmpty()){
                    Result_Box.setText("-");
                    Equation_Box.setText("-");
                }
                else if(getValue.equals("0") && !getEquation.isEmpty()){
                    Result_Box.setText("-");
                    Equation_Box.setText(Equation_Box.getText() + "-");
                }
                else if(getValue.isEmpty()){
                    Result_Box.setText("-");
                    Equation_Box.setText(Equation_Box.getText() + "-");
                }
                else if(getValue.contains("-")){
                    Result_Box.setText(Result_Box.getText().toString());
                }
                else if(getEquation.matches("([0-9.,]+|[0-9.]+E[1-9]+|Ans)") || getValue.matches("[0-9.]+E[0-9]+")){

                    String calculation = df.format(Double.parseDouble(getValue.replaceAll(",",""))*-1)+"";

                    if(calculation.length()<=15){
                        Equation_Box.setText(calculation);
                        Result_Box.setText(calculation);
                    }
                    else{
                        Equation_Box.setText(Double.parseDouble(getValue.replaceAll(",",""))*-1+"");
                        Result_Box.setText(Double.parseDouble(getValue.replaceAll(",",""))*-1+"");
                    }
                }
            }
        });


        /* Clear & Erase: */
        final Button button_Erase = findViewById(R.id.btn_erase);
        final CardView button_Erase_view = findViewById(R.id.btn_erase_view);
        button_Erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getValue = Result_Box.getText().toString().trim();
                String getEquation = Equation_Box.getText().toString().trim();

                if(getEquation.isEmpty()){
                    Equation_Box.setText(null);
                }
                else {
                    int equation_length = Equation_Box.length();

                    if (equation_length > 0) {
                        Equation_Box.setText(getEquation.substring(0, getEquation.length() - 1));
                    }

                    if(equation_length > 2 && getEquation.substring(getEquation.length()-2).matches(",[0-9]")){
                        Equation_Box.setText(getEquation.substring(0, getEquation.length() - 2));
                    }

                    String substring = getEquation.substring(getEquation.length() - 3);
                    if(equation_length > 3 && (substring.equals("Ans") || substring.equals("mod"))){
                        Equation_Box.setText(getEquation.substring(0, getEquation.length() - 3));
                    }

                    if (equation_length < 2 ) {
                        Equation_Box.setText(null);
                    }
                }

                if(getValue.equals("0")){

                    Result_Box.setText("0");
                }

                else {
                    int result_length = getValue.length();

                    if (result_length > 0) {
                        Result_Box.setText(getValue.substring(0, getValue.length() - 1));
                    }

                    if (getValue.equals("Infinity") || getValue.equals("NaN") || getValue.equals("∞")) {
                        Result_Box.setText("0");
                        Equation_Box.setText(null);
                    }
                    if(result_length > 2 && getValue.substring(getValue.length()-2).matches(",[0-9]")){
                        Result_Box.setText(getValue.substring(0, getValue.length() - 2));
                    }
                    if (result_length < 2) {
                        Result_Box.setText("0");
                    }
                }

                Bounce_Animation();
                button_Erase_view.startAnimation(Bounce_Animation);

                Vibration();
            }
        });


        button_Erase.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Result_Box.setText("0");
                Equation_Box.setText(null);

                Vibration();

                return true;
            }
        });


        final Button button_Clear = findViewById(R.id.btn_clear);
        final CardView button_Clear_view = findViewById(R.id.btn_clear_view);
        button_Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Result_Box.setText("0");
                Equation_Box.setText(null);

                Bounce_Animation();
                button_Clear_view.startAnimation(Bounce_Animation);

                Vibration();
            }
        });


        /* Store Result */
        final Button button_Ans = findViewById(R.id.btn_Ans);
        final CardView button_Ans_view = findViewById(R.id.btn_Ans_view);
        button_Ans.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                String getEquation = Equation_Box.getText().toString().trim();

                Bounce_Animation();
                button_Ans_view.startAnimation(Bounce_Animation);

                Vibration();

                try{
                    String result = df.format(Double.parseDouble(Load_Result().replaceAll(",","")+"")) + "";

                    if(result.length()<=15){
                        Result_Box.setText(result);
                    }
                    else{
                        Result_Box.setText(Double.parseDouble(Load_Result().replaceAll(",",""))+ "");
                    }

                    if(getEquation.substring(0,getEquation.length()).matches("[0-9.,]+")){
                        Equation_Box.setText("Ans");
                    }
                    else {
                        Equation_Box.setText(Equation_Box.getText()+"Ans");
                    }
                }
                catch (Exception e){
                    Log.d("TAG",e.getMessage());
                    Custom_SnackBar("Please! Try Again");
                }
            }
        });


        /* Equal Function */
        final Button button_Equal = findViewById(R.id.btn_Equal);
        final CardView button_Equal_view = findViewById(R.id.btn_Equal_view);
        button_Equal.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                button_Equal_view.startAnimation(Bounce_Animation);

                Vibration();

                if (Result_Box.getText().toString().isEmpty() || Equation_Box.getText().toString().isEmpty()) {
                    Custom_Toast();
                }
                else {
                    calculation();
                }
            }
        });


        /* Footer */
        Footer_Item();
    }


    /* Calculation */
    public void calculation(){

        try{
            String getValue = Result_Box.getText().toString().trim();

            switch (getValue) {

                case "0":
                    Result_Box.setText("0");
                    break;


                default:

                    if (Equation_Box.getText().toString().contains(" + ")) {
                        Addition();
                    }

                    if (Equation_Box.getText().toString().contains(" - ")) {
                        Subtraction();
                    }

                    if (Equation_Box.getText().toString().contains(" × ")) {
                        Multiplication();
                    }

                    if (Equation_Box.getText().toString().contains(" ÷ ")) {
                        Division();
                    }

                    if (Equation_Box.getText().toString().contains(" mod ")) {
                        Module();
                    }
                    break;
            }
        }

        catch (Exception e){
            Log.d("TAG",e.getMessage());
            Custom_SnackBar("Please! Try Again");
        }
    }

    @SuppressLint("SetTextI18n")
    private void Addition(){

        String Ans_value = Load_Result()+"";
        String getText = Equation_Box.getText().toString().trim();
        String[] value = getText.split(" \\+ ");

        /* Value one */
        if(value[0].contains("!")){

            String[] factValue = value[0].split("!");

            double mValue = Double.parseDouble(factValue[0].replaceAll("[,]", "").trim() + "");

            double factorial=1;

            for(double i = 1 ; i<=mValue ; i++ ){
                factorial = factorial*i;
            }
            mValueOne = Double.parseDouble((factorial+"").trim());
        }
        else if(value[0].contains("%")){
            String[] perValue = value[0].split("%");
            double mValue = Double.parseDouble(perValue[0].replaceAll("[,]", "").trim() + "");
            mValueOne = Double.parseDouble((mValue/100+"").trim());
        }
        else if(value[0].contains("Ans")){
            mValueOne = Double.parseDouble((Load_Result().replaceAll(",","")+"").trim());
        }
        else {
            mValueOne = Double.parseDouble(value[0].replaceAll("[,]", "").trim() + "");
        }


        /* Value Two */
        if(value[1].contains("!")){
            String[] factValue = value[1].split("!");

            double mValue = Double.parseDouble(factValue[0].replaceAll("[,]", "").trim() + "");

            double factorial=1;

            for(double i = 1 ; i<=mValue ; i++ ){
                factorial = factorial*i;
            }
            mValueTwo = Double.parseDouble((factorial+"").trim());
        }
        else if(value[1].contains("%")){
            String[] perValue = value[1].split("%");
            double mValue = Double.parseDouble(perValue[0].replaceAll("[,]", "").trim() + "");
            mValueTwo = Double.parseDouble((mValue/100+"").trim());
        }
        else if(value[1].contains("Ans")){
            mValueTwo = Double.parseDouble((Load_Result().replaceAll(",","")+"").trim());
        }
        else {
            mValueTwo = Double.parseDouble(value[1].replaceAll("[,]", "").trim() + "");
        }

        double doubleCalculation = Double.parseDouble(mValueOne + mValueTwo +"");
        String calculation = df.format(doubleCalculation)+"";

        if(calculation.length()<=15){
            Equation_Box.setText(calculation);
            Save_Result(calculation);
            Result_Box.setText(calculation);
            mySQLiteDB.insertData(value[0].replace("Ans",Ans_value)+" + "+value[1].replace("Ans",Ans_value)+" = "+calculation);
        }
        else{
            Equation_Box.setText(doubleCalculation+"");
            Save_Result(doubleCalculation+"");
            Result_Box.setText(doubleCalculation+"");
            mySQLiteDB.insertData(value[0].replace("Ans",Ans_value)+" + "+value[1].replace("Ans",Ans_value)+" = "+doubleCalculation+"");
        }
    }

    @SuppressLint("SetTextI18n")
    private void Subtraction(){

        String Ans_value = Load_Result()+"";
        String getText = Equation_Box.getText().toString().trim();
        String[] value = getText.split(" - ");

        /* Value one */
        if(value[0].contains("!")){

            String[] factValue = value[0].split("!");

            double mValue = Double.parseDouble(factValue[0].replaceAll("[,]", "").trim() + "");

            double factorial=1;

            for(double i = 1 ; i<=mValue ; i++ ){
                factorial = factorial*i;
            }
            mValueOne = Double.parseDouble((factorial+"").trim());
        }
        else if(value[0].contains("%")){

            String[] perValue = value[0].split("%");
            double mValue = Double.parseDouble(perValue[0].replaceAll("[,]", "").trim() + "");
            mValueOne = Double.parseDouble((mValue/100+"").trim());

        }

        else if(value[0].contains("Ans")){
            mValueOne = Double.parseDouble((Load_Result().replaceAll(",","")+"").trim());
        }

        else {
            mValueOne = Double.parseDouble(value[0].replaceAll("[,]", "").trim() + "");
        }


        /* Value Two */
        if(value[1].contains("!")){
            String[] factValue = value[1].split("!");

            double mValue = Double.parseDouble(factValue[0].replaceAll("[,]", "").trim() + "");

            double factorial=1;

            for(double i = 1 ; i<=mValue ; i++ ){
                factorial = factorial*i;
            }
            mValueTwo = Double.parseDouble((factorial+"").trim());
        }

        else if(value[1].contains("%")){
            String[] perValue = value[1].split("%");
            double mValue = Double.parseDouble(perValue[0].replaceAll("[,]", "").trim() + "");
            mValueTwo = Double.parseDouble((mValue/100+"").trim());
        }

        else if(value[1].contains("Ans")){
            mValueTwo = Double.parseDouble((Load_Result().replaceAll(",","")+"").trim());
        }

        else {
            mValueTwo = Double.parseDouble(value[1].replaceAll("[,]", "").trim() + "");
        }

        if(Equation_Box.getText().toString().contains("!")){

            String getFactValue = Equation_Box.getText().toString().trim();
            String[] factValue = getFactValue.split("!");

            double mValue = Double.parseDouble(factValue[0].replaceAll("[,]", "").trim() + "");

            double factorial=1;

            for(double i = 1 ; i<=mValue ; i++ ){
                factorial = factorial*i;
                mValueOne = Double.parseDouble((factorial+"").replaceAll("!","").trim());
            }
        }

        double doubleCalculation = Double.parseDouble(mValueOne - mValueTwo +"");

        String calculation = df.format(doubleCalculation)+"";
        if(calculation.length()<=15){
            Equation_Box.setText(calculation);
            Save_Result(calculation);
            Result_Box.setText(calculation);
            mySQLiteDB.insertData(value[0].replace("Ans",Ans_value)+" - "+value[1].replace("Ans",Ans_value)+" = "+calculation);
        }
        else{
            Equation_Box.setText(doubleCalculation+"");
            Save_Result(doubleCalculation+"");
            Result_Box.setText(doubleCalculation+"");
            mySQLiteDB.insertData(value[0].replace("Ans",Ans_value)+" - "+value[1].replace("Ans",Ans_value)+" = "+doubleCalculation+"");
        }
    }

    @SuppressLint("SetTextI18n")
    private void Multiplication(){

        String Ans_value = Load_Result()+"";
        String getText = Equation_Box.getText().toString().trim();
        String[] value = getText.split(" × ");

        /* Value one */
        if(value[0].contains("!")){

            String[] factValue = value[0].split("!");

            double mValue = Double.parseDouble(factValue[0].replaceAll("[,]", "").trim() + "");

            double factorial=1;

            for(double i = 1 ; i<=mValue ; i++ ){
                factorial = factorial*i;
            }
            mValueOne = Double.parseDouble((factorial+"").trim());
        }
        else if(value[0].contains("%")){

            String[] perValue = value[0].split("%");
            double mValue = Double.parseDouble(perValue[0].replaceAll("[,]", "").trim() + "");
            mValueOne = Double.parseDouble((mValue/100+"").trim());

        }

        else if(value[0].contains("Ans")){
            mValueOne = Double.parseDouble((Load_Result().replaceAll(",","")+"").trim());
        }

        else {
            mValueOne = Double.parseDouble(value[0].replaceAll("[,]", "").trim() + "");
        }


        /* Value Two */
        if(value[1].contains("!")){
            String[] factValue = value[1].split("!");

            double mValue = Double.parseDouble(factValue[0].replaceAll("[,]", "").trim() + "");

            double factorial=1;

            for(double i = 1 ; i<=mValue ; i++ ){
                factorial = factorial*i;
            }
            mValueTwo = Double.parseDouble((factorial+"").trim());
        }

        else if(value[1].contains("%")){
            String[] perValue = value[1].split("%");
            double mValue = Double.parseDouble(perValue[0].replaceAll("[,]", "").trim() + "");
            mValueTwo = Double.parseDouble((mValue/100+"").trim());
        }

        else if(value[1].contains("Ans")){
            mValueTwo = Double.parseDouble((Load_Result().replaceAll(",","")+"").trim());
        }

        else {
            mValueTwo = Double.parseDouble(value[1].replaceAll("[,]", "").trim() + "");
        }

        double doubleCalculation = Double.parseDouble(mValueOne * mValueTwo +"");

        String calculation = df.format(doubleCalculation)+"";
        if(calculation.length()<=15){
            Equation_Box.setText(calculation);
            Save_Result(calculation);
            Result_Box.setText(calculation);
            mySQLiteDB.insertData(value[0].replace("Ans",Ans_value)+" × "+value[1].replace("Ans",Ans_value)+" = "+calculation);
        }
        else{
            Equation_Box.setText(doubleCalculation+"");
            Save_Result(doubleCalculation+"");
            Result_Box.setText(doubleCalculation+"");
            mySQLiteDB.insertData(value[0].replace("Ans",Ans_value)+" × "+value[1].replace("Ans",Ans_value)+" = "+doubleCalculation+"");
        }
    }

    @SuppressLint("SetTextI18n")
    private void Division(){

        String Ans_value = Load_Result()+"";
        String getText = Equation_Box.getText().toString().trim();
        String[] value = getText.split(" ÷ ");

        /* Value one */
        if(value[0].contains("!")){

            String[] factValue = value[0].split("!");

            double mValue = Double.parseDouble(factValue[0].replaceAll("[,]", "").trim() + "");

            double factorial=1;

            for(double i = 1 ; i<=mValue ; i++ ){
                factorial = factorial*i;
            }
            mValueOne = Double.parseDouble((factorial+"").trim());
        }
        else if(value[0].contains("%")){

            String[] perValue = value[0].split("%");
            double mValue = Double.parseDouble(perValue[0].replaceAll("[,]", "").trim() + "");
            mValueOne = Double.parseDouble((mValue/100+"").trim());

        }

        else if(value[0].contains("Ans")){
            mValueOne = Double.parseDouble((Load_Result().replaceAll(",","")+"").trim());
        }

        else {
            mValueOne = Double.parseDouble(value[0].replaceAll("[,]", "").trim() + "");
        }


        /* Value Two */
        if(value[1].contains("!")){
            String[] factValue = value[1].split("!");

            double mValue = Double.parseDouble(factValue[0].replaceAll("[,]", "").trim() + "");

            double factorial=1;

            for(double i = 1 ; i<=mValue ; i++ ){
                factorial = factorial*i;
            }
            mValueTwo = Double.parseDouble((factorial+"").trim());
        }

        else if(value[1].contains("%")){
            String[] perValue = value[1].split("%");
            double mValue = Double.parseDouble(perValue[0].replaceAll("[,]", "").trim() + "");
            mValueTwo = Double.parseDouble((mValue/100+"").trim());
        }

        else if(value[1].contains("Ans")){
            mValueTwo = Double.parseDouble((Load_Result().replaceAll(",","")+"").trim());
        }

        else {
            mValueTwo = Double.parseDouble(value[1].replaceAll("[,]", "").trim() + "");
        }

        double doubleCalculation = Double.parseDouble(mValueOne / mValueTwo +"");

        String calculation = df.format(doubleCalculation)+"";

        if(mValueOne==0){
            Equation_Box.setText("∞");
            Save_Result("∞");
            Result_Box.setText("∞");
            mySQLiteDB.insertData(value[0].replace("Ans",Ans_value)+" ÷ "+value[1].replace("Ans",Ans_value)+" = ∞");
        }
        else{
            if(calculation.length()<=15){
                Equation_Box.setText(calculation);
                Save_Result(calculation);
                Result_Box.setText(calculation);
                mySQLiteDB.insertData(value[0].replace("Ans",Ans_value)+" ÷ "+value[1].replace("Ans",Ans_value)+" = "+calculation);
            }
            else{
                Equation_Box.setText(doubleCalculation+"");
                Save_Result(doubleCalculation+"");
                Result_Box.setText(doubleCalculation+"");
                mySQLiteDB.insertData(value[0].replace("Ans",Ans_value)+" ÷ "+value[1].replace("Ans",Ans_value)+" = "+doubleCalculation+"");
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void Module(){

        String Ans_value = Load_Result()+"";
        String getText = Equation_Box.getText().toString().trim();
        String[] value = getText.split(" mod ");

        /* Value one */
        if(value[0].contains("!")){

            String[] factValue = value[0].split("!");

            double mValue = Double.parseDouble(factValue[0].replaceAll("[,]", "").trim() + "");

            double factorial=1;

            for(double i = 1 ; i<=mValue ; i++ ){
                factorial = factorial*i;
            }
            mValueOne = Double.parseDouble((factorial+"").trim());
        }
        else if(value[0].contains("%")){

            String[] perValue = value[0].split("%");
            double mValue = Double.parseDouble(perValue[0].replaceAll("[,]", "").trim() + "");
            mValueOne = Double.parseDouble((mValue/100+"").trim());

        }

        else if(value[0].contains("Ans")){
            mValueOne = Double.parseDouble((Load_Result().replaceAll(",","")+"").trim());
        }

        else {
            mValueOne = Double.parseDouble(value[0].replaceAll("[,]", "").trim() + "");
        }


        /* Value Two */
        if(value[1].contains("!")){
            String[] factValue = value[1].split("!");

            double mValue = Double.parseDouble(factValue[0].replaceAll("[,]", "").trim() + "");

            double factorial=1;

            for(double i = 1 ; i<=mValue ; i++ ){
                factorial = factorial*i;
            }
            mValueTwo = Double.parseDouble((factorial+"").trim());
        }

        else if(value[1].contains("%")){
            String[] perValue = value[1].split("%");
            double mValue = Double.parseDouble(perValue[0].replaceAll("[,]", "").trim() + "");
            mValueTwo = Double.parseDouble((mValue/100+"").trim());
        }

        else if(value[1].contains("Ans")){
            mValueTwo = Double.parseDouble((Load_Result().replaceAll(",","")+"").trim());
        }

        else {
            mValueTwo = Double.parseDouble(value[1].replaceAll("[,]", "").trim() + "");
        }

        double doubleCalculation = Double.parseDouble(mValueOne % mValueTwo +"");

        String calculation = df.format(doubleCalculation)+"";
        if(calculation.length()<=15){
            Equation_Box.setText(calculation);
            Save_Result(calculation);
            Result_Box.setText(calculation);
            mySQLiteDB.insertData(value[0].replace("Ans",Ans_value)+" mod "+value[1].replace("Ans",Ans_value)+" = "+calculation);
        }
        else{
            Equation_Box.setText(doubleCalculation+"");
            Save_Result(doubleCalculation+"");
            Result_Box.setText(doubleCalculation+"");
            mySQLiteDB.insertData(value[0].replace("Ans",Ans_value)+" mod "+value[1].replace("Ans",Ans_value)+" = "+doubleCalculation+"");
        }
    }

    private void Save_Result(String calculation) {
        getApplicationContext().getSharedPreferences("storeResult", Context.MODE_PRIVATE).edit().putString("resultKey",calculation).apply();
    }

    private String Load_Result() {
        return getApplicationContext().getSharedPreferences("storeResult", Context.MODE_PRIVATE).getString("resultKey","0");
    }
    /* End (Calculation) */



    /* Footer */
    private void Footer_Item(){

        final LinearLayout bottomSheetLayout = findViewById(R.id.bottomSheet_id);
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);

        final Switch vibrate_Switch = findViewById(R.id.drawer_Vibrate_switch_id);
        final CardView vibrate_Btn = findViewById(R.id.drawer_Vibrate_id);
        final TextView history_Btn = findViewById(R.id.drawer_history_id);
        final TextView feedback_Btn = findViewById(R.id.drawer_feedback_id);
        final TextView share_Btn = findViewById(R.id.drawer_Share_id);

        bottomSheetLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Vibration();

                if (bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });


        vibrate_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Vibration();

                if(vibrate_Switch.isChecked()){
                    vibrate_Switch.setChecked(false);
                }
                else {
                    vibrate_Switch.setChecked(true);
                }
            }
        });


        if(loadVibrationMood()){
            vibrate_Switch.setChecked(true);
        }

        vibrate_Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    setVibrationMood(true);
                    vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    if (vibe != null) vibe.vibrate(50);
                }
                else {
                    setVibrationMood(false);
                    vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    if (vibe != null) vibe.cancel();
                }
            }
        });


        history_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                Vibration();

                History();
            }
        });


        feedback_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                Vibration();

                if(isConnected()){
                    Feedback();
                }
                else {
                    Custom_SnackBar("Please! Check your internet connection");
                }
            }
        });


        share_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                Vibration();

                if(isConnected()){
                    Share();
                }
                else {
                    Custom_SnackBar("Please! Check your internet connection");
                }
            }
        });
    }

    private void History(){

        View history_View = View.inflate(getApplicationContext(),R.layout.layout_history, null);

        final TextView title = history_View.findViewById(R.id.history_alertTitle_id);
        final EditText history_Etxt = history_View.findViewById(R.id.history_etxt_id);

        final Button history_clear_btn = history_View.findViewById(R.id.clear_history_btn_id);
        final Button history_cancel_btn = history_View.findViewById(R.id.cancel_history_btn_id);


        /* Font Set: */
        Typeface tf = Typeface.createFromAsset(getAssets(), "pf_regular.ttf");
        title.setTypeface(tf);
        history_Etxt.setTypeface(tf);
        history_clear_btn.setTypeface(tf);
        history_cancel_btn.setTypeface(tf);


        /* History using Alert Box: (custom) */
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setView(history_View);

        alertDialogBuilder.setCancelable(false);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().getDecorView().setBackgroundColor(TRANSPARENT);
        alertDialog.show();


        /* Display data */
        Cursor cursor = mySQLiteDB.displayData();

        StringBuilder stringBuilder = new StringBuilder();

        while (cursor.moveToNext()) {
            stringBuilder.append(getResources().getString(R.string.Point)).append(cursor.getString(1)).append("\n\n");
        }

        history_Etxt.setText(stringBuilder.toString());


        history_clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                history_clear_btn.startAnimation(Bounce_Animation);

                Vibration();

                history_Etxt.setText(null);

                mySQLiteDB.deleteAll();
            }
        });

        history_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                history_cancel_btn.startAnimation(Bounce_Animation);

                Vibration();

                alertDialog.dismiss();
            }
        });
    }

    private void Feedback(){

        View feedback_View = View.inflate(getApplicationContext(),R.layout.layout_feedback, null);

        final TextView title = feedback_View.findViewById(R.id.feedback_alertTitle_id);
        final EditText feedback_Etxt_Email = feedback_View.findViewById(R.id.feedback_etxt_Email_id);
        final EditText feedback_Etxt_Feedback = feedback_View.findViewById(R.id.feedback_etxt_Feedback_id);

        final Button feedback_send_btn = feedback_View.findViewById(R.id.send_feedback_btn_id);
        final Button feedback_clear_btn = feedback_View.findViewById(R.id.clear_feedback_btn_id);
        final Button feedback_cancel_btn = feedback_View.findViewById(R.id.cancel_feedback_btn_id);

        final String emailPattern = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{2,6}))?$";

        /* Font Set: */
        Typeface tf = Typeface.createFromAsset(getAssets(), "pf_regular.ttf");
        title.setTypeface(tf);
        feedback_Etxt_Email.setTypeface(tf);
        feedback_Etxt_Feedback.setTypeface(tf);
        feedback_send_btn.setTypeface(tf);
        feedback_clear_btn.setTypeface(tf);
        feedback_cancel_btn.setTypeface(tf);


        /* feedback using Alert Box: (custom) */
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setView(feedback_View);

        alertDialogBuilder.setCancelable(false);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().getDecorView().setBackgroundColor(TRANSPARENT);
        alertDialog.show();


        /* Validation */
        feedback_Etxt_Email.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().isEmpty() && !s.toString().matches(emailPattern)) {
                    feedback_Etxt_Email.setError("Please! Enter a valid Email\nEx: abc@Email.com");
                    requestFocus(feedback_Etxt_Email);
                }
            }
        });


        /* Send Feedback */
        feedback_send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) imm.hideSoftInputFromWindow(alertDialog.getWindow().getDecorView().getWindowToken(), 0);

                Bounce_Animation();
                feedback_send_btn.startAnimation(Bounce_Animation);

                Vibration();

                if(isConnected()){

                    final String feedback_email = feedback_Etxt_Email.getText().toString();
                    final String feedback_feedback = feedback_Etxt_Feedback.getText().toString();

                    if (feedback_email.isEmpty()) {
                        feedback_Etxt_Email.setError("Please! Enter Your Email");
                        requestFocus(feedback_Etxt_Email);
                    }
                    else if (!feedback_email.matches(emailPattern)) {
                        feedback_Etxt_Email.setError("Please! Enter a valid Email\nEx: abc@Email.com");
                        requestFocus(feedback_Etxt_Email);
                    }
                    else if (feedback_feedback.isEmpty()) {
                        feedback_Etxt_Feedback.setError("Please! Write Something");
                        requestFocus(feedback_Etxt_Feedback);
                    }
                    else {

                        if (isConnected()) {

                            try {
                                String recipient = "pro.event38@gmail.com";
                                Intent intent_mail = new Intent(Intent.ACTION_SEND);
                                intent_mail.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient});
                                intent_mail.putExtra(Intent.EXTRA_SUBJECT, feedback_email);
                                intent_mail.putExtra(Intent.EXTRA_TEXT, feedback_feedback);
                                intent_mail.setType("message/rfc822");
                                startActivity(Intent.createChooser(intent_mail, "Send Feedback"));

                                alertDialog.dismiss();
                                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }
                            catch (Exception e) {
                                Custom_SnackBar("Sorry! Something Wrong");
                            }
                        }
                        else {
                            Custom_SnackBar("Please! Check your internet connection");
                            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                        }
                    }
                }

                else{
                    Custom_SnackBar("Please! Check your internet connection");
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    alertDialog.dismiss();
                }
            }
        });

        feedback_clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) imm.hideSoftInputFromWindow(alertDialog.getWindow().getDecorView().getWindowToken(), 0);

                Bounce_Animation();
                feedback_clear_btn.startAnimation(Bounce_Animation);

                Vibration();

                if(isConnected()){

                    feedback_Etxt_Email.setText(null);
                    feedback_Etxt_Feedback.setText(null);
                }

                else{
                    Custom_SnackBar("Please! Check your internet connection");
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    alertDialog.dismiss();
                }
            }
        });

        feedback_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                feedback_cancel_btn.startAnimation(Bounce_Animation);

                Vibration();

                alertDialog.dismiss();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            }
        });
    }

    private void Share(){

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        String subject = "Calculadora";
        String body = "\nLet me recommend you this application\n\n" + "Get this Application:  " + Uri.parse("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());

        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(Intent.createChooser(intent, "Share With"));
    }

    private void Share_APK(){

        try {
            PackageManager pm = getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(getPackageName(), 0);
            File srcFile = new File(ai.publicSourceDir);
            Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.setType("*/*");
            share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(srcFile));
            startActivity(Intent.createChooser(share, "Calculadora"));
        } catch (Exception e) {
            Log.d("TAG", e.getMessage());
        }
    }

    public boolean isConnected(){

        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();

            if (info != null){

                for (NetworkInfo anInfo : info){

                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {

                        /* Check if network connected but no internet */
                        Runtime runtime = Runtime.getRuntime();
                        try {
                            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
                            int     exitValue = ipProcess.waitFor();
                            return (exitValue == 0);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        return false;
                    }
                }
            }

        }
        return false;
    }

    private void requestFocus(View view){
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    /* End (Footer) */



    /* Custom Toast */
    public  void Custom_Toast(){

        Toast custom_toast = Toast.makeText(getApplicationContext(), "Please! input a value", Toast.LENGTH_SHORT);
        custom_toast.setGravity(Gravity.BOTTOM,0,50);

        View toast_view= custom_toast.getView();
        toast_view.setPadding(20,15,20,15);
        toast_view.setBackgroundResource(R.drawable.bg_equal_button);

        TextView tv_view= toast_view.findViewById(android.R.id.message);
        tv_view.setTextColor(Color.WHITE);
        tv_view.setGravity(Gravity.CENTER);

        custom_toast.show();
    }
    /* End (Toast) */



    /* Custom SnackBar */
    public void Custom_SnackBar(final String SnackBarText){

        Typeface snackBarTf = Typeface.createFromAsset(getAssets(), "pf_regular.ttf");
        CoordinatorLayout root_layout = findViewById(R.id.root_layout_id);
        snackbar = Snackbar.make(root_layout, SnackBarText, Snackbar.LENGTH_LONG)
                .setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                })
                .setActionTextColor(getApplicationContext().getResources().getColor(R.color.color_white));


        Snackbar.SnackbarLayout s_layout = (Snackbar.SnackbarLayout) snackbar.getView();

        TextView textView = s_layout.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(15);
        textView.setTypeface(snackBarTf);

        s_layout.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.bg_action_bar));

        snackbar.show();
    }
    /* End (SnackBar)*/



    /* Bounce when button is clicked */
    public void Bounce_Animation(){
        Bounce_Animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.025, 0.5);
        Bounce_Animation.setInterpolator(interpolator);
    }
    /* End (Animation) */



    /* Vibrate when button is clicked */
    public void Vibration(){

        vibe = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);

        if(loadVibrationMood()){
            vibe.vibrate(35);
        }
        else {
            vibe.cancel();
        }
    }

    public void setVibrationMood(boolean state){
        getApplicationContext().getSharedPreferences("vibration", Context.MODE_PRIVATE).edit().putBoolean("VibrationMode",state).apply();
    }

    public Boolean loadVibrationMood(){
        return getApplicationContext().getSharedPreferences("vibration", Context.MODE_PRIVATE).getBoolean("VibrationMode",false);
    }
    /* End (Vibrator) */
}
