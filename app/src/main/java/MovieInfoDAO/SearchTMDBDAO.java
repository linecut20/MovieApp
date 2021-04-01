package MovieInfoDAO;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import model.MovieInfo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchTMDBDAO extends AsyncTask<String,Void, MovieInfo[]> {

    private String tag;
    private static ArrayList<MovieInfo> searchMovieList = new ArrayList<>();
    private int count;

    public SearchTMDBDAO(String tag, int count) {
        this.tag = tag;
        this.count = count;
    }

    @Override
    protected MovieInfo[] doInBackground(String... strings) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/search/company"+tag+"?api_key=3816c409634358e152e19eb237829a50&language=ko-KR&page="+count)
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
    protected void onPostExecute(MovieInfo[] movieInfos) {
        super.onPreExecute();
        if (movieInfos.length>0){
            for (MovieInfo mi : movieInfos){
                searchMovieList.add(mi);
            }
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
