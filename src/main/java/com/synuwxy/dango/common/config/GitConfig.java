package com.synuwxy.dango.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author wxy
 */
@Slf4j
@Configuration
public class GitConfig {

    @Value("${git.ssh.publicKey}")
    private String publicKey;
    @Value("${git.ssh.privateKey}")
    private String privateKey;
    @Value("${git.user.name}")
    private String name;
    @Value("${git.user.email}")
    private String email;


//    /**
//     * 初始化Git环境
//     */
//    @PostConstruct
//    public void init() {
//        log.info("[Git] init start");
//        try {
//            log.info("[init: registryGitAccount] 注册 Git 账户");
//            if (!GitUtil.registryGitAccount(name, email)) {
//                throw new RuntimeException("注册 Git 账户失败");
//            }
//
//            log.info("[init: setKey] 设置 Git 公私钥");
//            if (!GitUtil.setKey(publicKey, privateKey)) {
//                throw new RuntimeException("设置 Git 公私钥失败");
//            }
//        } catch (Exception e) {
//            log.error("[init error] message: {}", e.getMessage());
//        }
//        log.info("[Git] init end");
//    }
}
