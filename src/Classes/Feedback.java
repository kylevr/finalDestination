package Classes;

import java.util.Date;
import java.util.List;

public class Feedback {

    private Date timeCreated;
    private int rating;
    private String description;
    private String userFrom_Username;
    private String userTo_Username;

    public Date getTimeCreated() {
        return timeCreated;
    }

    public int getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public String getUserFrom_Username() {
        return userFrom_Username;
    }

    public String getUserTo_Username() {
        return userTo_Username;
    }
    
    /**
     * constructor used to created a new feedback object that never existed before 
     * @param userFrom_Username
     * @param userTo_Username
     * @param rating
     * @param description 
     */
    public Feedback(String userFrom_Username, String userTo_Username, int rating, String description)
    {
        timeCreated = new Date();
        this.userFrom_Username = userFrom_Username;
        this.userTo_Username = userTo_Username;
        this.rating = rating;
        this.description = description;
    }

    /**
     * constructor used to created a new feedback object that existed before in the database.
     * @param timeCreated
     * @param userFrom_Username
     * @param userTo_Username
     * @param rating
     * @param description 
     */
    public Feedback(Date timeCreated, String userFrom_Username, String userTo_Username, int rating, String description)
    {
        this.timeCreated = timeCreated;
        this.userFrom_Username = userFrom_Username;
        this.userTo_Username = userTo_Username;
        this.rating = rating;
        this.description = description;
    }

    @Override
    public String toString() {
        return "timeCreated:" + timeCreated + ", rating:" + rating + ", description:" + description + ", userFrom_Username:" + userFrom_Username + ", userTo_Username:" + userTo_Username + '}';
    }
}
