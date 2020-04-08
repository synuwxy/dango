package com.synuwxy.dango.api.git.github.model.hook;

import lombok.Data;

/**
 * @author wxy
 */
@Data
public class GithubHookParam {

    private GithubRepository repository;

    private GithubSender sender;

}
