package com.synuwxy.dango.ddd.aggreate.docker.image;

import com.alibaba.fastjson.JSONObject;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.ListImagesCmd;
import com.github.dockerjava.api.model.Image;
import com.synuwxy.dango.service.docker.model.SearchImageParam;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author wxy
 */
@Slf4j
public class DockerImageRepository {
    private final DockerClient dockerClient;

    public DockerImageRepository(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    public Image searchImage(String imageFullName) {
        log.info("查询镜像 名称: {}", imageFullName);
        String str = ":";
        if (!imageFullName.contains(str)) {
            imageFullName += ":latest";
            log.info("填充镜像全称 tag: {}", imageFullName);
        }
        // 筛选服务器上的镜像
        List<Image> images = dockerClient.listImagesCmd().withImageNameFilter(imageFullName).exec();
        if (images.isEmpty()) {
            log.info("查询结果为空");
            return null;
        }
        return images.get(0);
    }

    public List<Image> searchImages(SearchImageParam searchImageParam) {
        log.info("查询镜像列表 searchImageParam: {}", JSONObject.toJSONString(searchImageParam));
        ListImagesCmd listImagesCmd = dockerClient.listImagesCmd();
        if (null != searchImageParam.getImageName()) {
            listImagesCmd.withImageNameFilter(searchImageParam.getImageName());
        }
        List<String> labels = searchImageParam.getLabels();
        if (null != labels && labels.size() > 0) {
            labels.forEach(listImagesCmd::withLabelFilter);
        }
        return listImagesCmd.exec();
    }
}
