package dataBase;

import business.Event;
import business.EventBooked;
import business.EventTypes;
import business.Photo;
import business.Student;
import business.ToBeMemberRequest;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author boris.klett
 */
@Stateless
public class ActionsOnDataBaseFiles implements Serializable {

    @Inject
    DataBaseInit init;

    private static final long serialVersionUID = 1L;
    final String EVENTS_FILE_NAME = "/DBFiles/EventsDBFile.txt";
//    final String EVENTSBOOKED_FILE_NAME = "\\TB_webhook_KB\\src\\java\\dataBase\\files\\EventsBookedDBFile.txt";
    final String EVENTSBOOKED_FILE_NAME = "/DBFiles/EventsBookedDBFile.txt";
    final String EVENTTYPES_FILE_NAME = "/DBFiles/EventTypesDBFile.txt";
    final String PHOTOS_FILE_NAME = "/DBFiles/PhotosDBFile.txt";
    final String STUDENTS_FILE_NAME = "/DBFiles/StudentsDBFile.txt";
    final String TOBEMEMBERRESQUESTS_FILE_NAME = "/DBFiles/ToBeMemberRequestsDBFile.txt";

    private File file;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Map<String, Event> events = new HashMap<>();
    private Map<Integer, Photo> photos = new HashMap<>();
    private Map<String, EventTypes> eventTypes = new HashMap<>();
    private Map<String, Student> students = new HashMap<>();
    private List<EventBooked> eventsBooked = new ArrayList<>();
    private Map<String, ToBeMemberRequest> toBeMemberRequests = new HashMap<>();

    public ActionsOnDataBaseFiles() {
    }

    public Map<String, Event> returnEvents() {
        this.initAttributes(1);
        return this.events;
    }

    public void persistEvents(Map<String, Event> events) {
        this.initAttributes(1);
        this.events.putAll(events);
        this.persist(1);
    }

    public Map<Integer, Photo> returnPhotos() {
        this.initAttributes(4);
        return photos;
    }

    public void persistPhotos(Map<Integer, Photo> photos) {
        this.initAttributes(4);
        this.photos.putAll(photos);
        this.persist(4);
    }

    public Map<String, EventTypes> returnEventTypes() {
        this.initAttributes(3);
        return eventTypes;
    }

    public void persistEventTypes(Map<String, EventTypes> eventTypes) {
        this.initAttributes(3);
        this.eventTypes.putAll(eventTypes);
        this.persist(3);
    }

    public Map<String, Student> returnStudents() {
        this.initAttributes(5);
        return students;
    }

    public void persistStudents(Map<String, Student> students) {
        this.initAttributes(5);
        this.students.putAll(students);
        this.persist(5);
    }

    public List<EventBooked> returnEventsBooked() {
        this.initAttributes(2);
        return eventsBooked;
    }

    public void persistEventsBooked(List<EventBooked> eventsBooked) {
        this.initAttributes(2);
        this.eventsBooked.addAll(eventsBooked);
        this.persist(2);
    }

    public Map<String, ToBeMemberRequest> returnToBeMemberRequests() {
        this.initAttributes(6);
        return toBeMemberRequests;
    }

    public void persistToBeMemberRequests(Map<String, ToBeMemberRequest> toBeMemberRequests) {
        this.initAttributes(6);
        this.toBeMemberRequests.putAll(toBeMemberRequests);
        this.persist(6);
    }

    public void deleteEventsDBFile() {
        this.deleteDBFiles(1);
    }

    public void deleteEventsBookedDBFile() {
        this.deleteDBFiles(2);
    }

    public void deleteEventTypesDBFile() {
        this.deleteDBFiles(3);
    }

    public void deletePhotosDBFile() {
        this.deleteDBFiles(4);
    }

    public void deleteStudentsDBFile() {
        this.deleteDBFiles(5);
    }

    public void deleteToBeMemberRequestsDBFile() {
        this.deleteDBFiles(6);
    }
//--------------------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------------------

    private void getDataBasePath(String dBFPath) {
        try {
            File folder = new File("/DBFiles");
            if (!folder.exists()) {
                this.init.init();
            }
            file = new File(dBFPath);
//            file = new File(this.getClass().getClassLoader().getResource(dBFPath).getPath());
            file.createNewFile();

        } catch (IOException ex) {
            Logger.getLogger(ActionsOnDataBaseFiles.class.getName()).log(Level.WARNING, null, ex);
        }

    }

