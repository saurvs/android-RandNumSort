package com.omnify.hire.randnumsort;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GeneratorFragment.OnSortedListener
{
    private GeneratorFragment generatorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            return;
        }

        generatorFragment = new GeneratorFragment();
        generatorFragment.setOnSortedListener(this);
        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, generatorFragment).commit();
        getSupportActionBar().setTitle(R.string.gen_frag_title);
    }

    public void onSorted(ArrayList<Integer> mSortedList, ArrayList<Integer> qSortedList) {
        if (!isFinishing() && !isDestroyed()) {
            SortedFragment sortedFragment = SortedFragment.newInstance(mSortedList, qSortedList);
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, sortedFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.sort_frag_title);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, generatorFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(R.string.gen_frag_title);
        return true;
    }
}
