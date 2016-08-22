package onionsss.it.mytab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<VipFragment> Pagelist;
    private MyTab mIndicator;
    private ViewPager mVp;
    private ArrayList<String> list;
    private FragmentPagerAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIndicator = (MyTab) findViewById(R.id.indicator);
        mVp = (ViewPager) findViewById(R.id.vp);
        list = new ArrayList<>();
        Pagelist = new ArrayList<>();
        list.add("短信");
        list.add("新闻");
        list.add("广播");
        list.add("短信2");
        list.add("新闻2");
        list.add("广播2");
        list.add("短信3");
        list.add("新闻3");
        list.add("广播3");

        for (int i = 0; i < list.size(); i++) {
            VipFragment instance = VipFragment.getInstance(list.get(i));
            Pagelist.add(instance);
        }

        myAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Fragment getItem(int position) {
                return Pagelist.get(position);
            }
        };

        mVp.setAdapter(myAdapter);
        mIndicator.setTitles(list);
        mIndicator.attachViewPager(mVp);

    }
}
