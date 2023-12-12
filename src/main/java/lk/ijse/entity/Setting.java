package lk.ijse.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter

@Entity
@Table(name = "setting")
public class Setting {
    @Id
    private String settingID;
    private String showUsername;
    private String showDate;
    private String showTime;
    private String showNotification;

    @OneToOne(mappedBy = "setting")
    private User user;
}
