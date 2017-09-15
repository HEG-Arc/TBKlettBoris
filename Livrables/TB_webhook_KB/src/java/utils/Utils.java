package utils;

import business.Event;
import business.Student;
import business.jacksonClasses.ContextOut;
import business.jacksonClasses.Parameters;
import business.jacksonClasses.WebhookResponse2;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import services.Services;

/**
 *
 * @author boris.klett
 */
@Stateless
public class Utils {

    private static final long serialVersionUID = 1L;
    WebhookResponse2 webhookResponse;
    private ObjectMapper mapper;
    private ContextOut contextOut;
    private Parameters parameters;
    private final SimpleDateFormat dateformat = new SimpleDateFormat("dd MMMM YYYY");
    private final SimpleDateFormat dateformat2 = new SimpleDateFormat("dd MMMM");
    public final String TICKET_SERVICE = "ticket";
    public final String INFORMATION_SERVICE = "informations";
    public final String MEMBER_SERVICE = "inscription";
    public final String DIRECT_INFORMATION_SERVICE = "directInformations";
    public final String DIRECT_TICKET_SERVICE = "directTicket";
    @Inject
    Services services;

    public Utils() {
    }

    public SimpleDateFormat getDateformat() {
        return dateformat;
    }

    public SimpleDateFormat getDateformat2() {
        return dateformat2;
    }

    public String getJsonResponse(String response, String contextName, int contextLifePan, String serviceName, String eventName) {
        return this.getJsonResponse(response, contextName, contextLifePan, serviceName, eventName, "");
    }

    public String getJsonResponse(String response, String contextName, int contextLifePan, String serviceName, String eventName, String userName) {
        return this.getJsonResponse(response, contextName, contextLifePan, serviceName, eventName, userName, "", "");
    }

    public String getJsonResponse(String response, String contextName, int contextLifePan, String serviceName, String eventName, String userName, String userFirstname) {
        return this.getJsonResponse(response, contextName, contextLifePan, serviceName, eventName, userName, userFirstname, "");
    }

    public String getJsonResponse(String response, String contextName, int contextLifePan, String serviceName, String eventName, String userName, String userFirstname, String needed_information) {
        return this.getJsonResponse(response, contextName, contextLifePan, serviceName, eventName, userName, userFirstname, needed_information, -1);
    }

