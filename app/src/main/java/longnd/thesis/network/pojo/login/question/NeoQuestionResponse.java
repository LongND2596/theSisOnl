package longnd.thesis.network.pojo.login.question;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import longnd.thesis.network.pojo.login.base.BaseResponse;

public class NeoQuestionResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private NeoData data;

    public NeoData getData() {
        return data;
    }

    public class NeoData {
        @SerializedName("type")
        @Expose
        private String type;

        @SerializedName("questions")
        @Expose
        private List<NeoQuestion> questions;

        @SerializedName("answerSet")
        @Expose
        private List<String> answer;

        @SerializedName("scoreSet")
        @Expose
        private List<Integer> srore;

        public String getType() {
            return type;
        }

        public List<NeoQuestion> getQuestions() {
            return questions;
        }

        public List<String> getAnswer() {
            return answer;
        }

        public List<Integer> getSrore() {
            return srore;
        }
    }

    public class NeoQuestion {
        @SerializedName("id")
        @Expose
        private int id;

        @SerializedName("content")
        @Expose
        private String content;

        @SerializedName("reverse_score")
        @Expose
        private Boolean reverseScore;

        public int getId() {
            return id;
        }

        public String getContent() {
            return content;
        }

        public Boolean getReverseScore() {
            return reverseScore;
        }
    }

}
