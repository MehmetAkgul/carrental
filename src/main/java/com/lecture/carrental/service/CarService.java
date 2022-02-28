package com.lecture.carrental.service;

import com.lecture.carrental.domain.Car;
import com.lecture.carrental.domain.FileDB;
import com.lecture.carrental.dto.CarDTO;
import com.lecture.carrental.exception.BadRequestException;
import com.lecture.carrental.exception.ResourceNotFoundException;
import com.lecture.carrental.repository.CarRepository;
import com.lecture.carrental.repository.FileDBRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final FileDBRepository fileDBRepository;
    private static final String IMAGE_NOT_FOUND_MSG = "image with id %s not found";
    private static final String CAR_NOT_FOUND_MSG = "car with id %d not found";

    public CarDTO findByIdOrderById(Long id) throws ResourceNotFoundException {

        return carRepository.findByIdOrderById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(CAR_NOT_FOUND_MSG, id)));
    }


    public void add(Car car, String imageId) throws BadRequestException {

        FileDB fileDB = fileDBRepository.findById(imageId).orElseThrow(
                () -> new ResourceNotFoundException(String.format(IMAGE_NOT_FOUND_MSG, imageId)));

        Set<FileDB> fileDBSet = new HashSet<>();
        fileDBSet.add(fileDB);
        car.setImage(fileDBSet);
        carRepository.save(car);

    }

    public List<CarDTO> fetchAllCars() {

        return carRepository.findAllCar();

    }

    public void updateCar(Long id, String imageId, Car car) throws BadRequestException {

        FileDB dbFileDB = fileDBRepository.findById(imageId).get(); //databasedeki

        Car dbCar = carRepository.getById(id);//databasedeki
        if (dbCar.getBuiltIn()) {
            throw new BadRequestException("You dont have permission to update car!");
        }
        //car.setBuiltIn(false);

        Set<FileDB> fileDBSet = new HashSet<>();
        fileDBSet.add(dbFileDB);
        car.setImage(fileDBSet); //güncellenecek aracın resimini güncelledik
        car.setId(id);// güncellencek aracın id sini güncellenecek bilgilere ekledik
        carRepository.save(car);
    }

    public void removeById(Long id) throws BadRequestException {

        Car car = carRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(CAR_NOT_FOUND_MSG, id)));

        if (car.getBuiltIn())
            throw new BadRequestException("You dont have permission delete car!");


    }
}
