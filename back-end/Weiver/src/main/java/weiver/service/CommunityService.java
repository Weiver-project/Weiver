	package weiver.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import weiver.entity.Image;
import weiver.entity.Post;
import weiver.entity.PostLike;
import weiver.entity.ReReply;
import weiver.entity.Reply;
import weiver.entity.User;
import weiver.repository.CommunityRepository;
import weiver.repository.ImageRepository;
import weiver.repository.PostLikeRepository;
import weiver.repository.ReReplyRepository;
import weiver.repository.ReplyRepository;
import weiver.repository.UserRepository;

@Service
public class CommunityService {
	
	@Autowired
    private CommunityRepository communityRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ReReplyRepository rereplyRepository;
	
	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private PostLikeRepository postlikeRepository;
	
	// 이미지를 저장하는 디렉토리 경로
    private static final String IMAGE_UPLOAD_DIR = "C:/multi/Weiver/back-end/Weiver/src/main/resources/static/img/image";
	
	/*
	 * 게시글 관련 기능
	 * */
	
	//모든 게시글 가져오기
	public List<Post> getAllPosts() {
		 List<Post> posts = communityRepository.findAll();
		 System.out.println(posts);
		 return posts;
	}

	
	//게시글 타입(Review, Chat)에 따라 게시글 가져오기
	public List<Post> getAllPostsByType(String type) {
	    List<Post> posts = communityRepository.findAllByType(type);
	    System.out.println(posts);
	    return posts;
	}

	
	//조회수를 기준으로 인기 게시글 가져오기
	public List<Post> getBestPostDesc() {
		List<Post> posts = communityRepository.findAllByOrderByViewedDesc();
		System.out.println(posts);
	    return posts;
	}

	//게시글 id에 따라 게시글 가져오기 > 상세 페이지
	public Post getPostById(Long id) {
		Post post = communityRepository.getPostById(id);
		System.out.println(post);
		
		return post;
	}

	//title, content에 들어 있는 키워드에 따라 게시글 가져오기 > 커뮤니티 검색 페이지
		public List<Post> getPostByKeyword(String keyword) {
			List<Post> posts = communityRepository.findByTitleContainingOrContentContaining(keyword, keyword);
			System.out.println(posts);
		    return posts;
		}
	

	//게시글 수정하기
	public boolean updatePost(Long id, String type, String title, String content) throws Exception, SQLException {
		int res = communityRepository.updatePost(id, type, title, content);
		if (res != 0) {
	        return true;
	    } else {
	        throw new Exception("글 수정 실패");
	    }
	}

	
	//게시글 삭제하기
	public boolean deletePostById(Long id) throws Exception, SQLException {
		boolean result = false;
		
		int res = communityRepository.deletePostById(id);
		
		if(res != 0) {
			result = true;
		} else {
			throw new Exception("글 삭제 실패");
		}
		
		return result;
	}
	
	// 게시글 작성
		 public boolean savePost(User user, String type, String title, String content, List<String> imagePaths) throws Exception {
		        Date date = new Date();

		        Post post = Post.builder()
		                        .user(user)
		                        .type(type)
		                        .title(title)
		                        .content(content)
		                        .createdTime(date)
		                        .viewed(0L)
		                        .build();

		        Post resultPost = communityRepository.save(post);
		        if (resultPost.getId() == null) {
		            return false;
		        }

		        // 이미지 삽입
		        if (imagePaths != null && !imagePaths.isEmpty()) {
		            for (String imagePath : imagePaths) {
		                Image image = Image.builder()
		                                    .postId(resultPost.getId())
		                                    .path(imagePath)
		                                    .build();

		                imageRepository.save(image);
		            }
		        }

		        return true;
		    }

    
	
	/*
	 * 댓글 관련 기능
	 * */
	//댓글 조회
	public Reply findReply(Long id) {
		return replyRepository.findById(id).get();
	}
	
	
	//댓글 삽입
	public boolean insertReply(Reply reply) {

		// user_id와 post_id의 유효성 검증
	    User user = userRepository.getUserById(reply.getUser().getId());
	    if (user == null) {
	        return false;
	    }

	    Post post = communityRepository.getPostById(reply.getPost().getId());
	    if (post == null) {
	    	return false;
	    }

	    // 댓글 삽입 로직
	    if (replyRepository.save(reply) != null) {
	        return true;
	    } else {
	    	return false;
	    }
	}


	// post_id에 따라 댓글 가져오기
    public List<Reply> findReplyByPostId(Long postId) {
    	List<Reply> replies = replyRepository.findRepliesByPostId(postId);
    	System.out.println(replies);
    	return replies;
    }
	
	//아이디로 하나의 댓글 가져오기.
	public Reply getReplyById(Long id) {
		Reply reply = replyRepository.getReplyById(id);
		System.out.println(reply);
		
		return reply;
	}

	//댓글 수정하기
	public boolean updateReply(Long id, String content) throws Exception, SQLException {
		int res = replyRepository.updateReply(id, content);
		if (res != 0) {
	        return true;
	    } else {
	        throw new Exception("댓글 수정 실패");
	    }
	}

