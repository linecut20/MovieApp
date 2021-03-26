package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;

import java.util.ArrayList;

import model.MovieInfo;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.RecyclerViewHolder> {
    private ArrayList<MovieInfo> mMovieList;
    private LayoutInflater mInflate;
    private Context mContext;

    //constructor
    public MainRecyclerViewAdapter(Context context, ArrayList<MovieInfo> itemList) {
        this.mContext = context;
        this.mInflate = LayoutInflater.from(context);
        this.mMovieList = itemList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.main_list_item, parent, false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        //포스터만 출력하자.
        String url = "http://image.tmdb.org/t/p/w500" + mMovieList.get(position).getPoster_path();
        Glide.with(mContext)
                .load(url)
                .centerCrop()
                .crossFade()
                .into(holder.iv_main);
    }

    @Override
    public int getItemCount() {
        return this.mMovieList.size();
    }


    //뷰홀더 - 따로 클래스 파일로 만들어도 된다.
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_main;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            iv_main = (ImageView) itemView.findViewById(R.id.iv_main);
        }
    }
}
