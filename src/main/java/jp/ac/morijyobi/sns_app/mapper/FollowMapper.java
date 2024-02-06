package jp.ac.morijyobi.sns_app.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FollowMapper {

    @Insert("INSERT INTO follow_sns_app VALUES (default, #{followId},#{followerId})")
    void insertFollow(int followId, int followerId);

    @Delete("DELETE FROM follow_sns_app WHERE followid = #{followId} AND " +
            "followerid = #{followerId}")
    void deleteFollow(int followId, int followerId);

    @Select("SELECT id FROM follow_sns_app WHERE followid = #{userId} AND followerid = #{loginId}")
    Integer selectFollowed(int userId,int loginId);
}
