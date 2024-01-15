package uit.ensak.dishwishbackend.service;

import org.springframework.stereotype.Service;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final ClientRepository clientRepository;
    private final ChefRepository chefRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public CommentService(ClientRepository clientRepository,
                          ChefRepository chefRepository,
                          CommentRepository commentRepository,
                          CommentMapper commentMapper) {
        this.clientRepository = clientRepository;
        this.chefRepository = chefRepository;
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    public CommentResponseDTO addCommentToChef(CommentRequestDTO commentDetails) throws ClientNotFoundException {
        Client client = clientRepository.findById(commentDetails.getClientId())
                .orElseThrow(() -> new ClientNotFoundException("User by Id " + commentDetails.getClientId() + " could not be found."));
        Chef chef = chefRepository.findById(commentDetails.getChefId())
                .orElseThrow(() -> new ClientNotFoundException("Chef by Id " + commentDetails.getChefId() + " could not be found."));
        Comment commentCreated = Comment.builder().sender(client).
                                                    receiver(chef).
                                                    content(commentDetails.getContent()).
                                                    build();

        return commentMapper.mapToCommentDTO(commentRepository.save(commentCreated));
    }

    public List<CommentResponseDTO> getChefComments(Long chefId) throws ClientNotFoundException {
        Chef chef = chefRepository.findById(chefId)
                .orElseThrow(() -> new ClientNotFoundException("Cook by Id " + chefId + " could not be found."));
        List<Comment> comments = commentRepository.findByReceiverId(chefId);

        return comments.stream()
                .map(commentMapper::mapToCommentDTO)
                .collect(Collectors.toList());
    }
}
