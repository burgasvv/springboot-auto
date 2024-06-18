package com.burgas.springbootauto.calculations;

import com.burgas.springbootauto.entity.car.Car;
import com.burgas.springbootauto.entity.car.Equipment;
import com.burgas.springbootauto.entity.engine.EngineCharacteristics;
import com.burgas.springbootauto.entity.transmission.Gearbox;
import com.burgas.springbootauto.entity.turbocharging.Turbocharger;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

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
        return data;
    }

    public Double acceleration() {
        // t = {m(Delta V)^2}/{2P}
        Map<String, Double> data = dataProcessing();
        double hp = (data.get("engineStartPower") + data.get("enginePower") +
                data.get("turbochargerPowerGeneration") - data.get("turbochargerPowerInTake")) / 2;
        hp = (hp * 735 * 0.8) / 1000;
        double t = (data.get("carWeight") * Math.pow(28, 2)) / (2 * hp * 1000);
        NumberFormat instance = NumberFormat.getInstance();
        instance.setMinimumFractionDigits(2);
        return getDouble(instance.format(t));
    }

    public Double maxSpeed() {
        Map<String, Double> data = dataProcessing();
        return data.get("maxSpeed");
    }
}
