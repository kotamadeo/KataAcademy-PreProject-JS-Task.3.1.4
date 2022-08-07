package com.gmail.at.kotamadeo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    @NotNull(message = "Имя не может быть пустым!")
    @Size(min = 3, max = 30, message = "Имя должно содержать от 3 до 30 символов!")
    private String name;

    @Column
    @NotNull(message = "Фамилия не может быть пустой!")
    @Size(min = 3, max = 30, message = "Фамилия должна содержать от 3 до 30 символов!")
    private String surname;

    @Column
    @NotNull(message = "Пол не может быть пустым!")
    @Size(min = 1, max = 6, message = "обозначение пола должно быть 1 символом!")
    private String sex;

    @Column
    private String nickname;
    @Column
    @Email(message = "Email должен быть валидным!")
    private String email;

    @Column
    @Min(message = "Возраст не может быть отрицательным!", value = 0)
    @NotNull(message = "Возраст не может быть пустым!")
    private byte age;

    @Column
    //    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{7,20}$",
//            message = "Пароль должен содержать минимум 7 символов, максимум 20, а также 1 цифру, " +
//                    "1 букву в верхнем\\нижнем регистре и специальный символ!")
    private String password;

    @ManyToMany(cascade = CascadeType.MERGE)
    @Fetch(FetchMode.JOIN)
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User(String surname, String name, String sex, String email, String nickname, byte age) {
        this.surname = surname;
        this.name = name;
        this.sex = sex;
        this.email = email;
        this.nickname = nickname;
        this.age = age;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
