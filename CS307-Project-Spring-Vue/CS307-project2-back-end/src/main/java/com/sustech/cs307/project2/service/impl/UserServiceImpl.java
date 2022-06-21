package com.sustech.cs307.project2.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sustech.cs307.project2.entity.User;
import com.sustech.cs307.project2.mapper.UserMapper;
import com.sustech.cs307.project2.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
