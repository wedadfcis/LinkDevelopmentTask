package com.example.wedadabdelkareem.linkdevelopmenttask.view.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.wedadabdelkareem.linkdevelopmenttask.R;
import com.example.wedadabdelkareem.linkdevelopmenttask.util.Constants;
import com.example.wedadabdelkareem.linkdevelopmenttask.util.Utilities;
import com.example.wedadabdelkareem.linkdevelopmenttask.view.base.BaseActivity;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNavigation();
        displayArticleListFragment();
    }

    private void initNavigation() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, getToolbar(), R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void displayArticleListFragment() {
        /// add fragment
        ArticleListFragment articleListFragment = new ArticleListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, articleListFragment, Constants.ARTICLE_LIST_TAG).commit();
    }


    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        }
        else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_explore) {
            Utilities.displayToast(item.getTitle().toString(),this);

        } else if (id == R.id.nav_livechat) {
            Utilities.displayToast(item.getTitle().toString(),this);
        } else if (id == R.id.nav_gallery) {
            Utilities.displayToast(item.getTitle().toString(),this);
        } else if (id == R.id.nav_wishlist) {
            Utilities.displayToast(item.getTitle().toString(),this);
        } else if (id == R.id.nav_emagazine) {
            Utilities.displayToast(item.getTitle().toString(),this);
        }

        //close drawer after select item
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




}
