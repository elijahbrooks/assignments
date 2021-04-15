package com.smoothstack.login_assignment;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name="login", urlPatterns = {"/login"})
public class LoginForm extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        resp.setContentType("text/html");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if(username.equals("user") && password.equals("password1"))
            pw.println("Successfully logged in.");
        else
            pw.println("Failed to log user in.");
        pw.close();
    }

    public void destroy() {
    }
}