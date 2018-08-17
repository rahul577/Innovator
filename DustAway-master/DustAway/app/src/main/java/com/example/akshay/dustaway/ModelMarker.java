package com.example.akshay.dustaway;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by akshay on 3/1/18.
 */

public class ModelMarker implements Serializable {
    private transient LatLng latLng;
    private int index;
    private String Cname;
    private String Cdesc;
    private boolean inRange;

    public boolean isInRange() {
        return inRange;
    }

    public void setInRange(boolean inRange) {
        this.inRange = inRange;
    }

    public ModelMarker(ModelMarker m){
        this.latLng = m.latLng;
        this.index = m.index;
        this.Cname = m.Cname;
        this.Cdesc = m.Cdesc;
        inRange = false;
    }
    public LatLng getLatLng() {
        return latLng;
    }

    public int getIndex() {
        return index;
    }

    public String getCname() {
        return Cname;
    }

    public String getCdesc() {
        return Cdesc;
    }

    public ModelMarker(int index, String Cname, String Cdesc, LatLng latlng){
        this.index = index;
        this.Cname = Cname;
        this.Cdesc = Cdesc;
        this.latLng = latlng;
        inRange = false;

    }
}
