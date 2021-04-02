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
import java.util.List;

import model.MovieInfo;

import static com.example.movieapp.MainActivity.searchDataList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearcViewhHolder>implements Filterable,Parcelable {

    private ArrayList<MovieInfo> searchList;
    private ArrayList<String> list;
    private Context context;
    ArrayList<String> unFilteredlist;
    ArrayList<String> filteredList;


    public SearchAdapter(ArrayList<MovieInfo> searchList, Context context) {
        this.searchList = searchList;
        this.context = context;
    }

    public SearchAdapter(ArrayList<MovieInfo> searchList, Context context, ArrayList<String> list) {
        this.searchList =searchList;
        this.context = context;
        this.unFilteredlist =list;
        this.filteredList =list;
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


        return new SearcViewhHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearcViewhHolder holder, int position) {
        String url = "https://image.tmdb.org/t/p/w500"+ searchList.get(position).getPoster_path() ;
        Glide.with(context)
                .load(url)
                .centerCrop()
                .crossFade()
                .into(holder.ivPost);

        holder.tvMovieTitle.setText(searchList.get(position).getTitle());
        holder.tvMovieInfo.setText(searchList.get(position).getOverview()+"... (더보기)");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MovieInfoDetail.class);
                MovieInfo movieInfo = searchList.get(position);
                intent.putExtra("movieInfo", movieInfo);
                context.startActivity(intent);
            }
        });

    }

    //이부분 중요!! 검색 리스트를 나오게하기 위해 꼭 필요
    public void setFilter(List<MovieInfo> searchIems) {
        searchDataList.clear();
        searchDataList.addAll(searchIems);
        notifyDataSetChanged();
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

}
