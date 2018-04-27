package Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Aayushi.Garg on 20-09-2017.
 */
public enum  SocialLoginType implements Serializable {

    Unknown("Unknown" , 0),

    Facebook("Facebook",1),
    LinkedIn("LinkedIn ",2),
    GooglePlus("GooglePlus ",3),
    Twitter("Twitter ",4);



    @SerializedName("stringValue")
    private String stringValue;
    private int intValue;
    private SocialLoginType(String toString, int value) {
        stringValue = toString;
        intValue = value;
    }

    @Override
    public String toString() {
        return stringValue;
    }



}
