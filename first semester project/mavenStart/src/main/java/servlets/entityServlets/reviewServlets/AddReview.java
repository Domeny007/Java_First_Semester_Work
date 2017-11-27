package servlets.entityServlets.reviewServlets;

import database.dao.ActorDao;
import database.dao.DirectorDao;
import database.dao.MovieDao;
import database.entity.*;
import utils.Const;
import utils.DatabaseUtils.DbWrapper;
import database.dao.ReviewDao;
import exceptions.DbException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "/addReview")
public class AddReview extends HttpServlet{

    private Integer obj_id;

    private String type;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Установка кодировки
        Const.setRightEncoding(req,resp);

        addReview(req);

        switch (type){
            case "movie":
                req.getSession().setAttribute(Const.KEY_MOVIE_ID, obj_id);
                resp.sendRedirect(getServletContext().getContextPath() + "/movie_content");
                break;

            case "actor":
                req.getSession().setAttribute(Const.KEY_ACTOR_ID, obj_id);
                resp.sendRedirect(getServletContext().getContextPath() + "/actor_content");
                break;

            case "director":
                req.getSession().setAttribute(Const.KEY_DIRECTOR_ID, obj_id);
                resp.sendRedirect(getServletContext().getContextPath() + "/director_content");
                break;
        }



    }

    private void addReview(HttpServletRequest request) {

        try {

            HttpSession session = request.getSession();

            session.setAttribute(Const.POPUP_DATA,"Добавлено новое ревью");

            User writer = (User) session.getAttribute(Const.KEY_CURRENT_USER);

            String title = request.getParameter(Const.KEY_TITLE);

            String content = request.getParameter(Const.KEY_CONTENT);

            obj_id = Integer.valueOf(request.getParameter(Const.KEY_OBJ_ID));

            type = request.getParameter(Const.REVIEW_TYPE);

            Review review = new Review();

            review.setTitle(title);
            review.setContent(content);
            review.setWriter(writer);


            switch (type){
                case "movie":
                    Movie movie = new MovieDao(DbWrapper.getConnection()).find(obj_id);
                    review.setMovie(movie);
                    break;

                case "actor":
                    Actor actor = new ActorDao(DbWrapper.getConnection()).find(obj_id);
                    review.setActor(actor);
                    break;

                case "director":
                    Director director = new DirectorDao(DbWrapper.getConnection()).find(obj_id);
                    review.setDirector(director);
                    break;
            }


            ReviewDao movieReviewDao = new ReviewDao(DbWrapper.getConnection());

            movieReviewDao.save(review,type);


        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
