package metal.helloword.data.database;

import android.database.Cursor;

/**
 * Created by smetalnikov on 12.02.2015.
 */
public class Category {
    public int ID;

    public String Name;

    public int Priority;

    public  int HitCount;

    public Category(){

    }

    public Category(Cursor cursor){
        this.ID = cursor.getInt(cursor.getColumnIndex(Categories._ID));

        this.Name = cursor.getString(cursor.getColumnIndex(Categories.FIELD_NAME));

        this.HitCount = cursor.getInt(cursor.getColumnIndex(Categories.FIELD_HIT_COUNTS));

        this.Priority = cursor.getInt(cursor.getColumnIndex(Categories.FIELD_PRIORITY));
    }

    public String toString() {
        return this.Name;
    }
}
