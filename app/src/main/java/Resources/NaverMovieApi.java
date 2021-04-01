package Resources;

import model.NaverMovie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface NaverMovieApi {
    @Headers({"X-Naver-Client-Id: b3KcUKRLG5zSTrfcbLHR", "X-Naver-Client-Secret: 8jX8yOar6w"})
    @GET("search/movie.json")
    Call<NaverMovie> searchNaverMovie(
            @Query("query") String query,
            @Query("display") int display,
            @Query("yearfrom") String yearfrom,
            @Query("yearto") String yearto
    );
}
