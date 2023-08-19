package com.sirzechs.ppdb.model.status;

import com.google.gson.annotations.SerializedName;
import com.sirzechs.ppdb.model.sekolah.SekolahData;

public class Status {
    @SerializedName("data")
    private StatusData statusData;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;

    public StatusData getStatusData() {
        return statusData;
    }

    public void setStatusData(StatusData statusData) {
        this.statusData = statusData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
