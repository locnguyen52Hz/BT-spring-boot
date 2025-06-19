package com.example.user.management.Service;

import com.example.user.management.Service.Imp.FileServiceImp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService implements FileServiceImp {

    @Value("${fileUpload.rootPath}")
    private String rootPath;
    private Path root;


    //Kiểm tra file có tốn tại ko, nếu ko thì tạo file
    private void init(){
        root = Paths.get(rootPath);
        if (Files.notExists(root)) {
            try {
                Files.createDirectories(root);
            }catch (Exception e){
                System.out.println("Error in init: "+e.getMessage());
            }
        }
    }


    @Override
    public boolean saveFile(MultipartFile file) {

        init();
        System.out.println(file);
        try{
            //Nếu user ko cập nhật file mới thì bỏ qua
            if(file == null){
                System.out.println("Empty file");
                return true;
            }
            else {
                Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
                return true;
            }
            //

        }catch (Exception e){
            System.out.println("Error in save file: "+e.getMessage());
        }
        return false;
    }

    @Override
    public Resource loadFileAsResource(String filename) {
        init();
        try{
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            //kiểm tra file có tồn tại ko
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
        }catch (Exception e){
            System.out.println("Error in load file: "+e.getMessage());
        }
        return null;
    }
}
