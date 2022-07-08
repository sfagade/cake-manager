package com.waracle.cakemgr.service;

import com.waracle.cakemgr.model.Cake;

import java.util.List;

public interface CakeService {

    public List<Cake> fetchAllCakes();
    public Cake saveNewCakeData(Cake newCake);
    public Cake findCakeById(Long cakeId);

}
