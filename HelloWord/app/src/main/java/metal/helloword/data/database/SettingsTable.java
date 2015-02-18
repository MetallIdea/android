package metal.helloword.data.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import metal.helloword.data.Settings;

/**
 * Created by smetalnikov on 18.02.2015.
 */
public class SettingsTable extends BaseTable {

    public static final String TABLE_NAME = "settings";

    public static final String FIELD_TYPE = "type";

    public static final String FIELD_VALUE = "value";

    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " +
            TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            FIELD_TYPE + " VARCHAR(1000)," +
            FIELD_VALUE + " BLOB" +
            ");";

    public static final String SQL_INSERT_VALUES = "INSERT INTO " +
            TABLE_NAME + " VALUES ('%1$s', '%2$s');";

    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
            + TABLE_NAME;

    public SettingsTable() {
        super();
    }

    public SettingsTable(SQLiteDatabase db){
        super(db);
    }

    @Override
    public void createTable() {
        query(SQL_CREATE_ENTRIES);

        Settings settings = new Settings();

        saveCurrent(settings);
    }

    public Settings getCurrent() {

        Cursor cursor = this.GetItems(TABLE_NAME, new String[] { "Type", "Value"}, null, null, null);

        if(cursor.moveToNext()) {

            return new Settings(cursor);
        }

        return null;
    }

    public void saveCurrent(Settings settings) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos =new ObjectOutputStream(bos);
            oos.writeObject(settings);
            oos.flush();
            byte[] value = bos.toByteArray();
            oos.close();

            Settings settingsOld = this.getCurrent();

            ContentValues values = new ContentValues();
            values.put(FIELD_TYPE, "Settings");
            values.put(FIELD_VALUE, value);

            if(settingsOld != null)
            {
                this.updateItem(TABLE_NAME, values, settingsOld.id);
            }
            else
            {
                this.InsertItem(TABLE_NAME, values);
            }

        }catch (Exception ex){

        }
    }
}
