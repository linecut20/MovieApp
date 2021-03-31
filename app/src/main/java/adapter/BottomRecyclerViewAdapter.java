package adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.MovieInfoDetail;
import com.example.movieapp.R;

import java.util.ArrayList;

import model.MovieInfo;

public class BottomRecyclerViewAdapter extends RecyclerView.Adapter<BottomRecyclerViewAdapter.RecyclerViewHolder> implements Parcelable {
    private ArrayList<MovieInfo> mMovieList;
    private Context mContext;

    //constructor
    public BottomRecyclerViewAdapter(Context context, ArrayList<MovieInfo> itemList) {
        this.mContext = context;
        this.mMovieList = itemList;
    }

    protected BottomRecyclerViewAdapter(Parcel in) {
    }

    public BottomRecyclerViewAdapter() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BottomRecyclerViewAdapter> CREATOR = new Creator<BottomRecyclerViewAdapter>() {
        @Override
        public BottomRecyclerViewAdapter createFromParcel(Parcel in) {
            return new BottomRecyclerViewAdapter(in);
        }

        @Override
        public BottomRecyclerViewAdapter[] newArray(int size) {
            return new BottomRecyclerViewAdapter[size];
        }
    };

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_bottom_item, parent, false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        //포스터만 출력.
        String url = "https://image.tmdb.org/t/p/w500" + mMovieList.get(position).getPoster_path();
        Glide.with(mContext)
                .load(url)
                .centerCrop()
                .crossFade()
                .into(holder.iv_main);

        //각 아이템 클릭 이벤트
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MovieInfoDetail.class);
                intent.putExtra("id", mMovieList.get(position).getId());
                intent.putExtra("title", mMovieList.get(position).getTitle());
                intent.putExtra("poster_path", mMovieList.get(position).getPoster_path());
                intent.putExtra("overview", mMovieList.get(position).getOverview());
                intent.putExtra("release_date", mMovieList.get(position).getRelease_date());
                intent.putExtra("vote_average", mMovieList.get(position).getVote_average());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.mMovieList.size();
    }


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_main;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            iv_main = (ImageView) itemView.findViewById(R.id.iv_main);
        }
    }

}
