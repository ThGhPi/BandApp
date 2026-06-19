package com.thghpi.bandapp.band_api.repository;
import com.thghpi.bandapp.band_api.entity.FileInfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {

}
