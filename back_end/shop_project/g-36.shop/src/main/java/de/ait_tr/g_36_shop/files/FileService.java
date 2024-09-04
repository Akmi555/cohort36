package de.ait_tr.g_36_shop.files;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String upload(MultipartFile file, String productTitle);
}
