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

import com.ufund.api.ufundapi.Need;
import com.ufund.api.ufundapi.Cupboard;

/**
 * Handles the REST API requests for the Need resource
 * Test commit
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author SWEN Faculty
 */

@RestController
@RequestMapping("Needs")
public class NeedController {
    private static final Logger LOG = Logger.getLogger(NeedController.class.getName());
    private Cupboard Cupboard;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param Cupboard The {@link Cupboard Need Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public NeedController(Cupboard Cupboard) {
        this.Cupboard = Cupboard;
    }

    /**
     * Responds to the GET request for a {@linkplain Need Need} for the given id
     * 
     * @param id The id used to locate the {@link Need Need}
     * 
     * @return ResponseEntity with {@link Need Need} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Need> getNeed(@PathVariable int id) {
        LOG.info("GET /Needs/" + id);
        try {
            Need Need = Cupboard.getNeed(id);
            if (Need != null)
                return new ResponseEntity<Need>(Need,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } 
        catch(Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Need Needes}
     * 
     * @return ResponseEntity with array of {@link Need Need} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Need[]> getNeeds() {
        LOG.info("GET /Needs");

        try {
            Need[] Needs = Cupboard.getNeeds();
            if (Needs != null)
                return new ResponseEntity<>(Needs,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Need Needes} whose name contains
     * the text in name
     * 
     * @param name The name parameter which contains the text used to find the {@link Need Needes}
     * 
     * @return ResponseEntity with array of {@link Need Need} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * Example: Find all Needes that contain the text "ma"
     * GET http://localhost:8080/Needes/?name=ma
     */
    @GetMapping("/")
    public ResponseEntity<Need[]> searchNeeds(@RequestParam(required=false) String name) {
        LOG.info("GET /Needs/?name="+name);
        
        try {
            Need[] Needs = Cupboard.findNeeds(name);
            if (Needs != null)
                return new ResponseEntity<>(Needs,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Need Need} with the provided Need object
     * 
     * @param Need - The {@link Need Need} to create
     * 
     * @return ResponseEntity with created {@link Need Need} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Need Need} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Need> createNeed(@RequestParam() String name, @RequestParam() String type, 
                                            @RequestParam() int quantity, @RequestParam() int cost) {
        LOG.info("POST /Needs/" + name + "/" + quantity + "/" + cost + "/" + type);
        
        try {
            Need initialNeed = new Need(1, name, quantity, cost, type);
            Need newNeed = Cupboard.createNeed(initialNeed);
            if (newNeed  != null) {
                return new ResponseEntity<>(newNeed,HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    /**
     * Updates the {@linkplain Need Need} with the provided {@linkplain Need Need} object, if it exists
     * 
     * @param Need The {@link Need Need} to update
     * 
     * @return ResponseEntity with updated {@link Need Need} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Need> updateNeed(@RequestParam() int id, @RequestParam() String name, @RequestParam() String type, 
                                            @RequestParam() int quantity, @RequestParam() int cost) {
        LOG.info("PUT /Needs/" + id + "/" + name + "/" + quantity + "/" + cost + "/" + type);

        try {
            Need inputNeed = new Need(id, name, quantity, cost, type);
            Need updatedNeed = Cupboard.updateNeed(inputNeed);
            if (updatedNeed == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(updatedNeed, HttpStatus.OK);
        } catch (Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Need Need} with the given id
     * 
     * @param id The id of the {@link Need Need} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTtest status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Need> deleteNeed(@PathVariable int id) {
        LOG.info("DELETE /Needs/" + id);

        try {
            if (Cupboard.deleteNeed(id)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
