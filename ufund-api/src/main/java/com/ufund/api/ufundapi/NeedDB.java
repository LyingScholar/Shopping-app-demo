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
public class NeedDB {
    
    private static final Logger LOG = Logger.getLogger(NeedDB.class.getName());
    Map<Integer,Need> needs;   // Provides a local cache of the need objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between need
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new need
    private String filename;    // Filename to read from and write to

    /**
     * Creates a need File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public NeedDB(@Value("${needs.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the needs from the file
    }

    /**
     * Generates the next id for a new {@linkplain need need}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Need needs} from the tree map
     * <br>
     * 
     * @return  The array of {@link Need needs}, may be empty
     */
    private Need[] getNeedsArray() { // if containsText == null, no filter
        ArrayList<Need> needArrayList = new ArrayList<>();

        for (Need need : needs.values()) {
            needArrayList.add(need);

        }
        Need[] needArray = new Need[needArrayList.size()];
        needArrayList.toArray(needArray);
        return needArray;
    }

    
    /**
     * Saves the {@linkplain need needs} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link need needs} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Need[] needArray = getNeedsArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),needArray);
        return true;
    }

    /**
     * Loads {@linkplain need needs} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        needs = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of needs
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Need[] needArray = objectMapper.readValue(new File(filename),Need[].class);

        // Add each need to the tree map and keep track of the greatest id
        for (Need need : needArray) {
            needs.put(need.getId(),need);
            if (need.getId() > nextId)
                nextId = need.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

}
