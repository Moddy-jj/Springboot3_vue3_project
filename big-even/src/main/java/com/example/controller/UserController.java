package com.example.controller;
import java.util.Map;
import com.example.pojo.Result;
import com.example.pojo.User;
import com.example.service.UserService;
import com.example.utils.JwtUtil;
import com.example.utils.Md5Util;
import com.example.utils.ThreadLocalUtil;

import java.util.HashMap;

import jakarta.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;
import org.mockito.internal.util.StringUtil;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
            
        //查询用户是否存在
        User u = userService.findByUserName(username);
        if (u==null){
            //用户不存在，注册用户
            userService.register(username, password);
            return Result.success();
        }else{
            //用户存在
            return Result.error("用户已存在");
        }
    }
    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        //根据用户名查询用户
        User loginUser = userService.findByUserName(username);
        //判断用户是否存在
        if (loginUser == null) {
            return Result.error("用户错误");
    }
    //判断密码是否正确
    if (Md5Util.getMD5String(password).equals(loginUser.getPassword())) {
        Map<String,Object> claims = new HashMap<>();
        claims.put("id", loginUser.getId());
        claims.put("username", loginUser.getUsername());
        String token = JwtUtil.genToken(claims);
        //生成token
        //把token存储到redis
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(token, token,1, java.util.concurrent.TimeUnit.HOURS);
        return Result.success(token);
    } 
        return Result.error("密码错误");

    }
    @GetMapping("/userInfo")
    public Result<User> userInfo(@RequestHeader(name = "Authorization") String token){
        //根据token获取用户信息
        // Map<String,Object> map = JwtUtil.parseToken(token);
        // String username = (String) map.get("username");
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user=userService.findByUserName(username);
        return Result.success(user);
    }
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }
    @PatchMapping("/updatePwd")
    public Result updatePassword(@RequestBody Map<String,String> params,@RequestHeader("Authorization") String token){
        //校验参数
        String oldPassword = params.get("oldPassword");
        String newPassword = params.get("newPassword");
        String rePassword = params.get("rePassword");
        
        if (!StringUtils.hasLength(rePassword) || !StringUtils.hasLength(newPassword) || !StringUtils.hasLength(oldPassword)) {
            return Result.error("缺少参数");
        }
        //原密码校验
        //调用userService根据用户名查询原密码，判断原密码是否正确
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.findByUserName(username);
        if (loginUser.getPassword().equals(Md5Util.getMD5String(oldPassword))) {
            return Result.error("原密码不正确");
        }
        //校验新密码和确认密码是否一致
        if (!newPassword.equals(rePassword)) {
            return Result.error("两次密码不一致");
        }
        //调用service更新密码
        userService.updatePassword(newPassword);
        //删除redis对应的token
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.getOperations().delete(token);
        return Result.success();
    }
}



