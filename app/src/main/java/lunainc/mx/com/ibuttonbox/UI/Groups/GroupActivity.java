package lunainc.mx.com.ibuttonbox.UI.Groups;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        ButterKnife.bind(this);

        group_uid = getIntent().getExtras().getString("uid_group");

        initVars();
        loadData();

    }

    public void initVars(){

        auth = FirebaseAuth.getInstance();
        user_uid = auth.getCurrentUser().getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();


    }



    public void loadData(){

        firebaseFirestore.collection("Groups").document(group_uid).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        String groupName = documentSnapshot.getString("name");
                        String groupColor = documentSnapshot.getString("color");
                        String color = "#"+groupColor;

                        toolbar.setTitle(groupName);
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
                            actionBar.setTitle(groupName);
                        }


                    }
                });



        GroupPagerAdapter myPagerAdapter = new GroupPagerAdapter(getSupportFragmentManager(), group_uid);
        viewPager.setAdapter(myPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);






    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            // handle close button click here
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
