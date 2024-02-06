package jp.ac.morijyobi.sns_app.mapper;

import jp.ac.morijyobi.sns_app.bean.dto.SelectPostDTO;
import jp.ac.morijyobi.sns_app.bean.entity.Post;
import jp.ac.morijyobi.sns_app.bean.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PostMapper {

    @Select("SELECT p.id , p.user_id , u.name , p.post , p.like_num , p.created_at " +
            "FROM post_sns_app AS p JOIN user_sns_app AS u ON p.user_id = u.id " +
            "WHERE u.id IN (SELECT f.followid FROM follow_sns_app AS f WHERE f.followerid = #{userId}) " +
            "or u.id = #{userId} " +
            "ORDER BY p.created_at DESC")
    List<SelectPostDTO> selectPostByFollowIdOrUserId(int userId);

    @Insert("INSERT INTO post_sns_app VALUES (default,#{post},-1,#{userId},0,CURRENT_TIMESTAMP)")
    void insertPost(Post post);


    @Select("SELECT p.id,p.user_id,u.name ,p.post,p.like_num , p.created_at "+
            "FROM post_sns_app AS p JOIN user_sns_app AS u ON p.user_id = u.id "+
            "WHERE p.post LIKE CONCAT('%',#{keyword},'%') " +
            "ORDER BY p.created_at DESC ")
    List<SelectPostDTO> selectPostByKeyword(String keyword);

    @Select("SELECT p.id, p.user_id, u.name, p.post, p.like_num, p.created_at "+
            "FROM post_sns_app AS p JOIN user_sns_app AS u ON p.user_id = u.id "+
            "WHERE u.id = #{userId}")
    List<SelectPostDTO> selectPostByUserId(int userId);

}
