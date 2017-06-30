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
    public static final User A_USER = new User();
    public static final List<Trip> NO_TRIPS = Arrays.asList();
    public static final Trip TRIP = new Trip();
    private TripDAO tripDAO;
    private UserSession userSession;
    private TripService tripService;


    @Before
    public void setUp() throws Exception {
        tripDAO = Mockito.mock(TripDAO.class);
        userSession = Mockito.mock(UserSession.class);
        tripService = new TripService(tripDAO,userSession);
    }

    @Test(expected = UserNotLoggedInException.class)
    public void throw_an_exception_if_user_is_not_logged() throws Exception {
        given_a_user(GUEST);

        getTripsOf(A_USER);
    }


    @Test
    public void user_without_friends_can_not_retrive_trips() throws Exception {
        given_a_user(LOGGED_USER);
        Mockito.when(tripDAO.findTrips(FRIEND)).thenReturn(NO_TRIPS);

        assertThat(getTripsOf(FRIEND).size(),is(0));
    }

    @Test
    public void user_can_get_friend_trips() throws Exception {
        FRIEND.setFriends(Arrays.asList(LOGGED_USER));
        given_a_user(LOGGED_USER);
        Mockito.when(tripDAO.findTrips(FRIEND)).thenReturn(Arrays.asList(TRIP));

        assertThat(getTripsOf(FRIEND).size(),is(1));
    }

    private void given_a_user(User guest) {
        Mockito.when(userSession.getLoggedUser()).thenReturn(guest);
    }

    private List<Trip> getTripsOf(User aUser) throws UserNotLoggedInException {
        return tripService.getTripsByUser(aUser);
    }

}
