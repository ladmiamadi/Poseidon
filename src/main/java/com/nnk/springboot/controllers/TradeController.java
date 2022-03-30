package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
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

@Controller
public class TradeController {
    @Autowired
    TradeService tradeService;

    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        Iterable<Trade> trades = tradeService.getTradeList();
        model.addAttribute("trades", trades);
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade trade, Model model) {
        model.addAttribute("trade", trade);
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            model.addAttribute("trade", trade);
        } else {
            trade.setCreationDate(new Timestamp(new Date().getTime()));

            tradeService.createNewTrade(trade);
            redirectAttributes.addFlashAttribute("success", "Trade successfully added");
            return "redirect:/trade/list";
        }

        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Trade trade = tradeService.getTradeById(id);

        if(trade != null) {
            model.addAttribute("trade", trade);
            return "trade/update";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid trade ID");
            return "redirect:/trade/list";
        }
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        trade.setTradeId(id);
        trade.setCreationDate(tradeService.getTradeById(id).getCreationDate());
        if(result.hasErrors()) {
            trade.setTradeId(id);
            model.addAttribute("trade", trade);
        } else {
            trade.setRevisionDate(new Timestamp(new Date().getTime()));
            tradeService.createNewTrade(trade);
            redirectAttributes.addFlashAttribute("success", "Trade successfully updated");
            return "redirect:/trade/list";
        }

        return "trade/update";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        Trade trade = tradeService.getTradeById(id);

        if(trade != null) {
            tradeService.deleteTrade(trade);
            redirectAttributes.addFlashAttribute("success", "Trade successfully deleted");
        }
        return "redirect:/trade/list";
    }
}
