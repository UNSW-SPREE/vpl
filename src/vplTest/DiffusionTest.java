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
public class DiffusionTest extends UnitTest{
    public DiffusionTest(){
        super("Diffusion Test");
    }

    @Override
    public boolean basicTest() {
        boolean result;
        double diffusionCost = 0;
        double dilution = 40;
        double gasFlow = 25;
        Cost waferCost = new Cost();
        diffusionCost = waferCost.spinOnDiffusion(0.1, 180, 650, 865, 650, gasFlow, dilution);
        result = Math.abs(diffusionCost - 0.0101) <= 0.0001;
        assertion(result, "Basic Test");
        return result;
    }

    @Override
    public void postInitialize() {
    
    }
    
}
