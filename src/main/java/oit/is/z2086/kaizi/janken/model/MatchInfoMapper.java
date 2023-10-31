package oit.is.z2086.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchInfoMapper {
  @Insert("insert into matchinfo (user1,user2,user1Hand,isActive) values (#{user1},#{user2},#{user1Hand},#{isActive})")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertinfo(MatchInfo matchinfo);

  @Select("select * from matchinfo where isActive=true")
  ArrayList<MatchInfo> selectactive();

  @Select("select * from matchinfo where isActive=true and user1=#{user1} and user2=#{user2}")
  MatchInfo SelectByUsers(int user1, int user2);

  @Update("update matchinfo set isActive=false where user1=#{user1} and user2=#{user2} and isActive=true")
  boolean updateMatchInfo(int user1, int user2);
}
