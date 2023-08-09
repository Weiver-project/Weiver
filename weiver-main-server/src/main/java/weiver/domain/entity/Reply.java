package weiver.domain.entity;

import java.util.Date;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "reply")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reply_sequence_generator")
    @SequenceGenerator(name = "reply_sequence_generator", sequenceName = "reply_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "content")
    private String content;

    @Column(name = "created_time")
    private Date createdTime;
}



