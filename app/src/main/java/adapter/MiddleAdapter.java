package adapter;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;

public class MiddleAdapter extends RecyclerView.Adapter<MiddleAdapter.RecyclerViewHolder> implements Parcelable {
    private Context context;
    private String[] middleList;
    private OnItemClickListener mListener;

    public MiddleAdapter(Context context, String[] middleList) {
        this.context = context;
        this.middleList = middleList;
    }

    protected MiddleAdapter(Parcel in) {
        middleList = in.createStringArray();
    }

    public static final Creator<MiddleAdapter> CREATOR = new Creator<MiddleAdapter>() {
        @Override
        public MiddleAdapter createFromParcel(Parcel in) {
            return new MiddleAdapter(in);
        }

        @Override
        public MiddleAdapter[] newArray(int size) {
            return new MiddleAdapter[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(middleList);
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private Button btnMiddle;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            btnMiddle = itemView.findViewById(R.id.btnMiddle);
        }
    }

    @NonNull
    @Override
    public MiddleAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_middle_item, parent, false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MiddleAdapter.RecyclerViewHolder viewHolder, int position) {
        //각 버튼에 타이틀 배치
        viewHolder.btnMiddle.setText(middleList[position]);
    }

    @Override
    public int getItemCount() {
        return middleList.length;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener (OnItemClickListener listener) {
        this.mListener = listener;
    }

}
