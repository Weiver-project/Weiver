package weiver.entity;

import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
    private Long viewed = 0L;

    //이미지 데이터 받아와서 저장
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "post_id")
    private List<Image> images = new ArrayList<>();

    public void addImage(Image image) {
        images.add(image);
    }

    public void removeImage(Image image) {
        images.remove(image);
    }
    
    //좋아요 데이터 받아와서 저장
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "post_id")
    private List<PostLike> postlikes = new ArrayList<>();

    public void addLike(PostLike postlike) {
    	postlikes.add(postlike);
    }

    public void removeLike(PostLike postlike) {
    	postlikes.remove(postlike);
    }
}
