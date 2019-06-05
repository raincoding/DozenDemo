package com.dozen.dozendemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dozen.dozendemo.clear.ClearActivity;
import com.dozen.dozendemo.hello.HelloActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnHello=findViewById(R.id.btn_view);
        btnHello.setOnClickListener(btnListener);

        Button btnClear=findViewById(R.id.btn_clear);
        btnClear.setOnClickListener(btnListener);

    }

    View.OnClickListener btnListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Class cla=null;

            switch (view.getId()){
                case R.id.btn_view:
                    cla=HelloActivity.class;
                    break;
                case R.id.btn_clear:
                    cla=ClearActivity.class;
                    break;
            }

            if (cla!=null){
                startActivity(new Intent(MainActivity.this,cla));
            }
        }
    };

}
