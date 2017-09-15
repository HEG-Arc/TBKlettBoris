package webhook.logic;

import business.Student;
import business.ToBeMemberRequest;
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
public class MembersRegistration implements Serializable {

    private static final long serialVersionUID = 1L;

    public final String ERROR_MESSAGE = "Ecoute j'ai un petit problème là je pense que c'est dû à ma connexion internet. Tu peux nous contacter directement par email ou alors essaye de revenir un peu plus tard s'il te plaît.";

    @Inject
    private Services services;

    @Inject
    private Utils utils;

    public MembersRegistration() {
    }

    private Boolean memberRequestAlreadyExist(Student user) {
        Boolean exist = false;
        List<ToBeMemberRequest> requests = new ArrayList<>();
        requests = services.getToBeMemberRequestByStudent(user);
        if (!requests.isEmpty()) {
            if (requests.get(0) != null) {
                exist = true;
            }
        }
        return exist;
    }

    public String memberRequestRegistration(JSONObject requestObject) {
        
        String serviceName = utils.getParamFromContext(requestObject, "context-members-registration", "serviceName");
        String userName = utils.getParamFromContext(requestObject, "context-members-registration", "userName");
        String userFirstName = utils.getParamFromContext(requestObject, "context-members-registration", "userFirstname");
//        String serviceName = "inscription";
//        String userName = "Amta";
//        String userFirstName = "Ulrich";

        List<String> responses = new ArrayList<>();
        Student user = services.getStudentByLastnameNFirstname(userName, userFirstName);
        String r = "";
        if (memberRequestAlreadyExist(user)) {
            ToBeMemberRequest tbm = services.getToBeMemberRequestByStudent(user).get(0);
            if (tbm.getTraitmentDate() == null) {
                r = "Ok ! Ha mais " + userFirstName + " j’avais déjà enregistré ta demande le " + utils.getDateformat2().format(tbm.getRequestDate()) + " dernier. Par contre c’est vrai qu’elle n’a pas encore été traitée. Avec tout ce qu’il y a à faire ces jours, je pense qu’ils sont tous overbookés. Je te dirais bien d’attendre encore un peu mais au pire essaye de passer directement au secrétariat de l’école pour le faire si tu veux (y).";
            } else {
                r = "Ok ! Ha mais " + userFirstName + " j’avais déjà enregistré ta demande le " + utils.getDateformat2().format(tbm.getRequestDate()) + " dernier et elle a déjà été traitée. Ecoute, contrôle encore ta boîte email de l’école tu devrais avoir reçu une confirmation.";
            }

        } else {
            responses.add("Ok " + userFirstName.toLowerCase() + " j’ai enregistré ta demande pour devenir membre de la Gest’arc. Si jamais, un membre de la Gest’arc te confirmera ton adhésion et te convoquera par email, dans les prochains jours.");
            responses.add("Ça marche. Écoute j’ai enregistré ta demande " + userFirstName.toLowerCase() + ", par contre un membre de la Gest’arc te confirmera ton adhésion et te convoquera par email, dans les prochains jours :D .");
            ToBeMemberRequest tbm = new ToBeMemberRequest();
            tbm.setRequestDate(new Date());
            tbm.setStudent(user);
            int random;
            random = (int) (Math.random() * 2);
            r = responses.get(random);
            services.persistAMemberRequest(tbm);
        }
        return utils.getJsonResponse(r, "context-service-done", 1, serviceName, "", userName, userFirstName);
    }

}
