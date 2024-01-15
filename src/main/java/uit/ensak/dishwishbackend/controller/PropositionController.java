package uit.ensak.dishwishbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uit.ensak.dishwishbackend.dto.CommandDTO;
import uit.ensak.dishwishbackend.dto.PropositionDTO;
import uit.ensak.dishwishbackend.exception.ClientNotFoundException;
import uit.ensak.dishwishbackend.exception.CommandNotFoundException;
import uit.ensak.dishwishbackend.model.Command;
import uit.ensak.dishwishbackend.model.Proposition;
import uit.ensak.dishwishbackend.service.PropositionService;

import java.util.List;


@RestController
@RequestMapping("/propositions")
public class PropositionController {

    private final PropositionService propositionService;

    public PropositionController(PropositionService propositionService) {
        this.propositionService = propositionService;
    }

    @GetMapping
    public ResponseEntity<List<Proposition>> getAllPropositions() {
        List<Proposition> Propositions = propositionService.getAllPropositions();
        return new ResponseEntity<>(Propositions, HttpStatus.OK);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<List<Proposition>> getPropositionsByClientId(@PathVariable Long clientId) {
        List<Proposition> propositionsByClientId = propositionService.getPropositionsByClientId(clientId);
        return new ResponseEntity<>(propositionsByClientId, HttpStatus.OK);
    }

    @PostMapping("/offer")
    public ResponseEntity<Proposition> offerPriceForOrder(@RequestBody Proposition proposition ) throws ClientNotFoundException, CommandNotFoundException {

        Proposition createdProposition = propositionService.createProposition(proposition);
        if (createdProposition != null) {
            return new ResponseEntity<>(createdProposition, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{propositionId}")
    public ResponseEntity<String> deleteProposition(@PathVariable Long propositionId) {
        boolean deleted = propositionService.deleteProposition(propositionId);
        if (deleted) {
            return new ResponseEntity<>("Offer deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to delete offer", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{propositionId}")
    public ResponseEntity<?> updateProposition(@PathVariable Long propositionId, @RequestBody PropositionDTO propositionDTO) {
        Proposition updatedProposition = propositionService.updateProposition(propositionId, propositionDTO);
        if (updatedProposition != null) {
            return new ResponseEntity<>(updatedProposition,HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Command not found",HttpStatus.BAD_REQUEST);
        }
    }

}
