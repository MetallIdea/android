package metal.helloword.views;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EventListener;

import metal.helloword.R;
import metal.helloword.data.AppContext;
import metal.helloword.data.database.Coast;
import metal.helloword.data.database.Coasts;
import metal.helloword.data.database.DBContext;

/**
 * Created by smetalnikov on 03.02.2015.
 */
public class SourceActivity extends Fragment {
    protected ListView listView;

    protected EditText editText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myFragmentView = inflater.inflate(R.layout.source,
                container, false);
        SimpleCursorAdapter adapter = loadData();

        listView = (ListView)myFragmentView.findViewById(R.id.listView);

        listView.setAdapter(adapter);

        editText = (EditText)myFragmentView.findViewById(R.id.editText);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(actionId == EditorInfo.IME_ACTION_DONE)
                {
                    String val = v.getText().toString();

                    if(val != null && !val.isEmpty()) {
                        saveData(Double.parseDouble(val));

                        SimpleCursorAdapter adapter = loadData();

                        listView.setAdapter(adapter);

                        v.setText("");
                    }
                }

                return true;
            }
        });

        return myFragmentView;
    }

    private void saveData(double value){
        Coasts coasts = new Coasts(AppContext.Current.DB);

        Coast coast = new Coast();
        coast.Sum = value;
        coast.Category = "Test";
        coast.DateCoast = new Date();

        coasts.InsertCoast(coast);
    }

    private SimpleCursorAdapter loadData() {
        try {
            Coasts coasts = new Coasts(AppContext.Current.DB);

            Cursor cursor = coasts.GetCoasts();

            return new SimpleCursorAdapter(getActivity().getApplicationContext(),
                    android.R.layout.simple_list_item_2, cursor,
                    new String[]{Coasts.FIELD_DATE, Coasts.FIELD_SUM},
                    new int[]{android.R.id.text1, android.R.id.text2}, 0);

            //cursor.close();
        }catch (Exception e){
            Log.w("LOG_TAG", e.getMessage());
            return null;
        }
    }
}
