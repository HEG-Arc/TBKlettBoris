/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webhook.servlet;

import dataBase.DataBaseInit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Utils;
import webhook.logic.ChatbotGeneralLogic;
import webhook.logic.InformationResearch;
import webhook.logic.MembersRegistration;
import webhook.logic.TicketNMemberServices;
import webhook.logic.TicketReservation;

/**
 *
 * @author boris.klett
 */
public class Webhook_KB extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @EJB
    private Utils utils;
    @EJB
    private ChatbotGeneralLogic chatbotGeneralLogic;
    @EJB
    private InformationResearch informationsResearch;
    @EJB
    private TicketNMemberServices ticketNMemberServices;
    @EJB
    private TicketReservation ticketReservation;
    @EJB
    private MembersRegistration membersRegistration;

    @EJB
    private DataBaseInit init;
//    @EJB
//    private DataBaseInit dbi;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws org.json.JSONException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JSONException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            StringBuilder jb = new StringBuilder();
            @SuppressWarnings("UnusedAssignment")
            String line = null;

            //Get the request from API.AI
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }

            //Parse the request in JSONObject
            JSONObject jsonObject = new JSONObject(jb.toString());

            //The logic
            JSONObject resultObject = jsonObject.getJSONObject("result");
            String action = resultObject.getString("action");
            String defaultMess = "Il faut encore essayer";

            @SuppressWarnings("UnusedAssignment")
            String responseJson = new String();
            responseJson = utils.getJsonResponse(defaultMess, "error", 1, "", "");

            switch (action) {
                case "init":
                    responseJson = init.init();
                    break;
                case "get-events-to-help":
                    responseJson = chatbotGeneralLogic.chatbotEventNameUnknown(resultObject);
                    break;
                case "check-event-name":
                    responseJson = chatbotGeneralLogic.chatbotEventNameKnown(resultObject);
                    break;
                case "get-needed-information":
                    responseJson = informationsResearch.userNeedEventInfoGetInfo(resultObject);
                    break;
                case "user-thank":
                    responseJson = chatbotGeneralLogic.userThankful(resultObject);
                    break;
                case "user-not-thank":
                    responseJson = chatbotGeneralLogic.userNotThankful(resultObject);
                    break;
                case "salutations":
                    responseJson = chatbotGeneralLogic.bye();
                    break;
                case "get-user-by-lastname":
                    responseJson = ticketNMemberServices.othersServicesUserLastname(resultObject);
                    break;
                case "get-user-by-last-and-firstname":
                    responseJson = ticketNMemberServices.othersServicesUserFirstname(resultObject);
                    break;
                case "user-confirm-email":
                    responseJson = ticketReservation.ticketNumberOfTickets(resultObject);
                    break;
                case "tickets-reservation":
                    responseJson = ticketReservation.ticketsReservation(resultObject, "");
                    break;
                case "registration-member":
                    responseJson = membersRegistration.memberRequestRegistration(resultObject);
                    break;
                case "direct-ticket-choice":
                    responseJson = ticketReservation.directChoice(resultObject);
                    break;
                default:
                    responseJson = utils.getJsonResponse(defaultMess, "error", 1, "", "");
                    break;
            }

            JSONObject resultOject = new JSONObject(responseJson);
            out.println(resultOject);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(Webhook_KB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(Webhook_KB.class.getName()).log(Level.SEVERE, null, ex);
        }
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
