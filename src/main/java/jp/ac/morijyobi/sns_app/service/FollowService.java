package jp.ac.morijyobi.sns_app.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface FollowService {
    void registerFollow(int userId, UserDetails userDetails);

    void deleteFollow(int followId,UserDetails userDetails);
}
