package servlets;

import utils.Const;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SessionFilter  implements Filter {

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

        Object object = session.getAttribute(Const.KEY_CURRENT_USER);

        if(object != null){
            filterChain.doFilter(req,resp);
        }else {
            resp.sendRedirect(req.getServletContext().getContextPath() + "/index.jsp");
        }

    }

    @Override
    public void destroy() {

    }
}
