package metal.helloword.views;

import android.content.Entity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.ArrayMap;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import metal.helloword.MainActivity;
import metal.helloword.R;
import metal.helloword.data.AppContext;
import metal.helloword.data.database.Coast;
import metal.helloword.data.database.Coasts;

/**
 * Created by smetalnikov on 03.02.2015.
 */
public class SourceActivity extends Fragment {

    public static final SimpleDateFormat DATA_FORMAT_VIEW = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    protected CoastsLoad coastLoadTask;

    protected SimpleAdapter adapter;

    protected ListView listView;

    protected EditText editText;

    /**
     * Инициализирует элементы. Загружает занчения.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myFragmentView = inflater.inflate(R.layout.source,
                container, false);

        listView = (ListView)myFragmentView.findViewById(R.id.listView);
        listView.setTextFilterEnabled(true);

        editText = (EditText)myFragmentView.findViewById(R.id.editText);


        // Обработка нажатия клавиши Done (Готово). После которой происходит отсылка результатов.
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String val = editText.getText().toString();

                    if(val != null && !val.isEmpty()){

                        saveValue(Double.parseDouble(val));

                        loadData();

                        listView.setAdapter(adapter);

                        editText.setText("");

                        editText.requestFocus();
                    }
                }

                return true;
            }
        });

        return myFragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();

        coastLoadTask = new CoastsLoad();
        coastLoadTask.execute();
    }

    @Override
    public void onResume() {
        super.onResume();

        editText.requestFocus();
    }

    private void loadData() {
        try {
            Coasts coasts = new Coasts();

            Cursor cursor = coasts.GetCoasts();

            ArrayList<HashMap<String, String>> coastList = new ArrayList();


            while (cursor.moveToNext()){

                HashMap<String, String> coastMap = new HashMap();

                Coast coast = new Coast(cursor);

                coastMap.put("Value", SourceActivity.DATA_FORMAT_VIEW.format(coast.DateCoast) + " " + coast.Sum);
                coastMap.put("Category", coast.Category);

                coastList.add(coastMap);
            }

            cursor.close();

            adapter = new SimpleAdapter(getActivity().getApplicationContext(), coastList,
                    android.R.layout.simple_list_item_2,
                    new String[]{"Value", "Category"},
                    new int[]{android.R.id.text1, android.R.id.text2});


        }catch (Exception e){
            Log.w("LOG_TAG", e.getMessage());
        }
    }

    private void saveValue(double value)
    {
        Coast coast = new Coast();
        coast.Sum = value;
        coast.Category = "Test";
        coast.DateCoast = new Date();

        Coasts coasts = new Coasts();

        coasts.InsertCoast(coast);
    }

    class CoastsLoad extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                loadData();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            listView.setAdapter(adapter);
        }
    }
}