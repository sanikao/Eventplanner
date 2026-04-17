public class Concert extends Event {

    private String artist;

    public Concert(String id, String name, String date, String location, String artist) {
        super(id, name, date, location);
        this.artist = artist;
    }

    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }

    @Override
    public void display() {
        displayBasicInfo();
        System.out.println("Type: Concert");
        System.out.println("Artist: " + artist);
        System.out.println("----------------------------");
    }
}