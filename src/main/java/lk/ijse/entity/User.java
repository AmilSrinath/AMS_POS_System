package lk.ijse.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter

@Entity
@Table(name = "user")
public class User {
    @Id
    private String id;
    private String name;
    private String username;
    private String password;
    private String displayUsername;
    private String email;
    private String contactNumber;
}
