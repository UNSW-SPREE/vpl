package vpl;
import java.io.Serializable;

public class Cost implements Serializable{
    private double overallThroughput = 400;
    /*
     * class property
     */
    private double waferCost;
    private double electricityCost;
    private double waterChemicalCost;
    private double labourCost;
    private double equipmentCost;
    private double buildingCost;
    private double gasCost;
    private double printingScreenCost;
    private double printingPastesCost;
    private double totalCost;
    private double exchangeRate;//from EUR to AU
    private double depriciation;
    public Cost(){
        electricityCost = 0;
        waterChemicalCost = 0;
        equipmentCost = 0;
        gasCost = 0;
        printingScreenCost = 0;
        printingPastesCost = 0;
        totalCost = 0;
        exchangeRate = 0;
        Constant.initialize();
        depriciation = Constant.getGlobalConstant("Depriciation");
        exchangeRate = Constant.getGlobalConstant("ExchangeRate");
        waferCost = Constant.getGlobalConstant("WaferPrice");
        labourCost = Constant.getGlobalConstant("LabourCost");
        buildingCost = Constant.getGlobalConstant("BuildingCost");
        equipmentCost += Constant.getGlobalConstant("AutomationCost");
    }
    public double inspection(){
        double upfront = Constant.getProcessConstant("Inspection/Equipment/UpfrontCost");
        double upTime = Constant.getProcessConstant("Inspection/Equipment/Uptime");
        double equipThroughput = Constant.getProcessConstant("Inspection/Equipment/Throughput");
        double elecRate = Constant.getProcessConstant("Inspection/Equipment/ElectricityConsumptionRate");
        double yield = Constant.getProcessConstant("Inspection/Equipment/Yield");
        double breakageRate = Constant.getProcessConstant("Inspection/Equipment/BreakageRate");
        double capex = equipmentCost(upfront, upTime, equipThroughput, yield, breakageRate);
        double totalWaferPerYear = totalWaferPerYear(upTime, equipThroughput, yield, breakageRate);
        //Electricity Cost
        double workHour = workHoursPerYear(upTime, yield);
        double elecCost = electricityCost(workHour, elecRate) / totalWaferPerYear;
        equipmentCost += capex;
        electricityCost += elecCost;
        return elecCost + capex;
    }
    /*
     * @chemConc: int  30 for 30%
     * @etchTime: min
     */
    public double etch(double bathTemp, double chemConc, double etchTime){
        //equipment
        double upfront = Constant.getProcessConstant("Etch/Equipment/UpfrontCost");
        double upTime = Constant.getProcessConstant("Etch/Equipment/Uptime");
        double equipThroughput = Constant.getProcessConstant("Etch/Equipment/Throughput");
        double elecRate = Constant.getProcessConstant("Etch/Equipment/ElectricityConsumptionRate");
        double yield = Constant.getProcessConstant("Etch/Equipment/Yield");
        double breakageRate = Constant.getProcessConstant("Etch/Equipment/BreakageRate");
        double capex = equipmentCost(upfront, upTime, equipThroughput, yield, breakageRate);
        double totalWaferPerYear = totalWaferPerYear(upTime, equipThroughput, yield, breakageRate);
        //temperature affect
        elecRate = (1+(bathTemp-85)/100)*elecRate;
        //Electricity Cost
        double workHour = workHoursPerYear(upTime, yield);
        double elecCost = electricityCost(workHour, elecRate) / totalWaferPerYear;
        //material cost
        etchTime /= 60;
        chemConc /= 100;
        double waferPerBatch = Constant.getProcessConstant("Etch/WaferPerBatch");
        double sumpValue = Constant.getProcessConstant("Etch/SumpValue");
        double bleedRate = Constant.getProcessConstant("Etch/BleedRate");
        double kohPrice = Constant.getProcessConstant("Etch/KOH/Price");
        double diWaterPrice = Constant.getProcessConstant("Etch/Water/Price");
        double throughput = waferPerBatch / (etchTime);
        
        double kohTotalVolume = chemConc * sumpValue;
        double waterTotalVolume = (1 - chemConc) * sumpValue;
        
        double kohConsumptionPerHour = kohTotalVolume * bleedRate;
        double waterConsumptionPerHour = waterTotalVolume * bleedRate;
        
        double kohConsumptionPerWafer = kohConsumptionPerHour/throughput;
        double waterConsumptionPerWafer = waterConsumptionPerHour/throughput;
        double kohCost = kohConsumptionPerWafer * kohPrice;
        double waterCost = waterConsumptionPerWafer * diWaterPrice;
        double materialCost = kohCost + waterCost;
        waterChemicalCost = waterChemicalCost + materialCost;
        electricityCost += elecCost;
        equipmentCost += capex;
        return materialCost + capex + elecCost;
    }
    public void sawRemovalClean(){
        
    }
    public double texture(double textureTime, double temp, double chemConc, double propanol, double efr){
        //equipment
        double upfront = Constant.getProcessConstant("Texture/Equipment/UpfrontCost");
        double upTime = Constant.getProcessConstant("Texture/Equipment/Uptime");
        double equipThroughput = Constant.getProcessConstant("Texture/Equipment/Throughput");
        double elecRate = Constant.getProcessConstant("Texture/Equipment/ElectricityConsumptionRate");
        double yield = Constant.getProcessConstant("Texture/Equipment/Yield");
        double breakageRate = Constant.getProcessConstant("Texture/Equipment/BreakageRate");
        double capex = equipmentCost(upfront, upTime, equipThroughput, yield, breakageRate);
        double totalWaferPerYear = totalWaferPerYear(upTime, equipThroughput, yield, breakageRate);
        //Electricity Cost
        elecRate = (1 + (temp - 85) / 100) * elecRate;
        double workHour = workHoursPerYear(upTime, yield);
        double elecCost = electricityCost(workHour, elecRate) / totalWaferPerYear;
        //material
        textureTime /= 60;
        chemConc /= 100;
        propanol /= 100;
        double evaporationRate = Constant.getProcessConstant("Texture/EvaporationRate");
        double waferPerBatch = Constant.getProcessConstant("Texture/WaferPerBatch");
        double sumpValue = Constant.getProcessConstant("Texture/SumpValue");
        double bleedRate = Constant.getProcessConstant("Texture/BleedRate");
        double ipaPrice = Constant.getProcessConstant("Texture/Material/IPA/Price");
        double diWaterPrice = Constant.getProcessConstant("Texture/Material/Water/Price");
        double throughput = waferPerBatch / (textureTime);
        evaporationRate = (1+(efr-2)/2)*evaporationRate;
        
        double ipaTotalVolume = propanol * sumpValue;
        double waterTotalVolume = (1 - chemConc - propanol) * sumpValue;
        
        double ipaConsumptionPerHour = ipaTotalVolume * bleedRate + ipaTotalVolume * evaporationRate;
        double waterConsumptionPerHour = waterTotalVolume * bleedRate;
        
        double ipaConsumptionPerWafer = ipaConsumptionPerHour/throughput;
        double waterConsumptionPerWafer = waterConsumptionPerHour/throughput;
        
        double ipaCost = ipaConsumptionPerWafer * ipaPrice;
        double waterCost = waterConsumptionPerWafer * diWaterPrice;
        double materialCost = waterCost + ipaCost;
        waterChemicalCost = waterChemicalCost + materialCost;
        equipmentCost += capex;
        electricityCost += elecCost;
        return materialCost + capex + elecCost;
    }
    public void textureClean(){
    }
    
