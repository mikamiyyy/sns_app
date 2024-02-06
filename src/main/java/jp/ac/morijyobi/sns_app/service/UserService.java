package jp.ac.morijyobi.sns_app.service;

import jp.ac.morijyobi.sns_app.bean.dto.UserProfileDTO;
import jp.ac.morijyobi.sns_app.bean.entity.User;
import jp.ac.morijyobi.sns_app.bean.form.UserForm;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    User registerUser(UserForm userForm);
    List<User> getUserByKeyword(String keyword);

    UserProfileDTO getUserById(int userId,UserDetails userDetails);

    List<User> getAllUser();

    List<User> getUserByKeywordAdmin(String keyword);

    void accountLock(int userId);
    void accountUnLocked(int userId);
    List<User> getAccountLockUser();

    List<User> getFollowUser(int userId);

    List<User> getFollowerUser(int userId);
}
