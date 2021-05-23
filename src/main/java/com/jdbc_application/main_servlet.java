package com.jdbc_application;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class main_servlet extends HttpServlet{
    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        PrintWriter out=response.getWriter();
        response.setContentType("text/html;charset=UTF-8");
        try{
            String username=request.getParameter("Username");
            String password=request.getParameter("Password");
            String date_of_birth=request.getParameter("date_of_birth");
            database db=new database();
            String cssTag="<link rel='stylesheet' type='text/css' href='./styling/style.css'>";
            out.println("<head><title>Servlet Home Page</title>"+cssTag);
            out.println("<meta http-equiv='X-UA-Compatible' content='IE=edge'>"+
            "<meta name='viewport' content='width=device-width, initial-scale=1.0'></head>");
            out.println("<body>");
            String conditional=(String) request.getParameter("button");
            if(conditional.contentEquals("Register")){
                db.put_data(username, password, date_of_birth);
                out.println("<h2>Registration Page, <br> Successful Append into Database</h2>");
                db.get_data(request, response);
            }
            else if(conditional.contentEquals("Login")){
                db.login(username, password);
                out.println("<h2>Login Page, <br> Successful Login</h2>");
            }
        }
        catch(SQLException e){
            RequestDispatcher rd=request.getRequestDispatcher("data_search.html");
            rd.include(request, response);
            out.println("<p style=color:red>Entered in wrong format, exception occured!</p>");
            out.println("<h4 style=color:red>Error!"+e+"</h4>");
        }
        catch (Exception e) {
            RequestDispatcher rd=request.getRequestDispatcher("data_insertion.html");
            rd.include(request, response);
            out.println("<p style=color:red>Entered in wrong format, exception occured!</p>");
            out.println("<h4 style=color:red>Error!"+e+"</h4>");
        }
        finally{
            out.println("</body>");
            out.close();
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}

