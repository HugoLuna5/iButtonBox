package lunainc.mx.com.ibuttonbox.Utils;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import lunainc.mx.com.ibuttonbox.UI.Student.StudentHomeActivity;
import lunainc.mx.com.ibuttonbox.UI.Teacher.TeacherHomeActivity;

public class Constants {


    public void goToNextActivity(Activity activity, Activity activityFinal){

        Intent intent = new Intent(activity, activityFinal.getClass());
        activity.startActivity(intent);
        activity.finish();
    }

    public void checkAndGoTo(Activity activityStart, String type_acc){
        switch (type_acc){
            case "student":
                new Constants().goToNextActivity(activityStart, new StudentHomeActivity());
                break;
            case "teacher":
                new Constants().goToNextActivity(activityStart, new TeacherHomeActivity());
                break;
            case "admin":
                Toast.makeText(activityStart, "Dont exist admin type account", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }




}
