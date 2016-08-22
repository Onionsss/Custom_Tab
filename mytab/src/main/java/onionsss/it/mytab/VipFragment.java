package onionsss.it.mytab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 作者：张琦 on 2016/8/21 15:00
 */
public class VipFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView tv = new TextView(getActivity());
        Bundle arguments = getArguments();
        tv.setText(arguments.getString("title"));
        tv.setGravity(Gravity.CENTER);
        return tv;
    }

    public static VipFragment getInstance(String title){
        VipFragment vipFragment = new VipFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        vipFragment.setArguments(bundle);
        return vipFragment;
    }
}
