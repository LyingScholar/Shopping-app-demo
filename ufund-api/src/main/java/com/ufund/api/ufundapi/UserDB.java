package com.ufund.api.ufundapi;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class UserDB {
    
    private static final Logger LOG = Logger.getLogger(UserDB.class.getName());
    Map<Integer,User> users;   // Provides a local cache of the user objects
                                // so that we don't user to read from the file
                                // each time
    private List<User> loggedInUsers; // Provides a list of users that are logged in
    private ObjectMapper objectMapper;  // Provides conversion between User
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new user
    private String filename;    // Filename to read from and write to

    /**
     * Creates a User File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public UserDB(@Value("${users.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the users from the file
    }

    /**
     * Generates the next id for a new {@linkplain User user}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain User users} from the tree map
     * 
     * @return  The array of {@link User users}, may be empty
     */
    private User[] getUsersArray() {
        return getUsersArray("");
    }

    /**
     * Generates an array of {@linkplain User users} from the tree map for any
     * {@linkplain User users} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain User users}
     * in the tree map
     * 
     * @return  The array of {@link User users}, may be empty
     */
    private User[] getUsersArray(String containsText) { // if containsText == null, no filter
        ArrayList<User> userArrayList = new ArrayList<>();

        for (User user : users.values()) {
            if (!(containsText == null) && user.getName().contains(containsText)) {
                userArrayList.add(user);
            }
        }

        User[] userArray = new User[userArrayList.size()];
        userArrayList.toArray(userArray);
        return userArray;
    }

    public int login(String username) {
        // User doesn't exist
        if (checkForAUser(username) == false) {
            return 1;
        // User already logged in
        } else if (checkForALoggedInUser(username) == true) {
            return 2;
        // User logged in successfully
        } else {
            User user = getUserByName(username);
            loggedInUsers.add(user);
            return 3;
        }
    }
    /**
     * Saves the {@linkplain User users} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link User users} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        User[] userArray = getUsersArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),userArray);
        return true;
    }

    /**
     * Loads {@linkplain User users} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        users = new TreeMap<>();
        loggedInUsers = new ArrayList<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of users
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        User[] userArray = objectMapper.readValue(new File(filename),User[].class);

        // Add each user to the tree map and keep track of the greatest id
        for (User user : userArray) {
            users.put(user.getId(),user);
            if (user.getId() > nextId)
                nextId = user.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    
    public User[] getUsers() {
        synchronized(users) {
            return getUsersArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    
    public User[] findUsers(String containsText) {
        synchronized(users) {
            return getUsersArray(containsText);
        }
    }

    /**
    ** {@inheritDoc}
     */
    
    public User getUser(int id) {
        synchronized(users) {
            if (users.containsKey(id))
                return users.get(id);
            else
                return null;
        }
    }

    public User getUserByName(String name) {
        synchronized(users) {
            User[] userArray = getUsersArray();
            for (User user : userArray) {
                if (user.getName().equals(name)) {
                    return user;
                }
            }
            return null;
        }
    }

    public boolean checkForAUser(String name) {
        synchronized(users) {
            User[] userArray = getUsersArray();
            for (User user : userArray) {
                if (user.getName().equals(name)) {
                    return true;
                }
            }
            return false;
        }
    }

    public boolean checkForALoggedInUser(String name) {
        synchronized(loggedInUsers) {
            for (User user : loggedInUsers) {
                if (user.getName().equals(name)) {
                    return true;
                }
            }
            return false;
        }
    }
    /**
    ** {@inheritDoc}
     */
    
    public User createUser(User user) throws IOException {
        synchronized(users) {
            // We create a new user object because the id field is immutable
            // and we user to assign the next unique id
            if (!checkForAUser(user.getName())) {
                User newUser = new User(nextId(),user.getName(), user.isAdmin());
                users.put(newUser.getId(),newUser);
                save(); // may throw an IOException
                return newUser;
            } else {
                return null;
            }
        }
    }

    public Helper createHelper(Helper helper) throws IOException {
        synchronized(users) {
            // We create a new user object because the id field is immutable
            // and we user to assign the next unique id
            if (!checkForAUser(helper.getName())) {
                Helper newHelper = new Helper(nextId(),helper.getName(), helper.isAdmin());
                users.put(newHelper.getId(),newHelper);
                save(); // may throw an IOException
                return newHelper;
            } else {
                return null;
            }
        }
    }

    /**
    ** {@inheritDoc}
     */
    
    public User updateUser(User user) throws IOException {
        synchronized(users) {
            if (users.containsKey(user.getId()) == false)
                return null;  // user does not exist

            users.put(user.getId(),user);
            save(); // may throw an IOException
            return user;
        }
    }

    /**
    ** {@inheritDoc}
     */
    
    public boolean deleteUser(int id) throws IOException {
        synchronized(users) {
            if (users.containsKey(id)) {
                users.remove(id);
                return save();
            }
            else
                return false;
        }
    }
}
