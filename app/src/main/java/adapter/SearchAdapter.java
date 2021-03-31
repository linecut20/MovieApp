package adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.MovieInfoDetail;
import com.example.movieapp.R;
import com.example.movieapp.SearchData;
import com.example.movieapp.fragment.FragmentSearch;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import model.MovieInfo;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearcViewhHolder>implements Filterable,Parcelable {

    private ArrayList<MovieInfo> searchList;
    private Context context;
    ArrayList<String> unFilteredlist;
    ArrayList<String> filteredList;

    public SearchAdapter(ArrayList<MovieInfo> searchList, Context context) {
        this.searchList = searchList;
        this.context = context;
    }

    protected SearchAdapter(Parcel in) {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }

    public static final Creator<SearchAdapter> CREATOR = new Creator<SearchAdapter>() {
        @Override
        public SearchAdapter createFromParcel(Parcel in) {
            return new SearchAdapter(in);
        }

        @Override
        public SearchAdapter[] newArray(int size) {
            return new SearchAdapter[size];
        }
    };


    @NonNull
    @Override
    public SearcViewhHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_list, parent, false);
        SearchAdapter.SearcViewhHolder searcViewhHolder = new SearchAdapter.SearcViewhHolder(view);
        context = parent.getContext();

        return searcViewhHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearcViewhHolder holder, int position) {
        String url = "https://image.tmdb.org/t/p/w500"+ searchList.get(position).getPoster_path() + searchList.get(position).getTitle() + searchList.get(position).getOverview() ;
        Glide.with(context)
                .load(url)
                .centerCrop()
                .crossFade()
                .into(holder.ivPost);
                //.into(holder.tvMovieTitle, holder.tvMovieInfo);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MovieInfoDetail.class);
                intent.putExtra("id", searchList.get(position).getId());
                intent.putExtra("title", searchList.get(position).getTitle());
                intent.putExtra("poster_path", searchList.get(position).getPoster_path());
                intent.putExtra("overview", searchList.get(position).getOverview());
                intent.putExtra("release_date", searchList.get(position).getRelease_date());
                intent.putExtra("vote_average", searchList.get(position).getVote_average());
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return (searchList != null) ? searchList.size() : 0;
    }

    public void setSearchList(ArrayList<MovieInfo> searchDataList) {
        this.searchList = searchList;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()){
                    filteredList = unFilteredlist;
                }else{
                    ArrayList<String> filteredList = new ArrayList<>();
                    for (String name : unFilteredlist){
                        if (name.toLowerCase().contains(charString.toLowerCase())){
                            filteredList.add(name);
                        }
                    }
                    filteredList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredList = (ArrayList<String>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    //내부클래스 만들기
    public class SearcViewhHolder extends RecyclerView.ViewHolder {
        private ImageView ivPost;
        private TextView tvMovieTitle, tvMovieInfo;

        public SearcViewhHolder(@NonNull View itemView) {
            super(itemView);
            ivPost = itemView.findViewById(R.id.ivPost);
            tvMovieTitle = itemView.findViewById(R.id.tvMovieTitle);
            tvMovieInfo = itemView.findViewById(R.id.tvMovieInfo);

        }
    }

    //---------------------------------------------------

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


}
