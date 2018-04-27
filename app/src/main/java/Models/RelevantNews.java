package Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Aayushi.Garg on 09-11-2017.
 */

public class RelevantNews  implements Serializable{
    @SerializedName("Article")
    public List<Article> Article;
    public List<Article> getArticle(){
        return this.Article;
    }

}
