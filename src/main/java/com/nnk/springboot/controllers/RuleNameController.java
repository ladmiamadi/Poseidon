package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
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

@Controller
@Slf4j
public class RuleNameController {
    @Autowired
    RuleNameService ruleNameService;

    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        Iterable<RuleName> ruleNames = ruleNameService.getRuleNameList();
        log.info("Getting rulNames list="+ ruleNames);
        model.addAttribute("ruleNames", ruleNames);
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName, Model model) {
        log.info("Displaying form");
        model.addAttribute("ruleName", ruleName);
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            log.error("There ere errors in the form=" + result.getAllErrors());
            model.addAttribute("ruleName", ruleName);
        } else {
            ruleNameService.createNewRuleName(ruleName);
            log.debug("Creating new RuleName="+ ruleName);
            redirectAttributes.addFlashAttribute("success", "Rule successfully added");

            return "redirect:/ruleName/list";
        }
        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RuleName ruleName = ruleNameService.getRuleNameById(id);
        log.info("Displaying form");
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        ruleName.setId(id);

        if(result.hasErrors()) {
            ruleName.setId(id);
            log.error("There ere errors in the form=" + result.getAllErrors());
            model.addAttribute("ruleName", ruleName);
        } else {
            ruleNameService.createNewRuleName(ruleName);
            log.debug("Updating RuleName=" + ruleName);
            redirectAttributes.addFlashAttribute("success", "Rule successfully updated");
            return "redirect:/ruleName/list";
        }

        return "ruleName/update";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        RuleName ruleName = ruleNameService.getRuleNameById(id);

        if(ruleName != null) {
            ruleNameService.deleteRuleName(ruleName);
            log.info("Deleting ruleName="+ruleName);
            redirectAttributes.addFlashAttribute("success", "Rule successfully deleted");
        }
        return "redirect:/ruleName/list";
    }
}
