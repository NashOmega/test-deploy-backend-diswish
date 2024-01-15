package uit.ensak.dishwishbackend.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Command {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String serving;

    private String address;

    private String city;

    private String deadline;

    private String price;

    private String status;

    @CreationTimestamp(source = SourceType.DB)
    private Instant createdOn;

    @UpdateTimestamp(source = SourceType.DB)
    private Instant lastUpdatedOn;


    @ManyToOne
    @JoinColumn(name="client_id", nullable=false)
    @JsonIgnoreProperties("commands")
    private Client client;


    @ManyToOne
    @JoinColumn(name="chef_id")
    @JsonIgnoreProperties("commands")
    private Chef chef;
}