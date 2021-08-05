package vpl;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Element;


/**
 *
 * @author Quanzhou Yu
 */
public class Constant {
    public static ConstantDocument document;
    public static int ConstantInstanceNumber = 0;
    public static void initialize(){
        if(ConstantInstanceNumber < 1)
        {
            String urlPrefix = "file:" + System.getProperty("user.dir")
            + System.getProperty("file.separator");
            String filename = "config.dat";
            // try {
                
                document = new ConstantDocument(Constant.class.getClassLoader().getResourceAsStream(filename));
            // } catch (MalformedURLException ex) {
            //     Logger.getLogger(Constant.class.getName()).log(Level.SEVERE, null, ex);
            // }
            ConstantInstanceNumber++;
        }
    }
    public static double getProcessConstant(String location){
        String[] locations = location.split("/");
        Element e = document.getScopeByName("Process");
        for(int i = 0;i < locations.length - 1; i++){
            e = document.getElementByName(e, locations[i]);
        }
        double result = Double.parseDouble(document.getElementContent(e, locations[locations.length - 1]));
        return result;
    }
    public static double getGlobalConstant(String location){
        initialize();
        String[] locations = location.split("/");
        Element e = document.getScopeByName("Global");
        for(int i = 0;i < locations.length - 1; i++){
            e = document.getElementByName(e, locations[i]);
        }
        double result = Double.parseDouble(document.getElementContent(e, locations[locations.length - 1]));
        return result;
    }
}