    public double spinOnDiffusion(double beltSpeed, double dryingTemp, 
                          double zone1Temp, double zone2Temp, 
                          double zone3Temp, double gasFlow, double dilutionFactor){
        
        //equipment
        double upfront = Constant.getProcessConstant("SpinOnDiffusion/Equipment/UpfrontCost");
        double upTime = Constant.getProcessConstant("SpinOnDiffusion/Equipment/Uptime");
        double equipThroughput = Constant.getProcessConstant("SpinOnDiffusion/Equipment/Throughput");
        double elecRate = Constant.getProcessConstant("SpinOnDiffusion/Equipment/ElectricityConsumptionRate");
        double yield = Constant.getProcessConstant("SpinOnDiffusion/Equipment/Yield");
        double breakageRate = Constant.getProcessConstant("SpinOnDiffusion/Equipment/BreakageRate");
        double capex = equipmentCost(upfront, upTime, equipThroughput, yield, breakageRate);
        double totalWaferPerYear = totalWaferPerYear(upTime, equipThroughput, yield, breakageRate);
        //material
        double phosphorusConsumption = Constant.getProcessConstant("SpinOnDiffusion/Material/H3PO4/Consumption");
        double phosphorusPrice = Constant.getProcessConstant("SpinOnDiffusion/Material/H3PO4/Price");
        double nitrogenConsumption = Constant.getProcessConstant("SpinOnDiffusion/Material/N2/Consumption");
        double nitrogenPrice = Constant.getProcessConstant("SpinOnDiffusion/Material/N2/Price");
        double nitrogenCost = nitrogenConsumption / 25 * gasFlow * nitrogenPrice;
        double phosphorusCost = phosphorusConsumption / 40 * phosphorusPrice;
        double gas = nitrogenCost /totalWaferPerYear; 
        double chemicalCost = phosphorusCost / totalWaferPerYear; 
        //increased belt speed will decreases gas and chemical cost
        gas = (1-(beltSpeed - 0.1)/1.5)*gas;
        chemicalCost = (1-(chemicalCost - 0.1)/1.5)*chemicalCost;
        double workHour = workHoursPerYear(upTime, yield);
        double elecCost = electricityCost(workHour, elecRate) / totalWaferPerYear;
        gasCost += gas;
        electricityCost += elecCost;
        equipmentCost += capex;
        waterChemicalCost += chemicalCost;
        return capex + gas + elecCost + chemicalCost;
    }
    
