package jp.ac.morijyobi.sns_app.mapper;

import jp.ac.morijyobi.sns_app.bean.dto.LoginUserDto;
import jp.ac.morijyobi.sns_app.bean.dto.UserProfileDTO;
import jp.ac.morijyobi.sns_app.bean.entity.User;
import jp.ac.morijyobi.sns_app.bean.form.UserForm;
import jp.ac.morijyobi.sns_app.bean.form.UserUpdateForm;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UsersMapper {

    @Select("SELECT u.id,u.name,u.mail,u.password,a.authority,u.account_locked "+
            "FROM user_sns_app AS u "+
            "INNER JOIN authorities_sns_app AS a ON a.id = u.authority_id "+
            "WHERE u.mail = #{mail}")
    LoginUserDto selectUserByMail(String mail);

    @Select("SELECT id , name FROM user_sns_app WHERE name LIKE CONCAT('%', #{keyword}, '%') AND authority_id != 2")
    List<User> selectUserByKeyword(String keyword);

    @Insert("INSERT INTO user_sns_app VALUES (default,#{name},#{mail},#{password}, " +
            "null, #{authorityId},#{accountLocked})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void insertUser(User user);

    @Select("SELECT u.id, u.name, u.profile, " +
            "COUNT(f2.id) AS followCount, " +
            "COUNT(f1.id) AS followerCount "+
            "FROM user_sns_app AS u "+
            "LEFT JOIN follow_sns_app AS f1 ON u.id = f1.followid " +
            "LEFT JOIN follow_sns_app AS f2 ON u.id = f2.followerid " +
            "WHERE u.id = #{userId} " +
            "GROUP BY u.id, u.name, u.profile")
    UserProfileDTO selectUserById(int userId);

    @Select("SELECT id, name,account_locked FROM user_sns_app WHERE authority_id != 2")
    List<User> selectAllUser();

    @Select("SELECT id, name,account_locked FROM user_sns_app WHERE name LIKE CONCAT('%', #{keyword}, '%') AND authority_id != 2")
    List<User> selectUserByKeywordAdmin(String keyword);

    @Update("UPDATE user_sns_app SET account_locked = false WHERE id = #{userId} AND authority_id != 2")
    void accountLock(int userId);

    @Update("UPDATE user_sns_app SET account_locked = true WHERE id = #{userId} AND authority_id != 2")
    void accountUnLocked(int userId);

    @Select("SELECT id, name, account_locked FROM user_sns_app WHERE account_locked = false")
    List<User> selectAccountLockUser();

    @Select("SELECT u.id, u.name FROM user_sns_app AS u INNER JOIN follow_sns_app AS f " +
            "ON u.id = f.followid WHERE f.followerid = #{userId}")
    List<User> selectFollowUser(int userId);

    @Select("SELECT u.id, u.name FROM user_sns_app AS u INNER JOIN follow_sns_app AS f " +
            "ON u.id = f.followerid WHERE f.followid = #{userId}")
    List<User> selectFollowerUser(int userId);

    @Update("UPDATE user_sns_app SET name = #{name},profile = #{profile} " +
            "WHERE id = #{id}")
    void updateProfile(User user);
}
