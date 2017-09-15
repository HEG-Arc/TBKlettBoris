package business.jacksonClasses;

import java.io.Serializable;

/**
 *
 * @author boris.klett
 */
public class Parameters implements Serializable {

    private static final long serialVersionUID = 1L;

    private String serviceName;
    private String eventName;
    private String userName;
    private String userFirstname;
    private String needed_information;
    private Integer number_of_tickets;

    public Parameters(String serviceName, String eventName, String userName, String userFirstname, String needed_information, Integer number_of_tickets) {
        this.serviceName = serviceName;
        this.eventName = eventName;
        this.userName = userName;
        this.userFirstname = userFirstname;
        this.needed_information = needed_information;
        this.number_of_tickets = number_of_tickets;
    }

    public Parameters() {
        this.serviceName = "";
        this.eventName = "";
        this.userName = "";
        this.userFirstname = "";
        this.needed_information = "";
        this.number_of_tickets = -1;

    }

    public String getUserFirstname() {
        return userFirstname;
    }

    public void setUserFirstname(String userFirstname) {
        this.userFirstname = userFirstname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getNeeded_information() {
        return needed_information;
    }

    public void setNeeded_information(String needed_information) {
        this.needed_information = needed_information;
    }

    public Integer getNumber_of_tickets() {
        return number_of_tickets;
    }

    public void setNumber_of_tickets(Integer number_of_tickets) {
        this.number_of_tickets = number_of_tickets;
    }

}
