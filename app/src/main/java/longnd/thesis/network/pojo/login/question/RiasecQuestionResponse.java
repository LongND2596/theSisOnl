package longnd.thesis.network.pojo.login.question;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import longnd.thesis.network.pojo.login.base.BaseResponse;

public class RiasecQuestionResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private RiasecData data;

    public RiasecData getData() {
        return data;
    }

    public class RiasecData {
        @SerializedName("type")
        @Expose
        private String type;

        @SerializedName("questions")
        @Expose
        private List<RiasecQuestion> questions;

        @SerializedName("answerSet")
        @Expose
        private List<String> answer;

        @SerializedName("scoreSet")
        @Expose
        private List<Double> srore;

        public String getType() {
            return type;
        }

        public List<RiasecQuestion> getQuestions() {
            return questions;
        }

        public List<String> getAnswer() {
            return answer;
        }

        public List<Double> getSrore() {
            return srore;
        }
    }

    public class RiasecQuestion {
        @SerializedName("id")
        @Expose
        private int id;

        @SerializedName("content")
        @Expose
        private String content;

        public int getId() {
            return id;
        }

        public String getContent() {
            return content;
        }
    }

}
