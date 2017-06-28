package com.sunlines.qi.monitor.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.sunlines.qi.monitor.entity.GlobalParameter;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "GLOBAL_PARAMETER".
*/
public class GlobalParameterDao extends AbstractDao<GlobalParameter, Long> {

    public static final String TABLENAME = "GLOBAL_PARAMETER";

    /**
     * Properties of entity GlobalParameter.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property Ip = new Property(1, String.class, "ip", false, "IP");
        public final static Property Floor = new Property(2, int.class, "floor", false, "FLOOR");
        public final static Property Room = new Property(3, int.class, "room", false, "ROOM");
    }


    public GlobalParameterDao(DaoConfig config) {
        super(config);
    }
    
    public GlobalParameterDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"GLOBAL_PARAMETER\" (" + //
                "\"_id\" INTEGER PRIMARY KEY NOT NULL ," + // 0: id
                "\"IP\" TEXT," + // 1: ip
                "\"FLOOR\" INTEGER NOT NULL ," + // 2: floor
                "\"ROOM\" INTEGER NOT NULL );"); // 3: room
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"GLOBAL_PARAMETER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, GlobalParameter entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String ip = entity.getIp();
        if (ip != null) {
            stmt.bindString(2, ip);
        }
        stmt.bindLong(3, entity.getFloor());
        stmt.bindLong(4, entity.getRoom());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, GlobalParameter entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String ip = entity.getIp();
        if (ip != null) {
            stmt.bindString(2, ip);
        }
        stmt.bindLong(3, entity.getFloor());
        stmt.bindLong(4, entity.getRoom());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public GlobalParameter readEntity(Cursor cursor, int offset) {
        GlobalParameter entity = new GlobalParameter( //
            cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // ip
            cursor.getInt(offset + 2), // floor
            cursor.getInt(offset + 3) // room
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, GlobalParameter entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setIp(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setFloor(cursor.getInt(offset + 2));
        entity.setRoom(cursor.getInt(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(GlobalParameter entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(GlobalParameter entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(GlobalParameter entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}