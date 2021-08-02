/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vplTest;

import vpl.Cost;

/**
 *
 * @author Quanzhou Yu
 */
public class ElectricityCostTest extends UnitTest{
    public ElectricityCostTest(){
        super("Electricity Cost Test");
    }
    @Override
    public boolean basicTest() {
        boolean result;
        double elecRate = 60.036;
        double workHours = 8257.437;
        double electricityCost;
        Cost waferCost = new Cost();
        electricityCost = waferCost.electricityCost(workHours, elecRate);
        result = Math.abs(electricityCost - 42138.2) <= 0.1;
        assertion(result, "Basic Test");
        return result;
    }

    @Override
    public void postInitialize() {
    
    }
}
