package Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Aayushi.Garg on 09-11-2017.
 */

public class Article implements Serializable {


    @SerializedName("Id ")
    public Long Id ;

    @SerializedName("Title ")
    public String Title ;

    @SerializedName("Author ")
    public String Author ;

    @SerializedName("Description ")
    public String Description ;


    @SerializedName("Language ")
    public Long Language ;

    @SerializedName("ArticleKey ")
    public String ArticleKey ;

    @SerializedName("Url ")
    public String Url ;

    @SerializedName("UrlToImage ")
    public String UrlToImage ;

    @SerializedName("PublishedAt ")
    public String PublishedAt ;
    @SerializedName("Provider ")
    public String Provider ;

    //HashKey
    @SerializedName("HashKey ")
    public String HashKey ;


    public Long getId(){
        return this.Id;
    }
    public String getTitle(){
        return this.Title;
    }
    public String getAuthor(){
        return this.Author;
    }
    public String getDescription(){
        return this.Description;
    }

    public String getArticleKey(){
        return this.ArticleKey;
    }

    public String getUrl(){
        return  this.Url;
    }

    public String getUrlToImage(){
        return this.UrlToImage;
    }

    public String getPublishedAt(){
        return this.PublishedAt;
    }
    public String getProvider(){
        return this.Provider;
    }
    public String getHashKey(){
        return this.HashKey;
    }
    public Long getLanguage(){
        return this.Language;
    }


}
