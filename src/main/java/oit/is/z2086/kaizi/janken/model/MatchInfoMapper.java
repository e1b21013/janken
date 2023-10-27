package oit.is.z2086.kaizi.janken.model;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface MatchInfoMapper {
  @Insert("insert into matchinfo (user1,user2,user1Hand,isActive) values (#{user1},#{user2},#{user1Hand},#{isActive})")
  void insertinfo(MatchInfo matchinfo);
}
