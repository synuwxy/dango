package com.synuwxy.dango.controller;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author wxy
 */
@Data
public class GitHubHookParam {

    private GitHubRepository repository;

    private GitHubSender sender;




}
