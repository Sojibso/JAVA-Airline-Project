public class Flight {
    private String flightNumber;
    private String origin;
    private String destination;
    private String date;
    private int availableSeats;

    public Flight(String flightNumber, String origin, String destination, String date, int availableSeats) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.availableSeats = availableSeats;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getDate() {
        return date;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    @Override
    public String toString() {
        return "Flight Number: " + flightNumber + ", Origin: " + origin + ", Destination: " + destination + ", Date: " + date + ", Available Seats: " + availableSeats;
    }
}
