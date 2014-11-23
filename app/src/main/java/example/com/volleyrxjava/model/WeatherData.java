package example.com.volleyrxjava.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dkocian on 11/23/2014.
 */
public class WeatherData implements Serializable {
    @SerializedName("cod")
    private String cod;
    @SerializedName("message")
    private Float message;
    @SerializedName("city")
    private City city;
    @SerializedName("cnt")
    private int count;
    @SerializedName("list")
    private List<WeatherInfo> weatherInfoList;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Float getMessage() {
        return message;
    }

    public void setMessage(Float message) {
        this.message = message;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<WeatherInfo> getWeatherInfoList() {
        return weatherInfoList;
    }

    public void setWeatherInfoList(List<WeatherInfo> weatherInfoList) {
        this.weatherInfoList = weatherInfoList;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "cod='" + cod + '\'' +
                ", message=" + message +
                ", city=" + city +
                ", count=" + count +
                ", weatherInfoList=" + weatherInfoList +
                '}';
    }
}
