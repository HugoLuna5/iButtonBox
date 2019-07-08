package lunainc.mx.com.ibuttonbox.Holder;

import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import lunainc.mx.com.ibuttonbox.R;

public class GroupHolder extends RecyclerView.ViewHolder {


    public @BindView(R.id.statusView)
    View statusView;

    public @BindView(R.id.cardContainer)
    CardView cardContainer;

    public @BindView(R.id.name)
    TextView name;

    public @BindView(R.id.groupName)
    TextView groupName;

    public @BindView(R.id.time)
    TextView time;

    public @BindView(R.id.numMembers)
    TextView numMembers;


    public GroupHolder(View itemView){
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
