package com.nnk.springboot.services;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RuleNameService.class})
@ExtendWith(SpringExtension.class)
class RuleNameServiceTest {
    @MockBean
    private RuleNameRepository ruleNameRepository;

    @Autowired
    private RuleNameService ruleNameService;

    RuleName ruleName;

    @BeforeEach
    void setUp() {
        ruleName = new RuleName();
        ruleName.setDescription("The characteristics of someone or something");
        ruleName.setJson("Json");
        ruleName.setId(1);
        ruleName.setName("Name");
        ruleName.setSqlPart("Sql Part");
        ruleName.setSqlStr("Sql Str");
        ruleName.setTemplate("Template");
    }

    @Test
    void testGetRuleNameList() {
        ruleNameService.getRuleNameList();

        verify(ruleNameRepository).findAll();
    }

    @Test
    void testCreateNewRuleName() {
        ruleNameService.createNewRuleName(ruleName);

        ArgumentCaptor<RuleName> argumentCaptor = ArgumentCaptor.forClass(RuleName.class);

        verify(ruleNameRepository).save(argumentCaptor.capture());

        RuleName capturedRuleName = argumentCaptor.getValue();
        assertThat(capturedRuleName).isEqualTo(ruleName);
    }

    @Test
    void testGetRuleNameById() {
        when(ruleNameService.createNewRuleName(ruleName)).thenReturn(ruleName);

       ruleNameService.getRuleNameById(ruleName.getId());

        verify(ruleNameRepository).findById(ruleName.getId());
    }

    @Test
    void testDeleteRuleName() {
        doNothing().when(this.ruleNameRepository).delete((RuleName) any());

        this.ruleNameService.deleteRuleName(ruleName);
        verify(this.ruleNameRepository).delete((RuleName) any());
        assertEquals("The characteristics of someone or something", ruleName.getDescription());
        assertEquals("Template", ruleName.getTemplate());
        assertEquals("Sql Str", ruleName.getSqlStr());
        assertEquals("Sql Part", ruleName.getSqlPart());
        assertEquals("Name", ruleName.getName());
        assertEquals("Json", ruleName.getJson());
        assertEquals(1, ruleName.getId().intValue());
        assertTrue(((Collection<RuleName>) this.ruleNameService.getRuleNameList()).isEmpty());
    }
}

