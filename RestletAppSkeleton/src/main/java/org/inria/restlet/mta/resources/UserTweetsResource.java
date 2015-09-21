package org.inria.restlet.mta.resources;

import java.util.ArrayList;
import java.util.Collection;

import org.inria.restlet.mta.backend.Backend;
import org.inria.restlet.mta.internals.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;


/**
 * Resource exposing the users
 *
 * @author najeeb
 * 
 *
 */
public class UserTweetsResource extends ServerResource
{

    /** Backend. */
    private Backend backend_;

    /**
     * Constructor.
     * Call for every single user request.
     */
    public UserTweetsResource()
    {
        super();
        backend_ = (Backend) getApplication().getContext().getAttributes()
            .get("backend");
    }

    /**
     *
     * Returns the list of all the users
     *
     * @return  JSON representation of the users
     * @throws JSONException
     **/

    // Method to handle HTTP Get request to return tweets of user of given ID 
    @Get("json")
    public Representation getTweets() throws Exception
    {
        String userIdString = (String) getRequest().getAttributes().get("userId");
        int userId = Integer.valueOf(userIdString);
        User user_ = backend_.getDatabase().getUser(userId);

        JSONObject userObject = new JSONObject();
        userObject.put("id", user_.getId());
        userObject.put("name", user_.getName());
        userObject.put("These are my Tweets", user_.getTweet());
              

        return new JsonRepresentation(userObject);
    }

    // Method to handle post request to create tweets for user of given id 
    @Post("json")
    public Representation createTweets(JsonRepresentation representation)
        throws Exception
    {
        JSONObject object = representation.getJsonObject();
        int id = object.getInt("id");
        String tweet = object.getString("tweet");
        // Save tweet for that user id 
        User user = backend_.getDatabase().createTweet(id, tweet);

        // generate result
        JSONObject resultObject = new JSONObject();
                
        ArrayList<String> tweets = user.getTweet();
        int total_tweets = tweets.size();

        resultObject.put("Tweet message is :", tweets.get(total_tweets-1));
        resultObject.put("Total Tweet are : ", user.getName());
        resultObject.put("Tweet is created by user : ", total_tweets);
        JsonRepresentation result = new JsonRepresentation(resultObject);
        return result;
    }

}
