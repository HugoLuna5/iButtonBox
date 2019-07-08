package lunainc.mx.com.ibuttonbox.Holder;

import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import lunainc.mx.com.ibuttonbox.R;

public class MemberHolder extends RecyclerView.ViewHolder {

    public @BindView(R.id.name)
    TextView name;

    public @BindView(R.id.image)
    CircleImageView image;




    public MemberHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
