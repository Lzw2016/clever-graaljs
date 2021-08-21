package org.clever.graaljs.fast.api.dto.response;

import lombok.Data;
import org.clever.graaljs.fast.api.entity.FileResource;
import org.clever.task.core.entity.Job;
import org.clever.task.core.entity.JobTrigger;

import java.io.Serializable;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/08/21 21:53 <br/>
 */
@Data
public class JsJobInfoRes implements Serializable {
    private FileResource fileResource;

    private Job job;

    private JobTrigger jobTrigger;
}
