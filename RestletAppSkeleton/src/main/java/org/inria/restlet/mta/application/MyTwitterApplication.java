package org.inria.restlet.mta.application;


import org.inria.restlet.mta.resources.UserResource;
import org.inria.restlet.mta.resources.UsersResource;
import org.inria.restlet.mta.resources.UsersResource;
import org.inria.restlet.mta.resources.UsersTweetsResource;
import org.inria.restlet.mta.resources.UserTweetsResource;
import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;
import java.util.logging.*;


/**
 *
 * Application.
 *
 * @author msimonin
 *
 */
public class MyTwitterApplication extends Application
{

    public MyTwitterApplication(Context context)
    {
        super(context);
    }

    @Override
    public Restlet createInboundRoot()
    {
        Logger logger = Logger.getAnonymousLogger();
        // LOG this level to the log
        logger.setLevel(Level.FINER);

        ConsoleHandler handler = new ConsoleHandler();
        // PUBLISH this level
        handler.setLevel(Level.FINER);
        logger.addHandler(handler);
        
        Router router = new Router(getContext());
        router.attach("/users/tweets", UsersTweetsResource.class);   // url to handle get request all tweets for all users 
        router.attach("/users", UsersResource.class);
        router.attach("/users/{userId}", UserResource.class);        // url to handle get and post request of create user and get user
        router.attach("/users/{userId}/tweets", UserTweetsResource.class);    // url to handle post and get request for tweets 
      
        return router;
    }
}
