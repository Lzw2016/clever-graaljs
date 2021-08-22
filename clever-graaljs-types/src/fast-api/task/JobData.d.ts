/**
 * 定时任务数据
 */
interface JobData extends JMap<any, any> {
    [key: any]: any;
}

/**
 * 当前定时任务数据
 */
declare const jobData: JobData;
