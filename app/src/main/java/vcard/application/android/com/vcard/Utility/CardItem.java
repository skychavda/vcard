package vcard.application.android.com.vcard.Utility;

import android.content.Context;
import android.graphics.Bitmap;

public class CardItem {

    private String response;
    private String tmp;
    private int cardId;
    private int userId;
    private String frontImage;
    private String companyName;
    private String companyAddress;
    private String firstName1;
    private String contactNumber1;
    private String contactEmail;
    private String designation;

    public String getResponse() {
        return response;
    }

    public String getTmp() {
        return tmp;
    }

    public int getCardId() {
        return cardId;
    }

    public int getUserId() {
        return userId;
    }

    public String getFrontImage() {
        return frontImage;
    }

    public String getCompayName() {
        return companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public String getFirstName1() {
        return firstName1;
    }

    public String getContactNumber1() {
        return contactNumber1;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getDesignation() {
        return designation;
    }
}
