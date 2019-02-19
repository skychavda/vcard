package vcard.application.android.com.vcard.Utility;

public class User {

    private String response;
    private String name;
    private String number;
    private  String email;
    private String companyName;
    private String Password;

    public User(String response, String name, String number, String email, String companyName, String password) {
        this.response = response;
        this.name = name;
        this.number = number;
        this.email = email;
        this.companyName = companyName;
        Password = password;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getResponse() {
        return response;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return Password;
    }
}

