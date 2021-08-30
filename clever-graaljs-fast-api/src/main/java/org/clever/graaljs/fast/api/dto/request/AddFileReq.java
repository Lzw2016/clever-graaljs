package org.clever.graaljs.fast.api.dto.request;

import lombok.Data;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.clever.graaljs.fast.api.entity.EnumConstant;
import org.clever.graaljs.spring.core.utils.validator.annotation.ValidIntegerStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/01 17:25 <br/>
 */
@Data
public class AddFileReq implements Serializable {
    /**
     * 所属模块：0-自定义扩展，1-资源文件，2-初始化脚本，3-HTTP API，4-定时任务
     */
    @NotNull
    @ValidIntegerStatus(value = {EnumConstant.MODULE_0, EnumConstant.MODULE_1, EnumConstant.MODULE_2, EnumConstant.MODULE_3, EnumConstant.MODULE_4})
    private Integer module;

    @Pattern(regexp = "^(/[a-zA-Z0-9\\u4e00-\\u9fa5_-]+)*/?$")
    @NotBlank
    private String path;

    @Pattern(regexp = "^[a-zA-Z0-9\\u4e00-\\u9fa5_-]+(.js)?(.d.ts)?$")
    @NotBlank
    private String name;

    private String content;

    public String getName() {
        if (module != null && StringUtils.isBlank(FilenameUtils.getExtension(name))) {
            switch (module) {
                case EnumConstant.MODULE_0:
                    name = name + ".d.ts";
                    break;
                case EnumConstant.MODULE_1:
                    name = name + ".xml";
                    break;
                case EnumConstant.MODULE_2:
                case EnumConstant.MODULE_3:
                case EnumConstant.MODULE_4:
                    name = name + ".js";
                    break;
            }
        }
        return name;
    }

    public String getContent() {
        if (module != null || StringUtils.isBlank(content)) {
            switch (module) {
                case EnumConstant.MODULE_0:
                    content = "" +
                            "// default code\n" +
                            "interface Demo {\n" +
                            "}\n" +
                            "declare const Demo: Demo;";
                    break;
                case EnumConstant.MODULE_1:
                    String namespace = StringUtils.replace(path + FilenameUtils.getBaseName(name), "/", ".");
                    namespace = StringUtils.replace(namespace, "..", ".");
                    if (namespace.startsWith(".")) {
                        namespace = namespace.substring(1);
                    }
                    if (namespace.endsWith(".")) {
                        namespace = namespace.substring(0, namespace.length() - 1);
                    }
                    content = "" +
                            "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                            "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"https://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n" +
                            "<mapper namespace=\"" + namespace + "\">\n" +
                            "</mapper>";
                    break;
                case EnumConstant.MODULE_2:
                    content = "" +
                            "//default code\n" +
                            "console.info(\"初始化脚本\");";
                    break;
                case EnumConstant.MODULE_3:
                    content = "" +
                            "//default code\n" +
                            "return { ok: true };";
                    break;
                case EnumConstant.MODULE_4:
                    content = "//default code\n" +
                            "jobData.now = CommonUtils.currentTimeMillis();\n" +
                            "console.log('# %s -->', jobData.now);";
                    break;
            }
        }
        return content;
    }
}
