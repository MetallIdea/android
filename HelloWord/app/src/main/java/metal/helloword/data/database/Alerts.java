package metal.helloword.data.database;

/**
 * Created by Ленуська on 29.02.2016.
 */
public class Alerts extends BaseTable {
    public static final String TABLE_NAME = "alerts";

    public static final String FIELD_NAME = "name";

    public static final String FIELD_BUY = "buy";

    public static final String FIELD_CATEGORIES = "categories";

    public static final String FIELD_DATE_START = "date_start";

    public static final String FIELD_CONTACT = "contact";

    public static final String FIELD_PLACE = "place";

    public static final String FIELD_DATE_END = "date_end";

    public static final String FIELD_PERIOD = "period";

    public static final String FIELD_DAYS_OF_WEEK = "days_of_week";

    public static final String SQL_INSERT_VALUES = "INSERT INTO " +
            TABLE_NAME + " VALUES (null, '%1$s', 0, 0);";

    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
            + TABLE_NAME;

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getCreateFields() {
        return null;
    }
}
