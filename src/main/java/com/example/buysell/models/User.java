package com.example.buysell.models;

import com.example.buysell.models.enums.Role;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id ;
    @Column(name = "email",unique = true)
    private String email;
    @Column(name = "phone_number" )
    private String phoneNumber;
    @Column(name = "name")
    private String name;
    @Column(name ="active" )
    private boolean active;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id")
    @Column(name = "avatar")
    private Image avatar;
    @Column(name = "password", length = 1000)
    private String password;

    @ElementCollection(targetClass = Role.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
    joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();
    private LocalDateTime dateOfCreated;

    @PrePersist
    private void init(){
        dateOfCreated=LocalDateTime.now();
    }

    public User() {
    }

    public User(Long id, String email, String phoneNumber, String name, boolean active, Image avatar, String password, Set<Role> roles, LocalDateTime dateOfCreated) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.active = active;
        this.avatar = avatar;
        this.password = password;
        this.roles = roles;
        this.dateOfCreated = dateOfCreated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public LocalDateTime getDateOfCreated() {
        return dateOfCreated;
    }

    public void setDateOfCreated(LocalDateTime dateOfCreated) {
        this.dateOfCreated = dateOfCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return active == user.active && id.equals(user.id) && email.equals(user.email) && phoneNumber.equals(user.phoneNumber) && name.equals(user.name) && avatar.equals(user.avatar) && password.equals(user.password) && roles.equals(user.roles) && dateOfCreated.equals(user.dateOfCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, phoneNumber, name, active, avatar, password, roles, dateOfCreated);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", name='" + name + '\'' +
                ", active=" + active +
                ", avatar=" + avatar +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", dateOfCreated=" + dateOfCreated +
                '}';
    }
}
