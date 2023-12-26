package com.han.train.member.requset;

public class PassengerQueryReq {

    private Long memberId;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PassengerQueryReq{");
        sb.append("memberId=").append(memberId);
        sb.append('}');
        return sb.toString();
    }
}