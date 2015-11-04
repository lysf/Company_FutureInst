package com.futureinst.fileupload;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 */
public interface MultiPartRequest {

    void addFileUpload(String param, File file);
    
    void addStringUpload(String param, String content);
    
    Map<String,File> getFileUploads();
    
    Map<String,String> getStringUploads();
    
    List<String> getFileNameUploads();
}