package jp.ac.morijyobi.sns_app.service.impl;

import jp.ac.morijyobi.sns_app.bean.dto.SelectPostDTO;
import jp.ac.morijyobi.sns_app.bean.entity.Post;
import jp.ac.morijyobi.sns_app.bean.entity.User;
import jp.ac.morijyobi.sns_app.bean.form.PostForm;
import jp.ac.morijyobi.sns_app.mapper.FollowMapper;
import jp.ac.morijyobi.sns_app.mapper.PostMapper;
import jp.ac.morijyobi.sns_app.mapper.UsersMapper;
import jp.ac.morijyobi.sns_app.service.SnsService;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SnsServiceImpl implements SnsService {

    private final PostMapper postMapper;
    private final UsersMapper usersMapper;


    public SnsServiceImpl(PostMapper postMapper,UsersMapper usersMapper){
        this.postMapper = postMapper;
        this.usersMapper = usersMapper;
    }

    @Override
    public List<SelectPostDTO> getTimeLineByUser(UserDetails userDetails) {
        int userId = usersMapper.selectUserByMail(userDetails.getUsername()).getId();
        return postMapper.selectPostByFollowIdOrUserId(userId);
    }

    @Override
    public void registerPost(PostForm postForm,UserDetails userDetails) {
        int userId = usersMapper.selectUserByMail(userDetails.getUsername()).getId();
        Post post = new Post();
        post.setPost(postForm.getPost());
        post.setUserId(userId);

        postMapper.insertPost(post);
    }


    @Override
    public List<SelectPostDTO> getPostByKeyword(String keyword) {
        return postMapper.selectPostByKeyword(keyword);
    }

    @Override
    public List<SelectPostDTO> getPostByUserId(int userId) {
        return postMapper.selectPostByUserId(userId);
    }

    @Override
    public int getLoginId(UserDetails userDetails) {
        return usersMapper.selectUserByMail(userDetails.getUsername()).getId();
    }

}
