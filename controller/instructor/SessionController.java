/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.instructor;

//import controller.authentication.BasedAuthorizationController;
//import controller.authentication.BasedRequiredAuthenticationController;
import dal.SessionDBContext;
import dal.AccountDBContext;
import entity.Session;
import entity.Account;
import entity.Role;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DateTimeHelper;

/**
 *
 * @author Admin
 */
public class SessionController extends HttpServlet { //extends BasedAuthorizationController {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, String instructor_id)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account loggedUser = (Account) session.getAttribute("account");

        if (loggedUser != null) {
            String username = loggedUser.getUsername();
            AccountDBContext dbContext = new AccountDBContext();
            ArrayList<Account> accountList = dbContext.getAccountIdByUsername(username);

            for (Account account : accountList) {
                if (account.getInstructor() != null) {
                    instructor_id = account.getInstructor().getInstructor_id();
                    break;
                }
            }

            String r_from = request.getParameter("from");
            String r_to = request.getParameter("to");
            ArrayList<Date> dates = new ArrayList<>();

            if (r_from == null) {
                dates = DateTimeHelper.getCurrentWeekDates();
            } else {
                try {
                    dates = DateTimeHelper.getSqlDatesInRange(r_from, r_to);
                } catch (ParseException ex) {
                    Logger.getLogger(SessionController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            SessionDBContext sesDB = new SessionDBContext();
            ArrayList<Session> sessions = sesDB.getSessionsByInstrucor(instructor_id, dates.get(0), dates.get(dates.size() - 1));

            request.setAttribute("dates", dates);
            request.setAttribute("from", dates.get(0));
            request.setAttribute("to", dates.get(dates.size() - 1));
            request.setAttribute("sessions", sessions);
        }

        request.getRequestDispatcher("...").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response, null);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response, null);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
