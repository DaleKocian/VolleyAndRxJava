package android.example.com.volleyrxjava.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dkocian on 11/23/2014.
 */
public class Temperature implements Serializable {
    @SerializedName("day")
    Float day;
    @SerializedName("min")
    Float minimum;
    @SerializedName("max")
    Float maximum;
    @SerializedName("night")
    Float night;
    @SerializedName("eve")
    Float evening;
    @SerializedName("morn")
    Float morning;

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
