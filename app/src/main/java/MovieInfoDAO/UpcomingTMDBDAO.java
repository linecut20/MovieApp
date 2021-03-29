package MovieInfoDAO;

import android.content.Context;
import android.os.AsyncTask;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.lang.reflect.Array;
import java.util.ArrayList;

import model.MovieInfo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UpcomingTMDBDAO extends AsyncTask<String, Void, MovieInfo[]> {
    private ArrayList<MovieInfo> list = new ArrayList<>();
    private Context context;
    public ArrayList<MovieInfo> getList() {
        return list;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //upcoming의 json 결과물을 추출(메인배너용)
    @Override
    protected MovieInfo[] doInBackground(String... strings) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/upcoming?api_key=3816c409634358e152e19eb237829a50&language=ko-KR&page=1")
                .build();
        try {
            Response response = client.newCall(request).execute();
            Gson gson = new GsonBuilder().create();
            JsonParser parser = new JsonParser();
            JsonElement rootObject = parser.parse(response.body().charStream())
                    .getAsJsonObject().get("results");
            MovieInfo[] posts = gson.fromJson(rootObject, MovieInfo[].class);
            return posts;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(MovieInfo[] result) {
        super.onPostExecute(result);

        if (result.length > 0) {
            for (MovieInfo p : result) {
                list.add(p);
            }
        }

        ArrayList<Integer> imageList = new ArrayList<>();
        for(int i=0; i<5;i++) {
            String url = "https://image.tmdb.org/t/p/w500" + list.get(i).getBackdrop_path();

        }


    }
}
