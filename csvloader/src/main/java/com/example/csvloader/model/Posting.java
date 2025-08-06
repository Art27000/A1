package com.example.csvloader.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "postings")
public class Posting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "mat_doc")
    private String matDoc;

    @Column(name = "item")
    private Integer item;

    @Column(name = "doc_date")
    private LocalDate docDate;

    @Column(name = "pstng_date")
    private LocalDate pstngDate;

    @Column(name = "material_description", length = 512)
    private String materialDescription;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "bun")
    private String bun;

    @Column(name = "amount_lc")
    private BigDecimal amountLc;

    @Column(name = "crcy")
    private String crcy;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "authorized_supply")
    private Boolean authorizedSupply;

    public void setMatDoc(String matDoc) {
        this.matDoc = matDoc;
    }

    public void setItem(Integer item) {
        this.item = item;
    }

    public void setDocDate(LocalDate docDate) {
        this.docDate = docDate;
    }

    public void setPstngDate(LocalDate pstngDate) {
        this.pstngDate = pstngDate;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public void setBun(String bun) {
        this.bun = bun;
    }

    public void setAmountLc(BigDecimal amountLc) {
        this.amountLc = amountLc;
    }

    public void setCrcy(String crcy) {
        this.crcy = crcy;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAuthorizedSupply(boolean authorizedSupply) {
        this.authorizedSupply = authorizedSupply;
    }

    public BigDecimal getAmountLc() {
        return amountLc;
    }

    public Boolean getAuthorizedSupply() {
        return authorizedSupply;
    }

    public String getBun() {
        return bun;
    }

    public String getCrcy() {
        return crcy;
    }

    public LocalDate getDocDate() {
        return docDate;
    }

    public Long getId() {
        return id;
    }

    public Integer getItem() {
        return item;
    }

    public String getMatDoc() {
        return matDoc;
    }

    public String getMaterialDescription() {
        return materialDescription;
    }

    public LocalDate getPstngDate() {
        return pstngDate;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public String getUserName() {
        return userName;
    }
}
