package business;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author boris.klett
 */
public class EventBooked implements Serializable {

    private static final long serialVersionUID = 1L;


    private Integer id;

    private Integer nbOfPlaces;

    private Date dateOfReservation;

    private Event event;

    private Student student;

    public EventBooked(Integer id, Integer nbOfPlaces, Date dateOfReservation, Event event, Student student) {
        this.id = id;
        this.nbOfPlaces = nbOfPlaces;
        this.dateOfReservation = dateOfReservation;
        this.event = event;
        this.student = student;
    }

    public EventBooked() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNbOfPlaces() {
        return nbOfPlaces;
    }

    public void setNbOfPlaces(Integer nbOfPlaces) {
        this.nbOfPlaces = nbOfPlaces;
    }

    public Date getDateOfReservation() {
        return dateOfReservation;
    }

    public void setDateOfReservation(Date dateOfReservation) {
        this.dateOfReservation = dateOfReservation;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

}
