package adapter;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
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

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.RecyclerViewHolder> implements Parcelable {
    private ArrayList<MovieInfo> mMovieList;
    private Context mContext;

    //constructor
    public MainRecyclerViewAdapter(Context context, ArrayList<MovieInfo> itemList) {
        this.mContext = context;
        this.mMovieList = itemList;
    }

    protected MainRecyclerViewAdapter(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MainRecyclerViewAdapter> CREATOR = new Creator<MainRecyclerViewAdapter>() {
        @Override
        public MainRecyclerViewAdapter createFromParcel(Parcel in) {
            return new MainRecyclerViewAdapter(in);
        }

        @Override
        public MainRecyclerViewAdapter[] newArray(int size) {
            return new MainRecyclerViewAdapter[size];
        }
    };

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.main_list_item, parent, false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        //포스터만 출력하자.
        String url = "https://image.tmdb.org/t/p/w500" + mMovieList.get(position).getPoster_path();
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
