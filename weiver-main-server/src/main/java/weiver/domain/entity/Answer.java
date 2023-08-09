package weiver.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "inquiry")
@Entity
@Table(name = "answer")
public class Answer {
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer_seq_gen")
	@SequenceGenerator(name = "answer_seq_gen", sequenceName = "answer_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquiry_id")
    private Inquiry inquiry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @Column(name = "answer")
    private String answer;

    @Column(name = "created_time")
    private Date createdTime;
}
