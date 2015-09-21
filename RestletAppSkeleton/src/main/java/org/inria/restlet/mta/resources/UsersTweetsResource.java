package org.inria.restlet.mta.resources;

import java.util.ArrayList;
import java.util.Collection;
import org.json.JSONArray;

import org.inria.restlet.mta.backend.Backend;
import org.inria.restlet.mta.internals.User;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

/**
 *
 * Resource exposing a user.
 *
 * @author najeeb
 *
 */
public class UsersTweetsResource extends ServerResource
{

    /** Backend.*/
    private Backend backend_;

    /** Utilisateur géré par cette resource.*/
    private User user_;

    /**
     * Constructor.
     * Call for every single user request.
     */
    public UsersTweetsResource()
    {
        backend_ = (Backend) getApplication().getContext().getAttributes()
                .get("backend");
    }


    @Get("json")
    public Representation getTweets() throws Exception
    {
        Collection<User> users = backend_.getDatabase().getUsers();
        Collection<JSONObject> jsonUsers = new ArrayList<JSONObject>();
        
        for (User user : users)
        {
            JSONObject current = new JSONObject();
            current.put("Tweets for user", user.getName());
            current.put("These are my Tweets", user.getTweet());
            jsonUsers.add(current);

        }
        JSONArray jsonArray = new JSONArray(jsonUsers);
        return new JsonRepresentation(jsonArray);
    }

}
