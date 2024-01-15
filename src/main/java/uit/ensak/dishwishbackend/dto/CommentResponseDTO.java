package uit.ensak.dishwishbackend.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponseDTO {
    private ClientDTO client;
    private String content;
}
