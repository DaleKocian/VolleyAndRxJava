package example.com.volleyrxjava.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dkocian on 11/23/2014.
 */
public class Temperature implements Serializable {
    @SerializedName("day")
    private Float day;
    @SerializedName("min")
    private Float minimum;
    @SerializedName("max")
    private Float maximum;
    @SerializedName("night")
    private Float night;
    @SerializedName("eve")
    private Float evening;
    @SerializedName("morn")
    private Float morning;

    public Float getDay() {
        return day;
    }

    public void setDay(Float day) {
        this.day = day;
    }

    public Float getMinimum() {
        return minimum;
    }

    public void setMinimum(Float minimum) {
        this.minimum = minimum;
    }

    public Float getMaximum() {
        return maximum;
    }

    public void setMaximum(Float maximum) {
        this.maximum = maximum;
    }

    public Float getNight() {
        return night;
    }

    public void setNight(Float night) {
        this.night = night;
    }

    public Float getEvening() {
        return evening;
    }

    public void setEvening(Float evening) {
        this.evening = evening;
    }

    public Float getMorning() {
        return morning;
    }

    public void setMorning(Float morning) {
        this.morning = morning;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "day=" + day +
                ", minimum=" + minimum +
                ", maximum=" + maximum +
                ", night=" + night +
                ", evening=" + evening +
                ", morning=" + morning +
                '}';
    }
}
