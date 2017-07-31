package com.xiaocao.weiyue.dao;

import android.database.sqlite.SQLiteDatabase;

import com.xiaocao.weiyue.MyApp;
import com.xiaocao.weiyue.model.ChannelVo;
import com.xiaocao.weiyue.model.ChannelVoDao;
import com.xiaocao.weiyue.model.DaoMaster;
import com.xiaocao.weiyue.model.DaoSession;

import java.util.List;

/**
 * description: ChannelDao
 * author: xiaocao
 * date: 17/7/22 10:43
 */

public class ChannelDao {


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

    /**
     * 添加单条数据
     *
     * @param channel
     */
    public static void insertChannel(ChannelVo channel) {
//        MyApp.getDaoInstant().getTestModeDao().insert(channel);
        getDaoInstant().getChannelVoDao().insertOrReplace(channel);
    }

    /**
     * 添加多条数据
     * @param channels
     */
    public static void insertsChannel(List<ChannelVo> channels){
        getDaoInstant().getChannelVoDao().insertInTx(channels);
    }

    /**
     * 删除数据
     *
     * @param id
     */
    public static void deleteChannel(long id) {
        getDaoInstant().getChannelVoDao().deleteByKey(id);
    }

    /**
     * 更新数据
     *
     * @param channel
     */
    public static void updateChannel(ChannelVo channel) {
        getDaoInstant().getChannelVoDao().update(channel);
    }

    /**
     * 更新数据
     *
     * @param channels
     */
    public static void updatesChannel(List<ChannelVo> channels) {
        getDaoInstant().getChannelVoDao().updateInTx(channels);
    }


    /**
     * 查询全部
     * @return
     */
    public static List<ChannelVo> queryAll(){
        return getDaoInstant().getChannelVoDao().loadAll();
    }

    /**
     * 查询
     * @param type
     * @return
     */
    public static List<ChannelVo> queryChannel(int type) {
        return getDaoInstant().getChannelVoDao().queryBuilder().where(ChannelVoDao.Properties.Type.eq(type)).list();
    }
}
