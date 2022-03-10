package com.xuan.vueblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuan.vueblog.entity.Blog;
import com.xuan.vueblog.mapper.BlogMapper;
import com.xuan.vueblog.service.BlogService;
import org.springframework.stereotype.Service;

/**
* @author xx
* @description 针对表【m_blog】的数据库操作Service实现
* @createDate 2022-03-05 11:21:55
*/
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog>
    implements BlogService{

}




