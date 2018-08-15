package com.dream.assessment.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ProjectMsg {
    @Id(autoincrement = true)
    private Long _id;

    private Long project_msg_id;

    @NotNull
    private String from_or_to;

    @NotNull
    private String msg_time;

    @NotNull
    private String msg_content;

    @Generated(hash = 1690667889)
    public ProjectMsg(Long _id, Long project_msg_id, @NotNull String from_or_to,
            @NotNull String msg_time, @NotNull String msg_content) {
        this._id = _id;
        this.project_msg_id = project_msg_id;
        this.from_or_to = from_or_to;
        this.msg_time = msg_time;
        this.msg_content = msg_content;
    }

    @Generated(hash = 883484169)
    public ProjectMsg() {
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public Long getProject_msg_id() {
        return this.project_msg_id;
    }

    public void setProject_msg_id(Long project_msg_id) {
        this.project_msg_id = project_msg_id;
    }

    public String getFrom_or_to() {
        return this.from_or_to;
    }

    public void setFrom_or_to(String from_or_to) {
        this.from_or_to = from_or_to;
    }

    public String getMsg_time() {
        return this.msg_time;
    }

    public void setMsg_time(String msg_time) {
        this.msg_time = msg_time;
    }

    public String getMsg_content() {
        return this.msg_content;
    }

    public void setMsg_content(String msg_content) {
        this.msg_content = msg_content;
    }

    
}
