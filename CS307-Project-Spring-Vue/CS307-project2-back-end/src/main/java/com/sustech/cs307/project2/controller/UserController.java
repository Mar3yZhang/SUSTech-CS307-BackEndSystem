package com.sustech.cs307.project2.controller;

import com.sustech.cs307.project2.config.Result;
import com.sustech.cs307.project2.entity.User;
import com.sustech.cs307.project2.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/users")
public class UserController {

    @Resource
    private UserMapper userMapper;

    @PostMapping("/login")
    public Result<?> login(@RequestBody User user) {
        User res = userMapper.selectByName(user.getUsername());
        Result<User> R = new Result<>(res);
        if (res == null) {
            return Result.error("114", "User \"" + user.getUsername() + "\" doesn't exit");
        } else if (!user.getPassword().equals(res.getPassword())) {
            return Result.error("514", "Wrong password");
        }
//        userMapper.insert(user);
        return R;
    }

    @PostMapping("/create")
    public int create(@RequestBody User user) {
        return userMapper.Insert(user.getUsername(), user.getPassword(), user.getRole());
    }

    @GetMapping("/get")
    public User select(@RequestBody User user) {
        return userMapper.selectByName(user.getUsername());

    }

}
