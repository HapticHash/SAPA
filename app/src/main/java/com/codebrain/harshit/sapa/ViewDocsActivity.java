package com.codebrain.harshit.sapa;

import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import java.util.List;

public class ViewDocsActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String SAMPLE_FILE = "SIH2018.pdf";
    PDFView pdfView;
    private int position = 0;
    Integer pageNumber = 0;
    String pdfFileName,url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_docs);


//        pdfView= (PDFView)findViewById(R.id.pdfView);

        int pos = Integer.parseInt(getIntent().getExtras().getString("id"));
        url = getIntent().getExtras().getString("docs");
        String doc="<iframe src='iframe src='http://docs.google.com/viewer?url="+url+"&embedded=true' width='100%' height='100%' style='border: none;'></iframe>";
        WebView  wv = (WebView)findViewById(R.id.webView);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setAllowFileAccess(true);

//        wv.loadData( doc , "text/html",  "UTF-8");
        wv.loadUrl(url);
//       Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//        startActivity(browserIntent);

        Log.d("abc",url+"");
//        displayFromAsset(url);

    }

}
