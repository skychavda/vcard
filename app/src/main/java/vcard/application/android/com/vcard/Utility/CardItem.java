package vcard.application.android.com.vcard.Utility;

import android.content.Context;
import android.graphics.Bitmap;

public class CardItem {
    private String name;
    private String number;
    private int cardId;
    private int picture;

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
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
