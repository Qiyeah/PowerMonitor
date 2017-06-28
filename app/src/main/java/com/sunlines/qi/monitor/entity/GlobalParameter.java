package com.sunlines.qi.monitor.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by temporary on 2017-01-06.
 */
@Entity
public class GlobalParameter {
    @Id
    private long id;
    private String ip;
    private int floor;
    private int room;
    @Generated(hash = 514543620)
    public GlobalParameter(long id, String ip, int floor, int room) {
        this.id = id;
        this.ip = ip;
        this.floor = floor;
        this.room = room;
    }
    @Generated(hash = 1750850206)
    public GlobalParameter() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getIp() {
        return this.ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
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

}
