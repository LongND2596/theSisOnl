package longnd.thesis.network.pojo.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataSuccess {
    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("universities")
    @Expose
    private List<Universitie> universities;

    @SerializedName("user")
    @Expose
    private UserResponse user;

    @SerializedName("profile")
    @Expose
    private ProfileResponse profile;

    public String getToken() {
        return token;
    }

    public List<Universitie> getUniversities() {
        return universities;
    }

    public UserResponse getUser() {
        return user;
    }

    public ProfileResponse getProfile() {
        return profile;
    }
}
