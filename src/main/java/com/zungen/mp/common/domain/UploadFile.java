package com.zungen.mp.common.domain;

import lombok.Data;

/**
 * <p>
 * 上传文件对象
 * </p>
 *
 * @author minson
 * @since 2020-11-26
 */
@Data
public class UploadFile {
    private String path;
    private String name;
    private int size;
    private String rawFileName;
    private String fileType;
}
