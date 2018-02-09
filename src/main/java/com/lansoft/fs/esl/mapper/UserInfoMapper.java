package com.lansoft.fs.esl.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserInfoMapper {
    @Select("SELECT UA.UAN FROM IFM_SYS_GROUP_UAN UA, IFM_SYS_USER U, IFM_SYS_USER_MAPP_GRO UG WHERE U.USER_ID=UG.USER_ID AND UG.GROUP_ID=UA.GROUP_ID AND U.DEL_FLAG='1' AND U.VAILD_MOBILE_PH=#{mobile}")
    List<String> selectUanbyUserMobile(String mobile);
}
