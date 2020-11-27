package com.common.simpale.service;

import com.common.simpale.common.ServerResponse;
import com.common.simpale.pojo.Mail;

public interface TestService {

    ServerResponse testIdempotence();

    ServerResponse accessLimit();

    ServerResponse send(Mail mail);
}
