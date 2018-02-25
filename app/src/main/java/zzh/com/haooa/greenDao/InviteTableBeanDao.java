package zzh.com.haooa.greenDao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import zzh.com.haooa.bean.InviteTableBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "INVITE_TABLE_BEAN".
*/
public class InviteTableBeanDao extends AbstractDao<InviteTableBean, String> {

    public static final String TABLENAME = "INVITE_TABLE_BEAN";

    /**
     * Properties of entity InviteTableBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property User_hxUsername = new Property(0, String.class, "user_hxUsername", true, "USER_HX_USERNAME");
        public final static Property Nick = new Property(1, String.class, "nick", false, "NICK");
        public final static Property Group_name = new Property(2, String.class, "group_name", false, "GROUP_NAME");
        public final static Property Group_hxid = new Property(3, String.class, "group_hxid", false, "GROUP_HXID");
        public final static Property Group_invite_user = new Property(4, String.class, "group_invite_user", false, "GROUP_INVITE_USER");
        public final static Property Reason = new Property(5, String.class, "reason", false, "REASON");
        public final static Property Status = new Property(6, int.class, "status", false, "STATUS");
    }


    public InviteTableBeanDao(DaoConfig config) {
        super(config);
    }
    
    public InviteTableBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"INVITE_TABLE_BEAN\" (" + //
                "\"USER_HX_USERNAME\" TEXT PRIMARY KEY NOT NULL ," + // 0: user_hxUsername
                "\"NICK\" TEXT," + // 1: nick
                "\"GROUP_NAME\" TEXT," + // 2: group_name
                "\"GROUP_HXID\" TEXT," + // 3: group_hxid
                "\"GROUP_INVITE_USER\" TEXT," + // 4: group_invite_user
                "\"REASON\" TEXT," + // 5: reason
                "\"STATUS\" INTEGER NOT NULL );"); // 6: status
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"INVITE_TABLE_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, InviteTableBean entity) {
        stmt.clearBindings();
 
        String user_hxUsername = entity.getUser_hxUsername();
        if (user_hxUsername != null) {
            stmt.bindString(1, user_hxUsername);
        }
 
        String nick = entity.getNick();
        if (nick != null) {
            stmt.bindString(2, nick);
        }
 
        String group_name = entity.getGroup_name();
        if (group_name != null) {
            stmt.bindString(3, group_name);
        }
 
        String group_hxid = entity.getGroup_hxid();
        if (group_hxid != null) {
            stmt.bindString(4, group_hxid);
        }
 
        String group_invite_user = entity.getGroup_invite_user();
        if (group_invite_user != null) {
            stmt.bindString(5, group_invite_user);
        }
 
        String reason = entity.getReason();
        if (reason != null) {
            stmt.bindString(6, reason);
        }
        stmt.bindLong(7, entity.getStatus());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, InviteTableBean entity) {
        stmt.clearBindings();
 
        String user_hxUsername = entity.getUser_hxUsername();
        if (user_hxUsername != null) {
            stmt.bindString(1, user_hxUsername);
        }
 
        String nick = entity.getNick();
        if (nick != null) {
            stmt.bindString(2, nick);
        }
 
        String group_name = entity.getGroup_name();
        if (group_name != null) {
            stmt.bindString(3, group_name);
        }
 
        String group_hxid = entity.getGroup_hxid();
        if (group_hxid != null) {
            stmt.bindString(4, group_hxid);
        }
 
        String group_invite_user = entity.getGroup_invite_user();
        if (group_invite_user != null) {
            stmt.bindString(5, group_invite_user);
        }
 
        String reason = entity.getReason();
        if (reason != null) {
            stmt.bindString(6, reason);
        }
        stmt.bindLong(7, entity.getStatus());
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public InviteTableBean readEntity(Cursor cursor, int offset) {
        InviteTableBean entity = new InviteTableBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // user_hxUsername
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // nick
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // group_name
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // group_hxid
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // group_invite_user
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // reason
            cursor.getInt(offset + 6) // status
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, InviteTableBean entity, int offset) {
        entity.setUser_hxUsername(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setNick(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setGroup_name(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setGroup_hxid(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setGroup_invite_user(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setReason(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setStatus(cursor.getInt(offset + 6));
     }
    
    @Override
    protected final String updateKeyAfterInsert(InviteTableBean entity, long rowId) {
        return entity.getUser_hxUsername();
    }
    
    @Override
    public String getKey(InviteTableBean entity) {
        if(entity != null) {
            return entity.getUser_hxUsername();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(InviteTableBean entity) {
        return entity.getUser_hxUsername() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
