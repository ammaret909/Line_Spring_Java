package com.example.rmxline.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "check_rcc")
public class RCCModel {
    @Id
    private Long id;

    @Column(name = "check_rcc")
    private String checkRcc;

    public RCCModel() {
    }

    public RCCModel(Long id, String checkRcc) {
        this.id = id;
        this.checkRcc = checkRcc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCheckRcc() {
        return checkRcc;
    }

    public void setCheckRcc(String checkRcc) {
        this.checkRcc = checkRcc;
    }
}
