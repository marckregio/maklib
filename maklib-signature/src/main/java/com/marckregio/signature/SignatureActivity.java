package com.marckregio.signature;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.marckregio.providers.Files;
import com.marckregio.providers.database.ContentContract;
import com.marckregio.ui.FontAwesome;
import com.simplify.ink.InkView;

public class SignatureActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView title;
    private InkView inkView;
    private TextView delete, done;
    private static OnSignatureCreatedListener listener;

    public static String BASE_FOLDER = "SIGNS/";
    public static final String REQUEST_ID = "request_id";
    private String filename = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FontAwesome.apply();
        setContentView(R.layout.activity_signature);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            filename = "sign." + bundle.getString(REQUEST_ID);
        }
        if (filename.equals("")){
            filename = "sign." + System.currentTimeMillis() + "";
        }

        title = (TextView) findViewById(R.id.title);
        title.setText("File: " + filename);


        inkView = (InkView) findViewById(R.id.ink);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            inkView.setColor(getResources().getColor(R.color.ink, null));
        } else {
            inkView.setColor(getResources().getColor(R.color.ink));
        }
        inkView.setMinStrokeWidth(1.5f);
        inkView.setMaxStrokeWidth(3f);

        delete = (TextView) findViewById(R.id.delete);
        delete.setOnClickListener(this);
        done = (TextView) findViewById(R.id.done);
        done.setOnClickListener(this);


    }

    public static void setSignatureListener(OnSignatureCreatedListener l){
        listener = l;
    }

    private void saveImage(Bitmap bitmap) {
        String filePath = Files.saveImage(this.filename, bitmap);
        Log.v("IMAGES", filePath);
        if (listener != null) {
            listener.onFinished(filePath);
        }

        ContentValues values = new ContentValues();
        values.put(ContentContract.COLUMN_NAME, filename);
        values.put(ContentContract.COLUMN_VALUE, filePath);

        Uri insert = getContentResolver().insert(ContentContract.CONTENT_URI, values);
        Log.v("DATABASE", insert.getPath());

        Cursor cursor = getContentResolver().query(ContentContract.CONTENT_URI, null, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++){
                Log.v("DATABASE", cursor.getString(cursor
                        .getColumnIndex(ContentContract.COLUMN_NAME)));
                cursor.moveToNext();
            }

            cursor.close();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == delete){
            inkView.clear();
        } else if (view == done){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                saveImage(inkView.getBitmap(getResources().getColor(R.color.white, null)));
            } else {
                saveImage(inkView.getBitmap(getResources().getColor(R.color.white)));
            }
        }
    }
}
