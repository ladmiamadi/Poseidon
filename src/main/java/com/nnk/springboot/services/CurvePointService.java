package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurvePointService {
    @Autowired
    CurvePointRepository curvePointRepository;

    public Iterable<CurvePoint> getCurvePointsList() {
        return curvePointRepository.findAll();
    }

    public CurvePoint createNewCurvePoint(CurvePoint curvePoint) {
        return curvePointRepository.save(curvePoint);
    }

    public CurvePoint getCurvePointById(Integer id) {
        return curvePointRepository.getById(id);
    }

    public void deleteCurvePoint(CurvePoint curvePoint) {
        curvePointRepository.delete(curvePoint);
    }
}
