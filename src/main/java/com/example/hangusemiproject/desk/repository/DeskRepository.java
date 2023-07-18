package com.example.hangusemiproject.desk.repository;


import com.example.hangusemiproject.desk.entity.Desk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeskRepository extends JpaRepository<Desk,Long> {
}
