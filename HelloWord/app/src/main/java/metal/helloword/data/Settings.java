package metal.helloword.data;

import android.database.Cursor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import metal.helloword.data.database.BaseTable;
import metal.helloword.data.database.SettingsTable;

/**
 * Created by smetalnikov on 18.02.2015.
 */
public class Settings{
    public int id;

    public String dateFormat;

    public String timeFormat;

    public Settings current;

    public Settings()
    {
        dateFormat = "dd.MM";

        timeFormat = "HH:mm";
    }

    public Settings(Cursor cursor)
    {
        this.id = cursor.getInt(cursor.getColumnIndex(SettingsTable._ID));

        byte[] buffer = cursor.getBlob(cursor.getColumnIndex(SettingsTable.FIELD_VALUE));

        ByteArrayInputStream fis = new ByteArrayInputStream(buffer);
        try {
            ObjectInputStream oin =new ObjectInputStream(fis);
            this.current = (Settings) oin.readObject();
        }catch (Exception ex){

        }
    }
}
