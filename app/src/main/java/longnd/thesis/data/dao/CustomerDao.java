package longnd.thesis.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import longnd.thesis.data.model.Customer;
import longnd.thesis.utils.Define;
import io.reactivex.Single;

@Dao
public interface CustomerDao {

    @Query("SELECT count(*) FROM " + Define.Customer.TABLE_NAME)
    Single<Long> getCountCustomer();

    @Query("SELECT * FROM " + Define.Customer.TABLE_NAME + " WHERE " + Define.Customer.EMAIL + " = :email" + " AND " + Define.Customer.PASS + " = :pass")
    Single<Customer> getCustomerByEmail(String email, String pass);

    @Query("SELECT count(*) FROM " + Define.Customer.TABLE_NAME + " WHERE " + Define.Customer.EMAIL + " = :email" + " AND " + Define.Customer.PASS + " = :pass")
    Single<Long> existsCustomerByEmail(String email, String pass);

    @Query("SELECT * FROM " + Define.Customer.TABLE_NAME + " WHERE " + Define.Customer.EMAIL + " = :email" + " AND " + Define.Customer.PASS + " = :pass")
    Single<Customer> signInCustomer(String email, String pass);

    @Insert
    Single<Long> insertCustomerReturnId(Customer customer);

    @Update
    Single<Integer> updateCustomer(Customer customer);
}
