package longnd.thesis.network.result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import longnd.thesis.network.pojo.login.base.BaseResponse;

public class PsyResultResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    public DataPsy data;

    public class DataPsy {
        @SerializedName("result")
        @Expose
        public List<ResultPsy> result;
    }

    public class ResultPsy {
        @SerializedName("typeId")
        @Expose
        public int typeId;

        @SerializedName("score")
        @Expose
        public int score;

        @SerializedName("typeResult")
        @Expose
        public String typeResult;

        @SerializedName("trouble")
        @Expose
        public String trouble;
    }
}
