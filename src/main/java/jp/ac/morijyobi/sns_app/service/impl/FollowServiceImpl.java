package jp.ac.morijyobi.sns_app.service.impl;

import jp.ac.morijyobi.sns_app.mapper.FollowMapper;
import jp.ac.morijyobi.sns_app.mapper.UsersMapper;
import jp.ac.morijyobi.sns_app.service.FollowService;
import jp.ac.morijyobi.sns_app.service.SnsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceImpl implements FollowService {

    private final UsersMapper usersMapper;
    private final FollowMapper followMapper;

    public FollowServiceImpl(UsersMapper usersMapper,FollowMapper followMapper){
        this.usersMapper = usersMapper;
        this.followMapper = followMapper;
    }

    @Override
    public void registerFollow(int userId, UserDetails userDetails) {
        int loginId = usersMapper.selectUserByMail(userDetails.getUsername()).getId();
        followMapper.insertFollow(userId,loginId);
    }

    @Override
    public void deleteFollow(int followId, UserDetails userDetails) {
        int followerId = usersMapper.selectUserByMail(userDetails.getUsername()).getId();
        followMapper.deleteFollow(followId,followerId);
    }

}
