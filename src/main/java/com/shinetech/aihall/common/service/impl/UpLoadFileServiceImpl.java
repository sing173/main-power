package com.shinetech.aihall.common.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.shinetech.aihall.common.domain.UploadFile;
import com.shinetech.aihall.common.service.UpLoadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * <p>
 * 上传服务 实现
 * </p>
 *
 * @author minson
 * @since 2020-11-26
 */
public class UpLoadFileServiceImpl implements UpLoadService{
    @Value("${spring.servlet.multipart.location}")
    private String fileTempPath;

    @Override
    public UploadFile upload2Local(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String rawFileName = StrUtil.subBefore(fileName, ".", true);
        String fileType = StrUtil.subAfter(fileName, ".", true);
        String localFilePath = StrUtil.appendIfMissing(fileTempPath, "/") + rawFileName + "-" + DateUtil.current(false) + "." + fileType;

        file.transferTo(new File(localFilePath));

        UploadFile uploadFile = new UploadFile();
        uploadFile.setPath(localFilePath);
        uploadFile.setName(fileName);
        uploadFile.setRawFileName(rawFileName);
        uploadFile.setFileType(fileType);

        return uploadFile;
    }
}
