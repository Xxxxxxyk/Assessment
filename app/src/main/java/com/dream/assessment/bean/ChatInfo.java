package com.dream.assessment.bean;

import java.io.Serializable;

public class ChatInfo implements Serializable {

    public ChatInfo(String content, String time, int fromOrTo) {
        this.content = content;
        this.time = time;
        this.fromOrTo = fromOrTo;
    }

    private static final long serialVersionUID = -6240488099748291325L;
    public int iconFromResId;
    public String iconFromUrl;
    public String content;
    public String time;
    public int fromOrTo;// 0 是收到的消息，1是发送的消息 2是图片

    @Override
    public String toString() {
        return "ChatInfoEntity [iconFromResId=" + iconFromResId
                + ", iconFromUrl=" + iconFromUrl + ", content=" + content
                + ", time=" + time + ", fromOrTo=" + fromOrTo + "]";
    }
}
