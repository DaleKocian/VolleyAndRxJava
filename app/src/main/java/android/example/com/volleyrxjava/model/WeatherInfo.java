package android.example.com.volleyrxjava.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dkocian on 11/23/2014.
 */
public class WeatherInfo implements Serializable {
    @SerializedName("dt")
    Long dt;
    @SerializedName("temp")
    Temperature temperature;
    @SerializedName("pressure")
    Float pressure;
    @SerializedName("humidity")
    Float humidity;
    @SerializedName("weather")
    List<Weather> weather;
    @SerializedName("speed")
    Float speed;
    @SerializedName("deg")
    Float degrees;
    @SerializedName("clouds")
    Float clouds;

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
