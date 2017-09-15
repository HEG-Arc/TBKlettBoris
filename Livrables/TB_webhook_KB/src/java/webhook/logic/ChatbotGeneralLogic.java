package webhook.logic;

import business.Event;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
public class ChatbotGeneralLogic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    TicketReservation ticketReservation;

    @Inject
    Utils utils;

    @Inject
    Services services;

    @Inject
    InformationResearch infoResearch;

    private final String ERROR_MESSAGE = "écoute là pour l'instant je rencontre des problèmes de connexion je n'arrive pas à trouver les évènements qui sont organisés. Essaye de nous envoyer un email ou de passer directement à la salle 356 au batiment de la HEG si jamais.";

    public ChatbotGeneralLogic() {
    }

    public String chatbotEventNameUnknown(JSONObject requestObject) {

        String serviceName = utils.getParamFromContext(requestObject, "context-general", "serviceName");
        String needed_information = utils.getParamFromContext(requestObject, "context-general", "needed_information");
        Integer number_of_tickets = utils.getParamIntFromContext(requestObject, "context-general", "number_of_tickets");

        String contextOutName = "context-general";
        String dateStr = "";
        String strReturn = "";
        List<Event> events1 = new ArrayList<>();
        List<Event> events = new ArrayList<>();

        // get only events that aren't passed for a ticket reservation
        try {
            events1 = services.getEventList();
            if (serviceName.equalsIgnoreCase(utils.TICKET_SERVICE) || serviceName.equalsIgnoreCase(utils.DIRECT_TICKET_SERVICE) || serviceName.equalsIgnoreCase(utils.DIRECT_TICKET_SERVICE + "1")) {
                if (!events1.isEmpty()) {
                    for (Event e : events1) {
                        if (new Date().getTime() < e.getEventDate().getTime()) {
                            events.add(e);
                        }
                    }
                }
            } else {
                events.addAll(events1);
            }
            //------------------------------------------------------------------

            if (events.isEmpty()) {
                strReturn = "J'aurais bien voulu t'aider, mais le problème c'est juste qu'aucun évènement est enregistré pour le moment "
                        + "donc ça va être un peu compliqué pour moi là. Mais est ce que tu veux qu'un membre de la gest'arc te contacte pour voir ça directement avec lui?";
            } else if (events.size() == 1) {
                dateStr = events.get(0).getTitle() + ", organisé pour le " + utils.getDateformat().format(events.get(0).getEventDate()) + ".  ";
                strReturn = "Ce n’est pas grave. Voici l'évènement que nous avons organisé. " + dateStr + "Alors tu peux juste me dire le nom de l'évènement si ça t'intéresse s'il te plaît.";

            } else {
                int i = 0;

                while (i < events.size() - 2) {
                    dateStr = dateStr + "\n - " + events.get(i).getTitle() + ", organisé pour le " + utils.getDateformat().format(events.get(i).getEventDate()) + ", \n \n  ";
                    i = i + 1;
                }

                dateStr = dateStr + "\n - " + events.get(events.size() - 2).getTitle() + ", organisé pour le " + utils.getDateformat().format(events.get(events.size() - 2).getEventDate()) + " et \n \n ";
                dateStr = dateStr + "\n - " + events.get(events.size() - 1).getTitle() + ", organisé pour le " + utils.getDateformat().format(events.get(events.size() - 1).getEventDate()) + ".  ";
                strReturn = "Ce n’est pas grave, si non voici la liste de nos évènements: \n \n " + dateStr + "Alors tu peux juste me dire le nom de l'évènement qui t'intéresse s'il te plaît.";

            }

        } catch (Exception ex) {
            System.out.println(ex);
            return utils.getJsonResponse(ERROR_MESSAGE, "ERROR", 1, "", "");

        }
        return utils.getJsonResponse(strReturn, contextOutName, 1, serviceName, " ", " ", " ", needed_information, number_of_tickets);
    }

    public String chatbotEventNameKnown(JSONObject requestObject) {
        String serviceName = utils.getParamFromContext(requestObject, "context-general", "serviceName");

        if (serviceName.equalsIgnoreCase(utils.INFORMATION_SERVICE) || serviceName.equalsIgnoreCase(utils.DIRECT_INFORMATION_SERVICE) || serviceName.equalsIgnoreCase(utils.DIRECT_INFORMATION_SERVICE + "1")) {
            return infoResearch.userNeedEventInfoGetInfo(requestObject);
        }

//        if (serviceName.equalsIgnoreCase(utils.INFORMATION_SERVICE)) {
//            return infoResearch.userNeedEventInfoEventNameKnown(requestObject, utils.INFORMATION_SERVICE);
//        }
        if (serviceName.equalsIgnoreCase(utils.TICKET_SERVICE) || serviceName.equalsIgnoreCase(utils.DIRECT_TICKET_SERVICE) || serviceName.equalsIgnoreCase(utils.DIRECT_TICKET_SERVICE + "1")) {
            return ticketReservation.ticketReservationEventNameKnown(requestObject);
        }
        return null;
    }

    public String userThankful(JSONObject requestObject) {
        String response = ERROR_MESSAGE;
        String serviceName = "";
        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        List<String> responses = new ArrayList<>();

        try {

            serviceName = utils.getParamFromContext(requestObject, "context-service-done", "serviceName");
        } catch (Exception ex) {
            System.out.println(ex);
            return utils.getJsonResponse(response, "context-service-done", 1, "", "");
        }
        responses.add("Pas de quoi ! si non tu aimerais quelque chose d’autre ?");
        response = "Pas de quoi (y). Est-ce que tu aimerais quelque chose d’autre ? du genre ";

        if (serviceName.equals(utils.INFORMATION_SERVICE)) {
            response = response + "devenir un membre de la Gest’arc ou réserver des tickets pour un évènement.";
        } else if (serviceName.equalsIgnoreCase(utils.TICKET_SERVICE)) {
            response = response + "devenir un membre de la Gest’arc ou avoir une information sur un évènement. Est ce que ça t'intéresse?";
        } else if (serviceName.equalsIgnoreCase(utils.MEMBER_SERVICE)) {
            response = response + "avoir des informations sur un évènement ou encore réserver des ticket pour un évènement si tu le souhaite.";
        }
        responses.add(response);

        response = "Avec plaisir. Est-ce que tu as besoin de quelque chose d’autre du coup ? ";

        if (serviceName.equals(utils.INFORMATION_SERVICE)) {
            response = response + "Devenir un membre de la Gest’arc ou réserver des tickets pour un évènement ! ça serait cool (y) ?";
        } else if (serviceName.equalsIgnoreCase(utils.TICKET_SERVICE)) {
            response = response + "Devenir un membre de la Gest’arc ou avoir une information sur un évènement. ça serait cool (y) ?";
        } else if (serviceName.equalsIgnoreCase(utils.MEMBER_SERVICE)) {
            response = response + "Avoir des informations sur un évènement ou encore réserver des ticket pour un évènement si tu le souhaite.";
        }
        responses.add(response);

        int random = (int) (Math.random() * 3);
        String s = responses.get(random);

        return utils.getJsonResponse(s, "context-service-done", 1, "", "");

    }

    public String userNotThankful(JSONObject requestObject) {
        String response = infoResearch.ERROR_MESSAGE;
        String serviceName = "";

        try {

            serviceName = utils.getParamFromContext(requestObject, "context-service-done", "serviceName");
        } catch (Exception ex) {
            System.out.println(ex);
            return utils.getJsonResponse(response, "context-service-done", 1, "", "");
        }

        return utils.getJsonResponse(utils.getNotThankfulSpeech(serviceName), "context-service-done", 1, "", "");

    }

    @SuppressWarnings("deprecation")
    public String bye() {
        Date toDayIs = new Date();
        int hour = toDayIs.getHours();
        String response;

        if (hour > 3 && hour < 13) {
            response = "Ça marche ! Dans ce cas je te souhaite une bonne journée et te dis à bientôt. N’hésites pas à écrire si tu as besoin de mon aide. Je suis là pour ça.";
        } else if (hour > 12 && hour < 18) {
            response = "Ça marche ! Dans ce cas je te souhaite une bonne après-midi et je te dis alors à bientôt. Surtout n’hésites pas à écrire au cas où.";
        } else if (hour > 17 && hour < 22) {
            response = "Ok (y)! Bon bah alors bonne soirée et surtout n’hésites pas à écrire si tu as besoin. Je suis là pour ça.";
        } else {
            response = "pas de soucis (y)! Bon bah alors bonne nuit et n’hésites sutout pas à écrire si tu as besoin. Je suis là pour ça.";
        }

        return utils.getJsonResponse(response, "context-bye", 1, "", "");
    }

}
