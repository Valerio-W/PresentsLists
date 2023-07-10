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

public class AuthenticateFilter extends HttpFilter implements Filter {

	public AuthenticateFilter() {
        super();
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        String loginURI = req.getContextPath();

        boolean loggedIn = session != null && session.getAttribute("user") != null;
        if(loggedIn) {
            chain.doFilter(request, response);
        }
        else {
        	res.sendRedirect(loginURI);
        }
    }
}
