package servlets.entityServlets.dopEntityServlets;

import database.dao.CountryDao;
import database.entity.Country;
import exceptions.DbException;
import utils.DatabaseUtils.DbWrapper;
import utils.Const;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "/add-country")
public class AddCountry extends HttpServlet {

    private String name;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Установка кодировки
        Const.setRightEncoding(req,resp);

        name = req.getParameter(Const.KEY_COUNTRY_NAME);

        addCountry(req);

        resp.sendRedirect(getServletContext().getContextPath() + "personal_page");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Const.setRightEncoding(req, resp);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/addInstance/addCountry.jsp");
        requestDispatcher.forward(req,resp);
    }

    private void addCountry(HttpServletRequest request) {

        try {

            HttpSession session = request.getSession();

            session.setAttribute(Const.POPUP_DATA,"Добавлена страна");

            Country country = new Country();

            country.setName(name);

            CountryDao countryDao = new CountryDao(DbWrapper.getConnection());

            countryDao.save(country);

        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
