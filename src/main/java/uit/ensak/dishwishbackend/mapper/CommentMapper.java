package uit.ensak.dishwishbackend.mapper;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import uit.ensak.dishwishbackend.dto.ClientDTO;
import uit.ensak.dishwishbackend.dto.CommentResponseDTO;
import uit.ensak.dishwishbackend.model.Client;
import uit.ensak.dishwishbackend.model.Comment;

@Component
@Transactional
@AllArgsConstructor
public class CommentMapper {
    private final ClientMapper clientMapper;

    public CommentResponseDTO mapToCommentDTO(Comment comment) {
        CommentResponseDTO commentDTO = new CommentResponseDTO();
        commentDTO.setContent(comment.getContent());

        Client sender = comment.getSender();
        ClientDTO senderDTO = clientMapper.fromClientToClientDto(sender);

        commentDTO.setClient(senderDTO);

        return commentDTO;
    }
}
