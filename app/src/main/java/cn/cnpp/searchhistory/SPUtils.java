package cn.cnpp.searchhistory;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import java.util.ArrayList;

/**
 * @author Administrator
 *         SharedPreferences使用工具类
 */
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class SPUtils {
    private static SharedPreferences sp;
    private static SPUtils instance = new SPUtils();
    public static Context mContext;

    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "maigoo";

    private SPUtils() {
    }

    /**
     * xxx改为你想保存的sp文件名称
     */
    public static SPUtils getInstance(Context context) {
        mContext = context;
        if (sp == null) {
            sp = context.getApplicationContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        }
        return instance;
    }

    /**
     * 保存数据
     */
    public void put(String key, Object value) {
        if (value instanceof Integer) {
            sp.edit().putInt(key, (Integer) value).apply();
        } else if (value instanceof String) {
            sp.edit().putString(key, (String) value).apply();
        } else if (value instanceof Boolean) {
            sp.edit().putBoolean(key, (Boolean) value).apply();
        } else if (value instanceof Float) {
            sp.edit().putFloat(key, (Float) value).apply();
        } else if (value instanceof Long) {
            sp.edit().putLong(key, (Long) value).apply();
        }
    }

    /**
     * 2. 读取数据
     */
    public int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public String getString(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    /**
     * 读取数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public <T> T get(String key, T defValue) {
        T t = null;
        if (defValue instanceof String || defValue == null) {
            String value = sp.getString(key, (String) defValue);
            t = (T) value;
        } else if (defValue instanceof Integer) {
            Integer value = sp.getInt(key, (Integer) defValue);
            t = (T) value;
        } else if (defValue instanceof Boolean) {
            Boolean value = sp.getBoolean(key, (Boolean) defValue);
            t = (T) value;
        } else if (defValue instanceof Float) {
            Float value = sp.getFloat(key, (Float) defValue);
            t = (T) value;
        }
        return t;
    }

    /**
     * 保存搜索记录
     *
     * @param keyword
     */
    public void save(String keyword) {
        // 获取搜索框信息
        SharedPreferences mysp = mContext.getSharedPreferences("search_history", 0);
        String old_text = mysp.getString("history", "");
        // 利用StringBuilder.append新增内容，逗号便于读取内容时用逗号拆分开
        StringBuilder builder = new StringBuilder(old_text);
        builder.append(keyword + ",");

        // 判断搜索内容是否已经存在于历史文件，已存在则不重复添加
        if (!old_text.contains(keyword + ",")) {
            SharedPreferences.Editor myeditor = mysp.edit();
            myeditor.putString("history", builder.toString());
            myeditor.commit();
        }
    }

    public String[] getHistoryList() {
        // 获取搜索记录文件内容
        SharedPreferences sp = mContext.getSharedPreferences("search_history", 0);
        String history = sp.getString("history", "");
        // 用逗号分割内容返回数组
        String[] history_arr = history.split(",");
        // 保留前50条数据
        if (history_arr.length > 50) {
            String[] newArrays = new String[50];
            System.arraycopy(history_arr, 0, newArrays, 0, 50);
        }
        return history_arr;
    }

    /**
     * 清除搜索记录
     */
    public void cleanHistory() {
        SharedPreferences sp = mContext.getSharedPreferences("search_history", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }
}
