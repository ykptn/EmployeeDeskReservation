package com.eyl.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="desks")
public class Desk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "desk_no")
    private Long deskNo;

    @Column(name = "room")
    private String room;

    @ElementCollection
    @CollectionTable(name = "desk_unavailable_dates", joinColumns = @JoinColumn(name = "desk_no"))
    @Column(name = "unavailable_date", columnDefinition = "DATE")
    private Set<LocalDate> unavailableDates = new HashSet<>();

}
