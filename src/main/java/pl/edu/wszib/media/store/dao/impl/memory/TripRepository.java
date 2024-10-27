package pl.edu.wszib.media.store.dao.impl.memory;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import pl.edu.wszib.media.store.dao.ITripDAO;
import pl.edu.wszib.media.store.model.Trip;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class TripRepository implements ITripDAO {
    List<Trip> trips = new ArrayList<>();
    private final IdSequence idSequence;

    public TripRepository(IdSequence idSequence) {
        this.idSequence = idSequence;
        // Dodawanie wycieczek bez zdjęć (photo = null)
        trips.add(new Trip((long) this.idSequence.getId(),
                "Wakacje w Hiszpanii",
                "Wakacyjna",
                "Hiszpania",
                "Ekscytująca 7-dniowa wycieczka po najpiękniejszych miastach Hiszpanii.",
                2999.99,
                20,
                null));

        trips.add(new Trip((long) this.idSequence.getId(),
                "Biznesowy wypad do Londynu",
                "Biznesowa",
                "Londyn",
                "4-dniowy wyjazd do Londynu z możliwością uczestnictwa w prestiżowych konferencjach.",
                4599.00,
                15,
                null));

        trips.add(new Trip((long) this.idSequence.getId(),
                "Egzotyczne wakacje na Bali",
                "Wakacyjna",
                "Bali",
                "10-dniowa wycieczka na wyspę Bali z zakwaterowaniem w luksusowych hotelach.",
                7499.50,
                10,
                null));

        try {
            trips.add(new Trip((long) this.idSequence.getId(),
                    "Przygoda w Alpach",
                    "Przygodowa",
                    "Alpy",
                    "7-dniowa wycieczka po Alpach z możliwością wspinaczki i zwiedzania.",
                    5999.90,
                    12,
                    Base64.getDecoder().decode(Files.readAllBytes(Paths.get("/photo_base64.txt"))))); // Load the photo
                                                                                                      // bytes
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, e.g., set photo to null or log the error
            trips.add(new Trip((long) this.idSequence.getId(),
                    "Przygoda w Alpach",
                    "Przygodowa",
                    "Alpy",
                    "7-dniowa wycieczka po Alpach z możliwością wspinaczki i zwiedzania.",
                    5999.90,
                    12,
                    null));
        }
    }

    @Override
    public Optional<Trip> getById(final Long id) {
        return this.trips.stream()
                .filter(trip -> trip.getId().equals(id))
                .findAny()
                .map(this::copy);
    }

    @Override
    public List<Trip> getAll() {
        return this.trips.stream().map(this::copy).toList();
    }

    @Override
    public List<Trip> getByPattern(final String pattern) {
        return this.trips.stream()
                .filter(trip -> trip.getTitle().toLowerCase().contains(pattern.toLowerCase()) ||
                        trip.getDirection().toLowerCase().contains(pattern.toLowerCase()))
                .map(this::copy)
                .toList();
    }

    @Override
    public void save(Trip trip) {
        trip.setId((long) this.idSequence.getId());
        this.trips.add(trip);
    }

    @Override
    public void update(final Trip trip) {
        this.trips.stream()
                .filter(b -> b.getId().equals(trip.getId()))
                .findAny()
                .ifPresent(b -> {
                    b.setTitle(trip.getTitle());
                    b.setDirection(trip.getDirection());
                    b.setType(trip.getType());
                    b.setPrice(trip.getPrice());
                    b.setQuantity(trip.getQuantity());
                    b.setPhoto(trip.getPhoto());
                    b.setDescription(trip.getDescription());

                    // if (trip.getPhoto() != null) {
                    // b.setPhoto(trip.getPhoto());
                    // }
                });
    }

    @Override
    public void remove(Long id) {
        this.trips.removeIf(trip -> trip.getId().equals(id));
    }

    private Trip copy(Trip trip) {
        Trip copy = new Trip();
        copy.setId(trip.getId());
        copy.setTitle(trip.getTitle());
        copy.setDirection(trip.getDirection());
        copy.setType(trip.getType());
        copy.setPrice(trip.getPrice());
        copy.setQuantity(trip.getQuantity());
        copy.setDescription(trip.getDescription());
        copy.setPhoto(trip.getPhoto()); // Kopiowanie zdjęcia
        return copy;
    }
}
