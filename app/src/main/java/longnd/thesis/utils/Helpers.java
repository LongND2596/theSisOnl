package longnd.thesis.utils;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

public class Helpers {
    public static void showDialogFragment(FragmentManager fragmentManager, DialogFragment dialogFragment) {
        if (dialogFragment != null) {
            if (dialogFragment.isAdded()) {
                dialogFragment.dismiss();
            }
            fragmentManager.beginTransaction().add(dialogFragment, dialogFragment.getClass().getSimpleName()).commitAllowingStateLoss();
        }
    }
}
