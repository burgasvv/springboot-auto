package com.burgas.springbootauto.calculations;

import com.burgas.springbootauto.entity.car.Car;
import com.burgas.springbootauto.entity.car.Equipment;
import com.burgas.springbootauto.entity.engine.EngineCharacteristics;
import com.burgas.springbootauto.entity.transmission.Gearbox;
import com.burgas.springbootauto.entity.transmission.Transmission;
import com.burgas.springbootauto.entity.turbocharging.Turbocharger;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.text.NumberFormat;
import java.util.*;
import java.util.stream.DoubleStream;

@Getter
@Setter
@RequiredArgsConstructor
public class EquipmentDataProcessing {

    private final Equipment equipment;

    private Double getDouble(String string) {
        if (string != null) {
            StringBuilder stringBuilder = new StringBuilder(string);
            boolean isNumber = false;
            int count = 0;
            int i = 0;
            for (char c : string.toCharArray()) {
                if (Character.isDigit(c)) {
                    isNumber = true;
                }
                if (!Character.isDigit(c) && c != ',') {
                    stringBuilder.setCharAt(i, ' ');
                }
                if (count >= 1 && c == ',') {
                    stringBuilder.setCharAt(i, ' ');

                } else if (count == 0 && c == ',') {
                    stringBuilder.setCharAt(i, '.');
                    ++count;
                }
                i++;
            }
            string = stringBuilder.toString().replaceAll(" ", "");
            if (!isNumber) {
                return 0.0;
            }
            return Double.parseDouble(string);
        }
        return 0.0;
    }

    private Map<String, Double> dataProcessing() {
        Car car = equipment.getCar();
        EngineCharacteristics characteristics = equipment.getEngine().getEngineCharacteristics();
        Transmission transmission = equipment.getTransmission();
        Gearbox gearbox = equipment.getTransmission().getGearbox();
        Turbocharger turbocharger = equipment.getTurbocharger();
        Map<String, Double> data = new HashMap<>(
                Map.of(
                        "carWeight", getDouble(car.getWeight()),
                        "engineCylinders", getDouble(characteristics.getCylinders()),
                        "enginePower", getDouble(characteristics.getPower()),
                        "engineCompression", getDouble(characteristics.getCompression()),
                        "engineVolume", getDouble(characteristics.getVolume()),
                        "engineTorque", getDouble(characteristics.getTorque()),
                        "enginePiston", getDouble(characteristics.getPiston()),
                        "transmissionStairs", getDouble(String.valueOf(gearbox.getStairs())),
                        "turbochargerPowerInTake", getDouble(turbocharger.getPowerIntake()),
                        "turbochargerPowerGeneration", getDouble(turbocharger.getPowerGeneration())
                )
        );
        data.put("engineStartPower", getDouble(characteristics.getStartPower()));
        data.put("engineRpm", getDouble(characteristics.getRpm()));
        data.put("transmissionFinalRatio", getDouble(transmission.getFinalRatio()));
        data.put("transmissionRatio", getDouble(transmission.getRatio()));
        return data;
    }

    public String acceleration() {
        Map<String, Double> data = dataProcessing();
        Double engineStartPower = data.get("engineStartPower");
        Double enginePower = data.get("enginePower");
        Double turbochargerPowerGeneration = data.get("turbochargerPowerGeneration");
        Double turbochargerPowerInTake = data.get("turbochargerPowerInTake");
        Double carWeight = data.get("carWeight");
        DoubleStream doubleStream = DoubleStream.builder()
                .add(engineStartPower).add(enginePower).add(turbochargerPowerGeneration).add(turbochargerPowerInTake)
                .add(carWeight).build();
        for (Double i : doubleStream.toArray()) {
            if (i == 0.0) return null;
        }
        double hp = (engineStartPower + enginePower + turbochargerPowerGeneration - turbochargerPowerInTake) / 2;
        hp = (hp * 735 * 0.8) / 1000;
        double t = (carWeight * Math.pow(28, 2)) / (2 * hp * 1000);
        NumberFormat instance = NumberFormat.getInstance();
        instance.setMinimumFractionDigits(2);
        return instance.format(t);
    }

    public Integer maxSpeed() {
        Map<String, Double> data = dataProcessing();
        Double engineRpm = data.get("engineRpm");
        Double transmissionFinalRatio = data.get("transmissionFinalRatio");
        Double transmissionRatio = data.get("transmissionRatio");
        DoubleStream doubleStream = DoubleStream.builder()
                .add(engineRpm).add(transmissionFinalRatio)
                .add(transmissionRatio).build();
        for (Double i : doubleStream.toArray()) {
            if (i == 0.0) return null;
        }
        double maxSpeed = engineRpm * 24 * transmissionFinalRatio * transmissionRatio * 60 / 5280.0 / 100.0 * 1.609;
        return (int) maxSpeed;
    }
}
