package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
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

@Controller
public class RatingController {
    @Autowired
    RatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        Iterable<Rating> ratings = ratingService.getRatingList();
        model.addAttribute("ratings", ratings);
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating, Model model) {

        model.addAttribute("rating", rating);
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            model.addAttribute("rating", rating);
        } else {
            ratingService.createNewRating(rating);
            redirectAttributes.addFlashAttribute("success", "Rating successfully added");

            return "redirect:/rating/list";
        }
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Rating rating = ratingService.getRatingById(id);

        model.addAttribute("rating", rating);
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        rating.setId(id);

        if(result.hasErrors()) {
            rating.setId(id);
            model.addAttribute("rating", rating);
        } else {
            ratingService.createNewRating(rating);
            redirectAttributes.addFlashAttribute("success", "Rating successfully updated");
            return "redirect:/rating/list";
        }

        return "rating/update";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        Rating rating = ratingService.getRatingById(id);

        if(rating != null) {
            ratingService.deleteRating(rating);
            redirectAttributes.addFlashAttribute("success", "Rating successfully deleted");
        }
        return "redirect:/rating/list";
    }
}
