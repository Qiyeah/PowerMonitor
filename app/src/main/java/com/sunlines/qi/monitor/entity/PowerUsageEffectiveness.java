package com.sunlines.qi.monitor.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

/**
 * Created by temporary on 2017-01-06.
 */
@Entity
public class PowerUsageEffectiveness {
    @Id
    private long id;
    private int floor;
    private int room;
    private int type;
    private float value;
    private Date dt;
    @Generated(hash = 1081925207)
    public PowerUsageEffectiveness(long id, int floor, int room, int type,
            float value, Date dt) {
        this.id = id;
        this.floor = floor;
        this.room = room;
        this.type = type;
        this.value = value;
        this.dt = dt;
    }
    @Generated(hash = 1818014576)
    public PowerUsageEffectiveness() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getFloor() {
        return this.floor;
    }
    public void setFloor(int floor) {
        this.floor = floor;
    }
    public int getRoom() {
        return this.room;
    }
    public void setRoom(int room) {
        this.room = room;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public float getValue() {
        return this.value;
    }
    public void setValue(float value) {
        this.value = value;
    }
    public Date getDt() {
        return this.dt;
    }
    public void setDt(Date dt) {
        this.dt = dt;
    }

}
