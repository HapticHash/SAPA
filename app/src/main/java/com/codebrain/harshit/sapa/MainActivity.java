package com.codebrain.harshit.sapa;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.MenuItem;
import android.widget.TextView;

import com.robertsimoes.quoteable.QuotePackage;
import com.robertsimoes.quoteable.Quoteable;

public class MainActivity extends AppCompatActivity implements Quoteable.ResponseReadyListener  {

    static String Quote,Author, QuoteF, AuthorF;
     Context ctx;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_photos:
                    fragmentTransaction.replace(R.id.content, new PicsFragment()).commit();
                    return true;
                case R.id.navigation_videos:
                    fragmentTransaction.replace(R.id.content, new VideoFragment()).commit();
                    return true;
                case R.id.navigation_docs:
                    fragmentTransaction.replace(R.id.content, new DocsFragment()).commit();
                    return true;
                case R.id.navigation_quotes:
                    fragmentTransaction.replace(R.id.content, new QuotesFragment()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Quoteable quoteable = new Quoteable(this, "Don’t take rest after your first victory because if you fail in second, more lips are waiting to say that your first victory was just luck.", "Dr. A.P.J. Abdul Kalam");
        quoteable.request();
        /*Explode explode = new Explode();
        getWindow().setExitTransition(explode); */

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, new PicsFragment()).commit();
    }

    @Override
    public void onQuoteResponseReady(QuotePackage quotePackage) {
        Quote = quotePackage.getQuote();
        Author = quotePackage.getAuthor();
    }

    @Override
    public void onQuoteResponseFailed(QuotePackage defaultResponse) {
        QuoteF = defaultResponse.getQuote();
        AuthorF = defaultResponse.getAuthor();
    }

    @Override
    public void onBackPressed() {
       /* super.onBackPressed();
        Intent i = new Intent(MainActivity.this, MainActivity.class);
        startActivity(i);*/

        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.super.onBackPressed();
                    }
                }).create().show();
    }
}
