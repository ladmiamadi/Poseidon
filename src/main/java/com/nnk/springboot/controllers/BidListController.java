package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
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
 * The type Bid list controller.
 * Created By Ladmia
 */
@Controller
@Slf4j
public class BidListController {
    /**
     * The Bid list service.
     */
    @Autowired
    BidListService bidListService;

    /**
     * Home
     */
    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        Iterable<BidList> bidLists= bidListService.getBidLists();
        log.debug("Getting bidLists: " + bidLists);
        model.addAttribute("bidLists", bidLists);
        return "bidList/list";
    }

    /**
     * display bid form .
     *
     * @param bid  the bidList
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid, Model model) {
        log.info("display form");

        model.addAttribute("bidList", bid);

        return "bidList/add";
    }

    /**
     * Validate BidList Form.
     *
     * @param bid  the new bidList
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            log.error("There are errors in the form");
            model.addAttribute("bidList", bid);
        } else {
            bid.setBidListDate(new Timestamp(new Date().getTime()));
            bid.setCreationDate(new Timestamp(new Date().getTime()));
            bidListService.createNewBidList(bid);

            log.debug("adding bidList to dataBase: "+ bid);
            redirectAttributes.addFlashAttribute("success", "BidList successfully added");
            return "redirect:/bidList/list";
        }

        return "bidList/add";
    }

    /**
     * Show update form string.
     *
     * @param id the id of the bidList to update
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<BidList> bidList = bidListService.getBidListById(id);
        log.info("Looking for"+ bidList +"in database");

        if(bidList.isPresent()) {
            model.addAttribute("bidList", bidList);
            return "bidList/update";
        } else {
            log.error("Invalid id=" + id);
            redirectAttributes.addFlashAttribute("error", "Invalid BidList ID");
            return "redirect:/bidList/list";
        }
    }

    /**
     * Update bidList.
     *
     * @param id the id of the bidList to update
     * @param bidList the bid list to update
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        bidList.setBidListId(id);
        bidList.setCreationDate(bidListService.getBidListById(id).get().getCreationDate());

        if(result.hasErrors()) {
            log.error("There are errors in the form");
            bidList.setBidListId(id);
            model.addAttribute("bidList", bidList);
        } else {
            bidList.setRevisionDate(new Timestamp(new Date().getTime()));
            bidListService.createNewBidList(bidList);
            log.debug("Adding bidList=" + bidList + "to dataBase");
            redirectAttributes.addFlashAttribute("success", "BidList successfully updated");
            return "redirect:/bidList/list";
        }

        return "bidList/update";
    }

    /**
     * Delete bid string.
     *
     * @param id of the bidlist to delete
     *
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        Optional<BidList> bidList = bidListService.getBidListById(id);

        if(bidList.isPresent()) {
            bidListService.deleteBidList(bidList.get());
            log.info("deleting bidList id=" + id);
            redirectAttributes.addFlashAttribute("success", "BidList successfully deleted");
        }
        return "redirect:/bidList/list";
    }
}
