package com.ufund.api.ufundapi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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

/**
 * Handles the REST API requests for the Helper resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * </p>
 * <p>
 * {@literal @}RequestMapping maps HTTP requests to handler methods of MVC and REST controllers
 * </p>
 * 
 * @autor Team 2
 */
@RestController
@RequestMapping("Helper")
public class HelperController {
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());
    private Cupboard cupboard;
    private UserDB userDB;

    /**
     * Creates a Helper Controller to respond to requests
     * 
     * @param cupboard The {@link Cupboard} to manage needs
     * @param userDB The {@link UserDB} User Data Access Object to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public HelperController(Cupboard cupboard, UserDB userDB) {
        this.cupboard = cupboard;
        this.userDB = userDB;
    }

    /**
     * Handles the GET request to view all needs.
     * 
     * @return a {@link ResponseEntity} containing an array of {@link Need} objects and HTTP status
     */
    @GetMapping("/needs")
    public ResponseEntity<Need[]> viewNeeds() {
        LOG.info("GET /Helper/needs");
        try {
            Need[] cupboardNeeds = cupboard.getNeeds();
            if (cupboardNeeds != null)
                return new ResponseEntity<>(cupboardNeeds, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the GET request to search for needs by name.
     * 
     * @param name the name to search for
     * @return a {@link ResponseEntity} containing an array of {@link Need} objects and HTTP status
     */
    @GetMapping("/needs/search/")
    public ResponseEntity<Need[]> searchNeeds(@RequestParam(required = false) String name) {
        LOG.log(Level.INFO, "GET /Helper/needs/search/?name={0}", name);
        
        try {
            Need[] needs = cupboard.findNeeds(name);
            if (needs != null)
                return new ResponseEntity<>(needs, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the POST request to create a new Helper.
     * 
     * @param helper the {@link Helper} object to create
     * @return a {@link ResponseEntity} containing the created {@link Helper} object and HTTP status
     */
    @PostMapping("")
    public ResponseEntity<User> createHelper(@RequestBody Helper helper) {
        LOG.log(Level.INFO, "POST /Helper {0}", helper);
        
        try {
            Helper newHelper = userDB.createHelper(helper);
            if (newHelper != null) {
                return new ResponseEntity<>(newHelper, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the GET request to view the funding basket of a Helper by user ID.
     * 
     * @param userId the ID of the user
     * @return a {@link ResponseEntity} containing an ArrayList of {@link Need} objects and HTTP status
     */
    @GetMapping("/fundingBasket/")
    public ResponseEntity<ArrayList<Need>> viewFundingBasket(@RequestParam(required = true) int userId) {
        LOG.log(Level.INFO, "GET /Helper/fundingBasket/?userId={0}", userId);
        
        try {
            Helper helper = userDB.getHelper(userId);
            if (helper == null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else if (!(helper instanceof Helper)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            } else {
                ArrayList<Need> fundingBasket = helper.getFundingBasket();
                if (fundingBasket != null)
                    return new ResponseEntity<>(fundingBasket, HttpStatus.OK);
                else
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the PUT request to add a need to the funding basket of a Helper by user ID and need ID.
     * 
     * @param userId the ID of the user
     * @param needId the ID of the need
     * @return a {@link ResponseEntity} containing the {@link Need} object and HTTP status
     */
    @PutMapping("/fundingBasket/{userId}/{needId}")
    public ResponseEntity<Need> addNeed(@PathVariable(required = true) int userId, @PathVariable(required = true) int needId) {
        LOG.log(Level.INFO, "PUT /Helper/fundingBasket/{0}/{1}", new Object[]{userId, needId});

        try {
            User user = userDB.getUser(userId);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else if (!(user instanceof Helper)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            } else {
                Helper helper = (Helper) user;
                Need need = cupboard.getNeed(needId);
                if (need != null) {
                    helper.addNeed(need);
                    cupboard.removeNeed(need);
                    return new ResponseEntity<>(need, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }  
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the DELETE request to remove a need from the funding basket of a Helper by user ID and need ID.
     * 
     * @param userId the ID of the user
     * @param needId the ID of the need
     * @return a {@link ResponseEntity} containing the HTTP status
     */
    @DeleteMapping("fundingBasket/delete/{userId}/{needId}")
    public ResponseEntity<Need> removeNeed(@PathVariable(required = true) int userId, @PathVariable(required = true) int needId) {
        LOG.log(Level.INFO, "DELETE /Helper/fundingBasket/delete/{0}/{1}", new Object[]{userId, needId});

        try {
            User user = userDB.getUser(userId);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else if (!(user instanceof Helper)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            } else {
                Helper helper = (Helper) user;
                Need need = helper.getNeed(needId);
                cupboard.addNeed(need);
                if (need != null) {
                    helper.removeNeed(need);
                    return new ResponseEntity<>(HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                } 
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the PUT request to clear the funding basket of a Helper by user ID.
     * 
     * @param userId the ID of the user
     * @return a {@link ResponseEntity} containing the HTTP status
     */
    @PutMapping("/checkout/{userId}")
    public ResponseEntity<Need> checkout(@PathVariable(required = true) int userId) {
        LOG.log(Level.INFO, "PUT /Helper/checkout/{0}", userId);

        try {
            User user = userDB.getUser(userId);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else if (!(user instanceof Helper)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            } else {
                Helper helper = (Helper) user;
                helper.checkout();
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
