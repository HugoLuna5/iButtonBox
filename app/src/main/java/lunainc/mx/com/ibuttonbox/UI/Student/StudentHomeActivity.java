package lunainc.mx.com.ibuttonbox.UI.Student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import lunainc.mx.com.ibuttonbox.Adapter.StudenPagerAdapter;
import lunainc.mx.com.ibuttonbox.R;
import lunainc.mx.com.ibuttonbox.UI.PerfilActivity;
import lunainc.mx.com.ibuttonbox.UI.Teacher.TeacherHomeActivity;
import lunainc.mx.com.ibuttonbox.Utils.Constants;

public class StudentHomeActivity extends AppCompatActivity  {



    

    public @BindView(R.id.toolbar)
    Toolbar toolbar;
    public @BindView(R.id.pager)
    ViewPager viewPager;
    public @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    public @BindView(R.id.imageToolbar)
    CircleImageView imageToolbar;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseFirestore userData;
    private FirebaseFirestore firestoreGroup;
    private FirebaseAuth auth;
    private String user_uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        ButterKnife.bind(this);

        initVars();
        events();
        loadDataUser();

    }

   


    public void initVars(){

        auth = FirebaseAuth.getInstance();
        user_uid = auth.getCurrentUser().getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firestoreGroup = FirebaseFirestore.getInstance();
        userData = FirebaseFirestore.getInstance();

        setSupportActionBar(toolbar);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
        tabLayout.setTabTextColors(Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF"));

        StudenPagerAdapter myPagerAdapter = new StudenPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }



    private void events() {

        imageToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Constants().goToNextActivity(StudentHomeActivity.this, new PerfilActivity());
            }
        });

    }



    public void loadDataUser(){


    }






}
