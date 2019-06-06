package longnd.thesis.network.pojo.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import longnd.thesis.network.pojo.login.base.BaseResponse;

public class SpecialitieResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private Specialities data;

    public Specialities getData() {
        return data;
    }

    public class Specialities {
        @SerializedName("specialities")
        @Expose
        private List<String> specialities;

        public List<String> getSpecialities() {
            return specialities;
        }
    }
}
