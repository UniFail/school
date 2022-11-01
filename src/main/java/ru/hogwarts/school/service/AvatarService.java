package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.awt.image.BufferedImage;

import static java.nio.file.StandardOpenOption.CREATE_NEW;


@Service
@Transactional
public class AvatarService {

    @Value("${avatars.avatar.dir.path}")

    private String avatarsDir;

    private StudentService studentService;

    private AvatarRepository avatarRepository;

    public AvatarService(StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    public void uploadCover(Long studentId, MultipartFile file) throws IOException {
        Student student = studentService.findStudent(studentId);

        Path filePath = Path.of(avatarsDir, studentId + "." + getExtension(file.getOriginalFilename())); //генерирум имя файла
        Files.createDirectories(filePath.getParent()); //при первом запуске создает папку avatarsDir
        Files.deleteIfExists(filePath); //удаляем дубль

        try (InputStream is = file.getInputStream(); // try закрывает поток данных
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 500); //записываем по несколько байт
             BufferedOutputStream bos = new BufferedOutputStream(os, 500);
        ) {
            bis.transferTo(bos); //из входного потока в выходной
        }

        Avatar avatar = findStudentAvatar(studentId); //Ищем аватар у студента если нет то создаем новую если есть редактируем
        //на локальный диск
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        //в базу
        avatar.setData(generatorImageData(filePath));
    }

    private byte[] generatorImageData(Path filePath) throws IOException{
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is,500);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage data = new BufferedImage(100,height, image.getType());
            Graphics2D graphics2D = data.createGraphics();
            graphics2D.drawImage(image,0,0,100,height,null);
            graphics2D.dispose();

            ImageIO.write(data,getExtension(filePath.getFileName().toString()),baos);
            return baos.toByteArray();
        }


    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1); //находим расширение файла
    }

    public Avatar findStudentAvatar(Long studentId) {
        return avatarRepository.findById(studentId).orElseThrow();
    }

}
