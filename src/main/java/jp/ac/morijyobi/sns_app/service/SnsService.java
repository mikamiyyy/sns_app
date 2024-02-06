package jp.ac.morijyobi.sns_app.service;

import jp.ac.morijyobi.sns_app.bean.dto.SelectPostDTO;
import jp.ac.morijyobi.sns_app.bean.entity.Post;
import jp.ac.morijyobi.sns_app.bean.entity.User;
import jp.ac.morijyobi.sns_app.bean.form.PostForm;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface SnsService {

 List<SelectPostDTO> getTimeLineByUser(UserDetails userDetails);

 void registerPost(PostForm postForm,UserDetails userDetails);


 List<SelectPostDTO> getPostByKeyword(String keyword);

 List<SelectPostDTO> getPostByUserId(int userId);

 int getLoginId(UserDetails userDetails);
}
