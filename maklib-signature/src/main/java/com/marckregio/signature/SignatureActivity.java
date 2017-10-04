package com.marckregio.signature;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.marckregio.providers.Files;
import com.marckregio.ui.FontAwesome;
import com.simplify.ink.InkView;

public class SignatureActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView title;
    private InkView inkView;
    private TextView delete, done;
    private static OnSignatureCreatedListener listener;

    public static final String REQUEST_ID = "request_id";
    private String filename = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FontAwesome.apply();
        setContentView(R.layout.activity_signature);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            filename = bundle.getString(REQUEST_ID);
        }
        if (filename.equals("")){
            filename = System.currentTimeMillis() + "";
        }

        title = (TextView) findViewById(R.id.title);
        title.setText("Sign for " + filename);


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

    private void saveImage(Bitmap bitmap){
        Files.setOUTPUT("SIGNATURES");
        String filePath = Files.saveImage(this.filename, bitmap);
        Log.v("IMAGES", filePath);
        if (listener != null){
            listener.onFinished(filePath);
        }

        finish();
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
