package com.sunlines.qi.monitor.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.sunlines.qi.monitor.entity.Energy;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ENERGY".
*/
public class EnergyDao extends AbstractDao<Energy, Long> {

    public static final String TABLENAME = "ENERGY";

    /**
     * Properties of entity Energy.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property Floor = new Property(1, int.class, "floor", false, "FLOOR");
        public final static Property Room = new Property(2, int.class, "room", false, "ROOM");
        public final static Property Type = new Property(3, int.class, "type", false, "TYPE");
        public final static Property Value = new Property(4, float.class, "value", false, "VALUE");
        public final static Property Dt = new Property(5, java.util.Date.class, "dt", false, "DT");
    }


    public EnergyDao(DaoConfig config) {
        super(config);
    }
    
    public EnergyDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ENERGY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY NOT NULL ," + // 0: id
                "\"FLOOR\" INTEGER NOT NULL ," + // 1: floor
                "\"ROOM\" INTEGER NOT NULL ," + // 2: room
                "\"TYPE\" INTEGER NOT NULL ," + // 3: type
                "\"VALUE\" REAL NOT NULL ," + // 4: value
                "\"DT\" INTEGER);"); // 5: dt
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ENERGY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Energy entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
        stmt.bindLong(2, entity.getFloor());
        stmt.bindLong(3, entity.getRoom());
        stmt.bindLong(4, entity.getType());
        stmt.bindDouble(5, entity.getValue());
 
        java.util.Date dt = entity.getDt();
        if (dt != null) {
            stmt.bindLong(6, dt.getTime());
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Energy entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
        stmt.bindLong(2, entity.getFloor());
        stmt.bindLong(3, entity.getRoom());
        stmt.bindLong(4, entity.getType());
        stmt.bindDouble(5, entity.getValue());
 
        java.util.Date dt = entity.getDt();
        if (dt != null) {
            stmt.bindLong(6, dt.getTime());
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public Energy readEntity(Cursor cursor, int offset) {
        Energy entity = new Energy( //
            cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // floor
            cursor.getInt(offset + 2), // room
            cursor.getInt(offset + 3), // type
            cursor.getFloat(offset + 4), // value
            cursor.isNull(offset + 5) ? null : new java.util.Date(cursor.getLong(offset + 5)) // dt
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Energy entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setFloor(cursor.getInt(offset + 1));
        entity.setRoom(cursor.getInt(offset + 2));
        entity.setType(cursor.getInt(offset + 3));
        entity.setValue(cursor.getFloat(offset + 4));
        entity.setDt(cursor.isNull(offset + 5) ? null : new java.util.Date(cursor.getLong(offset + 5)));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Energy entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Energy entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Energy entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}