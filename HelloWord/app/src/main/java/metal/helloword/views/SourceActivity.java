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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

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
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                if (cs.toString().length() == 0)
                    editText.setText(cs.toString() + arg1 + arg2 + arg3);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }

        });

        return myFragmentView;
    }

    private SimpleCursorAdapter loadData() {
        try {
            Coasts coasts = new Coasts(AppContext.Current.DB);

            Cursor cursor = coasts.GetCoasts();

            return new SimpleCursorAdapter(getActivity().getApplicationContext(),
                    android.R.layout.simple_list_item_2, cursor,
                    new String[]{Coasts._ID, Coasts.FIELD_SUM},
                    new int[]{android.R.id.text1, android.R.id.text2}, 0);

            //cursor.close();
        }catch (Exception e){
            Log.w("LOG_TAG", e.getMessage());
            return null;
        }
    }
}
