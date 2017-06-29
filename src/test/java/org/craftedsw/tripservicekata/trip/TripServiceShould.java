package org.craftedsw.tripservicekata.trip;


import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Test;

public class TripServiceShould {

    @Test(expected = UserNotLoggedInException.class)
    public void throw_an_exception_if_user_is_not_logged() throws Exception {
        TripService tripService = new TripServiceTested();

        tripService.getTripsByUser(new User());
    }

    private class TripServiceTested extends TripService {
        @Override
        protected User getLoggedUser() {
            return null;
        }
    }
}
