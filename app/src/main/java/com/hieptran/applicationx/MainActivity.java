package com.hieptran.applicationx;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.hieptran.applicationx.common.customview.Blur;
import com.hieptran.applicationx.common.customview.BlurBitmapHelper;
import com.hieptran.applicationx.control.its.FeedControl;
import com.hieptran.applicationx.utils.Utils;
import com.hieptran.applicationx.view.adapter.FeedAdapter;
import com.hieptran.applicationx.view.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AppBarLayout.OnOffsetChangedListener {
    AppBarLayout appbar;

    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;
    private ViewFlipper mViewFlipper;
    private android.os.Handler hand;
    private  TabLayout tabLayout;
    final int[]  locations = new int[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (android.os.Build.VERSION.SDK_INT >= 11) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        }*/
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        appbar = (AppBarLayout) findViewById(R.id.appbar);
        appbar.addOnOffsetChangedListener(this);
        hand = new android.os.Handler();
        mViewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
        // ab = getSupportActionBar();
        // final ActionBar  ab = getSupportActionBar();
        // ab.setHomeAsUpIndicator(R.drawable.ic_menu_manage);
        // ab.setDisplayHomeAsUpEnabled(true);
        // ab.hide();
//        Fragment setting = new HomeFragment();
//        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, setting).commit();
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }
         tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        tabLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                //   drawer.getViewTreeObserver().removeGlobalOnLayoutListener(this);


                tabLayout.getLocationOnScreen(locations);
                int x = locations[0];
                int y = locations[1];
                Log.d("HiepGa", x + "-" + y + "-" + tabLayout.getHeight());
            }
        });



    }
    private int[] getViewLocations(View view) {
        int[] locations = new int[2];
        view.getLocationInWindow(locations);
        return locations;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    Runnable updateFliper = new Runnable() {
        @Override
        public void run() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mViewFlipper.setOutAnimation(inFromLeftAnimation());
                            mViewFlipper.setInAnimation(outToRightAnimation());
                            mViewFlipper.showNext();
                        }
                    });
                }
            }).start();
            hand.postDelayed(updateFliper, 5000);

        }
    };


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    protected void handleMenuSearch() {
        // ActionBar action = getSupportActionBar(); //get the actionbar
        final ActionBar ab = getSupportActionBar();
        if (isSearchOpened) { //test if the search is open

            ab.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            ab.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);

            //add the search icon in the action bar
            //  mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_open_search));

            isSearchOpened = false;
        } else { //open the search entry

            ab.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            ab.setCustomView(R.layout.search_bar);//add the custom view
            ab.setDisplayShowTitleEnabled(false); //hide the title

            edtSeach = (EditText) ab.getCustomView().findViewById(R.id.edtSearch); //the text editor

            //this is a listener to do a search when the user clicks on search button
            edtSeach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        Toast.makeText(MainActivity.this, "Search", Toast.LENGTH_LONG).show();
                        // doSearch();
                        return true;
                    }
                    return false;
                }
            });


            edtSeach.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);


            //add the close icon
            // mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_close_search));

            isSearchOpened = true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            handleMenuSearch();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(appbar), "Trang chủ");
        adapter.addFragment(new HomeFragment(appbar), "Dự Án");
        adapter.addFragment(new HomeFragment(appbar), "Thành viên");
        viewPager.setAdapter(adapter);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
     /*   Bitmap b = BlurBitmapHelper.blurBitmap(this,locations[1],locations[0],getWindow().getDecorView().getWidth(),tabLayout.getHeight());
        Drawable d = new BitmapDrawable(getResources(),b);
        tabLayout.setBackground(d);*/
     //   render(tabLayout);
        final ActionBar ab = getSupportActionBar();
        if (i == 0) {
           // Toast.makeText(MainActivity.this, "On", Toast.LENGTH_LONG).show();
            //  ab = getSupportActionBar();


            ab.hide();
        } else {
           // Toast.makeText(MainActivity.this, "Off", Toast.LENGTH_LONG).show();

            // ab = getSupportActionBar();
            // ab.show();
        }
    }


    private void render(View mView) {


            Bitmap bitmap = Utils.loadBitmapFromView(mView);
             bitmap = scaleBitmap(bitmap);
            bitmap = Blur.fastblur(this, bitmap, 8, false);
            mView.setBackground(new BitmapDrawable(getResources(),bitmap));

        }

    private Bitmap scaleBitmap(Bitmap myBitmap) {

        int width = (int) (myBitmap.getWidth() / 5);
        int height = (int) (myBitmap.getHeight() / 5);

        return Bitmap.createScaledBitmap(myBitmap, width, height, false);
    }




    // for the next movement
    public static Animation inFromLeftAnimation() {
        Animation inFromLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromLeft.setDuration(350);
        inFromLeft.setInterpolator(new AccelerateInterpolator());
        return inFromLeft;
    }

    // for the previous movement
    public static Animation inFromRightAnimation() {

        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(350);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
    }

    public static Animation outToLeftAnimation() {
        Animation outtoLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoLeft.setDuration(350);
        outtoLeft.setInterpolator(new AccelerateInterpolator());
        return outtoLeft;
    }

    public static Animation outToRightAnimation() {
        Animation outtoRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoRight.setDuration(350);
        outtoRight.setInterpolator(new AccelerateInterpolator());
        return outtoRight;
    }

    @Override
    protected void onResume() {
        super.onResume();
        hand.post(updateFliper);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hand.removeCallbacks(updateFliper);
    }
}
