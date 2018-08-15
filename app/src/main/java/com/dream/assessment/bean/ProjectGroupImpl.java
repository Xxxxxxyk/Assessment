package com.dream.assessment.bean;

public class ProjectGroupImpl {

    public ProjectGroupImpl(Long id,String name, String progect_group_id, String admin, boolean flag) {
        this.id = id;
        this.name = name;
        this.progect_group_id = progect_group_id;
        this.admin = admin;
        this.flag = flag;
    }

    public Long id;

    public String name;

    public String progect_group_id;

    public String admin;

    public boolean flag;
}
