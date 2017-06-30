package org.craftedsw.tripservicekata.trip;

import java.util.ArrayList;
import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

public class TripService {

	public static final ArrayList<Trip> EMPTY_TRIPS = new ArrayList<>();
	private TripDAO tripDao;
	private UserSession userSession;

	public TripService(TripDAO tripDao, UserSession userSession) {
		this.tripDao = tripDao;
		this.userSession = userSession;
	}

	public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
		User loggedUser = getLoggedUser();
		validate(loggedUser);

		if (user.areFriends(loggedUser)) {
			return getTripsOf(user);
		}
		return EMPTY_TRIPS;
	}

	private void validate(User loggedUser) throws UserNotLoggedInException {
		if (loggedUser == null) {
			throw new UserNotLoggedInException();
		}
	}

	protected List<Trip> getTripsOf(User user) {
		return tripDao.findTrips(user);
	}

	protected User getLoggedUser() {
		return userSession.getLoggedUser();
	}

}
