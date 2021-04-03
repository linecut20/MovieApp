package adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.LikeActivity;
import com.example.movieapp.LikeData;
import com.example.movieapp.MainActivity;
import com.example.movieapp.MovieInfoDetail;
import com.example.movieapp.R;
import com.example.movieapp.util.DbOpenHelper;

import java.util.ArrayList;

import model.MovieInfo;

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.LikeViewHolder> {
    private int layout;
    private ArrayList<MovieInfo> list;
    private View view;
    private Context context;

    private DbOpenHelper dbOpenHelper;

    public LikeAdapter(int layout, ArrayList<MovieInfo> list) {
        this.layout = layout;
        this.list = list;
    }

    @NonNull
    @Override
    public LikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        LikeAdapter.LikeViewHolder likeViewHolder = new LikeAdapter.LikeViewHolder(view);

        context = parent.getContext();

        return likeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LikeViewHolder holder, int position) {

        String url = "https://image.tmdb.org/t/p/w500"+ list.get(position).getPoster_path() ;
        Glide.with(context)
                .load(url)
                .centerCrop()
                .crossFade()
                .into(holder.ivLikePost);

        holder.tvLikeTitle.setText(list.get(position).getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MovieInfoDetail.class);
                MovieInfo movieInfo = list.get(position);
                intent.putExtra("movieInfo", movieInfo);
                context.startActivity(intent);
            }
        });


        holder.ivLikePost.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                deleteLikeListFunction();
                return true;
            }
        });

    }

    private void deleteLikeListFunction() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("보고싶은 영화 삭제").setMessage("선택하신 영화를 삭제하시겠습니까?");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dbOpenHelper = new DbOpenHelper(context);
                dbOpenHelper.openLike();
                dbOpenHelper.deleteLikeColumns(list.get(id).getId());
                dbOpenHelper.close();

            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // 삭제 취소
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.show();
    }
    @Override
    public int getItemCount() {

        return (list != null)?list.size():0;

    }

    public class LikeViewHolder extends RecyclerView.ViewHolder {
        ImageView ivLikePost;
        TextView tvLikeTitle;
        public LikeViewHolder(@NonNull View itemView) {
            super(itemView);
            ivLikePost = itemView.findViewById(R.id.ivLikePost);
            tvLikeTitle = itemView.findViewById(R.id.tvLikeTitle);

        }
    }
}
