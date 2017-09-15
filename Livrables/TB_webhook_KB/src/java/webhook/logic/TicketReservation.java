package webhook.logic;

import business.Event;
import business.EventBooked;
import business.Student;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.json.JSONException;
import org.json.JSONObject;
import services.Services;
import utils.Utils;

/**
 *
 * @author boris.klett
 */
@Stateless
public class TicketReservation {

    private static final long serialVersionUID = 1L;
    public final String ERROR_MESSAGE = "Ecoute j'ai un petit problème là je pense que c'est dû à ma connexion internet. Tu peux nous contacter directement par email ou alors essaye de revenir un peu plus tard s'il te plaît.";

    @Inject
    Utils utils;

    @Inject
    Services services;

    public TicketReservation() {
    }

    @SuppressWarnings("null")
    public String directChoice(JSONObject requestObject) {
        String serviceName = utils.getParamFromContext(requestObject, "context-others-services", "serviceName");
        String eventName = utils.getParamFromContext(requestObject, "context-others-services", "eventName");
        Integer number_of_tickets = -1;
        number_of_tickets = utils.getParamIntFromContext(requestObject, "context-others-services", "number_of_tickets");

        if (eventName.equals(" ") || eventName.equals("nul") || eventName == null) {
            List<String> responses = new ArrayList<>();
            responses.add("Salut, oui bien sûr, pour quel événement? est-ce que tu peux me donner le nom de l’événement s'il te plaît ?");
            responses.add("Bonjour, pas de soucis, mais est-ce que tu pourrais juste me donner le nom de l’événement s’il te plaît ?");
            responses.add("Hello, ça marche, donne-moi juste le nom de l’événement s’il te plaît.");
            responses.add("Hey ciao! Ok, tu sais le nom de l’événement ?");

            return utils.getJsonResponse(utils.getAResponseRandomly(responses), "context-general", 1, utils.DIRECT_TICKET_SERVICE, " ", " ", " ", " ", number_of_tickets);

        }

        List<String> responses = new ArrayList<>();
        return this.checkEvent(responses, eventName, eventName, serviceName, number_of_tickets);
    }

    public String ticketReservationEventNameKnown(JSONObject requestObject) {

        String eventName = utils.getEventNameFromParams(requestObject);
        String serviceName = utils.getParamFromContext(requestObject, "context-general", "serviceName");
        Integer number_of_tickets = utils.getParamIntFromContext(requestObject, "context-general", "number_of_tickets");

        List<String> responses = new ArrayList<>();
        String contextOutName = "context-others-services";
        if (eventName.equalsIgnoreCase("nul")) {
            return utils.getJsonResponse("Tu peux juste me redonner le nom de l'évènement? s'il te plaît", "context-general", 1, serviceName, "");
        }
        return this.checkEvent(responses, contextOutName, eventName, serviceName, number_of_tickets);

    }

