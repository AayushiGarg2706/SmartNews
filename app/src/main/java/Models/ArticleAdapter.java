package Models;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.logischtech.smartnews.R;

import java.util.List;

/**
 * Created by Aayushi.Garg on 02-11-2017.
 */

public class ArticleAdapter extends BaseAdapter {

    private Activity activity;
    private List<Article> data;
    private static LayoutInflater inflater=null;


    public ArticleAdapter(Activity a, List<Article> relatedarticles ) {
        activity = a ;
        data = relatedarticles ;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      //  imageLoader=new ImageLoader(activity.getApplicationContext());
    }



    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.related_news_layout, null);
     //   Article article = new Article();
       // article = data[];
        final Article s = (Article) this.getItem(position);
        ImageView img = (ImageView)vi.findViewById(R.id.imgr);
        TextView tvr = (TextView)vi.findViewById(R.id.tvr);
        tvr.setText(s.getTitle());




        return vi;
    }
}
