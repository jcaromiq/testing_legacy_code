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


    private static final User GUEST = null;
    private static final User LOGGED = new User();

    private static final User USER_FRIEND_OF_LOOGED = new User();
    private static final User A_USER = new User();
    private static final User USER_WITHOUT_FRIENDS = new User();

    private static final Trip TRIP = new Trip();


    private TripDAO tripDAO;
    private UserSession userSession;
    private TripService tripService;

    static {
        USER_FRIEND_OF_LOOGED.setFriends(Arrays.asList(LOGGED));
    }

    @Before
    public void setUp() throws Exception {
        tripDAO = Mockito.mock(TripDAO.class);
        userSession = Mockito.mock(UserSession.class);
        tripService = new TripService(tripDAO,userSession);
    }

    @Test(expected = UserNotLoggedInException.class)
    public void throw_an_exception_if_user_is_not_logged() throws Exception {
        given_a_user(GUEST);

        when_retrieve_trips_of(A_USER);
    }


    @Test
    public void user_without_friends_can_not_retrieve_trips() throws Exception {
        given_a_user(LOGGED);

        List<Trip> trips = when_retrieve_trips_of(USER_WITHOUT_FRIENDS);

        verify_then(trips, 0);
    }

    @Test
    public void user_can_get_friend_trips() throws Exception {
        given_a_user_logged_and_friend_with_trips();

        List<Trip> trips = when_retrieve_trips_of(USER_FRIEND_OF_LOOGED);

        verify_then(trips, 1);
    }

    private void verify_then(List<Trip> trips, int value) {
        assertThat(trips.size(),is(value));
    }

    private void given_a_user_logged_and_friend_with_trips() {
        Mockito.when(userSession.getLoggedUser()).thenReturn(LOGGED);
        Mockito.when(tripDAO.findTrips(USER_FRIEND_OF_LOOGED)).thenReturn(Arrays.asList(TRIP));
    }

    private void given_a_user(User user) {
        Mockito.when(userSession.getLoggedUser()).thenReturn(user);
    }

    private List<Trip> when_retrieve_trips_of(User user) throws UserNotLoggedInException {
        return tripService.getTripsByUser(user);
    }

}
