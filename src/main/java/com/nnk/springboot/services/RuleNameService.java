package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuleNameService {
    @Autowired
    RuleNameRepository ruleNameRepository;

    public Iterable<RuleName> getRuleNameList() {
        return ruleNameRepository.findAll();
    }

    public RuleName createNewRuleName(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }

    public RuleName getRuleNameById(Integer id) {
        return ruleNameRepository.getById(id);
    }

    public void deleteRuleName(RuleName ruleName) {
        ruleNameRepository.delete(ruleName);
    }
}
