package com.techsell.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.techsell.R;
import com.techsell.fragment.DashboardFragment;
import com.techsell.global.AppConfig;
import com.techsell.ui.CustomTitle;
import com.techsell.ui.CustomTypeFace;
import com.techsell.ui.SnackBar;

import java.security.PublicKey;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ankit on 27/06/16.
 */
public class DashboardActivity extends AppCompatActivity {

    @Bind(R.id.navigation_view)
    NavigationView navigationView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.drawer)
    DrawerLayout drawerLayout;
    private MenuItem previousMenuItem;
    private View header;
public String Us;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Username = "username";
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        overridePendingTransition(0, 0);
        pref=getApplication().getSharedPreferences("Options", MODE_PRIVATE);


        header = navigationView.getHeaderView(0);
        TextView txtWelcome = (TextView) header.findViewById(R.id.txtWelcome);
        TextView txtName = (TextView) header.findViewById(R.id.txtName);
        Typeface typeface = CustomTypeFace.getTypeface(this);
        txtWelcome.setTypeface(typeface);
        txtName.setTypeface(typeface);
        String Us=pref.getString(Username, "");

        txtName.setText(Us);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (previousMenuItem != null)
                    previousMenuItem.setChecked(false);

                menuItem.setCheckable(true);
                menuItem.setChecked(true);

                previousMenuItem = menuItem;

                drawerLayout.closeDrawers();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                switch (menuItem.getItemId()) {
                    case R.id.dashboard:

                        DashboardFragment dashboardFragment = new DashboardFragment();
                        fragmentTransaction.replace(R.id.frame, dashboardFragment);
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle(CustomTitle.getTitle(DashboardActivity.this, getString(R.string.dashboard)));
                        AppConfig.currentFragment = dashboardFragment;
                        return true;

                    case R.id.contact_us:
                        Toast.makeText(getApplicationContext(), "Contact Us", Toast.LENGTH_SHORT).show();

                        return true;

                    case R.id.Profile:
                        Toast.makeText(getApplicationContext(), "Profile Under Maintainence", Toast.LENGTH_SHORT).show();

                        return true;
                    case R.id.History:
                        Toast.makeText(getApplicationContext(), "Under Maintainence", Toast.LENGTH_SHORT).show();

                        return true;

                    case R.id.Wishlist:
                        Toast.makeText(getApplicationContext(), "Under Maintainence", Toast.LENGTH_SHORT).show();

                        return true;
                    case R.id.privacy:
                        Toast.makeText(getApplicationContext(), "Under Maintainence", Toast.LENGTH_SHORT).show();

                        return true;

                    case R.id.logout:
                        logout();
                        return true;

                    case R.id.rate_us:
                        Toast.makeText(getApplicationContext(), "Rate Us", Toast.LENGTH_SHORT).show();

                        return true;

                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });


        navigationView.getMenu().getItem(0).setChecked(true);
        DashboardFragment fragment = new DashboardFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(CustomTitle.getTitle(DashboardActivity.this, getString(R.string.dashboard)));


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }
    void logout() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(DashboardActivity.this);
        final MaterialDialog dialog = builder.build();
        builder.title(R.string.logout).content(R.string.logout_message).positiveText(R.string.logout).negativeText(R.string.cancel).typeface("robo_font_bold.otf", "robo_font.otf");
        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                dialog.dismiss();
                try {
                    AppConfig.logout(DashboardActivity.this);
                    String Us = pref.getString(Username, null);
                    Us=Us + "Bye Bye";
                    Toast.makeText(DashboardActivity.this,Us,Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = pref.edit();

                    editor.putString(Username, "");

                    Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("message", getResources().getString(R.string.logout_success));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtras(bundle);
                    DashboardActivity.this.startActivity(intent);
                } catch (Exception e) {
                    SnackBar.show(DashboardActivity.this, e.toString());
                    e.printStackTrace();
                }
            }
        });
        builder.onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


    @Override
    public void onBackPressed() {
        if (AppConfig.currentFragment != null && !(AppConfig.currentFragment instanceof DashboardFragment)) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            DashboardFragment dashboardFragment = new DashboardFragment();
            fragmentTransaction.replace(R.id.frame, dashboardFragment);
            fragmentTransaction.commit();
            getSupportActionBar().setTitle(CustomTitle.getTitle(DashboardActivity.this, getString(R.string.dashboard)));
            AppConfig.currentFragment = dashboardFragment;
            return;
        }
        if(AppConfig.isLogin(DashboardActivity.this))
        {
            finish();
        }
            super.onBackPressed();
    }
}