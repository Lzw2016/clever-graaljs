package org.clever.graaljs.fast.api.dto.response;

import lombok.Data;
import org.clever.graaljs.fast.api.entity.FileResource;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/08/22 14:30 <br/>
 */
@Data
public class DelJsJobRes implements Serializable {
    List<FileResource> fileList;
}
