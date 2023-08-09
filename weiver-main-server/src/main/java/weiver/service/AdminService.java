package weiver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weiver.domain.entity.*;
import weiver.domain.repository.*;

import java.util.List;

@Service
public class AdminService {
    
    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private MusicalRepository musicalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private InquiryRepository inquiryRepository;

    /*============================        Login         ===================================*/

    // 아이디 중복 확인
    public boolean checkUserExists(String userId) {
        boolean result = adminRepository.existsById(userId);
        return result;
    }

    // 회원 가입
    @Transactional
    public boolean saveAdmin(String adminId, String adminPw, String name) throws Exception{
        Admin admin = Admin.builder()
                .id(adminId)
                .password(adminPw)
                .name(name)
                .build();

        Admin result = adminRepository.save(admin);

        if(!result.getId().isEmpty()) {
            return true;
        }

        return false;
    }

    // 로그인
    public Admin loginTest(String adminId, String adminPw) throws Exception{
        Admin admin = adminRepository.getAdminById(adminId);

        if(admin.getPassword().equals(adminPw)) {
            return admin;
        }

        return null;
    }

    // 회원 탈퇴
    public void removeAdmin(String adminId) throws Exception{
        adminRepository.deleteById(adminId);
    }
    
    // AdminId로 Admin 정보 가져오기
    public Admin getAdminById(String adminId) {
    	return adminRepository.getAdminById(adminId);
    }

    /*============================        Actor         ===================================*/
    
    // 전체 Actor 반환 : 전체 리스트를 볼 때 사용
    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }
    
    // 특정 Actor 반환 : 특정 Actor 정보를 볼 때 사용
    public Actor getActor(String actorId) {
        return actorRepository.getById(actorId);
    }
    
    // 특정 Actor 업데이트
    public void updateActor(Actor actor) {
        actorRepository.save(actor);
    }
    
    // 특정 Actor 삭제
    public void deleteActor(String actorId) {
        actorRepository.deleteById(actorId);
    }
    
    /*============================        Musical         ===================================*/

    // 전체 Musical 반환
    public List<Musical> getAllMusicals() {
        return musicalRepository.findAll();
    }
    
    // 특정 Musical 반환
    public Musical getMusical(String musicalId) {
        return musicalRepository.getMusicalById(musicalId);
    }
    
    // 특정 Musical 업데이트
    public void updateMusical(Musical musical) {
        musicalRepository.save(musical);
    }
    
    // 특정 Musical 삭제
    public void deleteMusical(String musicalId) {
        musicalRepository.deleteById(musicalId);
    }
    
    /*============================        User         ===================================*/
    
    // 전체 User 반환
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    // 특정 User 반환
    public User getUser(String userId) {
        return userRepository.getUserById(userId);
    }
    
    // 특정 User 업데이트
    public void updateUser(User user) {
        userRepository.save(user);
    }
    
    // 특정 User 삭제
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
    
    /*============================        Admin         ===================================*/
    
    // 전체 Admin 반환
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }
    
    // 특정 Admin 반환
    public Admin getAdmin(String adminId) {
        return adminRepository.getAdminById(adminId);
    }
    
    // 특정 Admin 업데이트
    public void updateAdmin(Admin admin) {
        adminRepository.save(admin);
    }
    
    // 특정 Admin 삭제
    public void deleteAdmin(String adminId) {
        adminRepository.deleteById(adminId);
    }
    
    
    /*============================        Post         ===================================*/
    
    // 전체 Post 반환
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
    
    // 특정 Post 반환
    public Post getPost(Long postId) {
        return postRepository.getPostById(postId);
    }
    
    // 특정 Post 업데이트
    public void updatePost(Post post) {
        postRepository.save(post);
    }
    
    // 특정 Post 삭제
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
    
    
    /*============================        Inquiry         ===================================*/
    
    // 전체 Inquiry 반환
    public List<Inquiry> getAllInquirys() {
        return inquiryRepository.findAll();
    }
    
    // 특정 Inquiry 반환
    public Inquiry getInquiry(Long inquiryId) {
        return inquiryRepository.getInquiryById(inquiryId);
    }
    
    // 특정 Inquiry 업데이트
    public void updateInquiry(Inquiry inquiry) {
        inquiryRepository.save(inquiry);
    }
    
    // 특정 Inquiry 삭제
    public void deleteInquiry(Long inquiryId) {
        inquiryRepository.deleteById(inquiryId);
    }
    
}

