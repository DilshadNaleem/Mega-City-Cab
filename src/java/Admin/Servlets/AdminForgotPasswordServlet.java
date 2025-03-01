package Admin.Servlets;

import AServices.ForgotPasswordEmail;
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import DatabaseConnection.*;

public class AdminForgotPasswordServlet extends HttpServlet {

   private Connection conn;
   
   public void init() throws ServletException 
   {
       try {
           conn = DatabaseConnection.getConnection();
       }
       catch (Exception e)
       {
           e.printStackTrace();
           throw new ServletException("Database connection error ", e);
       }
   }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        String email = request.getParameter("email");
        
        HttpSession session = request.getSession();
        session.setAttribute("adminemail", email);
        
        try (PrintWriter out = response.getWriter())
        {
            String query = "SELECT * FROM admin WHERE email = ?";
            
            try (PreparedStatement stmt = conn.prepareStatement(query))
            {
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();
                
                if(rs.next())
                {
                    ForgotPasswordEmail emailService = new ForgotPasswordEmail();
                    try {
                        emailService.sendResetPasswordEmail(email);
                        response.sendRedirect("/Mega_City/Admin/Reset_Password.html");
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                        out.println("<h2> Error sending email: " + e.getMessage() + "</h2>");
                    }
                } else
                {
                    out.println("<h2> Email not found </h2>");
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            response.getWriter().println("<h2> Database Error: " + e.getMessage() + "</h2>");
        }
    }
    
   public void destroy()
   {
       try 
       {
           if(conn != null && !conn.isClosed())
           {
               conn.close();
           }
       } catch (SQLException e)
       {
           e.printStackTrace();
       }
        }
}
