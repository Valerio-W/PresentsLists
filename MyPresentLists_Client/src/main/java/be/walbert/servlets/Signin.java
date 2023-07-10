package be.walbert.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.walbert.javabeans.Users;

public class Signin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Signin() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/Signin.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pseudo = request.getParameter("pseudo");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        //Set the list of errors
        ArrayList<String> errors = new ArrayList<String>();

        //Check if a field is null or empty
        if (pseudo == null || password == null || email == null || pseudo.isEmpty() || password.isEmpty()
                || email.isEmpty()) {
            errors.add("A field is null or empty.");
        } else {
            if (!pseudo.matches("^[0-9a-zA-Z]{5,}$")) {//Check the size and format of pseudo
                errors.add("Pseudo must contain at least 5 characters.");
            }
            if (!password.matches("^[0-9a-zA-Z]{4,}$")) {//Check the size and format of password
                errors.add("Password must contain at least 4 characters.");
            }

            Users u = new Users(0, pseudo, password, email);

            if (!u.checkAccount()) {//Check if pseudo or email are already taken
                errors.add("Sorry, email or pseudo not available.");
            }
        }

        if (errors.isEmpty()) {//If errors is empty (no errors)
            Users u = new Users(0, pseudo, password, email);
            if (u.Signin()) {
                request.getSession().setAttribute("confirmAccount", "Great, your account has just been created");
                response.sendRedirect(request.getContextPath() + "/HomePage");
            } else {
            	getServletContext().getRequestDispatcher("/WEB-INF/Errors.jsp").forward(request, response);
			    return;
            }
        }

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/WEB-INF/Signin.jsp").forward(request, response);
        }
    }
}
