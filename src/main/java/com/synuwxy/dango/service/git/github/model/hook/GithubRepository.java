package com.synuwxy.dango.service.git.github.model.hook;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author wxy
 */
@Data
public class GithubRepository {
    private String name;
    @JSONField(name = "clone_url")
    private String cloneUrl;
    @JSONField(name = "default_branch")
    private String defaultBranch;
}