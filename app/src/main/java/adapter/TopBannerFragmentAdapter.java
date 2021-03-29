package adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.movieapp.MainActivity;
import com.example.movieapp.fragment.FragmentBanner1;
import com.example.movieapp.fragment.FragmentBanner2;
import com.example.movieapp.fragment.FragmentBanner3;
import com.example.movieapp.fragment.FragmentBanner4;
import com.example.movieapp.fragment.FragmentBanner5;

import java.util.ArrayList;

import MovieInfoDAO.UpcomingTMDBDAO;
import model.MovieInfo;

public class TopBannerFragmentAdapter extends FragmentStateAdapter {
    private int count;

    public TopBannerFragmentAdapter(@NonNull MainActivity mainActivity, int count) {
        super(mainActivity);
        this.count = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        //프래그먼트 배너 1~5 배치(0~4)
        int index = position % count;

        switch(index) {
            case 0: return FragmentBanner1.newInstance(index+1);
            case 1: return FragmentBanner2.newInstance(index+1);
            case 2: return FragmentBanner3.newInstance(index+1);
            case 3: return FragmentBanner4.newInstance(index+1);
            case 4: return FragmentBanner5.newInstance(index+1);
            default:
                Log.e("FragmentAdapter","FragmentAdapter Error....");
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 200;
    }
}
