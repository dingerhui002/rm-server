package com.bc.rm.server.entity.electronic;

import lombok.Data;

/** 流程附件类
 * @author Administrator
 */
@Data
public class Attachment {
    /**
     * 附件名称
     */
    private String attachmentName;

    /**
     * 附件Id
     */
    private String fileId;
}
