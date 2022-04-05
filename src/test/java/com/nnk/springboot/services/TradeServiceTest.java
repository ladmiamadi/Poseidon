package com.nnk.springboot.services;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TradeService.class})
@ExtendWith(SpringExtension.class)
class TradeServiceTest {
    @MockBean
    private TradeRepository tradeRepository;

    @Autowired
    private TradeService tradeService;

    Trade trade;

    @BeforeEach
    void setUp() {
        trade = new Trade();
        trade.setAccount("3");
        trade.setBuyQuantity(10.0d);
        trade.setCreationDate(mock(Timestamp.class));
        trade.setType("Type");
    }

    @Test
    void testGetTradeList() {
        ArrayList<Trade> tradeList = new ArrayList<>();
        when(tradeRepository.findAll()).thenReturn(tradeList);
        Iterable<Trade> actualTradeList = tradeService.getTradeList();
        assertSame(tradeList, actualTradeList);
        assertTrue(((Collection<Trade>) actualTradeList).isEmpty());
        verify(tradeRepository).findAll();
    }

    @Test
    void testCreateNewTrade() {
        tradeService.createNewTrade(trade);

        ArgumentCaptor<Trade> argumentCaptor = ArgumentCaptor.forClass(Trade.class);

        verify(tradeRepository).save(argumentCaptor.capture());

        Trade capturedRuleName = argumentCaptor.getValue();
        assertThat(capturedRuleName).isEqualTo(trade);
    }

    @Test
    void testGetTradeById() {
        when(tradeRepository.getById((Integer) any())).thenReturn(trade);
        assertSame(trade, tradeService.getTradeById(1));
        verify(tradeRepository).getById((Integer) any());
    }

    @Test
    void testDeleteTrade() {
        doNothing().when(tradeRepository).delete((Trade) any());

        tradeService.deleteTrade(trade);
        verify(tradeRepository).delete((Trade) any());
        assertEquals("3", trade.getAccount());
        assertEquals("Type", trade.getType());

        assertTrue(((Collection<Trade>) tradeService.getTradeList()).isEmpty());
    }
}

