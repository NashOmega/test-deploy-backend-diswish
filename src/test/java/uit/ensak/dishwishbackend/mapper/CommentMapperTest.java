package uit.ensak.dishwishbackend.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uit.ensak.dishwishbackend.dto.ClientDTO;
import uit.ensak.dishwishbackend.dto.CommentResponseDTO;
import uit.ensak.dishwishbackend.model.Address;
import uit.ensak.dishwishbackend.model.Client;
import uit.ensak.dishwishbackend.model.Comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentMapperTest {
    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    CommentMapper commentMapper;

    @Test
    void mapToCommentDTO_ValidComment_ReturnsCommentResponseDTO() {
        Comment comment = new Comment();
        comment.setContent("Test content");
        Address address = mock(Address.class);

        Client sender = new Client();
        sender.setFirstName("John");
        sender.setLastName("Doe");

        comment.setSender(sender);

        when(clientMapper.fromClientToClientDto(sender)).thenReturn(new ClientDTO("John", "Doe", address, "81818118", "allergies", null));

        CommentResponseDTO commentDTO = commentMapper.mapToCommentDTO(comment);

        assertEquals("Test content", commentDTO.getContent());
        assertEquals("John", commentDTO.getClient().getFirstName());
        assertEquals("Doe", commentDTO.getClient().getLastName());
    }
}
