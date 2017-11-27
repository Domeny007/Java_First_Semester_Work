package utils.SessionUtils;

import utils.Const;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class MovieSessionUtil {

    private String name;

    private String subscription;

    private String year;

    private String budget;

    private String money;

    private String photo;

    private void getParameters(HttpServletRequest req){
        name = req.getParameter(Const.KEY_MOVIE_NAME);

        subscription = req.getParameter(Const.KEY_MOVIE_SUBSCRIPTION);

        year = req.getParameter(Const.KEY_MOVIE_YEAR);

        budget = req.getParameter(Const.KEY_MOVIE_BUDGET);

        money = req.getParameter(Const.KEY_MOVIE_MONEY);

        photo = req.getParameter(Const.KEY_MOVIE_PHOTO);
    }

    public void addDataToSession(HttpServletRequest req){

        getParameters(req);

        HttpSession session = req.getSession();



        session.setAttribute(Const.KEY_MOVIE_NAME,name);
        session.setAttribute(Const.KEY_MOVIE_SUBSCRIPTION,subscription);
        session.setAttribute(Const.KEY_MOVIE_BUDGET,budget);
        session.setAttribute(Const.KEY_MOVIE_MONEY,money);
        session.setAttribute(Const.KEY_MOVIE_YEAR,year);
        session.setAttribute(Const.KEY_MOVIE_PHOTO,photo);

    }


    public static void clearSession(HttpServletRequest request){


        HttpSession session = request.getSession();

        session.removeAttribute(Const.KEY_MOVIE_ACTORS);
        session.removeAttribute(Const.KEY_MOVIE_ACTORS_NAMES);

        session.removeAttribute(Const.KEY_MOVIE_DIRECTORS);
        session.removeAttribute(Const.KEY_MOVIE_DIRECTORS_NAMES);

        session.removeAttribute(Const.KEY_MOVIE_COUNTRIES);
        session.removeAttribute(Const.KEY_MOVIE_COUNTRIES_NAMES);

        session.removeAttribute(Const.KEY_MOVIE_GENRES);
        session.removeAttribute(Const.KEY_MOVIE_GENRES_NAMES);

        session.removeAttribute(Const.KEY_MOVIE_NAME);
        session.removeAttribute(Const.KEY_MOVIE_SUBSCRIPTION);
        session.removeAttribute(Const.KEY_MOVIE_BUDGET);
        session.removeAttribute(Const.KEY_MOVIE_MONEY);
        session.removeAttribute(Const.KEY_MOVIE_YEAR);
        session.removeAttribute(Const.KEY_MOVIE_PHOTO);

        session.removeAttribute(Const.KEY_MOVIE_ADD_SERVLET);
    }
}
