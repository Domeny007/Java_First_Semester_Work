package utils;

import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public class Const {

    // для БД
    public static final String DATABASE = "movie_app";

    public static final String SCHEMA = "public";

    // доп константы
    public static final Integer START_PAGE = 0;
    public static final String COUNTRIES_DEFAULT_MODE = "usual";

    public static final String POPUP_DATA = "popup_data";
    public static final String ADD_MOVIE_PAGE = "addMoviePage";

    public static final String KEY_OBJ_ID = "obj_id";
    public static final String REVIEW_TYPE = "review_type";

    public static final String SEPARATOR = ",";

    public static final String KEY_NUMLIST = "numList";
    public static final String KEY_COUNTRIES = "countries";

    public static final String KEY_MOVIE_ADD_SERVLET = "movieAddServ";

    //marks
    public static final String KEY_USER_MOVIE_MARK = "user_movie_mark";
    public static final String KEY_NUM_PAGE = "numPage";

//  константы для user
    public static final String KEY_EMAIL = "email";
    public static final String KEY_USERNAME= "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PASSWORD_AGAIN = "passwordAgain";
    public static final String KEY_COUNTRY = "countryId";
    public static final String KEY_GENDER = "gender";


//  константы для review
    public static final String KEY_REVIEW_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_CONTENT = "content";

    public static final String KEY_CURRENT_REVIEW = "review";

    //  константы для movie
    public static final String KEY_MOVIE_ID = "movie_id";
    public static final String KEY_MOVIE_NAME = "movie_name";
    public static final String KEY_MOVIE_SUBSCRIPTION = "movie_subscription";
    public static final String KEY_MOVIE_YEAR = "movie_year";
    public static final String KEY_MOVIE_BUDGET = "movie_budget";
    public static final String KEY_MOVIE_MONEY = "movie_money";
    public static final String KEY_MOVIE_MARK = "movie_mark";
    public static final String KEY_MOVIE_PHOTO = "movie_photo";

    public static final String KEY_CURRENT_MOVIE = "movie";

    public static final String KEY_MOVIE_REVIEWS = "movie_reviews";
    public static final String KEY_ACTOR_REVIEWS = "actor_reviews";
    public static final String KEY_DIRECTOR_REVIEWS = "director_reviews";

    public static final String KEY_MOVIE_ACTORS = "movie_actors";
    public static final String KEY_MOVIE_DIRECTORS = "movie_directors";
    public static final String KEY_MOVIE_COUNTRIES = "movie_countries";
    public static final String KEY_MOVIE_GENRES = "movie_genres";

    public static final String KEY_MOVIE_ACTORS_NAMES = "actorsNames";
    public static final String KEY_MOVIE_DIRECTORS_NAMES = "directorsNames";
    public static final String KEY_MOVIE_COUNTRIES_NAMES = "countriesNames";
    public static final String KEY_MOVIE_GENRES_NAMES = "genresNames";

    //  константы для actor
    public static final String KEY_ACTOR_ID = "actor_id";
    public static final String KEY_ACTOR_NAME = "actor_name";
    public static final String KEY_ACTOR_SURNAME = "actor_surname";
    public static final String KEY_ACTOR_BIRTHDAY = "actor_birthday";
    public static final String KEY_ACTOR_MOTHERLAND = "actor_motherland";
    public static final String KEY_ACTOR_PHOTO = "actor_photo";
    public static final String KEY_ACTOR_MARK = "actor_mark";

    public static final String KEY_CURRENT_ACTOR = "actor";

    //  константы для director
    public static final String KEY_DIRECTOR_ID = "director_id";
    public static final String KEY_DIRECTOR_NAME = "director_name";
    public static final String KEY_DIRECTOR_SURNAME = "director_surname";
    public static final String KEY_DIRECTOR_BIRTHDAY = "director_birthday";
    public static final String KEY_DIRECTOR_MOTHERLAND = "director_motherland";
    public static final String KEY_DIRECTOR_PHOTO = "director_photo";
    public static final String KEY_DIRECTOR_MARK = "director_mark";

    public static final String KEY_CURRENT_DIRECTOR = "director";

    //константы для people
    public static final String KEY_ACTOR_TYPE = "actor";
    public static final String KEY_DIRECTOR_TYPE = "director";

    //  константы для country
    public static final String KEY_COUNTRY_ID = "country_id";
    public static final String KEY_COUNTRY_NAME = "country_name";

    //  константы для genre
    public static final String KEY_GENRE_ID = "genre_id";
    public static final String KEY_GENRE_NAME = "genre_name";


//    константы для авторизации пользователя
    public static final String KEY_RIGHTS = "userRights";
    public static final String KEY_CURRENT_USER = "current_user";
    public static final String KEY_REMEMBER_WHILE_SESSION = "rememberInSession";

//    константы для сохрания куки пользователя
    public static final String KEY_COOKIE_ID = "rememberKey";
    public static final String KEY_REMEMBER_COOKIE = "cookieRemember";

    private static final String RIGHT_ENCONDING = "UTF-8";




//  метод для установки кодировки в сервлете
    public static void setRightEncoding(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        resp.setCharacterEncoding(RIGHT_ENCONDING);
        req.setCharacterEncoding(RIGHT_ENCONDING);
        resp.setContentType("text/html; charset=" + RIGHT_ENCONDING);
    }

//    метод для удаления куки
    public static void eraseCookie(Cookie cookie, HttpServletResponse resp) {
        if(cookie != null) {
            cookie.setValue("");
            cookie.setPath("/");
            cookie.setMaxAge(0);
            resp.addCookie(cookie);
        }

    }
}
