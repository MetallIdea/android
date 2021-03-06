package metal.helloword.data.database;

import android.database.Cursor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by smetalnikov on 03.02.2015.
 */
public class Coast {

    public int ID;

    public String Category;

    public Date DateCoast;

    public double Sum;

    public Coast() {
    }

    public Coast(Cursor cursor) {
        this.ID = cursor.getInt(cursor.getColumnIndex(Coasts.FIELD_SUM));

        this.Sum = cursor.getDouble(cursor.getColumnIndex(Coasts.FIELD_SUM));

        this.Category = cursor.getString(cursor.getColumnIndex(Coasts.FIELD_CATEGORY));

        try{
            this.DateCoast = DBContext.DATA_FORMAT.parse(
                    cursor.getString(cursor.getColumnIndex(Coasts.FIELD_DATE)));
        }catch (ParseException e){
            this.DateCoast = null;
        }
    }
}
