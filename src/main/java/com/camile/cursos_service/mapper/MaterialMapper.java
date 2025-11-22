package com.camile.cursos_service.mapper;

import com.camile.cursos_service.domain.Material;
import com.camile.cursos_service.dto.MaterialResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MaterialMapper {

    @Value("${minio.endpoint}")
    private String minioEndpoint;

    @Value("${minio.bucket.name}")
    private String bucketName;

    public MaterialResponseDTO toDTO(Material material) {
        if (material == null) {
            return null;
        }
        // Monta a URL completa para acessar o arquivo no MinIO
        String url = String.format("%s/%s/%s", minioEndpoint, bucketName, material.getUrlStorage());

        return new MaterialResponseDTO(
                material.getId(),
                material.getNomeArquivo(),
                material.getTipoArquivo(),
                material.getTamanho(),
                url);
    }
}