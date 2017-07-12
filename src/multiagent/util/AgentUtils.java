/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiagent.util;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import multiagent.MultiAgent;

/**
 *
 * @author Marcel_Meinerz (marcel.meinerz@th-bingen.de)
 * @author Steffen_Hollenbach
 * @author Jasmin_Welschbillig
 * 
 * @version 1.0
 */
public class AgentUtils {
    
    /**
     *
     */
    public static final String GO = "go";

    /**
     *
     */
    public static final String PUT = "put";

    /**
     *
     */
    public static final String TAKE = "take";

    /**
     *
     */
    public static final String CHECK = "check";

    /**
     *
     */
    public static final String LEFT = "l";

    /**
     *
     */
    public static final String RIGHT = "r";

    /**
     *
     */
    public static final String TOP = "t";

    /**
     *
     */
    public static final String BOTTOM = "b";

    /**
     *
     */
    public static final String BUY = "buy";
    
    /**
     *
     */
    public static final String ROT = "#f77462";

    /**
     *
     */
    public static final String BLAU = "#659bf2";

    /**
     *
     */
    public static final String GELB = "#f7f35b";

    /**
     *
     */
    public static final String CYAN = "#60e3f2";

    /**
     *
     */
    public static final String LILA = "#b091e0";

    /**
     *
     */
    public static final String ORANGE = "#ffa500";

    /**
     *
     */
    public static final String ROSA = "#f2a7c2";
    
    /**
     *
     */
    public static  final Color [] COLORS = new Color[]{
    	Color.decode(ROT), //rot 
    	Color.decode(BLAU), //blau
    	Color.decode(GELB), //gelb 
    	Color.decode(CYAN), //cyan
    	Color.decode(LILA), //lila 
    	Color.decode(ORANGE), //orange 
    	Color.decode(ROSA), //rosa 
    	Color.WHITE
    	};

    /**
     *
     * @param resource
     * @param type
     * @return
     */
    public static File getFile(String resource, String type){
        File file = null;
        URL res = MultiAgent.class.getResource("/multiagent/resources/"+resource);
        if (res.toString().startsWith("jar:")) {
            try {
                InputStream input = MultiAgent.class.getResourceAsStream("/multiagent/resources/"+resource);
                file = File.createTempFile("tempfile", "."+type);
                OutputStream out = new FileOutputStream(file);
                int read;
                byte[] bytes = new byte[1024];

                while ((read = input.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                file.deleteOnExit();
            } catch (IOException ex) {
            }
        } else {
            //this will probably work in your IDE, but not from a JAR
            file = new File(res.getFile());
        }

        if (file != null && !file.exists()) {
            throw new RuntimeException("Error: File " + file + " not found!");
        }
        
        return file;
    }
}



