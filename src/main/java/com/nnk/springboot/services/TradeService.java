package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Optional<Trade> getTradeById(Integer id) {
        return tradeRepository.findById(id);
    }

    public void deleteTrade(Trade trade) {
        tradeRepository.delete(trade);
    }
}
