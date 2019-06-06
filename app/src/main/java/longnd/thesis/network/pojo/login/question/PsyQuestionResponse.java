package longnd.thesis.network.pojo.login.question;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import longnd.thesis.network.pojo.login.base.BaseResponse;

public class PsyQuestionResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private PsyData data;

    public PsyData getData() {
        return data;
    }

    public class PsyData{
        @SerializedName("type")
        @Expose
        private String type;

        @SerializedName("typeList")
        @Expose
        private List<TypeList> typeList;

        public String getType() {
            return type;
        }

        public List<TypeList> getTypeList() {
            return typeList;
        }
    }

    public class TypeList{
        @SerializedName("id")
        @Expose
        private int id;

        @SerializedName("content")
        @Expose
        private String content;

        @SerializedName("questions")
        @Expose
        private List<PsyQuestion> questions;

        @SerializedName("answers")
        @Expose
        private List<PsyAnswer> answers;

        public int getId() {
            return id;
        }

        public String getContent() {
            return content;
        }

        public List<PsyQuestion> getQuestions() {
            return questions;
        }

        public List<PsyAnswer> getAnswers() {
            return answers;
        }
    }

    public class PsyQuestion{
        @SerializedName("id")
        @Expose
        private int id;

        @SerializedName("content")
        @Expose
        private String content;

        @SerializedName("type_psychology_id")
        @Expose
        private int type_psychology_id;

        public int getId() {
            return id;
        }

        public String getContent() {
            return content;
        }

        public int getType_psychology_id() {
            return type_psychology_id;
        }
    }

    public class PsyAnswer{
        @SerializedName("name")
        @Expose
        private String name;

        @SerializedName("score")
        @Expose
        private int score;

        @SerializedName("type_id")
        @Expose
        private int type_id;

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }

        public int getType_id() {
            return type_id;
        }
    }

}
