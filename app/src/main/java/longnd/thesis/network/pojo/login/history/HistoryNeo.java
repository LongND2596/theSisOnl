package longnd.thesis.network.pojo.login.history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import longnd.thesis.network.pojo.login.base.BaseResponse;

public class HistoryNeo extends BaseResponse {
    @SerializedName("data")
    @Expose
    public DataHistoryNeo data;

    public class DataHistoryNeo {
        @SerializedName("id")
        @Expose
        public int id;

        @SerializedName("user_id")
        @Expose
        public int user_id;

        @SerializedName("title")
        @Expose
        public String title;

        @SerializedName("result")
        @Expose
        public List<ResultHistoryNeo> results;
    }

    public class ResultHistoryNeo {
        @SerializedName("type")
        @Expose
        public String type;

        @SerializedName("level")
        @Expose
        public String level;

        @SerializedName("score")
        @Expose
        public int score;

        @SerializedName("type_id")
        @Expose
        public int type_id;
    }
}
