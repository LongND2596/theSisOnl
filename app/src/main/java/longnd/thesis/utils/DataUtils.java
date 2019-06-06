package longnd.thesis.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import longnd.thesis.data.model.Customer;
import longnd.thesis.data.model.Question;
import longnd.thesis.network.pojo.login.DataQuestionType;
import longnd.thesis.network.pojo.login.ProfileResponse;
import longnd.thesis.network.pojo.login.Universitie;
import longnd.thesis.network.pojo.login.UserResponse;
import longnd.thesis.network.result.NeoResultResponse;
import longnd.thesis.network.result.PsyResultResponse;
import longnd.thesis.network.result.RiasecResultResponse;

public class DataUtils {
    private static DataUtils instance;
    private Customer customer;
    private List<Question> neos;
    private List<Question> riasec;
    private List<Question> psychological;
    private UserResponse user;
    private ProfileResponse profile;
    private List<DataQuestionType.BaseTypeQuestion> questionsType;
    private List<Universitie> universities;
    private String token;
    public List<NeoResultResponse.ResultNeo> resultNeo;
    public RiasecResultResponse.DataRiasec resultRiasec;
    public List<PsyResultResponse.ResultPsy> resultPsycho;

    public Map<Integer,String> typeListPsy = new HashMap<>();

    public String historyId;

    public static DataUtils getInstance() {
        if (instance == null) {
            instance = new DataUtils();
        }
        return instance;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Question> getNeos() {
        return neos;
    }

    public void setNeos(List<Question> neos) {
        this.neos = neos;
    }

    public List<Question> getRiasec() {
        return riasec;
    }

    public void setRiasec(List<Question> riasec) {
        this.riasec = riasec;
    }

    public List<Question> getPsychological() {
        return psychological;
    }

    public void setPsychological(List<Question> psychological) {
        this.psychological = psychological;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public ProfileResponse getProfile() {
        return profile;
    }

    public void setProfile(ProfileResponse profile) {
        this.profile = profile;
    }

    public List<DataQuestionType.BaseTypeQuestion> getQuestionsType() {
        return questionsType;
    }

    public void setQuestionsType(List<DataQuestionType.BaseTypeQuestion> questionsType) {
        this.questionsType = questionsType;
    }

    public List<Universitie> getUniversities() {
        return universities;
    }

    public void setUniversities(List<Universitie> universities) {
        this.universities = universities;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public List<Integer> scoreLoAu;
    public List<Integer> scoreTramCam;
    public List<Integer> scoreStress;
    public List<Integer> scoreKhoTapTrung;
    public List<Integer> scoreTangDong;
    public List<Integer> scoreKKVeGiaoTiepXaHoi;
    public List<Integer> scoreKHHocTap;
    public List<Integer> scoreKKTrongDinhHuongNgheNghiep;
    public List<Integer> scoreKKTrongMoiQuanHeChaMe;
    public List<Integer> scoreKKTrongMoiQuanHeThayCo;
    public List<Integer> scoreKKTrongMoiQuanHeBanBe;
    public List<Integer> scoreHanhViThachThucChongDoi;
    public List<Integer> scoreRoiLoanHanhViUngXu;
    public List<Integer> scoreGayHan;
}
