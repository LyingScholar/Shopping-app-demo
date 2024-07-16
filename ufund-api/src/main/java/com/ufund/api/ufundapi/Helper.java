package com.ufund.api.ufundapi;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Helper extends User {

    private static final Logger LOG = Logger.getLogger(Helper.class.getName());

    @JsonProperty("fundingBasket") private ArrayList<Need> fundingBasket;

    /**
     * Create a Helper with the given id, name, admin status, skills, and availability
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
        this.fundingBasket = new ArrayList<Need>();
    }

    public ArrayList<Need> getFundingBasket() {
        return this.fundingBasket;
    }

    public void addNeed(Need need) {
        fundingBasket.add(need);
    }

    public void removeNeed(Need need) {
        fundingBasket.remove(need);
    }
    public Need getNeed(int needId) {
        for (Need need : fundingBasket) {
            if (need.getId() == needId) {
                return need;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Helper";
    }
}
