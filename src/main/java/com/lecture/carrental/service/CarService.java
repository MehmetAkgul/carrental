package com.lecture.carrental.service;

import com.lecture.carrental.domain.Car;
import com.lecture.carrental.domain.FileDB;
import com.lecture.carrental.exception.BadRequestException;
import com.lecture.carrental.exception.ResourceNotFoundException;
import com.lecture.carrental.repository.CarRepository;
import com.lecture.carrental.repository.FileDBRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final FileDBRepository fileDBRepository;
    private static final String IMAGE_NOT_FOUND_MSG = "image with id %s not found";

    public void add(Car car, String imageId) throws BadRequestException {

        FileDB fileDB = fileDBRepository.findById(imageId).orElseThrow(
                () -> new ResourceNotFoundException(String.format(IMAGE_NOT_FOUND_MSG, imageId)));

        Set<FileDB> fileDBSet = new HashSet<>();
        fileDBSet.add(fileDB);
        car.setImage(fileDBSet);
        carRepository.save(car);

    }
}
