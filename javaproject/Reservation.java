public class Reservation {
    private String reservationId;
    private Flight flight;
    private User user;

    public Reservation(String reservationId, Flight flight, User user) {
        this.reservationId = reservationId;
        this.flight = flight;
        this.user = user;
    }

    public String getReservationId() {
        return reservationId;
    }

    public Flight getFlight() {
        return flight;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId + ", Flight: [" + flight.toString() + "], User: " + user.getUsername();
    }
}
