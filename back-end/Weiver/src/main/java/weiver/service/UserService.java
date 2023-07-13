package weiver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weiver.entity.*;
import weiver.repository.*;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommunityRepository communityRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private ReReplyRepository reReplyRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    // 전체 조회
    public void test() {
        List<User> result = userRepository.findAll();
        System.out.println(result);
    }
    
    // 유저 Id로 조회   
    public void findById(String id) {
        Optional<User> result = userRepository.findById(id);
        System.out.println(result);
    }
    
    // 유저가 쓴 게시글 조회
    public void findPostsByUserId(String id) {
        List<Post> result = communityRepository.findByUserId(id);
        int countresult = communityRepository.countByUserId(id);
        System.out.println(result);
        System.out.println(countresult);
    }

    // 유저가 쓴 댓글 조회
    public void findRepliesByUserId(String id) {
        List<Reply> replyResult = replyRepository.findByUserId(id);
        List<ReReply> reReplyResult = reReplyRepository.findByUserId(id);
        int countResult = replyRepository.countByUserId(id) + reReplyRepository.countByUserId(id);
        System.out.println(replyResult);
        System.out.println(reReplyResult);
        System.out.println(countResult);
    }

    // 유저가 좋아요 누른 게시글 조회
    public void findPostLikeByUserId(String id) {
        List<Long> postIdList = postLikeRepository.findPostIdByUserId(id);
        System.out.println(postIdList);
//        for (Post post : postIdList) {
//            Long postId = post.getId();
//            System.out.println(communityRepository.findById(postId));
//        }
    }
    
    // 유저 정보 수정(사진, 이름)
    public void updateInfo(String nickname, String profileImg, String id) {
        userRepository.updateInfoById(nickname, profileImg, id);
        System.out.println(userRepository.findById(id));
    }
    
    // 유저 정보 수정(비밀번호)
    public void updatePassword(String password, String id) {
        User user = userRepository.findPasswordById(id);
        String result = user.getPassword();
        if(!result.equals(password)) {
            userRepository.updatePasswordById(password, id);
            System.out.println(userRepository.findById(id));
        }
    }
    
    
    
}
