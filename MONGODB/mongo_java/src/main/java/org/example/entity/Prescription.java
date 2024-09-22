package org.example.entity;

import java.util.Date;

public class Prescription {
    private String detailsPrescription;
    private Date datePrescription;

    public String getDetailsPrescription() {
        return detailsPrescription;
    }

    public void setDetailsPrescription(String detailsPrescription) {
        this.detailsPrescription = detailsPrescription;
    }

    public Date getDatePrescription() {
        return datePrescription;
    }

    public void setDatePrescription(Date datePrescription) {
        this.datePrescription = datePrescription;
    }
}
