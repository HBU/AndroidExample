package cn.hbu.cs.loadsqlitedb;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//ref:http://blog.csdn.net/qq629290/article/details/60126533
//ref:http://blog.csdn.net/pang_ping/article/details/74364334
//ref:https://github.com/zz51233273/helloworld2/tree/master/app/weatherBroadcast
public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private ArrayAdapter<String> adapter1;
    private ArrayAdapter<String> adapter2;
    private Spinner spinner1;
    protected static Spinner spinner2;
    private static List<String> provinces;
    private List<String> citys;
    protected static List<String> codes;
    private TextView testview;
    private String cityName;
    private DbHelper dbHelper;    //用于创建帮助器对象（处理数据库相关操作）
    private SQLiteDatabase database;    //用于创建数据库对象
    private boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DbManager dbManager=new DbManager(getApplicationContext());
        dbManager.openDataBase();
        testview=(TextView)findViewById(R.id.title_name);
        dbHelper = new DbHelper(MainActivity.this, "city.db", null, 3);//创建帮助器对象
        spinner1=(Spinner)findViewById(R.id.select_provinces);
        spinner2=(Spinner)findViewById(R.id.select_citys);
        if(null==provinces){
            provinces=new ArrayList<>();
            addProvinces();
        }
        citys=new ArrayList<>();
        codes=new ArrayList<>();

        //定义适配器
        adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,provinces);
        adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,citys);

        //3.adapter设置下拉样式
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //4.spinner加载适配器
        spinner1.setAdapter(adapter1);
        spinner2.setAdapter(adapter2);

        //5.spinner设置监听器
        spinner1.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);

        spinner1.setTag(1);     //设置标签，用于判断当前选择了哪一个下拉框
        spinner2.setTag(2);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int tag=(Integer) parent.getTag();
        // Log.d("test",tag+"");
        switch (tag){
            case 1:
                chooseProvine();
                break;
            case 2:
                testview.setText(adapter2.getItem(position));
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void chooseProvine(){
        database = dbHelper.getWritableDatabase();  //创建数据库对象
        Cursor cursor=null;

        /*
            实现功能：选择城市时，下拉框默认值为主页面所选的省份和城市
         */
        String defCity="";
        if(!flag&&!"".equals(getIntent().getStringExtra("keycode"))){          //设置省份默认值，此页面中只执行一次
            cursor=dbHelper.QueryByCode(database,getIntent().getStringExtra("keycode"));
            if (cursor.moveToFirst()) {
                int pos=adapter1.getPosition(cursor.getString(cursor.getColumnIndex(City.KEY_PROVINCE)));   //得到一个省在下拉框中的位置
                defCity=cursor.getString(cursor.getColumnIndex(City.KEY_CITY));
                spinner1.setSelection(pos);
            }
        }

        /*
            根据选择的省份，实时变化城市选项
        */
        cityName = (String) spinner1.getSelectedItem();
        testview.setText(cityName);
        cursor=dbHelper.QueryByProvince(database,cityName);  //读取城市信息
        citys.clear();      //清空原有城市数据
        codes.clear();      //清空原有相应的城市代码
        addCitysAndCodes(cursor);
        adapter2.notifyDataSetChanged();                       //通知spinner刷新数据 ***** 很必要
        if(!flag&&!"".equals(getIntent().getStringExtra("keycode"))){       //设置城市默认值，此页面中只执行一次
            int pos=adapter2.getPosition(defCity);      //得到defCity这个城市在下拉框中的位置
            spinner2.setSelection(pos);             //设置spinner2下拉框默认值
            flag=true;
        }
        database.close();
    }
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//    }

    public void addProvinces(){                 //往List provinces中添加省份
        database=dbHelper.getWritableDatabase();
        Cursor cursor=dbHelper.onQueryProvince(database);
        if (cursor.moveToFirst()) {
            do {
                City c = new City();
                c.province = cursor.getString(cursor.getColumnIndex(c.KEY_PROVINCE));
                if(provinces.indexOf(c.province)==-1){      //保证省份不重复出现
                    provinces.add(c.province);
                }
            } while (cursor.moveToNext());
        }

    }
    void addCitysAndCodes(Cursor cursor){       //往List citys，codes中添加城市和相应城市代码
        if (cursor.moveToFirst()) {
            do {
                City c = new City();
                c.cityName = cursor.getString(cursor.getColumnIndex(c.KEY_CITY));
                c.code= Integer.parseInt(cursor.getString(cursor.getColumnIndex(c.KEY_CODE)));
                citys.add(c.cityName);
                codes.add(String.valueOf(c.code));
            } while (cursor.moveToNext());
        }
    }
}
