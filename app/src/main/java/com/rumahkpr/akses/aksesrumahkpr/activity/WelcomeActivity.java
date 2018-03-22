package com.rumahkpr.akses.aksesrumahkpr.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rumahkpr.akses.aksesrumahkpr.MainActivity;
import com.rumahkpr.akses.aksesrumahkpr.R;
import com.rumahkpr.akses.aksesrumahkpr.adapter.AdapterWelcomeApps;
import com.rumahkpr.akses.aksesrumahkpr.util.DialogManager;
import com.rumahkpr.akses.aksesrumahkpr.util.UtilStartapps;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout dotLayout;
    private TextView[] dots;
    private int []layout;
    private Button next, skip;
    private UtilStartapps utilStartapps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_apps);

        initXMl();
//        initStatusBar();
        initStartApps();

    }

    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    public void initStartApps() {
        utilStartapps = new UtilStartapps(this);
        if(!utilStartapps.getFirtsTime()){
            launchHome();
        }

        layout = new int[]{
                R.layout.welcome_1, R.layout.welcome_2, R.layout.welcome_3
        };

        addDot(0);
        changeStatusBarColor();

        AdapterWelcomeApps adapterStartApps = new AdapterWelcomeApps(layout, WelcomeActivity.this);
        viewPager.setAdapter(adapterStartApps);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                addDot(position);

                if(position == layout.length - 1){
                    next.setText(getString(R.string.start));
                    skip.setVisibility(View.GONE);
                }else{
                    next.setText(getString(R.string.next));
                    skip.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void addDot(int position) {
        dots = new TextView[layout.length];
        int[]colorActive = getResources().getIntArray(R.array.array_dot_active);
        int[]colorInActive = getResources().getIntArray(R.array.array_dot_inactive);

        dotLayout.removeAllViews();
        for(int a=0; a<layout.length; a++){
            dots[a] = new TextView(this);
            dots[a].setText(Html.fromHtml("&#9679;"));
            dots[a].setTextSize(15);
            dots[a].setTextColor(colorInActive[0]);
            dotLayout.addView(dots[a]);
        }

        if(dots.length > 0)
            dots[position].setTextColor(colorActive[0]);
    }

    private void launchHome() {
        utilStartapps.setFirtsTimeLaund(false);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void initXMl() {
        viewPager = (ViewPager)findViewById(R.id.pagerWelcome);
        dotLayout = (LinearLayout)findViewById(R.id.dotWelcome);
        next = (Button)findViewById(R.id.bt_next);
        skip = (Button)findViewById(R.id.bt_skip);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchHome();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getItem(+1);
                if(position<layout.length){
                    viewPager.setCurrentItem(position);
                }else{
                    launchHome();
                }
            }
        });

    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() +i;
    }

    @Override
    public void onBackPressed() {

        if(viewPager.getCurrentItem() == 2){
            viewPager.setCurrentItem(1);
        }else if(viewPager.getCurrentItem() == 1){
            viewPager.setCurrentItem(0);
        }else {
            new DialogManager().AlertOKNO(this, getResources().getString(R.string.exit), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            System.exit(0);
                        }
                    },
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        if (hasFocus) {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}

