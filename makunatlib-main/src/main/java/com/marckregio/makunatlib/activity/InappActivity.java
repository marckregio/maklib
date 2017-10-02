package com.marckregio.makunatlib.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.marckregio.makunatlib.BaseActivity;
import com.marckregio.makunatlib.Broadcast;
import com.marckregio.makunatlib.inapp.IabBroadcastReceiver;
import com.marckregio.makunatlib.inapp.IabHelper;
import com.marckregio.makunatlib.inapp.IabResult;
import com.marckregio.makunatlib.inapp.InappRequest;
import com.marckregio.makunatlib.inapp.Inventory;
import com.marckregio.makunatlib.inapp.Purchase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by makregio on 22/05/2017.
 */

public abstract class InappActivity extends BaseActivity implements IabBroadcastReceiver.IabBroadcastListener {


    private static final int PURCHASE_REQUEST_CODE = 10001;
    private static final int SUBS_REQUEST_CODE = 10002;
    private static final int RESPONSE_OK = 0;
    public static final String RESPONSE = "response";

    public static final String QUERY = "queryItems";
    public static final String TYPE = "type";
    public static final String JSON = "jsonResult";
    public static final String SUBS_ORDERID = "orderId";
    public static final String SUBS_PRODUCTID = "productId";
    public static final String SUBS_STATE = "purchaseState";
    public static final String SUBS_AUTO = "autoRenewing";
    public static final String PRODUCT_SKUID = "productId";
    public static final String PRODUCT_SKUPRICE = "price";
    public static final String PRODUCT_SKUDESC = "description";

    public static String SAMPLE = "com.marckregio.apitester.producttest";
    public static String SUBS_WEEKLY = "";
    public static String SUBS_MONTHLY = "";
    public static String SUBS_BIMONTHLY = "";
    public static String SUBS_3MONTHS = "";
    public static String SUBS_6MONTHS = "";
    public static String SUBS_YEARLY = "";
    public static String SUBS_SEASONAL = "";

    public static String SUBS_WEEKLY_PRICE = "";
    public static String SUBS_MONTHLY_PRICE = "";
    public static String SUBS_BIMONTHLY_PRICE = "";
    public static String SUBS_3MONTHS_PRICE = "";
    public static String SUBS_6MONTHS_PRICE = "";
    public static String SUBS_YEARLY_PRICE = "";
    public static String SUBS_SEASONAL_PRICE = "";

    IInAppBillingService inAppBillingService;
    public IabHelper inAppHelper;
    public IabBroadcastReceiver inAppBroadcastReceiver = null;

    private ArrayList<String> skuList = new ArrayList<>();

