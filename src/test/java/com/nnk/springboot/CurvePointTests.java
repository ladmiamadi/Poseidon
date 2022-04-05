package com.nnk.springboot;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurvePointTests {

	@Autowired
	private CurvePointRepository curvePointRepository;

	CurvePoint curvePoint;

	@Test
	public void curvePointTest() {
		curvePoint = new CurvePoint();
		curvePoint.setCreationDate(new Timestamp(new Date().getTime()));
		curvePoint.setCurveId(10);
		curvePoint.setTerm(10d);
		curvePoint.setValue(30d);

		// Save
		curvePoint = curvePointRepository.save(curvePoint);
		Assert.assertNotNull(curvePoint.getId());
		Assert.assertEquals(10, (int) curvePoint.getCurveId());

		// Update
		curvePoint.setCurveId(20);
		curvePoint = curvePointRepository.save(curvePoint);
		Assert.assertEquals(20, (int) curvePoint.getCurveId());

		// Find
		List<CurvePoint> listResult = curvePointRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = curvePoint.getId();
		curvePointRepository.delete(curvePoint);
		Optional<CurvePoint> curvePointList = curvePointRepository.findById(id);
		Assert.assertFalse(curvePointList.isPresent());
	}

}
