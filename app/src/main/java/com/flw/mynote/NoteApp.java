package com.flw.mynote;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

/**
 * Created by lgq on 2017/3/8.
 */

public class NoteApp extends Application {
    @Override
    public void onCreate() {
        SpeechUtility.createUtility(NoteApp.this, SpeechConstant.APPID + "=58bf7bd3"+","+ SpeechConstant.ENGINE_MODE + "=" + SpeechConstant.MODE_MSC);
        super.onCreate();
    }
}
