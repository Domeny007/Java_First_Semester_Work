package servlets.entityServlets.userSerlvlets;

import database.dao.UserDao;
import database.entity.User;
import exceptions.DbException;
import utils.Const;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static utils.DatabaseUtils.DbWrapper.getConnection;

@WebServlet(value = "/delete-user")
public class DeleteUser extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Установка кодировки
        Const.setRightEncoding(req,resp);

        User user = (User) req.getSession().getAttribute(Const.KEY_CURRENT_USER);

        deleteUser(user.getId());

        RequestDispatcher requestDispatcher = req.getRequestDispatcher(req.getServletContext().getContextPath() + "/exit");

        requestDispatcher.forward(req,resp);

    }

    private void deleteUser(Integer userId) {

        try {

            UserDao userDao = new UserDao(getConnection());

            userDao.delete(userId);


        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
