package org.mickey.framework.e2b.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * description
 *
 * @author mickey
 * 2020-02-24
 */
@Slf4j
@Data
public class R3UUIDUtil {
    public static final String DELIMITEd="-";
    public static String uuidR3Format(String uuid) {
        if(StringUtils.isBlank(uuid)){
            return null;
        }
        //8a81c095646b353e01646ebecc5a35a6
        if(uuid.contains("-")) {
            return uuid;
        }
        String sec1 = uuid.substring(0,8);
        String sec2= uuid.substring(8,12);
        String sec3 = uuid.substring(12,16);
        String sec4= uuid.substring(16,20);
        String sec5 = uuid.substring(20);
        return sec1 + DELIMITEd + sec2 + DELIMITEd + sec3 +DELIMITEd + sec4 +DELIMITEd + sec5;
    }
    public static void main(String[] args) {
        System.out.println(uuidR3Format("8a81c095646b353e01646ebecc5a35a6"));
    }
}
