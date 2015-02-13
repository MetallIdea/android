package metal.helloword.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.text.SimpleDateFormat;

/**
 * Created by smetalnikov on 03.02.2015.
 */
public class DBContext extends SQLiteOpenHelper {

    public static final SimpleDateFormat DATA_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private static final String DATABASE_NAME = "cat_database.db";
    private static final int DATABASE_VERSION = 8;

    public SQLiteDatabase DataBase;

    public DBContext(Context context) {
        // TODO Auto-generated constructor stub
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        DataBase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(Coasts.SQL_CREATE_ENTRIES);

        db.execSQL(Categories.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        Log.w("LOG_TAG", "Обновление базы данных с версии " + oldVersion
                + " до версии " + newVersion + ", которое удалит все старые данные");
        // Удаляем предыдущую таблицу при апгрейде
        db.execSQL(Coasts.SQL_DELETE_ENTRIES);

        db.execSQL(Categories.SQL_DELETE_ENTRIES);
        // Создаём новый экземпляр таблицы
        onCreate(db);
    }
}