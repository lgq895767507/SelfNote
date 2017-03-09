package com.flw.mynote;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.flw.mynote.xunfei.IatText;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class Main2Activity extends AppCompatActivity {

    private IatText iatText;
    private EditText contentEdit;
    private Observer observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        initObject();
        initView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                iatText.startListen();
            }
        });
    }
    private void initObject() {
        iatText = new IatText(this);
    }

    private void initView() {
        contentEdit = (EditText) findViewById(R.id.contentEdit);
        showContent();
    }

    private void showContent() {
        observer = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object value) {
                contentEdit.setText((String) value);
                contentEdit.setSelection(((String) value).length());
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
