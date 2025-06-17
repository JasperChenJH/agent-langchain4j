package com.cqupt.java.ai.langchain4j.mapper;

import com.cqupt.java.ai.langchain4j.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.xmlbeans.impl.xb.xmlconfig.Extensionconfig;
@SuppressWarnings("all")
@Mapper
public interface UserMapper {
    @Select("select * from user where username=#{username} and password = #{password}")
    public User findByUsernameAndPassword(User user);
}
