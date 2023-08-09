package weiver.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import weiver.domain.entity.Post;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostReplyLikeDTO {
	private Post post;
	private long replyCount;
	private long likeCount;
}
