package com.ufund.api.ufundapi;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Helper extends User {

    private static final Logger LOG = Logger.getLogger(Helper.class.getName());

    @JsonProperty("fundingBasket") 
    private final ArrayList<Need> fundingBasket;

    /**
     * Create a Helper with the given id, name, and admin status.
     * 
     * @param id The id of the Helper
     * @param name The name of the Helper
     * @param admin The admin status of the Helper
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.
     */
    public Helper(@JsonProperty("id") int id, 
                  @JsonProperty("name") String name, 
                  @JsonProperty("admin") boolean admin) {
        super(id, name, admin);
        this.fundingBasket = new ArrayList<>();
    }

    /**
     * Returns the funding basket of the Helper.
     * 
     * @return the funding basket, which is a list of {@link Need} objects
     */
    public ArrayList<Need> getFundingBasket() {
        return this.fundingBasket;
    }

    /**
     * Adds a need to the Helper's funding basket.
     * 
     * @param need the {@link Need} object to add
     */
    public void addNeed(Need need) {
        fundingBasket.add(need);
    }

    /**
     * Clears the Helper's funding basket.
     */
    public void checkout() {
        fundingBasket.clear();
    }

    /**
     * Removes a need from the Helper's funding basket.
     * 
     * @param need the {@link Need} object to remove
     */
    public void removeNeed(Need need) {
        fundingBasket.remove(need);
    }

    /**
     * Retrieves a need from the Helper's funding basket by its ID.
     * 
     * @param needId the ID of the need to retrieve
     * @return the {@link Need} object with the specified ID, or null if not found
     */
    public Need getNeed(int needId) {
        for (Need need : fundingBasket) {
            if (need.getId() == needId) {
                return need;
            }
        }
        return null;
    }

    /**
     * Returns a string representation of the Helper.
     * 
     * @return the string "Helper"
     */
    @Override
    public String toString() {
        return "Helper";
    }
}
