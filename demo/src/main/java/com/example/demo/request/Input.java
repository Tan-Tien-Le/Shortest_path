package com.example.demo.request;

import com.example.demo.model.Vertical;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Input {
    Vertical head;
    Vertical end;

    @Override
    public String toString() {
        return "Input{" +
                "head=" + head +
                ", end=" + end +
                '}';
    }
}
