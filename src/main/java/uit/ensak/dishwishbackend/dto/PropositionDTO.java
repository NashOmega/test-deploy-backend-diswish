package uit.ensak.dishwishbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import uit.ensak.dishwishbackend.model.Chef;

@Getter
@Setter
public class PropositionDTO {
    @JsonProperty("lastChefProposition")
    private float lastChefProposition;

    @JsonProperty("lastClientProposition")
    private float lastClientProposition;

}