    private void initOos(String dBFPath) {
        try {
            this.getDataBasePath(dBFPath);
//            oos = new ObjectOutputStream(new FileOutputStream(file.getPath()));
            oos = new ObjectOutputStream(new FileOutputStream(file.getAbsolutePath()));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ActionsOnDataBaseFiles.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ActionsOnDataBaseFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initOis(String dBFPath) {
        try {
            this.getDataBasePath(dBFPath);
//            ois = new ObjectInputStream(new FileInputStream(file.getPath()));
            ois = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ActionsOnDataBaseFiles.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ActionsOnDataBaseFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param fileNumber correspond to those values: 1 => Event 2 => EventBooked
     * 3 => EventTypes 4 => Photo 5 => Student 6 => ToBeMemberRequest
     */
    private void deleteDBFiles(Integer fileNumber) {

        switch (fileNumber) {
            case 1:
                this.deleteFile(this.EVENTS_FILE_NAME);
                break;
            case 2:
                this.deleteFile(this.EVENTSBOOKED_FILE_NAME);
                break;
            case 3:
                this.deleteFile(this.EVENTTYPES_FILE_NAME);
                break;
            case 4:
                this.deleteFile(this.PHOTOS_FILE_NAME);
                break;
            case 5:
                this.deleteFile(this.STUDENTS_FILE_NAME);
                break;
            case 6:
                this.deleteFile(this.TOBEMEMBERRESQUESTS_FILE_NAME);
                break;
            default:

                break;
        }

    }

    private void deleteFile(String dBFPath) {
        this.getDataBasePath(dBFPath);
        file.delete();
        this.initOos(dBFPath);
    }

    /**
     *
     * @param fileNumber correspond to those values: 1 => Event 2 => EventBooked
     * 3 => EventTypes 4 => Photo 5 => Student 6 => ToBeMemberRequest
     */
    private void persist(Integer fileNumber) {

        switch (fileNumber) {
            case 1:
                this.persistEvent(this.EVENTS_FILE_NAME);
                break;
            case 2:
                this.persistEventsBooked(this.EVENTSBOOKED_FILE_NAME);
                break;
            case 3:
                this.persistEventTypes(this.EVENTTYPES_FILE_NAME);
                break;
            case 4:
                this.persistPhotos(this.PHOTOS_FILE_NAME);
                break;
            case 5:
                this.persistStudents(this.STUDENTS_FILE_NAME);
                break;
            case 6:
                this.persistToBeMemberRequests(this.TOBEMEMBERRESQUESTS_FILE_NAME);
                break;
            default:

                break;
        }

    }

    /**
     *
     * @param fileNumber correspond to those values: 1 => Event 2 => EventBooked
     * 3 => EventTypes 4 => Photo 5 => Student 6 => ToBeMemberRequest
     */
    private void initAttributes(Integer fileNumber) {

        switch (fileNumber) {
            case 1:
                this.initEvent(this.EVENTS_FILE_NAME);
                break;
            case 2:
                this.initEventBooked(this.EVENTSBOOKED_FILE_NAME);
                break;
            case 3:
                this.initEventTypes(this.EVENTTYPES_FILE_NAME);
                break;
            case 4:
                this.initPhotos(this.PHOTOS_FILE_NAME);
                break;
            case 5:
                this.initStudents(this.STUDENTS_FILE_NAME);
                break;
            case 6:
                this.initToBeMemberRequest(this.TOBEMEMBERRESQUESTS_FILE_NAME);
                break;
            default:

                break;
        }

    }
//--------------------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------------------

    private void initEvent(String dBFileName) {
        try {
            this.initOis(dBFileName);
            while (true) {
                try {
                    Event e = (Event) ois.readObject();
                    this.events.put(e.getTitle(), e);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ActionsOnDataBaseFiles.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
        }
    }

    private void initEventBooked(String dBFileName) {
        try {
            this.initOis(dBFileName);
            while (true) {
                try {
                    EventBooked eB = (EventBooked) ois.readObject();
                    this.eventsBooked.add(eB);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ActionsOnDataBaseFiles.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
        }
    }

    private void initEventTypes(String dBFileName) {
        try {
            this.initOis(dBFileName);
            while (true) {
                try {
                    EventTypes evT = (EventTypes) ois.readObject();
                    this.eventTypes.put(evT.getTittle(), evT);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ActionsOnDataBaseFiles.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
        }
    }

    private void initPhotos(String dBFileName) {
        try {
            this.initOis(dBFileName);
            while (true) {
                try {
                    Photo photo = (Photo) ois.readObject();
                    this.photos.put(photo.getId(), photo);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ActionsOnDataBaseFiles.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
        }
    }

    private void initStudents(String dBFileName) {
        try {
            this.initOis(dBFileName);
            while (true) {
                try {
                    Student student = (Student) ois.readObject();
                    this.students.put(student.getEmail(), student);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ActionsOnDataBaseFiles.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
        }
    }

    private void initToBeMemberRequest(String dBFileName) {
        try {
            this.initOis(dBFileName);
            while (true) {
                try {
                    ToBeMemberRequest tbmr = (ToBeMemberRequest) ois.readObject();
                    this.toBeMemberRequests.put(tbmr.getStudent().getEmail(), tbmr);

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ActionsOnDataBaseFiles.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
        }
    }

    private void persistEvent(String dBFileName) {
        try {
            this.initOos(dBFileName);

            for (Event ev : new ArrayList<>(events.values())) {
                oos.writeObject(ev);
            }
        } catch (IOException ex) {
            Logger.getLogger(ActionsOnDataBaseFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void persistEventsBooked(String dBFileName) {
        try {
            this.initOos(dBFileName);

            for (EventBooked evB : eventsBooked) {
                oos.writeObject(evB);
            }
        } catch (IOException ex) {
            Logger.getLogger(ActionsOnDataBaseFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void persistEventTypes(String dBFileName) {
        try {
            this.initOos(dBFileName);

            for (EventTypes evT : new ArrayList<>(eventTypes.values())) {
                oos.writeObject(evT);
            }
        } catch (IOException ex) {
            Logger.getLogger(ActionsOnDataBaseFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void persistPhotos(String dBFileName) {
        try {
            this.initOos(dBFileName);

            for (Photo photo : new ArrayList<>(photos.values())) {
                oos.writeObject(photo);
            }
        } catch (IOException ex) {
            Logger.getLogger(ActionsOnDataBaseFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void persistStudents(String dBFileName) {
        try {
            this.initOos(dBFileName);

            for (Student student : new ArrayList<>(students.values())) {
                oos.writeObject(student);
            }
        } catch (IOException ex) {
            Logger.getLogger(ActionsOnDataBaseFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void persistToBeMemberRequests(String dBFileName) {
        try {
            this.initOos(dBFileName);

            for (ToBeMemberRequest tbmr : new ArrayList<>(toBeMemberRequests.values())) {
                oos.writeObject(tbmr);
            }
        } catch (IOException ex) {
            Logger.getLogger(ActionsOnDataBaseFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//---------------------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------------------

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
