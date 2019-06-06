package longnd.thesis.network;

import java.util.Map;

import longnd.thesis.network.pojo.login.LoginResponse;
import longnd.thesis.network.pojo.login.ProfileResponse;
import longnd.thesis.network.pojo.login.SpecialitieResponse;
import longnd.thesis.network.pojo.login.history.HistoryResponse;
import longnd.thesis.network.pojo.login.question.NeoQuestionResponse;
import longnd.thesis.network.pojo.login.question.PsyQuestionResponse;
import longnd.thesis.network.pojo.login.question.QuestionTypeResponse;
import longnd.thesis.network.pojo.login.question.RiasecQuestionResponse;
import longnd.thesis.network.result.NeoResultResponse;
import longnd.thesis.network.result.PsyResultResponse;
import longnd.thesis.network.result.RiasecResultResponse;
import longnd.thesis.utils.Define;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    @Headers({"Accept: application/json"})
    @POST("register")
    @FormUrlEncoded
    Single<LoginResponse> registerServer(@Field("email") String email,
                                         @Field("name") String name,
                                         @Field("password") String password);

    @POST("login")
    @FormUrlEncoded
    Single<LoginResponse> loginServer(@Field("email") String email, @Field("password") String password);

    @GET("profile")
    Single<LoginResponse> getProfileByToken(@Header("Authorization") String token);

    @GET("question")
    Single<QuestionTypeResponse> getQuestionsType();

    @GET("major")
    Single<SpecialitieResponse> getSpecialitiesByUniversityId(@Query("universityId") int universityId);

    @Headers({"Accept: application/json"})
    @POST("profile")
    @FormUrlEncoded
    Single<ProfileResponse> updateProfileByToken(@Header("Authorization") String token,
                                                 @Field("username") String username,
                                                 @Field("universityId") int universityId,
                                                 @Field("speciality") String speciality,
                                                 @Field("fullname") String fullname,
                                                 @Field("sex") String sex,
                                                 @Field("phone-number") String phoneNumber,
                                                 @Field("birthday") String birthday);

    @Multipart
    @POST("profile/upload")
    Single<ProfileResponse> updateAvatar(@Header("Authorization") String token, @Part MultipartBody.Part part);

    @Headers({"Accept: application/json"})
    @GET("question/form?type=" + Define.Question.TYPE_NEO)
    Single<NeoQuestionResponse> getListQuestionNeo(@Header("Authorization") String token);

    @Headers({"Accept: application/json"})
    @GET("question/form?type=" + Define.Question.TYPE_RIASEC)
    Single<RiasecQuestionResponse> getListQuestionRiasec(@Header("Authorization") String token);

    @Headers({"Accept: application/json"})
    @GET("question/form?type=" + +Define.Question.TYPE_PSY_POCHOLIGICAL)
    Single<PsyQuestionResponse> getListQuestionPsy(@Header("Authorization") String token);

    /**
     * Submit Question
     */

    @FormUrlEncoded
    @POST("question/neo")
    Single<NeoResultResponse> submitNeo(@Header("Authorization") String token,
                                        @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("question/riasec")
    Single<RiasecResultResponse> submitRiasec(@Header("Authorization") String token,
                                              @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("question/psycho")
    Single<PsyResultResponse> submitPsycho(@Header("Authorization") String token,
                                           @FieldMap Map<String, String> params);

    @Headers({"Accept: application/json"})
    @GET("history")
    Single<HistoryResponse> getListHistory(@Header("Authorization") String token);

    @GET("history/neo/{id}")
    Single<NeoResultResponse> getHistoryNeo(@Header("Authorization") String token, @Path("id") int id);

    @GET("history/riasec/{id}")
    Single<RiasecResultResponse> getHistoryRiasec(@Header("Authorization") String token, @Path("id") int id);

    @GET("history/psychology/{id}")
    Single<PsyResultResponse> getHistoryPsy(@Header("Authorization") String token, @Path("id") int id);
}
