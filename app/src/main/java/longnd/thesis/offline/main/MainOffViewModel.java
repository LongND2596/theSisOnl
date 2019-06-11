package longnd.thesis.offline.main;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import longnd.thesis.data.model.Customer;

public class MainOffViewModel extends ViewModel {
    private Customer mCustomer;
    private int numberBack;

    @Inject
    MainOffViewModel() {

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
