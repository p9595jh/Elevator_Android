package com.example.user.myapplication;

import java.io.Serializable;
import java.util.ArrayList;

public class Sub {
    private String id;
    private Subscribes subscribes;
    private Stops stops;
    private Livetickets livetickets;
    private Liveviwers liveviwers;
    private String liveintro;
    private String livelocation;
    private String livedate;
    private String livetitle;

    public Sub(String id, Subscribes subscribes, Stops stops) {
        this.id = id;
        this.subscribes = subscribes;
        this.stops = stops;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Subscribes getSubscribes() {
        return subscribes;
    }

    public void setSubscribes(Subscribes subscribes) {
        this.subscribes = subscribes;
    }

    public Stops getStops() {
        return stops;
    }

    public void setStops(Stops stops) {
        this.stops = stops;
    }

    public Livetickets getLivetickets() {
        return livetickets;
    }

    public void setLivetickets(Livetickets livetickets) {
        this.livetickets = livetickets;
    }

    public Liveviwers getLiveviwers() {
        return liveviwers;
    }

    public void setLiveviwers(Liveviwers liveviwers) {
        this.liveviwers = liveviwers;
    }

    public String getLiveintro() {
        return liveintro;
    }

    public void setLiveintro(String liveintro) {
        this.liveintro = liveintro;
    }

    public String getLivelocation() {
        return livelocation;
    }

    public void setLivelocation(String livelocation) {
        this.livelocation = livelocation;
    }

    public String getLivedate() {
        return livedate;
    }

    public void setLivedate(String livedate) {
        this.livedate = livedate;
    }

    public String getLivetitle() {
        return livetitle;
    }

    public void setLivetitle(String livetitle) {
        this.livetitle = livetitle;
    }

    public static class Subscribes extends ArrayList<String> implements  Serializable {}
    public static class Stops extends ArrayList<String> implements Serializable {}
    public static class Livetickets extends ArrayList<String> implements Serializable {}
    public static class Liveviwers extends ArrayList<LiveviewerInfo> implements Serializable {}

    public static class LiveviewerInfo {
        private String id;
        private String ticket;

        public LiveviewerInfo(String id, String ticket) {
            this.id = id;
            this.ticket = ticket;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTicket() {
            return ticket;
        }

        public void setTicket(String ticket) {
            this.ticket = ticket;
        }
    }
}
