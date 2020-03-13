package com.mko.doo;
import android.app.Application;
import java.io.File;
import android.os.Environment;
import simon.mko.Mko;

public class App extends Application
{

	@Override
	public void onCreate()
	{
		super.onCreate();
		File file = new File(Environment.getExternalStorageDirectory().toString() + File.separator + "sample");
        if (!file.exists()) {
            file.mkdirs();
        }
		//简易初始化
		//Mko.init(10*1024,file.toString());
        Mko.init("name",1,10*1024,file.toString());
		//参数  数据库名称,数据库版本,数据库大小,数据库目录
	}
	
}
