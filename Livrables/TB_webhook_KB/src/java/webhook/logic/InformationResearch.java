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
public class InformationResearch implements Serializable {

    private static final long serialVersionUID = 1L;

    public final String ERROR_MESSAGE = "Ecoute j'ai un petit problème là je pense que c'est dû à ma connexion internet. Tu peux nous contacter directement par email ou alors essaye de revenir un peu plus tard s'il te plaît.";

    @Inject
    private Utils utils;

    @Inject
    private Services services;

    public InformationResearch() {
    }

    private String userNeedEventInfoEventNameKnown(JSONObject requestObject, String pServiceName) {

        String eventName = utils.getEventNameFromParams(requestObject);
        if (" ".equals(eventName) || "nul".equals(eventName) || eventName == null) {
            eventName = utils.getParamFromContext(requestObject, "context-general", "eventName");
        }

        String serviceName = pServiceName;
        List<String> befResp = new ArrayList<>();
        befResp.add("Salut! ");
        befResp.add("Bonjour! ");
        befResp.add("Hello! ");
        befResp.add("Hey ciao! ");
        befResp.add("Hey salut! ");

        List<String> responses = new ArrayList<>();

        if (eventName.equalsIgnoreCase("nul")) {
            return utils.getJsonResponse("Tu peux juste me redonner le nom de l'évènement? s'il te plaît", "context-general", 1, serviceName, "");
        }

        Event event = new Event();
        Date sysDate = new Date();

        try {

            event = services.getEventByTitle(eventName);

            //Check if the event is event passed.
            if (sysDate.getTime() > event.getEventDate().getTime()) {
                responses.add("Ha ok je vois ! c'était vraiment sympa j'ai trouvé. Qu’aimerais-tu savoir dessus ?");
                responses.add("Ok alors dis-moi qu’est-ce que t’aimerais comme info sur cette " + event.getEventType().getTittle().toLowerCase() + " ?");
                responses.add("Qu’est-ce que tu aimerais savoir sur cette " + event.getEventType().getTittle().toLowerCase() + " ?");

            } else {

                responses.add("Je pense que ça sera vraiment bien cette " + event.getEventType().getTittle().toLowerCase() + ". Qu’est-ce que tu aimerais ?");
                responses.add("Qu’est-ce que tu aimerais savoir sur cette " + event.getEventType().getTittle().toLowerCase() + "?");
                responses.add("Ok alors dis-moi qu’est-ce que t’aimerais ?");
            }
            String s = utils.getAResponseRandomly(responses);
            if (serviceName.equalsIgnoreCase(utils.DIRECT_INFORMATION_SERVICE)) {
                s = utils.getAResponseRandomly(befResp) + s;
            }
            return utils.getJsonResponse(s, "context-research-information", 1, serviceName, eventName, "", "", "");

        } catch (Exception ex) {
            System.out.println(ex);
            return utils.getJsonResponse("Tu peux juste me redonner le nom de l'évènement? s'il te plaît", "context-general", 2, serviceName, "");

        }

    }

