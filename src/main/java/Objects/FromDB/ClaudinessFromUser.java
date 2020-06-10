package Objects.FromDB;

public class ClaudinessFromUser { //Klasa reprezentuje pomiar zachmurzenia od użytkownika
    private String date=null;
    private String userName=null;
    private String claudiness=null;
    private int id=0;

    public ClaudinessFromUser(String date, String userName, String claudiness,int id) {
        this.date = date;
        this.userName = userName;
        this.claudiness = claudiness;
        this.id=id;
    }

    public String getDate() {
        return date;
    }

    public String getUserName() {
        return userName;
    }

    public int getId() {
        return id;
    }

    public String getClaudiness() {
        return claudiness;
    }
}
