package com.ufund.api.ufundapi;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Need {

    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(Need.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "need [id=%d, name=%s, quantity=%d, cost=%d, type=%s]";

    @JsonProperty("id") private final int id;
    @JsonProperty("name") private String name;
    @JsonProperty("quantity") int quantity;
    @JsonProperty("cost") int cost;
    @JsonProperty("type") String type;

    /**
     * Create a need with the given id and name
     * @param id The id of the need
     * @param name The name of the need
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Need(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("quantity") int quantity, 
                @JsonProperty("cost") int cost, @JsonProperty("type") String type) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.cost = cost;
        this.type = type;
    }
    // test
    /**
     * Retrieves the id of the need
     * @return The id of the need
     */
    public int getId() {return id;}

    /**
     * Sets the name of the need - necessary for JSON object to Java object deserialization
     * @param name The name of the need
     */
    public void setName(String name) {this.name = name;}

    /**
     * Retrieves the name of the need
     * @return The name of the need
     */
    public String getName() {return name;}
    /**
     * retrieves quantities of the need
     * @return the quantity of the need
     */
    public int getQuantity() {return quantity;}
    /**
     * 
     * @return
     */
    public int getCost() {return cost;}

    public String getType() {return type;}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,name, quantity, cost, type);
    }

}