package com.example.reol.opengles02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

//    @BindView(R.id.button) Button btn2;
    @BindView(R.id.button2) Button btn1;
    @BindView(R.id.button3) Button btn3;

    @OnClick(R.id.button) void jump(){
        startActivity(new Intent(MainActivity.this, NewModelLabAct.class));
    }
    @OnClick(R.id.button3) void jump3(){
        startActivity(new Intent(MainActivity.this, StlDisplayAct.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