	//댓글 삭제하기
	public boolean deleteReplyById(Long id) throws Exception, SQLException {
		boolean result = false;
		
		int res = replyRepository.deleteReplyById(id);
		
		if(res != 0) {
			result = true;
		} else {
			throw new Exception("댓글 삭제 실패");
		}
		
		return result;
		
	}

	
	/*
	 * 대댓글 관련 기능
	 * */
	
	//고유 아이디로 하나의 대댓글 조회하기
	public ReReply getRereplyById(Long id) {
		ReReply rereply = rereplyRepository.getRereplyById(id);
		System.out.println(rereply);
		
		return rereply;
	}
	
	// post_id와 reply_id에 따라 대댓글 가져오기
		public List<ReReply> getReReplyByPostIdAndReplyId(Long postId, Long replyId) {
		    	List<ReReply> rereplies = rereplyRepository.findByPostIdAndReplyId(postId, replyId);
		    	System.out.println(rereplies);
		    	return rereplies;
		   }

	
		public List<ReReply> getReReplyByReplyId(Long replyId) {
	    	return rereplyRepository.findByReplyId(replyId);
	   }
		
		
	//대댓글 수정
	public boolean updateRereply(Long id, String content)throws Exception, SQLException {
		int res = rereplyRepository.updateRereply(id, content);
		if (res != 0) {
	        return true;
	    } else {
	        throw new Exception("대댓글 수정 실패");
	    }
	}

	
	//대댓글 삭제
	public boolean deleteRereplyById(Long id)throws Exception, SQLException {
		boolean result = false;
		
		int res = rereplyRepository.deleteRereplyById(id);
		
		if(res != 0) {
			result = true;
		} else {
			throw new Exception("댓글 삭제 실패");
		}
		
		return result;
	}


		//대댓글 삽입
		public boolean insertRereply(ReReply rereply) {
		    boolean result = false;
	
		    // user_id, post_id, reply_id의 유효성 검증
		    User user = userRepository.getUserById(rereply.getUser().getId());
		    if (user == null) {
		        return false;
		    }

		    Post post = communityRepository.getPostById(rereply.getPost().getId());
		    if (post == null) {
		    	return false;
		    }
		    Reply reply = replyRepository.getReplyById(rereply.getReply().getId());
		    if (reply == null) {
		    	return false;
		    }
	
		    // 댓글 삽입 로직
		    if (rereplyRepository.save(rereply) != null) {
		        return true;
		    } else {
		    	return false;
		    }
		   

		    
		}


		/*
		 * 기타
		 * */
		
		public boolean insertImage(Image image) throws SQLException, Exception {
			boolean result = false;
			
			Long postId = image.getPostId();
			String path = image.getPath();
			
			int res = imageRepository.insertImage(postId, path);
			
			
			if (res != 0) {
		        result = true;
		    } else {
		        throw new Exception("이미지 생성 실패");
		    }
			System.out.println(res);
		    return result;
		}
		
		public void incrementViewCount(Post post) {
			// 현재 조회수를 가져옴
		    Long currentViewCount = post.getViewed();

		    // 조회수를 1 증가시킴
		    post.setViewed(currentViewCount + 1);

		    // 증가된 조회수를 업데이트
		    communityRepository.save(post);
		}


		public String saveImage(MultipartFile imageFile) throws IOException {
	        // 이미지 파일의 확장자를 추출
	        String fileExtension = FilenameUtils.getExtension(imageFile.getOriginalFilename());

	        // 서버에 저장할 새 파일 이름 생성 (랜덤 문자열 사용 또는 업로드 시간 기반 이름 등)
	        String newFileName = UUID.randomUUID().toString() + "." + fileExtension;

	        // 이미지 파일을 서버의 지정된 디렉토리로 저장
	        Path filePath = Paths.get(IMAGE_UPLOAD_DIR, newFileName);
	        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

	        // 이미지 파일의 경로를 반환
	        return IMAGE_UPLOAD_DIR + "/" + newFileName;
	    }

		
		// 게시글 좋아요 삽입
		public boolean insertPostLike(PostLike postlike) {

			// user_id와 post_id의 유효성 검증
		    User user = userRepository.getUserById(postlike.getUser().getId());
		    if (user == null) {
		        return false;
		    }

		    Post post = communityRepository.getPostById(postlike.getPost().getId());
		    if (post == null) {
		    	return false;
		    }

		    // 좋아요 삽입 로직
		    if (postlikeRepository.save(postlike) != null) {
		        return true;
		    } else {
		    	return false;
		    }
		}



		public PostLike getpostlikeById(Long id) {
			PostLike postlike = postlikeRepository.getPostLikeById(id);
			
			return postlike;
		}


		public boolean deletePostlike(Long id) throws Exception, SQLException {
			boolean result = false;
			
			int res = postlikeRepository.deletePostLikeById(id);
			
			if(res != 0) {
				result = true;
			} else {
				throw new Exception("좋아요 취소 실패");
			}
			
			return result;
			
		}










    
    
}







	




