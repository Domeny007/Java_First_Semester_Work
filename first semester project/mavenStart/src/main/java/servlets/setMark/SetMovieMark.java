package servlets.setMark;

import database.dao.ActorDao;
import database.dao.DirectorDao;
import database.dao.MovieDao;
import database.entity.Actor;
import database.entity.Director;
import database.entity.Movie;
import exceptions.DbException;
import utils.DatabaseUtils.DbWrapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/setMovieMark")
public class SetMovieMark extends HttpServlet {

    private Integer mark;

    private Integer markId;

    private Integer userId;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        mark = Integer.valueOf(req.getParameter("mark"));

        userId = Integer.valueOf(req.getParameter("userId"));

        markId = Integer.valueOf(req.getParameter("markId"));

        String typeMark = req.getParameter("typeMark");

        switch (typeMark){
            case "movie":
                addMovieMark();
                break;

            case "actor":
                addActorMark();
                break;

            case "director":
                addDirectorMark();
                break;
        }


    }

    private void addMovieMark(){
        try {
            MovieDao movieDao = new MovieDao(DbWrapper.getConnection());

            Movie movie = movieDao.find(markId);

            movie.addUserMark(mark,markId,userId);

            movieDao.update(movie);

        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void addActorMark(){
        try {
            ActorDao actorDao = new ActorDao(DbWrapper.getConnection());

            Actor actor = actorDao.find(markId);

            actor.addUserMark(mark,markId,userId);

            actorDao.update(actor);

        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void addDirectorMark(){
        try {
            DirectorDao directorDao = new DirectorDao(DbWrapper.getConnection());

            Director director = directorDao.find(markId);

            director.addUserMark(mark,markId,userId);

            directorDao.update(director);

        } catch (DbException e) {
            e.printStackTrace();
        }
    }

}
