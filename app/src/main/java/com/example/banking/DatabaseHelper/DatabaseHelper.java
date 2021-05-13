package com.example.banking.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private String Table1 = "Table1";
    private String Table2 = "Table2";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "User.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + Table1 +" (PHONENUMBER INTEGER PRIMARY KEY ,NAME TEXT,BALANCE DECIMAL,EMAIL VARCHAR,ACCOUNT_NO VARCHAR,IFSC_CODE VARCHAR)");
        db.execSQL("create table " + Table2 +" (TRANSACTIONID INTEGER PRIMARY KEY AUTOINCREMENT,DATE TEXT,FROMNAME TEXT,TONAME TEXT,AMOUNT DECIMAL,STATUS TEXT)");
        db.execSQL("insert into Table1 values(6301547854,'Alisha',15000.00,'alisha@gmail.com','XXXXXXXXXXXX3021','IBI43210987')");;
        db.execSQL("insert into Table1 values(9264909932,'Ashmit Singh',5000.00,'ashmitsingh204@gmail.com','XXXXXXXXXXXX1247','IBI08576543')");
        db.execSQL("insert into Table1 values(7854120214,'Abuzar Ahmed Quadri',3000.00,'abuzar@gmail.com','XXXXXXXXXXXX7433','IBI65432109')");
        db.execSQL("insert into Table1 values(7452301259,'Adarsh Singh',6000.00,'adarsh@gmail.com','XXXXXXXXXXXX7451','IBI87654321')");
        db.execSQL("insert into Table1 values(8542136521,'Joy Dey',7000.00,'joyd@gmail.com','XXXXXXXXXXXX6521','IBI98765432')");
        db.execSQL("insert into Table1 values(9471203215,'Shristi rani',9000.00,'shrist1304@gmail.com','XXXXXXXXXXXX5201','IBI76543210')");
        db.execSQL("insert into Table1 values(9517538246,'Heenal Keshwani',5000.00,'heenal@gmail.com','XXXXXXXXXXXX3456','IBI21098765')");
        db.execSQL("insert into Table1 values(9784514747,'Sarthak Swapnil',10000.00,'sarthak@gmail.com','XXXXXXXXXXXX5214','IBI54321098')");
        db.execSQL("insert into Table1 values(8520147963,'Shaurya Srivastav',5000.00,'shaurya@gmail.com','XXXXXXXXXXXX5234','IBI32109876')");
        db.execSQL("insert into Table1 values(3652148796,'Priyanka Patra',25000.00,'pripi@gmail.com','XXXXXXXXXXXX3959','IBI10987654')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Table1);
        db.execSQL("DROP TABLE IF EXISTS "+ Table2);
        onCreate(db);
    }

    public Cursor readalldata(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Table1", null);
        return cursor;
    }

    public Cursor readparticulardata(String phonenumber){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Table1 where phonenumber = " +phonenumber, null);
        return cursor;
    }

    public Cursor readselectuserdata(String phonenumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Table1 except select * from Table1 where phonenumber = " +phonenumber, null);
        return cursor;
    }

    public void updateAmount(String phonenumber, String amount){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update Table1 set balance = " + amount + " where phonenumber = " +phonenumber);
    }

    public Cursor readtransferdata(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Table2", null);
        return cursor;
    }

    public boolean insertTransferData(String date,String from_name, String to_name, String amount, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("DATE", date);
        contentValues.put("FROMNAME", from_name);
        contentValues.put("TONAME", to_name);
        contentValues.put("AMOUNT", amount);
        contentValues.put("STATUS", status);
        Long result = db.insert(Table2, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
}
