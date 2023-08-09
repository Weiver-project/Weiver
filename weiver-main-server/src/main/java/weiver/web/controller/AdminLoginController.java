package weiver.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import weiver.domain.entity.Admin;
import weiver.service.AdminService;

import javax.servlet.http.HttpSession;

@RestController
@Slf4j
public class AdminLoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AdminService adminService;

    // 회원가입
    @RequestMapping(value = "/ad/signupTest", method = RequestMethod.POST)
    public ResponseEntity<String> signup(@RequestParam("adminId") String adminId,
                                         @RequestParam("adminPw") String adminPw,
                                         @RequestParam("adminPwCheck") String adminPwCheck,
                                         @RequestParam("adminName") String adminName) {

        boolean adminIdExists = adminService.checkUserExists(adminId);

        // 유저 id 중복 확인
        if (adminIdExists) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 아이디입니다.");
        }

        // 패스워드, 패스워드 확인 체크
        if(!adminPw.equals(adminPwCheck)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("입력하신 비밀번호와 확인 비밀번호가 다릅니다.");
        }

        // 회원 가입 try - catch
        try {
            boolean result = adminService.saveAdmin(adminId, adminPw, adminName);

            if (result) {
                return ResponseEntity.ok("회원가입이 완료되었습니다.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("LoginContorller : 회원가입 중 문제 발생");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부 에러가 발생했습니다.");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 가입 중 에러가 발생했습니다.");
    }

    // 로그인
    @RequestMapping(value =  "/ad/loginTest", method = RequestMethod.POST)
    public ResponseEntity<String> loginTest(@RequestParam(value = "adminId") String adminId,
                                            @RequestParam(value = "adminPw") String adminPw,
                                            HttpSession session) {
        logger.info("요청 아이디 : " + adminId);
        logger.info("요청 비밀번호 : " + adminPw);

        try {
            if(adminId == null || adminId.isEmpty() || adminPw == null || adminPw.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("아이디, 비밀번호를 입력해주세요");
            }

            Admin admin = adminService.loginTest(adminId, adminPw);

            session.setAttribute("adminId", admin.getId());
            session.setAttribute("adminName", admin.getName());

            logger.info("로그인 컨트롤러 DB 호출 ID : " + admin.getId());
            logger.info("로그인 컨트롤러 DB 호출 PW : " + admin.getPassword());
            return ResponseEntity.ok("로그인에 성공했습니다.");

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("로그인 인증 실패");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호가 틀렸습니다.");
        }

    }
}
