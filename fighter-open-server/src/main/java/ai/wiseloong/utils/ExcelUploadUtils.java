package ai.wiseloong.utils;

import ai.wiseloong.attachment.manage.apis.pojo.UploadFile;

public  class ExcelUploadUtils {
    /**
     * getUploadFile
     * @return
     */
    public static UploadFile getUploadFile(String name) {
        UploadFile uploadFile = new UploadFile();
        uploadFile.setFileName(name);
        uploadFile.setFileSuffixName("xlsx");
        uploadFile.setFileContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        return uploadFile;
    }

}
