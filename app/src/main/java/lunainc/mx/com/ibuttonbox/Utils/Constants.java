package lunainc.mx.com.ibuttonbox.Utils;

import android.app.Activity;
import android.content.Intent;

import lunainc.mx.com.ibuttonbox.UI.StudentHomeActivity;

public class Constants {


    public void goToNextActivity(Activity activity, Activity activityFinal){

        Intent intent = new Intent(activity, activityFinal.getClass());
        activity.startActivity(intent);
        activity.finish();
    }




}
