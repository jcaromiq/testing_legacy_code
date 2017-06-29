package org.craftedsw.tripservicekata.trip;


import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

public class TripServiceShould {

    @Test(expected = UserNotLoggedInException.class)
    public void throw_an_exception_if_user_is_not_logged() throws Exception {
        TripService tripService = new TripServiceTested(null);

        tripService.getTripsByUser(new User());
    }

    @Test
    public void user_without_friends_can_not_retrive_trips() throws Exception {
        TripService tripService = new TripServiceTested(new User());
        List<Trip> trips = tripService.getTripsByUser(new User());

        assertThat(trips.size(),is(0));
    }

    private class TripServiceTested extends TripService {
        private User user;

        public TripServiceTested(User user) {
            this.user = user;
        }

        @Override
        protected User getLoggedUser() {
            return user;
        }
    }
}
