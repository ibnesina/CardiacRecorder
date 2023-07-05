package com.example.cardiacrecorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreenActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private int progress;
    ImageView sp_image;
    TextView sp_text,sp_text2;
    Animation Splash_top,Splash_bottom ,Splash_RL;

    /**
     * Called when the activity is starting.
     *      Performs initialization tasks and sets up the splash screen UI.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        sp_image=findViewById(R.id.splash_image);
        sp_text=findViewById(R.id.splash_text);
        sp_text2=findViewById(R.id.splash_text2);

        //animation

        Splash_top = AnimationUtils.loadAnimation(this, R.anim.splash_top);
        Splash_bottom = AnimationUtils.loadAnimation(this, R.anim.splash_bottom);
        Splash_RL=AnimationUtils.loadAnimation(this, R.anim.right_to_left);
        sp_image.setAnimation(Splash_top);
        sp_text.setAnimation(Splash_RL);
        sp_text2.setAnimation(Splash_bottom);

        //animation to image and text


        progressBar=findViewById(R.id.progress_bar);
        //Toast.makeText(SplashScreenActivity.this,"Welcome to CardiacRecorder",Toast.LENGTH_SHORT).show();
        Thread thread =new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
                statApp();
            }
        });
        thread.start();
    }

    /**
     * Performs the background work for the splash screen.
     * Updates the progress bar in increments of 20% at a fixed interval.
     */
    public void doWork(){
        for (progress = 0; progress <= 100; progress = progress + 20) {
            try {
                Thread.sleep(500);
                progressBar.setProgress(progress);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Checks if a user is already authenticated.
     *      If not, it starts the registration activity.
     *      If the user is authenticated, it starts the main activity.
     */
    public void statApp()
    {
        if(FirebaseAuth.getInstance().getCurrentUser()==null) {
            Intent intent=new Intent(SplashScreenActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            finish();
        }
    }
}