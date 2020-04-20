package com.synuwxy.dango.api.code;

import com.alibaba.fastjson.JSONObject;
import com.synuwxy.dango.api.code.model.CodeType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CodeTypeFinderTest {

    @Autowired
    private CodeTypeFinder codeTypeFinder;

    @Test
    public void findCodeTypeTest() {
        String codePath = "F:\\workspace\\github.com\\otter";
        CodeType codeType = codeTypeFinder.findCodeType(codePath);
        System.out.println(JSONObject.toJSONString(codeType));
    }
}
