package weiver.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

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

    // 이미지 목록을 저장하는 컬렉션 필드
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "post_id")
    private List<Image> images = new ArrayList<>();

    public void addImage(Image image) {
        images.add(image);
    }

    public void removeImage(Image image) {
        images.remove(image);
    }
    
    //좋아요 목록을 저장하는 컬렉션 필드
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "post_id")
    private List<PostLike> postLikes = new ArrayList<>();

    public void addPostLike(PostLike postLike) {
    	postLikes.add(postLike);
    }

    public void removePostLike(PostLike postLike) {
    	postLikes.remove(postLike);
    }
}



