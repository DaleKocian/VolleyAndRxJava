package example.com.volleyrxjava.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dkocian on 11/23/2014.
 */
public class WeatherInfo implements Serializable {
    @SerializedName("dt")
    private Long dt;
    @SerializedName("temp")
    private Temperature temperature;
    @SerializedName("pressure")
    private Float pressure;
    @SerializedName("humidity")
    private Float humidity;
    @SerializedName("weather")
    private List<Weather> weather;
    @SerializedName("speed")
    private Float speed;
    @SerializedName("deg")
    private Float degrees;
    @SerializedName("clouds")
    private Float clouds;

    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public Float getPressure() {
        return pressure;
    }

    public void setPressure(Float pressure) {
        this.pressure = pressure;
    }

    public Float getHumidity() {
        return humidity;
    }

    public void setHumidity(Float humidity) {
        this.humidity = humidity;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Float getSpeed() {
        return speed;
    }

    public void setSpeed(Float speed) {
        this.speed = speed;
    }

    public Float getDegrees() {
        return degrees;
    }

    public void setDegrees(Float degrees) {
        this.degrees = degrees;
    }

    public Float getClouds() {
        return clouds;
    }

    public void setClouds(Float clouds) {
        this.clouds = clouds;
    }

    @Override
    public String toString() {
        return "WeatherInfo{" +
                "dt=" + dt +
                ", temperature=" + temperature +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", weather=" + weather +
                ", speed=" + speed +
                ", degrees=" + degrees +
                ", clouds=" + clouds +
                '}';
    }
}
