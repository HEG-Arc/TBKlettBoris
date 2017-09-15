package beans;

import dataBase.DataBaseInit;
import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
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
@Named
@ApplicationScoped
public class TestBean implements Serializable {

    @Inject
    Utils utils;

    @Inject
    ChatbotGeneralLogic general;

    @Inject
    TicketReservation ticket;
    @Inject
    MembersRegistration member;
    @Inject
    TicketNMemberServices tnmember;
    @Inject
    InformationResearch info;
    @Inject
    DataBaseInit init;

    private String test;
    private String test2;
    private String test3;
    private String test4;
    private static final long serialVersionUID = 1L;

    public TestBean() {
    }

    public String getTest() {
        return null;

    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getTest2() {
        return init.init();

    }

    public void setTest2(String test2) {
        this.test2 = test2;
    }

    public String getTest3() {
        return null;
    }

    public void setTest3(String test3) {
        this.test3 = test3;
    }

    public String getTest4() {
        return null;
    }

    public void setTest4(String test4) {
        this.test4 = test4;
    }

}
