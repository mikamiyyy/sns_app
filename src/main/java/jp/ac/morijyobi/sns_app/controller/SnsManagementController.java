package jp.ac.morijyobi.sns_app.controller;

import jp.ac.morijyobi.sns_app.bean.dto.SelectPostDTO;
import jp.ac.morijyobi.sns_app.bean.entity.User;
import jp.ac.morijyobi.sns_app.service.SnsService;
import jp.ac.morijyobi.sns_app.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.swing.plaf.ListUI;
import java.util.List;

@Controller
@RequestMapping("/management")
public class SnsManagementController {

    private final UserService userService;

    public SnsManagementController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/allUser")
    public String getAllUser(Model model){
        List<User> userList = userService.getAllUser();
        model.addAttribute("userList",userList);
        return "sns/search";
    }

    @GetMapping("/LockedAccount")
    public String getAccountLocked(Model model){
        List<User> userList = userService.getAccountLockUser();
        model.addAttribute("userList",userList);
        return "sns/search";
    }

    @PostMapping("/accountLock")
    public String accountLock(@RequestParam int userId, Model model,
                              RedirectAttributes redirectAttributes){
        userService.accountLock(userId);
        return "redirect:/management/allUser";
    }

    @PostMapping("/accountUnLocked")
    public String accountUnLocked(@RequestParam int userId,Model model,
                                  RedirectAttributes redirectAttributes){
        userService.accountUnLocked(userId);
        return "redirect:/management/allUser";
    }

    @GetMapping("/search")
    public String searchByKeyword(@RequestParam(defaultValue = "") String keyword,
                                  @AuthenticationPrincipal UserDetails userDetails,
                                  Model model){
        List<User> userList = userService.getUserByKeywordAdmin(keyword);
        model.addAttribute("userList",userList);

        return "sns/search";
    }

}
