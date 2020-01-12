package lunainc.mx.com.ibuttonbox.UI.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import lunainc.mx.com.ibuttonbox.Holder.TestHolder;
import lunainc.mx.com.ibuttonbox.Model.Test;
import lunainc.mx.com.ibuttonbox.R;
import lunainc.mx.com.ibuttonbox.UI.PerfilActivity;
import lunainc.mx.com.ibuttonbox.UI.StudentHomeActivity;
import lunainc.mx.com.ibuttonbox.Utils.Constants;
import lunainc.mx.com.ibuttonbox.Utils.GetTimeAgo;

public class HomeStudentFragment extends Fragment {


    private View view;
    public @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    public @BindView(R.id.btnAction)
    FloatingActionButton btnJoinGroup;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseFirestore userData;
    private FirebaseFirestore firestoreGroup;
    private FirebaseAuth auth;
    private String user_uid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment, container, false);

        ButterKnife.bind(this, view);

        initVars();
        events();

        return view;
    }


    public void initVars(){

        auth = FirebaseAuth.getInstance();
        user_uid = auth.getCurrentUser().getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firestoreGroup = FirebaseFirestore.getInstance();
        userData = FirebaseFirestore.getInstance();

        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        btnJoinGroup.setColorFilter(Color.parseColor("#FFFFFF"));

    }


    private void events() {

        btnJoinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(getActivity());
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }


    private void loadData(){


        Query query = firebaseFirestore.collection("Test")
                .orderBy("time", Query.Direction.DESCENDING).limit(60);


        FirestoreRecyclerOptions<Test> recyclerOptions = new FirestoreRecyclerOptions.Builder<Test>().
                setQuery(query, Test.class)
                .build();

        FirestoreRecyclerAdapter adapter = new FirestoreRecyclerAdapter<Test, TestHolder>(recyclerOptions) {
            @Override
            protected void onBindViewHolder(final TestHolder holder, int i, Test test) {



                firebaseFirestore.collection("Members").whereEqualTo("uid_user", user_uid)
                        .whereEqualTo("uid_group", test.getUid_group()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                        holder.name.setText(test.getTitle());

                        firestoreGroup.collection("Groups").document(test.getUid_group())
                                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {


                                holder.groupName.setText(documentSnapshot.get("name").toString());

                            }
                        });

                        GetTimeAgo getTimeAgo = new GetTimeAgo();

                        String lastSeenTime = getTimeAgo.getTimeAgo(test.getTime(), getActivity());

                        if (lastSeenTime != null) {
                            holder.time.setText(lastSeenTime);
                        } else {
                            holder.time.setText("Hace un momento ");
                        }


                    }
                });






            }

            @NonNull
            @Override
            public TestHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.item, group, false);

                return new TestHolder(view);
            }
        };


        adapter.startListening();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);


    }






    public void showDialog(Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_join_to_group);

        EditText nameField = (EditText) dialog.findViewById(R.id.nameField);

        MaterialButton dialogButton = (MaterialButton) dialog.findViewById(R.id.btnAction);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameField.getText().toString();


                if (!name.isEmpty()){

                    //verify exist group

                    firebaseFirestore.collection("Groups").whereEqualTo("code", name)
                            .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot snapshots) {

                            int ex = snapshots.size();

                            if (ex > 0 && ex <= 1){


                                //verify exist member
                                String uid_group = snapshots.getDocuments().get(0).getId();

                                firebaseFirestore.collection("Members").whereEqualTo("uid_user", user_uid)
                                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot snapshots) {

                                        int sizeVal = snapshots.size();

                                        if (sizeVal >= 0){
                                            String uid_member_group = firebaseFirestore.collection("Members").document().getId();
                                            Map<String, Object> member = new HashMap<>();
                                            member.put("uid_user", user_uid);
                                            member.put("uid", uid_member_group);
                                            member.put("uid_group", uid_group);
                                            member.put("join_at", System.currentTimeMillis());


                                            firebaseFirestore.collection("Members").document(uid_member_group)
                                                    .set(member).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    TastyToast.makeText(activity, "Te has unido al grupo: "+snapshots.getDocuments().get(0).getString("name"), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                                    dialog.dismiss();
                                                }
                                            });

                                        }

                                    }
                                });









                            }else{
                                TastyToast.makeText(activity, "Es posible que el codigo del grupo sea incorrecto, o no exista", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            }

                        }
                    });




                }else{
                    TastyToast.makeText(activity, "Debes llenar todos los campos", TastyToast.LENGTH_LONG, TastyToast.INFO);
                }


            }
        });

        dialog.show();

    }


}
