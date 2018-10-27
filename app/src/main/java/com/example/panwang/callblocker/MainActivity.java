package com.example.panwang.callblocker;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    protected EditText  m_whiteCodesView;
    protected Button    m_whiteCodesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_whiteCodesView = (EditText)findViewById(R.id.WhiteCodesText);
        m_whiteCodesButton = (Button)findViewById(R.id.WhiteCodeFinish);

        m_whiteCodesButton.setVisibility(View.GONE);

        m_whiteCodesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_whiteCodesButton.setVisibility(View.GONE);
                m_whiteCodesView.clearFocus();
                m_whiteCodesView.setFocusableInTouchMode(false);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(m_whiteCodesView.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);

                String whiteCodesString = m_whiteCodesView.getText().toString();
                String[] whiteCodes = whiteCodesString.split(",");
                CallsManager.m_whiteCodes = new ArrayList<String>(Arrays.asList(whiteCodes));
            }
        });

        m_whiteCodesView.setFocusableInTouchMode(false);

        m_whiteCodesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_whiteCodesButton.setVisibility(View.VISIBLE);
                m_whiteCodesView.setFocusableInTouchMode(true);
                m_whiteCodesView.requestFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(m_whiteCodesView, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        if (!CallsManager.m_whiteCodes.isEmpty()) {
            String whiteCodesString = CallsManager.m_whiteCodes.get(0);
            for (int i=1; i<CallsManager.m_whiteCodes.size(); i++) {
                whiteCodesString += "," + CallsManager.m_whiteCodes.get(i);
            }
            m_whiteCodesView.setText(whiteCodesString);
        }
    }

}
