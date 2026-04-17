Event Planner

A Java OOP project for managing events. You can add, edit, delete and view events, add participants, search by name, and export/import data as CSV. It also has a simple GUI built with Java Swing.

**Student:** Sanira Samaganova  
**Course:** COMFCI25

## What does it do?

This app lets you manage three types of events concerts, conferences, and birthdays. Everything is saved to a file so your data stays after you close the app.

**Main features:**
Add, view, edit and delete events (CRUD)
Three event types: Concert, Conference, Birthday
Add participants to events (with email validation)
Search events by name
Export and import data in CSV format
GUI window built with Java Swing


## Project goals

Practice OOP principles: encapsulation, inheritance and polymorphism
Work with file I/O to save and load data
Build a simple but functional GUI
Handle user input errors properly


## Project requirements

1. Users can create new events (name, date, location, type)
2. Users can view a list of all events
3. Users can edit existing events
4. Users can delete events
5. Users can add participants to events
6. Users can view participants of a specific event
7. Input is validated — empty fields and wrong email formats are rejected
8. Data is saved to a file and loaded on startup
9. Three event types use inheritance from one parent class
10. Users can export and import data in CSV format


## OOP concepts used

**Encapsulation** all class fields are `private`, accessed only through getters and setters.

**Inheritance** `Concert`, `Conference`, and `Birthday` all extend the abstract `Event` class. They share common fields (id, name, date, location) and each adds its own unique field.

**Polymorphism**  each subclass overrides the `display()` method differently. In `EventManager`, one loop calls `e.display()` and Java automatically picks the right version depending on the object type.


## File structure

```
EventPlanner/
├── Event.java           # abstract parent class
├── Concert.java         # extends Event, adds artist field
├── Conference.java      # extends Event, adds topic field
├── Birthday.java        # extends Event, adds birthdayPerson field
├── Participant.java     # stores name and email, has email validation
├── EventManager.java    # handles all logic: CRUD, file I/O, CSV
├── MainGUI.java         # Swing GUI — window, buttons, table
├── events.txt           # data file (auto-created on first run)
└── export.csv           # created when you export
```


## How to run

1. Make sure you have Java installed (version 17 or higher recommended)
2. Download or clone the repository
3. Open the project in IntelliJ IDEA or any Java IDE
4. Run `MainGUI.java`
5. The app window will open — you can start adding events right away


## How to use

When you open the app you'll see a table on the left and buttons on the right.

**Add Event** — choose the type, fill in the details, done
**Show All / Refresh** — updates the table with current data
**Edit Event** — enter the event ID and new details
**Delete Event** — enter the event ID to remove it
**Add Participant** — enter event ID, participant name and email
**Show Participants** — see who's added to an event
**Search** — type a keyword to find events by name
**Export CSV** — saves all events to `export.csv`
**Import CSV** — loads events from `export.csv`

## Data storage

Events are saved automatically to `events.txt` after every change. The format looks like this:

```
Concert,EVT-1,Rock Night,15.06.2025,Almaty,The Strokes
PARTICIPANT,Alex,alex@gmail.com
Birthday,EVT-2,Sara's Birthday,20.07.2025,Home,Sara
```


## Difficulties I faced

The trickiest part was the file saving/loading I had to make sure that commas inside event names wouldn't break the file. I fixed it by using `split(",", 6)` which limits how many parts the line gets split into.

Another thing was the GUI I had to add special `*AsString()` methods in `EventManager` because Swing can't display things printed to the console.


## Technologies

Java
Java Swing (GUI)
File I/O with `BufferedReader` and `PrintWriter`
CSV export/import

## Screenshots

<img width="1470" height="956" alt="Screenshot 2026-04-17 at 11 04 50" src="https://github.com/user-attachments/assets/409de3e0-49cf-4d64-a320-40851eb8d850" />
<img width="1470" height="956" alt="Screenshot 2026-04-17 at 11 09 13" src="https://github.com/user-attachments/assets/8ed847ee-5342-4e4b-ab3c-49e2412cfdd8" />
<img width="1470" height="956" alt="Screenshot 2026-04-17 at 11 10 52" src="https://github.com/user-attachments/assets/a8ceea82-4339-4ea3-ad90-03966e53f805" />
<img width="1470" height="956" alt="Screenshot 2026-04-17 at 11 08 27" src="https://github.com/user-attachments/assets/d96b2937-1837-4400-8d02-397a16698761" />
<img width="1470" height="956" alt="Screenshot 2026-04-17 at 11 10 49" src="https://github.com/user-attachments/assets/096fc5b1-a931-4d2f-ab13-e2d7a776ba7c" />
<img width="1470" height="956" alt="Screenshot 2026-04-17 at 11 07 13" src="https://github.com/user-attachments/assets/39a94afe-4993-4784-b6c4-b1ea37cb3692" />
<img width="1470" height="956" alt="Screenshot 2026-04-17 at 11 10 11" src="https://github.com/user-attachments/assets/329c08c0-f94a-4f23-9ed0-ca735a46c549" />
<img width="1470" height="956" alt="Screenshot 2026-04-17 at 11 02 26" src="https://github.com/user-attachments/assets/d9040c8e-2fca-4063-a1c3-7873e982dc97" />
<img width="1470" height="956" alt="Screenshot 2026-04-17 at 11 10 08" src="https://github.com/user-attachments/assets/24a62dd1-8041-41bb-af2e-c4f2e1a5dda0" />
<img width="1470" height="956" alt="Screenshot 2026-04-17 at 11 11 15" src="https://github.com/user-attachments/assets/59d844f5-bc54-45c6-95be-4a340bdf745b" />
<img width="1470" height="956" alt="Screenshot 2026-04-17 at 11 11 36" src="https://github.com/user-attachments/assets/de120ea1-b093-4f49-9efa-d60abef2500b" />
<img width="1470" height="956" alt="Screenshot 2026-04-17 at 11 12 30" src="https://github.com/user-attachments/assets/17ec55df-4e28-440d-9445-b8e0fad5d56e" />
<img width="1470" height="956" alt="Screenshot 2026-04-17 at 11 12 33" src="https://github.com/user-attachments/assets/53f24798-8026-488a-a6af-fb77810214f3" />
<img width="1470" height="956" alt="Screenshot 2026-04-17 at 11 09 10" src="https://github.com/user-attachments/assets/455f945b-82e8-478d-9c3f-7e578910788a" />



