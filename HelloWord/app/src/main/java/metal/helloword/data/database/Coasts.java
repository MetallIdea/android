package metal.helloword.data.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by smetalnikov on 03.02.2015.
 */
public class Coasts extends BaseTable{

    public static final String TABLE_NAME = "coasts";

    public static final String FIELD_SUM = "sum";

    public static final String FIELD_DATE = "date_coast";

    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " +
            Coasts.TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            Coasts.FIELD_SUM + " NUMBER(1000, 2)," +
            Coasts.FIELD_DATE + " VARCHAR(1000, 2)" +
            ");";

    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
            + Coasts.TABLE_NAME;

    public Coasts(SQLiteDatabase dataBase) {
        super(dataBase);
    }

    public Cursor GetCoasts() {
        ArrayList<Coast> returnValues = new ArrayList<>();

        return GetItems(Coasts.TABLE_NAME, new String[] {
                Coasts._ID, Coasts.FIELD_SUM, Coasts.FIELD_DATE }, null, null);
    }

    public void InsertCoast(Coast coast){
        ContentValues cv = new ContentValues();
        cv.put(Coasts.FIELD_SUM, coast.Sum);
        cv.put(Coasts.FIELD_DATE, new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(coast.DateCoast));

        InsertItem(Coasts.TABLE_NAME, cv);
    }
}
