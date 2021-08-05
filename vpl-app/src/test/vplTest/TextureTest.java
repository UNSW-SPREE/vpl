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
public class TextureTest extends UnitTest{
    public TextureTest(){
        super("Texture Test");
    }

    @Override
    public boolean basicTest() {
        double textureCost = 0;
        Cost waferCost = new Cost();
        waferCost.setOverallThroughput(400);
        textureCost = waferCost.texture(25, 80, 2, 5, 2);
        boolean result = Math.abs(textureCost - 0.0249) <= 0.0001;
        assertion(result, "Basic Test");
        return result;
    }

    @Override
    public void postInitialize() {
        addTest("temperatureChangeTest");
    }
    public boolean temperatureChangeTest(){
        double tempChangeCost = 0;
        Cost waferCost = new Cost();
        waferCost.setOverallThroughput(400);
        tempChangeCost = waferCost.texture(25, 20, 2, 5, 2);
        boolean result = Math.abs(tempChangeCost - 0.0230) <= 0.0001;
        assertion(result, "Temperature Change ");
        return result;
    }
}
