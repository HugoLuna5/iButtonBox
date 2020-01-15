package lunainc.mx.com.ibuttonbox.Connect;


import lunainc.mx.com.ibuttonbox.Model.ResponseDefaultLR;
import lunainc.mx.com.ibuttonbox.Model.User;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    /**
     * Login and Register client
     * @param body
     * @return
     */

    @POST("api/v1/register")
    Call<ResponseDefaultLR> registerUser(@Body RequestBody body);

    @POST("api/v1/login")
    Call<ResponseDefaultLR> loginUser(@Body RequestBody body);


    /**
     * Get data of client from server
     * @param accept
     * @param Auth
     * @return
     */
    @POST("api/v1/getDataUser")
    Call<User> getDataUser(@Header("Accept") String accept, @Header("Authorization") String Auth);

}
