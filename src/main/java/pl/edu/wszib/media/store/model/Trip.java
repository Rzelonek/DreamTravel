package pl.edu.wszib.media.store.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "ttrip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String type;
    private String direction;
    private String description;
    private double price;
    private int quantity;

    @Lob
    private byte[] photo;

    public Trip(Long id) {
        this.id = id;
    }
}
