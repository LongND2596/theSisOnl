package longnd.thesis.network.result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import longnd.thesis.network.pojo.login.base.BaseResponse;

public class RiasecResultResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    public DataRiasec data;

    public class DataRiasec{
        @SerializedName("results")
        @Expose
        public RiasecResult results;

        @SerializedName("result")
        @Expose
        public RiasecResult result;

        @SerializedName("highestFieldExplain")
        @Expose
        public HighestFieldExplain highestFieldExplain;
    }

    public class RiasecResult{
        @SerializedName("Thực tế")
        @Expose
        public double thucTe;

        @SerializedName("Khám phá")
        @Expose
        public double khamPha;

        @SerializedName("Nghệ thuật")
        @Expose
        public double ngheThuat;

        @SerializedName("Xã hội")
        @Expose
        public double xaHoi;

        @SerializedName("Quy tắc")
        @Expose
        public double quyTac;

        @SerializedName("Thuyết phục")
        @Expose
        public double thuyetPhuc;
    }

    public class HighestFieldExplain{
        @SerializedName("id")
        @Expose
        public int id;

        @SerializedName("type")
        @Expose
        public String type;

        @SerializedName("content")
        @Expose
        public String content;
    }
}
