import java.util.ArrayList;
import java.util.List;

public abstract class Event {

    private String id;
    private String name;
    private String date;
    private String location;
    private List<Participant> participants;

    public Event(String id, String name, String date, String location) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.location = location;
        this.participants = new ArrayList<>();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDate() { return date; }
    public String getLocation() { return location; }
    public List<Participant> getParticipants() { return participants; }

    public void setName(String name) { this.name = name; }
    public void setDate(String date) { this.date = date; }
    public void setLocation(String location) { this.location = location; }

    public void addParticipant(Participant p) {
        participants.add(p);
    }

    public abstract void display();

    protected void displayBasicInfo() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Date: " + date);
        System.out.println("Location: " + location);
        System.out.println("Participants: " + participants.size());

    }
}