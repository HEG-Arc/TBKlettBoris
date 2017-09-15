package dataBase;

import business.Event;
import business.EventBooked;
import business.EventTypes;
import business.Photo;
import business.Student;
import business.ToBeMemberRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import utils.Utils;

/**
 *
 * @author boris.klett
 */
@Stateless
public class DataBaseInit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private Utils utils;
    private final ActionsOnDataBaseFiles dbF = new ActionsOnDataBaseFiles();
    private final DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
    private Map<String, Student> students;
    private Map<String, ToBeMemberRequest> toBeMemberRequests;
    private Map<String, EventTypes> eventTypes;
    private Map<String, Event> events;
    private List<EventBooked> eventsBooked;
    private Map<Integer, Photo> photos;

    public DataBaseInit() {
    }

    private void studentsInit() {
        dbF.deleteStudentsDBFile();
        students = new HashMap<>();
        Student student;
        //Student 1-------------------------------------------------------------
        student = new Student();
        student.setId(1);
        student.setLastName("Klett");
        student.setFirstName("Boris");
        student.setEmail("boris.klett@he-arc.ch");
        students.put(student.getEmail(), student);

        //Student 2-------------------------------------------------------------
        student = new Student();
        student.setId(2);
        student.setLastName("Habegger");
        student.setFirstName("Steven");
        student.setEmail("steven.habegger@he-arc.ch");
        students.put(student.getEmail(), student);

        //Student 3-------------------------------------------------------------
        student = new Student();
        student.setId(3);
        student.setLastName("Voirol");
        student.setFirstName("Melissa");
        student.setEmail("melissa.voirol@he-arc.ch");
        students.put(student.getEmail(), student);

        //Student 4-------------------------------------------------------------
        student = new Student();
        student.setId(4);
        student.setLastName("Schneider");
        student.setFirstName("Julien");
        student.setEmail("julien.schneider@he-arc.ch");
        students.put(student.getEmail(), student);

        //Student 5-------------------------------------------------------------
        student = new Student();
        student.setId(5);
        student.setLastName("Sapin");
        student.setFirstName("Florianne");
        student.setEmail("florianne.sapin@he-arc.ch");
        students.put(student.getEmail(), student);

        //Student 6-------------------------------------------------------------
        student = new Student();
        student.setId(6);
        student.setLastName("Grangier");
        student.setFirstName("Stephane");
        student.setEmail("stephane.grangier@he-arc.ch");
        students.put(student.getEmail(), student);

        //Student 7-------------------------------------------------------------
        student = new Student();
        student.setId(7);
        student.setLastName("Bakouloukila");
        student.setFirstName("Wilvie");
        student.setEmail("wilvie.bakouloukila@he-arc.ch");
        students.put(student.getEmail(), student);

        //Student 8-------------------------------------------------------------
        student = new Student();
        student.setId(8);
        student.setLastName("Klett");
        student.setFirstName("Wilvie");
        student.setEmail("wilvie.klett@he-arc.ch");
        students.put(student.getEmail(), student);

        //Student 9-------------------------------------------------------------
        student = new Student();
        student.setId(9);
        student.setLastName("Brumann");
        student.setFirstName("Veronique");
        student.setEmail("veronique.brumann@he-arc.ch");
        students.put(student.getEmail(), student);
        dbF.persistStudents(students);

    }

    private void toBeMemberRequestsInit() {
        try {
            toBeMemberRequests = new HashMap<>();
            ToBeMemberRequest toBeMemberRequest;
            Student user;
            students = new HashMap<>();
            students.putAll(dbF.returnStudents());
            dbF.deleteToBeMemberRequestsDBFile();

            // Request 1----------------------------------------------------------------
            toBeMemberRequest = new ToBeMemberRequest();
            user = new Student();

            user = students.get("florianne.sapin@he-arc.ch");
            toBeMemberRequest.setId(1);
            toBeMemberRequest.setRequestDate(sourceFormat.parse("01/08/2017"));
            toBeMemberRequest.setTraitmentDate(sourceFormat.parse("04/08/2017"));
            toBeMemberRequest.setStudent(user);
            toBeMemberRequests.put(user.getEmail(), toBeMemberRequest);

            // Request 2----------------------------------------------------------------
            toBeMemberRequest = new ToBeMemberRequest();
            user = new Student();

            user = students.get("steven.habegger@he-arc.ch");
            toBeMemberRequest.setId(2);
            toBeMemberRequest.setRequestDate(sourceFormat.parse("17/07/2017"));
            toBeMemberRequest.setTraitmentDate(sourceFormat.parse("25/07/2017"));
            toBeMemberRequest.setStudent(user);
            toBeMemberRequests.put(user.getEmail(), toBeMemberRequest);

            // Request 3----------------------------------------------------------------
            toBeMemberRequest = new ToBeMemberRequest();
            user = new Student();

            user = students.get("boris.klett@he-arc.ch");
            toBeMemberRequest.setId(3);
            toBeMemberRequest.setRequestDate(sourceFormat.parse("11/08/2017"));
            toBeMemberRequest.setStudent(user);
            toBeMemberRequests.put(user.getEmail(), toBeMemberRequest);

            // Request 4----------------------------------------------------------------
            toBeMemberRequest = new ToBeMemberRequest();
            user = new Student();

            user = students.get("wilvie.klett@he-arc.ch");
            toBeMemberRequest.setId(4);
            toBeMemberRequest.setRequestDate(sourceFormat.parse("24/08/2017"));
            toBeMemberRequest.setStudent(user);
            toBeMemberRequests.put(user.getEmail(), toBeMemberRequest);

            dbF.persistToBeMemberRequests(toBeMemberRequests);

        } catch (ParseException ex) {
            Logger.getLogger(DataBaseFile2.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void initEvents() {
        events = new HashMap<>();
        dbF.deleteEventsDBFile();
        dbF.deletePhotosDBFile();
        photos = new HashMap<>();
        try {
            //Event 1 ----------------------------------------------------------
            Event event = new Event();

            Calendar cals = Calendar.getInstance();
            cals.set(Calendar.HOUR_OF_DAY, 22);
            cals.set(Calendar.MINUTE, 30);
            Date sh = cals.getTime();

            Calendar cale = Calendar.getInstance();
            cale.set(Calendar.HOUR_OF_DAY, 2);
            cale.set(Calendar.MINUTE, 30);
            Date eh = cale.getTime();

            event.setId(1);
            event.setTitle("Bal des etudiants");
            event.setStartingTime(new Time(sh.getTime()));
            event.setEndingTime(new Time(eh.getTime()));
            event.setLocation("Les arcades");
            event.setEventDate(sourceFormat.parse("23/06/2017"));
            event.setTheme("Soiree blanche");
            event.setEventType(null);
            event.setBookable(true);
            event.setFreePlacesNb(1);
            event.setPlacesNb(200);
            event.setBookablePlacesNb(0);
            event.setStudiantsPrice(Double.valueOf("15.00"));
            event.setMembersPrice(Double.valueOf("5.00"));
            event.setNoneStudiantsPrice(Double.valueOf("20.00"));

            Photo photo = new Photo();
            photo.setId(1);
            photo.setEvent(event);
            photo.setFbLink("https://www.facebook.com/boris.klett.1/media_set?set=a.1376537349287705.1073741828.100007943583575&type=3");

            event.getPhotos().add(photo);
            photos.put(photo.getId(), photo);
            dbF.persistPhotos(photos);
            events.put(event.getTitle(), event);

            //Event 2-----------------------------------------------------------
            event = new Event();

            cals = Calendar.getInstance();
            cals.set(Calendar.HOUR_OF_DAY, 18);
            cals.set(Calendar.MINUTE, 00);
            sh = cals.getTime();
            cale = Calendar.getInstance();
            cale.set(Calendar.HOUR_OF_DAY, 20);
            cale.set(Calendar.MINUTE, 30);
            eh = cale.getTime();

            event.setId(2);
            event.setTitle("Pot de bienvenue");
            event.setStartingTime(new Time(sh.getTime()));
            event.setEndingTime(new Time(eh.getTime()));
            event.setLocation("Antidote");
            event.setEventDate(sourceFormat.parse("28/09/2017"));
            event.setTheme("Annees 80");
            event.setEventType(null);
            event.setBookable(false);
            event.setFreePlacesNb(0);
            event.setPlacesNb(0);
            event.setBookablePlacesNb(0);
            event.setStudiantsPrice(Double.valueOf("0.00"));
            event.setMembersPrice(Double.valueOf("0.00"));
            event.setNoneStudiantsPrice(Double.valueOf("0.00"));
            events.put(event.getTitle(), event);

            //Event 3-----------------------------------------------------------
            event = new Event();
            cals = Calendar.getInstance();
            cals.set(Calendar.HOUR_OF_DAY, 9);
            cals.set(Calendar.MINUTE, 30);
            sh = cals.getTime();
            cale = Calendar.getInstance();
            cale.set(Calendar.HOUR_OF_DAY, 18);
            cale.set(Calendar.MINUTE, 30);
            eh = cale.getTime();

            event.setId(3);
            event.setTitle("La journee sportive");
            event.setStartingTime(new Time(sh.getTime()));
            event.setEndingTime(new Time(eh.getTime()));
            event.setLocation("Stade la maladiere");
            event.setEventDate(sourceFormat.parse("05/08/2017"));
            event.setTheme(null);
            event.setEventType(null);
            event.setBookable(true);
            event.setFreePlacesNb(45);
            event.setPlacesNb(300);
            event.setBookablePlacesNb(20);
            event.setStudiantsPrice(Double.valueOf("0.00"));
            event.setMembersPrice(Double.valueOf("0.00"));
            event.setNoneStudiantsPrice(Double.valueOf("50.00"));
            events.put(event.getTitle(), event);

            //Event 4-----------------------------------------------------------
            event = new Event();
            cals = Calendar.getInstance();
            cals.set(Calendar.HOUR_OF_DAY, 18);
            cals.set(Calendar.MINUTE, 30);
            sh = cals.getTime();
            cale = Calendar.getInstance();
            cale.set(Calendar.HOUR_OF_DAY, 20);
            cale.set(Calendar.MINUTE, 00);
            eh = cale.getTime();

            event.setId(4);
            event.setTitle("La Gest'arc");
            event.setStartingTime(new Time(sh.getTime()));
            event.setEndingTime(new Time(eh.getTime()));
            event.setLocation("Auditoire1, HEG-arc");
            event.setEventDate(sourceFormat.parse("18/10/2017"));
            event.setTheme(null);
            event.setEventType(null);
            event.setBookable(true);
            event.setFreePlacesNb(60);
            event.setPlacesNb(80);
            event.setBookablePlacesNb(60);
            event.setStudiantsPrice(Double.valueOf("0.00"));
            event.setMembersPrice(Double.valueOf("0.00"));
            event.setNoneStudiantsPrice(Double.valueOf("0.00"));
            events.put(event.getTitle(), event);
            dbF.persistEvents(events);
        } catch (ParseException ex) {
            Logger.getLogger(DataBaseFile2.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void eventTypeInit() {
        eventTypes = new HashMap<>();
        List<Event> eventsList;
        dbF.deleteEventTypesDBFile();
        events = new HashMap<>();
        events.putAll(dbF.returnEvents());

        //EventType 1-----------------------------------------------------------
        EventTypes eventType = new EventTypes();
        eventType.setId(1);
        eventType.setTittle("Soiree etudiante");

        events.get("Bal des etudiants").setEventType(eventType);
        events.get("Pot de bienvenue").setEventType(eventType);
        eventsList = new ArrayList<>();
        eventsList.add(events.get("Bal des etudiants"));
        eventsList.add(events.get("Pot de bienvenue"));
        eventType.setEventsList(eventsList);

        eventTypes.put(eventType.getTittle(), eventType);

        //EventType 2-----------------------------------------------------------
        eventType = new EventTypes();
        eventType.setId(2);
        eventType.setTittle("Activite sportive");

        events.get("La journee sportive").setEventType(eventType);
        eventsList = new ArrayList<>();
        eventsList.add(events.get("La journee sportive"));
        eventType.setEventsList(eventsList);

        eventTypes.put(eventType.getTittle(), eventType);

        //EventType 3-----------------------------------------------------------
        eventType = new EventTypes();
        eventType.setId(3);
        eventType.setTittle("Presentation");

        events.get("La Gest'arc").setEventType(eventType);
        eventsList = new ArrayList<>();
        eventsList.add(events.get("La Gest'arc"));
        eventType.setEventsList(eventsList);

        eventTypes.put(eventType.getTittle(), eventType);

        //EventType 4-----------------------------------------------------------
        eventType = new EventTypes();
        eventType.setId(4);
        eventType.setTittle("Sortie scolaire");

        eventTypes.put(eventType.getTittle(), eventType);
        dbF.persistEventTypes(eventTypes);
        dbF.deleteEventsDBFile();
        dbF.persistEvents(events);

    }

    private void eventsBookedInit() {
        this.events = new HashMap<>();
        this.events.putAll(dbF.returnEvents());

        this.students = new HashMap<>();
        this.students.putAll(dbF.returnStudents());

        eventsBooked = new ArrayList<>();
        dbF.deleteEventsBookedDBFile();
        try {
            EventBooked eventBooked;
            Event event;
            Student student;
            //eventBooked 1---------------------------------------------------------event = new Event();
            eventBooked = new EventBooked();
            eventBooked.setId(1);
            eventBooked.setDateOfReservation(sourceFormat.parse("02/07/2017"));
            event = events.get("La journee sportive");
            eventBooked.setEvent(event);
            eventBooked.setNbOfPlaces(1);
            student = students.get("stephane.grangier@he-arc.ch");
            eventBooked.setStudent(student);
            students.get(student.getEmail()).getEventBookeds().add(eventBooked);
            eventsBooked.add(eventBooked);

            //eventBooked 2---------------------------------------------------------
            eventBooked = new EventBooked();
            eventBooked.setId(2);
            eventBooked.setDateOfReservation(sourceFormat.parse("01/08/2017"));
            event = events.get("Bal des etudiants");
            eventBooked.setEvent(event);
            eventBooked.setNbOfPlaces(5);
            student = students.get("boris.klett@he-arc.ch");
            eventBooked.setStudent(student);
            students.get(student.getEmail()).getEventBookeds().add(eventBooked);
            eventsBooked.add(eventBooked);

            //eventBooked 3---------------------------------------------------------
            eventBooked = new EventBooked();
            eventBooked.setId(3);
            eventBooked.setDateOfReservation(sourceFormat.parse("01/09/2017"));
            event = events.get("La Gest'arc");
            eventBooked.setEvent(event);
            eventBooked.setNbOfPlaces(2);
            student = students.get("florianne.sapin@he-arc.ch");
            eventBooked.setStudent(student);
            students.get(student.getEmail()).getEventBookeds().add(eventBooked);
            eventsBooked.add(eventBooked);

            //eventBooked 4---------------------------------------------------------
            eventBooked = new EventBooked();
            eventBooked.setId(4);
            eventBooked.setDateOfReservation(sourceFormat.parse("23/08/2017"));
            event = events.get("La journee sportive");
            eventBooked.setEvent(event);
            eventBooked.setNbOfPlaces(5);
            student = students.get("wilvie.klett@he-arc.ch");
            eventBooked.setStudent(student);
            students.get(student.getEmail()).getEventBookeds().add(eventBooked);
            eventsBooked.add(eventBooked);

            //eventBooked 5---------------------------------------------------------
            eventBooked = new EventBooked();
            eventBooked.setId(5);
            eventBooked.setDateOfReservation(sourceFormat.parse("24/08/2017"));
            event = events.get("Bal des etudiants");
            eventBooked.setEvent(event);
            eventBooked.setNbOfPlaces(2);
            student = students.get("melissa.voirol@he-arc.ch");
            eventBooked.setStudent(student);
            students.get(student.getEmail()).getEventBookeds().add(eventBooked);
            eventsBooked.add(eventBooked);

            dbF.persistEventsBooked(eventsBooked);
            dbF.deleteStudentsDBFile();
            dbF.persistStudents(students);

        } catch (ParseException ex) {
            Logger.getLogger(DataBaseFile2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String init() {
        this.studentsInit();
        this.initEvents();
        this.eventTypeInit();
        this.eventsBookedInit();
        this.toBeMemberRequestsInit();
        return utils.getJsonResponse("Init done sucessfully!", "sucess", 1, "", "");
    }

//    public static void main(String[] args) {
//        Student st = new Student();
//        st.setId(1);
//        st.setEmail("test");
//        st.setFirstName("test");
//        st.setLastName("test");
//
//        ObjectOutputStream oos;
//        ObjectInputStream ois;
//
//        File folder = new File("/testFinal2");
//        File file = new File("/testFinal2/test.txt");
//        if (!folder.exists()) {
//            folder.mkdir();
//        }
//        try {
//            file.createNewFile();
//
//            System.out.println("Absolute path: " + file.getAbsolutePath());
//        } catch (IOException ex) {
//        }
//        DataBaseInit d = new DataBaseInit();
//        d.init();
//        System.out.println("Process done succesfully!");
//
//    }
}
