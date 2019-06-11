package longnd.thesis.network.pojo.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataQuestionType {
    @SerializedName("neo")
    @Expose
    private BaseTypeQuestion neo;

    @SerializedName("riasec")
    @Expose
    private BaseTypeQuestion riasec;

    @SerializedName("psychology")
    @Expose
    private BaseTypeQuestion psychology;


    public BaseTypeQuestion getNeo() {
        return neo;
    }

    public BaseTypeQuestion getRiasec() {
        return riasec;
    }

    public BaseTypeQuestion getPsychology() {
        return psychology;
    }

    public static class BaseTypeQuestion {
        @SerializedName("title")
        @Expose
        private String title;

        @SerializedName("description")
        @Expose
        private String description;

        @SerializedName("image")
        @Expose
        private String image;

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public BaseTypeQuestion(String title, String description) {
            this.title = title;
            this.description = description;
        }

        public String getImage() {
            return image;
        }
    }
}


