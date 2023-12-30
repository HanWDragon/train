package com.han.train.member.request;

import jakarta.validation.constraints.NotBlank;

/**
 * @author Han
 * @date 2023年09月06日 09:35
 */
public class MemberRegisterReq {

    @NotBlank(message = "【手机号】不能为空")
    public String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MemberRegisterReq{");
        sb.append("mobile='").append(mobile).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
