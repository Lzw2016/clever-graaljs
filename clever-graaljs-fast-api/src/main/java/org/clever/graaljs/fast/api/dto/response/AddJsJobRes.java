package org.clever.graaljs.fast.api.dto.response;

import lombok.Data;
import org.clever.graaljs.fast.api.entity.FileResource;
import org.clever.task.core.entity.Job;
import org.clever.task.core.entity.JobTrigger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/08/22 14:28 <br/>
 */
@Data
public class AddJsJobRes implements Serializable {
    private List<FileResource> fileList = new ArrayList<>();

    private Job job;

    private JobTrigger jobTrigger;
}
