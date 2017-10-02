package com.marckregio.firebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthActivity extends AppCompatActivity{

    private FirebaseAuth fbAuth;
    private FirebaseUser fbUser;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fbAuth = FirebaseAuth.getInstance();
        fbUser = FirebaseAuth.getInstance().getCurrentUser();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (fbUser == null){
                    Log.v("FIREBASE", "--Logout");
                }
            }
        };

        //register("marckregio@gmail.com", "makunat10");
        //signIn("marckregio@gmail.com", "makunat10");
    }

    public boolean checkUserSession(){
        if (fbAuth.getCurrentUser() != null){
            return true;
        } else {
            return false;
        }
    }

    public void signIn(String email, String password){
        fbAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.v("FIREBASE", "--Login");
                        } else {
                            Log.v("FIREBASE", "--NO User matched");
                        }
                    }
                }
        );
    }

    public void register(String email, String password){
        fbAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.v("FIREBASE", "--Registered");
                        } else {
                            Log.v("FIREBASE", "--User Exists");
                        }
                    }
                }
        );
    }

    public void sendResetEmail(String email){
        fbAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.v("FIREBASE", "--Email Sent");
                        }
                    }
                }
        );
    }

    public void changeEmail(String newEmail){
        if(fbUser != null){
            fbUser.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.v("FIREBASE", "--Email Changed");
                }
            });
        }
    }

    public void changePassword(String newPassword){
        if (fbUser != null){
            fbUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Log.v("FIREBASE", "--Password Changed");
                    }
                }
            });
        }
    }

    public void signOut(){
        fbAuth.signOut();

    }

    @Override
    protected void onStart() {
        super.onStart();
        fbAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null){
            fbAuth.removeAuthStateListener(authStateListener);
        }
    }
}
