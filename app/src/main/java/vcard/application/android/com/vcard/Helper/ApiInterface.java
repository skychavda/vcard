package vcard.application.android.com.vcard.Helper;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import vcard.application.android.com.vcard.Utility.User;

public interface ApiInterface {

    @GET("signup.php")
    Call<User> performRegistration(@Query("email") String Email, @Query("password") String Password, @Query("number") String MobileNumber, @Query("userName") String Name);

    @GET("login.php")
    Call<User> performUserLogin(@Query("email") String Email, @Query("password") String Password);
}
