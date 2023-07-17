package weiver.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import weiver.entity.Post;
import weiver.entity.ReReply;
import weiver.entity.Reply;
import weiver.entity.User;
import weiver.service.CommunityService;

@Controller
public class CommunityController {

    private final CommunityService communityService;

    @Autowired
    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }
    
    
    /*
     * 게시글 관련
     * */
    
    //게시글 전체 가져오기
    @GetMapping("/post")
    public String getAllPosts(Model model) {
        List<Post> postList = communityService.getAllPosts();
        model.addAttribute("posts", postList);

        return "posts"; // 반환할 뷰의 이름
    }
    
    //게시글 type에 따라 게시글 조회
    @GetMapping("/post/type/{type}")
    public String getAllPostsByType(@PathVariable String type,
    								Model model) {
        List<Post> postList = communityService.getAllPostsByType(type);
        model.addAttribute("posts", postList);

        return "posts"; // 반환할 뷰의 이름
    }
    
    //조회수 별로 인기 커뮤니티 글 가져오기
    @GetMapping("/post/best")
    public String getBestPostDesc(Model model) {
        List<Post> postList = communityService.getBestPostDesc();
        model.addAttribute("posts", postList);

        return "posts"; // 반환할 뷰의 이름
    }

    //id에 따라 게시글 조회하기
    @RequestMapping(value = "/post/{id}", method = RequestMethod.GET)
    public String getPostById(@PathVariable Long id, Model model) {  	
    	Post post = communityService.getPostById(id);
    	
    	model.addAttribute("posts", post);
    	
    	return "posts";
    }
    
    //title, content 키워드 별로 게시글 조회하기
    @GetMapping("/search")
    public String getPostByKeyword(@RequestParam(required = false) String keyword, Model model) {

        List<Post> postList = communityService.getPostByKeyword(keyword);

        model.addAttribute("posts", postList);

        return "posts"; // 반환할 뷰의 이름
    }
    
    //게시글 수정
    @RequestMapping(value = "/test/{id}", method = RequestMethod.GET)
    public String updatePostById(@PathVariable Long id, Model model) {  	
    	Post post = communityService.getPostById(id);
    	
    	model.addAttribute("posts", post);
    	
    	return "posts";
    }
    
    @RequestMapping(value = "/test/{id}/update", method = RequestMethod.PUT)
    public void updatePost(@PathVariable Long id,
                           @ModelAttribute("type") String type,
                           @ModelAttribute("title") String title,
                           @ModelAttribute("content") String content) {
        Post post = communityService.getPostById(id);
        post.setType(type);
        post.setTitle(title);
        
        // content 값이 null인 경우 빈 문자열로 처리
        post.setContent(content != null ? content : "");
        
        try {
            boolean result = communityService.updatePost(id, type, title, content);
            if (result) {
                System.out.println("글 수정 성공");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //게시글 삭제
    @RequestMapping(value = "/post/{id}", method = RequestMethod.DELETE)
	public void deletePost(@PathVariable Long id) {
		String view = "error";
		
		boolean result = false;
		
		try {
			result = communityService.deletePostById(id);
			
			if(result) {
				
				System.out.println("삭제 성공");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		
		}
		
		
	}


    /*
     * 댓글 관련 기능
     * */

	 // 댓글 삽입 : 개발 중
	    @RequestMapping(value = "/reply", method = RequestMethod.POST)
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
	    
	    //댓글 수정
	     @RequestMapping(value = "/reply/{id}/update", method = RequestMethod.PUT)
	     public void updateReply(@PathVariable Long id,
	                            @ModelAttribute("content") String content) {
	         Reply reply = communityService.getReplyById(id);
	         reply.setContent(content);
	         
	         try {
	             boolean result = communityService.updateReply(id, content);
	             if (result) {
	                 System.out.println("글 수정 성공");
	             }
	         } catch (Exception e) {
	             e.printStackTrace();
	         }
	     }
	    
	    //댓글 삭제
	     @RequestMapping(value = "/reply/{id}", method = RequestMethod.DELETE)
	 	 public void deleteReply(@PathVariable Long id) {
	 		String view = "error";
	 		
	 		boolean result = false;
	 		
	 		try {
	 			result = communityService.deleteReplyById(id);
	 			
	 			if(result) {
	 				
	 				System.out.println("삭제 성공");
	 			}
	 			
	 		} catch (Exception e) {
	 			e.printStackTrace();
	 		
	 		}
	     }
	     
	     
	     /*
	      * 대댓글 관련 기능
	      * */
	     
	     
	     //대댓글 수정
	     @RequestMapping(value = "/rereply/{id}/update", method = RequestMethod.PUT)
	     public void updateRereply(@PathVariable Long id,
	                               @ModelAttribute("content") String content) {
	         ReReply rereply = communityService.getRereplyById(id);
	         rereply.setContent(content);
	         
	         try {
	             boolean result = communityService.updateRereply(id, content);
	             if (result) {
	                 System.out.println("글 수정 성공");
	             }
	         } catch (Exception e) {
	             e.printStackTrace();
	         }
	     }
	     
	     //대댓글 삭제
	     @RequestMapping(value = "/rereply/{id}", method = RequestMethod.DELETE)
	 	 public void deleteRereply(@PathVariable Long id) {
	 		String view = "error";
	 		
	 		boolean result = false;
	 		
	 		try {
	 			result = communityService.deleteRereplyById(id);
	 			
	 			if(result) {
	 				
	 				System.out.println("삭제 성공");
	 			}
	 			
	 		} catch (Exception e) {
	 			e.printStackTrace();
	 		
	 		}
	     }

    







    
    
    
}
