package weiver.domain.entity;

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
@Table(name = "re_reply")
public class ReReply {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rereply_sequence_generator")
    @SequenceGenerator(name = "rereply_sequence_generator", sequenceName = "re_reply_sequence", allocationSize = 1)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id")
    private Reply reply;

    @Column(name = "content")
    private String content;
    
    @Column(name = "created_time")
    private Date createdTime;
    
}



