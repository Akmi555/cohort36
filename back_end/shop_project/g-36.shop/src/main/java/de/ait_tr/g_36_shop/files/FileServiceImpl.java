package de.ait_tr.g_36_shop.files;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import de.ait_tr.g_36_shop.service.interfaces.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private final AmazonS3 client;
    private final ProductService productService;

    public FileServiceImpl(AmazonS3 client, ProductService productService) {
        this.client = client;
        this.productService = productService;
    }

    @Override
    public String upload(MultipartFile file, String productTitle) {
        String uniqueFileName = generateFileName(file);

        try {
            ObjectMetadata metaData = new ObjectMetadata(); // объект для метаданных о файле
            metaData.setContentType(file.getContentType()); // получаем эти метаданные
            PutObjectRequest request = new PutObjectRequest(
                    "shopcohort36images", uniqueFileName, file.getInputStream(), metaData
            ).withCannedAcl(CannedAccessControlList.PublicRead);

            client.putObject(request); // отправляем в облако

            String url = client.getUrl("shopcohort36images", uniqueFileName).toString();

            productService.attachImage(url, productTitle);

            return uniqueFileName;
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private String generateFileName(MultipartFile file) {
        String sourceFileName = file.getOriginalFilename();
        int dotIndex = sourceFileName.lastIndexOf("."); // ищем индекс точки в строке - имени файла, ищем с правого конца строки
        String fileName = sourceFileName.substring(0, dotIndex); // берем подстроку до точки
        String extension = sourceFileName.substring(dotIndex); // берем подстроку после точки

        return String.format("%s-%s%s", fileName, UUID.randomUUID(), extension);
    }

}
