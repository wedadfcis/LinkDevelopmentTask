package com.example.wedadabdelkareem.linkdevelopmenttask.view.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
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

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, ArticleClickCallBack {
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // super.setContentView(R.layout.app_bar_main);
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
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void displayArticleListFragment() {
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

        //close drawer after select item
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showToast(String selectedItemTitle) {
        Toast.makeText(this, selectedItemTitle, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(Article article) {
        // navigate to article details
        Intent intent = new Intent(this,ArticleDetailsActivity.class);
        intent.putExtra(Constants.ARTICLE,article);
        startActivity(intent);
    }
}
