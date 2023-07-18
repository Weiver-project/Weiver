package weiver.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import weiver.entity.Image;
import weiver.entity.Post;
import weiver.entity.PostLike;
import weiver.entity.ReReply;
import weiver.entity.Reply;
import weiver.service.CommunityService;

@Controller
public class CommunityController {

    private final CommunityService communityService;

    @Autowired
    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

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
        
        // 조회수 +1
        communityService.incrementViewCount(post);

        model.addAttribute("posts", post);
        model.addAttribute("reply", replies);
        model.addAttribute("rereply", rereplies);

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
		public String replyDetail(@PathVariable Long id, Model model) {
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

		       model.addAttribute("reply", replies);
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
		public String insertPostForm() {
			return "registerPost";
		}
		
		@RequestMapping(value="/community/board", method=RequestMethod.POST)
		public String insertPost(@ModelAttribute Post newPost,
					 @ModelAttribute Image newImage) {
			String view = "error";
			
			boolean postResult = false;
			boolean imageResult = false;
			
			try {
				postResult = communityService.insertPost(newPost);
				imageResult = communityService.insertImage(newImage);
				
				if(postResult && imageResult) {
					view = "redirect:/community";
					return view;
				}
				
			} catch (Exception e) {
				System.out.println("Exception occurred while inserting post or image: " + e.getMessage());

				return view;
			}
			
			return view;
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
	    @RequestMapping(value = "/community/insert/reply/{id}", method = RequestMethod.POST)
	    public void insertReply(@RequestBody Reply reply) {
	        String view = "error";
	        boolean result = false;
	        
	        try {
	            result = communityService.insertReply(reply);
	            
	            if (result) {
	                System.out.println("댓글 삽입 성공");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
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
	    

		 // 대댓글 삽입                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
		    @RequestMapping(value = "/community/insert/rereply/{id}", method = RequestMethod.POST)
		    public void insertReply(@RequestBody ReReply rereply) {
		        String view = "error";
		        boolean result = false;
		        
		        try {
		            result = communityService.insertRereply(rereply);
		            
		            if (result) {
		                System.out.println("대댓글 삽입 성공");
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
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
		    @RequestMapping(value = "/community/insert/postlike", method = RequestMethod.POST)
		    public ResponseEntity<String> insertPostLike(@RequestBody PostLike postlike) {
		        try {
		            // 서비스로부터 결과를 받아서 처리하도록 변경
		            boolean result = communityService.insertPostlike(postlike);

		            if (result) {
		                return new ResponseEntity<>("좋아요 데이터 삽입 성공", HttpStatus.OK);
		            } else {
		                return new ResponseEntity<>("좋아요 데이터 삽입 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		            return new ResponseEntity<>("서버 오류 발생", HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		    }


	    
	     
	     

    







    
    
    
}