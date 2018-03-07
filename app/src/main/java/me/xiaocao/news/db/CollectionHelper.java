package me.xiaocao.news.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import me.xiaocao.news.app.MyApp;
import me.xiaocao.news.model.db.CollectionVo;
import me.xiaocao.news.model.db.CollectionVoDao;
import me.xiaocao.news.model.db.DaoMaster;
import me.xiaocao.news.model.db.DaoSession;

/**
 * description: CollectionDao
 * author: lijun
 * date: 17/9/14 17:45
 */

public class CollectionHelper {
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

    public static CollectionVo queryWebUrl(String url) {
        return getDaoInstant().getCollectionVoDao().queryBuilder().where(CollectionVoDao.Properties.Url.eq(url)).unique();
    }

    public static List<CollectionVo> queryAll() {
        return getDaoInstant().getCollectionVoDao().loadAll();
    }

    public static List<CollectionVo> getPageQuery(int offset) {
        return getDaoInstant().getCollectionVoDao().queryBuilder().offset(offset * 10).limit(10).list();
    }

}
