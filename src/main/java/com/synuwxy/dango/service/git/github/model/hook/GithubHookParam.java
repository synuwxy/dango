package com.synuwxy.dango.service.git.github.model.hook;

import lombok.Data;

/**
 * @author wxy
 */
@Data
public class GithubHookParam {

    private GithubRepository repository;

    private GithubSender sender;

}
