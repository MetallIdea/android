package metal.helloword.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import metal.helloword.R;

/**
 * ����
 */
public class BuyFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // ��������� �������� �� ���
        View myFragmentView = inflater.inflate(R.layout.buy,
                container, false);

        // �������� ���������
        Bundle parameters = getArguments();

        return myFragmentView;
    }
}
