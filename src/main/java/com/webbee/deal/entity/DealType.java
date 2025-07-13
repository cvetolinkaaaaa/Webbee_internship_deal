package com.webbee.deal.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "deal_type")
@NoArgsConstructor
@AllArgsConstructor
public class DealType {

    @Id
    @Column(length = 30)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
