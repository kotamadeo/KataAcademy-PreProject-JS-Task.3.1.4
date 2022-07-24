package com.gmail.at.kotamadeo.models;

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
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull(message = "Фамилия не может быть пустой!")
    @Size(min = 3, max = 30, message = "Фамилия должна содержать от 3 до 30 символов!")
    private String surname;

    @Column
    @NotNull(message = "Имя не может быть пустым!")
    @Size(min = 3, max = 30, message = "Имя должно содержать от 3 до 30 символов!")
    private String name;

    @Column
    @NotNull(message = "Пол не может быть пустым!")
    @Size(min = 1, max = 6, message = "обозначение пола должно быть 1 символом!")
    private String sex;

    @Column
    @Email(message = "Email должен быть валидным!")
    private String email;

    @Column
    @Min(message = "Возраст не может быть отрицательным!", value = 0)
    @NotNull(message = "Возраст не может быть пустым!")
    private byte age;

    @Column
    private String username;

    @Column
//    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{7,20}$",
//            message = "Пароль должен содержать минимум 7 символов, максимум 20, а также 1 цифру, " +
//                    "1 букву в верхнем\\нижнем регистре и специальный символ!")
    private String password;

    @ManyToMany(cascade = CascadeType.MERGE)
    @Fetch(FetchMode.JOIN)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    public User(String surname, String name, String sex, String email, byte age) {
        this.surname = surname;
        this.name = name;
        this.sex = sex;
        this.email = email;
        this.age = age;
    }

    public User(long id, String surname, String name, String sex, String email, byte age, String username) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.sex = sex;
        this.email = email;
        this.age = age;
        this.username = username;
    }

    public User(long id, String surname, String name, String sex, String email, byte age, String username, String password) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.sex = sex;
        this.email = email;
        this.age = age;
        this.username = username;
        this.password = password;
    }

    public User(long id, String surname, String name, String sex, String email, byte age, String username,
                String password, Set<Role> roles) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.sex = sex;
        this.email = email;
        this.age = age;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public String getRolesString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Role role : this.getRoles()) {
            stringBuilder.append(role.getRoleTitle().split("_")[1]).append(" ");
        }
        return stringBuilder.toString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
}
