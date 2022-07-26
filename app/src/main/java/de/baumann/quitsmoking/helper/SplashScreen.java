package de.baumann.quitsmoking.helper;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import de.baumann.quitsmoking.MainActivity;
import de.baumann.quitsmoking.R;

@SuppressWarnings("ALL")
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread thread = new Thread(){
            @Override
            public void run(){
                try{
                    sleep(2500);
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                }catch (Exception e){

                }
            }
        }; thread.start();

    }
}