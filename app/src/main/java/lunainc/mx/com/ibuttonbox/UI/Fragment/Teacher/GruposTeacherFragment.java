package lunainc.mx.com.ibuttonbox.UI.Fragment.Teacher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lunainc.mx.com.ibuttonbox.Adapter.GroupAdapter;
import lunainc.mx.com.ibuttonbox.Connect.APIService;
import lunainc.mx.com.ibuttonbox.Connect.ApiUtils;
import lunainc.mx.com.ibuttonbox.Holder.GroupHolder;
import lunainc.mx.com.ibuttonbox.Holder.TestHolder;
import lunainc.mx.com.ibuttonbox.Model.Group;
import lunainc.mx.com.ibuttonbox.R;
import lunainc.mx.com.ibuttonbox.UI.Groups.CreateGroupActivity;
import lunainc.mx.com.ibuttonbox.UI.Groups.GroupActivity;
import lunainc.mx.com.ibuttonbox.UI.PerfilActivity;
import lunainc.mx.com.ibuttonbox.Utils.Constants;
import lunainc.mx.com.ibuttonbox.Utils.GetTimeAgo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GruposTeacherFragment extends Fragment implements GroupAdapter.ItemClickListener {



    private View view;
    public @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    public @BindView(R.id.btnAction)
    FloatingActionButton btnAction;


    private FirebaseFirestore firebaseFirestore;
    private FirebaseFirestore firestoreGroup;

    private FirebaseAuth auth;
    private String uid_user;
    private APIService mAPIService;
    private SharedPreferences sharedPref;
    private String token =  "";
    private GroupAdapter groupAdapter;
    private  ArrayList<Group> groups;
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
        firebaseFirestore = FirebaseFirestore.getInstance();
        firestoreGroup = FirebaseFirestore.getInstance();
        uid_user = auth.getCurrentUser().getUid();
        Context context = getActivity().getApplicationContext();
        sharedPref = context.getSharedPreferences(
                "credentials", Context.MODE_PRIVATE);

        mAPIService = ApiUtils.getAPIService();
        token = sharedPref.getString(("token"), "noLogged");

        groups = loadData();


        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(getActivity());
        //linearLayoutManager.setReverseLayout(true);
        //linearLayoutManager.setStackFromEnd(true);
        //linearLayoutManager.findFirstVisibleItemPosition();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        groupAdapter = new GroupAdapter(getActivity(), groups);
        groupAdapter.notifyDataSetChanged();
        groupAdapter.setClickListener(this);


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        Log.i("tag", "This'll run 800 milliseconds later");
                        recyclerView.setAdapter(groupAdapter);
                    }
                },
                800);

    }

    public void events(){

        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateGroupActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

    }




    public ArrayList<Group> loadData(){
         ArrayList<Group> groups = new ArrayList<Group>();

        String completeToken = "Bearer "+token;
        mAPIService.myGroupsTeacher("Accept", completeToken).enqueue(new Callback<Group>() {
            @Override
            public void onResponse(Call<Group> call, Response<Group> response) {

                if (response.isSuccessful()){

                    if (response.body().getStatusResponse().equals("success")){


                        if (response.body().getGroups().size() > 0){
                            for (int i = 0; i < response.body().getGroups().size(); i++) {
                                Group group = response.body().getGroups().get(i);
                                groups.add(group);
                            }

                        }

                    }else{
                        TastyToast.makeText(getActivity(), response.body().getMessage(),TastyToast.LENGTH_LONG, TastyToast.ERROR);

                    }

                }else{
                    TastyToast.makeText(getActivity(), "Ocurrio un error al conectarse al servidor",TastyToast.LENGTH_LONG, TastyToast.ERROR);
                }

            }

            @Override
            public void onFailure(Call<Group> call, Throwable t) {
                TastyToast.makeText(getActivity(), t.getMessage(),TastyToast.LENGTH_LONG, TastyToast.ERROR);

            }
        });


       return groups;

    }


    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), GroupActivity.class);
        intent.putExtra("id_group", groups.get(position).getUid());
        intent.putExtra("name", groups.get(position).getName());
        intent.putExtra("color", groups.get(position).getColor());
        getActivity().startActivity(intent);
        getActivity().finish();

    }
}
