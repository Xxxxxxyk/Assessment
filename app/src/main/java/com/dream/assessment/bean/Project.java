package com.dream.assessment.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.DaoException;

@Entity
public class Project {

    @Id
    private Long _id;

    private Long projectId;

    @NotNull
    private String name;

    @NotNull
    private String progect_id;

    @NotNull
    private String desc;

    @NotNull
    private String time;

    @ToMany(referencedJoinProperty = "project_msg_id")
    private List<ProjectMsg> progectMsgList;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1378029107)
    private transient ProjectDao myDao;

    @Generated(hash = 691729101)
    public Project(Long _id, Long projectId, @NotNull String name,
            @NotNull String progect_id, @NotNull String desc,
            @NotNull String time) {
        this._id = _id;
        this.projectId = projectId;
        this.name = name;
        this.progect_id = progect_id;
        this.desc = desc;
        this.time = time;
    }

    @Generated(hash = 1767516619)
    public Project() {
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public Long getProjectId() {
        return this.projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProgect_id() {
        return this.progect_id;
    }

    public void setProgect_id(String progect_id) {
        this.progect_id = progect_id;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 138638211)
    public List<ProjectMsg> getProgectMsgList() {
        if (progectMsgList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ProjectMsgDao targetDao = daoSession.getProjectMsgDao();
            List<ProjectMsg> progectMsgListNew = targetDao
                    ._queryProject_ProgectMsgList(_id);
            synchronized (this) {
                if (progectMsgList == null) {
                    progectMsgList = progectMsgListNew;
                }
            }
        }
        return progectMsgList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 887619849)
    public synchronized void resetProgectMsgList() {
        progectMsgList = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2081800561)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getProjectDao() : null;
    }
    

}
