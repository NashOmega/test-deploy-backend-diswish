package uit.ensak.dishwishbackend.dto;

import lombok.*;
import uit.ensak.dishwishbackend.model.Address;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDTO {

    private String firstName;

    private String lastName;

    private Address address;

    private String phoneNumber;

    private String allergies;

    private DietDTO dietDTO;
}
