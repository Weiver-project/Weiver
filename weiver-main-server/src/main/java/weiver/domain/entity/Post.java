package weiver.domain.entity;

import lombok.*;

import javax.persistence.*;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Data
@Entity
@Table(name = "post")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_seq_gen")
	@SequenceGenerator(name = "post_seq_gen", sequenceName = "post_sequence", allocationSize = 1)
	private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "type")
    private String type;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "viewed")
    private Long viewed = 0L;

    @Column(name = "image")
    private String image;
}
