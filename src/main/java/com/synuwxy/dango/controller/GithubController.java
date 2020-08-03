package com.synuwxy.dango.controller;

import com.alibaba.fastjson.JSONObject;
import com.synuwxy.dango.service.git.github.GithubService;
import com.synuwxy.dango.service.git.github.model.hook.GithubHookParam;
import com.synuwxy.dango.common.ResultObject;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author wxy
 */
@RestController
@RequestMapping("/github")
public class GithubController {

    private final GithubService githubService;

    public GithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    @PostMapping("/hook/build/{type}")
    public ResultObject hookBuild(@RequestBody String payload, @PathVariable("type") String type) throws IOException, InterruptedException {
        GithubHookParam githubHookParam = JSONObject.parseObject(payload, GithubHookParam.class);
        githubService.hookBuild(githubHookParam, type);
        return ResultObject.success();
    }

    @PostMapping("/hook/deploy/{type}")
    public ResultObject hookDeploy(@RequestBody String payload, @PathVariable("type") String type) throws IOException, InterruptedException {
        GithubHookParam githubHookParam = JSONObject.parseObject(payload, GithubHookParam.class);
        githubService.hookDeploy(githubHookParam, type);
        return ResultObject.success();
    }
}
