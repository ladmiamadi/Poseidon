package com.nnk.springboot.services;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

import java.sql.Timestamp;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CurvePointService.class})
@ExtendWith(SpringExtension.class)
class CurvePointServiceTest {
    @MockBean
    private CurvePointRepository curvePointRepository;

    @Autowired
    private CurvePointService curvePointService;

    private  CurvePoint curvePoint;

    @BeforeEach
    void setUp() {
        curvePoint = new CurvePoint();
        curvePoint.setAsOfDate(mock(Timestamp.class));
        curvePoint.setCreationDate(mock(Timestamp.class));
        curvePoint.setCurveId(123);
        curvePoint.setTerm(10.0);
        curvePoint.setValue(10.0);
        curvePoint.setId(1);
    }

    @Test
    void testGetCurvePointsList() {
        curvePointService.getCurvePointsList();

        verify(curvePointRepository).findAll();
    }

    @Test
    void testCreateNewCurvePoint() {
        curvePointService.createNewCurvePoint(curvePoint);

        ArgumentCaptor<CurvePoint> argumentCaptor = ArgumentCaptor.forClass(CurvePoint.class);

        verify(curvePointRepository).save(argumentCaptor.capture());

        CurvePoint capturedCurvePointList = argumentCaptor.getValue();
        assertThat(capturedCurvePointList).isEqualTo(curvePoint);
    }

    @Test
    void testGetCurvePointById() {
        when(curvePointService.createNewCurvePoint(curvePoint)).thenReturn(curvePoint);

        curvePointService.getCurvePointById(curvePoint.getId());

        verify(curvePointRepository).findById(curvePoint.getId());
    }

    @Test
    void testDeleteCurvePoint() {
        doNothing().when(curvePointRepository).delete(curvePoint);

        curvePointService.deleteCurvePoint(curvePoint);

        verify(curvePointRepository).delete(curvePoint);
        assertTrue(((Collection<CurvePoint>) curvePointService.getCurvePointsList()).isEmpty());
    }
}

