package com.smile.studio.recipe.model.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/**
 * DAO for table "PECIPE".
 */
public class PecipeDao extends AbstractDao<Pecipe, Long> {

    public static final String TABLENAME = "PECIPE";

    /**
     * Creates the underlying database table.
     */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PECIPE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"PID\" INTEGER NOT NULL ," + // 1: pid
                "\"TITLE\" TEXT," + // 2: title
                "\"IMAGE\" TEXT," + // 3: image
                "\"DESCRIPTION\" TEXT," + // 4: description
                "\"CONTENT\" TEXT," + // 5: content
                "\"TYPE\" INTEGER);"); // 6: type
        // Add Indexes
        db.execSQL("CREATE UNIQUE INDEX " + constraint + "IDX_PECIPE_PID ON \"PECIPE\"" +
                " (\"PID\");");
    }


    public PecipeDao(DaoConfig config) {
        super(config);
    }

    public PecipeDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PECIPE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Pecipe entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getPid());

        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }

        String image = entity.getImage();
        if (image != null) {
            stmt.bindString(4, image);
        }

        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(5, description);
        }

        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(6, content);
        }

        Integer type = entity.getType();
        if (type != null) {
            stmt.bindLong(7, type);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Pecipe entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getPid());

        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }

        String image = entity.getImage();
        if (image != null) {
            stmt.bindString(4, image);
        }

        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(5, description);
        }

        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(6, content);
        }

        Integer type = entity.getType();
        if (type != null) {
            stmt.bindLong(7, type);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    @Override
    public Pecipe readEntity(Cursor cursor, int offset) {
        Pecipe entity = new Pecipe( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.getLong(offset + 1), // pid
                cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // title
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // image
                cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // description
                cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // content
                cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6) // type
        );
        return entity;
    }

    @Override
    public void readEntity(Cursor cursor, Pecipe entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPid(cursor.getLong(offset + 1));
        entity.setTitle(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setImage(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDescription(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setContent(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setType(cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6));
    }

    @Override
    public Long getKey(Pecipe entity) {
        if (entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    protected final Long updateKeyAfterInsert(Pecipe entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /**
     * Properties of entity Pecipe.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Pid = new Property(1, long.class, "pid", false, "PID");
        public final static Property Title = new Property(2, String.class, "title", false, "TITLE");
        public final static Property Image = new Property(3, String.class, "image", false, "IMAGE");
        public final static Property Description = new Property(4, String.class, "description", false, "DESCRIPTION");
        public final static Property Content = new Property(5, String.class, "content", false, "CONTENT");
        public final static Property Type = new Property(6, Integer.class, "type", false, "TYPE");
    }

    @Override
    public boolean hasKey(Pecipe entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
