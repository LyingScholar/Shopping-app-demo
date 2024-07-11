package com.ufund.api.ufundapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the REST API requests for the User resource
 * Test commit
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Team 2
 */

@RestController
@RequestMapping("Helper")
public class HelperController {
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());
    private Cupboard cupboard;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param UserDB The {@link UserDB User Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public HelperController(Cupboard cupboard) {
        this.cupboard = cupboard;
    }

    @GetMapping("")
    public ResponseEntity<Need[]> viewNeeds() {
        LOG.info("GET /Helper");
        try {
            Need[] cupboardNeeds = cupboard.getNeeds();
            if (cupboardNeeds != null)
                return new ResponseEntity<Need[]>(cupboardNeeds,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public ResponseEntity<Need[]> searchNeeds(@RequestParam(required=false) String name) {
        LOG.info("GET /Helper/?name="+name);
        
        try {
            Need[] needs = cupboard.findNeeds(name);
            if (needs != null)
                return new ResponseEntity<>(needs,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}