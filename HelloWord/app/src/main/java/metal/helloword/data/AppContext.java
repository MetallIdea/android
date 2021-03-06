package metal.helloword.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import metal.helloword.data.database.DBContext;

/**
 * Created by smetalnikov on 03.02.2015.
 */
public class AppContext {
    public DBContext DBContext;

    public SQLiteDatabase DB;

    public Settings settings;

    public static AppContext Current;

    public AppContext(Context context) {

        DBContext = new DBContext(context);

        DB = DBContext.getWritableDatabase();
    }

    public void close(){

        if(DB.isOpen())
        {
            DB.close();
        }

        DBContext.close();

    }
}
