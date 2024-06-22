package com.burgas.springbootauto.calculations;

import com.burgas.springbootauto.entity.car.Car;
import com.burgas.springbootauto.entity.car.Equipment;
import com.burgas.springbootauto.entity.engine.EngineCharacteristics;
import com.burgas.springbootauto.entity.transmission.Transmission;
import com.burgas.springbootauto.entity.turbocharging.Turbocharger;
import lombok.*;

import java.text.NumberFormat;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentDataProcessing {

    private Equipment equipment;

    public Double getDouble(String string) {
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
        Map<String, Double> data = new HashMap<>();
        if (equipment.getCar() != null) {
            Car car = equipment.getCar();
            data.put("carWeight", getDouble(car.getWeight()));
        }
        if (equipment.getEngine() != null) {
            EngineCharacteristics characteristics = equipment.getEngine().getEngineCharacteristics();
            data.put("engineCylinders", getDouble(characteristics.getCylinders()));
            data.put("enginePower", getDouble(characteristics.getPower()));
            data.put("engineVolume", getDouble(characteristics.getVolume()));
            data.put("engineTorque", getDouble(characteristics.getTorque()));
            data.put("engineRpm", getDouble(characteristics.getRpm()));
        }
        if (equipment.getTransmission() != null) {
            Transmission transmission = equipment.getTransmission();
            data.put("transmissionFinalRatio", getDouble(transmission.getFinalRatio()));
            data.put("transmissionRatio", getDouble(transmission.getRatio()));
        }
        if (equipment.getTurbocharger() != null) {
            Turbocharger turbocharger = equipment.getTurbocharger();
            data.put("turbochargerRpm", getDouble(turbocharger.getRpm()));
            data.put("turbochargerPower", getDouble(turbocharger.getPower()));
            data.put("turbochargerTorque", getDouble(turbocharger.getTorque()));
        }
        return data;
    }

    public String acceleration() {
        Map<String, Double> data = dataProcessing();
        Double enginePower = data.get("enginePower");
        Double turbochargerPower = data.get("turbochargerPower");
        Double carWeight = data.get("carWeight");
        if (enginePower == null || turbochargerPower == null || carWeight == null) {
            return null;
        }
        double hp = (enginePower + turbochargerPower) / 2;
        hp = (hp * 735 * 0.8) / 1000;
        double t = (carWeight * Math.pow(28, 2)) / (2 * hp * 1000);
        NumberFormat instance = NumberFormat.getInstance();
        instance.setMinimumFractionDigits(2);
        return instance.format(t);
    }

    public Integer maxSpeed() {
        Map<String, Double> data = dataProcessing();
        Double engineRpm = data.get("engineRpm");
        Double turbochargerRpm = data.get("turbochargerRpm");
        Double transmissionFinalRatio = data.get("transmissionFinalRatio");
        Double transmissionRatio = data.get("transmissionRatio");
        if (engineRpm == null || turbochargerRpm == null || transmissionFinalRatio == null || transmissionRatio == null) {
            return null;
        }
        double maxSpeed = (engineRpm + turbochargerRpm) * 24 * transmissionFinalRatio * transmissionRatio * 60 / 5280.0 / 100.0 * 1.609;
        return (int) maxSpeed;
    }
}
