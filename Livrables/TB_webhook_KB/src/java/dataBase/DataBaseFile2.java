package dataBase;

import business.Event;
import business.EventBooked;
import business.EventTypes;
import business.Photo;
import business.Student;
import business.ToBeMemberRequest;
import java.io.Serializable;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

/**
 *
 * @author boris.klett
 */
@Stateless
public class DataBaseFile2 implements Serializable {

    private static final long serialVersionUID = 1L;

    private final DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
//    @Inject
//    private Utils utils;
    private Map<String, Event> events = new HashMap<>();
    private Map<Integer, Photo> photos = new HashMap<>();
    private Map<String, EventTypes> eventTypes = new HashMap<>();
    private Map<String, Student> students = new HashMap<>();
    private List<EventBooked> eventsBooked = new ArrayList<>();
    private Map<String, ToBeMemberRequest> toBeMemberRequests = new HashMap<>();

    public DataBaseFile2() {
        this.initializer();

    }

    /**
     * Post construct initializer
     */
    @PostConstruct
    public void postConst() {
        this.initializer();

    }

    private void initializer() {
        if (events.isEmpty()) {
//            this.init();
        }
        if (eventTypes.isEmpty()) {
//            eventTypeInit();
        }

        if (eventsBooked.isEmpty()) {
//            eventsBookedInit();
        }

//        if (toBeMemberRequests.isEmpty()) {
//            toBeMemberRequestsInit();
//        }
    }


   



    public Map<String, Event> getEvents() {
        return events;
    }

    public void setEvents(Map<String, Event> events) {
        this.events = events;
    }

    public Map<Integer, Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Map<Integer, Photo> photos) {
        this.photos = photos;
    }

    public Map<String, EventTypes> getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(Map<String, EventTypes> eventTypes) {
        this.eventTypes = eventTypes;
    }

    public Map<String, Student> getStudents() {
        return students;
    }

    public void setStudents(Map<String, Student> students) {
        this.students = students;
    }

    public void addStudent(Student student) {
        this.students.put(student.getEmail(), student);
    }

    public Student getStudentById(Integer id) {
        for (Student s : new ArrayList<>(students.values())) {
            if (Objects.equals(s.getId(), id)) {
                return s;
            }
        }

        return null;
    }

    public Event getEventById(Integer id) {
        for (Event e : new ArrayList<>(events.values())) {
            if (Objects.equals(e.getId(), id)) {
                return e;
            }
        }

        return null;
    }

    public List<EventBooked> getEventsBooked() {
        return eventsBooked;
    }

    public void setEventsBooked(List<EventBooked> eventsBooked) {
        this.eventsBooked = eventsBooked;
    }

    public Map<String, ToBeMemberRequest> getToBeMemberRequests() {
        return toBeMemberRequests;
    }

    public void setToBeMemberRequests(Map<String, ToBeMemberRequest> toBeMemberRequests) {
        this.toBeMemberRequests = toBeMemberRequests;
    }

}
