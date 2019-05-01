package com.example.administrator.accounapp;

import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;

public class RecordDatabaseHelper extends SQLiteOpenHelper {

    private static String TAG = "RecordDatabaseHelper";
    public static final String DB_NAME = "Record";
    public static final String CREATE_RECORD_DB = "create table Record("
            + "id integer primary key autoincrement,"
            + "uuid text,"
            + "type integer,"
            + "category, "
            + "remark integer,"
            + "amount double,"
            + "time integer,"
            + "date date)";
    private RecordBean bean;

    public RecordDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RECORD_DB);   //执行SQL语句的方法：exeSQL(String)    //表纯在不执行
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //插入一条记录
    public void addRecord(RecordBean bean){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("uuid",bean.getUuid());
        values.put("type",bean.getType());
        values.put("category",bean.getCategory());
        values.put("remark",bean.getRemark());
        values.put("amount",bean.getAmount());
        values.put("time",bean.getTimeStamp());
        values.put("date",bean.getDate());
        db.insert(DB_NAME,null,values);
        values.clear();
        Log.i(TAG,bean.getUuid() + " added");
    }
    //删除一条记录  //根据uuid删除
    public void removeRecord(String uuid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_NAME,"uuid = ?",new String[]{uuid});
    }
    //编辑记录  //根据uuid
    public void editRecord(String uuid,RecordBean bean){
//        SQLiteDatabase db = this.getWritableDatabase();
        //先删除旧记录
        removeRecord(uuid);
        //在添加新记录
        addRecord(bean);
    }
    //查询
    public LinkedList<RecordBean> readRecords(String dateStr){
        LinkedList<RecordBean> records = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select DISTINCT * from Record where date = ? order by time asc",new String[]{dateStr});
        if(cursor.moveToFirst()){
            do {
                //字段
                String uuid = cursor.getString(cursor.getColumnIndex("uuid"));
                int type = cursor.getInt(cursor.getColumnIndex("type"));
                String category = cursor.getString(cursor.getColumnIndex("category"));
                String remark = cursor.getString(cursor.getColumnIndex("remark"));
                double amount = cursor.getDouble(cursor.getColumnIndex("amount"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                long timeStamp = cursor.getLong(cursor.getColumnIndex("time"));

                RecordBean record = new RecordBean();
                record.setUuid(uuid);
                record.setType(type);
                record.setCategory(category);
                record.setRemark(remark);
                record.setAmount(amount);
                record.setDate(date);
                record.setTimeStamp(timeStamp);

                records.add(record);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return records;
    }

    //...
    public LinkedList<String> getAvaliableDate(){
        LinkedList<String> dates = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select DISTINCT * from Record order by date asc",new String[]{});
        if(cursor.moveToFirst()){
            do {
                String date = cursor.getString(cursor.getColumnIndex("date"));
                if (!dates.contains(date)){
                    dates.add(date);
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
;

        return dates;
    }

}

