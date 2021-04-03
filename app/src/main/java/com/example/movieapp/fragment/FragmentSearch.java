package com.example.movieapp.fragment;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
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

import MovieInfoDAO.TMDBDAO;
import adapter.BottomRecyclerViewAdapter;
import adapter.SearchAdapter;
import model.MovieInfo;

import com.example.movieapp.SearchContent;
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

    private SearchAdapter searchAdapter;
    private SearchContent searchContent;
    private TMDBDAO tmdbdao;

    private androidx.appcompat.widget.SearchView.OnQueryTextListener queryTextListener;


    private ArrayList<MovieInfo> arrayList = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof Activity) {
            this.activity = (Activity) context;
            arrayList = searchDataList;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_drawer_search, container, false);

        findViewByIdFunc(view);

        //initView();

        //어뎁터 만들기
        makeAdapter(container);
        //어뎁터 세팅하기
        settingAdapterDataList(arrayList);
        //이벤트 처리하기
        eventHandler();


        return view;
    }

//    private void initView() {
//
//        arrayList =tmdbdao.getMovieList();
//        drawerRcyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        ArrayList<MovieInfo> movieItem = new ArrayList<>();
//
//        for (int i = 0; i< arrayList.size();i++){
//            MovieInfo mi = arrayList.get(i);
//            movieItem.add(mi);
//        }
//        searchAdapter = new SearchAdapter(movieItem, getContext());
//        drawerRcyclerView.setAdapter(searchAdapter);
//    }

    //리사이클러뷰에 어뎁터 세팅
    private void settingAdapterDataList(ArrayList<MovieInfo> searchDataList) {

        searchAdapter.setSearchList(searchDataList);
        drawerRcyclerView.setAdapter(searchAdapter);
        searchAdapter.notifyDataSetChanged();

    }
    //어뎁터 만들기
    private void makeAdapter(ViewGroup container) {

        searchAdapter = new SearchAdapter(arrayList, context);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(container.getContext());

        drawerRcyclerView.setLayoutManager(linearLayoutManager);
        drawerRcyclerView.setAdapter(searchAdapter);


        //SearchAdapter searchAdapter = new SearchAdapter(searchDataList,getContext());

    }

    //이벤트 처리하기
    public void eventHandler() {

        //버튼을 눌렀을 때 프레그먼트 전환
        ivbBack.setOnClickListener(v -> {

            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentProfile = new FragmentProfile();

            fragmentTransaction.replace(R.id.frmDrawer, fragmentProfile);
            fragmentTransaction.commit();
        });


    }


    /*//
    public ArrayList<MovieInfo> filter(List<MovieInfo> seachList, String query) {
        query = query.toLowerCase();

        final ArrayList<MovieInfo> filteredNoticeList = new ArrayList<>();
        if (query != null && !query.equals("")) {
            for (MovieInfo mi : seachList) {
                final String title = mi.getTitle().toLowerCase();
                final String overview = mi.getOverview().toLowerCase();
                if (title.contains(query)) {
                    filteredNoticeList.add(mi);
                }else if(overview.contains(query)){
                    filteredNoticeList.add(mi);
                }
            }
        }
        return filteredNoticeList;
    }*/



    //객체찾기
    public void findViewByIdFunc(View view) {

        drawerRcyclerView = view.findViewById(R.id.drawerRcyclerView);
        searchView = view.findViewById(R.id.searchView);
        frmDrawer = view.findViewById(R.id.frmDrawer);
        ivbBack = view.findViewById(R.id.ivbBack);

    }
/* implement textwark>>?

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        searchAdapter.getFilter().filter(charSequence);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
*/

}
