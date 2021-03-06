package vcard.application.android.com.vcard.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import vcard.application.android.com.vcard.R;

public class PrefConfig {
    private SharedPreferences sharedPreferences;
    private Context context;

    public PrefConfig(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_file),context.MODE_PRIVATE);
    }

    public void writeLoginStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.pref_login_status),status);
        editor.commit();
    }

    public boolean readLoginStatus(){
        return  sharedPreferences.getBoolean(context.getString(R.string.pref_login_status),false);
    }

    public void writeUserId(int userId){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(context.getString(R.string.pref_user_id),userId);
        editor.commit();
    }

    public void writeName(String name){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_user_name),name);
        editor.commit();
    }
    public void writeEmail(String email){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_user_email),email);
        editor.commit();
    }
    public void writeNumber(String number){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_user_number),number);
        editor.commit();
    }
    public void writePassword(String password){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_user_password),password);
        editor.commit();
    }
    public void writeCompany(String companyName){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_user_company),companyName);
        editor.commit();
    }
    public void writeAddress(String address){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_user_address),address);
        editor.commit();
    }

    public int readUserId(){
        return  sharedPreferences.getInt(context.getString(R.string.pref_user_id),0);
    }

    public String readName(){
        return  sharedPreferences.getString(context.getString(R.string.pref_user_name),"User");
    }

    public String readEmail(){
        return  sharedPreferences.getString(context.getString(R.string.pref_user_email),"User");
    }

    public String readNumber(){
        return  sharedPreferences.getString(context.getString(R.string.pref_user_number),"User");
    }

    public String readCompany(){
        return  sharedPreferences.getString(context.getString(R.string.pref_user_company),"User");
    }

    public String readAddress(){
        return  sharedPreferences.getString(context.getString(R.string.pref_user_address),"User");
    }


    public void displayToast(String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
