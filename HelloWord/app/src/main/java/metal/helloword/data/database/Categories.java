package metal.helloword.data.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by smetalnikov on 06.02.2015.
 */
public class Categories extends BaseTable {
    public static final String TABLE_NAME = "categories";

    public static final String FIELD_NAME = "name";

    public static final String FIELD_PRIORITY = "priority";

    public static final String FIELD_HIT_COUNTS = "hit_counts";

    public static final String SQL_INSERT_VALUES = "INSERT INTO " +
            TABLE_NAME + " VALUES (null, '%1$s', 0, 0);";

    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
            + TABLE_NAME;

    /**
     * Берет по умолчанию базу из текущего контекста приложения.
     */
    public Categories(){
        super();
    }

    public Categories(SQLiteDatabase db){
        super(db);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getCreateFields() {
        return FIELD_NAME + " VARCHAR(1000)," +
            FIELD_PRIORITY + " INTEGER," +
            FIELD_HIT_COUNTS + " INTEGER, ";
    }

    @Override
    public void createTable() {
        super.createTable();

        query(String.format(SQL_INSERT_VALUES, "Машина"));

        query(String.format(SQL_INSERT_VALUES, "Продукты"));

        query(String.format(SQL_INSERT_VALUES, "Ремонт"));

        query(String.format(SQL_INSERT_VALUES, "Одежда"));

        query(String.format(SQL_INSERT_VALUES, "Телефон"));

        query(String.format(SQL_INSERT_VALUES, "Подарки"));

        query(String.format(SQL_INSERT_VALUES, "Зарплата"));
    }

    public ArrayList<Category> GetCategories() {
        ArrayList<Category> categories = new ArrayList<>();

        Cursor cursor = GetItems(TABLE_NAME, new String[] {
                _ID, FIELD_NAME, FIELD_PRIORITY, FIELD_HIT_COUNTS }, null, null, null);

        while (cursor.moveToNext()){
            categories.add(new Category(cursor));
        }

        cursor.close();

        return categories;
    }

    public ArrayList<Category> GetByName(String name){

        ArrayList<Category> categories = new ArrayList<>();

        Cursor cursor = GetItems(TABLE_NAME, new String[] {
                _ID, FIELD_NAME, FIELD_PRIORITY, FIELD_HIT_COUNTS }, FIELD_NAME + " LIKE ?", new String[] { "%"+ name + "%" }, null);

        while (cursor.moveToNext()){
            categories.add(new Category(cursor));
        }

        cursor.close();

        return categories;
    }

    public void addCategory(Category category){
        ContentValues cv = new ContentValues();

        cv.put(FIELD_NAME, category.Name);

        cv.put(FIELD_HIT_COUNTS, category.HitCount);

        cv.put(FIELD_PRIORITY, category.Priority);

        InsertItem(TABLE_NAME, cv);
    }
}
