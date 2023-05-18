package com.example.backend_prj.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonFile {


    public static final String JSONFILENAME = System.getProperty("user.dir") + "/src/main/resources/static/data/checkUser.json";

    public static void writeTo(int id, String fileName){

            JSONObject object = new JSONObject();
            object.put("id", id);
        try {
            Files.write(Paths.get(fileName), object.toJSONString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static Object readForJSONObject(String fileName){

        FileReader reader;
        try {
            reader = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        JSONParser jsonParser = new JSONParser();
        try {
            return jsonParser.parse(reader);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public static int readFromJSON(String fileName){
        JSONObject object = (JSONObject) readForJSONObject(fileName);
        Long count = (Long) object.get("id");
        return count.intValue();
    }
    public static void noOneLogged(String fileName){
        JSONObject object = new JSONObject();
        object.put("id",0);
        try {
            Files.write(Paths.get(fileName), object.toJSONString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
