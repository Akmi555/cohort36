package de.ait_tr.g_36_shop.files;

import de.ait_tr.g_36_shop.exception_handling.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileController {

    private FileService service;

    public FileController(FileService service) {
        this.service = service;
    }

    //ожидаем файл с картинкой и строку с названием продукта
    @PostMapping
    public Response upload(@RequestParam MultipartFile file, @RequestParam String productTitle){
        String url = service.upload(file, productTitle);
        return new Response("Upload successful: url - " + url);
    }



}
