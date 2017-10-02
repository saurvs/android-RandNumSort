package com.omnify.hire.randnumsort;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;

public class QuickSortService extends IntentService {
    public static final String ACTION_SORT = "com.omnify.hire.randnumsort.action.QSORT";
    public static final String BROADCAST_ACTION_SORTED =
            "omnify.hire.randnumsort.broadcast.action.QSORTED";
    public static final String SORTED_LIST_PARAM = "com.omnify.hire.randnumsort.extra.QSORTED_LIST";

    public QuickSortService() {
        super("QuickSortService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SORT.equals(action)) {
                ArrayList<Integer> list =
                        intent.getIntegerArrayListExtra(GeneratorFragment.LIST_PARAM);

                final QuickSort quickSort = new QuickSort();
                quickSort.sort(list);

                Intent broadcastIntent = new Intent(BROADCAST_ACTION_SORTED);
                broadcastIntent.putIntegerArrayListExtra(SORTED_LIST_PARAM, list);

                LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
            }
        }
    }
}
