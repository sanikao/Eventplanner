public class Conference extends Event {

    private String topic;

    public Conference(String id, String name, String date, String location, String topic) {
        super(id, name, date, location);
        this.topic = topic;
    }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    @Override
    public void display() {
        displayBasicInfo();
        System.out.println("Type: Conference");
        System.out.println("Topic: " + topic);
        System.out.println("----------------------------");
    }
}