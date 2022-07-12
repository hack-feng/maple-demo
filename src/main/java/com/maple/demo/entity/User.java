package com.maple.demo.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.maple.demo.config.bean.BaseEntity;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户中心-用户信息表
 * </p>
 *
 * @author 笑小枫
 * @since 2022-07-11
 */
@Getter
@Setter
@TableName("usc_user")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "User对象", description = "用户中心-用户信息表")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户账号")
    private String account;

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("用户类型（00系统用户,01小程序用户）")
    private String userType;

    @ApiModelProperty("用户邮箱")
    private String email;

    @ApiModelProperty("手机号码")
    private String phone;

    @ApiModelProperty("用户性别（0男 1女 2未知）")
    private String sex;

    @ApiModelProperty("头像地址")
    private String avatar;

    @ApiModelProperty("用户加密盐值")
    private String salt;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("帐号状态（0正常 1停用）")
    private String status;

    @ApiModelProperty("删除标志")
    @TableLogic
    private Boolean deleteFlag;

    @ApiModelProperty("备注")
    private String remark;


}
