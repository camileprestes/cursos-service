package com.camile.cursos_service.service;

import com.camile.cursos_service.domain.Material;
import com.camile.cursos_service.dto.MaterialResponseDTO;
import com.camile.cursos_service.mapper.MaterialMapper;
import com.camile.cursos_service.repository.MaterialRepository;
import com.camile.cursos_service.repository.ModuloRepository;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;
    private final ModuloRepository moduloRepository;
    private final MinioClient minioClient;
    private final MaterialMapper materialMapper;

    @Value("${minio.bucket.name}")
    private String bucketName;

    public MaterialService(MaterialRepository materialRepository, ModuloRepository moduloRepository, MinioClient minioClient, MaterialMapper materialMapper) {
        this.materialRepository = materialRepository;
        this.moduloRepository = moduloRepository;
        this.minioClient = minioClient;
        this.materialMapper = materialMapper;
    }

    public MaterialResponseDTO anexarMaterial(Long moduloId, MultipartFile arquivo) {
        var modulo = moduloRepository.findById(moduloId)
                .orElseThrow(() -> new EntityNotFoundException("Módulo com id " + moduloId + " não encontrado."));

        try {
            // 1. Gera um nome de arquivo único para evitar conflitos
            String nomeArquivoUnico = UUID.randomUUID() + "-" + arquivo.getOriginalFilename();

            // 2. Faz o upload para o MinIO
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(nomeArquivoUnico)
                            .stream(arquivo.getInputStream(), arquivo.getSize(), -1)
                            .contentType(arquivo.getContentType())
                            .build());

            // 3. Cria a entidade Material com os metadados
            Material material = Material.builder()
                    .modulo(modulo)
                    .nomeArquivo(arquivo.getOriginalFilename())
                    .tipoArquivo(arquivo.getContentType())
                    .urlStorage(nomeArquivoUnico) // Armazenamos apenas o nome do objeto, a URL completa pode ser montada depois
                    .tamanho(arquivo.getSize())
                    .build();

            // 4. Salva no banco de dados
            Material materialSalvo = materialRepository.save(material);
            return materialMapper.toDTO(materialSalvo);

        } catch (Exception e) {
            throw new RuntimeException("Falha ao fazer upload do arquivo: " + e.getMessage(), e);
        }
    }

    public List<MaterialResponseDTO> listarMateriaisPorModulo(Long moduloId) {
        if (!moduloRepository.existsById(moduloId)) {
            throw new EntityNotFoundException("Módulo com id " + moduloId + " não encontrado.");
        }
        return materialRepository.findByModuloId(moduloId).stream()
                .map(materialMapper::toDTO)
                .toList();
    }
}
