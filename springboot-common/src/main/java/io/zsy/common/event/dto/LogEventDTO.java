package io.zsy.common.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhangshuaiyin
 * @date 2021-06-30 14:47
 */
@Data
public class LogEventDTO {
    /**
     * 用户uid
     */
    private String userId;
    /**
     * 日志备注
     */
    private String logRemark;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}
