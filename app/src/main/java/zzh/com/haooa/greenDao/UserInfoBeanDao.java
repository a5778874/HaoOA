package zzh.com.haooa.greenDao;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.SqlUtils;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import zzh.com.haooa.bean.DepartmentBean;

import zzh.com.haooa.bean.UserInfoBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER_INFO_BEAN".
*/
public class UserInfoBeanDao extends AbstractDao<UserInfoBean, String> {

    public static final String TABLENAME = "USER_INFO_BEAN";

    /**
     * Properties of entity UserInfoBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property HxUsername = new Property(0, String.class, "HxUsername", true, "HX_USERNAME");
        public final static Property Nick = new Property(1, String.class, "nick", false, "NICK");
        public final static Property Sex = new Property(2, String.class, "sex", false, "SEX");
        public final static Property Head = new Property(3, String.class, "head", false, "HEAD");
        public final static Property DepartmentID = new Property(4, String.class, "departmentID", false, "DEPARTMENT_ID");
        public final static Property Phone = new Property(5, String.class, "phone", false, "PHONE");
        public final static Property Mail = new Property(6, String.class, "mail", false, "MAIL");
        public final static Property IsAdmin = new Property(7, Boolean.class, "isAdmin", false, "IS_ADMIN");
        public final static Property CreateTime = new Property(8, String.class, "createTime", false, "CREATE_TIME");
        public final static Property UpdateTime = new Property(9, String.class, "updateTime", false, "UPDATE_TIME");
    }

    private DaoSession daoSession;


    public UserInfoBeanDao(DaoConfig config) {
        super(config);
    }
    
    public UserInfoBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER_INFO_BEAN\" (" + //
                "\"HX_USERNAME\" TEXT PRIMARY KEY NOT NULL ," + // 0: HxUsername
                "\"NICK\" TEXT," + // 1: nick
                "\"SEX\" TEXT," + // 2: sex
                "\"HEAD\" TEXT," + // 3: head
                "\"DEPARTMENT_ID\" TEXT," + // 4: departmentID
                "\"PHONE\" TEXT," + // 5: phone
                "\"MAIL\" TEXT," + // 6: mail
                "\"IS_ADMIN\" INTEGER," + // 7: isAdmin
                "\"CREATE_TIME\" TEXT," + // 8: createTime
                "\"UPDATE_TIME\" TEXT);"); // 9: updateTime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER_INFO_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UserInfoBean entity) {
        stmt.clearBindings();
 
        String HxUsername = entity.getHxUsername();
        if (HxUsername != null) {
            stmt.bindString(1, HxUsername);
        }
 
        String nick = entity.getNick();
        if (nick != null) {
            stmt.bindString(2, nick);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(3, sex);
        }
 
        String head = entity.getHead();
        if (head != null) {
            stmt.bindString(4, head);
        }
 
        String departmentID = entity.getDepartmentID();
        if (departmentID != null) {
            stmt.bindString(5, departmentID);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(6, phone);
        }
 
        String mail = entity.getMail();
        if (mail != null) {
            stmt.bindString(7, mail);
        }
 
        Boolean isAdmin = entity.getIsAdmin();
        if (isAdmin != null) {
            stmt.bindLong(8, isAdmin ? 1L: 0L);
        }
 
        String createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindString(9, createTime);
        }
 
        String updateTime = entity.getUpdateTime();
        if (updateTime != null) {
            stmt.bindString(10, updateTime);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UserInfoBean entity) {
        stmt.clearBindings();
 
        String HxUsername = entity.getHxUsername();
        if (HxUsername != null) {
            stmt.bindString(1, HxUsername);
        }
 
        String nick = entity.getNick();
        if (nick != null) {
            stmt.bindString(2, nick);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(3, sex);
        }
 
        String head = entity.getHead();
        if (head != null) {
            stmt.bindString(4, head);
        }
 
        String departmentID = entity.getDepartmentID();
        if (departmentID != null) {
            stmt.bindString(5, departmentID);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(6, phone);
        }
 
        String mail = entity.getMail();
        if (mail != null) {
            stmt.bindString(7, mail);
        }
 
        Boolean isAdmin = entity.getIsAdmin();
        if (isAdmin != null) {
            stmt.bindLong(8, isAdmin ? 1L: 0L);
        }
 
        String createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindString(9, createTime);
        }
 
        String updateTime = entity.getUpdateTime();
        if (updateTime != null) {
            stmt.bindString(10, updateTime);
        }
    }

    @Override
    protected final void attachEntity(UserInfoBean entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public UserInfoBean readEntity(Cursor cursor, int offset) {
        UserInfoBean entity = new UserInfoBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // HxUsername
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // nick
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // sex
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // head
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // departmentID
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // phone
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // mail
            cursor.isNull(offset + 7) ? null : cursor.getShort(offset + 7) != 0, // isAdmin
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // createTime
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9) // updateTime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UserInfoBean entity, int offset) {
        entity.setHxUsername(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setNick(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setSex(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setHead(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDepartmentID(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPhone(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setMail(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setIsAdmin(cursor.isNull(offset + 7) ? null : cursor.getShort(offset + 7) != 0);
        entity.setCreateTime(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setUpdateTime(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
     }
    
    @Override
    protected final String updateKeyAfterInsert(UserInfoBean entity, long rowId) {
        return entity.getHxUsername();
    }
    
    @Override
    public String getKey(UserInfoBean entity) {
        if(entity != null) {
            return entity.getHxUsername();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(UserInfoBean entity) {
        return entity.getHxUsername() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getDepartmentBeanDao().getAllColumns());
            builder.append(" FROM USER_INFO_BEAN T");
            builder.append(" LEFT JOIN DEPARTMENT_BEAN T0 ON T.\"DEPARTMENT_ID\"=T0.\"DEPARTMENT_ID\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected UserInfoBean loadCurrentDeep(Cursor cursor, boolean lock) {
        UserInfoBean entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        DepartmentBean department = loadCurrentOther(daoSession.getDepartmentBeanDao(), cursor, offset);
        entity.setDepartment(department);

        return entity;    
    }

    public UserInfoBean loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<UserInfoBean> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<UserInfoBean> list = new ArrayList<UserInfoBean>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<UserInfoBean> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<UserInfoBean> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}