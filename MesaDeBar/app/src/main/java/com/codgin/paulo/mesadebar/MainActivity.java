package com.codgin.paulo.mesadebar;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.codgin.paulo.mesadebar.Model.User;
import com.codgin.paulo.mesadebar.Service.FirebaseService;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    TextView txtStatus;
    LoginButton loginButton;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callbackManager = CallbackManager.Factory.create();
        txtStatus = (TextView) findViewById(R.id.txtStatus);
        loginButton = (LoginButton) findViewById(R.id.login_button);

     /*   try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.codgin.paulo.mesadebar",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }*/
        if (isLoggedIn()) {
            Profile profile = Profile.getCurrentProfile();
            txtStatus.setText("ENTROU??????W ");
            Intent intentListaMesa = new Intent(MainActivity.this, ListaMesa.class);
            intentListaMesa.putExtra("profile", profile);
            startActivity(intentListaMesa);
        } else {
            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Profile profile = Profile.getCurrentProfile();
                    FirebaseService firebaseService = new FirebaseService();
                    //txtStatus.setText("ENTROU??????W ");
                    User user = new User(profile.getId(), profile.getFirstName());
                    firebaseService.criarUsuarioFirebase(user);
                    Intent intentListaMesa = new Intent(MainActivity.this, ListaMesa.class);
                    intentListaMesa.putExtra("profile", profile);
                    startActivity(intentListaMesa);
                }

                @Override
                public void onCancel() {
                    Toast.makeText(MainActivity.this, "cancelou?", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(FacebookException error) {
                    Log.d("qual foi o erro?", error.toString());
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }
}
