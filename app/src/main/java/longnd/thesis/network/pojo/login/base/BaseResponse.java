package longnd.thesis.network.pojo.login.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseResponse {
    @SerializedName("success")
    @Expose
    private boolean isSuccess;

    @SerializedName("message")
    @Expose
    private String message;

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }
}
