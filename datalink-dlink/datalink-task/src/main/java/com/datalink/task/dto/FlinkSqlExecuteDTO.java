package com.datalink.task.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * FlinkSqlExecuteDTO
 *
 * @author wenmo
 * @since 2021/5/26 23:13
 */
@Getter
@Setter
public class FlinkSqlExecuteDTO {
    private String sessionId;
    private String flinkHost;
    private Integer port;
    private Integer maxRowNum ;
    private String statement;
}
