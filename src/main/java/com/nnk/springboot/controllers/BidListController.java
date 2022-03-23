package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Date;


@Controller
public class BidListController {
    @Autowired
    BidListService bidListService;

    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        Iterable<BidList> bidLists= bidListService.getBidLists();
        model.addAttribute("bidLists", bidLists);
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid, Model model) {
        model.addAttribute("bidList", bid);

        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            model.addAttribute("bidList", bid);
        } else {
            bid.setBidListDate(new Timestamp(new Date().getTime()));
            bid.setCreationDate(new Timestamp(new Date().getTime()));

            bidListService.createNewBidList(bid);
            redirectAttributes.addFlashAttribute("success", "BidList successfully added");
            return "redirect:/bidList/list";
        }

        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        BidList bidList = bidListService.getBidListById(id);

        if(bidList != null) {
            model.addAttribute("bidList", bidList);
            return "bidList/update";
        } else {
           // redirectAttributes.addFlashAttribute("error", "Invalid BidList ID");
            //return "redirect:/bidList/list";
            throw new EntityNotFoundException("BidList not found");
        }

    }


    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        bidList.setBidListId(id);
        bidList.setCreationDate(bidListService.getBidListById(id).getCreationDate());
        if(result.hasErrors()) {
            bidList.setBidListId(id);
            model.addAttribute("bidList", bidList);
        } else {
            bidList.setRevisionDate(new Timestamp(new Date().getTime()));
            bidListService.createNewBidList(bidList);
            redirectAttributes.addFlashAttribute("success", "BidList successfully updated");
            return "redirect:/bidList/list";
        }

        return "bidList/update";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        BidList bidList = bidListService.getBidListById(id);

        if(bidList != null) {
            bidListService.deleteBidList(bidList);
        }
        return "redirect:/bidList/list";
    }
}
