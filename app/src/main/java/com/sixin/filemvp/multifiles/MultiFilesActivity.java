package com.sixin.filemvp.multifiles;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;

import com.sixin.filemvp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MultiFilesActivity extends AppCompatActivity {

    private Unbinder mUnbinder;

    @BindView(R.id.tab_multi_files)
    TabLayout mTabMultiFiles;

    @BindView(R.id.vp_multi_files)
    ViewPager mVPMultiFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_files);
        mUnbinder = ButterKnife.bind(this);
        init();
    }

    private void init() {
        String[] titles = new String[]{"txt", "png"};

        for (String title : titles) {
            mTabMultiFiles.addTab(mTabMultiFiles.newTab());
        }

        MultiFilesAdapter adapter = new MultiFilesAdapter(titles, getSupportFragmentManager());
        mVPMultiFiles.setAdapter(adapter);
        mTabMultiFiles.setupWithViewPager(mVPMultiFiles);

        for (int i = 0 ; i < titles.length ; i++) {
            mTabMultiFiles.getTabAt(i).setText(titles[i]);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }
}
