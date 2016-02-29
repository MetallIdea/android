package metal.helloword.data.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import metal.helloword.data.AppContext;

/**
 * Created by smetalnikov on 03.02.2015.
 */
public class Coasts extends BaseTable{

    public static final String TABLE_NAME = "coasts";

    public static final String FIELD_SUM = "sum";

    public static final String FIELD_CATEGORY = "category";

    public static final String FIELD_DATE = "date_coast";

    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " +
            TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            FIELD_SUM + " NUMBER(1000, 2)," +
            FIELD_DATE + " VARCHAR(1000)," +
            FIELD_CATEGORY + " VARCHAR(1000)" +
            ");";

    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
            + TABLE_NAME;

    @Override
    public void createTable() {

    }

    /**
     * Берет по умолчанию базу из текущего контекста приложения.
     */


    public Cursor Get() {
        return GetItems(Coasts.TABLE_NAME, new String[] {
                Coasts._ID, Coasts.FIELD_SUM, Coasts.FIELD_DATE, Coasts.FIELD_CATEGORY }, null, null, Coasts.FIELD_DATE + " DESC");
    }

    public void Insert(Coast coast){
        ContentValues cv = new ContentValues();
        cv.put(Coasts.FIELD_SUM, coast.Sum);

        cv.put(Coasts.FIELD_CATEGORY, coast.Category);

        cv.put(Coasts.FIELD_DATE, DBContext.DATA_FORMAT.format(coast.DateCoast));

        InsertItem(Coasts.TABLE_NAME, cv);
    }
}
