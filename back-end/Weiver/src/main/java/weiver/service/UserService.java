package weiver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weiver.dto.UserDTO;
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

    @Autowired
    private SubscribeRepository subscribeRepository;


    // 전체 조회
    public void test() {
        List<User> result = userRepository.findAll();

        System.out.println(result);
    }
    
    // 유저 Id로 조회   
    public User findById(String id) {
        User result = userRepository.getUserById(id);

        return result;
    }
    
    // 유저가 쓴 게시글 조회
    public void findPostsByUserId(String id) {
        // 게시글 리스트
        List<Post> result = communityRepository.findByUserId(id);
        // 게시글 개수
        int countresult = communityRepository.countByUserId(id);

        System.out.println(result);
        System.out.println(countresult);
    }

    // 유저가 쓴 댓글 조회
    public void findRepliesByUserId(String id) {
        // 댓글 리스트
        List<Reply> replyResult = replyRepository.findByUserId(id);
        // 대댓글 리스트
        List<ReReply> reReplyResult = reReplyRepository.findByUserId(id);
        // 댓글 개수 합계
        int countResult = replyRepository.countByUserId(id) + reReplyRepository.countByUserId(id);

        System.out.println(replyResult);
        System.out.println(reReplyResult);
        System.out.println(countResult);
    }

    // 유저가 좋아요 누른 게시글 조회
    public void findPostLikeByUserId(String id) {
        // 좋아요누른 ID 기준으로 postId 리스트 조회
        List<PostLike> postIdList = postLikeRepository.findByUserId(id);

        // 게시글 리스트 매핑
        for (PostLike postLike : postIdList) {
            Long postId = postLike.getPostId();
            Optional<Post> result = communityRepository.findById(postId);

            System.out.println(result);
        }

    }

    // 유저가 찜하거나 봤던 뮤지컬 조회
    public void findSubscribe(String id, String type) {
//        List<String> result = subscribeRepository.findMusicalIdByUserIdAndType(id, type);
//        int countresult = subscribeRepository.countByUserIdAndType(id, type);
//
//        System.out.println(result);
//        System.out.println(countresult);
    }

    // 유저 정보 수정(사진)
    public boolean updateImg(String profileImg, String id) throws Exception {
        int result = userRepository.updateImgById(profileImg, id);
        if(result == 1) {
            return true;
        }
        return false;
    }

    // 유저 정보 수정(이름)
    public boolean updateName(String nickname, String id) throws Exception {
        int result =  userRepository.updateNameById(nickname, id);
        if(result == 1) {
            return true;
        }
        return false;
    }

    // 유저 정보 수정(비밀번호 암호화)
//    public boolean updateBcryptPassword(String password, String id) throws Exception {
//        String user_password = userRepository.getUserById(id).getPassword();
//        boolean result = passwordEncoder.matches(password,user_password);
//
//        if(!result) {
//            String bcryptPassword = passwordEncoder.encode(password);
//            int updateResult = userRepository.updatePasswordById(bcryptPassword, id);
//            if(updateResult == 1) {
//                return true;
//            }
//        }
//
//        return false;
//    }


    // 마이페이지 정보 출력
    public UserDTO userInfo(String id) {
        User user = userRepository.getUserById(id);

        return UserDTO.builder()
                .userId(id)
                .nickname(user.getNickname())
                .profileImg(user.getProfileImg())
                .countJjim(subscribeRepository.countByUserIdAndType(id,"찜"))
                .countIsWatched(subscribeRepository.countByUserIdAndType(id,"봤어요"))
                .countPosts(communityRepository.countByUserId(id))
                .countReplies(replyRepository.countByUserId(id) + reReplyRepository.countByUserId(id))
                .countPostLikes(postLikeRepository.countByUserId(id)).build();
    }

}

