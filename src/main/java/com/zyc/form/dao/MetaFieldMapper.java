package com.zyc.form.dao;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

import com.zyc.baselibs.dao.MybatisBaseMapper;
import com.zyc.form.cache.RedisCache;
import com.zyc.form.entities.MetaField;

@CacheNamespace(implementation = RedisCache.class,  flushInterval = 60000)
@Mapper
public interface MetaFieldMapper extends MybatisBaseMapper<MetaField> {

}
