package longnd.thesis.network.result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import longnd.thesis.network.pojo.login.base.BaseResponse;

public class NeoResultResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private DataNeo data;

    public DataNeo getData() {
        return data;
    }

    public class DataNeo {
        @SerializedName("results")
        @Expose
        private List<ResultNeo> results;

        @SerializedName("result")
        @Expose
        public List<ResultNeo> result;

        public List<ResultNeo> getResults() {
            return results;
        }
    }

    public class ResultNeo {
        @SerializedName("level")
        @Expose
        private String level;

        @SerializedName("score")
        @Expose
        private int score;

        @SerializedName("type")
        @Expose
        private String type;

        @SerializedName("type_id")
        @Expose
        private int type_id;

        @SerializedName("explain")
        @Expose
        private String explain;

        public String getLevel() {
            return level;
        }

        public int getScore() {
            return score;
        }

        public String getType() {
            return type;
        }

        public int getType_id() {
            return type_id;
        }

        public String getExplain() {
            return explain;
        }
    }
}
