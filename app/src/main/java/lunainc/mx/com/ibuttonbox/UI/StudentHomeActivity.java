package lunainc.mx.com.ibuttonbox.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import lunainc.mx.com.ibuttonbox.Adapter.MyPagerAdapter;
import lunainc.mx.com.ibuttonbox.Adapter.StudenPagerAdapter;
import lunainc.mx.com.ibuttonbox.Holder.TestHolder;
import lunainc.mx.com.ibuttonbox.R;
import lunainc.mx.com.ibuttonbox.Utils.Constants;
import lunainc.mx.com.ibuttonbox.Utils.GetTimeAgo;
import lunainc.mx.com.ibuttonbox.Model.Test;

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

        userData.collection("Users").document(user_uid).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String typeAccount = documentSnapshot.getString("type_account");



                           if (typeAccount.equals("teacher")){

                                new Constants().goToNextActivity(StudentHomeActivity.this, new TeacherHomeActivity());

                           }else if(typeAccount.equals("admin")){
                               //new Constants().goToNextActivity(StudentHomeActivity.this, new TeacherHomeActivity());

                           }else{

                           }


                    }
                });

    }






}
