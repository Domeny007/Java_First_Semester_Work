package servlets.main;

import utils.Const;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(value = "/exit")
public class Exit extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Установка кодировки
        Const.setRightEncoding(req,resp);

//        удаляем сессию
        HttpSession session = req.getSession();
        session.invalidate();

//        удаляем куки
        Cookie[] cookies = req.getCookies();
        for(Cookie cookie : cookies){
            switch (cookie.getName()){
                case Const.KEY_REMEMBER_COOKIE:
                    Const.eraseCookie(cookie,resp);
                    break;
                case Const.KEY_RIGHTS:
                    Const.eraseCookie(cookie,resp);
                    break;
                case Const.KEY_COOKIE_ID:
                    Const.eraseCookie(cookie,resp);
                    break;
            }

        }

        resp.sendRedirect(getServletContext().getContextPath() + "index.jsp");


    }
}
