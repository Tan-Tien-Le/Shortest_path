package com.example.demo.algorithm;

import com.example.demo.model.Edge;
import com.example.demo.model.Vertical;
import com.example.demo.repository.EdgeRepository;
import com.example.demo.repository.VerticalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DijkstraAlg {
    private final VerticalRepository verticalRepository;
    private final EdgeRepository edgeRepository;

    double calculateDistance(Vertical start, Vertical end) {
        return Math.sqrt(Math.pow(end.getX() - start.getX(), 2) + Math.pow(end.getY() - start.getY(), 2));
    }

    public Vertical findNearestVertex(Vertical source) {
        return verticalRepository.findAll().stream()
                .min(Comparator.comparingDouble(vertex -> calculateDistance(source, vertex)))
                .orElseThrow(); // Throw an exception if not found
    }


    public List<Vertical> findPath(Vertical head, Vertical tail) {
        Map<Vertical, Double> distance = new HashMap<>();
        Map<Vertical, Vertical> previous = new HashMap<>();
        PriorityQueue<Vertical> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(distance::get));

        List<Vertical> vertices = verticalRepository.findAll();

        for (Vertical v : vertices) {
            distance.put(v, Double.MAX_VALUE);
            previous.put(v, null);
        }

        distance.put(head, 0.0);
        priorityQueue.offer(head);

        while (!priorityQueue.isEmpty()) {
            Vertical current = priorityQueue.poll();

            if (current.equals(tail)) {
                break; // Đã tìm thấy đỉnh đích, dừng vòng lặp
            }

            for (Edge edge : edgeRepository.findByStart(current.getLabel())) {
                Vertical neighbor = verticalRepository.findById(edge.getEnd()).orElseThrow();

                double newDistance = distance.get(current) + calculateDistance(current, neighbor);

                if (newDistance < distance.get(neighbor)) {
                    distance.put(neighbor, newDistance);
                    previous.put(neighbor, current);
                    priorityQueue.offer(neighbor);
                }
            }
        }

        return buildPath(previous, head, tail);
    }

    private List<Vertical> buildPath(Map<Vertical, Vertical> previous, Vertical head, Vertical tail) {
        List<Vertical> path = new ArrayList<>();
        Vertical current = tail;

        while (current != null) {
            path.add(current);
            current = previous.get(current);
        }

        Collections.reverse(path);

        return path;
    }
}

