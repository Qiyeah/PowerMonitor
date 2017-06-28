package com.sunlines.qi.monitor.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by temporary on 2016-12-16.
 */
@Entity
public class Coordinate {
    @Id(autoincrement = true)
    private long id;
    private int row;
    private int col;
    private String name;
    private String sName;
    @Generated(hash = 377616354)
    public Coordinate(long id, int row, int col, String name, String sName) {
        this.id = id;
        this.row = row;
        this.col = col;
        this.name = name;
        this.sName = sName;
    }
    @Generated(hash = 139041697)
    public Coordinate() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getRow() {
        return this.row;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public int getCol() {
        return this.col;
    }
    public void setCol(int col) {
        this.col = col;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSName() {
        return this.sName;
    }
    public void setSName(String sName) {
        this.sName = sName;
    }


}
