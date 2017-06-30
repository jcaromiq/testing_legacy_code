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
        Mockito.when(userSession.getLoggedUser()).thenReturn(GUEST);
        TripService tripService = new TripService(tripDAO,userSession);

        tripService.getTripsByUser(new User());
    }

    @Test
    public void user_without_friends_can_not_retrive_trips() throws Exception {
        TripService tripService = new TripService(tripDAO,userSession);
        Mockito.when(userSession.getLoggedUser()).thenReturn(LOGGED_USER);
        Mockito.when(tripDAO.findTrips(FRIEND)).thenReturn(Arrays.asList());

        List<Trip> trips = tripService.getTripsByUser(FRIEND);

        assertThat(trips.size(),is(0));
    }

    @Test
    public void user_can_get_friend_trips() throws Exception {
        TripService tripService = new TripService(tripDAO,userSession);
        FRIEND.setFriends(Arrays.asList(LOGGED_USER));
        Mockito.when(userSession.getLoggedUser()).thenReturn(LOGGED_USER);
        Mockito.when(tripDAO.findTrips(FRIEND)).thenReturn(Arrays.asList(new Trip()));

        List<Trip> trips = tripService.getTripsByUser(FRIEND);

        assertThat(trips.size(),is(1));
    }

}
