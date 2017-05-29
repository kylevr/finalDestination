package Classes;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Feedback implements Serializable {

    private Date timeCreated;
    private int rating;
    private String description;
    private String userFrom_Username;
    private String userTo_Username;

    /**
     * returns creation timestamp
     *
     * @return Date
     */
    public Date getTimeCreated() {
        return timeCreated;
    }

    /**
     * returns rating given
     *
     * @return int
     */
    public int getRating() {
        return rating;
    }

    /**
     * returns description of feedback
     *
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * returns username of who gave feedback
     *
     * @return string
     */
    public String getUserFrom_Username() {
        return userFrom_Username;
    }

    /**
     * returns username of who recieved feedback
     *
     * @return string
     */
    public String getUserTo_Username() {
        return userTo_Username;
    }

    /**
     * constructor used to created a new feedback object that never existed
     * before
     *
     * @param userFrom_Username
     * @param userTo_Username
     * @param rating
     * @param description
     */
    public Feedback(String userFrom_Username, String userTo_Username, int rating, String description) {
        timeCreated = new Date();
        this.userFrom_Username = userFrom_Username;
        this.userTo_Username = userTo_Username;
        this.rating = rating;
        this.description = description;
    }

    /**
     * constructor used to created a new feedback object that existed before in
     * the database.
     *
     * @param timeCreated
     * @param userFrom_Username
     * @param userTo_Username
     * @param rating
     * @param description
     */
    public Feedback(Date timeCreated, String userFrom_Username, String userTo_Username, int rating, String description) {
        this.timeCreated = timeCreated;
        this.userFrom_Username = userFrom_Username;
        this.userTo_Username = userTo_Username;
        this.rating = rating;
        this.description = description;
    }

    @Override
    /**
     * A more readable toString() to be used when in the
     * profilefeedbackcontroller
     *
     * @return a String of feedback used for a page where the feedbackowner is
     * already known
     */
    public String toString() {
        return "FROM:" + userFrom_Username + "@" + timeCreated + ", RATING:" + rating + ", DESCRIPTION:" + description;
        //return "timeCreated:" + timeCreated + ", rating:" + rating + ", description:" + description + ", userFrom_Username:" + userFrom_Username + ", userTo_Username:" + userTo_Username + '}';
    }
}
