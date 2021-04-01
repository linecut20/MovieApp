package adapter;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
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

public class TopRecyclerViewAdapter extends RecyclerView.Adapter<TopRecyclerViewAdapter.RecyclerViewHolder> implements Parcelable {
    private ArrayList<MovieInfo> list;
    private Context context;

    public TopRecyclerViewAdapter( Context context, ArrayList<MovieInfo> list) {
        this.list = list;
        this.context = context;
    }

    //상단 메인베너 뷰홀더 커스텀
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_main_banner;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_main_banner = itemView.findViewById(R.id.iv_main_banner);
        }
    }

    //뷰홀더 커스터마이징
    @NonNull
    @Override
    public TopRecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_top_item, parent, false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TopRecyclerViewAdapter.RecyclerViewHolder holder, int position) {
        //포스터만 출력하자.
        String url = "https://image.tmdb.org/t/p/w1280" + list.get(position).getBackdrop_path();
        Glide.with(context)
                .load(url)
                .centerCrop()
                .crossFade()
                .into(holder.iv_main_banner);
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
    protected TopRecyclerViewAdapter(Parcel in) {
    }
    public static final Creator<TopRecyclerViewAdapter> CREATOR = new Creator<TopRecyclerViewAdapter>() {
        @Override
        public TopRecyclerViewAdapter createFromParcel(Parcel in) {
            return new TopRecyclerViewAdapter(in);
        }

        @Override
        public TopRecyclerViewAdapter[] newArray(int size) {
            return new TopRecyclerViewAdapter[size];
        }
    };
}
