package com.waracle.cakemgr.service.impl;

import com.waracle.cakemgr.model.Cake;
import com.waracle.cakemgr.repository.CakeRepository;
import com.waracle.cakemgr.service.CakeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CakeServiceImpl implements CakeService {

    private final CakeRepository cakeRepository;

    @Autowired
    public CakeServiceImpl(CakeRepository cakeRepository) {
        this.cakeRepository = cakeRepository;
    }

    @Override
    public List<Cake> fetchAllCakes() {

        return this.cakeRepository.findAll();
    }

    @Override
    public Cake saveNewCakeData(Cake newCake) {

        if (newCake == null || newCake.getImageUrl() == null
                || newCake.getDescription() == null || newCake.getTitle() == null) {
            return null;
        }

        return this.cakeRepository.save(newCake);
    }

    @Override
    public Cake findCakeById(Long cakeId) {

        if (cakeId != null) {
            Optional<Cake> optionalCake = this.cakeRepository.findById(cakeId);
            if (optionalCake.isPresent()) {
                return optionalCake.get();
            }
        }

        return null;
    }
}
