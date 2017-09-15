package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author boris.klett
 */
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;


    private Integer id;

    private String lastName;

    private String firstName;

    private String email;

    private List<EventBooked> eventBookeds;

    public Student() {
        eventBookeds = new ArrayList<>();
    }

    public Student(Integer id, String lastName, String firstName, String email, List<EventBooked> eventBookeds) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.eventBookeds = eventBookeds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<EventBooked> getEventBookeds() {
        return eventBookeds;
    }

    public void setEventBookeds(List<EventBooked> eventBookeds) {
        this.eventBookeds = eventBookeds;
    }

}