    @SuppressWarnings({"deprecation"})
    public String userNeedEventInfoGetInfo(JSONObject requestObject) {

        Event event = new Event();
        Boolean eventIsPassed = false;
        String response = ERROR_MESSAGE;

        String needed_information = "";
        String eventName = "";
        String serviceName = "";
        List<String> befResp = new ArrayList<>();
        befResp.add("Salut! ");
        befResp.add("Bonjour! ");
        befResp.add("Hello! ");
        befResp.add("Hey ciao! ");
        befResp.add("Hey salut! ");
        try {
            serviceName = utils.getParamFromContext(requestObject, "context-general", "serviceName");
            JSONObject parametersObject = requestObject.getJSONObject("parameters");
            needed_information = parametersObject.getString("needed_information");
            eventName = parametersObject.getString("eventName");

            if ("".equals(eventName) || eventName == null || "nul".equals(eventName)) {
                eventName = utils.getParamFromContext(requestObject, "context-general", "eventName");
            }

            if ("".equals(needed_information) || needed_information == null || "nul".equals(needed_information)) {
                needed_information = utils.getParamFromContext(requestObject, "context-research-information", "needed_information");
            }

            if ("".equals(needed_information) || needed_information == null || "nul".equals(needed_information)) {
                needed_information = utils.getParamFromContext(requestObject, "context-research-information", "needed_information");
            }

            List<String> resp = new ArrayList<>();
            resp.add("oui bien sûr, quel est le nom de l'évènement?");
            resp.add("pas de soucis, mais est-ce que tu pourrais juste me donner le nom de l’événement s’il te plaît ?");
            resp.add("ça marche, donne-moi juste le nom de l’événement s’il te plaît");
            resp.add("ok, tu sais le nom de l’événement ?");

            if ("".equals(needed_information) || "nul".equals(needed_information)) {
                needed_information = "";
                if ("".equals(eventName) || "nul".equals(eventName)) {
                    return utils.getJsonResponse(utils.getAResponseRandomly(resp), "context-general", 2, utils.INFORMATION_SERVICE, "", "", "", "");
                }
            }

            if ("".equals(needed_information) || "nul".equals(needed_information)) {
                needed_information = "";
                return this.userNeedEventInfoEventNameKnown(requestObject, utils.DIRECT_INFORMATION_SERVICE + "1");
            }

            if ("".equals(eventName) || "nul".equals(eventName)) {
                return utils.getJsonResponse(utils.getAResponseRandomly(befResp) + utils.getAResponseRandomly(resp) + " pour pouvoir faire la recherche.", "context-general", 1, utils.DIRECT_INFORMATION_SERVICE + "1", "", "", "", needed_information);
            }

            event = services.getEventByTitle(eventName);
            if (event == null) {
                return utils.getJsonResponse(utils.getAResponseRandomly(befResp) + utils.getAResponseRandomly(resp) + " pour pouvoir faire la recherche.", "context-general", 1, utils.DIRECT_INFORMATION_SERVICE + "1", "", "", "", needed_information);
            }

            if (new Date().getTime() > event.getEventDate().getTime()) {
                eventIsPassed = true;
            }

            switch (needed_information.trim()) {
                case "photos":
                    if (eventIsPassed) {
                        if (event.getPhotos().isEmpty()) {
                            response = "Désolé je ne trouve pas de photos! je pense que nous n'avons pas réaliser de photos lors de cette " + event.getEventType().getTittle().toLowerCase() + ".";
                            break;
                        }
                        response = "Oui biensur. Tiens le lien Facebook qui te permettra de les trouver -> " + event.getPhotos().get(0).getFbLink();
                        break;
                    }
                    response = "Je m'excuse, mais la date de l'évènement est le " + utils.getDateformat2().format(event.getEventDate()) + " prochain donc en occurence, il n'y a pas encore de photos dispo.";
                    break;

                case "heures":
                    if (eventIsPassed) {
                        response = "Selon le programme, cette " + event.getEventType().getTittle().toLowerCase() + " avait commencé à " + event.getStartingTime().getHours() + "h" + event.getStartingTime().getMinutes() + " et était finie à " + event.getEndingTime().getHours() + "h" + event.getEndingTime().getMinutes() + ".";
                        break;
                    }
                    response = "Selon le programme, cette " + event.getEventType().getTittle().toLowerCase() + " commencera à " + event.getStartingTime().getHours() + "h" + event.getStartingTime().getMinutes() + " et finira à " + event.getEndingTime().getHours() + "h" + event.getEndingTime().getMinutes() + ".";
                    break;
                case "lieu":
                    if (eventIsPassed) {
                        response = "le lieu de l'évènement était à " + event.getLocation();
                        break;
                    }
                    response = "le lieu de l'évènement est à " + event.getLocation();
                    break;
                case "theme":
                    if (eventIsPassed) {
                        if (event.getTheme().isEmpty() || event.getTheme() == null) {
                            response = "Cet évènement n'avait pas de thème particulier.";
                            break;
                        }
                        response = "Le thème de cet évènement était: " + event.getTheme().toLowerCase() + ". ";
                        break;
                    }
                    if (event.getTheme().isEmpty() || event.getTheme() == null) {
                        response = "Cet évènement n'a pas de thème particulier.";
                        break;
                    }
                    response = "Le thème de cet évènement est: " + event.getTheme().toLowerCase() + ". ";
                    break;
                case "nom":
                    if (eventIsPassed) {
                        response = "Oui pas de soucis. Alors le nom de cet évènement était: " + event.getTitle() + ".";
                        break;
                    }
                    response = "Oui pas de soucis. Alors le nom de cet évènement est: " + event.getTitle() + ".";
                    break;
                case "prix":
                    if (eventIsPassed) {
                        if (event.getStudiantsPrice() == 0.00) {
                            response = "Alors d'après les données que j'ai, cet évènement était gratuit.";
                            break;
                        }
                        response = "Oui pas de soucis. le prix pour les membres de la Gest'arc était de " + event.getMembersPrice() + " francs, celui pour les étudiants de " + event.getStudiantsPrice() + " francs et celui pour les non-étudiants était de " + event.getNoneStudiantsPrice() + " francs";
                        break;
                    }
                    if (event.getStudiantsPrice() == 0.00) {
                        response = "Alors d'après les données que j'ai, c'est cool car cet évènement est en fait gratuit :D .";
                        break;
                    }
                    response = "Oui pas de soucis, le prix pour les membres de la Gest'arc est de " + event.getMembersPrice() + " francs, celui pour les étudiants de " + event.getStudiantsPrice() + " francs et celui pour les non-étudiants est de " + event.getNoneStudiantsPrice() + " francs. Voilà";
                    break;
                case "genre":
                    if (eventIsPassed) {
                        response = "Oui pas de soucis. C'était une " + event.getEventType().getTittle().toLowerCase() + ". ";
                        break;
                    }
                    response = "Oui pas de soucis. Alors c'est une " + event.getEventType().getTittle().toLowerCase() + ". ";
                    break;
                case "date":
                    if (new Date().getTime() > event.getEventDate().getTime()) {
                        response = "L'évènement était le " + utils.getDateformat().format(event.getEventDate());
                        break;
                    } else if (new Date().getTime() == event.getEventDate().getTime()) {
                        response = "L'évènement est aujourd'hui à " + event.getStartingTime().getHours() + "h" + event.getStartingTime().getMinutes() + ".";
                        break;
                    }
                    response = "l'évènement est programmé pour le " + event.getEventDate().getDay() + " du " + event.getEventDate().getMonth() + " prochain.";
                    break;
                default:
                    return this.userNeedEventInfoEventNameKnown(requestObject, utils.DIRECT_INFORMATION_SERVICE);
            }
        } catch (Exception ex) {
            System.out.println(ex);
            return utils.getJsonResponse(response, "context-service-done", 2, utils.INFORMATION_SERVICE, "");
        }
        if (serviceName.equalsIgnoreCase(utils.DIRECT_INFORMATION_SERVICE)) {
            response = utils.getAResponseRandomly(befResp) + response;
        }
        return utils.getJsonResponse(response, "context-service-done", 2, utils.INFORMATION_SERVICE, "");
    }

}
