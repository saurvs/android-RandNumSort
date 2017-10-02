package com.omnify.hire.randnumsort;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class GeneratorFragment extends Fragment {
    private OnSortedListener listener;

    public static final String LIST_PARAM = "com.omnify.hire.randnumsort.extra.LIST";

    private static final Integer LIST_COUNT = 40;
    private static final Integer RAND_MIN = 10;
    private static final Integer RAND_MAX = 99;

    private ArrayAdapter arrayAdapter;
    private ArrayList<Integer> qSortedList;
    private ArrayList<Integer> mSortedList;

    public GeneratorFragment() {}

    private void generateRandomNumbers() {
        arrayAdapter.clear();
        for (int i = 0; i < LIST_COUNT; i++) {
            arrayAdapter.add(10 + (int) (Math.random()*(RAND_MAX - RAND_MIN)));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_generator, container, false);

        if (arrayAdapter == null) {
            arrayAdapter = new ArrayAdapter(getActivity(), R.layout.list_item, new ArrayList());
            generateRandomNumbers();
        }

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ListView listView = view.findViewById(R.id.unsortedListView);
        listView.setAdapter(arrayAdapter);

        IntentFilter qSortedIntentFilter =
                new IntentFilter(QuickSortService.BROADCAST_ACTION_SORTED);
        IntentFilter mSortedintentFilter =
                new IntentFilter(MergeSortService.BROADCAST_ACTION_SORTED);

        QSortBroadcastReceiver qSortBroadcastReceiver = new QSortBroadcastReceiver();
        MSortBroadcastReceiver mSortBroadcastReceiver = new MSortBroadcastReceiver();

        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(qSortBroadcastReceiver, qSortedIntentFilter);
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(mSortBroadcastReceiver, mSortedintentFilter);

        view.findViewById(R.id.gen_button).setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    genButtonClicked(v);
                }
            }
        );
        view.findViewById(R.id.sort_button).setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sortButtonClicked(v);
                }
            }
        );
    }

    public void genButtonClicked(View view) {
        generateRandomNumbers();
    }

    public void sortButtonClicked(View view) {
        mSortedList = null;
        qSortedList = null;

        ArrayList list = new ArrayList(arrayAdapter.getCount());
        for (int i = 0; i < arrayAdapter.getCount(); i++) {
            list.add(arrayAdapter.getItem(i));
        }

        Intent qSortIntent = new Intent(getActivity(), QuickSortService.class);
        qSortIntent.setAction(QuickSortService.ACTION_SORT);
        qSortIntent.putIntegerArrayListExtra(LIST_PARAM, list);
        getActivity().startService(qSortIntent);

        Intent mSortIntent = new Intent(getActivity(), MergeSortService.class);
        mSortIntent.setAction(MergeSortService.ACTION_SORT);
        mSortIntent.putIntegerArrayListExtra(LIST_PARAM, list);
        getActivity().startService(mSortIntent);
    }

    private class QSortBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            qSortedList = intent
                    .getIntegerArrayListExtra(QuickSortService.SORTED_LIST_PARAM);
            if (listener != null && mSortedList != null){
                listener.onSorted(mSortedList, qSortedList);
            }
        }
    }

    private class MSortBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            mSortedList = intent
                    .getIntegerArrayListExtra(MergeSortService.SORTED_LIST_PARAM);
            if (listener != null && qSortedList != null){
                listener.onSorted(mSortedList, qSortedList);
            }
        }
    }

    public void setOnSortedListener(OnSortedListener l) {
        listener = l;
    }

    public interface OnSortedListener {
        void onSorted(ArrayList<Integer> mSortedList, ArrayList<Integer> qSortedList);
    }
}
