package weiver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "post_like")
public class PostLike {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_like_seq_gen")
    @SequenceGenerator(name = "post_like_seq_gen", sequenceName = "post_like_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}



