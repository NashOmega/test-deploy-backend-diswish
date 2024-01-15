package uit.ensak.dishwishbackend.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uit.ensak.dishwishbackend.controller.CommentController;
import uit.ensak.dishwishbackend.dto.CommentRequestDTO;
import uit.ensak.dishwishbackend.dto.CommentResponseDTO;
import uit.ensak.dishwishbackend.exception.ClientNotFoundException;
import uit.ensak.dishwishbackend.service.CommentService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    @Test
    void testGetChefComments() throws ClientNotFoundException {
        Long chefId = 1L;
        List<CommentResponseDTO> expectedComments = Arrays.asList(new CommentResponseDTO(), new CommentResponseDTO());

        when(commentService.getChefComments(chefId)).thenReturn(expectedComments);


        ResponseEntity<List<CommentResponseDTO>> responseEntity = commentController.getChefComments(chefId);


        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedComments, responseEntity.getBody());
        verify(commentService, times(1)).getChefComments(chefId);
    }

    @Test
    void testAddCommentToChef() throws ClientNotFoundException {
        CommentRequestDTO commentDetails = new CommentRequestDTO();
        CommentResponseDTO expectedCommentResponse = new CommentResponseDTO();

        when(commentService.addCommentToChef(commentDetails)).thenReturn(expectedCommentResponse);

        ResponseEntity<CommentResponseDTO> responseEntity = commentController.addCommentToChef(commentDetails);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedCommentResponse, responseEntity.getBody());
        verify(commentService, times(1)).addCommentToChef(commentDetails);
    }
}
