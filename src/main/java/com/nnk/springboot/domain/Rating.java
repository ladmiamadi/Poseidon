package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
    @NotEmpty(message = "Moodys Rating is mandatory")
    private String moodysRating;

    @Column(name = "sandPRating")
    @NotEmpty(message = "Sand Rating is mandatory")
    private String sandPRating;

    @Column(name = "fitchRating")
    @NotEmpty(message = "Fitch Rating is mandatory")
    private String fitchRating;

    @Column(name = "orderNumber")
    @NotNull(message = "Order Number is mandatory")
    @Min(value = 1, message = "Order Number must be greater than 0")
    private Integer orderNumber;
}
