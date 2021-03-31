package adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.LikeData;
import com.example.movieapp.MainActivity;

import java.util.ArrayList;

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.LikeViewHolder> {
    private int layout;
    private ArrayList<LikeData> list;
    private View view;
    private Context context;

    public LikeAdapter(int layout, ArrayList<LikeData> list) {
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

        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
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
                if (list.size() != 0){
                    Toast.makeText(context,"정상 삭제되었습니다",Toast.LENGTH_SHORT).show();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public int getItemCount() {

        return (list != null)?list.size():0;

    }

    public class LikeViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public LikeViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
