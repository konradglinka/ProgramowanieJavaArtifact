package MeasuresFromUsers.TypeOfMeasure;

public class ClaudinessFromUser { //Klasa reprezentuje pomiar zachmurzenia od użytkownika
    private String date=null;
    private String userName=null;
    private String claudiness=null;
    private String city=null;

    public ClaudinessFromUser(String date, String userName, String claudiness, String city) {
        this.date = date;
        this.userName = userName;
        this.claudiness = claudiness;
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public String getUserName() {
        return userName;
    }

    public String getCity() {
        return city;
    }

    public String getClaudiness() {
        return claudiness;
    }
}
