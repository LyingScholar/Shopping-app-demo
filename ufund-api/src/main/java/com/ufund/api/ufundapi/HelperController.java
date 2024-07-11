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
import java.util.ArrayList;
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
    private UserDB userDB;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param UserDB The {@link UserDB User Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public HelperController(Cupboard cupboard, UserDB userDB) {
        this.cupboard = cupboard;
        this.userDB = userDB;
    }

    // @GetMapping("")
    // public ResponseEntity<Need[]> viewNeeds() {
    //     LOG.info("GET /Helper");
    //     try {
    //         Need[] cupboardNeeds = cupboard.getNeeds();
    //         if (cupboardNeeds != null)
    //             return new ResponseEntity<Need[]>(cupboardNeeds,HttpStatus.OK);
    //         else
    //             return new ResponseEntity<>(HttpStatus.CONFLICT);
    //     } catch (Exception e) {
    //         LOG.log(Level.SEVERE,e.getLocalizedMessage());
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

    // @GetMapping("/")
    // public ResponseEntity<Need[]> searchNeeds(@RequestParam(required=false) String name) {
    //     LOG.info("GET /Helper/?name="+name);
        
    //     try {
    //         Need[] needs = cupboard.findNeeds(name);
    //         if (needs != null)
    //             return new ResponseEntity<>(needs,HttpStatus.OK);
    //         else
    //             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //     } catch (Exception e) {
    //         LOG.log(Level.SEVERE,e.getLocalizedMessage());
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }
    @PostMapping("")
    public ResponseEntity<User> createHelper(@RequestBody Helper helper) {
        LOG.info("POST /Helper " + helper);
        
        try {
            Helper newHelper = userDB.createHelper(helper);
            if (newHelper  != null) {
                return new ResponseEntity<>(newHelper,HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @GetMapping("/")
    public ResponseEntity<ArrayList<Need>> viewFundingBasket(@RequestParam(required=true) int userId) {
        LOG.info("GET /Helper/?userId="+userId);
        
        try {
            User user = userDB.getUser(userId);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else if (!(user instanceof Helper)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            } else {
                Helper helper = (Helper) user;
                ArrayList<Need> fundingBasket = helper.getFundingBasket();
                if (fundingBasket != null)
                    return new ResponseEntity<>(fundingBasket,HttpStatus.OK);
                else
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{userId}/{needId}")
    public ResponseEntity<Need> addNeed(@PathVariable(required = true) int userId, @PathVariable(required = true) int needId) {
        LOG.info("PUT /Helper/" + userId + "/" + needId);

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
                    return new ResponseEntity<>(HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }  
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}