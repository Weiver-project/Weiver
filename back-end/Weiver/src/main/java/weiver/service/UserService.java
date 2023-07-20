package weiver.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weiver.dto.ReplyDTO;
import weiver.dto.UserDTO;
import weiver.entity.*;
import weiver.repository.*;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private ReplyLikeRepository replyLikeRepository;

    @Autowired
    private ReReplyLikeRepository reReplyLikeRepository;

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
    
    // 유저가 쓴 게시글 작성순 조회
    public List<Post> findPostsByUserIdTime(String id) {
        return communityRepository.findByUserIdOrderByCreatedTimeDesc(id);
    }

    // 유저가 쓴 게시글 개수
    public int countPostsByUserId(String id) {
        return communityRepository.countByUserId(id);
    }

    // 유저가 쓴 댓글/대댓글 조회
    public List<ReplyDTO> findRepliesByUserId(String id) {
        List<Reply> replyList = replyRepository.findByUserId(id);
        List<ReReply> reReplyList = reReplyRepository.findByUserId(id);
        List<ReplyDTO> replyDTOList = new ArrayList<>();

        for (Reply reply : replyList) {
            replyDTOList.add(ReplyDTO.builder()
                                        .id(reply.getId())
                                        .postId(reply.getPost().getId())
                                        .userId(reply.getUser().getId())
                                        .content(reply.getContent())
                                        .createdTime(reply.getCreatedTime())
                                        .countLikes(replyLikeRepository.count(reply.getId())).build());
        }

        for (ReReply reReply : reReplyList) {
            replyDTOList.add(ReplyDTO.builder()
                    .id(reReply.getId())
                    .postId(reReply.getPost().getId())
                    .userId(reReply.getUser().getId())
                    .content(reReply.getContent())
                    .createdTime(reReply.getCreatedTime())
                    .countLikes(reReplyLikeRepository.count(reReply.getId())).build());
        }

        return replyDTOList;
    }

    // 유저가 쓴 댓글 개수
    public int countRepliesByUserId(String id) {
        return replyRepository.countByUserId(id) + reReplyRepository.countByUserId(id);
    }

    // 유저가 좋아요 누른 게시글 조회
    public List<Post> findPostLikeByUserId(String id) {
        List<PostLike> postIdList = postLikeRepository.findByUserId(id);
        List<Post> postList = new ArrayList<>();

        for (PostLike postLike : postIdList) {

            Long postId = postLike.getPost().getId();
            Post result = communityRepository.getPostById(postId);

            postList.add(result);
        }

        return postList;
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
    public boolean updateBcryptPassword(String password, String id) throws Exception {
        String user_password = userRepository.getUserById(id).getPassword();
        boolean result = BCrypt.checkpw(password, user_password);
        String bcryptPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        if(!result) {
            int updateResult = userRepository.updatePasswordById(bcryptPassword, id);
            if(updateResult == 1) {
                return true;
            }
        }

        return false;
    }

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

