package com.flw.mynote;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.flw.mynote.utils.JsonParser;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by lgq on 2017/3/8.
 */

public class IatText {

    // 语音听写对象
    private SpeechRecognizer mIat;
    private Context mContext;
    private StringBuilder mResultBuilder = new StringBuilder();

    private final PublishSubject subject;

    public IatText(Context context){
        mContext = context;
        subject = PublishSubject.create();
        initIat();
    }

    public Observable<String> getObservable(){
        return subject;
    }


    private void initIat() {
        mIat = SpeechRecognizer.createRecognizer(mContext, mInitListener);
        //设置参数
        setParam();
    }

    public void startListen(){
        mIat.startListening(mRecoListener);
    }

    private RecognizerListener mRecoListener = new RecognizerListener() {
        @Override
        public void onVolumeChanged(int i, byte[] bytes) {

        }

        @Override
        public void onBeginOfSpeech() {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {

            String text = JsonParser.parseIatResult(results.getResultString());
            if (text.length() > 0 && !text.equals("。")) {
                mResultBuilder.append(text);
            }
            if (isLast){
                String content = mResultBuilder.toString();
                mResultBuilder.delete(0, mResultBuilder.length());
                Log.i("lgq","result:"+content);
                subject.onNext(content);
            }
        }

        @Override
        public void onError(SpeechError speechError) {

        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };

    private void setParam(){
        mIat.setParameter(SpeechConstant.ENGINE_TYPE,SpeechConstant.TYPE_CLOUD);
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");
    }

    private InitListener mInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
               Log.d("lgq", "mInitListener error,code is :" + code);
            }
        }
    };

}
