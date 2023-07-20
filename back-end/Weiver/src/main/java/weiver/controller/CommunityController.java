package weiver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import weiver.entity.*;
import weiver.service.CommunityService;
import weiver.service.MusicalService;
import weiver.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommunityController {

	private final CommunityService communityService;
    private final UserService userService;
    private final MusicalService MusicalService;


    /*
     * 커뮤니티 메인 페이지
     * */
    
    @GetMapping("/community")
    public String communityMain(@RequestParam(required = false) String type,
								@RequestParam(required = false) Long id,
								Model model) {

		//모든 게시글 가져오기
		List<Post> postList = communityService.getAllPosts();

		//게시글 타입(Review, Chat)에 따라 게시글 가져오기
		List<Post> postTypeList = communityService.getAllPostsByType(type);

		//인기 게시글 리스트 가져오기
        List<Post> bestPostList = communityService.getBestPostDesc();
        
        //댓글 개수 가져오기
        
        model.addAttribute("post", postList);
        model.addAttribute("typePost", postTypeList);
        model.addAttribute("bestPost", bestPostList);

        return "communityMain"; // 반환할 뷰의 이름
    }
    
    
    /*
     * 커뮤니티 상세 페이지
     * */

    @GetMapping("/community/{id}")
    public String communityDetail(@PathVariable Long id, Model model) {
        //id에 따라 게시글 가져오기
        Post post = communityService.getPostById(id);

        //post_id에 따라 댓글 가져오기
        List<Reply> replies = communityService.findReplyByPostId(post.getId());

        //post_id와 reply_id에 따라 대댓글 가져오기
        List<ReReply> rereplies = new ArrayList<>();
        for (Reply reply : replies) {
			List<ReReply> rerepliesForReply = communityService.getReReplyByPostIdAndReplyId(post.getId(), reply.getId());
            rereplies.addAll(rerepliesForReply);
        }
        
        //post id에 따라 등록된 뮤지컬 정보 가져오기
        Review review = communityService.getReviewByPostId(post.getId());
        
        
        // 조회수 +1
        communityService.incrementViewCount(post);

        model.addAttribute("posts", post);
        model.addAttribute("reply", replies);
        model.addAttribute("rereply", rereplies);
        model.addAttribute("reviews", review);

        return "communityDetail";
	}


	@RequestMapping(value = "/community/delete/post/{id}", method = RequestMethod.DELETE)
	public String deletePost(@PathVariable Long id) {
		String view = "error";

		boolean postResult = false;
		boolean replyResult = false;
		boolean rereplyResult = false;

		try {
			postResult = communityService.deletePostById(id);
			replyResult = communityService.deleteReplyById(id);
			rereplyResult = communityService.deleteRereplyById(id);

			if (postResult) {
				view = "redirect:http://localhost:8081/community";
				return view;
			}

		} catch (Exception e) {
			return view;

		}
		return view;
	}


	/*
	   * 커뮤니티 검색 결과 페이지
	   * */
	@GetMapping("/community/search")
	public String getPostByKeyword(@RequestParam(name = "keyword") String keyword, Model model) {
		List<Post> postList = communityService.getPostByKeyword(keyword);
		model.addAttribute("searchResults", postList);
		return "communitySearchResult";
	}

	/*
	 	* 커뮤니티 대댓글 페이지
	 	* */

	@GetMapping("/community/{id}/reply/{replyId}")
	public String replyDetail(@PathVariable Long id, @PathVariable Long replyId, Model model) {
		// id에 따라 게시글 가져오기
		Post post = communityService.getPostById(id);

		// replyId에 따라 댓글 하나만 가져오기
		Reply reply = communityService.getReplyById(replyId);

		// post_id와 reply_id에 따라 대댓글 가져오기
		List<ReReply> rereplies = communityService.getReReplyByPostIdAndReplyId(post.getId(), replyId);

		model.addAttribute("reply", reply);
		model.addAttribute("rereply", rereplies);

		return "rereplyDetail";
	}


	/*
		* 게시글 수정 페이지
		* */
	@RequestMapping(value = "community/update/{id}", method = RequestMethod.GET)
	public String updatePostById(@PathVariable Long id, Model model) {
		Post post = communityService.getPostById(id);

		model.addAttribute("posts", post);

		return "updatePost";
	}

	@RequestMapping(value = "community/{id}", method = RequestMethod.PUT)
	public String updatePost(@PathVariable Long id,
							 @ModelAttribute("type") String type,
							 @ModelAttribute("title") String title,
							 @ModelAttribute("content") String content) {

		String view = "error";

		Post post = communityService.getPostById(id);
		post.setType(type);
		// title 값이 빈 문자열인 경우 null로 처리
		post.setTitle(title.isEmpty() ? null : title);

		// content 값이 빈 문자열인 경우 null로 처리
		post.setContent(content.isEmpty() ? null : content);

		boolean postResult = false;
		try {
			postResult = communityService.updatePost(id, type, title, content);
			if (postResult) {
				view = "redirect:/community/" + post.getId();
				return view;
			}
		} catch (Exception e) {
			return view;
		}
		return view;
	}

	/*
		 * 게시글 작성 페이지
		 * */
	@RequestMapping(value="/community/board", method=RequestMethod.GET)
	public String insertPostForm(Model model) {
		List<Musical> musicals = MusicalService.getAllMusical();

		model.addAttribute("musicals",musicals);

		return "registerPost";
	}

	@PostMapping("/community/board")
	public String insertPost(@ModelAttribute Post post, @RequestParam(value = "images", required = false) List<MultipartFile> images,HttpSession session) {
		String userId = (String) session.getAttribute("userId");
		try {

			// User 객체의 ID 설정
			User user = userService.findById(userId);

			List<String> imagePaths = new ArrayList<>();

			if (images != null && !images.isEmpty()) {
				for (MultipartFile imageFile : images) {
					String imagePath = communityService.saveImage(imageFile);
					imagePaths.add(imagePath);
				}
			}

			boolean isPostSaved = communityService.savePost(user, post.getType(), post.getTitle(), post.getContent(), imagePaths);

			if (!isPostSaved) {
				return "errorPage";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "errorPage";
		}

		return "redirect:/community";
	}



    /*
     * 댓글 관련 기능
     * */

	// 댓글 수정 페이지를 렌더링하는 GET 메서드 추가
	@RequestMapping(value = "/community/update/reply/{id}", method = RequestMethod.GET)
	public String showUpdateCommentPage(@PathVariable Long id, Model model) {
		Reply reply = communityService.getReplyById(id);
		model.addAttribute("reply", reply);
		return "updateComment";
	}

	// 댓글을 업데이트하는 POST 메서드 추가
	@RequestMapping(value = "/community/update/reply/{id}", method = RequestMethod.POST)
	public String updateComment(@PathVariable Long id,
								@ModelAttribute("replyToUpdate") Reply updatedReply) {
		String view = "error";

		boolean result = false;

		Reply reply = communityService.getReplyById(id);
		reply.setContent(updatedReply.getContent());

		try {
			result = communityService.updateReply(id, updatedReply.getContent());

			if (result) {
				return "redirect:/community/" + reply.getPost().getId();
			}
		} catch (Exception e) {
			return view;
		}

		return view;
	}

	// 댓글 삽입
	@RequestMapping(value = "/community/insert/reply/{postId}", method = RequestMethod.POST)
	public String insertReply(@PathVariable String postId, @RequestParam String content, HttpSession Session) {
		Post post = new Post();
		post.setId(Long.parseLong(postId));

		String userId = Session.getAttribute("userId").toString();
		User user = new User();
		user.setId(userId);

		Reply reply = Reply.builder()
				.post(post)
				.user(user)
				.content(content)
				.createdTime(new Date())
				.build();

		if(communityService.insertReply(reply)) {
			return "redirect:/community/" + postId;
		}

		return "error";
	}

	//댓글 삭제
	@RequestMapping(value="/community/delete/reply/{id}", method=RequestMethod.DELETE)
	public String deleteReply(@PathVariable Long id) {
		String view = "error";

		boolean replyResult = false;
		boolean rereplyResult = false;

		Reply reply = communityService.getReplyById(id);

		try {
			// 댓글 삭제 시 대댓글도 같이 삭제

			replyResult = communityService.deleteReplyById(id);
			rereplyResult = communityService.deleteRereplyById(id);

			if(replyResult && rereplyResult) {
				view ="redirect:/community/" + reply.getPost().getId();
				return view;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return view;
		}

		return view;
	}


	/*
	      * 대댓글 관련 기능
	      * */

	//대댓글 수정 페이지를 랜더링
	@RequestMapping(value = "/community/update/rereply/{id}", method = RequestMethod.GET)
	public String showUpdaterecommentPage(@PathVariable Long id, Model model) {
		ReReply rereply = communityService.getRereplyById(id);
		model.addAttribute("rereply", rereply);
		return "updateRecomment";
	}

	// 대댓글을 업데이트하는 POST 메서드 추가
	@RequestMapping(value = "/community/update/rereply/{id}", method = RequestMethod.POST)
	public String updateRecomment(@PathVariable Long id,
								  @ModelAttribute("rereplyToUpdate") ReReply updatedRereply) {
		String view = "error";

		boolean result = false;

		ReReply rereply = communityService.getRereplyById(id);
		rereply.setContent(updatedRereply.getContent());

		try {
			result = communityService.updateRereply(id, updatedRereply.getContent());

			if (result) {
				return "redirect:/community/" + rereply.getPost().getId();
			}
		} catch (Exception e) {
			return view;
		}

		return view;
	}


	//대댓글 삽입
	@RequestMapping(value = "/community/insert/rereply/{postId}/{replyId}", method = RequestMethod.POST)
	public String insertReReply(@PathVariable String postId, @PathVariable String replyId,  @RequestParam String content, HttpSession Session) {
		Post post = new Post();
		post.setId(Long.parseLong(postId));

		String userId = Session.getAttribute("userId").toString();
		User user = new User();
		user.setId(userId);

		Reply reply  = new Reply();
		reply.setId(Long.parseLong(replyId));

		ReReply reReply = ReReply.builder()
				.post(post)
				.reply(reply)
				.user(user)
				.content(content)
				.createdTime(new Date())
				.build();

		if(communityService.insertRereply(reReply)) {
			return "redirect:/community/" + postId;
		}

		return "error";
	}


	//대댓글 삭제
	@RequestMapping(value = "/community/delete/rereply/{id}", method = RequestMethod.DELETE)
	public String deleteRereply(@PathVariable Long id) {
		String view = "error";

		boolean rereplyResult = false;

		ReReply rereply = communityService.getRereplyById(id);

		try {
			rereplyResult = communityService.deleteRereplyById(id);

			if(rereplyResult) {
				view ="redirect:/community/" + rereply.getPost().getId();
				return view;
			}

		} catch (Exception e) {
			return view;

		}
		return view;
	}


	/*
		     * 좋아요 기능
		     * */

	// 게시글 좋아요 삽입
	@RequestMapping(value = "/community/postlike/{postId}", method = RequestMethod.POST)
	public String insertPostLike(@RequestParam String postId, HttpSession session) {
		// 세션에서 userId 가져오기
		String userId = session.getAttribute("userId").toString();

		// PostLike 객체 생성
		PostLike postlike = new PostLike();

		// Post 객체 설정 (PostLike 엔티티에서 Post 참조 사용)
		Post post = new Post();
		post.setId(Long.parseLong(postId));
		postlike.setPost(post);

		// User 객체 설정
		User user = new User();
		user.setId(userId);
		postlike.setUser(user);

		// PostLike 삽입
		if (communityService.insertPostLike(postlike)) {
			return "redirect:/community/" + postId;
		}

		return "error";
	}

	//좋아요 취소 -> 좋아요 데이터 삭제
	@RequestMapping(value="/community/delete/postlike/{id}", method=RequestMethod.DELETE)
	public String deletePostlike(@PathVariable Long id) {
		String view = "error";

		boolean postlikeResult = false;

		PostLike postlike = communityService.getpostlikeById(id);

		try {

			postlikeResult = communityService.deletePostlike(id);

			if(postlikeResult) {
				view ="redirect:/community/" + postlike.getPost().getId();
				return view;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return view;
		}

		return view;
	}


}