package com.sunlines.qi.monitor.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

/**
 * Created by temporary on 2016-12-18.
 */
@Entity
public class WarningSetting {
    @Id(autoincrement = true)
    private long id;
    private long fk;
    private int rid;
    private float temp;
    private int state;
    private Date mDate;
    @Generated(hash = 893795434)
    public WarningSetting(long id, long fk, int rid, float temp, int state,
            Date mDate) {
        this.id = id;
        this.fk = fk;
        this.rid = rid;
        this.temp = temp;
        this.state = state;
        this.mDate = mDate;
    }
    @Generated(hash = 1365700613)
    public WarningSetting() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getFk() {
        return this.fk;
    }
    public void setFk(long fk) {
        this.fk = fk;
    }
    public int getRid() {
        return this.rid;
    }
    public void setRid(int rid) {
        this.rid = rid;
    }
    public float getTemp() {
        return this.temp;
    }
    public void setTemp(float temp) {
        this.temp = temp;
    }
    public int getState() {
        return this.state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public Date getMDate() {
        return this.mDate;
    }
    public void setMDate(Date mDate) {
        this.mDate = mDate;
    }

}
