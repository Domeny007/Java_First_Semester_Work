package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.dao.CommandDAO;

@WebServlet({"/history"})
public class HistoryServlet extends HttpServlet {
	
	private static final long serialVersionUID = -5884492115657275984L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("commands", CommandDAO.INSTANCE.select());
        getServletContext().getRequestDispatcher("/history.jsp").forward(req, resp);
	}
}
