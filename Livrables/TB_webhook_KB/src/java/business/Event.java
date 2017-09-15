package business;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author boris.klett
 */
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;


    private Integer id;

    private String title;

    private String location;

    private Time startingTime;

    private Time endingTime;

    private Boolean bookable;

    private int freePlacesNb;

    private int placesNb;

    private int bookablePlacesNb;

    private Double studiantsPrice;

    private Double membersPrice;

    private Double noneStudiantsPrice;

    private Date eventDate;

    private String theme;

    private EventTypes eventType;

    private List<Photo> photos;
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------    

    public Event() {
        photos = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Time getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(Time startingTime) {
        this.startingTime = startingTime;
    }

    public Time getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(Time endingTime) {
        this.endingTime = endingTime;
    }

    public Boolean getBookable() {
        return bookable;
    }

    public void setBookable(Boolean bookable) {
        this.bookable = bookable;
    }

    public int getFreePlacesNb() {
        return freePlacesNb;
    }

    public void setFreePlacesNb(int freePlacesNb) {
        this.freePlacesNb = freePlacesNb;
    }

    public int getPlacesNb() {
        return placesNb;
    }

    public void setPlacesNb(int placesNb) {
        this.placesNb = placesNb;
    }

    public int getBookablePlacesNb() {
        return bookablePlacesNb;
    }

    public void setBookablePlacesNb(int bookablePlacesNb) {
        this.bookablePlacesNb = bookablePlacesNb;
    }

    public Double getStudiantsPrice() {
        return studiantsPrice;
    }

    public void setStudiantsPrice(Double studiantsPrice) {
        this.studiantsPrice = studiantsPrice;
    }

    public Double getMembersPrice() {
        return membersPrice;
    }

    public void setMembersPrice(Double membersPrice) {
        this.membersPrice = membersPrice;
    }

    public Double getNoneStudiantsPrice() {
        return noneStudiantsPrice;
    }

    public void setNoneStudiantsPrice(Double noneStudiantsPrice) {
        this.noneStudiantsPrice = noneStudiantsPrice;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public EventTypes getEventType() {
        return eventType;
    }

    public void setEventType(EventTypes eventType) {
        this.eventType = eventType;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

}
