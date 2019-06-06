package longnd.thesis.network.pojo.login;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import longnd.thesis.network.pojo.login.base.BaseResponse;

public class DataQuestionResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private QuestionResponse data;

    public QuestionResponse getData() {
        return data;
    }

    public class QuestionResponse {
        @SerializedName("type")
        @Expose
        private String type;

        @SerializedName("questions")
        @Expose
        private List<Questions> questions;

        @SerializedName("answers")
        @Expose
        private List<Answers> answers;

        public String getType() {
            return type;
        }

        public List<Questions> getQuestions() {
            return questions;
        }

        public List<Answers> getAnswers() {
            return answers;
        }
    }

    public class Questions {
        @SerializedName("content")
        @Expose
        private String content;

        public String getContent() {
            return content;
        }
    }

    public class Answers {
        @SerializedName("name")
        @Expose
        private String name;

        @SerializedName("score")
        @Expose
        private int score;

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }
    }
}

