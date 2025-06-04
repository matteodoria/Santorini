package it.polimi.ingsw.Model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;

public class ParserManager {
    private JsonObject jSonObj;

    public ParserManager(String in) throws FileNotFoundException {

        InputStream is = null;
        is = this.getClass().getClassLoader().getResourceAsStream(in);
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));
        String line = null;
        try {
            line = buf.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        while (line != null) {
            sb.append(line).append("\n");
            try {
                line = buf.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String fileAsString = sb.toString();
        Gson gson = new Gson();
        this.jSonObj = gson.fromJson( fileAsString, JsonObject.class);
    }

    public JsonObject getJsonObj() {
        return jSonObj;
    }
}
