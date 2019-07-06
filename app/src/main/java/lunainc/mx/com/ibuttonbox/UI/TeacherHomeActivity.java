package lunainc.mx.com.ibuttonbox.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import lunainc.mx.com.ibuttonbox.Adapter.MyPagerAdapter;
import lunainc.mx.com.ibuttonbox.R;
import lunainc.mx.com.ibuttonbox.Utils.Constants;

public class TeacherHomeActivity extends AppCompatActivity {


    public @BindView(R.id.pager)
    ViewPager viewPager;
    public @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    public @BindView(R.id.toolbar)
    Toolbar toolbar;
    public @BindView(R.id.imageToolbar)
    CircleImageView imageToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        imageToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Constants().goToNextActivity(TeacherHomeActivity.this, new PerfilActivity());
            }
        });

    }
}
