package com.inject.xn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xn.dlib.XnClickView;
import com.xn.dlib.XnContentView;
import com.xn.dlib.XnFindView;

@XnContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @XnFindView(R.id.tv3)
    private TextView tv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv3.setText("tv33333333333333");
    }
    @XnClickView({R.id.tv1,R.id.tv2})
    public void clickView(View view){
        switch (view.getId()){
            case R.id.tv1:
                Toast.makeText(this, "TV1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv2:
                Toast.makeText(this, "TV2", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
