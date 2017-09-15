package services;

import business.Event;
import business.EventBooked;
import business.Student;
import business.ToBeMemberRequest;
import dataBase.DataBaseFile2;
import dataBase.DataBaseFiles;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author boris.klett
 */
@Stateless
public class Services implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    DataBaseFiles eventP;

    public Services() {
    }

    public List<Event> getEventList() {
        return eventP.getEvents();
    }

    public Event getEventByTitle(String eventName) {
        return eventP.getEventByTitle(eventName);
    }

    public List<Event> getEventListForTicket() {
        return eventP.getEventListForTicket();
    }

    public List<Student> getStudentByLastname(String lastname) {
        return eventP.getStudentByLastname(lastname);
    }

    public Student getStudentByLastnameNFirstname(String lastname, String firstName) {
        return eventP.getStudentByLastnameNFirstname(lastname, firstName);
    }

    public void persistStudent(Student user) {
        eventP.persistStudent(user);
    }

    public void modifyAnEvent(String eventName, Integer eventFreePlaces, Integer restOfBookablePlaces) {
        eventP.modifyAnEvent(eventName, eventFreePlaces, restOfBookablePlaces);
    }

    public void bookeEventTikets(EventBooked eB) {
        eventP.bookeEventTikets(eB);
    }

    public List<ToBeMemberRequest> getToBeMemberRequestByStudent(Student user) {
        return eventP.getToBeMemberRequestByStudent(user);
    }

    public void persistAMemberRequest(ToBeMemberRequest tbm) {
        eventP.persistAMemberRequest(tbm);
    }
}
