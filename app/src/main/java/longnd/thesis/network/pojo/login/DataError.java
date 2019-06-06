package longnd.thesis.network.pojo.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataError {
    @SerializedName("email")
    @Expose
    private List<String> email;

    public List<String> getEmail() {
        return email;
    }
}
