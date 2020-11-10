package com.github.microkibaco.android.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;

import com.github.microkibaco.android.annotation.MkBindView;
import com.github.microkibaco.android.sdk.MkButterKnife;

public class MainActivity extends AppCompatActivity {
    @MkBindView(R.id.button)
    AppCompatButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MkButterKnife.bindView(this);

        button.setText("New Text");
    }
}
