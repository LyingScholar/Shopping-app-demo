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

import com.ufund.api.ufundapi.User;
import com.ufund.api.ufundapi.UserDB;

/**
 * Handles the REST API requests for the User resource
 * Test commit
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author SWEN Faculty
 */

@RestController
@RequestMapping("Users")
public class UserController {
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());
    private UserDB UserDB;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param UserDB The {@link UserDB User Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public UserController(UserDB UserDB) {
        this.UserDB = UserDB;
    }

    /**
     * Responds to the GET request for a {@linkplain User User} for the given id
     * 
     * @param id The id used to locate the {@link User User}
     * 
     * @return ResponseEntity with {@link User User} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        LOG.info("GET /Users/" + id);
        try {
            User User = UserDB.getUser(id);
            if (User != null)
                return new ResponseEntity<User>(User,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } 
        catch(Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain User Useres}
     * 
     * @return ResponseEntity with array of {@link User User} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<User[]> getUsers() {
        LOG.info("GET /Users");

        try {
            User[] Users = UserDB.getUsers();
            if (Users != null)
                return new ResponseEntity<>(Users,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain User Useres} whose name contains
     * the text in name
     * 
     * @param name The name parameter which contains the text used to find the {@link User Useres}
     * 
     * @return ResponseEntity with array of {@link User User} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * Example: Find all Useres that contain the text "ma"
     * GET http://localhost:8080/Useres/?name=ma
     */
    @GetMapping("/search/")
    public ResponseEntity<User[]> searchUsers(@RequestParam(required=false) String name) {
        LOG.info("GET /Users/search/?name="+name);
        
        try {
            User[] Users = UserDB.findUsers(name);
            if (Users != null)
                return new ResponseEntity<>(Users,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain User User} with the provided User object
     * 
     * @param User - The {@link User User} to create
     * 
     * @return ResponseEntity with created {@link User User} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link User User} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody User User) {
        LOG.info("POST /Users " + User);
        
        try {
            User newUser = UserDB.createUser(User);
            if (newUser  != null) {
                return new ResponseEntity<>(newUser,HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    /**
     * Logs in the user with the provided username, if it is not already logged in
     * 
     * @param User The {@link User User} to update
     * 
     * @return ResponseEntity with updated {@link User User} object and HTTP status of OK if logged in<br>
     * ResponseEntity with HTTP status of UNAUTHORIZED if user doesn't exist<br>
     */
    @GetMapping("/login")
    public ResponseEntity<User> login(@RequestParam(required=false) String username) {
        LOG.info("GET /Users/login/?username=" + username);

        try {
            int status = UserDB.login(username);
            if (status == 1) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            } else if(status == 2) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                User user = UserDB.getUserByName(username);
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Logs out the user with the provided username, if it is already logged in
     * 
     * @param User The {@link User User} to update
     * 
     * @return ResponseEntity with updated {@link User User} object and HTTP status of OK if logged in<br>
     * ResponseEntity with HTTP status of UNAUTHORIZED if user doesn't exist<br>
     */
    @GetMapping("/logout")
    public ResponseEntity<User> logout(@RequestParam(required=false) String username) {
        LOG.info("GET /Users/logout/?username=" + username);

        try {
            int status = UserDB.logout(username);
            System.out.println(status);
            if (status == 1) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            } else if(status == 2) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                User user = UserDB.getUserByName(username);
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain User User} wih the provided {@linkplain User User} object, if it exists
     * 
     * @param User The {@link User User} to update
     * 
     * @return ResponseEntity with updated {@link User User} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<User> updateUser(@RequestBody User User) {
        LOG.info("PUT /Users " + User);

        try {
            User updatedUser = UserDB.updateUser(User);
            if (updatedUser == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain User User} with the given id
     * 
     * @param id The id of the {@link User User} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id) {
        LOG.info("DELETE /Users/" + id);

        try {
            if (UserDB.deleteUser(id)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Checks if a user is an admin
     * 
     * @param User The {@link User User} to update
     * 
     * @return ResponseEntity with updated {@link User User} object and HTTP status of OK if logged in<br>
     * ResponseEntity with HTTP status of UNAUTHORIZED if user doesn't exist<br>
     */
    @GetMapping("/check")
    public ResponseEntity<User> adminCheck(@RequestParam(required=false) String username) {
        LOG.info("GET /Users/check/?username=" + username);

        try {
            User user = UserDB.getUserByName(username);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                boolean isAdmin  = user.isAdmin();
                if (isAdmin) {
                    return new ResponseEntity<>(user, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(user, HttpStatus.FORBIDDEN);
                }
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
