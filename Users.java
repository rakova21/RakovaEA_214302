package com.crewrisk.model;

import com.crewrisk.model.enums.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Users implements UserDetails, Serializable {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Role role;

    private String username;
    private String password;

    private boolean certificationed = false;
    private String resume = "";

    @OneToOne(cascade = CascadeType.ALL)
    private Primarys primarys;
    @OneToOne(cascade = CascadeType.ALL)
    private Secondary secondary;
    @OneToOne(cascade = CascadeType.ALL)
    private Tertiary tertiary;
    @OneToOne(cascade = CascadeType.ALL)
    private Score score;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Vacancy> vacancies;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Offers> offers;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Apps> apps;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<HumanComments> commentsOwner;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<HumanComments> commentsUser;
    @OneToOne(cascade = CascadeType.ALL)
    private Question question;
    @OneToOne(cascade = CascadeType.ALL)
    private Certification certification;

    public Users(String username, String password, Role role) {
        this.role = role;
        this.username = username;
        this.password = passwordEncoder().encode(password);
        this.primarys = new Primarys();
        this.secondary = new Secondary();
        this.tertiary = new Tertiary();
        this.score = new Score(this);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(getRole());
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
