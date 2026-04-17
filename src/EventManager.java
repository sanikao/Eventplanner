import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class EventManager {

    private List<Event> events;
    private int idCounter = 1;
    private static final String FILE_NAME = "events.txt";

    public EventManager() {
        this.events = new ArrayList<>();
        loadFromFile();
    }

    public String generateId() {
        return "EVT-" + idCounter++;
    }

    public void addEvent(Event event) {
        events.add(event);
        saveToFile();
        System.out.println("Event added successfully!");
    }

    public void getAllEvents() {
        if (events.isEmpty()) {
            System.out.println("No events found.");
            return;
        }
        for (Event e : events) {
            e.display();
        }
    }

    public Event findById(String id) {
        for (Event e : events) {
            if (e.getId().equals(id)) return e;
        }
        return null;
    }

    public void updateEvent(String id, String newName, String newDate, String newLocation) {
        Event event = findById(id);
        if (event == null) {
            System.out.println("Event not found.");
            return;
        }
        event.setName(newName);
        event.setDate(newDate);
        event.setLocation(newLocation);
        saveToFile();
        System.out.println("Event updated!");
    }

    public void deleteEvent(String id) {
        Event event = findById(id);
        if (event == null) {
            System.out.println("Event not found.");
            return;
        }
        events.remove(event);
        saveToFile();
        System.out.println("Event deleted!");
    }

    public void addParticipant(String eventId, Participant participant) {
        Event event = findById(eventId);
        if (event == null) {
            System.out.println("Event not found.");
            return;
        }
        event.addParticipant(participant);
        saveToFile();
        System.out.println("Participant added!");
    }

    public void showParticipants(String eventId) {
        Event event = findById(eventId);
        if (event == null) {
            System.out.println("Event not found.");
            return;
        }
        List<Participant> participants = event.getParticipants();
        if (participants.isEmpty()) {
            System.out.println("No participants yet.");
            return;
        }
        System.out.println("Participants of " + event.getName() + ":");
        for (Participant p : participants) {
            p.display();
        }
    }

    public void searchByName(String keyword) {
        boolean found = false;
        for (Event e : events) {
            if (e.getName().toLowerCase().contains(keyword.toLowerCase())) {
                e.display();
                found = true;
            }
        }
        if (!found) System.out.println("No events matched your search.");
    }

    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Event e : events) {
                String type = e.getClass().getSimpleName();
                String extra = "";
                if (e instanceof Concert) extra = ((Concert) e).getArtist();
                else if (e instanceof Conference) extra = ((Conference) e).getTopic();
                else if (e instanceof Birthday) extra = ((Birthday) e).getBirthdayPerson();

                writer.println(type + "," + e.getId() + "," + e.getName() + ","
                        + e.getDate() + "," + e.getLocation() + "," + extra);

                for (Participant p : e.getParticipants()) {
                    writer.println("PARTICIPANT," + p.getName() + "," + p.getEmail());
                }
            }
        } catch (IOException ex) {
            System.out.println("Error saving data: " + ex.getMessage());
        }
    }

    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            Event currentEvent = null;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 6);
                if (parts[0].equals("PARTICIPANT") && currentEvent != null) {
                    currentEvent.addParticipant(new Participant(parts[1], parts[2]));
                } else {
                    currentEvent = switch (parts[0]) {
                        case "Concert" -> new Concert(parts[1], parts[2], parts[3], parts[4], parts[5]);
                        case "Conference" -> new Conference(parts[1], parts[2], parts[3], parts[4], parts[5]);
                        case "Birthday" -> new Birthday(parts[1], parts[2], parts[3], parts[4], parts[5]);
                        default -> null;
                    };
                    if (currentEvent != null) events.add(currentEvent);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error loading data: " + ex.getMessage());
        }
    }

    public void exportToCSV() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("export.csv"))) {
            writer.println("Type,ID,Name,Date,Location,Extra");
            for (Event e : events) {
                String type = e.getClass().getSimpleName();
                String extra = "";
                if (e instanceof Concert) extra = ((Concert) e).getArtist();
                else if (e instanceof Conference) extra = ((Conference) e).getTopic();
                else if (e instanceof Birthday) extra = ((Birthday) e).getBirthdayPerson();
                writer.println(type + "," + e.getId() + "," + e.getName() + ","
                        + e.getDate() + "," + e.getLocation() + "," + extra);
            }
            System.out.println("Data exported to export.csv!");
        } catch (IOException ex) {
            System.out.println("Error exporting data: " + ex.getMessage());
        }
    }

    public void importFromCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader("export.csv"))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 6);
                Event event = switch (parts[0]) {
                    case "Concert" -> new Concert(parts[1], parts[2], parts[3], parts[4], parts[5]);
                    case "Conference" -> new Conference(parts[1], parts[2], parts[3], parts[4], parts[5]);
                    case "Birthday" -> new Birthday(parts[1], parts[2], parts[3], parts[4], parts[5]);
                    default -> null;
                };
                if (event != null) events.add(event);
            }
            saveToFile();
            System.out.println("Data imported successfully!");
        } catch (IOException ex) {
            System.out.println("Error importing data: " + ex.getMessage());
        }
    }

    public String getAllEventsAsString() {
        StringBuilder sb = new StringBuilder();
        for (Event e : events) {
            sb.append("ID: ").append(e.getId()).append("\n");
            sb.append("Name: ").append(e.getName()).append("\n");
            sb.append("Date: ").append(e.getDate()).append("\n");
            sb.append("Location: ").append(e.getLocation()).append("\n");
            sb.append("Participants: ").append(e.getParticipants().size()).append("\n");
            sb.append("----------------------------\n");
        }
        return sb.toString();
    }

    public String getParticipantsAsString(String eventId) {
        Event event = findById(eventId);
        if (event == null) return "Event not found.";
        StringBuilder sb = new StringBuilder();
        for (Participant p : event.getParticipants()) {
            sb.append("- ").append(p.getName()).append(" (").append(p.getEmail()).append(")\n");
        }
        return sb.toString();
    }

    public String searchByNameAsString(String keyword) {
        StringBuilder sb = new StringBuilder();
        for (Event e : events) {
            if (e.getName().toLowerCase().contains(keyword.toLowerCase())) {
                sb.append("ID: ").append(e.getId()).append(" | ")
                        .append(e.getName()).append(" | ")
                        .append(e.getDate()).append("\n");
            }
        }
        return sb.toString();
    }

    public List<Event> getEventsList() {
        return events;
    }
}