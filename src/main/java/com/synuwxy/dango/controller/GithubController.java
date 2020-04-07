package com.synuwxy.dango.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.synuwxy.dango.api.git.github.GithubService;
import com.synuwxy.dango.api.git.github.model.hook.HookInfoDto;
import com.synuwxy.dango.api.git.github.model.hook.HookInfoModel;
import com.synuwxy.dango.common.ResultObject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/github")
public class GithubController {

    private final String HOOK_URL = "/github/hook";

    private HookInfoDto hookInfoDto;

    private final GithubService githubService;

    public GithubController(GithubService githubService) {
        this.githubService = githubService;
        List<HookInfoModel> hooks = new ArrayList<>();
        hooks.add(new HookInfoModel(HOOK_URL + "/build", ""));
        hooks.add(new HookInfoModel(HOOK_URL + "/deploy", ""));
        this.hookInfoDto = new HookInfoDto(hooks);
    }

    @GetMapping("/getHookUrl")
    public ResultObject<HookInfoDto> getHookUrl() {
        return ResultObject.instance(ResultObject.SUCCESS, "", hookInfoDto);
    }

    @PostMapping("/hook/build/{type}")
    public ResultObject<?> hookBuild(@RequestBody String payload, @PathVariable("type") String type) {
        GitHubHookParam gitHubHookParam = JSONObject.parseObject(payload, GitHubHookParam.class);
        githubService.hookBuild(gitHubHookParam, type);
        return ResultObject.success();
    }

    @PostMapping("/hook/deploy")
    public void hookDeploy() {

    }
}
