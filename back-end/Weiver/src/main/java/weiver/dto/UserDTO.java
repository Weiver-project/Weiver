package weiver.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// mypage main dto
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String userId;
    private String nickname;
    private String profileImg;
    private int countJjim;
    private int countIsWatched;
    private int countPosts;
    private int countReplies;
    private int countPostLikes;

}
