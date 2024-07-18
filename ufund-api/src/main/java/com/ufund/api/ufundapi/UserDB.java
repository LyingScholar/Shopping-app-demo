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
    
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(UserDB.class.getName());
    Map<Integer,User> users;   // Provides a local cache of the user objects
                                // so that we don't user to read from the file
                                // each time
    Map<Integer,Helper> helpers;
    private List<User> loggedInUsers; // Provides a list of users that are logged in
    private ObjectMapper objectMapper;  // Provides conversion between User
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new user
    private String userFile;    // User Filename to read from and write to
    private String helperFile;    // Helper Filename to read from and write to

    /**
     * Creates a User File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public UserDB(@Value("${users.file}") String userFile, @Value("${helpers.file}") String helperFile, ObjectMapper objectMapper) throws IOException {
        this.userFile = userFile;
        this.helperFile = helperFile;
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

    /**
     * Generates an array of {@linkplain Helper helpers} from the tree map for any
     * {@linkplain Helper helpers} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Helper helpers}
     * in the tree map
     * 
     * @return  The array of {@link Helper helpers}, may be empty
     */
    private Helper[] getHelpersArray() { // if containsText == null, no filter
        ArrayList<Helper> helperArrayList = new ArrayList<>();

        for (Helper helper : helpers.values()) {
            helperArrayList.add(helper);
        }

        Helper[] helperArray = new Helper[helperArrayList.size()];
        helperArrayList.toArray(helperArray);
        return helperArray;
    }
   /**
     * Logs in a {@linkplain Helper helpers}
     * @param username the username the user is attempting to login with
     * @return  An integer status code (1 = no user, 2 = already logged in, 3 = success)
     */
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
     * Logs out a {@linkplain Helper helpers}
     * @param username the username the user is attempting to login with
     * @return  An integer status code (1 = no user, 2 = already logged in, 3 = success)
     */
    public int logout(String username) {
        // User doesn't exist
        if (checkForAUser(username) == false) {
            return 1;
        // User hasn't logged in
        } else if (checkForALoggedInUser(username) == false) {
            return 2;
        // User logged out successfully
        } else {
            for (User user : loggedInUsers) {
                if (user.getName().equals(username)) {
                    loggedInUsers.remove(user);
                    break;
                }
            }
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
        Helper[] helperArray = getHelpersArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(userFile),userArray);
        objectMapper.writeValue(new File(helperFile),helperArray);
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
        helpers = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of users
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        User[] userArray = objectMapper.readValue(new File(userFile),User[].class);

        // Add each user to the tree map and keep track of the greatest id
        for (User user : userArray) {
            users.put(user.getId(),user);
            if (user.getId() > nextId)
                nextId = user.getId();
        }


        Helper[] helperArray = objectMapper.readValue(new File(helperFile),Helper[].class);

        // Add each user to the tree map and keep track of the greatest id
        for (Helper helper : helperArray) {
            users.put(helper.getId(),helper);
            helpers.put(helper.getId(),helper);
            if (helper.getId() > nextId)
                nextId = helper.getId();
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
    /**
    ** {@inheritDoc}
     */
    public Helper getHelper(int id) {
        synchronized(helpers) {
            if (helpers.containsKey(id))
                return helpers.get(id);
            else
                return null;
        }
    }
    /**
    ** {@inheritDoc}
     */
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
    /**
    ** {@inheritDoc}
     */
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
    /**
    ** {@inheritDoc}
     */
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

    /**
    ** {@inheritDoc}
     */
    public Helper createHelper(Helper helper) throws IOException {
        synchronized(users) {
            // We create a new user object because the id field is immutable
            // and we user to assign the next unique id
            if (!checkForAUser(helper.getName())) {
                Helper newHelper = new Helper(nextId(),helper.getName(), helper.isAdmin());
                users.put(newHelper.getId(),newHelper);
                helpers.put(newHelper.getId(), newHelper);
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
