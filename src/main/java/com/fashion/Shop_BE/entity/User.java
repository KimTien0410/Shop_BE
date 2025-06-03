package com.fashion.Shop_BE.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
@Entity

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;
    @Column(unique=true,name="email",nullable=false)
    private String email;
    @Column(nullable = false)
    @JsonIgnore
    private String password;
    @Column(name="first_name")
    private String firstName;
    @Column(name="lastName")
    private String lastName;
    @Column(name="user_phone")
    private String userPhone;
    @Column(name="date_of_birth")
    private Date dateOfBirth;
    private boolean gender=false;
    @Column(name="user_avatar")
    private String userAvatar;

    private String provider;
    @Column(name="verification_code")
    private String verificationCode;

    private boolean enabled=false;

    @OneToMany(mappedBy = "user",cascade=CascadeType.REMOVE)
    @ToString.Exclude
    private List<ReceiverAddress> receiverAddresses;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at")
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="updated_at")
    private Date updatedAt;

    @Column(name="deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;


    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="user_roles",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_name")
    )
    private Set<Role> roles;

    @OneToOne(mappedBy="user",cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Order> orders;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
        this.deletedAt = null;
    }
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }
}
