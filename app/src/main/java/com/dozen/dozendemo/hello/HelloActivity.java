package com.dozen.dozendemo.hello;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dozen.dozendemo.R;

/**
 * Created by Dozen on 2019/06/03 21:02.
 * Describe:
 */
public class HelloActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        HelloView helloView=findViewById(R.id.hv_hello);
        helloView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(),"Hello view!!!",Toast.LENGTH_LONG).show();
            }
        });
    }
}
