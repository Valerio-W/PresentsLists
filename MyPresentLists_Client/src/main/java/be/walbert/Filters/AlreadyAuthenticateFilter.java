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

 
public class AlreadyAuthenticateFilter extends HttpFilter implements Filter {
       
 
    public AlreadyAuthenticateFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);
        
		if (isLoggedIn) {
			httpRequest.getSession().setAttribute("alreadyConnected", "Sorry, you can access to this because you are already authenticate.");
            httpResponse.sendRedirect(httpRequest.getContextPath()+"/UserPage");
        } else { 
            chain.doFilter(request, response);
        }
	}
}
