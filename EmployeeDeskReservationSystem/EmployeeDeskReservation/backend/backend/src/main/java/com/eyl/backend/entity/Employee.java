package com.eyl.backend.entity;

import com.eyl.backend.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empId;

    @Column(name= "first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name ="email", nullable = false, unique = true)
    private String email;

    @Column(name="password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "dep_id", nullable = false)
    private Department department;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private RoleEnum role;

}
