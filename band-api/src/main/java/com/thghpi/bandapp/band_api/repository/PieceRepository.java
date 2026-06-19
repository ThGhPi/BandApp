package com.thghpi.bandapp.band_api.repository;
import com.thghpi.bandapp.band_api.entity.Piece;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PieceRepository extends JpaRepository<Piece,Long> {

}
