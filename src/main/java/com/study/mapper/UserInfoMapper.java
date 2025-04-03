package com.study.mapper;

import com.study.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserInfoMapper {
    List<UserInfo> selectUserInfoByIds(List<Integer> ids);

    int batchInsertUserInfo(List<UserInfo> userInfos);

    int deleteUserInfoByIds(List<Integer> ids);
}
