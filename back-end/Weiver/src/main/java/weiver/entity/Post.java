package weiver.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Long viewed;

    // 이미지 entity 참조
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "post_id")
//    private List<Image> images = new ArrayList<>();
//
//    public void addImage(Image image) {
//        images.add(image);
//    }
//
//    public void removeImage(Image image) {
//        images.remove(image);
//    }

    // 좋아요 entity 참조
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "post_id")
//    private List<PostLike> postLikes = new ArrayList<>();
//
//    public void addPostLike(PostLike postLike) {
//        postLikes.add(postLike);
//    }
//
//    public void removePostLike(PostLike postLike) {
//        postLikes.remove(postLike);
//    }

    // 댓글 entity 참조
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "post_id")
//    private List<Reply> replies = new ArrayList<>();
//
//    public void addReply(Reply reply) {
//        replies.add(reply);
//    }
//
//    public void removeReply(Reply reply) {
//        replies.remove(reply);
//    }

    // 대댓글 entity 참조
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "post_id")
//    private List<ReReply> rereplies = new ArrayList<>();
//
//    public void addReReply(ReReply rereply) {
//        rereplies.add(rereply);
//    }
//
//    public void removeReReply(ReReply rereply) {
//        rereplies.remove(rereply);
//    }
}
