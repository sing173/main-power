package com.zungen.mp.common.service;


import com.zungen.mp.common.domain.UploadFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 上传服务
 * </p>
 *
 * @author minson
 * @since 2020-11-26
 */
public interface UpLoadService {

    UploadFile upload2Local(MultipartFile file) throws IOException;
}
