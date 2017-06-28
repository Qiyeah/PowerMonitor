package com.sunlines.qi.monitor.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by temporary on 2017-01-05.
 */
@Entity
public class Warning {
    @Id(autoincrement = true)
    private long id;
    private long fk;
    private String position;
    private String box;
    private int rid;
    private String definite;
    private float value;
    private String date;
    @Generated(hash = 430041060)
    public Warning(long id, long fk, String position, String box, int rid,
                   String definite, float value, String date) {
        this.id = id;
        this.fk = fk;
        this.position = position;
        this.box = box;
        this.rid = rid;
        this.definite = definite;
        this.value = value;
        this.date = date;
    }
    @Generated(hash = 1082087856)
    public Warning() {
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
    public String getPosition() {
        return this.position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public String getBox() {
        return this.box;
    }
    public void setBox(String box) {
        this.box = box;
    }
    public int getRid() {
        return this.rid;
    }
    public void setRid(int rid) {
        this.rid = rid;
    }
    public String getDefinite() {
        return this.definite;
    }
    public void setDefinite(String definite) {
        this.definite = definite;
    }
    public float getValue() {
        return this.value;
    }
    public void setValue(float value) {
        this.value = value;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
