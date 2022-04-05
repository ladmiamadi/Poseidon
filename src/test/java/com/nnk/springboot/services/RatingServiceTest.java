package com.nnk.springboot.services;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

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

@ContextConfiguration(classes = {RatingService.class})
@ExtendWith(SpringExtension.class)
class RatingServiceTest {
    @MockBean
    private RatingRepository ratingRepository;

    @Autowired
    private RatingService ratingService;

    Rating rating;

    @BeforeEach
    void setUp() {
        rating = new Rating();
        rating.setFitchRating("Fitch Rating");
        rating.setMoodysRating("Moodys Rating");
        rating.setOrderNumber(10);
        rating.setSandPRating("Sand PRating");
    }

    @Test
    void testGetRatingList() {
        ratingService.getRatingList();

        verify(ratingRepository).findAll();
    }


    @Test
    void testCreateNewRating() {
        ratingService.createNewRating(rating);

        ArgumentCaptor<Rating> argumentCaptor = ArgumentCaptor.forClass(Rating.class);

        verify(ratingRepository).save(argumentCaptor.capture());

        Rating capturedRating = argumentCaptor.getValue();
        assertThat(capturedRating).isEqualTo(rating);
    }

    @Test
    void testGetRatingById() {
        when(ratingRepository.save(rating)).thenReturn(rating);
        ratingService.getRatingById(rating.getId());
        verify(ratingRepository).getById((Integer) any());
    }

    @Test
    void testDeleteRating() {
        when(ratingRepository.save(rating)).thenReturn(rating);

        ratingService.deleteRating(rating);
        verify(ratingRepository).delete(rating);
    }

}

