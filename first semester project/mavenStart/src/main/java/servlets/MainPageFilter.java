package servlets;

import utils.Const;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class MainPageFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;

        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        //Установка кодировки
        Const.setRightEncoding(req, resp);

        HttpSession session = req.getSession();

        Cookie[] cookies = req.getCookies();

        if (session.getAttribute(Const.KEY_REMEMBER_WHILE_SESSION) != null) {
            boolean userInSession = (boolean) session.getAttribute(Const.KEY_REMEMBER_WHILE_SESSION);
            if (userInSession) {
                resp.sendRedirect(req.getServletContext().getContextPath() + "/personal_page");
            }
        } else if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(Const.KEY_REMEMBER_COOKIE)) {
                    resp.sendRedirect(req.getServletContext().getContextPath() + "/authorization");
                    break;
                }
            }
        }

        filterChain.doFilter(req,resp);
    }


    @Override
    public void destroy() {

    }
}
