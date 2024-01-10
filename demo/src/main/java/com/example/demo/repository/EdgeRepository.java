package com.example.demo.repository;

import com.example.demo.model.Edge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EdgeRepository extends JpaRepository<Edge,Integer> {
    List<Edge> findByStart(Integer currentVertexId);
}
