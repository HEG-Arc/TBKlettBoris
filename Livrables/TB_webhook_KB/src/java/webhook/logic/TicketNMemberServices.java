package webhook.logic;

import business.Student;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.json.JSONObject;
import services.Services;
import utils.Utils;

/**
 *
 * @author boris.klett
 */
@Stateless
public class TicketNMemberServices implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    Services services;

    @Inject
    Utils utils;

    @Inject
    InformationResearch infoResearch;

    public TicketNMemberServices() {
    }

    public String othersServicesUserLastname(JSONObject requestObject) {
        String serviceName = utils.getParamFromContext(requestObject, "context-others-services", "serviceName");
        String eventName = utils.getParamFromContext(requestObject, "context-others-services", "eventName");
        Integer number_of_tickets = utils.getParamIntFromContext(requestObject, "context-others-services", "number_of_tickets");
        String userName = utils.getUserNameFromParams(requestObject);

        Student user = utils.getUser(userName);
        List<String> responses = new ArrayList<>();
        int random = 0;
        String contextOutName = "context-members-registration";
        if (serviceName.equalsIgnoreCase(utils.TICKET_SERVICE) || serviceName.equalsIgnoreCase(utils.DIRECT_TICKET_SERVICE)) {
            contextOutName = "context-ticket-reservation";
        }

        if (user.getId() > -1) {
            return this.getResponseForEmailConfirmation(serviceName, user, eventName, userName, user.getFirstName(), number_of_tickets);
        }

        responses.add("ok, tu peux aussi me dire ton prénom ? s’il te plaît .");
        responses.add("ok et c’est comment ton prénom ?");
        responses.add("ok et ton prénom c’est quoi ?");
        responses.add("ok, mai j’ai encore besoin de ton prénom s’il te plaît.");
        random = (int) (Math.random() * 4);
        String r = responses.get(random);
        return utils.getJsonResponse(r, "context-need-firstname", 1, serviceName, eventName, userName, " ", " ", number_of_tickets);

    }

    private String getResponseForEmailConfirmation(String serviceName, Student user, String eventName, String userName, String userFirstname, Integer number_of_tickets) {
        String contextOutName = "context-members-registration";
        List<String> responses = new ArrayList<>();
        int random = 0;
        if (serviceName.equalsIgnoreCase(utils.TICKET_SERVICE) || serviceName.equalsIgnoreCase(utils.DIRECT_TICKET_SERVICE) || serviceName.equalsIgnoreCase(utils.DIRECT_TICKET_SERVICE + "1")) {
            contextOutName = "context-ticket-reservation";
        }

        responses.add("Ok, alors ton adresse email est bien " + user.getEmail() + " ?");
        responses.add("Ça marche. Juste ton email est bien " + user.getEmail() + " ?");
        responses.add("Alors, " + user.getEmail() + " est bien ton email ?");
        random = (int) (Math.random() * 3);
        String r = responses.get(random);

        return utils.getJsonResponse(r, contextOutName, 1, serviceName, eventName, userName, userFirstname, " ", number_of_tickets);

    }

    public String othersServicesUserFirstname(JSONObject requestObject) {
        String eventName = utils.getParamFromContext(requestObject, "context-need-firstname", "eventName");
        String serviceName = utils.getParamFromContext(requestObject, "context-need-firstname", "serviceName");
        String userName = utils.getParamFromContext(requestObject, "context-need-firstname", "userName");
        Integer number_of_tickets = utils.getParamIntFromContext(requestObject, "context-need-firstname", "number_of_tickets");
        String userFirstName = utils.getUserFirstNameFromParams(requestObject);

//        String eventName = "La Gest'arc";
//        String serviceName = utils.MEMBER_SERVICE;
//        String userName = "Amta";
//        String userFirstName = "Ulrich";
        Student user = utils.getUser(userName);

        if (user.getId() < -1) {
            user = utils.getUser(userName, userFirstName);
            return this.getResponseForEmailConfirmation(serviceName, user, eventName, userName, userFirstName, number_of_tickets);
        }

        user = new Student();
        user.setFirstName(userFirstName);
        user.setLastName(userName);
        user.setEmail(utils.buildUserEmail(userName, userFirstName));
        services.persistStudent(user);
        return this.getResponseForEmailConfirmation(serviceName, user, eventName, userName, userFirstName, number_of_tickets);
    }

}
