package vcard.application.android.com.vcard.Helper;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import vcard.application.android.com.vcard.Utility.CardItem;
import vcard.application.android.com.vcard.Utility.User;

public interface ApiInterface {

    @GET("signup.php")
    Call<User> performRegistration(@Query("email") String Email, @Query("password") String Password, @Query("number") String MobileNumber, @Query("userName") String Name);

    @GET("login.php")
    Call<User> performUserLogin(@Query("email") String Email, @Query("password") String Password);

    @GET("searchCard.php")
    Call<List<CardItem>> getCard(@Query("key") String keyword, @Query("userId") int UserId);

    @FormUrlEncoded
    @POST("deleteCard.php")
    Call<CardItem> deleteCard(@Field("cardId") int cardId);

    @Multipart
    @POST("insertCard.php")
    Call<CardItem> addCard(
            @Part("userID") int UserId,
            @Part("companyName") RequestBody CompanyName,
            @Part("companyAddress") RequestBody CompanyAddress,
            @Part("firstName1") RequestBody FirstName1,
            @Part("contactNumber1") RequestBody ContactNumber1,
            @Part("contactEmail1") RequestBody ContactEmail1,
            @Part("designation1") RequestBody Designation1);


}
