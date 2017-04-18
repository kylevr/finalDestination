package Classes;

import java.util.Date;
import java.util.List;

public class Feedback {

    private Date timeCreated;
    private int rating;
    private String description;
    private List<User> userFrom;
    private List<User> userTo;
    
    
    public Feedback(User from, User to, int rating, String description)
    {
        this.userFrom.add(from);
        this.userTo.add(to);
        this.rating = rating;
        this.description = description;
    }

}
