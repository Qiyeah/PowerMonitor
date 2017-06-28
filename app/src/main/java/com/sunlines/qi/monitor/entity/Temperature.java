package com.sunlines.qi.monitor.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by temporary on 2016-12-16.
 */
@Entity
public class Temperature {
    @Id(autoincrement = true)
    private long id;
    private long fk;
    private int rid;
    private String name;
    private float temp;
    @Generated(hash = 110068283)
    public Temperature(long id, long fk, int rid, String name, float temp) {
        this.id = id;
        this.fk = fk;
        this.rid = rid;
        this.name = name;
        this.temp = temp;
    }
    @Generated(hash = 1726533429)
    public Temperature() {
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
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public float getTemp() {
        return this.temp;
    }
    public void setTemp(float temp) {
        this.temp = temp;
    }

}
