<#assign valueMap=drug.valueMap>
<component typeCode="COMP">
    <substanceAdministration classCode="SBADM" moodCode="EVN">
        <id root="${valueMap["G.k.fileDrugId"]?if_exists?xml}"/>
        <#if isDebug == 1><!-- fileDrugId --></#if>
        <consumable typeCode="CSM">
            <instanceOfKind classCode="INST">
                <kindOfProduct classCode="MMAT" determinerCode="KIND">
                    <!--<code code="G.k.2.1.1b" codeSystem="TBD-MPID"
                          codeSystemVersion="G.k.2.1.1a"/>-->
                    <#if isDebug == 1><!-- G.k.2.1.1b --></#if> <#if isDebug == 1><!-- G.k.2.1.1a --></#if>
                    <#if valueMap["G.k.2.2"]?exists>
                    <name>${valueMap["G.k.2.2"]?if_exists?xml}</name>
					<#if isDebug == 1><!-- G.k.2.2 --></#if>
                    </#if>
                    <asManufacturedProduct classCode="MANU">
                        <subjectOf typeCode="SBJ">
                            <approval classCode="CNTRCT" moodCode="EVN">
                    <#if valueMap["G.k.3.1"]?exists>
                                <id extension="${valueMap["G.k.3.1"]?if_exists?xml}"
                                    root="2.16.840.1.113883.3.989.2.1.3.4"/>
                                <#if isDebug == 1><!-- G.k.3.1 --></#if>
                    </#if>
                    <#if valueMap["G.k.3.3"]?exists>
                                <holder typeCode="HLD">
                                    <role classCode="HLD">
                                        <playingOrganization
                                                classCode="ORG"
                                                determinerCode="INSTANCE">
                                            <name>${valueMap["G.k.3.3"]?if_exists?xml}</name>
										<#if isDebug == 1><!-- G.k.3.3 --></#if>	
                                        </playingOrganization>
                                    </role>
                                </holder>
                    </#if>
                    <#if valueMap["G.k.3.2"]?exists>
                                <author typeCode="AUT">
                                    <territorialAuthority
                                            classCode="TERR">
                                        <territory classCode="NAT"
                                                   determinerCode="INSTANCE">
                                            <code code="${valueMap["G.k.3.2"]?if_exists?xml}"
                                                  codeSystem="1.0.3166.1.2.2"/>
                                        </territory>
                                        <#if isDebug == 1><!-- G.k.3.2 --></#if>
                                    </territorialAuthority>
                                </author>
                    </#if>
                            </approval>
                        </subjectOf>
                    </asManufacturedProduct>
                    <#if drug.childrenMap?exists>
                        <#if drug.childrenMap["G.k.2.3.r"]?exists>
						<#if isDebug == 1><!-- G.k.2.3.r --></#if>
                            <#assign psurList=drug.childrenMap["G.k.2.3.r"]>
                            <#list psurList as psur>
                            <#include "/e2b_r3_eu/drug/G.k.2.3.r.ftl">
                            </#list>
                        </#if>
                    </#if>
                </kindOfProduct>
                <#if valueMap["G.k.2.4"]?exists>
                <subjectOf typeCode="SBJ">
                    <productEvent classCode="ACT" moodCode="EVN">
                        <code code="1"
                              codeSystem="2.16.840.1.113883.3.989.2.1.1.18"
                              codeSystemVersion="1.0"
                              displayName="retailSupply"/>
                        <performer typeCode="PRF">
                            <assignedEntity classCode="ASSIGNED">
                                <representedOrganization classCode="ORG"
                                                         determinerCode="INSTANCE">
                                    <addr>
                                        <country>${valueMap["G.k.2.4"]?if_exists?xml}</country>
										<#if isDebug == 1><!-- G.k.2.4 --></#if>
                                    </addr>
                                </representedOrganization>
                            </assignedEntity>
                        </performer>
                    </productEvent>
                </subjectOf>
                </#if>
            </instanceOfKind>
        </consumable>
        <#if drug.childrenMap?exists>
            <#if drug.childrenMap["G.k.9.i"]?exists>
			<#if isDebug == 1><!-- G.k.9.i --></#if>
                <#assign causalityList=drug.childrenMap["G.k.9.i"] >
                <#list causalityList as causality>
                    <#include "/e2b_r3_eu/drug/G.k.9.i.ftl">
                </#list>
            </#if>
        </#if>
        <#if valueMap["G.k.2.5"]?exists>
        <outboundRelationship2 typeCode="PERT">
            <observation classCode="OBS" moodCode="EVN">
                <code code="6"
                      codeSystem="2.16.840.1.113883.3.989.2.1.1.19"
                      codeSystemVersion="1.1" displayName="blinded"/>
                <value xsi:type="BL" value="${valueMap["G.k.2.5"]?if_exists?xml}"/>
				<#if isDebug == 1><!-- G.k.2.5 --></#if>
            </observation>
        </outboundRelationship2>
        </#if>
        <#if drug.childrenMap?exists>
                <#if drug.childrenMap["G.k.4.r"]?exists>
                    <#assign doseList=drug.childrenMap["G.k.4.r"]>
					<#if isDebug == 1><!-- G.k.4.r --></#if>
                    <#list doseList as dose>
                        <#include "/e2b_r3_eu/drug/G.k.4.r.ftl">
                    </#list>
                </#if>
        </#if>
        <#if valueMap["G.k.5a"]?has_content && valueMap["G.k.5b"]?has_content>
        <outboundRelationship2 typeCode="SUMM">
            <observation classCode="OBS" moodCode="EVN">
                <code code="14"
                      codeSystem="2.16.840.1.113883.3.989.2.1.1.19"
                      codeSystemVersion="1.1"
                      displayName="cumulativeDoseToReaction"/>
                <value xsi:type="PQ" value="${valueMap["G.k.5a"]?if_exists?xml}" unit="${valueMap["G.k.5b"]?if_exists?xml}"/>
				<#if isDebug == 1><!-- G.k.5a --></#if> <#if isDebug == 1><!-- G.k.5b --></#if>
            </observation>
        </outboundRelationship2>
        </#if>
        <#if valueMap["G.k.6a"]?has_content && valueMap["G.k.6b"]?has_content>
        <outboundRelationship2 typeCode="PERT">
            <observation classCode="OBS" moodCode="EVN">
                <code code="16"
                      codeSystem="2.16.840.1.113883.3.989.2.1.1.19"
                      codeSystemVersion="1.1"
                      displayName="gestationPeriod"/>
                <value xsi:type="PQ" value="${valueMap["G.k.6a"]?if_exists?xml}" unit="${valueMap["G.k.6b"]?if_exists?xml}"/>
				<#if isDebug == 1><!-- G.k.6a --></#if> <#if isDebug == 1><!-- G.k.5b --></#if>
            </observation>
        </outboundRelationship2>
        </#if>

        <#if drug.childrenMap?exists>
            <#if drug.childrenMap["G.k.9.i"]?exists>
                    <#if isDebug == 1><!-- G.k.9.i --></#if>
                <#assign causalityList=drug.childrenMap["G.k.9.i"] >
                <#list causalityList as causality>
                    <#include "/e2b_r3_eu/drug/G.k.9.i.4.ftl">
                </#list>
            </#if>
        </#if>
        <!-- move the causality node G.k.9.i.4 just for comments-->
		<#if isDebug == 1><!-- G.k.9.i.4 --></#if>
        <#if drug.childrenMap?exists>
                <#if drug.childrenMap["G.k.10.r.1"]?exists>
				<#if isDebug == 1><!-- G.k.10.r.1 --></#if>
                    <#assign moreInfoList=drug.childrenMap["G.k.10.r.1"] >
                    <#list moreInfoList as moreInfo>
                        <#include "/e2b_r3_eu/drug/G.k.10.r.1.ftl">
                    </#list>
                </#if>
        </#if>
        <#if valueMap["G.k.6a"]?exists>
        <outboundRelationship2 typeCode="REFR">
            <observation classCode="OBS" moodCode="EVN">
                <code code="2"
                      codeSystem="2.16.840.1.113883.3.989.2.1.1.19"
                      codeSystemVersion="1.1"
                      displayName="additionalInformation"/>
                <value xsi:type="ST">${valueMap["G.k.6a"]?if_exists?xml}</value>
				<#if isDebug == 1><!-- G.k.11 --></#if>
            </observation>
        </outboundRelationship2>
        </#if>
        <#if drug.childrenMap?exists>
                <#if drug.childrenMap["G.k.7.r"]?exists>
				<#if isDebug == 1><!-- G.k.7.r --></#if>
                    <#assign reasonList=drug.childrenMap["G.k.7.r"] >
                    <#list reasonList as reason>
                        <#include "/e2b_r3_eu/drug/G.k.7.r.ftl">
                    </#list>
                </#if>
        </#if>
        <#if valueMap["G.k.8"]?exists>
        <inboundRelationship typeCode="CAUS">
            <act classCode="ACT" moodCode="EVN">
                <code code="${valueMap["G.k.8"]?if_exists?xml}"
                      codeSystem="2.16.840.1.113883.3.989.2.1.1.15"
                      codeSystemVersion="2.0"/>
                <#if isDebug == 1><!-- G.k.8 --></#if>
            </act>
        </inboundRelationship>
        </#if>
    </substanceAdministration>
</component>
