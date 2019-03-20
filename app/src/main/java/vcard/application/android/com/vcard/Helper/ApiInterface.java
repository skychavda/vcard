package vcard.application.android.com.vcard.Helper;

import android.media.Image;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import vcard.application.android.com.vcard.Utility.CardItem;
import vcard.application.android.com.vcard.Utility.UploadedCard;
import vcard.application.android.com.vcard.Utility.User;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("signup.php")
    Call<User> performRegistration(@Field("email") String Email,
                                   @Field("password") String Password,
                                   @Field("number") String MobileNumber,
                                   @Field("firstName") String FirstName,
                                   @Field("lastName") String LastName,
                                   @Field("address") String Address,
                                   @Field("companyName") String CompanyName);

    @GET("login.php")
    Call<User> performUserLogin(@Query("email") String Email, @Query("password") String Password);

    @GET("searchCard.php")
    Call<List<CardItem>> getCard(@Query("key") String keyword, @Query("userId") int UserId);

    @FormUrlEncoded
    @POST("deleteCard.php")
    Call<CardItem> deleteCard(@Field("cardId") int cardId);

    @Multipart
    @POST("uploadImage.php")
    Call<UploadedCard> addCard(
            @Part("userID") int userID,
            @Part MultipartBody.Part Image);
//            @Part("companyName") RequestBody companyName,
//            @Part("companyAddress") RequestBody companyAddress,
//            @Part("firstName1") RequestBody firstName1,
//            @Part("contactNumber1") RequestBody contactNumber1,
//            @Part("contactEmail1") RequestBody contactEmail1,
//            @Part("designation1") RequestBody designation1);

}
