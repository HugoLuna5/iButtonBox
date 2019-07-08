package lunainc.mx.com.ibuttonbox.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import lunainc.mx.com.ibuttonbox.UI.Fragment.AjustesGrupoFragment;
import lunainc.mx.com.ibuttonbox.UI.Fragment.HomeGroupFragment;
import lunainc.mx.com.ibuttonbox.UI.Fragment.MiembrosFragment;

public class GroupPagerAdapter extends FragmentPagerAdapter {


    public String uid_group;
    public GroupPagerAdapter(FragmentManager fm, String uid_group) {
        super(fm);
        this.uid_group = uid_group;
        this.notifyDataSetChanged();
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HomeGroupFragment.newInstance(uid_group);
            case 1:
                return MiembrosFragment.newInstance(uid_group);
            case 2:
                return AjustesGrupoFragment.newInstance(uid_group);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "Actividad";
            case 1:
                return "Miembros";
            case 2:
                return "Ajustes";
            default:
                return null;
        }

    }
}
