package org.zero.zxing;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ScanEvent {

    private boolean isSuccess   ;

    private String   mContent     ;

    private String   failMsg      ;

    public ScanEvent(boolean isSuccess, String content) {
        this.isSuccess = isSuccess;
        mContent = content;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getFailMsg() {
        return failMsg;
    }

    public void setFailMsg(String failMsg) {
        this.failMsg = failMsg;
    }
}