    private String checkEvent(List<String> responses, String contextOutName, String eventName, String serviceName, Integer number_of_tickets) {
        Event event = new Event();
        List<String> befResp = new ArrayList<>();
        befResp.add("Salut, ");
        befResp.add("Bonjour, ");
        befResp.add("Hello, ");
        befResp.add("Hey ciao, ");
        befResp.add("Hey salut, ");

        try {

            event = services.getEventByTitle(eventName);
            if (event == null) {
                List<String> resp = new ArrayList<>();
                resp.add("oui bien sûr, pour quel événement? est-ce que tu peux me donner le nom de l’événement s'il te plaît ?");
                resp.add("pas de soucis, mais est-ce que tu pourrais juste me donner le nom de l’événement s’il te plaît ?");
                resp.add("ça marche, donne-moi juste le nom de l’événement s’il te plaît.");
                resp.add("Ok, tu sais le nom de l’événement ?");

                String r = utils.getAResponseRandomly(resp);

                if (serviceName.equalsIgnoreCase(utils.DIRECT_TICKET_SERVICE)) {
                    r = utils.getAResponseRandomly(befResp) + r;
                    contextOutName = "context-general";
                }

                return utils.getJsonResponse(r, contextOutName, 1, utils.DIRECT_TICKET_SERVICE + "1", " ", " ", " ", " ", number_of_tickets);

            }

            if (!eventIsBookable(event.getTitle())) {
                List<Event> events = services.getEventListForTicket();
                String endOfResponse = "";
                String dateStr = "";

                if (events.isEmpty()) {
                    endOfResponse = " Mais en plus il n'y a aucun autre évènement où il est possible de réserver là vraiment désolé. Je te conseillerais quand même de faire un tour au secrétariat. " + utils.getNotThankfulSpeech(serviceName);
                } else if (events.size() == 1) {
                    dateStr = "la " + events.get(0).getEventType().getTittle().toLowerCase() + ": " + events.get(0).getTitle() + ", pour le " + utils.getDateformat2().format(events.get(0).getEventDate()) + ". ";

                } else {
                    int i = 0;

                    while (i < events.size() - 2) {
                        dateStr = dateStr + "la " + events.get(0).getEventType().getTittle().toLowerCase() + ": " + events.get(0).getTitle() + ", pour le " + utils.getDateformat2().format(events.get(0).getEventDate()) + ", ";
                        i = i + 1;
                    }

                    dateStr = dateStr + "la " + events.get(0).getEventType().getTittle().toLowerCase() + ": " + events.get(0).getTitle() + ", pour le " + utils.getDateformat2().format(events.get(0).getEventDate()) + " et ";
                    dateStr = "la " + events.get(0).getEventType().getTittle().toLowerCase() + ": " + events.get(0).getTitle() + ", pour le " + utils.getDateformat2().format(events.get(0).getEventDate()) + ". ";
                }

                endOfResponse = "Si non il y'a aussi: " + dateStr + "Si tu veux.";

                if (!event.getBookable()) {
                    responses.add("ok. Ha bah les places pour cet event ne sont pas réservable désolé depuis Facebook, essaye d'aller voir au secrétariat pour le faire. " + endOfResponse);
                    responses.add("ce n'est pas possible de réserver des tickets pour cet évènement désolé en tout cas pas par Facebook. " + endOfResponse);
                }

                if (event.getBookablePlacesNb() < 0) {
                    responses.add("ok. Ha bah désolé il n'y a plus de places réservables cet events. Essaye de passer voir au secrétariat " + endOfResponse.toLowerCase());
                    responses.add("je ne peux pas réserver des billets pour cet évènement. Il n'y a plus de places de libre pouvant être réserver depuis Facebook.  " + endOfResponse);
                }

                if (new Date().getTime() > event.getEventDate().getTime()) {
                    responses.add("ok. Ha mais cette " + event.getEventType().getTittle().toLowerCase() + " était le " + utils.getDateformat2().format(event.getEventDate()) + " dernier donc là c'est mort. " + endOfResponse);
                }
                contextOutName = "context-service-done";

            } else {

                responses.add("ok. J’aurais juste besoin de ton nom de famille s’il te plaît.");
                responses.add("ça marche. Juste, c'est quoi ton nom de famille?");
                responses.add("ok et c'est comment ton nom de famille? s'il te plaît");
            }
            String s = utils.getAResponseRandomly(responses);

            if (serviceName.equalsIgnoreCase(utils.DIRECT_TICKET_SERVICE)) {
                s = utils.getAResponseRandomly(befResp) + " " + s;
            }
            return utils.getJsonResponse(s, contextOutName, 1, serviceName, eventName, " ", " ", " ", number_of_tickets);
        } catch (Exception ex) {
            System.out.println(ex);
            return utils.getJsonResponse(ERROR_MESSAGE, contextOutName, 1, "", "");

        }
    }

    private boolean eventIsBookable(String eventName) {
        Event event = services.getEventByTitle(eventName);
        return event.getBookable() && event.getBookablePlacesNb() > 0 && new Date().getTime() < event.getEventDate().getTime();
    }

    public String ticketNumberOfTickets(JSONObject requestObject) {
        String eventName = utils.getParamFromContext(requestObject, "context-ticket-reservation", "eventName");
        String serviceName = utils.getParamFromContext(requestObject, "context-ticket-reservation", "serviceName");
        String userName = utils.getParamFromContext(requestObject, "context-ticket-reservation", "userName");
        String userFirstName = utils.getParamFromContext(requestObject, "context-ticket-reservation", "userFirstname");
        Integer number_of_tickets = utils.getParamIntFromContext(requestObject, "context-ticket-reservation", "number_of_tickets");

        if (number_of_tickets > -1) {
            return this.ticketsReservation(requestObject, utils.DIRECT_TICKET_SERVICE);
        }

        List<String> responses = new ArrayList<>();
        int random = 0;
        responses.add("Ok " + userFirstName.toLowerCase() + " tu veux combien de ticket?.");
        responses.add("tu aimerais combien de ticket ?");
        responses.add("et tu veux combien de ticket " + userFirstName + "?");
        random = (int) (Math.random() * 3);
        String r = responses.get(random);
        return utils.getJsonResponse(r, "context-ticket-reservation-reservation", 1, serviceName, eventName, userName, userFirstName);
    }

