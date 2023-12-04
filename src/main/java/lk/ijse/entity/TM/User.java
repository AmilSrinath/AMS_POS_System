package lk.ijse.entity.TM;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