    ServiceConnection inappServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            inAppBillingService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name,
                                       IBinder service) {
            inAppBillingService = IInAppBillingService.Stub.asInterface(service);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isPlayStoreInstalled(this)) {
            Intent serviceIntent =
                    new Intent("com.android.vending.billing.InAppBillingService.BIND");
            serviceIntent.setPackage("com.android.vending");
            bindService(serviceIntent, inappServiceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    public void initBilling(){
        if (isPlayStoreInstalled(this)) {
            inAppHelper = new IabHelper(this, getResources().getString(R.string.INAPP_KEY));

            inAppHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
                @Override
                public void onIabSetupFinished(IabResult result) {
                    if (!result.isSuccess()) return;
                    if (inAppHelper == null) return;

                    Log.v("INAPP", "Success");

                    IntentFilter filter = new IntentFilter(IabBroadcastReceiver.ACTION);
                    registerReceiver(inAppBroadcastReceiver, filter);

                }
            });
        }
    }

    public void initBilling(ArrayList<String> skuList){
        if (isPlayStoreInstalled(this)) {
            inAppHelper = new IabHelper(this, getResources().getString(R.string.INAPP_KEY));

            this.skuList.addAll(skuList);

            inAppHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
                @Override
                public void onIabSetupFinished(IabResult result) {
                    if (!result.isSuccess()) return;
                    if (inAppHelper == null) return;

                    Log.v("INAPP", "Success");
                    IntentFilter filter = new IntentFilter(IabBroadcastReceiver.ACTION);
                    registerReceiver(inAppBroadcastReceiver, filter);


                    queryAllItems();

                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void queryAllItems(){
        if (inAppHelper == null) return;

        try {
            new InappRequest(this, inAppBillingService, skuList, IabHelper.ITEM_TYPE_INAPP) {
                @Override
                protected void onPostExecute(Boolean aBoolean) {
                    if (aBoolean) {
                        int response = skuDetails.getInt(IabHelper.RESPONSE_CODE);
                        if (response == 0) {
                            ArrayList<String> responseList = skuDetails.getStringArrayList(IabHelper.RESPONSE_GET_SKU_DETAILS_LIST);
                            for (String item : responseList) {
                                try {
                                    JSONObject jsonObject = new JSONObject(item);
                                    JSONObject jsonReturn = new JSONObject();
                                    jsonReturn.put(TYPE, QUERY);
                                    jsonReturn.put(JSON, jsonObject);
                                    sendInAppBroadcast(true, jsonReturn);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                        }
                    }
                }
            }.execute();
        } catch (Throwable e){
            e.printStackTrace();
        }
        try {
            new InappRequest(this, inAppBillingService, skuList, IabHelper.ITEM_TYPE_SUBS) {
                @Override
                protected void onPostExecute(Boolean aBoolean) {
                    if (aBoolean) {
                        int response = skuDetails.getInt(IabHelper.RESPONSE_CODE);
                        if (response == 0) {
                            ArrayList<String> responseList = skuDetails.getStringArrayList(IabHelper.RESPONSE_GET_SKU_DETAILS_LIST);
                            for (String item : responseList) {
                                try {
                                    JSONObject jsonObject = new JSONObject(item);
                                    JSONObject jsonReturn = new JSONObject();
                                    jsonReturn.put(TYPE, QUERY);
                                    jsonReturn.put(JSON, jsonObject);
                                    sendInAppBroadcast(true, jsonReturn);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                        }
                    }
                }
            }.execute();
        } catch (Throwable e){
            e.printStackTrace();
        }

        queryInventory();
    }



    public void queryInventory(){
        if (inAppHelper == null) return;

        try {
            inAppHelper.queryInventoryAsync(true, skuList, QueryInventoryListener);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void buyItem(String sku){
        if (inAppHelper == null) return;

        try {
            inAppHelper.launchPurchaseFlow(this, sku, PURCHASE_REQUEST_CODE, PurchaseListener, getDeveloperPayload());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void subscribeItem(String sku){
        if (inAppHelper == null) return;

        try {
            inAppHelper.launchSubscriptionPurchaseFlow(this, sku, SUBS_REQUEST_CODE, PurchaseListener);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    IabHelper.QueryInventoryFinishedListener QueryInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        @Override
        public void onQueryInventoryFinished(IabResult result, Inventory inv) {
            if (result.isFailure()) return; // NO PURCHASES

            JSONObject purchaseObject = new JSONObject();
            if (result.getResponse() == RESPONSE_OK) {
                for (String sku : skuList){
                    boolean purchaseProd = inv.hasPurchase(sku);
                    JSONObject prodJson = new JSONObject();
                    if (purchaseProd){
                        try {
                            if (sku.equals(SUBS_MONTHLY) || sku.equals(SUBS_6MONTHS) || sku.equals(SUBS_YEARLY)){
                                purchaseObject.put(TYPE, IabHelper.ITEM_TYPE_SUBS);
                                prodJson.put(PRODUCT_SKUID, sku);
                                purchaseObject.put(JSON, prodJson);
                            } else {
                                purchaseObject.put(TYPE, IabHelper.ITEM_TYPE_INAPP);
                                prodJson.put(PRODUCT_SKUID, sku);
                                purchaseObject.put(JSON, prodJson);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    sendInAppBroadcast(true, purchaseObject);
                }


            }

        }
    };

    IabHelper.OnIabPurchaseFinishedListener PurchaseListener = new IabHelper.OnIabPurchaseFinishedListener() {
        @Override
        public void onIabPurchaseFinished(IabResult result, Purchase info) {
            JSONObject purchaseJson = new JSONObject();
            if (result.isFailure()) {
                try {
                    purchaseJson.put(IabHelper.RESPONSE_CODE, result.getResponse());
                    purchaseJson.put(RESPONSE, result.getMessage());
                    sendInAppBroadcast(false, purchaseJson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return;
            }

            try {
                purchaseJson.put(TYPE, info.getItemType());
                purchaseJson.put(JSON, info.getOriginalJson());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            sendInAppBroadcast(true, purchaseJson);
        }

    };


    @Override
    public void receivedBroadcast(Context context, Intent intent) {
    }


    private void sendInAppBroadcast(boolean isSuccess, JSONObject jsonObject){

        Intent inappIntent = new Intent(IabBroadcastReceiver.ACTION);
        inappIntent.putExtra(Broadcast.REFRESH_KEY, isSuccess);
        inappIntent.putExtra(Broadcast.JSON_KEY, jsonObject.toString());
        sendBroadcast(inappIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (inAppHelper == null) return;

        if (!inAppHelper.handleActivityResult(requestCode, resultCode, data)){
            super.onActivityResult(requestCode, resultCode, data);
        } else {

        }
    }


    @Override
    protected void onDestroy() {

        if (inAppBillingService != null){
            unbindService(inappServiceConnection);
            inAppBillingService = null;
        }

        if (inAppHelper != null){
            inAppHelper.disposeWhenFinished();
            inAppHelper = null;
        }

        try{
            unregisterReceiver(inAppBroadcastReceiver);
            inAppBroadcastReceiver = null;
        } catch (Throwable e){
            e.printStackTrace();
        }
        super.onDestroy();
    }

    private String getDeveloperPayload(){
        return UUID.randomUUID().toString();
    }


}
