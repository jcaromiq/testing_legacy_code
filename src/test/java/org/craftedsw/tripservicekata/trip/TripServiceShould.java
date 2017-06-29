package org.craftedsw.tripservicekata.trip;


import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

public class TripServiceShould {

    @Test(expected = UserNotLoggedInException.class)
    public void throw_an_exception_if_user_is_not_logged() throws Exception {
        TripService tripService = new TripServiceTested(null, null);

        tripService.getTripsByUser(new User());
    }

    @Test
    public void user_without_friends_can_not_retrive_trips() throws Exception {
        TripService tripService = new TripServiceTested(new User(), null);
        List<Trip> trips = tripService.getTripsByUser(new User());

        assertThat(trips.size(),is(0));
    }

    @Test
    public void user_can_get_friend_trips() throws Exception {

        User me = new User();
        TripService tripService = new TripServiceTested(me, Arrays.asList(new Trip()));
        User friend = new User();

        friend.setFriends(Arrays.asList(me));
        List<Trip> trips = tripService.getTripsByUser(friend);

        assertThat(trips.size(),is(1));
    }

    private class TripServiceTested extends TripService {
        private User user;
        private List<Trip> trips;

        public TripServiceTested(User user, List<Trip> trips) {
            this.user = user;
            this.trips = trips;
        }

        @Override
        protected User getLoggedUser() {
            return user;
        }

        @Override
        protected List<Trip> getTripsBy(User user) {
            return trips;
        }
    }
}
