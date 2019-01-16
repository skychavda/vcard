package vcard.application.android.com.vcard.Utility;

import android.content.Context;
import android.graphics.Bitmap;

public class CardItem {
    private String name;
    private String number;
    private int cardId;
    private String picture;

    public CardItem(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;

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

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }
}
