package simon.mko;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import java.io.File;
import simon.mko.Cache.DiskCache;
import simon.mko.Cache.MemoryCache;

public final class Mko
{
	public interface Callback {
        void apply();
    }
	
	
    private static DiskCache diskCache ;
    private static MemoryCache memoryCache ;
    
	public static final int Type_All=0;
	public static final int Type_Memory=1;
	public static final int Type_Disk=2;
	public static void init(int cacheSize,String path){
		init("journal", 1, cacheSize, path);
	}
	public static void init(String name,int databaseversion,int cachesize,String path){
		
		DiskLruCache.JOURNAL_FILE=name;
        Mko.memoryCache = new MemoryCache();
        Mko.diskCache = new DiskCache(cachesize, path, databaseversion);
	}

	public static void remove(String key) {
        remove(key,Type_All);
    }
	public static void remove(String key,int Type){
		if(Type==Type_All){
			Mko.memoryCache.remove(key);
			Mko.diskCache.remove(key);
		}
		else if(Type==Type_Memory)
			Mko.memoryCache.remove(key);
		else if(Type==Type_Disk)
			Mko.diskCache.remove(key);
	}
	public static void clearData(){
		clearData(Type_All);
	}
	public static void clearData(int Type) {
		if(Type==Type_All){
        Mko.memoryCache.clear();
        Mko.diskCache.clear();
		}
		else if(Type==Type_Memory)
		Mko.memoryCache.clear();
		else if(Type==Type_Disk)
		Mko.diskCache.clear();
    }
	public static void putFloat(String key, float value) {
		putObject(key,new  Float(value));
         }

    public static void putDouble(String key, double value) {
		putObject(key,new Double(value));
          }
	public static void putInt(String key, int value) {
		putObject(key,new Integer(value));
    }

    public static void putLong(String key, long value) {
		putObject(key,new Long(value));
         }

    public static void putBoolean(String key, boolean value) {
		putObject(key,new Boolean(value));
		}
	public static void putObject(String key, Object value) {
		String mkey=key;
		if(key.contains(File.separator))
		mkey=key.replaceAll(File.separator,"|");
        Mko.memoryCache.put(key, value);
        Mko.diskCache.put(mkey, value);

    }
	public static void putBytes(String key, byte[] bytes) {
        putObject(key,bytes);
    }

    public static void putBitmap(String key, Bitmap value) {
        putObject(key,value);
    }
	public static void putString(String key, String value) {
		putObject(key,value);
	}
	public static int getInt(String key, int defalut) {
        Integer value = memoryCache.getInt(key);
        if (value == null) {
            value = diskCache.getInt(key);

            if (value == null) 
                return defalut;
             else {
                memoryCache.put(key,value);
                return value.intValue();
            }
        } else 
            return value.intValue();
    }
	public static long getLong(String key, long defalut) {
        Long value = memoryCache.getLong(key);
        if (value == null) {
            value = diskCache.getLong(key);

            if (value != null) {
                memoryCache.put(key,value);
                return value.longValue();
            }
            else
                return defalut;
        } else 
            return value.longValue();
    }
	public static String getString(String key,String defalut) {
        String value = memoryCache.getString(key);
        if (value == null) {
            value = diskCache.getString(key);
            if (value!=null)
                memoryCache.put(key,value);
				
            return value;
        } else {
            return value;
        }
    }
	public static boolean getBoolean(String key, boolean defalut) {
        Boolean value = memoryCache.getBoolean(key);
        if (value == null) {
            value = diskCache.getBoolean(key);
            if (value == null)
                return defalut;
            memoryCache.put(key,value);
            return value.booleanValue();
        } else {
            return value.booleanValue();
        }
    }
	public static double getDouble(String key, double defalut) {
        Double value = memoryCache.getDouble(key);
        if (value == null) {
            value = diskCache.getDouble(key);

            if (value == null)
                return defalut;
            memoryCache.put(key,value);
            return value.doubleValue();
        } else {
            return value.doubleValue();
        }
    }
	public static float getFloat(String key, float defalut) {
        Float value = memoryCache.getFloat(key);
        if (value == null) {
            value = diskCache.getFloat(key);
            if (value == null)
                return defalut;
            memoryCache.put(key,value);
            return value.floatValue();
        } else {
            return value.floatValue();
        }
    }
	public static Object getObject(String key) {
        Object object = memoryCache.getObject(key);
        if (object == null) {
            object = diskCache.getObject(key);
            if (object!=null)
                memoryCache.put(key,object);
            return object;
        } else {
            return object;
        }
    }
	public static byte[] getBytes(String key) {
        byte[] bytes = memoryCache.getBytes(key);
        if (bytes == null) {
            bytes = diskCache.getBytes(key);
        }
        if (bytes!=null)
            memoryCache.put(key,bytes);
        return bytes;
    }
	public static Bitmap getBitmap(String key,Bitmap deafult) {
        Bitmap bitmap = memoryCache.getBitmap(key);
        if (bitmap == null) {
            bitmap = diskCache.getBitmap(key);

        }
        if (bitmap!=null)
            memoryCache.put(key,bitmap);
         else
			 bitmap=deafult;
        return bitmap;
    }
	/**
     * 异步设置Bitmap类型的值
     *
     * @param key      键
     * @param value    值
     * @param callback 回调函数
     */
    public static void putBitmapAsync(final String key, final Bitmap value, final Callback callback) {
        new AsyncTask<Void, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                putBitmap(key, value);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                super.onPreExecute();
                if (callback != null) {
                    callback.apply();
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
	/**
     * 异步设置Object类型的值
     *
     * @param key      键
     * @param value    值
     * @param callback 回调函数
     */
    public static void putObjectAsync(final String key, final Object value, final Callback callback) {
        new AsyncTask<Void, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                putObject(key, value);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                super.onPreExecute();
                if (callback != null) {
                    callback.apply();
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }
}
