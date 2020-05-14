package com.synuwxy.dango.service.git;

import com.synuwxy.dango.service.git.model.GitCloneParam;

import java.io.IOException;

/**
 * @author wxy
 */
public interface GitService {

    /**
     * clone 代码
     * @param gitCloneParam git clone 所需参数
     * @param path clone代码位置
     * @return clone成功返回true
     * @throws IOException IO
     * @throws InterruptedException Interrupted
     */
    boolean clone(GitCloneParam gitCloneParam, String path) throws IOException, InterruptedException;

    /**
     * 注册Git账户 ，包含username和email
     * @param username username
     * @param email email
     * @return 注册成功返回true
     * @throws Exception 异常
     */
    boolean registryGitAccount(String username, String email) throws Exception;

    /**
     * 注册Git username
     * @param username username
     * @return 注册成功返回true
     * @throws Exception 异常
     */
    boolean registryName(String username) throws Exception;

    /**
     * 注册Git email
     * @param email email
     * @return 注册成功返回true
     * @throws Exception 异常
     */
    boolean registryEmail(String email) throws Exception;

    /**
     * 注册Git公私钥
     * @param publicKey 公钥
     * @param privateKey 私钥
     * @param home 用户home目录path
     * @return 注册成功返回true
     */
    boolean setKey(String publicKey, String privateKey, String home);

    /**
     * 注册Git公钥
     * @param publicKey 公钥
     * @param home 用户home目录path
     * @return 注册成功返回true
     */
    boolean setPublicKey(String publicKey, String home);

    /**
     * 注册Git私钥
     * @param privateKey 私钥
     * @param home 用户home目录path
     * @return 注册成功返回true
     */
    boolean setPrivateKey(String privateKey, String home);

    /**
     * 设置 KnownHosts
     * @param gitUrl git仓库url
     * @param home 用户home目录path
     * @return 设置成功返回true
     * @throws IOException IO
     */
    boolean setKnownHosts(String gitUrl, String home) throws IOException;
}
