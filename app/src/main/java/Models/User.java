package Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Aayushi.Garg on 20-09-2017.
 */

public class User implements Serializable {

    @SerializedName("Id")
    public long Id;

    @SerializedName("PartnerId ")
    public String PartnerId;

    @SerializedName("Name ")
    public String Name;

    @SerializedName("Email ")
    public String Email;

    @SerializedName("PhoneNumber ")
    public String PhoneNumber;

    @SerializedName("LoginType ")
    public SocialLoginType LoginType ;

    @SerializedName("PicUrl ")
    public String PicUrl;

   public long getId(){
       return this.getId();
   }
    public String getPartnerId(){
        return this.PartnerId;
    }
    public String getName(){
        return this.Name;
    }
    public String getEmail(){
        return this.Email;
    }
    public String getPhoneNumber(){
        return this.PhoneNumber;
    }
    public String getPicUrl(){
        return this.PicUrl;
    }
    public SocialLoginType getLoginType(){
        return  this.LoginType;
    }




}
