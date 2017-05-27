package com.flw.mynote.xunfei;

/**
 * Created by lgq on 2017/3/9.
 */

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.TextUnderstander;
import com.iflytek.cloud.TextUnderstanderListener;
import com.iflytek.cloud.UnderstanderResult;

import static com.iflytek.cloud.VerifierResult.TAG;

/**
 * 语义理解
 */

public class Understander {

    private TextUnderstander mTextUnderstander;
    private OnResponResultText mOnResponResultText;
    private Context mContext;

    public Understander(Context context) {
        mContext = context;
        init();
    }

    public void getResponText(OnResponResultText onResponResultText){
        mOnResponResultText = onResponResultText;
    }

    private void init() {
        mTextUnderstander = TextUnderstander.createTextUnderstander(mContext, mSpeechUdrInitListener);
        //设置参数
        setParam();
    }

    //3.开始语义理解
    public void startUnderdtander(String text) {
        mTextUnderstander.understandText(text, mTextUnderstanderListener);
    }


    private void setParam() {
        mTextUnderstander.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
    }

    /**
     * 初始化监听器（语音到语义）。
     */
    private InitListener mSpeechUdrInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "speechUnderstanderListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                Log.i("lgq", "初始化失败,错误码：" + code);
            }
        }
    };


    private TextUnderstanderListener mTextUnderstanderListener = new TextUnderstanderListener() {

        @Override
        public void onResult(final UnderstanderResult result) {
            if (null != result) {
                // 显示
                String text = result.getResultString();
                if (!TextUtils.isEmpty(text)) {
                    Log.i("lgq", "text:" + text);
                    if (mOnResponResultText != null){
                        mOnResponResultText.getResponResultText(text);
                    }
                }
            } else {
                Log.d(TAG, "understander result:null");
            }
        }

        @Override
        public void onError(SpeechError error) {
            // 文本语义不能使用回调错误码14002，请确认您下载sdk时是否勾选语义场景和私有语义的发布
            Log.i("lgq", "onError Code：" + error.getErrorCode());

        }
    };

    public interface OnResponResultText{
        public void getResponResultText(String text);
    }

}
