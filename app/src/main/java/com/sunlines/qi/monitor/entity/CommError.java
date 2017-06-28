package com.sunlines.qi.monitor.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

/**
 * Created by temporary on 2017-01-06.
 */
@Entity
public class CommError {
    @Id(autoincrement = true)
    private long id;
    private int type;
    private String errorName;
    private String message;
    private Date dt;
    @Generated(hash = 1626390270)
    public CommError(long id, int type, String errorName, String message, Date dt) {
        this.id = id;
        this.type = type;
        this.errorName = errorName;
        this.message = message;
        this.dt = dt;
    }
    @Generated(hash = 2064008480)
    public CommError() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getErrorName() {
        return this.errorName;
    }
    public void setErrorName(String errorName) {
        this.errorName = errorName;
    }
    public String getMessage() {
        return this.message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Date getDt() {
        return this.dt;
    }
    public void setDt(Date dt) {
        this.dt = dt;
    }

}
