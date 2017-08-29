package com.example.kora1101.classtest;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

/**
 * Created by kora1101 on 8/28/2017.
 */

public class SecondActivity extends Activity {

    public static Button button_smb1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kora);
        OnclickButtonListener();
    }

    public void OnclickButtonListener(){
        button_smb1 =(Button)findViewById(R.id.button);
        button_smb1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent myintent = new Intent("com.example.kora1101.classtest;");
                        startActivity(myintent);
                    }
                }
        );
    }
}
