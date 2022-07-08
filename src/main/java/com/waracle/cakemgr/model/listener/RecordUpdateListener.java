package com.waracle.cakemgr.model.listener;

import com.waracle.cakemgr.model.Cake;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class RecordUpdateListener {

    @PreUpdate
    public void setUpdateDates(Cake cake) {
        cake.setModifiedOn(LocalDateTime.now());
    }

    @PrePersist
    public void setCreateDates(Cake cake) {
        cake.setModifiedOn(LocalDateTime.now());
        cake.setCreatedOn(LocalDateTime.now());
    }
}
