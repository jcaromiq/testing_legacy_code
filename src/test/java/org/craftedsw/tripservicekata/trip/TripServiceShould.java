package org.craftedsw.tripservicekata.trip;


import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;
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
    private UserSession userSession;


    @Before
    public void setUp() throws Exception {
        tripDAO = Mockito.mock(TripDAO.class);
        userSession = Mockito.mock(UserSession.class);
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

        public TripServiceTested(User user, List<Trip> trips) {
            super(tripDAO, userSession);
            Mockito.when(userSession.getLoggedUser()).thenReturn(user);
            Mockito.when(tripDAO.findTrips(FRIEND)).thenReturn(trips);

        }




    }
}
