package com.codecool.trainscheduleapi.DTO;

import com.codecool.trainscheduleapi.entity.Cargo;
import com.codecool.trainscheduleapi.entity.Stop;
import com.codecool.trainscheduleapi.entity.Train;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainDTO {
    private Long id;
    private String type;

    private List<StopDTO> stops;
    private ServiceDTO service;
    private List<CargoDTO> cargos;

    public TrainDTO(Train train) {
        id = train.getId();
        type = train.getType();

        stops = new ArrayList<>();
        for (Stop stop : train.getStops()) {
            stops.add(new StopDTO(stop));
        }

        if(train.getService() != null)
            service = new ServiceDTO(train.getService());

        cargos = new ArrayList<>();
        for (Cargo cargo : train.getCargos()) {
            cargos.add(new CargoDTO(cargo));
        }
    }
}
