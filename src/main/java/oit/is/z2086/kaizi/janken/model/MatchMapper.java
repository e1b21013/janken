package oit.is.z2086.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchMapper {
  @Select("SELECT * from matches")
  ArrayList<Match> selectAllMatch();

  @Insert("INSERT INTO matches (user1,user2,user1Hand,user2Hand,isActive) VALUES (#{user1},#{user2},#{user1Hand},#{user2Hand},'FALSE');")
  @Options(useGeneratedKeys =true,keyColumn ="id",keyProperty = "id")
  void insertMatch(Match match);

  @Insert("insert into matches (user1,user2,user1Hand,user2Hand,isActive) VALUES (#{user1},#{user2},#{user1Hand},#{user2Hand},#{isActive});")
  @Options(useGeneratedKeys =true,keyColumn ="id",keyProperty = "id")
  void insertMatches(Match match);

  @Select("select * from matches where isActive=true")
  Match selectByActive();

  @Update("update matches set isActive=false where user1=#{user1} and user2=#{user2} and isActive=true")
  boolean updateMatchByusers(int user1, int user2);
}
