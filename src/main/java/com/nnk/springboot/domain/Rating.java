package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "rating")
@Setter
@Getter
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "moodysRating")
    private String moodysRating;
    @Column(name = "sandPRating")
    private String sandPRating;
    @Column(name = "fitchRating")
    private String fitchRating;
    @Column(name = "orderNumber")
    private Integer orderNumber;
}
