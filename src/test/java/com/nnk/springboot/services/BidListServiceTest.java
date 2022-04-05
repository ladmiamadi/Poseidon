package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Timestamp;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class BidListServiceTest {

    @MockBean
    BidListRepository bidListRepository;

    @Autowired
    BidListService bidListService;

    BidList bidList;

    @BeforeEach
    void setUp() {

        bidList = new BidList();
        bidList.setBidListId(1);
        bidList.setCreationDate(new Timestamp(new Date().getTime()));
        bidList.setBidQuantity(1.0);
        bidList.setAccount("bid");
        bidList.setType("type");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getBidListsTest() {
        bidListService.getBidLists();

        verify(bidListRepository).findAll();
    }

    @Test
    void createNewBidList() {
        bidListService.createNewBidList(bidList);

        ArgumentCaptor<BidList> argumentCaptor = ArgumentCaptor.forClass(BidList.class);

        verify(bidListRepository).save(argumentCaptor.capture());

        BidList capturedBidList = argumentCaptor.getValue();
        assertThat(capturedBidList).isEqualTo(bidList);
    }

    @Test
    void getBidListById() {
        when(bidListRepository.save(bidList)).thenReturn(bidList);

        bidListService.getBidListById(bidList.getBidListId());

        verify(bidListRepository).findById(bidList.getBidListId());
    }

    @Test
    void deleteBidList() {
        when(bidListRepository.save(bidList)).thenReturn(bidList);

        bidListService.deleteBidList(bidList);

        verify(bidListRepository).delete(bidList);
    }
}