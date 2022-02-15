package com.lecture.carrental.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 15)// obje için kısıtlamalar
    @NotNull(message = "Please enter your first name") // obje için kısıtlamalar
    @Column(nullable = false, length = 15) //database için kısıtlamalar
    private String firstName;

    @Size(max = 15)
    @NotNull(message = "Please enter your last name")
    @Column(nullable = false, length = 15)
    private String lastName;

    @Size(min = 4, max = 60, message = "the password length must to between 4 and 60 character ")
    @NotNull(message = "Please enter your password")
    @Column(nullable = false, length = 120)
    private String password;

    @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$", message = "Please enter valid phone number")
    @Size(min = 14, max = 14, message = "your phone length must to 14 character ")
    @NotNull(message = "Please enter your password")
    @Column(nullable = false, length = 14)
    private String phoneNumber;

    @Email()
    @Size(min = 5, max = 150)
    @NotNull(message = "Plase enter your mail")
    @Column(nullable = false, length = 14, unique = true)
    private String email;

    @Size(max = 250)
    @NotNull(message = "Plase enter your address")
    @Column(nullable = false, length = 250)
    private String address;

    @Size(min = 5, max = 150)
    @NotNull(message = "Plase enter your zip code")
    @Column(nullable = false, length = 15)
    private String zipCode;

    @Column(nullable = false)
    private Boolean builtIn;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();




}
