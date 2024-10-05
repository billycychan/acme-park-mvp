package com.billycychan.acmepark.member_service.ports;

import com.billycychan.acmepark.member_service.dto.Member;

public interface MemberManagement {
    void register(Member member);
    void flush();
}
