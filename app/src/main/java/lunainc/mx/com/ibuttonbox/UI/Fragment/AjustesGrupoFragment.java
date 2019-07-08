package lunainc.mx.com.ibuttonbox.UI.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.sdsmdg.tastytoast.TastyToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import lunainc.mx.com.ibuttonbox.R;

public class AjustesGrupoFragment extends Fragment {


    public @BindView(R.id.student)
    LinearLayout student;
    public @BindView(R.id.teacher)
    LinearLayout teacher;
    public @BindView(R.id.btnActionExit)
    Button btnActionExit;

    private View view;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth auth;
    private String uid_user;

    public static AjustesGrupoFragment newInstance(String parameter) {

        Bundle args = new Bundle();
        args.putString("parameter", parameter);
        AjustesGrupoFragment fragment = new AjustesGrupoFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ajustes, container, false);

        ButterKnife.bind(this, view);

        initVars();
        checkDataUser();
        events();


        return view;
    }

    public void initVars(){
        auth = FirebaseAuth.getInstance();
        uid_user = auth.getCurrentUser().getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();

    }

    public void checkDataUser(){

        firebaseFirestore.collection("Users").document(uid_user)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String typeAccount = documentSnapshot.getString("type_account");



                if (typeAccount.equals("teacher")){

                   student.setVisibility(View.GONE);
                   teacher.setVisibility(View.VISIBLE);

                }else if(typeAccount.equals("admin")){
                    //new Constants().goToNextActivity(StudentHomeActivity.this, new TeacherHomeActivity());

                }else{
                    teacher.setVisibility(View.GONE);
                    student.setVisibility(View.VISIBLE);
                }
            }
        });


    }


    public void events(){

        btnActionExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseFirestore.collection("Members").whereEqualTo("uid_user", uid_user)
                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot snapshots) {

                        int size = snapshots.size();

                        if (size > 0 && size <=1){

                            String key = snapshots.getDocuments().get(0).getId();

                            firebaseFirestore.collection("Members").document(key)
                                    .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    getActivity().finish();
                                    TastyToast.makeText(getActivity(), "Has abandonado el grupo", TastyToast.LENGTH_LONG, TastyToast.INFO);
                                }
                            });

                        }

                    }
                });

            }
        });


    }


}
