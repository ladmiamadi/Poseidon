package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Optional;

/**
 * The CurvePoint controller.
 * @author ladmia
 */
@Controller
@Slf4j
public class CurveController {
    @Autowired
    CurvePointService curvePointService;

    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        Iterable<CurvePoint> curvePoints = curvePointService.getCurvePointsList();
        log.info("CurvePoint list= " + curvePoints);
        model.addAttribute("curvePoints", curvePoints);
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePoint curvePoint, Model model) {
        log.info("Displaying form");
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model,
                           RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            log.error("There are errors in the form" + result.getAllErrors());
            model.addAttribute("curvePoint", curvePoint);
        } else {
            curvePoint.setCreationDate(new Timestamp(new Date().getTime()));
            curvePointService.createNewCurvePoint(curvePoint);
            log.debug("Adding curvePoint="+ curvePoint + " to the database");
            redirectAttributes.addFlashAttribute("success", "curvePoint successfully added");

            return "redirect:/curvePoint/list";
        }

        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<CurvePoint> curvePoint = curvePointService.getCurvePointById(id);
        log.info("Getting CurvePoint="+ curvePoint);

        if(curvePoint.isPresent()) {
            model.addAttribute("curvePoint", curvePoint.get());
            return "curvePoint/update";
        } else {
            log.error("Invalid curvePoint id=" + id);
            redirectAttributes.addFlashAttribute("error", "Invalid curvePoint ID");
            return "redirect:/curvePoint/list";
        }
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        curvePoint.setCurveId(id);
        curvePoint.setCreationDate(curvePointService.getCurvePointById(id).get().getCreationDate());

        if(result.hasErrors()) {
            log.error("There are errors in the form=" + result.getAllErrors());
            curvePoint.setCurveId(id);
            model.addAttribute("curvePoint", curvePoint);
        } else {
            curvePoint.setAsOfDate(new Timestamp(new Date().getTime()));
            curvePointService.createNewCurvePoint(curvePoint);
            log.debug("Updating curvePoint=" + curvePoint);
            redirectAttributes.addFlashAttribute("success", "Curve Point successfully updated");
            return "redirect:/curvePoint/list";
        }

        return "curvePoint/update";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        Optional<CurvePoint> curvePoint = curvePointService.getCurvePointById(id);

        if(curvePoint.isPresent()) {
            curvePointService.deleteCurvePoint(curvePoint.get());
            log.info("Deleting curvePoint=" + curvePoint);
            redirectAttributes.addFlashAttribute("success", "Curve Point successfully deleted");
        } else {
            log.error("Invalid curvePoint id=" + id);
            redirectAttributes.addFlashAttribute("error", "Invalid curvePoint ID");
        }

        return "redirect:/curvePoint/list";
    }
}
