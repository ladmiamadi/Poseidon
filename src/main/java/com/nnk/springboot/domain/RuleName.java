package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "rulename")
@Setter
@Getter
public class RuleName {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @NotEmpty(message = "Name is mandatory")
    private String name;

    @Column(name = "description")
    @NotEmpty(message = "Description is mandatory")
    private String description;

    @Column(name = "json")
    @NotEmpty(message = "Json is mandatory")
    private String json;

    @Column(name = "template")
    @NotEmpty(message = "Template is mandatory")
    private String template;

    @Column(name = "sqlStr")
    @NotEmpty(message = "Sql STR is mandatory")
    private String sqlStr;

    @Column(name = "sqlPart")
    @NotEmpty(message = "Sql Part is mandatory")
    private String sqlPart;
}
