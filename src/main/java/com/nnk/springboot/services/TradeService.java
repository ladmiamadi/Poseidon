package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeService {
    @Autowired
    TradeRepository tradeRepository;

    public Iterable<Trade> getTradeList() {
        return tradeRepository.findAll();
    }

    public Trade createNewTrade(Trade trade) {
        return tradeRepository.save(trade);
    }

    public Trade getTradeById(Integer id) {
        return tradeRepository.getById(id);
    }

    public void deleteTrade(Trade trade) {
        tradeRepository.delete(trade);
    }
}
