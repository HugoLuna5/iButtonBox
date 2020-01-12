package lunainc.mx.com.ibuttonbox.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import lunainc.mx.com.ibuttonbox.UI.Fragment.Teacher.GruposTeacherFragment;
import lunainc.mx.com.ibuttonbox.UI.Fragment.Teacher.HomeTeacherFragment;

public class MyPagerAdapter extends FragmentPagerAdapter {


    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeTeacherFragment();
            case 1:
                return new GruposTeacherFragment();
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
