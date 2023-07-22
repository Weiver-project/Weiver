package weiver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "INQUIRY")
@SequenceGenerator(name="INQUIRY_SEQUENCE_GENERATOR", sequenceName = "INQUIRY_SEQUENCE", initialValue = 1, allocationSize = 1)
public class Inquiry {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "INQUIRY_SEQUENCE_GENERATOR")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "created_time")
    private Date createdTime;

    @OneToOne(mappedBy = "inquiry")
    private Answer answer;
}