    public String getJsonResponse(String response, String contextName, int contextLifePan, String serviceName, String eventName, String userName, String userFirstname, String needed_information, Integer number_of_tickets) {
        mapper = new ObjectMapper();

        contextOut = new ContextOut();
        parameters = new Parameters();
        contextOut.setName(contextName);
        contextOut.setLifespan(contextLifePan);
        parameters.setServiceName(serviceName);
        parameters.setEventName(eventName);
        parameters.setUserName(userName);
        parameters.setUserFirstname(userFirstname);
        parameters.setNeeded_information(needed_information);
        parameters.setNumber_of_tickets(number_of_tickets);
        contextOut.setParameters(parameters);

        String jsonInString = null;

        webhookResponse = new WebhookResponse2();
        webhookResponse.setSpeech(response);
        webhookResponse.setDisplayText(response);
        webhookResponse.addContextOut(contextOut);
        try {
            // Convert object to JSON string
            jsonInString = mapper.writeValueAsString(webhookResponse);
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return jsonInString;
    }

    public String getAResponseRandomly(List<String> responses) {
        int random = (int) (Math.random() * responses.size());
        String s = responses.get(random);
        return s;
    }

    public Integer getParamIntFromContext(JSONObject requestObject, String contextName, String parameterName) {
        Integer eventName = -1;

        try {
            JSONArray contextObject = requestObject.getJSONArray("contexts");

            JSONObject contextParametersObject = new JSONObject();

            for (int i = 0; i < contextObject.length(); i++) {

                JSONObject o = contextObject.getJSONObject(i);
                if (o.getString("name").equalsIgnoreCase(contextName)) {
                    contextParametersObject = o;
                }

            }

            JSONObject contextParam = contextParametersObject.getJSONObject("parameters");
            eventName = contextParam.getInt(parameterName);
        } catch (Exception ex) {
            return -1;
        }
        return eventName;
    }

    public String getParamFromContext(JSONObject requestObject, String contextName, String parameterName) {
        String eventName = "";

        try {
            JSONArray contextObject = requestObject.getJSONArray("contexts");

            JSONObject contextParametersObject = new JSONObject();

            for (int i = 0; i < contextObject.length(); i++) {

                JSONObject o = contextObject.getJSONObject(i);
                if (o.getString("name").equalsIgnoreCase(contextName)) {
                    contextParametersObject = o;
                }

            }

            JSONObject contextParam = contextParametersObject.getJSONObject("parameters");
            eventName = contextParam.getString(parameterName);
        } catch (Exception ex) {
            return "nul";
        }
        return eventName;
    }

    public String getEventNameFromParams(JSONObject requestObject) {

        Event event = new Event();

        try {
            JSONObject parametersObject = requestObject.getJSONObject("parameters");
            String eventName = parametersObject.getString("eventName");

            event = services.getEventByTitle(eventName.trim());

            if (event != null) {
                return eventName;
            }

            return "nul";

        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            return "nul";
        }
    }

    public String getNotThankfulSpeech(String serviceName) {
        List<String> responses = new ArrayList<>();
        responses.add("Tu que je fasse quelque chose d'autre pour toi?");
        String response = "Tu souhaites quelque chose d’autre ? du genre ";

        if (serviceName.equals(INFORMATION_SERVICE)) {
            response = response + "devenir un membre de la Gest’arc ou réserver des tickets pour un évènement.";
        } else if (serviceName.equalsIgnoreCase(TICKET_SERVICE)) {
            response = response + "devenir un membre de la Gest’arc ou avoir une information sur un évènement. Est ce que ça t'intéresse?";
        } else if (serviceName.equalsIgnoreCase(MEMBER_SERVICE)) {
            response = response + "avoir des informations sur un évènement ou encore réserver des ticket pour un évènement si tu le souhaite.";
        }
        responses.add(response);
        int random = (int) (Math.random() * 2);
        String s = responses.get(random);
        return s;
    }

    public String getUserNameFromParams(JSONObject requestObject) {
        String userLastname = new String();

        try {
            JSONObject parametersObject = requestObject.getJSONObject("parameters");
            userLastname = parametersObject.getString("userName");
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            return "nul";
        }
        return userLastname;
    }

    public Student getUser(String lastname) {
        return this.getUser(lastname, "");
    }

    public Student getUser(String lastname, String firstName) {
        Integer moreThanOne = -2;
        Integer none = -1;
        Student student = new Student();
        List<Student> students = new ArrayList<>();

        if ("".equals(firstName) || firstName == null) {
            students = services.getStudentByLastname(lastname);
            if (students.isEmpty()) {
                student.setId(none);
            } else if (students.size() > 1) {
                student.setId(moreThanOne);
            } else {
                student = students.get(0);
            }

        } else {
            student = services.getStudentByLastnameNFirstname(lastname, firstName);
        }

        return student;
    }

    public String getUserFirstNameFromParams(JSONObject requestObject) {
        String userLastname = new String();

        try {
            JSONObject parametersObject = requestObject.getJSONObject("parameters");
            userLastname = parametersObject.getString("userFirstname");
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            return "nul";
        }
        return userLastname;
    }

    private String getNameReadyForEmail(String name) {
        String e1 = "é";
        String e2 = "è";
        String e3 = "ê";
        String e4 = "ë";
        String a1 = "à";
        String a2 = "ä";
        String a3 = "â";
        String u1 = "ü";
        String u2 = "û";
        String u3 = "ù";
        String o1 = "ö";
        String o2 = "ô";
        String o3 = "ò";

        String r;
        r = name.replace(e1, "e");
        r = r.replace(e2, "e");
        r = r.replace(e3, "e");
        r = r.replace(e4, "e");
        r = r.replace(a1, "a");
        r = r.replace(a2, "a");
        r = r.replace(a3, "a");
        r = r.replace(u1, "u");
        r = r.replace(u2, "u");
        r = r.replace(u3, "u");
        r = r.replace(o1, "o");
        r = r.replace(o2, "o");
        r = r.replace(o3, "o");

        return r;
    }

    public String buildUserEmail(String lastname, String firstname) {
        return this.getNameReadyForEmail(firstname).toLowerCase() + "." + this.getNameReadyForEmail(lastname).toLowerCase() + "@he-arc.ch";
    }

}
