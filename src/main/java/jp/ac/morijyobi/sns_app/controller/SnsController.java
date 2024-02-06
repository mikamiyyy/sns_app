package jp.ac.morijyobi.sns_app.controller;

import jp.ac.morijyobi.sns_app.bean.dto.SelectPostDTO;
import jp.ac.morijyobi.sns_app.bean.dto.UserProfileDTO;
import jp.ac.morijyobi.sns_app.bean.entity.User;
import jp.ac.morijyobi.sns_app.bean.form.PostForm;
import jp.ac.morijyobi.sns_app.mapper.FollowMapper;
import jp.ac.morijyobi.sns_app.service.FollowService;
import jp.ac.morijyobi.sns_app.service.SnsService;
import jp.ac.morijyobi.sns_app.service.UserService;
import jp.ac.morijyobi.sns_app.service.impl.SnsServiceImpl;
import org.apache.ibatis.annotations.Delete;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/sns")
public class SnsController {
    private final SnsService snsService;
    private final UserService userService;
    private final FollowService followService;

    public SnsController(SnsService snsService, UserService userService,FollowService followService){
        this.snsService = snsService;
        this.userService = userService;
        this.followService = followService;
    }

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal UserDetails userDetails,
                        Model model){
        int loginId = snsService.getLoginId(userDetails);
        model.addAttribute("loginId",loginId);
        List<SelectPostDTO> postList = snsService.getTimeLineByUser(userDetails);
        model.addAttribute("postList",postList);
        PostForm postForm = new PostForm();
        model.addAttribute("postForm",postForm);

        return "sns/home";
    }

    @PostMapping("/post")
    public String registerPost(@Validated PostForm postForm,
                               BindingResult bindingResult,
                               @AuthenticationPrincipal UserDetails userDetails,
                               Model model,
                               RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "sns/home";
        }

        snsService.registerPost(postForm,userDetails);

        return "redirect:/sns/home";
    }

    @GetMapping("/search")
    public String searchByKeyword(@RequestParam(defaultValue = "") String keyword,
                                    @AuthenticationPrincipal UserDetails userDetails,
                                    Model model){
        int loginId = snsService.getLoginId(userDetails);
        model.addAttribute("loginId",loginId);
        List<User> userList = userService.getUserByKeyword(keyword);
        List<SelectPostDTO> postList = snsService.getPostByKeyword(keyword);
        model.addAttribute("userList",userList);
        model.addAttribute("postList",postList);

        return "sns/search";
    }

    @GetMapping("/profile")
    public String getProfile(@RequestParam int userId,
                            @AuthenticationPrincipal UserDetails userDetails,
                            Model model){
        int loginId = snsService.getLoginId(userDetails);
        model.addAttribute("loginId",loginId);
        UserProfileDTO userProfileDTO = userService.getUserById(userId,userDetails);
        List<SelectPostDTO> postList = snsService.getPostByUserId(userId);
        model.addAttribute("user" , userProfileDTO);
        model.addAttribute("postList", postList);
        return "sns/profile";
    }

    @PostMapping("/follow")
    public String registerFollow(@RequestParam int followId,
                                 @AuthenticationPrincipal UserDetails userDetails,
                                 RedirectAttributes redirectAttributes,
                                 Model model){
        followService.registerFollow(followId,userDetails);

        redirectAttributes.addAttribute("userId",followId);
        return "redirect:/sns/profile";
    }

    @PostMapping("/unfollow")
    public String deleteFollow(@RequestParam int followId,
                               @AuthenticationPrincipal UserDetails userDetails,
                               RedirectAttributes redirectAttributes,
                               Model model){
        followService.deleteFollow(followId,userDetails);
        redirectAttributes.addAttribute("userId",followId);
        return "redirect:/sns/profile";
    }

    @GetMapping("/getFollow")
    public String getFollowUser(@RequestParam int userId,
                                Model model){
        List<User> userList =  userService.getFollowUser(userId);
        model.addAttribute("userList", userList);
        return "sns/follow-follower";
    }

    @GetMapping("/getFollower")
    public String getFollowerUser(@RequestParam int userId,
                                  Model model){
        List<User> userList = userService.getFollowerUser(userId);
        model.addAttribute("userList",userList);
        return "sns/follow-follower";
    }
}
