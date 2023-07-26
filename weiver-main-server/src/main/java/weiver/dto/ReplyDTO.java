package weiver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyDTO {
    private Long id;
    private Long postId;
    private String userId;
    private String content;
    private Date createdTime;
    private int countLikes;
}
