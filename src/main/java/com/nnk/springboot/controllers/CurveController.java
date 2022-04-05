package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Date;

/**
 * The CurvePoint controller.
 */
@Controller
public class CurveController {
    @Autowired
    CurvePointService curvePointService;

    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        Iterable<CurvePoint> curvePoints = curvePointService.getCurvePointsList();
        model.addAttribute("curvePoints", curvePoints);
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePoint curvePoint, Model model) {

        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model,
                           RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            model.addAttribute("curvePoint", curvePoint);
        } else {
            curvePoint.setCreationDate(new Timestamp(new Date().getTime()));
            curvePointService.createNewCurvePoint(curvePoint);
            redirectAttributes.addFlashAttribute("success", "curvePoint successfully added");

            return "redirect:/curvePoint/list";
        }

        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        CurvePoint curvePoint = curvePointService.getCurvePointById(id);
        model.addAttribute("curvePoint", curvePoint);

        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        curvePoint.setCurveId(id);
        curvePoint.setCreationDate(curvePointService.getCurvePointById(id).getCreationDate());

        if(result.hasErrors()) {
            curvePoint.setCurveId(id);
            model.addAttribute("curvePoint", curvePoint);
        } else {
            curvePoint.setAsOfDate(new Timestamp(new Date().getTime()));
            curvePointService.createNewCurvePoint(curvePoint);
            redirectAttributes.addFlashAttribute("success", "Curve Point successfully updated");
            return "redirect:/curvePoint/list";
        }

        return "curvePoint/update";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        CurvePoint curvePoint = curvePointService.getCurvePointById(id);

        if(curvePoint != null) {
            curvePointService.deleteCurvePoint(curvePoint);
            redirectAttributes.addFlashAttribute("success", "Curve Point successfully deleted");
        }
        return "redirect:/curvePoint/list";
    }
}
