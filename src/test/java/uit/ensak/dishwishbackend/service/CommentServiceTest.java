package uit.ensak.dishwishbackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import uit.ensak.dishwishbackend.dto.CommentRequestDTO;
import uit.ensak.dishwishbackend.dto.CommentResponseDTO;
import uit.ensak.dishwishbackend.exception.ClientNotFoundException;
import uit.ensak.dishwishbackend.mapper.CommentMapper;
import uit.ensak.dishwishbackend.model.Chef;
import uit.ensak.dishwishbackend.model.Client;
import uit.ensak.dishwishbackend.model.Comment;
import uit.ensak.dishwishbackend.repository.ChefRepository;
import uit.ensak.dishwishbackend.repository.ClientRepository;
import uit.ensak.dishwishbackend.repository.CommentRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ChefRepository chefRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentService commentService;

    @Test
    void addCommentToChef_shouldAddCommentAndReturnMappedDTO() throws ClientNotFoundException {
        CommentRequestDTO commentRequestDTO = new CommentRequestDTO("Great dish!", 2L, 1L);

        Client client = new Client();
        Chef chef = new Chef();
        Comment comment = Comment.builder().sender(client).receiver(chef).content("Great dish!").build();
        CommentResponseDTO expectedResponseDTO = new CommentResponseDTO();

        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
        when(chefRepository.findById(anyLong())).thenReturn(Optional.of(chef));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(commentMapper.mapToCommentDTO(any(Comment.class))).thenReturn(expectedResponseDTO);

        CommentResponseDTO result = commentService.addCommentToChef(commentRequestDTO);

        assertEquals(expectedResponseDTO, result);
        verify(clientRepository, times(1)).findById(anyLong());
        verify(chefRepository, times(1)).findById(anyLong());
        verify(commentRepository, times(1)).save(any(Comment.class));
        verify(commentMapper, times(1)).mapToCommentDTO(any(Comment.class));
    }

    @Test
    void getChefComments_shouldReturnListOfCommentDTOs() throws ClientNotFoundException {
        Long chefId = 1L;
        Chef chef = new Chef();
        Comment comment = Comment.builder().sender(new Client()).receiver(chef).content("Amazing dish!").build();
        List<Comment> comments = Collections.singletonList(comment);
        CommentResponseDTO commentResponseDTO = new CommentResponseDTO();

        when(chefRepository.findById(anyLong())).thenReturn(Optional.of(chef));
        when(commentRepository.findByReceiverId(anyLong())).thenReturn(comments);
        when(commentMapper.mapToCommentDTO(any(Comment.class))).thenReturn(commentResponseDTO);

        List<CommentResponseDTO> result = commentService.getChefComments(chefId);

        assertEquals(1, result.size());
        assertEquals(commentResponseDTO, result.get(0));
        verify(chefRepository, times(1)).findById(anyLong());
        verify(commentRepository, times(1)).findByReceiverId(anyLong());
        verify(commentMapper, times(1)).mapToCommentDTO(any(Comment.class));
    }
}
