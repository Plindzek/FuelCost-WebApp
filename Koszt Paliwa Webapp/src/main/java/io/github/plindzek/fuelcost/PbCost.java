package io.github.plindzek.fuelcost;

import io.github.plindzek.car.Car;
import io.github.plindzek.util.AvgFuelPricesScrapper;

/**
 * @author Adam
 */
class PbCost implements FuelCost {

    @Override
    public double calculateFuelCost(Car car, Trip trip) {

        return (Math.round((car.getPbOn100Km() * AvgFuelPricesScrapper.getAvgPbPrice()* trip.getKmOnPb() / 100) * 100.0))
                / 100.0;

    }
}
