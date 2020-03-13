package simon.mko.Cache;

import android.graphics.Bitmap;


public interface AllType {

    Bitmap getBitmap(String key);

    Object getString(String key);

    byte[] getBytes(String key);

    Object getObject(String key);

    Integer getInt(String key);

    Long getLong(String key);

    Double getDouble(String key);

    Float getFloat(String key);

    Boolean getBoolean(String key);


    void put(String key, Object value);

    void remove(String key);

    void clear();
}

