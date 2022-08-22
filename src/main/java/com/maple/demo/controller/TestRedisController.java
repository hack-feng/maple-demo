package com.maple.demo.controller;

import com.alibaba.fastjson.JSON;
import com.maple.demo.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author 笑小枫
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 * @date 2022/7/20
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/example")
@Api(tags = "实例演示-Redis接口文档")
public class TestRedisController {

    private final RedisUtil redisUtil;

    @PutMapping("/insertStr")
    @ApiOperation(value = "插入String类型的数据到redis")
    public void insertStr(String key, String value) {
        redisUtil.set(key, value);
    }

    @PostMapping("/getStr")
    @ApiOperation(value = "根据key获取redis的数据")
    public String getStr(String key) {
        return String.valueOf(redisUtil.get(key));
    }

    @DeleteMapping("/deleteStr")
    @ApiOperation(value = "根据key删除redis的数据")
    public Boolean deleteStr(String key) {
        redisUtil.remove(key);
        return redisUtil.exists(key);
    }

    @PostMapping("/operateMap")
    @ApiOperation(value = "模拟操作Map集合的数据")
    public Object operateMap() {
        redisUtil.hmSet("maple:map", "xiaofeng", "笑小枫");
        return redisUtil.hmGet("maple:map", "xiaofeng");
    }

    @PostMapping("/operateList")
    @ApiOperation(value = "模拟操作List集合的数据")
    public String operateList() {
        String listKey = "maple:list";
        redisUtil.lPush(listKey, "小枫");
        redisUtil.lPush(listKey, "小明");
        redisUtil.lPush(listKey, "小枫");
        return JSON.toJSONString(redisUtil.lRange(listKey, 0, 2));
    }

    @PostMapping("/operateSet")
    @ApiOperation(value = "模拟操作Set集合的数据")
    public String operateSet() {
        String listKey = "maple:set";
        redisUtil.addSet(listKey, "小枫");
        redisUtil.addSet(listKey, "小明");
        redisUtil.addSet(listKey, "小枫");
        log.info("集合中是否包含小枫" + redisUtil.isMember(listKey, "小枫"));
        log.info("集合中是否包含小红" + redisUtil.isMember(listKey, "小红"));
        return JSON.toJSONString(redisUtil.setMembers(listKey));
    }

    @PostMapping("/operateZSet")
    @ApiOperation(value = "模拟操作ZSet有序集合的数据")
    public String operateZSet() {
        String listKey = "maple:zSet";
        redisUtil.zAdd(listKey, "小枫", 8);
        redisUtil.zAdd(listKey, "小明", 1);
        redisUtil.zAdd(listKey, "小红", 12);
        redisUtil.zAdd(listKey, "大明", 5);
        redisUtil.zAdd(listKey, "唐三", 10);
        redisUtil.zAdd(listKey, "小舞", 9);
        // 降序获取source最高的5条数据
        return JSON.toJSONString(redisUtil.reverseRange(listKey, 0L, 4L));
    }
}
