package com.example.movieapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.MainActivity;
import com.example.movieapp.R;

import adapter.BottomRecyclerViewAdapter;
import adapter.SearchAdapter;
import model.MovieInfo;

import com.example.movieapp.SearchData;

import java.util.ArrayList;
import java.util.List;

import static com.example.movieapp.MainActivity.searchDataList;

public class FragmentSearch extends Fragment {
    private Context context;
    private Activity activity;

    private RecyclerView drawerRcyclerView;
    private FrameLayout frmDrawer;
    private SearchView searchView;
    private EditText edtSearch;
    private Button btnSearch;
    private FragmentProfile fragmentProfile;
    private FragmentSearch fragmentSearch;
    private ImageButton ivbBack;

    private MainActivity mainActivity = new MainActivity();
    private SearchAdapter searchAdapter;

    private ArrayList<MovieInfo> arrayList = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof Activity) {
            this.activity = (Activity) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_drawer_search, container, false);

        findViewByIdFunc(view);

        makeAdapter(container);

        settingAdapterDataList(searchDataList);

        eventHandler();


        return view;
    }

    private void settingAdapterDataList(ArrayList<MovieInfo> searchDataList) {
        searchAdapter.setSearchList(searchDataList);
        drawerRcyclerView.setAdapter(searchAdapter);
        searchAdapter.notifyDataSetChanged();

    }

    private void makeAdapter(ViewGroup container) {

        searchAdapter = new SearchAdapter(searchDataList,context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(container.getContext());

        drawerRcyclerView.setLayoutManager(linearLayoutManager);
        drawerRcyclerView.setAdapter(searchAdapter);
    }

    public void eventHandler() {

        /*drawerRcyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int itemTotalCount = recyclerView.getAdapter().getItemCount();
                if (lastVisibleItemPosition == (itemTotalCount - 1) && itemTotalCount % 20 == 0) {

                }

            }
        });
*/
        ivbBack.setOnClickListener(v -> {

            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentProfile = new FragmentProfile();

            fragmentTransaction.replace(R.id.frmDrawer, fragmentProfile);
            fragmentTransaction.commit();
        });

    }

/*
    private void startMovieSearching(final String word) {



        String query = word;
        query = query.replace(" ", "+");


    }
*/

    public void findViewByIdFunc(View view) {

        drawerRcyclerView = view.findViewById(R.id.drawerRcyclerView);
        searchView = view.findViewById(R.id.searchView);
        frmDrawer = view.findViewById(R.id.frmDrawer);
        ivbBack = view.findViewById(R.id.ivbBack);

    }

}
