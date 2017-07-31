package com.xiaocao.weiyue.dao;

import android.database.sqlite.SQLiteDatabase;

import com.xiaocao.weiyue.MyApp;
import com.xiaocao.weiyue.model.CollectionVo;
import com.xiaocao.weiyue.model.CollectionVoDao;
import com.xiaocao.weiyue.model.DaoMaster;
import com.xiaocao.weiyue.model.DaoSession;

import java.util.List;

/**
 * description: CollectionDao
 * author: xiaocao
 * date: 17/7/25 16:39
 */

public class CollectionDao {

    private static DaoSession daoSession;

    /**
     * 配置数据库
     */
    public static void initDatabase(String dbname) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(MyApp.getInstance(), dbname, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }

    public static void insert(CollectionVo channel) {
        getDaoInstant().getCollectionVoDao().insertOrReplace(channel);
    }

    public static void deleteChannel(long id) {
        getDaoInstant().getCollectionVoDao().deleteByKey(id);
    }

    public static CollectionVo queryImgUrl(String imgurl){
        return getDaoInstant().getCollectionVoDao().queryBuilder().where(CollectionVoDao.Properties.ImgUrl.eq(imgurl)).unique();
    }

    public static List<CollectionVo> queryAll(){
        return getDaoInstant().getCollectionVoDao().loadAll();
    }

    public static List<CollectionVo> query(int type) {
        return getDaoInstant().getCollectionVoDao().queryBuilder().where(CollectionVoDao.Properties.Type.eq(type)).list();
    }
}
