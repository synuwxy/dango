package com.synuwxy.dango.controller;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author wxy
 */
@Data
public class GitHubRepository {
    private String name;
    @JSONField(name = "clone_url")
    private String cloneUrl;
    @JSONField(name = "default_branch")
    private String defaultBranch;
}