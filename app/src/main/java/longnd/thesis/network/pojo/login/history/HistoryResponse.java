package longnd.thesis.network.pojo.login.history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import longnd.thesis.network.pojo.login.base.BaseResponse;

public class HistoryResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    public List<DataHistory> data;

    public class DataHistory {
        @SerializedName("id")
        @Expose
        public int id;

        @SerializedName("title")
        @Expose
        public String title;

        @SerializedName("created_at")
        @Expose
        public String created_at;

        @SerializedName("result")
        @Expose
        public String result;

        @SerializedName("type")
        @Expose
        public String type;
    }
}
