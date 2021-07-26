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
public class InspectionTest extends UnitTest{
    public InspectionTest(){
        super("Inspection Test");
    }
    @Override
    public boolean basicTest() {
        double inspectionCost = 0;
        Cost waferCost = new Cost();
        waferCost.setOverallThroughput(400);
        inspectionCost = waferCost.inspection();
        boolean result = Math.abs(inspectionCost - 0.00641) <= 0.0001;
        assertion(result, "Basic Test");
        return result;
    }

    @Override
    public void postInitialize() {
    }
    
}
