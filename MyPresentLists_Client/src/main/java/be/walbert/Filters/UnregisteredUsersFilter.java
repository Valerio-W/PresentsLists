package be.walbert.Filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UnregisteredUsersFilter extends HttpFilter implements Filter {

    public UnregisteredUsersFilter() {
        super();
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        String idParam = request.getParameter("id");

        if (session != null && session.getAttribute("user") != null) {
            chain.doFilter(request, response);
        } else if (idParam != null) {
            // No user session, but "id" parameter is in the URL
            int id = Integer.parseInt(idParam);
            session = httpRequest.getSession(true);  
            session.setAttribute("id", id); // Save the "id" parameter in session
            res.sendRedirect("/MyPresentLists_Client/Signin"); // Redirect to SignIn
        } else {
            // No user session and no "id" parameter in the URL
        	res.sendRedirect("/MyPresentLists_Client/Error"); // Redirect to Error page
        }
    }
}
