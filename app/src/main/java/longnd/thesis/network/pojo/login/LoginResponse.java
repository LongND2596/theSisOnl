package longnd.thesis.network.pojo.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("success")
    @Expose
    private boolean isSuccess;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("errors")
    @Expose
    private DataError errors;

    @SerializedName("data")
    @Expose
    private DataSuccess data;

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public DataError getErrors() {
        return errors;
    }

    public DataSuccess getData() {
        return data;
    }
}
