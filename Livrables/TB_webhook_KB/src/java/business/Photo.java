package business;

import java.io.Serializable;

/**
 *
 * @author boris.klett
 */
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;


    private Integer id;

    private String fbLink;

    private Event event;

    public Photo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFbLink() {
        return fbLink;
    }

    public void setFbLink(String fbLink) {
        this.fbLink = fbLink;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

}
