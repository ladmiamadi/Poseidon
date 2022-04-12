package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
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
import java.util.Optional;

/**
 * The type Rating controller.
 * @author ladmia
 */
@Controller
@Slf4j
public class RatingController {
    @Autowired
    RatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        Iterable<Rating> ratings = ratingService.getRatingList();
        log.info("Getting ratings list="+ ratings);
        model.addAttribute("ratings", ratings);
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating, Model model) {
        log.info("Displaying form");
        model.addAttribute("rating", rating);
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            log.error("There ere errors in the form=" + result.getAllErrors());
            model.addAttribute("rating", rating);
        } else {
            ratingService.createNewRating(rating);
            log.debug("Creating new rating="+ rating);
            redirectAttributes.addFlashAttribute("success", "Rating successfully added");

            return "redirect:/rating/list";
        }
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Rating> rating = ratingService.getRatingById(id);
        log.info("Getting Rating="+ rating);

        if(rating.isPresent()) {
            model.addAttribute("rating", rating.get());
            return "rating/update";
        } else {
            log.error("Invalid rating id=" + id);
            redirectAttributes.addFlashAttribute("error", "Invalid rating ID");
            return "redirect:/rating/list";
        }
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        rating.setId(id);

        if(result.hasErrors()) {
            rating.setId(id);
            log.error("There ere errors in the form=" + result.getAllErrors());
            model.addAttribute("rating", rating);
        } else {
            ratingService.createNewRating(rating);
            log.info("Updating rating="+ rating);
            redirectAttributes.addFlashAttribute("success", "Rating successfully updated");
            return "redirect:/rating/list";
        }

        return "rating/update";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        Optional<Rating> rating = ratingService.getRatingById(id);

        if(rating.isPresent()) {
            ratingService.deleteRating(rating.get());
            log.info("Deleting rating from database: " + rating);
            redirectAttributes.addFlashAttribute("success", "Rating successfully deleted");
        }  else {
            log.error("Invalid rating id=" + id);
            redirectAttributes.addFlashAttribute("error", "Invalid rating ID");
        }

        return "redirect:/rating/list";
    }
}
