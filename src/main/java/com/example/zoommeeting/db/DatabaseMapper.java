package com.example.zoommeeting.db;

import com.example.zoommeeting.model.core.AppConfig;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface DatabaseMapper {

    @Select("SELECT * FROM app_config WHERE `key` = #{key}")
    AppConfig getAppConfigValue(@Param("key") String zoomAccessToken);

    @Update("UPDATE app_config SET `value` = #{value}, updated_at = NOW() WHERE `key` = #{key}")
    void updateAppConfig(
            @Param("value") String accessToken,
            @Param("key")  String key
    );

    @Insert("INSERT INTO app_config(`key`, `value`, `description`, updated_at) VALUES(#{key}, #{value}, #{description}, NOW())")
    void insertIntoAppConfig(
            @Param("key") String key,
            @Param("value") String value,
            @Param("description") String description
    );
}
