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
public class ARCoatingTest extends UnitTest {
    public ARCoatingTest(){
        super("SAR Coating Test");
    }
    @Override
    public boolean basicTest() {
        boolean result;
        double arCoatingCost = 0;
        Cost waferCost = new Cost();
        waferCost.setOverallThroughput(400);
        arCoatingCost = waferCost.arCoating(17, 200, 3, 4);
        result = Math.abs(arCoatingCost - 0.0215) <= 0.0001;
        assertion(result, "Basic Test");
        return result;
    }

    @Override
    public void postInitialize() {
    }
    
}
