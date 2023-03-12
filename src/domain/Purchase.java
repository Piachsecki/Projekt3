package domain;

import domain.Car;
import domain.Person;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Purchase {
    private long id;
    private Person person;
    private Car car;
    private Location location;
    private LocalDate date;

    public long getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

    public Location getLocation() {
        return location;
    }

    public LocalDate getDate() {
        return date;
    }

    public Car getCar() {
        return car;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", person=" + person +
                ", car=" + car +
                ", location=" + location +
                ", date=" + date +
                '}';
    }

    public Purchase(long id, Person person, Car car, Location location, LocalDate date) {
        this.id = id;
        this.person = person;
        this.car = car;
        this.location = location;
        this.date = date;
    }
}
