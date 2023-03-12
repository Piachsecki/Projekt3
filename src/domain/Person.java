package domain;

public class Person {
    private String firstName;
    private String lastName;
    private String email;
    private String ipAddress;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public Person(String firstName, String lastName, String email, String ipAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                '}';
    }
}
