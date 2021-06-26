package org.clever.graaljs.fast.api.dto.response;

import lombok.Data;
import org.clever.graaljs.fast.api.entity.FileResource;
import org.clever.graaljs.fast.api.entity.HttpApi;

import java.io.Serializable;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/26 21:50 <br/>
 */
@Data
public class HttpApiFileResourceRes implements Serializable {
    private FileResource fileResource;

    private HttpApi httpApi;
}