    public double plasmaEtch(double power, double petime){
        double upfront = Constant.getProcessConstant("PlasmaEtch/Equipment/UpfrontCost");
        double upTime = Constant.getProcessConstant("PlasmaEtch/Equipment/Uptime");
        double equipThroughput = Constant.getProcessConstant("PlasmaEtch/Equipment/Throughput");
        double yield = Constant.getProcessConstant("PlasmaEtch/Equipment/Yield");
        double breakageRate = Constant.getProcessConstant("PlasmaEtch/Equipment/BreakageRate");
        double capex = equipmentCost(upfront, upTime, equipThroughput, yield, breakageRate);
        double totalWaferPerYear = totalWaferPerYear(upTime, equipThroughput, yield, breakageRate);
        //Electricity Cost
        double elecRate = power / 1000;
        double workHour = workHoursPerYear(upTime, yield);
        double elecCost = electricityCost(workHour, elecRate) / totalWaferPerYear;
        electricityCost += elecCost;
        equipmentCost += capex;
        return elecCost + capex;
    }
    public double arCoating(double artime, double temp, double gasFlow1, double gasFlow2){ 
        
        //equipment cost
        double upfront = Constant.getProcessConstant("ARCoating/Equipment/UpfrontCost");
        double upTime = Constant.getProcessConstant("ARCoating/Equipment/Uptime");
        double equipThroughput = Constant.getProcessConstant("ARCoating/Equipment/Throughput");
        double elecRate = Constant.getProcessConstant("ARCoating/Equipment/ElectricityConsumptionRate");
        double yield = Constant.getProcessConstant("ARCoating/Equipment/Yield");
        double breakageRate = Constant.getProcessConstant("ARCoating/Equipment/BreakageRate");
        double capex = equipmentCost(upfront, upTime, equipThroughput, yield, breakageRate);
        double totalWaferPerYear = totalWaferPerYear(upTime, equipThroughput, yield, breakageRate);
        //material cost
        double SiH4Consumption = Constant.getProcessConstant("ARCoating/Material/SIH4/Consumption");
        double SiH4Price = Constant.getProcessConstant("ARCoating/Material/SIH4/Price");
        double NH3Consumption = Constant.getProcessConstant("ARCoating/Material/NH3/Consumption");
        double NH3Price = Constant.getProcessConstant("ARCoating/Material/NH3/Price");
        double baseSiH4Consumption = SiH4Consumption/3;
        double baseNH3Consumption = NH3Consumption/4;
        double realSiH4Consumption = baseSiH4Consumption * gasFlow1;
        double realNH3Consumption = baseNH3Consumption * gasFlow2;
        double gas = (realSiH4Consumption * SiH4Price + realNH3Consumption * NH3Price)/totalWaferPerYear;
        double workHour = workHoursPerYear(upTime, yield);
        double elecCost = electricityCost(workHour, elecRate) / totalWaferPerYear;
        gasCost += gas;
        electricityCost += elecCost;
        equipmentCost += capex;
        return capex + gas + elecCost;
    }
    public double silverScreenPrint(double meshDensity, double emulsionThicknessAbove,
                                    double emulsionThicknessBelow, double fingerSpacing, 
                                    double strandDiameter, double fingerWidth){ 
        //equipment cost
        double upfront = Constant.getProcessConstant("SilverScreenPrint/Equipment/UpfrontCost");
        double upTime = Constant.getProcessConstant("SilverScreenPrint/Equipment/Uptime");
        double equipThroughput = Constant.getProcessConstant("SilverScreenPrint/Equipment/Throughput");
        double elecRate = Constant.getProcessConstant("SilverScreenPrint/Equipment/ElectricityConsumptionRate");
        double yield = Constant.getProcessConstant("SilverScreenPrint/Equipment/Yield");
        double breakageRate = Constant.getProcessConstant("SilverScreenPrint/Equipment/BreakageRate");
        double capex = equipmentCost(upfront, upTime, equipThroughput, yield, breakageRate);
        double totalWaferPerYear = totalWaferPerYear(upTime, equipThroughput, yield, breakageRate);
        //Electricity Cost
        double workHour = workHoursPerYear(upTime, yield);
        double elecCost = electricityCost(workHour, elecRate) / totalWaferPerYear;
        //Screen Cost
        double screenConsumptionRate = Constant.getProcessConstant("SilverScreenPrint/Material/Screen/ConsumptionRate");
        double screenPrice = Constant.getProcessConstant("SilverScreenPrint/Material/Screen/Price");
        double screenCost = screenPrice / screenConsumptionRate;
        //Pastes Cost
        double busbarWidth = Constant.getProcessConstant("SilverScreenPrint/Busbar/Width");
        double busbarNumber = Constant.getProcessConstant("SilverScreenPrint/Busbar/Number");
        double pastesDensity = Constant.getProcessConstant("SilverScreenPrint/Material/SilverPaste/Density");
        double pastesPrice = Constant.getProcessConstant("SilverScreenPrint/Material/SilverPaste/Price");
        double cellSize = Constant.getGlobalConstant("CellSize");
        
        double fingerNum = cellSize * 10 / fingerSpacing;
        double silverPastesThickness = emulsionThicknessBelow/10000*(1-(meshDensity-50)/210);
        double fingerCrossSectionalArea = silverPastesThickness * fingerWidth / 10000;
        double busbarCrossSectionalArea = silverPastesThickness * busbarWidth;
        double fingerPastesVolume = fingerCrossSectionalArea * cellSize * fingerNum;
        double busbarPastesVolume = busbarCrossSectionalArea * cellSize * busbarNumber;
        double pastesConsumptionVolume = fingerPastesVolume + busbarPastesVolume;
        double pastesConsumptionMass = pastesConsumptionVolume * pastesDensity;
        double pastesCost = pastesConsumptionMass * pastesPrice / 1000;
        equipmentCost += capex;
        electricityCost += elecCost;
        printingScreenCost += screenCost;
        printingPastesCost += pastesCost;
        return capex + elecCost + screenCost + pastesCost;
    }
    public double alScreenSetup(double meshDensity, double emulsionThicknessAbove,
                                double emulsionThicknessBelow, double strandDiameter, 
                                String pattern){
        //equipment cost
        double upfront = Constant.getProcessConstant("AlScreenSetup/Equipment/UpfrontCost");
        double upTime = Constant.getProcessConstant("AlScreenSetup/Equipment/Uptime");
        double equipThroughput = Constant.getProcessConstant("AlScreenSetup/Equipment/Throughput");
        double elecRate = Constant.getProcessConstant("AlScreenSetup/Equipment/ElectricityConsumptionRate");
        double yield = Constant.getProcessConstant("AlScreenSetup/Equipment/Yield");
        double breakageRate = Constant.getProcessConstant("AlScreenSetup/Equipment/BreakageRate");
        double capex = equipmentCost(upfront, upTime, equipThroughput, yield, breakageRate);
        double totalWaferPerYear = totalWaferPerYear(upTime, equipThroughput, yield, breakageRate);
        //Electricity Cost
        double workHour = workHoursPerYear(upTime, yield);
        double elecCost = electricityCost(workHour, elecRate) / totalWaferPerYear;
        //Material Cost
        //Screen Cost
        double screenConsumptionRate = Constant.getProcessConstant("AlScreenSetup/Material/Screen/ConsumptionRate");
        double screenPrice = Constant.getProcessConstant("AlScreenSetup/Material/Screen/Price");
        double screenCost = screenPrice / screenConsumptionRate;
        //Pastes Cost
        double silverTabWidth = Constant.getProcessConstant("AlScreenSetup/Tab/Width");
        double silverTabNumber = Constant.getProcessConstant("AlScreenSetup/Tab/Number");
        double silverPastesDensity = Constant.getProcessConstant("AlScreenSetup/Material/SilverPaste/Density");
        double silverPastesPrice = Constant.getProcessConstant("AlScreenSetup/Material/SilverPaste/Price");
        double alPastesDensity = Constant.getProcessConstant("AlScreenSetup/Material/AluminiumPaste/Density");
        double alPastesPrice = Constant.getProcessConstant("AlScreenSetup/Material/AluminiumPaste/Price");
        double cellSize = Constant.getGlobalConstant("CellSize");
        double cellArea = Constant.getGlobalConstant("CellArea");
        
        double pastesConsumptionThickness = emulsionThicknessBelow / 10000*
                                            (1-(meshDensity-50)/210)*
                                            (1-(strandDiameter-20)/130);
        double alPastesConsumptionVolume = cellArea * pastesConsumptionThickness;
        double alPastesConsumptionMass = alPastesConsumptionVolume * alPastesDensity;
        double silverTabCrossSectionalArea = pastesConsumptionThickness * silverTabWidth;
        double silverTabConsumptionVolume = silverTabCrossSectionalArea * cellSize * silverTabNumber;
        double silverPastesConsumptionMass = silverTabConsumptionVolume * silverPastesDensity;
        double pastesCost = alPastesConsumptionMass * alPastesPrice / 1000 + 
                            silverPastesConsumptionMass * silverPastesPrice / 1000;
        printingPastesCost += pastesCost;
        printingScreenCost += screenCost;
        equipmentCost += capex;
        electricityCost += elecCost;
        return capex + elecCost + screenCost + pastesCost;
    }
    public double cofiring(double beltSpeed, double dryingTemp, double zone1Temp, 
                           double zone2Temp, double zone3Temp,double gasFlow, double o2Percent){
        //gas cost
        double nitrogenCost = Constant.getProcessConstant("Cofiring/NitrogenCost");
        double oxygenCost = Constant.getProcessConstant("Cofiring/OxygenCost");
        nitrogenCost = (1 + (gasFlow - 10)/20) * nitrogenCost;
        oxygenCost = (1 + (o2Percent - 30)/100) * oxygenCost;
        double gas = oxygenCost + nitrogenCost;
        //equipment cost
        double upfront = Constant.getProcessConstant("Cofiring/Equipment/UpfrontCost");
        double upTime = Constant.getProcessConstant("Cofiring/Equipment/Uptime");
        double equipThroughput = Constant.getProcessConstant("Cofiring/Equipment/Throughput");
        double elecRate = Constant.getProcessConstant("Cofiring/Equipment/ElectricityConsumptionRate");
        double yield = Constant.getProcessConstant("Cofiring/Equipment/Yield");
        double breakageRate = Constant.getProcessConstant("Cofiring/Equipment/BreakageRate");
        double capex = equipmentCost(upfront, upTime, equipThroughput, yield, breakageRate);
        double totalWaferPerYear = totalWaferPerYear(upTime, equipThroughput, yield, breakageRate);
        //Electricity Cost
        //affected by temperature
        elecRate = (1+(dryingTemp + zone1Temp + zone2Temp + zone3Temp - 2250)/400)*elecRate;
        double workHour = workHoursPerYear(upTime, yield);
        double elecCost = electricityCost(workHour, elecRate) / totalWaferPerYear;
        electricityCost += elecCost;
        equipmentCost += capex;
        gasCost += gas;
        return elecCost + capex + gas;
    }
    /*
     * upfront cost: EUR
     */
    public double equipmentCost(double upfront, double uptime, double throughput, 
                                double yield, double breakageRate){
        upfront *= exchangeRate;
        double actualWorkingHours = uptime * yield;
        double totalWaferNum = actualWorkingHours * overallThroughput * (1 - breakageRate);
        double totalCapex = upfront / depriciation;
        double cost = totalCapex / totalWaferNum;
        return cost;
    }
    public double electricityCost(double workHour, double elecRate){
            double electricityPrice = Constant.getGlobalConstant("ElectricityPrice");
            double electricityCost = workHour * electricityPrice;
            return electricityCost;
    }
    public double workHoursPerYear(double uptime, double yield){
        double actualWorkingHours = uptime * yield;
        return actualWorkingHours;
    }
    public double totalWaferPerYear(double uptime, double throughput, double yield, double breakageRate){
        double actualWorkingHours = workHoursPerYear(uptime, yield);
        double totalWaferNum = actualWorkingHours;
        return totalWaferNum;
    }
    public double getTotalWaferCost(){
        totalCost = (electricityCost + waterChemicalCost + 
                    labourCost + equipmentCost +
                    buildingCost + gasCost +
                    printingScreenCost + printingPastesCost+
                    waferCost) * 100;
        return totalCost;
    }
    public void setOverallThroughput(double t){
        overallThroughput = t;
    }
    public double getEquipmentCost(){
        return this.equipmentCost;
    }
}
