package Resources;

import model.NaverMovie;

public interface OnGetNaverMovieCallback {
    void onSuccess(NaverMovie.ItemsBean movieItem);

    void onError(Boolean research);


}
