package persistence;

import org.json.JSONObject;

// Modeled after Code in JsonSerializationDemo, repo found here:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
