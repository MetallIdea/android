package metal.helloword.data.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by smetalnikov on 03.02.2015.
 */
public class BaseTable implements BaseColumns {

    public SQLiteDatabase DataBase;

    public BaseTable(SQLiteDatabase dataBase) {

        DataBase = dataBase;
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
}
