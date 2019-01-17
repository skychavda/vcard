package vcard.application.android.com.vcard.Utility;

import android.content.Context;
import android.graphics.Bitmap;

public class CardItem {
    private String name;
    private String number;
    private String cardId;
    private String picture;
    private String email;

    public CardItem(){}

    public CardItem(String name, String number, String cardId, String picture, String email) {
        this.name = name;
        this.number = number;
        this.cardId = cardId;
        this.picture = picture;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    Context context;

    public CardItem(Context context){
        this.context=context;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
