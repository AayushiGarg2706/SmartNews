package Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Aayushi.Garg on 09-11-2017.
 */

public class SearchArticleResponse implements Serializable {



    @SerializedName("Article")
    public Article Article;
    @SerializedName("RelevantNews")
    public List<Article> RelevantNews ;
    public Article getArticle(){
        return this.Article;
    }
    public List<Article> getRelevantNews(){
        return this.RelevantNews;
    }


}
