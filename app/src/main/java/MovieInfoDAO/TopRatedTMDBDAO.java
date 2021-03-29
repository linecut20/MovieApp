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

public class TopRatedTMDBDAO extends AsyncTask<String, Void, MovieInfo[]> {
    private ArrayList<MovieInfo> movieList = new ArrayList<>();

    public ArrayList<MovieInfo> getMovieList() {
        return movieList;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        progressDialog.setMessage("\t영화정보를 가져오는 중입니다");
//        //show dialog
//        progressDialog.show();
    }


    //top_rated 메뉴의 JSON result를 추출
    @Override
    protected MovieInfo[] doInBackground(String... strings) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/top_rated?api_key=3816c409634358e152e19eb237829a50&language=ko-KR&page=1")
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
//        progressDialog.dismiss();
        //ArrayList에 차례대로 집어 넣는다.
        if (result.length > 0) {
            for (MovieInfo p : result) {
                movieList.add(p);
            }
        }

    }

}
