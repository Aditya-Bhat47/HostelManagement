package com.example.HostelManagement.interfaces;

import com.example.HostelManagement.entity.Complaints;
import com.example.HostelManagement.entity.MessMeal;
import com.example.HostelManagement.enums.MessIssue;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("MESS")
public class MessComplaint extends Complaints {
    @Enumerated(EnumType.STRING)
    private MessIssue issueType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meal_id")
    private MessMeal meal;        // which meal it was about

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;
}
