package vcard.application.android.com.vcard.Utility;

public class User {

    private String response;
    private String firstName;
    private  String lastName;
    private String address;
    private int id;
    private String number;
    private  String email;
    private String companyName;
    private String Password;

    public User(String response, String firstName, String lastName, String address, int id, String number, String email, String companyName, String password) {
        this.response = response;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.id = id;
        this.number = number;
        this.email = email;
        this.companyName = companyName;
        Password = password;
    }

    public String getResponse() {
        return response;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getEmail() {
        return email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getPassword() {
        return Password;
    }
}

