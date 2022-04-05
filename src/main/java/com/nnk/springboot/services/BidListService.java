package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BidListService {
    @Autowired
    BidListRepository bidListRepository;

    public Iterable<BidList> getBidLists() {
        return bidListRepository.findAll();
    }

    public BidList createNewBidList(BidList bidList) {
        return bidListRepository.save(bidList);
    }

    public Optional<BidList> getBidListById(Integer id) {
        return bidListRepository.findById(id);
    }

    public void deleteBidList(BidList bidList) {
        bidListRepository.delete(bidList);
    }
}
