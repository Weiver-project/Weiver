package weiver.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "admin")
public class Admin {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "admin_pw")
    private String password;

    @Column(name = "admin_name")
    private String name;
}
