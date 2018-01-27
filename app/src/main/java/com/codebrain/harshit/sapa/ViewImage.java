package com.codebrain.harshit.sapa;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.jsibbold.zoomage.ZoomageView;


import java.util.ArrayList;

public class ViewImage extends AppCompatActivity {
    String url;
    ZoomageView zoomageView;
    ViewPager viewPager;
    Context context;
    private LayoutInflater inflater;
    ArrayList<String> imgs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

       // photoView = (PhotoView)findViewById(R.id.img);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        context = getApplicationContext();

        int pos = Integer.parseInt(getIntent().getExtras().getString("id"));
        imgs = getIntent().getExtras().getStringArrayList("image");
        viewPager.setAdapter(new adapter(imgs));
        viewPager.setCurrentItem(pos);
       // photoView.setImageResource();
    }


    public class adapter extends PagerAdapter {
        ArrayList<String> imgs;

        adapter(ArrayList<String> imgs){
            this.imgs = imgs;
        }

        @Override
        public int getCount() {
            return imgs.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (RelativeLayout) object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ZoomageView zoomageView;
            View viewLayout = inflater.inflate(R.layout.imagelayout,container,false);

            zoomageView = viewLayout.findViewById(R.id.imgview1);

            Glide.with(context).load(imgs.get(position)).into(zoomageView);

                    ((ViewPager)container).addView(viewLayout);
             return viewLayout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((RelativeLayout) object);
        }
    }
}
