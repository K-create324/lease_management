package com.example.lease_management;

import com.sun.jna.platform.unix.solaris.LibKstat;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String contractNumber;

    private LocalDate startDate;

    private LocalDate endDate;

    private BigDecimal amount;

    private BigDecimal instalmentAmount;


    private LocalDate instalmentDate;



    @ManyToOne
    @JoinColumn(name="client_id")
    private Client client; //klucz obcy

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL)
    List<Item> items;

    public Contract() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getInstalmentAmount() {
        return instalmentAmount;
    }

    public void setInstalmentAmount(BigDecimal instalmentAmount) {
        this.instalmentAmount = instalmentAmount;
    }

    public LocalDate getInstalmentDate() {
        return instalmentDate;
    }

    public void setInstalmentDate(LocalDate instalmentDate) {
        this.instalmentDate = instalmentDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
