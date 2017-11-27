package servlets.setMark;

import database.dao.ReviewDao;
import database.entity.Review;
import database.entity.User;
import exceptions.DbException;
import utils.Const;
import utils.DatabaseUtils.DbWrapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "/setReviewMark")
public class SetReviewMark extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer mark = Integer.valueOf(req.getParameter("mark"));

        Integer writerId = Integer.valueOf(req.getParameter("writerId"));

        Integer reviewId = Integer.valueOf(req.getParameter("reviewId"));

        HttpSession session = req.getSession();

        User user = (User) session.getAttribute(Const.KEY_CURRENT_USER);

        if(!writerId.equals(user.getId())) {
            try {
                ReviewDao reviewDao = new ReviewDao(DbWrapper.getConnection());

                Review review = reviewDao.find(reviewId);

                review.addUserMark(mark, reviewId, user.getId());

                reviewDao.update(review);

            } catch (DbException e) {
                e.printStackTrace();
            }
        }

    }

}
