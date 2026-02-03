package com.json.jsonData;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Arrays;
import java.util.List;
class Car {
    String brand;
    String model;
    List<String> features;

    Car(String brand, String model, List<String> features) {
        this.brand =brand;
        this.model =model;
        this.features = features;
    }

    public static void main(String[] args) {
        Car car =new Car("Toyota","Corolla",
            Arrays.asList("Air Conditioning", "Bluetooth", "ABS", "Cruise Control"));

        // Convert Car object into JSON using org.json
        JSONObject carJson = new JSONObject();
        carJson.put("brand", car.brand);
        carJson.put("model", car.model);

        JSONArray featuresArray =new JSONArray(car.features);
        carJson.put("features", featuresArray);

        System.out.println("Generated JSON for Car- ");
        System.out.println(carJson.toString(4));
    }
}