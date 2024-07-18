package com.ufund.api.ufundapi;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(User.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "User [id=%d, name=%s, admin=%s]";

    @JsonProperty("id") private final int id;
    @JsonProperty("name") private String name;
    @JsonProperty("admin") private Boolean admin;

    /**
     * Create a User with the given id and name
     * @param id The id of the User
     * @param name The name of the User
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public User(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("admin") boolean admin) {
        this.id = id;
        this.name = name;
        this.admin = admin;
    }

    /**
     * Retrieves the name of the user
     * @return The name of the user
     * (this method might be entirely superfluous)
     */
    public String getName() {return name;}
    /**
     * return whether the user is an admin
     * @return boolean
     */
    public boolean isAdmin(){return this.admin;}
    /**
     * Retrieves the id of the need
     * @return The id of the need
     */
    public int getId() {return id;}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,name, admin);
    }

}