package com.marckregio.makunatlib.inapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;

import com.android.vending.billing.IInAppBillingService;

import java.util.ArrayList;


/**
 * Created by makregio on 15/05/2017.
 */

public class InappRequest extends AsyncTask<Object, Integer, Boolean> {

    public Bundle skuDetails;
    private IInAppBillingService inAppBillingService;
    private ArrayList<String> skuList;
    private Context context;
    private String type;

    public InappRequest(Context context, IInAppBillingService inAppBillingService, ArrayList<String> skuList, String type){
        this.inAppBillingService = inAppBillingService;
        this.skuList = skuList;
        this.context = context;
        this.type = type;
    }

    @Override
    protected Boolean doInBackground(Object... params) {
        Bundle querySkus = new Bundle();
        querySkus.putStringArrayList(IabHelper.GET_SKU_DETAILS_ITEM_LIST, skuList);
        try {
            skuDetails = inAppBillingService.getSkuDetails(3, context.getPackageName() , type, querySkus);
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}
