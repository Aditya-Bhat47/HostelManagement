package com.example.HostelManagement.interfaces;

import com.example.HostelManagement.entity.Complaints;
import com.example.HostelManagement.entity.Rooms;
import com.example.HostelManagement.enums.Category;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("GENERAL")
public class GeneralComplaint extends Complaints {

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Rooms room;

}
