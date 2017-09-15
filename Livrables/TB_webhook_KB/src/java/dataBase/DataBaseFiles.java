package dataBase;

import business.Event;
import business.EventBooked;
import business.Student;
import business.ToBeMemberRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author boris.klett
 */
@Stateless
public class DataBaseFiles implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    ActionsOnDataBaseFiles aodb;

    public DataBaseFiles() {
    }

    public List<Event> getEvents() {
        return new ArrayList<>(aodb.returnEvents().values());
    }

    public Event getEventByTitle(String eventName) {
        Map<String, Event> e = new HashMap<>();
        e.putAll(aodb.returnEvents());

        if (e.containsKey(eventName)) {
            return e.get(eventName);
        }
        return null;
    }

    public List<Event> getEventListForTicket() {
        List<Event> events = this.getEvents();
        List<Event> e = new ArrayList<>();

        for (Event ev : events) {
            if (ev.getBookable() && ev.getBookablePlacesNb() > 0 && new Date().getTime() < ev.getEventDate().getTime()) {
                e.add(ev);
            }
        }
        return e;
    }

    public List<Student> getStudentByLastname(String lastname) {
        List<Student> students = new ArrayList<>(aodb.returnStudents().values());
        List<Student> s = new ArrayList<>();
        for (Student st : students) {
            if (st.getLastName().equalsIgnoreCase(lastname)) {
                s.add(st);
            }
        }
        return s;
    }

    public Student getStudentByLastnameNFirstname(String lastname, String firstName) {
        List<Student> students = new ArrayList<>(aodb.returnStudents().values());
        for (Student st : students) {
            if (st.getLastName().equalsIgnoreCase(lastname) && st.getFirstName().equalsIgnoreCase(firstName)) {
                return st;
            }
        }
        return new Student();
    }

    public void persistStudent(Student user) {
        List<Student> sl = new ArrayList<>(aodb.returnStudents().values());
        Integer id = 1;
        for (Student sd : sl) {
            if (sd.getId() > id || sd.getId() == id) {
                id = sd.getId() + 1;
            }
        }

        user.setId(id);
        Map<String, Student> sts = new HashMap<>();
        sts.put(user.getEmail(), user);
        aodb.persistStudents(sts);
    }

    public void modifyAnEvent(String eventName, Integer eventFreePlaces, Integer restOfBookablePlaces) {
        Map<String, Event> evts = new HashMap<>();
        Event ev = this.getEventByTitle(eventName);
        ev.setFreePlacesNb(eventFreePlaces);
        ev.setBookablePlacesNb(restOfBookablePlaces);
        evts.put(ev.getTitle(), ev);
        aodb.persistEvents(evts);
    }

    public void bookeEventTikets(EventBooked eB) {
        List<EventBooked> ebs = new ArrayList<>();
        Map<String, Student> sts = new HashMap<>();
        Student st = this.getStudentByLastnameNFirstname(eB.getStudent().getLastName(), eB.getStudent().getFirstName());

        Integer id = 1;
        List<EventBooked> ebs2 = new ArrayList<>();
        ebs2.addAll(aodb.returnEventsBooked());
        for (EventBooked ev : ebs2) {
            if (ev.getId() > id || Objects.equals(ev.getId(), id)) {
                id = ev.getId() + 1;
            }
        }

        eB.setId(id);
        ebs.add(eB);
        aodb.persistEventsBooked(ebs);
        st.getEventBookeds().add(eB);
        sts.put(st.getEmail(), st);

        aodb.persistStudents(sts);
    }

    public List<ToBeMemberRequest> getToBeMemberRequestByStudent(Student user) {
        List<ToBeMemberRequest> t = new ArrayList<>();
        t.add(aodb.returnToBeMemberRequests().get(user.getEmail()));
        return t;
    }

    public void persistAMemberRequest(ToBeMemberRequest tbm) {
        List<ToBeMemberRequest> ts = new ArrayList<>(aodb.returnToBeMemberRequests().values());
        Integer id = 1;
        for (ToBeMemberRequest b : ts) {
            if (b.getId() > id || Objects.equals(b.getId(), id)) {
                id = b.getId() + 1;
            }
        }
        tbm.setId(id);
        Map<String, ToBeMemberRequest> tbmrRs = new HashMap<>();
        tbmrRs.put(tbm.getStudent().getEmail(), tbm);
        aodb.persistToBeMemberRequests(tbmrRs);
    }

}
