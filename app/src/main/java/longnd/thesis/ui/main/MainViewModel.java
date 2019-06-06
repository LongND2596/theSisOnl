package longnd.thesis.ui.main;

import androidx.lifecycle.ViewModel;
import longnd.thesis.data.model.Customer;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {
    private Customer mCustomer;
    private int numberBack;

    @Inject
    MainViewModel() {

    }

    public void setmCustomer(Customer customer) {
        this.mCustomer = customer;
    }

    public Customer getmCustomer() {
        return mCustomer;
    }

    public int getNumberBack() {
        return numberBack;
    }

    public void setNumberBack(int numberBack) {
        this.numberBack = numberBack;
    }
}
