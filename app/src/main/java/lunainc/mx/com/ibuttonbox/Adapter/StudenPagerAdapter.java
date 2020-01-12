package lunainc.mx.com.ibuttonbox.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import lunainc.mx.com.ibuttonbox.UI.Fragment.Student.GroupsStudentFragment;
import lunainc.mx.com.ibuttonbox.UI.Fragment.Student.HomeStudentFragment;

public class StudenPagerAdapter extends FragmentPagerAdapter {


    public StudenPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeStudentFragment();
            case 1:
                return new GroupsStudentFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "Actividad";
            case 1:
                return "Grupos";
            default:
                return null;
        }

    }

}
