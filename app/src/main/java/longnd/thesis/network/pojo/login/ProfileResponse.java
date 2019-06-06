package longnd.thesis.network.pojo.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileResponse {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("user_id")
    @Expose
    private int userId;

    @SerializedName("fullname")
    @Expose
    private String fullname;

    @SerializedName("avatar_path")
    @Expose
    private String avatarPath;

    @SerializedName("birthday")
    @Expose
    private String birthday;

    @SerializedName("sex")
    @Expose
    private String sex;

    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;

    @SerializedName("university")
    @Expose
    private String university;

    @SerializedName("speciality")
    @Expose
    private String speciality;

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getFullname() {
        return fullname;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getSex() {
        return sex;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUniversity() {
        return university;
    }

    public String getSpeciality() {
        return speciality;
    }
}
