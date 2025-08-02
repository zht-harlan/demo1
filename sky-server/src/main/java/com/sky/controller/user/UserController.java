package com.sky.controller.user;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.properties.JwtProperties;
import com.sky.result.Result;
import com.sky.service.UserService;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/user")
@Api(tags = "用户接口")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("/login")
    @ApiOperation("登录请求")
    public Result login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录请求：{}", userLoginDTO);
        User user=userService.login(userLoginDTO);
        // 这里可以添加实际的业务逻辑代码，比如验证用户名和密码
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        String token=JwtUtil.createJWT(jwtProperties.getUserTokenName(),jwtProperties.getAdminTtl(),claims);
        // 假设登录成功，返回一个成功的结果
        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO=UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(token)
                .build();
        return Result.success(userLoginVO);

    }
}
