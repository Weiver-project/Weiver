package weiver.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import weiver.entity.Post;
import weiver.entity.ReReply;
import weiver.entity.Reply;
import weiver.entity.User;
import weiver.repository.CommunityRepository;
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
	
	
	/*
	 * 댓글 관련 기능
	 * */
	
	//댓글 삽입
	public boolean insertReply(Reply reply) throws Exception {
	    boolean result = false;

	    // user_id와 post_id의 유효성 검증
	    User user = userRepository.getUserById(reply.getUser().getId());
	    if (user == null) {
	        throw new Exception("댓글 작성자 정보가 존재하지 않습니다.");
	    }

	    Post post = communityRepository.getPostById(reply.getPost().getId());
	    if (post == null) {
	        throw new Exception("게시물이 존재하지 않습니다.");
	    }

	    // 댓글 삽입 로직
	    int res = replyRepository.insertReply(reply.getId(), post.getId(), user.getId(), reply.getContent(), reply.getCreatedTime());
	    if (res != 0) {
	        result = true;
	        System.out.println("댓글 생성 성공");
	    } else {
	        throw new Exception("댓글 생성 실패");
	    }
	    return result;
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









    
    
}







	




