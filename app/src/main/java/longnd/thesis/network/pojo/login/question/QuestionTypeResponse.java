package longnd.thesis.network.pojo.login.question;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import longnd.thesis.network.pojo.login.base.BaseResponse;
import longnd.thesis.network.pojo.login.DataQuestionType;

public class QuestionTypeResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private DataQuestionType data;

    public DataQuestionType getData() {
        return data;
    }
}
