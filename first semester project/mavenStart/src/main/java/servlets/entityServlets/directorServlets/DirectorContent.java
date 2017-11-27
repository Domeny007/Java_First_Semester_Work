package servlets.entityServlets.directorServlets;

import database.dao.DirectorDao;
import database.entity.Director;
import database.entity.Review;
import database.entity.User;
import exceptions.DbException;
import utils.Const;
import utils.DatabaseUtils.DbWrapper;

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

@WebServlet(name = "directorContent", value = "/director_content")
public class DirectorContent extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Установка кодировки
        Const.setRightEncoding(req,resp);

        Object movieObjId = req.getSession().getAttribute(Const.KEY_DIRECTOR_ID);

        Integer directorId;

        if(movieObjId == null){
            directorId = Integer.valueOf(req.getParameter(Const.KEY_DIRECTOR_ID));
        } else {
            directorId = (Integer) movieObjId;
        }

        Director director = getDirector(directorId);

        setGeneralDirectorInf(director,req);

        HttpSession session = req.getSession();

        session.setAttribute(Const.KEY_CURRENT_DIRECTOR, director);

        List<Review> reviews = director.getReviews();

        session.setAttribute(Const.KEY_DIRECTOR_REVIEWS,reviews);

        User user = (User) session.getAttribute(Const.KEY_CURRENT_USER);

        Integer userMark = director.getUserMark(user.getId());

        session.setAttribute(Const.KEY_USER_MOVIE_MARK,userMark);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/directors/directorContent.jsp");
        requestDispatcher.forward(req,resp);

    }

    private Director getDirector(Integer directorId) {

        Director director = null;

        try {

            DirectorDao directorDao = new DirectorDao(DbWrapper.getConnection());

            director = directorDao.find(directorId);

        } catch (DbException e) {
            e.printStackTrace();
        }

        return director;
    }

    private void setGeneralDirectorInf(Director director, HttpServletRequest request){

        String birthday = director.getBirthdayStr();

        String motherland = director.getMotherland().getName();

        List<Integer> numList = new ArrayList<>();

        for(int i = 1; i <= 10; i++){
            numList.add(i);
        }

        HttpSession session = request.getSession();

        session.setAttribute(Const.KEY_DIRECTOR_BIRTHDAY,birthday);
        session.setAttribute(Const.KEY_DIRECTOR_MOTHERLAND,motherland);
        session.setAttribute(Const.KEY_NUMLIST,numList);

    }
}
