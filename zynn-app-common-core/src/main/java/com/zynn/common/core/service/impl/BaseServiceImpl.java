package com.zynn.common.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zynn.common.core.base.BaseMapper;
import com.zynn.common.core.service.BaseService;

/**
 * 公共service实现
 *
 * @author yu_chen
 * @date 2017-12-01 17:21
 **/
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

}
