package longnd.thesis.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import longnd.thesis.utils.Define;

import java.io.Serializable;

@Entity(tableName = Define.Customer.TABLE_NAME)
public class Customer implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Define.Customer.ID)
    private Integer id;

    @ColumnInfo(name = Define.Customer.FIRST_NAME)
    private String firstName;

    @ColumnInfo(name = Define.Customer.LAST_NAME)
    private String lastName;

    @ColumnInfo(name = Define.Customer.SCHOLL)
    private String school;

    @ColumnInfo(name = Define.Customer.SPECIALIZED)
    private String specialized;

    @ColumnInfo(name = Define.Customer.EMAIL)
    private String email;

    @ColumnInfo(name = Define.Customer.PHONE)
    private String phone;

    @ColumnInfo(name = Define.Customer.BIRTHDATE)
    private String birthdate;

    @ColumnInfo(name = Define.Customer.GENDER)
    private int gender;

    @ColumnInfo(name = Define.Customer.CREATED_AT)
    private String createdAt;

    @ColumnInfo(name = Define.Customer.UPDATED_AT)
    private String updatedAt;

    @ColumnInfo(name = Define.Customer.IMAGE)
    private String image;

    @ColumnInfo(name = Define.Customer.DEL_FLG)
    private Integer delFlg;

    @ColumnInfo(name = Define.Customer.PASS)
    private String pass;

    public Customer() {
    }

    @Ignore
    public Customer(String firstName, String lastName, String email, String phone, String birthdate,
                    int gender, String createdAt, String updatedAt, Integer delFlg) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.birthdate = birthdate;
        this.gender = gender;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.delFlg = delFlg;
    }

    @Ignore
    public Customer(String lastName, String email, String pass) {
        this.lastName = lastName;
        this.email = email;
        this.pass = pass;
        this.gender = -1;
        this.createdAt = "2/28/2019 10:12";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSpecialized() {
        return specialized;
    }

    public void setSpecialized(String specialized) {
        this.specialized = specialized;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
