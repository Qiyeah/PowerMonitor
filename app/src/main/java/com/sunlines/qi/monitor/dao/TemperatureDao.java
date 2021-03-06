package com.sunlines.qi.monitor.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.sunlines.qi.monitor.entity.Temperature;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TEMPERATURE".
*/
public class TemperatureDao extends AbstractDao<Temperature, Long> {

    public static final String TABLENAME = "TEMPERATURE";

    /**
     * Properties of entity Temperature.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property Fk = new Property(1, long.class, "fk", false, "FK");
        public final static Property Rid = new Property(2, int.class, "rid", false, "RID");
        public final static Property Name = new Property(3, String.class, "name", false, "NAME");
        public final static Property Temp = new Property(4, float.class, "temp", false, "TEMP");
    }


    public TemperatureDao(DaoConfig config) {
        super(config);
    }
    
    public TemperatureDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TEMPERATURE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + // 0: id
                "\"FK\" INTEGER NOT NULL ," + // 1: fk
                "\"RID\" INTEGER NOT NULL ," + // 2: rid
                "\"NAME\" TEXT," + // 3: name
                "\"TEMP\" REAL NOT NULL );"); // 4: temp
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TEMPERATURE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Temperature entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
        stmt.bindLong(2, entity.getFk());
        stmt.bindLong(3, entity.getRid());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
        stmt.bindDouble(5, entity.getTemp());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Temperature entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
        stmt.bindLong(2, entity.getFk());
        stmt.bindLong(3, entity.getRid());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
        stmt.bindDouble(5, entity.getTemp());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public Temperature readEntity(Cursor cursor, int offset) {
        Temperature entity = new Temperature( //
            cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // fk
            cursor.getInt(offset + 2), // rid
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // name
            cursor.getFloat(offset + 4) // temp
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Temperature entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setFk(cursor.getLong(offset + 1));
        entity.setRid(cursor.getInt(offset + 2));
        entity.setName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setTemp(cursor.getFloat(offset + 4));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Temperature entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Temperature entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Temperature entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
