package uit.ensak.dishwishbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uit.ensak.dishwishbackend.dto.CommentRequestDTO;
import uit.ensak.dishwishbackend.dto.CommentResponseDTO;
import uit.ensak.dishwishbackend.dto.RatingDTO;
import uit.ensak.dishwishbackend.exception.ClientNotFoundException;
import uit.ensak.dishwishbackend.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{chefId}")
    public ResponseEntity<List<CommentResponseDTO>> getChefComments(@PathVariable Long chefId) throws ClientNotFoundException {
        List<CommentResponseDTO> comments = commentService.getChefComments(chefId);

        return ResponseEntity.ok(comments);
    }

    @PostMapping("")
    public ResponseEntity<CommentResponseDTO> addCommentToChef(@RequestBody CommentRequestDTO commentDetails) throws ClientNotFoundException {

        return ResponseEntity.ok(commentService.addCommentToChef(commentDetails));
    }
}
