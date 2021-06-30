package org.clever.graaljs.fast.api.dto.response;

import lombok.Data;
import org.clever.graaljs.fast.api.entity.FileResource;
import org.clever.graaljs.fast.api.entity.HttpApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/30 14:51 <br/>
 */
@Data
public class AddHttpApiRes implements Serializable {
    private List<FileResource> fileList = new ArrayList<>();

    private HttpApi httpApi;
}
