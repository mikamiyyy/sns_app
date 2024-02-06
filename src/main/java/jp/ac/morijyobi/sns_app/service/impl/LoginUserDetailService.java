package jp.ac.morijyobi.sns_app.service.impl;

import jp.ac.morijyobi.sns_app.bean.dto.LoginUserDto;
import jp.ac.morijyobi.sns_app.mapper.UsersMapper;
import jp.ac.morijyobi.sns_app.security.LoginUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginUserDetailService implements UserDetailsService {

    private final UsersMapper usersMapper;

    public LoginUserDetailService(UsersMapper usersMapper){
        this.usersMapper = usersMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        LoginUserDto user = usersMapper.selectUserByMail(mail);

        if(user == null){
            throw new UsernameNotFoundException("ユーザーが見つかりません。");
        }

        return new LoginUserDetails(user);
    }
}
