package com.example.anurag.conducive;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;

public class ViewPagerSwipe extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_swipe);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        DisplayMetrics dm =new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        getWindow().setLayout((int) (width), (int) (height * .5));
        getWindow().setGravity(Gravity.BOTTOM);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

         fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentItem = mViewPager.getCurrentItem();
                if (currentItem==0)
                    fab.setImageResource(android.R.drawable.ic_media_next);
                else
                    fab.setImageResource(android.R.drawable.ic_media_previous);
                Snackbar.make(view, String.valueOf(mViewPager.getCurrentItem()), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mViewPager.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int currentItem = mViewPager.getCurrentItem();
                if (currentItem==0)
                    fab.setImageResource(android.R.drawable.ic_media_next);
                else
                    fab.setImageResource(android.R.drawable.ic_media_previous);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_pager_swipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private ProgressBar progressBar;
        private FloatingActionButton fab;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_view_pager_swipe, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            fab=(FloatingActionButton)getActivity().findViewById(R.id.fab);
            progressBar=(ProgressBar)getActivity().findViewById(R.id.progressBar);
            int count=getArguments().getInt(ARG_SECTION_NUMBER);
            ClipboardManager clipboard = (ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData.Item item= clipboard.getPrimaryClip().getItemAt(0);
            String word=item.getText().toString();
            final WebView web= (WebView)rootView.findViewById(R.id.webview);
//            web.getSettings().setSupportMultipleWindows(true);
            web.getSettings().setAppCacheEnabled(true);
            web.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
            web.getSettings().setJavaScriptEnabled(true);
            web.setWebViewClient(new WebViewClient() {
                ProgressDialog progressDialog;

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    super.shouldOverrideUrlLoading(view, url);
                    Log.d("I am Called", url);
                    web.loadUrl(url);
                    return true;
                }
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    Log.d("VIEW PAGER", url);
                    fab.setVisibility(View.GONE);
//                    progressDialog = ProgressDialog.show(getActivity(), "Loading", "Please Wait.. ", true);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            (view, url);
//                    progressDialog.dismiss();
                    fab.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            });
            if(count==1) {
                fab.setImageResource(android.R.drawable.ic_media_previous);
                if (word != null && word.length() < 19) {

                    String url = "https://www.google.co.in/search?q=define+" + word + "+&aqs=chrome..69i57j0l3.2820j0j4&sourceid=chrome-mobile&ie=UTF-8";
                    web.loadUrl(url);

                }
            }
            else {
                fab.setImageResource(android.R.drawable.ic_media_next);
                if (word != null && word.length() < 19) {
                    String url = "https://www.google.co.in/search?site=webhp&tbm=isch&source=hp&ei=bt2ZVtXLA4yIuATwtpDABg&q=" +
                            word + "&oq=" + word + "&gs_l=mobile-gws-hp.1.0.0l4j5.15777.17264.0.18312.10.9.0.1.1.0.183.1107.0j7.7.0....0...1c.1j4.64.mobile-gws-hp..2.7.1106.0.Opd6S1UD92g";
                    web.loadUrl(url);
                }
            }
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
            }
            return null;
        }
    }
}
