package com.marckregio.firebasemakunat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.marckregio.firebasemakunat.model.SampleModel;


/**
 * Created by eCopy on 9/29/2017.
 */

public class FirebaseDatabaseActivity extends AppCompatActivity {

    private FirebaseDatabase fbDatabase;
    private static boolean enabledPersistence = false;

    private String sample = "users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!enabledPersistence) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true); // enable offline store
            enabledPersistence = true;
        }
        fbDatabase = FirebaseDatabase.getInstance();

        insertValue(new SampleModel("natrishaann@gmail.com", "Natrisha Ann Susim", "09778217638"));
        //updateValueByNode("-KvPSB9RxmXe5JWLO0V5", SampleModel.NAME, "Natrisha Susim");
        //TODO : use authid from Firebase Auth
        //readSingleValue("-KvPPOo39ImcTVrd-PqW");
        //readValues(); //specify parameter as root for iteration/loop
        //removeValue("-KvPSB9RxmXe5JWLO0V5");
    }

    public void insertValue(SampleModel model){
        DatabaseReference dbRef = fbDatabase.getReference(sample);
        //TODO : change insert key, should be the authid acquired from Firebase Auth
        String key = dbRef.push().getKey();
        dbRef.child(key).setValue(model, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Log.v("FIREBASE", "INSERT SUCCESSFUL");
            }
        });
    }

    public void updateValueByKey(String key, SampleModel model){
        DatabaseReference dbRef = fbDatabase.getReference(sample);
        dbRef.child(key).setValue(model, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Log.v("FIREBASE", "UPDATE SUCCESSFUL");
            }
        });
    }
    
    public void updateValueByNode(String userid, String node, String value){
        DatabaseReference dbRef = fbDatabase.getReference(sample);
        dbRef.child(userid).child(node).setValue(value, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Log.v("FIREBASE", "UPDATE SUCCESSFUL");
            }
        });
    }


    public void readSingleValue(String key){
        DatabaseReference dbRef = fbDatabase.getReference(sample);
        dbRef.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SampleModel model = dataSnapshot.getValue(SampleModel.class);
                Log.v("FIREBASE", model.getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("FIREBASE", databaseError.getMessage());
            }
        });
    }

    public void readValues(){
        DatabaseReference dbRef = fbDatabase.getReference(sample);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    SampleModel model = data.getValue(SampleModel.class);
                    Log.v("FIREBASE", model.getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("FIREBASE", databaseError.getMessage());
            }
        });
    }

    public void removeValue(String key){
        DatabaseReference dbRef = fbDatabase.getReference(sample);
        dbRef.child(key).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Log.v("FIREBASE", "REMOVE SUCCESSFUL");
            }
        });
    }
}
