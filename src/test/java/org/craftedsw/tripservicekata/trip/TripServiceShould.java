package org.craftedsw.tripservicekata.trip;


import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

public class TripServiceShould {

    public static final User GUEST = null;
    public static final User LOGGED_USER = new User();
    public static final User FRIEND = new User();
    private TripDAO tripDAO;

    @Before
    public void setUp() throws Exception {
        tripDAO = Mockito.mock(TripDAO.class);
    }

    @Test(expected = UserNotLoggedInException.class)
    public void throw_an_exception_if_user_is_not_logged() throws Exception {
        TripService tripService = new TripServiceTested(GUEST, null);

        tripService.getTripsByUser(new User());
    }

    @Test
    public void user_without_friends_can_not_retrive_trips() throws Exception {
        TripService tripService = new TripServiceTested(LOGGED_USER, null);
        List<Trip> trips = tripService.getTripsByUser(FRIEND);

        assertThat(trips.size(),is(0));
    }

    @Test
    public void user_can_get_friend_trips() throws Exception {

        TripService tripService = new TripServiceTested(LOGGED_USER, Arrays.asList(new Trip()));
        FRIEND.setFriends(Arrays.asList(LOGGED_USER));

        List<Trip> trips = tripService.getTripsByUser(FRIEND);

        assertThat(trips.size(),is(1));
    }

    private class TripServiceTested extends TripService {
        private User user;

        public TripServiceTested(User user, List<Trip> trips) {
            super(tripDAO);
            Mockito.when(tripDAO.findTrips(FRIEND)).thenReturn(trips);
            this.user = user;
        }

        @Override
        protected User getLoggedUser() {
            return user;
        }


    }
}
