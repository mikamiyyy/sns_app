package jp.ac.morijyobi.sns_app.service.impl;

import jp.ac.morijyobi.sns_app.bean.dto.UserProfileDTO;
import jp.ac.morijyobi.sns_app.bean.entity.User;
import jp.ac.morijyobi.sns_app.bean.form.UserForm;
import jp.ac.morijyobi.sns_app.constants.AccountRoleConstants;
import jp.ac.morijyobi.sns_app.mapper.FollowMapper;
import jp.ac.morijyobi.sns_app.mapper.UsersMapper;
import jp.ac.morijyobi.sns_app.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UsersMapper usersMapper;
    private final PasswordEncoder passwordEncoder;
    private final FollowMapper followMapper;

    public UserServiceImpl(UsersMapper usersMapper,PasswordEncoder passwordEncoder,FollowMapper followMapper){
        this.usersMapper = usersMapper;
        this.passwordEncoder = passwordEncoder;
        this.followMapper = followMapper;
    }

    @Override
    public User registerUser(UserForm userForm) {
        User user = new User();
        user.setName(userForm.getName());
        user.setMail(userForm.getMail());
        String hashedPassword = passwordEncoder.encode(userForm.getPassword());
        user.setPassword(hashedPassword);
        user.setAuthorityId(AccountRoleConstants.GENERAL);
        user.setAccountLocked(true);

        usersMapper.insertUser(user);

        return null;
    }

    @Override
    public List<User> getUserByKeyword(String keyword) {;
        return usersMapper.selectUserByKeyword(keyword);
    }

    @Override
    public UserProfileDTO getUserById(int userId, UserDetails userDetails) {
        int loginId = usersMapper.selectUserByMail(userDetails.getUsername()).getId();
        UserProfileDTO userProfileDTO = usersMapper.selectUserById(userId);
        userProfileDTO.setLoginId(loginId);
        userProfileDTO.setUserId(userId);
        if(followMapper.selectFollowed(userId,loginId) != null){
            userProfileDTO.setFollowed(true);
        }
        return userProfileDTO;
    }

    @Override
    public List<User> getAllUser() {
        return usersMapper.selectAllUser();
    }

    @Override
    public List<User> getUserByKeywordAdmin(String keyword) {
        return usersMapper.selectUserByKeywordAdmin(keyword);
    }

    @Override
    public void accountLock(int userId) {
        usersMapper.accountLock(userId);
    }

    @Override
    public void accountUnLocked(int userId) {
        usersMapper.accountUnLocked(userId);
    }

    @Override
    public List<User> getAccountLockUser() {
        return usersMapper.selectAccountLockUser();
    }

    @Override
    public List<User> getFollowUser(int userId) {
        return usersMapper.selectFollowUser(userId);
    }

    @Override
    public List<User> getFollowerUser(int userId) {
        return usersMapper.selectFollowerUser(userId);
    }
}
