package cc.taketo.controller;

import cc.taketo.common.Result;
import cc.taketo.service.FtpService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangp
 * @date 2024/04/06 23:56
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    private FtpService ftpService;

    // 单上传文件
    @PostMapping(value = "upload", consumes = "multipart/*", headers = "content-type=multipart/form-data")
    public Result uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        if (file == null || file.isEmpty()) {
            return Result.error("401", "没有上传文件");
        }
        return Result.success();
    }

    // 导出文件
    @GetMapping(value = "download")
    public void downloadFile(@RequestParam String fileName, @RequestParam String ftpFilePath, HttpServletResponse response) {
        ftpService.downloadFile(fileName, ftpFilePath, response);
    }

    // 删除文件
    @GetMapping(value = "delete")
    public Result deleteFile(@RequestParam String ftpFilePath) {
        ftpService.deleteFile(ftpFilePath);
        return Result.success();
    }

}
