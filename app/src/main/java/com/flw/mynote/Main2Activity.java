package com.flw.mynote;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.flw.mynote.xunfei.IatText;
import com.flw.mynote.xunfei.Understander;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class Main2Activity extends AppCompatActivity {

    private IatText iatText;
    private EditText contentEdit;
    private Observer observer;
    private TextView answerText;

    private Understander understanderText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        initObject();
        initView();

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "开始说话...", Snackbar.LENGTH_INDEFINITE)
                        .setAction("结束说话", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                iatText.stopListen();
                            }
                        }).show();
                iatText.startListen();
            }
        });
    }
    private void initObject() {
        iatText = new IatText(this);
        understanderText = new Understander(this);
    }

    private void initView() {
        contentEdit = (EditText) findViewById(R.id.contentEdit);
        answerText = (TextView) findViewById(R.id.answerText);
        showContent();
    }

    private void showContent() {
        observer = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object value) {
                String text = (String) value;
                contentEdit.setText(text);
                contentEdit.setSelection(text.length());
                understanderText.startUnderdtander(text);
                understanderText.getResponText(new Understander.OnResponResultText() {
                    @Override
                    public void getResponResultText(String text) {
                        answerText.setText(text);
                    }
                });
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        iatText.getObservable().subscribe(observer);
    }
}
