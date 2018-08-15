package com.dream.assessment.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

import java.util.List;
import org.greenrobot.greendao.DaoException;

@Entity
public class ProjectGroup {

    @Id(autoincrement = true)
    private Long _id;

    @NotNull
    private String name;

    @NotNull
    private String progect_group_id;

    @NotNull
    private String admin;

    @NotNull
    private String time;

    @ToMany(referencedJoinProperty = "projectId")
    private List<Project> progectList;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1481665429)
    private transient ProjectGroupDao myDao;

    @Generated(hash = 1624213288)
    public ProjectGroup(Long _id, @NotNull String name,
            @NotNull String progect_group_id, @NotNull String admin,
            @NotNull String time) {
        this._id = _id;
        this.name = name;
        this.progect_group_id = progect_group_id;
        this.admin = admin;
        this.time = time;
    }

    @Generated(hash = 1321330112)
    public ProjectGroup() {
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProgect_group_id() {
        return this.progect_group_id;
    }

    public void setProgect_group_id(String progect_group_id) {
        this.progect_group_id = progect_group_id;
    }

    public String getAdmin() {
        return this.admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
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
    @Generated(hash = 1779186000)
    public List<Project> getProgectList() {
        if (progectList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ProjectDao targetDao = daoSession.getProjectDao();
            List<Project> progectListNew = targetDao
                    ._queryProjectGroup_ProgectList(_id);
            synchronized (this) {
                if (progectList == null) {
                    progectList = progectListNew;
                }
            }
        }
        return progectList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1749809669)
    public synchronized void resetProgectList() {
        progectList = null;
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

    @Override
    public String toString() {
        return "ProjectGroup{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", progect_group_id='" + progect_group_id + '\'' +
                ", admin='" + admin + '\'' +
                ", time='" + time + '\'' +
                ", progectList=" + getProgectList() +
                ", daoSession=" + daoSession +
                ", myDao=" + myDao +
                '}';
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 593551239)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getProjectGroupDao() : null;
    }
}
