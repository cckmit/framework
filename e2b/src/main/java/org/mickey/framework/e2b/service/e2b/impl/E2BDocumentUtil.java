package org.mickey.framework.e2b.service.e2b.impl;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.tree.DefaultText;
import org.mickey.framework.e2b.constant.E2BImportConfigConstant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 2020-02-24
 */
@Slf4j
@Data
public class E2BDocumentUtil {
    public static final String R3NSValue="urn:hl7-org:v3";
    public static final String NSKey="default";
    public static final String xsiKey="xsi";
    public static final String xsiValue="http://www.w3.org/2001/XMLSchema-instance";
    public static final Map<String,String> nsMap = new HashMap<>();

    static {
        nsMap.put(NSKey,R3NSValue);
        nsMap.put(xsiKey,xsiValue);
    }

    public static Node getSpecificSafetyReportNode(Document document, String e2bNumber) {
        List<Node> reportIdNodes = document.selectNodes("//safetyreportid");

        Node safetyreportNode = null;
        if (CollectionUtils.isEmpty(reportIdNodes)) {
            return null;
        }

        //从文件中选择对应的safetyreport节点
        for(Node reportIdNode: reportIdNodes){
            if(StringUtils.equalsIgnoreCase(reportIdNode.getText(), e2bNumber)) {
                safetyreportNode = reportIdNode.getParent();
                return safetyreportNode;
            }
        }
        return null;
    }

    /**
     * 自己被恶心到了，数据库中保存的数据过于稀烂， TODO 数据整理，不做复杂替换处理，性能还可以被提高
     *
     * 2018-07-19 已经上线了，估计也没机会修复数据了 :(
     * replace 是比较消耗性能的操作
     * @param xpath
     * @return
     */
    public static String getXMLNameSpaceFixed(String xpath)
    {
        //System.out.println("source:" + xpath);
        xpath= xpath.replaceAll("/(\\w)", "/"+ NSKey +":$1");//replace start with "/"
        xpath= xpath.replaceAll("(\\[)([a-zA-Z]{2,20})(\\d*)", "["+NSKey+":$2$3");    //replace start with word
        //xpath=xpath.replace(NSKey+":text()","text()");
        xpath= StringUtils.replace(xpath,NSKey+":text()","text()");
        xpath= StringUtils.replace(xpath,NSKey+":starts-with","starts-with");
        xpath= StringUtils.replace(xpath,"[@xsi:schemaLocation='urn:hl7-org:v3MCCI_IN200100UV01.xsd']","");
        xpath= StringUtils.replace(xpath,"[xsi:schemaLocation='urn:hl7-org:v3 MCCI_IN200100UV01.xsd']","");
        xpath= StringUtils.replace(xpath,"substring(","");
        xpath= StringUtils.replace(xpath,", 5)","");
        xpath= StringUtils.replace(xpath,", 8)","");
        xpath= StringUtils.replace(xpath,",5)","");
        xpath= StringUtils.replace(xpath,",8)","");

        //System.out.println("dest:" + xpath);
        return xpath;
    }
    public static List getXpathNodes(Document document,String xpath) {
        if(StringUtils.isBlank(xpath)) {
            return null;
        }
        xpath=getXMLNameSpaceFixed(xpath);
        XPath x = document.createXPath(xpath);
        x.setNamespaceURIs(nsMap);
        List nodes = x.selectNodes(document);
        if(CollectionUtils.isEmpty(nodes)) {
            return null;
        }
        return nodes;
    }

    public static String getXpathSingleValue(Document document,String xpath) {
        if(StringUtils.isBlank(xpath)) {
            return null;
        }

        List list = getXpathNodes(document, xpath);

        if(CollectionUtils.isEmpty(list)) {
            return null;
        }
        Object obj = list.get(0);
        if(obj instanceof DefaultText) {
            StringBuilder sb = new StringBuilder();
            for(Object singleObj: list) {
                DefaultText defaultText = (DefaultText) singleObj;
                sb.append(defaultText.getText());
            }
            return sb.toString();
        }
        if(obj instanceof String) {
            return obj.toString();
        }
        if(obj instanceof Attribute) {
            return ((Attribute) obj).getText();
        }
        if(obj instanceof Node) {
            return ((Node) obj).getText();
        }
        return null;
    }

    public static String buildPath(String flag, String elemnetNumber, String xpath, int reportIndex, int componentIndex, int subComponentIndex)
    {
        return buildPath(flag,elemnetNumber,xpath,reportIndex,componentIndex,subComponentIndex,null,null);
    }


    /**
     * 这里是没办法抽象的代码，规则只能写死了，可以解决很多问题
     * @return
     */
    public static String buildPath(String flag, String elemnetNumber, String xpath, int reportIndex, int componentIndex, int subComponentIndex,String drugId,String eventId) {
        xpath = StringUtils.replaceOnce(xpath, E2BImportConfigConstant.XPATH_BASE_FLAG, flag);
        xpath = StringUtils.replaceOnce(xpath, "[r]", "[" + reportIndex + "]");
        if(StringUtils.isNotBlank(eventId)) {
            xpath = StringUtils.replaceOnce(xpath, "UUIDofi-threaction", eventId);
        }
        if(StringUtils.isNotBlank(drugId)) {
            xpath = StringUtils.replaceOnce(xpath, "UUIDofk-thdrug", drugId);
        }

        if (componentIndex > 0) {
            //这个部分是通过reaction UUID 和 drug UUID 来拼接数据， 没有[k] 和 [i] 只有两个[r]
            if (StringUtils.containsIgnoreCase(elemnetNumber, "g.k.9.i") && !StringUtils.containsIgnoreCase(elemnetNumber,".r")) {
                xpath = StringUtils.replaceOnce(xpath, "[k]", "[" + componentIndex + "]");
                //这里实际上会做替换reactionID
                return xpath;
            }
            //这个数据比较特殊，不包含k, 是通过 drug UUID进行定位
            if (StringUtils.equalsIgnoreCase(elemnetNumber, "g.k.1")) {
                //这里实际上会替换drugID
                return xpath;
            }
            if(StringUtils.containsIgnoreCase(elemnetNumber,"G.k.9.i.2.r")) {
                xpath = StringUtils.replaceOnce(xpath, "[r]", "[" + componentIndex + "]");
                //这里实际上会做替换reactionID 和 drugID
                return xpath;
            }

            //针对药物信息，药物信息是以k做数据循环
            if (StringUtils.containsIgnoreCase(elemnetNumber, "g.k.")) {
                xpath = StringUtils.replaceOnce(xpath, "[k]", "[" + componentIndex + "]");
                if (StringUtils.containsIgnoreCase(elemnetNumber, ".r") && subComponentIndex > 0) {
                    xpath = StringUtils.replaceOnce(xpath, "[r]", "[" + subComponentIndex + "]");
                }
                return xpath;
            }
            //不良事件是通过[i]进行循环
            if (StringUtils.containsIgnoreCase(elemnetNumber, "e.i.")) {
                xpath = StringUtils.replaceOnce(xpath, "[i]", "[" + componentIndex + "]");
                return xpath;
            }
            //其他的部分只要包含[r],使用r进行循环
            if (StringUtils.containsIgnoreCase(elemnetNumber, ".r")) {
                xpath = StringUtils.replaceOnce(xpath, "[r]", "[" + componentIndex + "]");
                return xpath;
            }

        }
        return xpath;
    }
}
