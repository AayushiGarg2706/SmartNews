package Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Aayushi.Garg on 11-09-2017.
 */

public class GetArticleScan implements Serializable {

    @SerializedName("ArticleKey ")
    public String ArticleKey ;

    @SerializedName("Author ")
    public String Author ;

    @SerializedName("Description ")
    public String Description ;

    @SerializedName("Id ")
    public String Id ;

    @SerializedName("ImagePath ")
    public String ImagePath ;

    @SerializedName("Language ")
    public String Language ;

    @SerializedName("Title ")
    public String Title ;

    public String getArticleKey(){
        return this.ArticleKey;
    }
    public String getAuthor(){
        return this.Author;
    }
    public String getDescription(){
        return this.Description;
    }
    public String getId(){
        return this.getId();
    }
    public String getImagePath(){
        return this.ImagePath;
    }
    public String getLanguage(){
        return this.getLanguage();
    }
    public String getTitle(){
        return this.getTitle();
    }


}
