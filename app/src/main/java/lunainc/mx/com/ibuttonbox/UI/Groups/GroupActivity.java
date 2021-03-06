package lunainc.mx.com.ibuttonbox.UI.Groups;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;
import lunainc.mx.com.ibuttonbox.Adapter.GroupPagerAdapter;
import lunainc.mx.com.ibuttonbox.Adapter.MyPagerAdapter;
import lunainc.mx.com.ibuttonbox.R;
import lunainc.mx.com.ibuttonbox.UI.Student.StudentHomeActivity;
import lunainc.mx.com.ibuttonbox.UI.Teacher.TeacherHomeActivity;
import lunainc.mx.com.ibuttonbox.Utils.Constants;

public class GroupActivity extends AppCompatActivity {


    public @BindView(R.id.toolbar)
    Toolbar toolbar;
    public @BindView(R.id.pager)
    ViewPager viewPager;
    public @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth auth;
    private String user_uid;
    private String group_uid;
    private String group_name;
    private String group_color;
    private SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        ButterKnife.bind(this);

        group_uid = getIntent().getExtras().getString("id_group");
        group_name = getIntent().getExtras().getString("name");
        group_color = getIntent().getExtras().getString("color");

        initVars();
        loadData();

    }

    public void initVars(){

        auth = FirebaseAuth.getInstance();
        user_uid = auth.getCurrentUser().getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();
        Context context = getApplicationContext();
        sharedPref = context.getSharedPreferences(
                "credentials", Context.MODE_PRIVATE);

    }



    public void loadData(){


        toolbar.setTitle(group_name);
        String color = "#"+group_color;
        toolbar.setBackgroundColor(Color.parseColor(color));
        tabLayout.setBackgroundColor(Color.parseColor(color));
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
        tabLayout.setTabTextColors(Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF"));

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(Color.parseColor(color));
            getWindow().setStatusBarColor(Color.parseColor(color));
        }

        setSupportActionBar(toolbar);



        ActionBar actionBar =  getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_close);
            actionBar.setTitle(group_name);
        }






        GroupPagerAdapter myPagerAdapter = new GroupPagerAdapter(getSupportFragmentManager(), group_uid);
        viewPager.setAdapter(myPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);






    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            // handle close button click here
            String typeAccount = sharedPref.getString(("type_account"), "noLogged");

            if (!typeAccount.equals("noLogged")) {

                if (typeAccount.equals("teacher")) {
                    new Constants().goToNextActivity(GroupActivity.this, new TeacherHomeActivity());

                }else{
                    new Constants().goToNextActivity(GroupActivity.this, new StudentHomeActivity());

                }

            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        String typeAccount = sharedPref.getString(("type_account"), "noLogged");

        if (!typeAccount.equals("noLogged")) {

            if (typeAccount.equals("teacher")) {
                new Constants().goToNextActivity(GroupActivity.this, new TeacherHomeActivity());

            }else{
                new Constants().goToNextActivity(GroupActivity.this, new StudentHomeActivity());

            }

        }
    }


}
