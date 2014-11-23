package android.example.com.volleyrxjava.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dkocian on 11/23/2014.
 */
public class City implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("coord")
    private Coordinates coordinates;
    @SerializedName("country")
    private String country;
    @SerializedName("population")
    private int population;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    @Override
    public String toString() {
        return "City{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", country='" + country + '\'' +
                ", population=" + population +
                '}';
    }
}
