package com.example.wedadabdelkareem.linkdevelopmenttask.view.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.wedadabdelkareem.linkdevelopmenttask.R;
import com.example.wedadabdelkareem.linkdevelopmenttask.service.model.Article;
import com.example.wedadabdelkareem.linkdevelopmenttask.util.Constants;
import com.example.wedadabdelkareem.linkdevelopmenttask.view.callback.ArticleClickCallBack;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ArticleClickCallBack {
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;
    private ActionBar mActionBar;
    private boolean mToolBarNavigationListenerIsRegistered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        /// add fragment
        ArticleListFragment articleListFragment = new ArticleListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, articleListFragment, Constants.ARTICLE_LIST_TAG).commit();

    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
            if (backStackCount == 1) {
                getSupportFragmentManager().popBackStack();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ArticleListFragment(), Constants.ARTICLE_LIST_TAG).commit();
                showUpButton(false);
            } else {
                super.onBackPressed();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_explore) {
            showToast(item.getTitle().toString());
        } else if (id == R.id.nav_livechat) {
            showToast(item.getTitle().toString());
        } else if (id == R.id.nav_gallery) {
            showToast(item.getTitle().toString());
        } else if (id == R.id.nav_wishlist) {
            showToast(item.getTitle().toString());
        } else if (id == R.id.nav_emagazine) {
            showToast(item.getTitle().toString());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showToast(String selectedItemTitle) {
        Toast.makeText(this, selectedItemTitle, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(Article article) {
        // navigate to article details fragment
        ArticleDetailsFragment fragment = new ArticleDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.ARTICLE, article);
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(Constants.ARTICLE).commit();
        showUpButton(true);
    }

    private void showUpButton(boolean show) {
        // To keep states of ActionBar and ActionBarDrawerToggle synchronized,
        // when you enable on one, you disable on the other.
        // And as you may notice, the order for this operation is disable first, then enable - VERY VERY IMPORTANT.
        if (show) {
            // Remove hamburger
            toggle.setDrawerIndicatorEnabled(false);
            // Show back button
            mActionBar.setDisplayHomeAsUpEnabled(true);
            if (!mToolBarNavigationListenerIsRegistered) {
                toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });

                mToolBarNavigationListenerIsRegistered = true;
            }

        } else {
            // Remove back button
            mActionBar.setDisplayHomeAsUpEnabled(false);
            // Show hamburger
            toggle.setDrawerIndicatorEnabled(true);
            // Remove the/any drawer toggle listener
            toggle.setToolbarNavigationClickListener(null);
            mToolBarNavigationListenerIsRegistered = false;
        }
    }
}
