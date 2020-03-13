package com.mko.doo;

import android.app.*;
import android.os.*;
import android.widget.EditText;
import android.view.View;
import simon.mko.Mko;

public class MainActivity extends Activity 
{
	EditText key,value;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		key=findViewById(R.id.mainEditText1);
		value=findViewById(R.id.mainEditText2);
		
    }
	public void put(View v){
		//put使用
		Mko.putString(key.getText().toString(),value.getText().toString());
	}
	public void get(View v){
		//get使用
		String geti= Mko.getString(key.getText().toString(),"默认值");
		value.setText(geti);
	}
	public void demo(){
		//异步存bitmap  参数 key,bitmap,回调
		//支持异步的还有object
		Mko.putBitmapAsync("key", null, new Mko.Callback(){

				@Override
				public void apply()
				{
				}
			});
		Mko.clearData();//清除数据库,可传入下面三种参数,默认传入的是Type_All
		/*
		Mko.Type_All 清除内存和磁盘
		Mko.Type_Disk清除磁盘
		Mko.Type_Memory清除内存
		*/
		Mko.remove("key");//删除一个数据
		//参数 key,删除类型
		//类型可选参数同上
		
	}
	
}