    @SuppressWarnings("deprecation")
    public String ticketsReservation(JSONObject requestObject, String pService) {
        Integer number_of_tickets = 0;
        String context = "context-ticket-reservation-reservation";
        if (pService.equalsIgnoreCase(utils.DIRECT_TICKET_SERVICE)) {
            context = "context-ticket-reservation";
            number_of_tickets = utils.getParamIntFromContext(requestObject, context, "number_of_tickets");

        } else {
            try {
                number_of_tickets = requestObject.getJSONObject("parameters").getInt("number_of_tickets");
            } catch (JSONException ex) {
                Logger.getLogger(TicketReservation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        String eventName = utils.getParamFromContext(requestObject, context, "eventName");
        String serviceName = utils.getParamFromContext(requestObject, context, "serviceName");
        String userName = utils.getParamFromContext(requestObject, context, "userName");
        String userFirstName = utils.getParamFromContext(requestObject, context, "userFirstname");

        String responses;
        boolean freeEvent = false;

        Event event = services.getEventByTitle(eventName);
        Student user = services.getStudentByLastnameNFirstname(userName, userFirstName);
        if (event.getStudiantsPrice() == 0) {
            freeEvent = true;
        }

        String date = "le " + utils.getDateformat2().format(event.getEventDate());
        if (new Date().getTime() == event.getEventDate().getTime()) {
            date = "aujourd'hui";
        }
        if (event.getBookablePlacesNb() == 0) {
            responses = "Ecoute " + user.getFirstName() + " il n'y a plus de tickets réservable pour cette " + event.getEventType().getTittle().toLowerCase() + " désolé :/ . Mais essaye d'aller voir au secrétariat peut-être qu'ils en ont encore.";
        } else if (event.getBookablePlacesNb() < number_of_tickets) {
            number_of_tickets = event.getBookablePlacesNb();

            if (freeEvent) {
                responses = "Ha mince il ne reste plus que " + event.getBookablePlacesNb() + " tickets réservables. Je reserve ça tout de suite " + userFirstName + " . Et si jamais tu tiens toujours à avoir tous les tickets, tu peux aussi passer au secrétariat pour te renseigner s’il y en a encore. Il faudra surtour aller récupérer ça avant" + date + " à " + (event.getStartingTime().getHours() - 2) + "h" + event.getStartingTime().getMinutes() + "  si non les tickets seront remis en ligne. Par contre, l'évènement est gratuit si jamais (y)";
            } else {
                responses = "Ha mince il ne reste plus que " + event.getBookablePlacesNb() + " tickets réservables. Je te les réserve tout de suite " + userFirstName + " . Alors ça te fera " + event.getStudiantsPrice() * number_of_tickets + " francs si jamais ! Et si jamais tu tiens toujours à avoir tous les tickets, tu peux aussi passer au secrétariat pour te renseigner s’il y en a encore et y acheter le reste d’accord ? Juste encore une petite précision, là je te réserve ça mais par contre tu devras aller payer auprès de la Gest’arc dans le bâtiment de la HEG ou alors au secrétariat de l’école avant " + date + " à " + (event.getStartingTime().getHours() - 2) + "h" + event.getStartingTime().getMinutes() + " si non les tickets seront remis en vente.";
            }
            this.bookeEvent(event, user, number_of_tickets, 0);
        } else {
            if (freeEvent) {
                responses = "Ok ça joue ! Juste encore une petite précision je te réserve des tickets mais par contre tu devras aller les récupérer auprès de la Gest’arc dans le bâtiment de la HEG ou alors au secrétariat de l’école avant " + date + " à " + (event.getStartingTime().getHours() - 2) + "h" + event.getStartingTime().getMinutes() + " si non ça sera remis en ligne. Et l'évènement est gratuit si jamais (y)";
            } else {
                responses = "Alors ça te fera " + event.getStudiantsPrice() * number_of_tickets + " francs si jamais ! Juste encore une petite précision je te réserve ça mais par contre tu devras aller payer auprès de la Gest’arc dans le bâtiment de la HEG ou alors au secrétariat de l’école avant " + date + " à " + (event.getStartingTime().getHours() - 2) + "h" + event.getStartingTime().getMinutes() + "  si non les tickets seront remis en vente.";
            }
            this.bookeEvent(event, user, number_of_tickets, event.getBookablePlacesNb() - number_of_tickets);
        }
        return utils.getJsonResponse(responses, "context-service-done", 1, serviceName, eventName, userName, userFirstName);

    }

    private void bookeEvent(Event event, Student user, Integer numberOfTickets, Integer restOfBookablePlaces) {
        services.modifyAnEvent(event.getTitle(), event.getFreePlacesNb() - numberOfTickets, restOfBookablePlaces);
        EventBooked eB = new EventBooked();
        eB.setEvent(event);
        eB.setStudent(user);
        eB.setDateOfReservation(new Date());
        eB.setNbOfPlaces(numberOfTickets);

        services.bookeEventTikets(eB);
    }

}
