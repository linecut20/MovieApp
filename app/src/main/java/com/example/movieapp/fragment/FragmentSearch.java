package com.example.movieapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.MainActivity;
import com.example.movieapp.R;
import com.example.movieapp.SearchAdapter;
import com.example.movieapp.SearchData;

import java.util.ArrayList;
import java.util.List;

import java.util.Objects;

public class FragmentSearch extends Fragment {
    private Context context;
    private Activity activity;

    private RecyclerView drawerRcyclerView;
    private FrameLayout frmDrawer;
    private SearchView searchBar;
    private Button btnSearch;
    private MainActivity mainActivity;
    private SearchAdapter searchAdapter;

    private List<SearchData> list;

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

        //setAdapter(container);

        eventHandler();


        return view;
    }

    private void setAdapter(ViewGroup container) {
        searchAdapter = new SearchAdapter(container.getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(container.getContext());

        drawerRcyclerView.setLayoutManager(linearLayoutManager);
        drawerRcyclerView.setAdapter(searchAdapter);

    }

    public void eventHandler() {

        drawerRcyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int itemTotalCount = recyclerView.getAdapter().getItemCount();
                if (lastVisibleItemPosition == (itemTotalCount - 1) && itemTotalCount % 20 == 0) {

                }

            }
        });

    }

    private void startMovieSearching(final String word) {

        list = new ArrayList<>();

        String query = word;
        query = query.replace(" ", "+");


    }

    public void findViewByIdFunc(View view) {

        drawerRcyclerView = view.findViewById(R.id.drawerRcyclerView);
        frmDrawer = view.findViewById(R.id.frmDrawer);
        searchBar = view.findViewById(R.id.searchBar);
        btnSearch = view.findViewById(R.id.btnSearch);

    }

    //프래그먼트 종료
    private void goToMain(){
        FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        fragmentManager.beginTransaction().remove(FragmentSearch.this).commit();
        fragmentManager.popBackStack();
    }
}
