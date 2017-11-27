package servlets.entityServlets.movieServlets;

import database.dao.MovieDao;
import database.entity.Movie;
import exceptions.DbException;
import utils.CheckUtils.AddMovieCheck;
import utils.Const;
import utils.DatabaseUtils.DbWrapper;
import utils.DateUtil;
import utils.SessionUtils.MovieSessionUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/add-movie")
public class AddMovie extends HttpServlet {

    private String name;

    private String subscription;

    private String year;

    private String budget;

    private String money;

    private String photo;

    private String actors;

    private String directors;

    private String genres;

    private String countries;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Установка кодировки
        Const.setRightEncoding(req, resp);

        name = req.getParameter(Const.KEY_MOVIE_NAME);

        subscription = req.getParameter(Const.KEY_MOVIE_SUBSCRIPTION);

        year = req.getParameter(Const.KEY_MOVIE_YEAR);

        budget = req.getParameter(Const.KEY_MOVIE_BUDGET);

        money = req.getParameter(Const.KEY_MOVIE_MONEY);

        photo = req.getParameter(Const.KEY_MOVIE_PHOTO);

        actors = req.getParameter(Const.KEY_MOVIE_ACTORS);

        directors = req.getParameter(Const.KEY_MOVIE_DIRECTORS);

        countries = req.getParameter(Const.KEY_MOVIE_COUNTRIES);

        genres = req.getParameter(Const.KEY_MOVIE_GENRES);

        if (AddMovieCheck.checkMovieData(req,name,subscription, photo, budget, money,
                year, actors, directors, countries, genres)) {

            addMovie(req);

            MovieSessionUtil.clearSession(req);

            resp.sendRedirect(getServletContext().getContextPath() + "/personal_page");

        } else {

            new MovieSessionUtil().addDataToSession(req);

            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/addInstance/addMovie.jsp");
            requestDispatcher.forward(req,resp);
        }

    }

    private void addMovie(HttpServletRequest request) {

        try {

            HttpSession session = request.getSession();

            session.setAttribute(Const.POPUP_DATA,"Добавлен новый фильм");

            Movie movie = new Movie();

            movie.setName(name);
            movie.setSubscription(subscription);
            movie.setYear(DateUtil.parseYearDateFromStr(year));
            movie.setBudget(Integer.valueOf(budget));
            movie.setMoney(Integer.valueOf(money));
            movie.setPhoto(photo);
            movie.setActorsId(getBindList(actors));
            movie.setDirectorsId(getBindList(directors));
            movie.setCountriesId(getBindList(countries));
            movie.setGenresId(getBindList(genres));

            MovieDao movieDao = new MovieDao(DbWrapper.getConnection());

            movieDao.save(movie);

        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private List<Integer> getBindList(String list){
        List<Integer> bindList = new ArrayList<>();
        String[] listStr = list.split(Const.SEPARATOR);
        for(String item : listStr){
            if(!item.equals("")){
                bindList.add(Integer.valueOf(item));
            }
        }
        return bindList;
    }



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Установка кодировки
        Const.setRightEncoding(req, resp);

        HttpSession session = req.getSession();

        String fromPage = req.getParameter(Const.ADD_MOVIE_PAGE);

        if(Const.ADD_MOVIE_PAGE.equals(fromPage)){
            String checkSession = (String) session.getAttribute(Const.KEY_MOVIE_ADD_SERVLET);
            if(checkSession != null){
                MovieSessionUtil.clearSession(req);
            }
            session.setAttribute(Const.KEY_MOVIE_ADD_SERVLET,"true");
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/addInstance/addMovie.jsp");
        requestDispatcher.forward(req,resp);
    }

}