package com.example.movieapp;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.MockView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearcViewhHolder> {
    private Context context;
    private ArrayList<SearchData> searchList;
    private SearchData searchData;

    private OnItemSelectedInterface onItemSelected;

    public SearchAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public SearcViewhHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_list, parent, false);
        SearcViewhHolder searcViewhHolder = new SearcViewhHolder(view);
        context = parent.getContext();

        return searcViewhHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearcViewhHolder holder, int position) {
        Bitmap movieImg = getMoviePhoto(context, Integer.parseInt(searchList.get(position).getMoviePoster()), 200);

        holder.tvMovieName.setText(searchData.getMovieTitle());
        String movieInfo = searchData.getMovieInfo();
        movieInfo = movieInfo.replace("", "");
        holder.tvMovieInfo.setText(movieInfo);

        if (searchData.getMoviePoster() != null) {
            String url = "";

        }

        holder.itemView.setOnClickListener(view -> {
            String movieTile = searchData.getMovieTitle();
            String moviePoster = searchData.getMoviePoster();
            //searchList.onItemSelected(view,position,movieTile,moviePoster);
        });

        holder.tvMovieInfo.setOnClickListener(view -> {
            String movieTile = searchData.getMovieTitle();
            String moviePoster = searchData.getMoviePoster();
            //searchList.onItemSelected(view,position,movieTile,moviePoster);
        });

    }


    //이미지 사이즈 맞추기
    private Bitmap getMoviePhoto(Context context, int parseInt, int size) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        ContentResolver contentResolver = context.getContentResolver();

        //가져올 파일경로 자리였으니 api주소를 여기에 넣으면 되는 것인가,,,,
        Uri uri = Uri.parse("" + parseInt);


        if (uri != null) {
            ParcelFileDescriptor parcelFileDescriptor = null;
            try {
                parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "");
                options.inJustDecodeBounds = true;

                int scale = 0;
                if (options.outHeight > size || options.outWidth > size) {
                    scale = (int) Math.pow(2, (int) Math.round(Math.log(size / (double) Math.max(options.outHeight, options.outWidth)) / Math.log(0.5)));
                }
                options.inJustDecodeBounds = false;
                options.inSampleSize = scale;

                Bitmap bitmap = BitmapFactory.decodeFileDescriptor(parcelFileDescriptor.getFileDescriptor(), null, options);

                if (bitmap != null) {
                    if (options.outWidth != size || options.outHeight != size) {
                        Bitmap tmp = Bitmap.createScaledBitmap(bitmap, size, size, true);
                        bitmap.recycle();
                        bitmap = tmp;
                    }
                }
                return bitmap;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    parcelFileDescriptor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    @Override
    public int getItemCount() {
        return (searchList != null) ? searchList.size() : 0;
    }


    //내부클래스 만들기
    public class SearcViewhHolder extends RecyclerView.ViewHolder {
        private ImageView ivPost;
        private TextView tvMovieName, tvMovieInfo;

        public SearcViewhHolder(@NonNull View itemView) {
            super(itemView);
            ivPost = itemView.findViewById(R.id.ivPost);
            tvMovieName = itemView.findViewById(R.id.tvMovieName);
            tvMovieInfo = itemView.findViewById(R.id.tvMovieInfo);

        }
    }

    public interface OnItemSelectedInterface {
        void onItemSelected(View view, int position, String movieTitle, String moviePoster);
    }
}
