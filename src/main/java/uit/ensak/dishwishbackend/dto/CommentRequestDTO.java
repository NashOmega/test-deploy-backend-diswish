package uit.ensak.dishwishbackend.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequestDTO {
    private String content;
    private Long chefId;
    private Long clientId;
}
