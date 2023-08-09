package weiver.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import weiver.domain.entity.*;
import weiver.service.AdminService;

import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/admin")
@Controller
public class AdminController {

    private final AdminService adminService;
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String getAdminMainPage(Model model) {
        model.addAttribute("inquiries", adminService.getAllInquirys());
        return "adminInquiries";
    }

    /*============================        Admin         ===================================*/

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getAdminLoginPage(HttpSession session) {
        if (session.getAttribute("adminId") != null) {
            return "redirect:/admin/main";
        }

        return "adminLogin";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String getSignUpPage() {
        return "adminSignup";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String Logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/admin/login";
    }

    @RequestMapping(value = "/signout", method = RequestMethod.GET)
    public String withdrawalAdmin(HttpSession session) throws Exception {
        String adminId = (String) session.getAttribute("adminId");

        adminService.removeAdmin(adminId);
        session.invalidate();

        return "redirect:/admin/main";
    }

    /*============================        Actor         ===================================*/
    @RequestMapping(value = "/getAllActors", method = RequestMethod.GET)
    public String getAllActors(Model model) {
        model.addAttribute("actors", adminService.getAllActors());

        return "adminActors";
    }

    @RequestMapping(value = "/getActorDetail/{actorId}", method = RequestMethod.GET)
    public String getActorDetail(@PathVariable String actorId, Model model) {
        model.addAttribute("actor", adminService.getActor(actorId));

        return "adminActorDetail";
    }

    @RequestMapping(value = "/updateActor", method = RequestMethod.GET)
    public String updateActor(@RequestParam Actor actor, Model model) {
        adminService.updateActor(actor);

        return "adminActors";
    }

    @RequestMapping(value = "/deleteActor/{actorId}", method = RequestMethod.GET)
    public String deleteActor(@PathVariable String actorId, Model model) {
        adminService.deleteActor(actorId);

        return "adminActors";
    }

    /*============================        Musical         ===================================*/

    @RequestMapping(value = "/getAllMusicals", method = RequestMethod.GET)
    public String getAllMusicals(Model model) {
        model.addAttribute("musicals", adminService.getAllMusicals());

        return "adminMusicals";
    }

    @RequestMapping(value = "/getMusicalDetail/{musicalId}", method = RequestMethod.GET)
    public String getMusicalDetail(@PathVariable String musicalId, Model model) {
        model.addAttribute("musical", adminService.getMusical(musicalId));

        return "adminMusicalDetail";
    }

    @RequestMapping(value = "/updateMusical", method = RequestMethod.GET)
    public String updateMusical(@RequestParam Musical musical, Model model) {
        adminService.updateMusical(musical);

        return "adminMusicals";
    }

    @RequestMapping(value = "/deleteMusical/{musicalId}", method = RequestMethod.GET)
    public String deleteMusical(@PathVariable String musicalId, Model model) {
        adminService.deleteMusical(musicalId);

        return "adminMusicals";
    }

    /*============================        User         ===================================*/
    @RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
    public String getAllUsers(Model model) {
        model.addAttribute("users", adminService.getAllUsers());

        return "adminUsers";
    }

    @RequestMapping(value = "/getUserDetail/{userId}", method = RequestMethod.GET)
    public String getUserDetail(@PathVariable String userId, Model model) {
        model.addAttribute("user", adminService.getUser(userId));

        return "adminUserDetail";
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.GET)
    public String updateUser(@RequestParam User user, Model model) {
        adminService.updateUser(user);

        return "adminUsers";
    }

    @RequestMapping(value = "/deleteUser/{userId}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable String userId, Model model) {
        adminService.deleteUser(userId);

        return "adminUsers";
    }

    /*============================        Admin         ===================================*/

    @RequestMapping(value = "/getAllAdmins", method = RequestMethod.GET)
    public String getAllAdmins(Model model) {
        model.addAttribute("admins", adminService.getAllAdmins());

        return "adminAdmins";
    }

    @RequestMapping(value = "/getAdminDetail/{adminId}", method = RequestMethod.GET)
    public String getAdminDetail(@PathVariable String adminId, Model model) {
        model.addAttribute("admin", adminService.getAdmin(adminId));

        return "adminAdminDetail";
    }

    @RequestMapping(value = "/updateAdmin", method = RequestMethod.GET)
    public String updateAdmin(@RequestParam Admin admin, Model model) {
        adminService.updateAdmin(admin);

        return "adminAdmins";
    }

    @RequestMapping(value = "/deleteAdmin/{adminId}", method = RequestMethod.GET)
    public String deleteAdmin(@PathVariable String adminId, Model model) {
        adminService.deleteAdmin(adminId);

        return "adminAdmins";
    }

    /*============================        Post         ===================================*/

    @RequestMapping(value = "/getAllPosts", method = RequestMethod.GET)
    public String getAllPosts(Model model) {
        model.addAttribute("posts", adminService.getAllPosts());

        return "adminPosts";
    }

    @RequestMapping(value = "/getPostDetail/{postId}", method = RequestMethod.GET)
    public String getPostDetail(@PathVariable Long postId, Model model) {
        model.addAttribute("post", adminService.getPost(postId));

        return "adminPostDetail";
    }

    @RequestMapping(value = "/updatePost", method = RequestMethod.GET)
    public String updatePost(@RequestParam Post post, Model model) {
        adminService.updatePost(post);

        // model.addAttribute("posts", adminService.getAllPosts());

        return "adminPosts";
    }

    @RequestMapping(value = "/deletePost/{postId}", method = RequestMethod.GET)
    public String deletePost(@PathVariable Long postId, Model model) {
        adminService.deletePost(postId);

        return "adminPosts";
    }

    /*============================        Inquiry         ===================================*/

    @RequestMapping(value = "/getAllInquirys", method = RequestMethod.GET)
    public String getAllInquirys(Model model) {
        model.addAttribute("inquirys", adminService.getAllInquirys());

        return "adminInquiries";
    }

    @RequestMapping(value = "/getInquiryDetail/{inquriyId}", method = RequestMethod.GET)
    public String getInquiryDetail(@PathVariable Long inquiryId, Model model) {
        model.addAttribute("inquiry", adminService.getInquiry(inquiryId));

        return "adminInquiryDetail";
    }

    @RequestMapping(value = "/updateInquiry", method = RequestMethod.GET)
    public String updateInquiry(@RequestParam Inquiry inquiry, Model model) {
        adminService.updateInquiry(inquiry);


        return "adminInquiries";
    }

    @RequestMapping(value = "/deleteInquiry/{inquiryId}", method = RequestMethod.GET)
    public String deleteInquiry(@PathVariable Long inquiryId, Model model) {
        adminService.deleteInquiry(inquiryId);

        return "adminInquiries";
    }
}

