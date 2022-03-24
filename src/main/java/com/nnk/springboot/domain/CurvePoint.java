package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity
@Table(name = "curvepoint")
@Setter
@Getter
public class CurvePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "curveId")
    @Min(value = 1, message = "Curve Id must be greater than 0")
    @NotNull(message = "Must not be null")
    private Integer curveId;

    @Column(name = "asOfDate")
    private Timestamp asOfDate;

    @Column(name = "term")
    @Min(value = 1, message = "Term must be greater than 0")
    @NotNull(message = "Must not be null")
    private Double term;

    @Column(name = "value")
    @Min(value = 1, message = "Value must be greater than 0")
    @NotNull(message = "Must not be null")
    private Double value;

    @Column(name = "creationDate")
    private Timestamp creationDate;
}
