package com.lecture.carrental.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@AllArgsConstructor

public class FileDTO {


    private String name;
    private String url;
    private String type;
    private long size;

}
