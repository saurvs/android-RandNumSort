package com.omnify.hire.randnumsort;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;

public class MergeSortService extends IntentService {
    public static final String ACTION_SORT = "com.omnify.hire.randnumsort.action.MSORT";
    public static final String BROADCAST_ACTION_SORTED =
            "omnify.hire.randnumsort.broadcast.action.MSORTED";
    public static final String SORTED_LIST_PARAM = "com.omnify.hire.randnumsort.extra.MSORTED_LIST";

    public MergeSortService() {
        super("MergeSortService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SORT.equals(action)) {
                ArrayList<Integer> list =
                        intent.getIntegerArrayListExtra(GeneratorFragment.LIST_PARAM);

                final MergeSort mergeSort = new MergeSort();
                mergeSort.sort(list);

                Intent broadcastIntent = new Intent(BROADCAST_ACTION_SORTED);
                broadcastIntent.putIntegerArrayListExtra(SORTED_LIST_PARAM, list);

                LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
            }
        }
    }
}
