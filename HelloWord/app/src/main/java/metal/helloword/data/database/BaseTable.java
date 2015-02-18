package metal.helloword.data.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.provider.ContactsContract;

import metal.helloword.data.AppContext;

/**
 * Created by smetalnikov on 03.02.2015.
 */
public abstract class BaseTable implements BaseColumns {

    public SQLiteDatabase DataBase;

    public BaseTable() {
        this(AppContext.Current.DB);
    }

    public BaseTable(SQLiteDatabase dataBase) {
        DataBase = dataBase;
    }

    public abstract void createTable();

    public void query(String sqlQuery){
        DataBase.execSQL(sqlQuery);
    }

    public void InsertItem(String tableName, ContentValues values) {
        DataBase.insert(tableName, null, values);
    }

    public Cursor GetItems(String tableName, String[] fields, String whereFields, String[] whereValues) {
        Cursor cursor = DataBase.query(tableName, fields,
                whereFields, // The columns for the WHERE clause
                whereValues, // The values for the WHERE clause
                null, // don't group the rows
                null, // don't filter by row groups
                null // The sort order
        );

        return cursor;
    }

    public int updateItem(String tableName, ContentValues values, int id){
        return DataBase.update(tableName, values, "_id " + "=" + id, null);
    }
}
