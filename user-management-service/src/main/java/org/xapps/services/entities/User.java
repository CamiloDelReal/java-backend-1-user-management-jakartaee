package org.xapps.services.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity(name = "User")
@Table(name = "users")
public class User {
    @Id
    @TableGenerator(name = "user_id_table_generator", table = "users_id_generator", pkColumnName = "id_pk_generator", valueColumnName = "id_value_generator", pkColumnValue = "users_id_generator", initialValue = 10, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_id_table_generator")
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "protected_password")
    private String protectedPassword;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    List<Role> roles;

    public User(String email, String protectedPassword, String firstName, String lastName, List<Role> roles) {
        this.email = email;
        this.protectedPassword = protectedPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
    }
}
