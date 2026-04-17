public class Birthday extends Event {

    private String birthdayPerson;

    public Birthday(String id, String name, String date, String location, String birthdayPerson) {
        super(id, name, date, location);
        this.birthdayPerson = birthdayPerson;
    }

    public String getBirthdayPerson() { return birthdayPerson; }
    public void setBirthdayPerson(String birthdayPerson) { this.birthdayPerson = birthdayPerson; }

    @Override
    public void display() {
        displayBasicInfo();
        System.out.println("Type: Birthday");
        System.out.println("Birthday person: " + birthdayPerson);
        System.out.println("----------------------------");
    }
}