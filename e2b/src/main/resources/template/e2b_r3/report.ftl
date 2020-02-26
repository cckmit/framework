<?xml version="1.0" encoding="UTF-8"?>
<MCCI_IN200100UV01 ITSVersion="XML_1.0" xsi:schemaLocation="urn:hl7-org:v3 MCCI_IN200100UV01.xsd" xmlns="urn:hl7-org:v3"
                   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

    <id extension="${company.batchNumber?if_exists?xml}" root="2.16.840.1.113883.3.989.2.1.3.22"/>
		<#if isDebug == 1><!-- N.1.2 --></#if>
    <creationTime value="${company.batchTransmissionDate?if_exists?xml}"/>
		<#if isDebug == 1><!-- N.1.5 --></#if>
    <responseModeCode code="D"/>
    <interactionId extension="MCCI_IN200100UV01" root="2.16.840.1.113883.1.6"/>
    <name code="1" codeSystem="2.16.840.1.113883.3.989.2.1.1.1" codeSystemVersion="1.01"/>
        <#if isDebug == 1><!-- N.1.1 --></#if>

    <#list dataList as data>
        <#assign topValueMap=data["C.1.1"][0].valueMap>
        <#assign topNullflavorValueMap=data["C.1.1"][0].nullflavorValueMap>
    <PORR_IN049016UV>
        <id extension="${topValueMap['N.2.r.1']?if_exists?xml}" root="2.16.840.1.113883.3.989.2.1.3.1"/>
			<#if isDebug == 1><!-- N.2.r.1 --></#if>
        <creationTime value="${company.messageCreateDate?if_exists?xml}"/>
			<#if isDebug == 1><!-- N.2.r.4 --></#if>
        <interactionId extension="PORR_IN049016UV" root="2.16.840.1.113883.1.6"/>
        <processingCode code="P"/>
        <processingModeCode code="T"/>
        <acceptAckCode code="AL"/>
        <receiver typeCode="RCV">
            <device classCode="DEV" determinerCode="INSTANCE">
                <id extension="${company.messageReceiverIdentifier?if_exists?xml}" root="2.16.840.1.113883.3.989.2.1.3.12"/>
					<#if isDebug == 1><!-- N.2.r.3 --></#if>
            </device>
        </receiver>
        <sender typeCode="SND">
            <device classCode="DEV" determinerCode="INSTANCE">
                <id extension="${company.messageSenderIdentifier?if_exists?xml}" root="2.16.840.1.113883.3.989.2.1.3.11"/>
					<#if isDebug == 1><!-- N.2.r.2 --></#if>
            </device>
        </sender>
        <controlActProcess classCode="CACT" moodCode="EVN">
            <code code="PORR_TE049016UV" codeSystem="2.16.840.1.113883.1.18"/>
            <effectiveTime value="${company.createDate?if_exists?xml}"/>
				<#if isDebug == 1><!-- C.1.2 --></#if>
            <subject typeCode="SUBJ">
                <investigationEvent classCode="INVSTG" moodCode="EVN">
                    <id extension="${topValueMap['C.1.1']?if_exists?xml}"
                        root="2.16.840.1.113883.3.989.2.1.3.1"/>
						<#if isDebug == 1><!-- C.1.1 --></#if>
                    <id extension="${topValueMap['C.1.8.1']?if_exists?xml}" root="2.16.840.1.113883.3.989.2.1.3.2"/>
						<#if isDebug == 1><!-- C.1.8.1 --></#if>
                    <code code="PAT_ADV_EVNT" codeSystem="2.16.840.1.113883.5.4"/>
                    <text>${topValueMap['H.1']?if_exists?xml}</text>
						<#if isDebug == 1><!-- H.1 --></#if>
                    <statusCode code="active"/>
                    <effectiveTime>
                        <low value="${topValueMap['C.1.4']?if_exists?xml}"/>
							<#if isDebug == 1><!-- C.1.4 --></#if>
                    </effectiveTime>
                    <availabilityTime value="${topValueMap['C.1.5']?if_exists?xml}"/>
						<#if isDebug == 1><!-- C.1.5 --></#if>

						<#if data["C.1.6.1.r"]?exists>
                                <#if isDebug == 1><!-- C.1.6.1.r --></#if>
                            <#assign attachment1List = data["C.1.6.1.r"]>
                            <#list attachment1List as attachment1>
                                <#include "/e2b_r3/attachment/C.1.6.1.r.ftl">
                            </#list>
                        </#if>


						<#if data["C.4.r"]?exists>
                                <#if isDebug == 1><!-- C.4.r --></#if>
                            <#assign attachment2List = data["C.4.r"]>
                            <#list attachment2List as attachment2>
                                <#include "/e2b_r3/attachment/C.4.r.ftl">
                            </#list>
                        </#if>
                    <component typeCode="COMP">
                        <adverseEventAssessment classCode="INVSTG" moodCode="EVN">
                            <subject1 typeCode="SBJ">
                                <primaryRole classCode="INVSBJ">
                                    <player1 classCode="PSN" determinerCode="INSTANCE">
    <#if topValueMap["D.1"]?exists && topValueMap["D.1"] != "">
                                        <name>${topValueMap["D.1"]?if_exists?xml}</name>
            <#if isDebug == 1><!-- D.1 --></#if>
    <#elseif topNullflavorValueMap['D.1']?has_content>
        <name nullFlavor="${topNullflavorValueMap['D.1']?if_exists?xml}"/>
            <#if isDebug == 1><!-- D.1 nullflavor--></#if>
    </#if>
<#if topValueMap["D.5"]?has_content>
                                        <administrativeGenderCode code="${topValueMap["D.5"]?if_exists?xml}"
                                                                  codeSystem="1.0.5218"/>
        <#if isDebug == 1><!-- D.5 --></#if>
<#elseif topNullflavorValueMap['D.5']?has_content>
                                        <administrativeGenderCode nullFlavor="${topNullflavorValueMap["D.5"]?if_exists?xml}"
                                                                  codeSystem="1.0.5218"/>
        <#if isDebug == 1><!-- D.5 nullflavor--></#if>
</#if>
<#if topValueMap["D.2.1"]?exists>
                                        <birthTime value="${topValueMap["D.2.1"]?if_exists?xml}"/>
        <#if isDebug == 1><!-- D.2.1 --></#if>
<#elseif topNullflavorValueMap['D.2.1']?has_content>
                                        <birthTime nullFlavor="${topNullflavorValueMap["D.2.1"]?if_exists?xml}"/>
        <#if isDebug == 1><!-- D.2.1 nullflavor--></#if>
</#if>
                                        <#if topValueMap["D.9.1"]?exists>
                                        <deceasedTime value="${topValueMap["D.9.1"]?if_exists?xml}"/>
                                                <#if isDebug == 1><!-- D.9.1 --></#if>
                                        <#elseif topNullflavorValueMap['D.9.1']?has_content>
                                        <deceasedTime nullFlavor="${topNullflavorValueMap["D.9.1"]?if_exists?xml}"/>
                                                <#if isDebug == 1><!-- D.9.1 nullflavor--></#if>
                                        </#if>

											<#if topValueMap["D.1.1.1"]?exists>
                                                <asIdentifiedEntity classCode="IDENT">
                                                    <id extension="${topValueMap["D.1.1.1"]?if_exists?xml}"
                                                        root="2.16.840.1.113883.3.989.2.1.3.7"/>
                                                    <#if isDebug == 1><!-- D.1.1.1 --></#if>
                                                    <code code="1" codeSystem="2.16.840.1.113883.3.989.2.1.1.4"
                                                          codeSystemVersion="1.0" displayName="GP"/>
                                                </asIdentifiedEntity>
                                            <#elseif topNullflavorValueMap['D.1.1.1']?has_content>
                                                <asIdentifiedEntity classCode="IDENT">
                                                    <id nullFlavor="${topNullflavorValueMap["D.1.1.1"]?if_exists?xml}"
                                                        root="2.16.840.1.113883.3.989.2.1.3.7"/>
                                                    <#if isDebug == 1><!-- D.1.1.1 nullflavor --></#if>
                                                    <code code="1" codeSystem="2.16.840.1.113883.3.989.2.1.1.4"
                                                          codeSystemVersion="1.0" displayName="GP"/>
                                                </asIdentifiedEntity>
                                            </#if>
											<#if topValueMap["D.1.1.2"]?exists>
											<asIdentifiedEntity classCode="IDENT">
                                                <id extension="${topValueMap["D.1.1.2"]?if_exists?xml}"
                                                    root="2.16.840.1.113883.3.989.2.1.3.8"/>
												<#if isDebug == 1><!-- D.1.1.2 --></#if>
                                                <code code="2" codeSystem="2.16.840.1.113883.3.989.2.1.1.4"
                                                      codeSystemVersion="1.0" displayName="Specialist"/>
                                            </asIdentifiedEntity>
                                            <#elseif topNullflavorValueMap['D.1.1.2']?has_content>
											<asIdentifiedEntity classCode="IDENT">
                                                <id nullFlavor="${topNullflavorValueMap["D.1.1.2"]?if_exists?xml}"
                                                    root="2.16.840.1.113883.3.989.2.1.3.8"/>
												<#if isDebug == 1><!-- D.1.1.2 nullflavor--></#if>
                                                <code code="2" codeSystem="2.16.840.1.113883.3.989.2.1.1.4"
                                                      codeSystemVersion="1.0" displayName="Specialist"/>
                                            </asIdentifiedEntity>
                                            </#if>
                                            <#if topValueMap["D.1.1.3"]?exists>
											<asIdentifiedEntity classCode="IDENT">
                                                <id extension="${topValueMap["D.1.1.3"]?if_exists?xml}"
                                                    root="2.16.840.1.113883.3.989.2.1.3.9"/>
												<#if isDebug == 1><!-- D.1.1.3 --></#if>
                                                <code code="3" codeSystem="2.16.840.1.113883.3.989.2.1.1.4"
                                                      codeSystemVersion="1.0" displayName="Hospital Record"/>
                                            </asIdentifiedEntity>
                                            <#elseif topNullflavorValueMap['D.1.1.3']?has_content>
											<asIdentifiedEntity classCode="IDENT">
                                                <id nullFlavor="${topNullflavorValueMap["D.1.1.3"]?if_exists?xml}"
                                                    root="2.16.840.1.113883.3.989.2.1.3.9"/>
												<#if isDebug == 1><!-- D.1.1.3 nullflavor--></#if>
                                                <code code="3" codeSystem="2.16.840.1.113883.3.989.2.1.1.4"
                                                      codeSystemVersion="1.0" displayName="Hospital Record"/>
                                            </asIdentifiedEntity>
                                            </#if>
	                                        <#if topValueMap["D.1.1.4"]?exists>
											<asIdentifiedEntity classCode="IDENT">
                                                <id extension="${topValueMap["D.1.1.4"]?if_exists?xml}"
                                                    root="2.16.840.1.113883.3.989.2.1.3.10"/>
												<#if isDebug == 1><!-- D.1.1.4 --></#if>
                                                <code code="4" codeSystem="2.16.840.1.113883.3.989.2.1.1.4"
                                                      codeSystemVersion="1.0" displayName="Investigation"/>
                                            </asIdentifiedEntity>
                                            <#elseif topNullflavorValueMap['D.1.1.4']?has_content>
											<asIdentifiedEntity classCode="IDENT">
                                                <id nullFlavor="${topNullflavorValueMap["D.1.1.4"]?if_exists?xml}"
                                                    root="2.16.840.1.113883.3.989.2.1.3.10"/>
												<#if isDebug == 1><!-- D.1.1.4 nullflavor --></#if>
                                                <code code="4" codeSystem="2.16.840.1.113883.3.989.2.1.1.4"
                                                      codeSystemVersion="1.0" displayName="Investigation"/>
                                            </asIdentifiedEntity>
                                            </#if>
                                        <#if topValueMap["D.10.6"]?exists || topNullflavorValueMap["D.10.6"]?exists>
                                        <role classCode="PRS">
                                            <code code="PRN" codeSystem="2.16.840.1.113883.5.111"/>
                                            <associatedPerson classCode="PSN" determinerCode="INSTANCE">
                                                <#if topValueMap["D.10.1"]?exists>
                                                <name>${topValueMap["D.10.1"]?if_exists?xml}</name>
                                                        <#if isDebug == 1><!-- D.10.1 --></#if>
                                                <#elseif topNullflavorValueMap["D.10.1"]?has_content>
                                                <name nullFlavor="${topNullflavorValueMap['D.10.1']?xml}"/>
                                                        <#if isDebug == 1><!-- D.10.1 nullflavor --></#if>
                                                </#if>
                                                <#if topValueMap["D.10.6"]?exists>
                                                <administrativeGenderCode code="${topValueMap["D.10.6"]?if_exists?xml}"
                                                                          codeSystem="1.0.5218"/>
                                                        <#if isDebug == 1><!-- D.10.6 --></#if>
                                                <#elseif topNullflavorValueMap["D.10.6"]?has_content>
                                                <administrativeGenderCode nullFlavor="${topNullflavorValueMap["D.10.6"]?if_exists?xml}"
                                                                          codeSystem="1.0.5218"/>
                                                        <#if isDebug == 1><!-- D.10.6 nullflavor--></#if>
                                                </#if>
                                                <#if topValueMap["D.10.2.1"]?exists>
                                                <birthTime value="${topValueMap["D.10.2.1"]?if_exists?xml}"/>
                                                        <#if isDebug == 1><!-- D.10.2.1 --></#if>
                                                <#elseif topNullflavorValueMap["D.10.2.1"]?has_content>
                                                <birthTime nullFlavor="${topNullflavorValueMap["D.10.2.1"]?if_exists?xml}"/>
                                                        <#if isDebug == 1><!-- D.10.2.1 nullflavor --></#if>
                                                </#if>

                                            </associatedPerson>
                                            <#if topValueMap["D.10.2.2a"]?exists>
                                            <subjectOf2 typeCode="SBJ">
                                                <observation classCode="OBS" moodCode="EVN">
                                                    <code code="3" codeSystem="2.16.840.1.113883.3.989.2.1.1.19"
                                                          codeSystemVersion="1.1" displayName="age"/>
                                                    <value xsi:type="PQ" value="${topValueMap["D.10.2.2a"]?if_exists?xml}"
                                                           unit="${topValueMap["D.10.2.2b"]?if_exists?xml}"/>
														<#if isDebug == 1><!-- D.10.2.2a --></#if>
														<#if isDebug == 1><!-- D.10.2.2b --></#if>
                                                </observation>
                                            </subjectOf2>
                                            </#if>
                                            <#if topValueMap["D.10.3"]?exists>
                                            <subjectOf2 typeCode="SBJ">
                                                <observation classCode="OBS" moodCode="EVN">
                                                    <code code="22" codeSystem="2.16.840.1.113883.3.989.2.1.1.19"
                                                          codeSystemVersion="1.1"
                                                          displayName="lastMenstrualPeriodDate"/>
                                                    <value xsi:type="TS" value="${topValueMap["D.10.3"]?if_exists?xml}"/>
														<#if isDebug == 1><!-- D.10.3 --></#if>
                                                </observation>
                                            </subjectOf2>
                                            <#elseif topNullflavorValueMap["D.10.3"]?has_content>
                                            <subjectOf2 typeCode="SBJ">
                                                <observation classCode="OBS" moodCode="EVN">
                                                    <code code="22" codeSystem="2.16.840.1.113883.3.989.2.1.1.19"
                                                          codeSystemVersion="1.1"
                                                          displayName="lastMenstrualPeriodDate"/>
                                                    <value xsi:type="TS" nullFlavor="${topNullflavorValueMap["D.10.3"]?if_exists?xml}"/>
														<#if isDebug == 1><!-- D.10.3 nullflavor--></#if>
                                                </observation>
                                            </subjectOf2>
                                            </#if>
                                            <#if topValueMap["D.10.3"]?exists>
                                            <subjectOf2 typeCode="SBJ">
                                                <observation classCode="OBS" moodCode="EVN">
                                                    <code code="7" codeSystem="2.16.840.1.113883.3.989.2.1.1.19"
                                                          codeSystemVersion="1.1" displayName="bodyWeight"/>
                                                    <value xsi:type="PQ" value="${topValueMap["D.10.4"]?if_exists?xml}"
                                                           unit="kg"/>
														<#if isDebug == 1><!-- D.10.4 --></#if>
                                                </observation>
                                            </subjectOf2>
                                            </#if>
                                            <#if topValueMap["D.10.3"]?exists>
                                            <subjectOf2 typeCode="SBJ">
                                                <observation classCode="OBS" moodCode="EVN">
                                                    <code code="17" codeSystem="2.16.840.1.113883.3.989.2.1.1.19"
                                                          codeSystemVersion="1.1" displayName="height"/>
                                                    <value xsi:type="PQ" value="${topValueMap["D.10.5"]?if_exists?xml}"
                                                           unit="cm"/>
														<#if isDebug == 1><!-- D.10.5 --></#if>
                                                </observation>
                                            </subjectOf2>
                                            </#if>
                                            <#if topValueMap["D.10.7.2"]?exists>
                                            <subjectOf2 typeCode="SBJ">
                                                <organizer classCode="CATEGORY" moodCode="EVN">
                                                    <code code="1" codeSystem="2.16.840.1.113883.3.989.2.1.1.20"
                                                          codeSystemVersion="1.0"
                                                          displayName="relevantMedicalHistoryAndConcurrentConditions"/>
                                                <#--<component typeCode="COMP">-->
                                                <#--<observation classCode="OBS" moodCode="EVN">-->
                                                <#--<code code="D.10.7.1.r.1b" codeSystem="2.16.840.1.113883.6.163" codeSystemVersion="D.10.7.1.r.1a"/>-->
                                                <#--<effectiveTime xsi:type="IVL_TS">-->
                                                <#--<low value="20090101"/>-->
                                                <#--<high value="20090101"/>-->
                                                <#--</effectiveTime>-->
                                                <#--<outboundRelationship2 typeCode="COMP">-->
                                                <#--<observation classCode="OBS" moodCode="EVN">-->
                                                <#--<code code="10" codeSystem="2.16.840.1.113883.3.989.2.1.1.19" codeSystemVersion="1.1" displayName="comment"/>-->
                                                <#--<value xsi:type="ED">D.10.7.1.r.5</value>-->
                                                <#--</observation>-->
                                                <#--</outboundRelationship2>-->
                                                <#--<inboundRelationship typeCode="REFR">-->
                                                <#--<observation classCode="OBS" moodCode="EVN">-->
                                                <#--<code code="13" codeSystem="2.16.840.1.113883.3.989.2.1.1.19" codeSystemVersion="1.1" displayName="continuing"/>-->
                                                <#--<value xsi:type="BL" value="true"/>-->
                                                <#--</observation>-->
                                                <#--</inboundRelationship>-->
                                                <#--</observation>-->
                                                <#--</component>-->
                                                <#--<component typeCode="COMP">-->
                                                <#--<observation classCode="OBS" moodCode="EVN">-->
                                                <#--<code code="D.10.7.1.r.1b" codeSystem="2.16.840.1.113883.6.163" codeSystemVersion="D.10.7.1.r.1a"/>-->
                                                <#--<effectiveTime xsi:type="IVL_TS">-->
                                                <#--<low value="20090101"/>-->
                                                <#--<high value="20090101"/>-->
                                                <#--</effectiveTime>-->
                                                <#--<outboundRelationship2 typeCode="COMP">-->
                                                <#--<observation classCode="OBS" moodCode="EVN">-->
                                                <#--<code code="10" codeSystem="2.16.840.1.113883.3.989.2.1.1.19" codeSystemVersion="1.1" displayName="comment"/>-->
                                                <#--<value xsi:type="ED">D.10.7.1.r.5</value>-->
                                                <#--</observation>-->
                                                <#--</outboundRelationship2>-->
                                                <#--<inboundRelationship typeCode="REFR">-->
                                                <#--<observation classCode="OBS" moodCode="EVN">-->
                                                <#--<code code="13" codeSystem="2.16.840.1.113883.3.989.2.1.1.19" codeSystemVersion="1.1" displayName="continuing"/>-->
                                                <#--<value xsi:type="BL" value="true"/>-->
                                                <#--</observation>-->
                                                <#--</inboundRelationship>-->
                                                <#--</observation>-->
                                                <#--</component>-->
                                                    <component typeCode="COMP">
                                                        <observation classCode="OBS" moodCode="EVN">
                                                            <code code="18"
                                                                  codeSystem="2.16.840.1.113883.3.989.2.1.1.19"
                                                                  codeSystemVersion="1.1"
                                                                  displayName="historyAndConcurrentConditionText"/>
                                                            <value xsi:type="ED">${topValueMap["D.10.7.2"]?if_exists?xml}</value>
																<#if isDebug == 1><!-- D.10.7.2 --></#if>
                                                        </observation>
                                                    </component>
                                                </organizer>
                                            </subjectOf2>
                                            </#if>
                                        <#--<subjectOf2 typeCode="SBJ">-->
                                        <#--<organizer classCode="CATEGORY" moodCode="EVN">-->
                                        <#--<code code="2" codeSystem="2.16.840.1.113883.3.989.2.1.1.20" codeSystemVersion="1.0" displayName="drugHistory"/>-->
                                        <#--<component typeCode="COMP">-->
                                        <#--<substanceAdministration classCode="SBADM" moodCode="EVN">-->
                                        <#--<effectiveTime xsi:type="IVL_TS">-->
                                        <#--<low value="20090101"/>-->
                                        <#--<high value="20090101"/>-->
                                        <#--</effectiveTime>-->
                                        <#--<consumable typeCode="CSM">-->
                                        <#--<instanceOfKind classCode="INST">-->
                                        <#--<kindOfProduct classCode="MMAT" determinerCode="KIND">-->
                                        <#--<code code="D.10.8.r.2b" codeSystem="TBD-MPID" codeSystemVersion="D.10.8.r.2a"/>-->
                                        <#--<name>D.10.8.r.1</name>-->
                                        <#--</kindOfProduct>-->
                                        <#--</instanceOfKind>-->
                                        <#--</consumable>-->
                                        <#--<outboundRelationship2 typeCode="RSON">-->
                                        <#--<observation classCode="OBS" moodCode="EVN">-->
                                        <#--<code code="19" codeSystem="2.16.840.1.113883.3.989.2.1.1.19" codeSystemVersion="1.1" displayName="indication"/>-->
                                        <#--<value xsi:type="CE" code="D.10.8.r.6b" codeSystem="2.16.840.1.113883.6.163" codeSystemVersion="D.10.8.r.6a"/>-->
                                        <#--</observation>-->
                                        <#--</outboundRelationship2>-->
                                        <#--<outboundRelationship2 typeCode="CAUS">-->
                                        <#--<observation classCode="OBS" moodCode="EVN">-->
                                        <#--<code code="29" codeSystem="2.16.840.1.113883.3.989.2.1.1.19" codeSystemVersion="1.1" displayName="reaction"/>-->
                                        <#--<value xsi:type="CE" code="D.10.8.r.7b" codeSystem="2.16.840.1.113883.6.163" codeSystemVersion="D.10.8.r.7a"/>-->
                                        <#--</observation>-->
                                        <#--</outboundRelationship2>-->
                                        <#--</substanceAdministration>-->
                                        <#--</component>-->
                                        <#--<component typeCode="COMP">-->
                                        <#--<substanceAdministration classCode="SBADM" moodCode="EVN">-->
                                        <#--<effectiveTime xsi:type="IVL_TS">-->
                                        <#--<low value="20090101"/>-->
                                        <#--<high value="20090101"/>-->
                                        <#--</effectiveTime>-->
                                        <#--<consumable typeCode="CSM">-->
                                        <#--<instanceOfKind classCode="INST">-->
                                        <#--<kindOfProduct classCode="MMAT" determinerCode="KIND">-->
                                        <#--<code code="D.10.8.r.3b" codeSystem="TBD-PhPID" codeSystemVersion="D.10.8.r.3a"/>-->
                                        <#--<name>D.10.8.r.1</name>-->
                                        <#--</kindOfProduct>-->
                                        <#--</instanceOfKind>-->
                                        <#--</consumable>-->
                                        <#--<outboundRelationship2 typeCode="RSON">-->
                                        <#--<observation classCode="OBS" moodCode="EVN">-->
                                        <#--<code code="19" codeSystem="2.16.840.1.113883.3.989.2.1.1.19" codeSystemVersion="1.1" displayName="indication"/>-->
                                        <#--<value xsi:type="CE" code="D.10.8.r.6b" codeSystem="2.16.840.1.113883.6.163" codeSystemVersion="D.10.8.r.6a"/>-->
                                        <#--</observation>-->
                                        <#--</outboundRelationship2>-->
                                        <#--<outboundRelationship2 typeCode="CAUS">-->
                                        <#--<observation classCode="OBS" moodCode="EVN">-->
                                        <#--<code code="29" codeSystem="2.16.840.1.113883.3.989.2.1.1.19" codeSystemVersion="1.1" displayName="reaction"/>-->
                                        <#--<value xsi:type="CE" code="D.10.8.r.7b" codeSystem="2.16.840.1.113883.6.163" codeSystemVersion="D.10.8.r.7a"/>-->
                                        <#--</observation>-->
                                        <#--</outboundRelationship2>-->
                                        <#--</substanceAdministration>-->
                                        <#--</component>-->
                                        <#--</organizer>-->
                                        <#--</subjectOf2>-->
                                        </role>
                                        </#if>
                                    </player1>
                                    <subjectOf1 typeCode="SBJ">
                                        <researchStudy classCode="CLNTRL" moodCode="EVN">
                                            <#if topValueMap['C.5.3']?has_content>
                                            <id extension="${topValueMap['C.5.3']?if_exists?xml}"
                                                root="2.16.840.1.113883.3.989.2.1.3.5"/>
                                                    <#if isDebug == 1><!-- C.5.3 --></#if>
                                            <#elseif topNullflavorValueMap['C.5.3']?has_content>
                                            <id nullFlavor="${topNullflavorValueMap['C.5.3']?if_exists?xml}"
                                                root="2.16.840.1.113883.3.989.2.1.3.5"/>
                                                    <#if isDebug == 1><!-- C.5.3 nullflavor--></#if>
                                            </#if>
<#if topValueMap['C.5.4']?has_content>
                                            <code code="${topValueMap['C.5.4']?if_exists?xml}"
                                                  codeSystem="2.16.840.1.113883.3.989.2.1.1.8" codeSystemVersion="1.0"/>
        <#if isDebug == 1><!-- C.5.4 --></#if>
</#if>
<#if topValueMap['C.5.2']?has_content>
                                            <title>${topValueMap['C.5.2']?if_exists?xml}</title>
        <#if isDebug == 1><!-- C.5.2 --></#if>
<#elseif topNullflavorValueMap['C.5.2']?has_content>
<title nullFlavor="${topNullflavorValueMap['C.5.2']?if_exists?xml}"/>
        <#if isDebug == 1><!-- C.5.2 nullflavor--></#if>
</#if>
												<#if data["C.5.1.r"]?exists>
                                                    <#assign researchList = data["C.5.1.r"]>
                                                    <#list researchList as research>
                                                        <#include "/e2b_r3/research/C.5.1.r.ftl">
                                                    </#list>
                                                </#if>
                                        </researchStudy>
                                    </subjectOf1>
                                    <#if topValueMap['D.2.2b']?exists>
                                    <subjectOf2 typeCode="SBJ">
                                        <observation classCode="OBS" moodCode="EVN">
                                            <code code="3" codeSystem="2.16.840.1.113883.3.989.2.1.1.19"
                                                  codeSystemVersion="1.1" displayName="age"/>
                                            <value xsi:type="PQ" value="${topValueMap['D.2.2a']?if_exists?xml}"
                                                   unit="${topValueMap['D.2.2b']?if_exists?xml}"/>
												<#if isDebug == 1><!-- D.2.2a --></#if><#if isDebug == 1><!-- D.2.2b --></#if>
                                        </observation>
                                    </subjectOf2>
                                    </#if>
                                    <#if topValueMap['D.2.2.1a']?exists>
                                    <subjectOf2 typeCode="SBJ">
                                        <observation classCode="OBS" moodCode="EVN">
                                            <code code="16" codeSystem="2.16.840.1.113883.3.989.2.1.1.19"
                                                  codeSystemVersion="1.1" displayName="gestationPeriod"/>
                                            <value xsi:type="PQ" value="${topValueMap['D.2.2.1a']?if_exists?xml}"
                                                   unit="${topValueMap['D.2.2.1b']?if_exists?xml}"/>
												<#if isDebug == 1><!-- D.2.2.1a --></#if>
												<#if isDebug == 1><!-- D.2.2.1b --></#if>
                                        </observation>
                                    </subjectOf2>
                                    </#if>
                                    <#if topValueMap['D.2.3']?exists>
                                    <subjectOf2 typeCode="SBJ">
                                        <observation classCode="OBS" moodCode="EVN">
                                            <code code="4" codeSystem="2.16.840.1.113883.3.989.2.1.1.19"
                                                  codeSystemVersion="1.1" displayName="ageGroup"/>
                                            <value xsi:type="CE" code="${topValueMap['D.2.3']?if_exists?xml}"
                                                   codeSystem="2.16.840.1.113883.3.989.2.1.1.9"
                                                   codeSystemVersion="1.0"/>
												<#if isDebug == 1><!-- D.2.3 --></#if>
                                        </observation>
                                    </subjectOf2>
                                    </#if>
<#if topValueMap['D.3']?exists>
                                    <subjectOf2 typeCode="SBJ">
                                        <observation classCode="OBS" moodCode="EVN">
                                            <code code="7" codeSystem="2.16.840.1.113883.3.989.2.1.1.19"
                                                  codeSystemVersion="1.1" displayName="bodyWeight"/>
                                            <value xsi:type="PQ" value="${topValueMap['D.3']?if_exists?xml}" unit="kg"/>
												<#if isDebug == 1><!-- D.3 --></#if>
                                        </observation>
                                    </subjectOf2>
</#if>
<#if topValueMap['D.4']?exists>
                                    <subjectOf2 typeCode="SBJ">
                                        <observation classCode="OBS" moodCode="EVN">
                                            <code code="17" codeSystem="2.16.840.1.113883.3.989.2.1.1.19"
                                                  codeSystemVersion="1.1" displayName="height"/>
                                            <value xsi:type="PQ" value="${topValueMap['D.4']?if_exists?xml}" unit="cm"/>
												<#if isDebug == 1><!-- D.4 --></#if>
                                        </observation>
                                    </subjectOf2>
</#if>
<#if topValueMap['D.6']?exists>
                                    <subjectOf2 typeCode="SBJ">
                                        <observation classCode="OBS" moodCode="EVN">
                                            <code code="22" codeSystem="2.16.840.1.113883.3.989.2.1.1.19"
                                                  codeSystemVersion="1.1" displayName="lastMenstrualPeriodDate"/>
                                            <value xsi:type="TS" value="${topValueMap['D.6']?if_exists?xml}"/>
												<#if isDebug == 1><!-- D.6 --></#if>
                                        </observation>
                                    </subjectOf2>
<#elseif topNullflavorValueMap['D.6']?has_content>
                                    <subjectOf2 typeCode="SBJ">
                                        <observation classCode="OBS" moodCode="EVN">
                                            <code code="22" codeSystem="2.16.840.1.113883.3.989.2.1.1.19"
                                                  codeSystemVersion="1.1" displayName="lastMenstrualPeriodDate"/>
                                            <value xsi:type="TS"
                                                   nullFlavor="${topNullflavorValueMap['D.6']?if_exists?xml}"/>
                                            <#if isDebug == 1><!-- D.6 nullflavor--></#if>
                                        </observation>
                                    </subjectOf2>
</#if>
                                    <subjectOf2 typeCode="SBJ">
                                        <organizer classCode="CATEGORY" moodCode="EVN">
                                            <code code="1" codeSystem="2.16.840.1.113883.3.989.2.1.1.20"
                                                  codeSystemVersion="1.0"
                                                  displayName="relevantMedicalHistoryAndConcurrentConditions"/>

												<#if data["D.7.1.r"]?exists>
                                                    <#assign medicationHistoryList = data["D.7.1.r"]>
                                                    <#list medicationHistoryList as medicationHistory>
                                                        <#include "/e2b_r3/other_medication_history/D.7.1.r.ftl">
                                                    </#list>
                                                </#if>
<#if topValueMap['D.7.2']?exists>
                                            <component typeCode="COMP">
                                                <observation classCode="OBS" moodCode="EVN">
                                                    <code code="18" codeSystem="2.16.840.1.113883.3.989.2.1.1.19"
                                                          codeSystemVersion="1.1"
                                                          displayName="historyAndConcurrentConditionText"/>
                                                    <value xsi:type="ED">${topValueMap["D.7.2"]?if_exists?xml}</value>
														<#if isDebug == 1><!-- D.7.2 --></#if>
                                                </observation>
                                            </component>
<#elseif topNullflavorValueMap['D.7.2']?has_content>
                                            <component typeCode="COMP">
                                                <observation classCode="OBS" moodCode="EVN">
                                                    <code code="18" codeSystem="2.16.840.1.113883.3.989.2.1.1.19"
                                                          codeSystemVersion="1.1"
                                                          displayName="historyAndConcurrentConditionText"/>
                                                    <value xsi:type="ED"
                                                           nullFlavor="${topNullflavorValueMap["D.7.2"]?if_exists?xml}"/>
                                                    <#if isDebug == 1><!-- D.7.2 nullflavor--></#if>
                                                </observation>
                                            </component>
</#if>
<#if topValueMap['D.7.3']?exists>
                                            <component typeCode="COMP">
                                                <observation classCode="OBS" moodCode="EVN">
                                                    <code code="11" codeSystem="2.16.840.1.113883.3.989.2.1.1.19"
                                                          codeSystemVersion="1.1" displayName="concomitantTherapy"/>
                                                    <value xsi:type="BL" value="true"/>
														<#if isDebug == 1><!-- D.7.3 --></#if>
                                                </observation>
                                            </component>
</#if>
                                        </organizer>
                                    </subjectOf2>
										<#if data["D.8.r"]?exists>
                                            <#assign drugHistoryList = data["D.8.r"]>
										<subjectOf2 typeCode="SBJ">
                                            <organizer classCode="CATEGORY" moodCode="EVN">
                                                <code code="2" codeSystem="2.16.840.1.113883.3.989.2.1.1.20"
                                                      codeSystemVersion="1.0" displayName="drugHistory"/>
												<#list drugHistoryList as drugHistory>
                                                    <#include "/e2b_r3/other_medication_history/D.8.r.ftl">
                                                </#list>
                                            </organizer>
                                        </subjectOf2>
                                        </#if>


										<#if data["D.9.2.r"]?exists>
                                            <#assign deathList = data["D.9.2.r"]>
                                            <#list deathList as death>
                                                <#include "/e2b_r3/cause_of_death/D.9.2.r.ftl">
                                            </#list>
                                        </#if>



										<subjectOf2 typeCode="SBJ">
                                            <observation classCode="OBS" moodCode="EVN">
                                                <code code="5" codeSystem="2.16.840.1.113883.3.989.2.1.1.19"
                                                      codeSystemVersion="1.1" displayName="autopsy"/>
                                                <#if topValueMap["D.9.3"]?has_content>
                                                <value xsi:type="BL" value="${topValueMap["D.9.3"]?if_exists?xml}"/>
                                                <#if isDebug == 1><!-- D.9.3 --></#if>
                                                <#elseif topNullflavorValueMap['D.9.3']?has_content>
                                                <value xsi:type="BL" nullFlavor="${topNullflavorValueMap["D.9.3"]?if_exists?xml}"/>
                                                        <#if isDebug == 1><!-- D.9.3 nullflavor--></#if>
                                                </#if>
<#if data["D.9.4.r"]?exists>
<#assign autopsyList = data["D.9.4.r"]>

													<#list autopsyList as autopsy>
                                                        <#include "/e2b_r3/cause_of_death/D.9.4.r.ftl">
                                                    </#list>
</#if>
                                            </observation>
                                        </subjectOf2>



										<#if data["E.i"]?exists>
                                            <#assign reactionList = data["E.i"]>
                                            <#list reactionList as reaction>
                                                <#include "/e2b_r3/reaction/E.i.ftl">
                                            </#list>
                                        </#if>


										<#if data["F.r"]?exists>
                                            <#assign labdataList = data["F.r"]>
										<subjectOf2 typeCode="SBJ">
                                            <organizer classCode="CATEGORY" moodCode="EVN">
                                                <code code="3" codeSystem="2.16.840.1.113883.3.989.2.1.1.20"
                                                      codeSystemVersion="1.0"
                                                      displayName="testsAndProceduresRelevantToTheInvestigation"/>
											<#list labdataList as labdata>
                                                <#include "/e2b_r3/labdata/F.r.ftl">
                                            </#list>
                                            </organizer>
                                        </subjectOf2>
                                        </#if>


										<#if data["G.k"]?exists>
                                            <#assign drugList = data["G.k"]>
											<subjectOf2 typeCode="SBJ">
                                                <organizer classCode="CATEGORY" moodCode="EVN">
                                                    <code code="4" codeSystem="2.16.840.1.113883.3.989.2.1.1.20"
                                                          codeSystemVersion="1.0" displayName="drugInformation"/>
                                                    <!-- loog drug -->
													<#list drugList as drug>
                                                        <#include "/e2b_r3/drug/G.k.ftl">
                                                    </#list>
                                                </organizer>
                                            </subjectOf2>
                                        </#if>


                                </primaryRole>
                            </subject1>
								<#if data["G.k"]?exists>
                                    <#assign drugList = data["G.k"]>
                                    <#list drugList as drug>
										<component typeCode="COMP">
                                            <causalityAssessment classCode="OBS" moodCode="EVN">
                                                <code code="20" codeSystem="2.16.840.1.113883.3.989.2.1.1.19"
                                                      codeSystemVersion="1.1"
                                                      displayName="interventionCharacterization"/>
                                                <value xsi:type="CE" code="${drug.valueMap["G.k.1"]?if_exists?xml}"
                                                       codeSystem="2.16.840.1.113883.3.989.2.1.1.13"
                                                       codeSystemVersion="1.0"/>
												<#if isDebug == 1><!-- G.k.1 --></#if>
                                                <subject2 typeCode="SUBJ">
                                                    <productUseReference classCode="SBADM" moodCode="EVN">
                                                        <id root="${drug.valueMap["G.k.fileDrugId"]?if_exists?xml}"/>
                                                        <#if isDebug == 1><!-- fileDrugId --></#if>
                                                    </productUseReference>
                                                </subject2>
                                            </causalityAssessment>
                                        </component>
                                    </#list>
                                </#if>


								<#if data["G.k"]?exists>
                                    <#assign drugList = data["G.k"]>
                                    <#list drugList as drug>
                                        <#if drug.childrenMap?exists>
                                            <#if drug.childrenMap["G.k.9.i.2.r"]?exists>
                                                <#assign causalityItemList = drug.childrenMap["G.k.9.i.2.r"]>
                                                <#list causalityItemList as causalityItem>
                                                    <#include "/e2b_r3/drug_causality_item/G.k.9.i.2.r.ftl">
                                                </#list>
                                            </#if>
                                        </#if>
                                    </#list>
                                </#if>
<#if topValueMap['H.2']?exists>
                            <component1 typeCode="COMP">
                                <observationEvent classCode="OBS" moodCode="EVN">
                                    <code code="10" codeSystem="2.16.840.1.113883.3.989.2.1.1.19"
                                          codeSystemVersion="1.1" displayName="comment"/>
                                    <value xsi:type="ED">${topValueMap['H.2']?if_exists?xml}</value>
										<#if isDebug == 1><!-- H.2 --></#if>
                                    <author typeCode="AUT">
                                        <assignedEntity classCode="ASSIGNED">
                                            <code code="3" codeSystem="2.16.840.1.113883.3.989.2.1.1.21"
                                                  codeSystemVersion="1.0" displayName="sourceReporter"/>
                                        </assignedEntity>
                                    </author>
                                </observationEvent>
                            </component1>
</#if>
								<#if data["H.3.r"]?exists>
                                    <#assign diagnoseList = data["H.3.r"]>
                                    <#list diagnoseList as diagnose>
                                        <#include "/e2b_r3/cause_of_death/H.3.r.ftl">
                                    </#list>
                                </#if>
<#if topValueMap['H.4']?exists>
                            <component1 typeCode="COMP">
                                <observationEvent classCode="OBS" moodCode="EVN">
                                    <code code="10" codeSystem="2.16.840.1.113883.3.989.2.1.1.19"
                                          codeSystemVersion="1.1" displayName="comment"/>
                                    <value xsi:type="ED">${topValueMap['H.4']?if_exists?xml}</value>
										<#if isDebug == 1><!-- H.4 --></#if>
                                    <author typeCode="AUT">
                                        <assignedEntity classCode="ASSIGNED">
                                            <code code="1" codeSystem="2.16.840.1.113883.3.989.2.1.1.21"
                                                  codeSystemVersion="1.0" displayName="sender"/>
                                        </assignedEntity>
                                    </author>
                                </observationEvent>
                            </component1>
</#if>
                        </adverseEventAssessment>
                    </component>
                    <component typeCode="COMP">
                        <observationEvent classCode="OBS" moodCode="EVN">
                            <code code="1" codeSystem="2.16.840.1.113883.3.989.2.1.1.19" codeSystemVersion="1.1"
                                  displayName="additionalDocumentsAvailable"/>
								<#if data["C.1.6.1.r"]?exists>
									<value xsi:type="BL" value="true"/>
                                <#else>
									<value xsi:type="BL" value="false"/>
                                </#if>
								<#if isDebug == 1><!-- C.1.6.1 --></#if>
                        </observationEvent>
                    </component>
                    <component typeCode="COMP">
                        <observationEvent classCode="OBS" moodCode="EVN">
                            <code code="23" codeSystem="2.16.840.1.113883.3.989.2.1.1.19" codeSystemVersion="1.1"
                                  displayName="localCriteriaForExpedited"/>
                            <#if topValueMap["C.1.7"]?exists>
                            <value xsi:type="BL" value="${topValueMap["C.1.7"]?if_exists?xml}"/>
                                    <#if isDebug == 1><!-- C.1.7 --></#if>
                            <#else>
                            <value xsi:type="BL" nullFlavor="NI"/>
                                    <#if isDebug == 1><!-- C.1.7 nullFlavor--></#if>
                            </#if>
                        </observationEvent>
                    </component>

						<#if data["H.5.r"]?exists>
                            <#assign caseSummaryList = data["H.5.r"]>
                            <#list caseSummaryList as caseSummary>
                                <#include "/e2b_r3/case_summary/H.5.r.ftl">
                            </#list>
                        </#if>
                    <outboundRelationship typeCode="SPRT">
                        <relatedInvestigation classCode="INVSTG" moodCode="EVN">
                            <code code="1" codeSystem="2.16.840.1.113883.3.989.2.1.1.22" codeSystemVersion="1.0"
                                  displayName="initialReport"/>
                            <subjectOf2 typeCode="SUBJ">
                                <controlActEvent classCode="CACT" moodCode="EVN">
                                    <author typeCode="AUT">
                                        <assignedEntity classCode="ASSIGNED">
                                            <code code="${topValueMap["C.1.8.2"]?if_exists?xml}"
                                                  codeSystem="2.16.840.1.113883.3.989.2.1.1.3" codeSystemVersion="1.0"/>
												<#if isDebug == 1><!-- C.1.8.2 --></#if>
                                        </assignedEntity>
                                    </author>
                                </controlActEvent>
                            </subjectOf2>
                        </relatedInvestigation>
                    </outboundRelationship>
						<#if data["C.1.10.r.1"]?exists>
                            <#assign relatedCode1List = data["C.1.10.r.1"]>
                            <#list relatedCode1List as relatedCode1 >
                                <#include "/e2b_r3/relatedtocode/C.1.10.r.ftl">
                            </#list>
                        </#if>


						<#if data["C.2.r"]?exists>
                            <#assign reporterList = data["C.2.r"]>
                            <#list reporterList as reporter >
                                <#include "/e2b_r3/reporter/C.2.r.ftl">
                            </#list>
                        </#if>
                    <subjectOf1 typeCode="SUBJ">
                        <controlActEvent classCode="CACT" moodCode="EVN">
                            <author typeCode="AUT">
                                <assignedEntity classCode="ASSIGNED">
                                    <code code="${company.senderType?if_exists?xml}"
                                          codeSystem="2.16.840.1.113883.3.989.2.1.1.7" codeSystemVersion="1.0"/>
                                        <#if isDebug == 1><!-- C.3.1 --></#if>
                                    <addr>
                                        <#if company.senderStreetAddress?has_content>
                                        <streetAddressLine>${company.senderStreetAddress?if_exists?xml}</streetAddressLine>
                                                <#if isDebug == 1><!-- C.3.4.1 --></#if>
                                        </#if>
                                        <#if company.senderStreetAddress?has_content>
                                        <city>${company.senderCity?if_exists?xml}</city>
                                                <#if isDebug == 1><!-- C.3.4.2 --></#if>
                                        </#if>
                                        <#if company.senderStreetAddress?has_content>
                                        <state>${company.senderProvince?if_exists?xml}</state>
                                                <#if isDebug == 1><!-- C.3.4.3 --></#if>
                                        </#if>
                                        <#if company.senderStreetAddress?has_content>
                                        <postalCode>${company.senderZipCode?if_exists?xml}</postalCode>
                                                <#if isDebug == 1><!-- C.3.4.4 --></#if>
                                        </#if>
                                    </addr>
                                    <#if company.senderPhoneNumber?has_content>
                                    <telecom value="tel:${company.senderPhoneNumber?if_exists?xml}"/>
                                            <#if isDebug == 1><!-- C.3.4.6 --></#if>
                                    </#if>
                                    <#if company.senderFaxNumber?has_content>
                                    <telecom value="fax:${company.senderFaxNumber?if_exists?xml}"/>
                                            <#if isDebug == 1><!-- C.3.4.7 --></#if>
                                    </#if>
                                    <#if company.senderEmail?has_content>
                                    <telecom value="mailto:${company.senderEmail?if_exists?xml}"/>
                                            <#if isDebug == 1><!-- C.3.4.8 --></#if>
                                    </#if>
                                    <assignedPerson classCode="PSN" determinerCode="INSTANCE">
                                        <name>
                                        <#if company.senderJobName?has_content>
                                            <prefix>${company.senderJobName?if_exists?xml}</prefix>
                                                <#if isDebug == 1><!-- C.3.3.2 --></#if>
                                        </#if>
                                        <#if company.senderGivenName?has_content>
                                            <given>${company.senderGivenName?if_exists?xml}</given>
                                                <#if isDebug == 1><!-- C.3.3.3 --></#if>
                                        </#if>
                                            <#if company.senderMiddleName?has_content>
                                            <given>${company.senderMiddleName?if_exists?xml}</given>
                                                    <#if isDebug == 1><!-- C.3.3.4 --></#if>
                                            </#if>
                                            <#if company.senderFamilyName?has_content>
                                            <family>${company.senderFamilyName?if_exists?xml}</family>
                                                    <#if isDebug == 1><!-- C.3.3.5 --></#if>
                                            </#if>
                                        </name>
                                        <#if company.senderCountryCode?has_content>
                                        <asLocatedEntity classCode="LOCE">
                                            <location classCode="COUNTRY" determinerCode="INSTANCE">
                                                <code code="${company.senderCountryCode?if_exists?xml}"
                                                      codeSystem="1.0.3166.1.2.2"/>
													<#if isDebug == 1><!-- C.3.4.5 --></#if>
                                            </location>
                                        </asLocatedEntity>
                                        </#if>
                                    </assignedPerson>
                                    <representedOrganization classCode="ORG" determinerCode="INSTANCE">
                                        <#if company.senderDepartment?has_content>
                                        <name>${company.senderDepartment?if_exists?xml}</name>
                                                <#if isDebug == 1><!-- C.3.3.1 --></#if>
                                        </#if>
<#if company.senderOrganization?has_content>
                                        <assignedEntity classCode="ASSIGNED">
                                            <representedOrganization classCode="ORG" determinerCode="INSTANCE">
                                                <name>${company.senderOrganization?if_exists?xml}</name>
													<#if isDebug == 1><!-- C.3.2 --></#if>
                                            </representedOrganization>
                                        </assignedEntity>
</#if>
                                    </representedOrganization>
                                </assignedEntity>
                            </author>
                        </controlActEvent>
                    </subjectOf1>

						<#if data["C.1.9.1.r"]?exists>
                            <#assign relatedCode2List = data["C.1.9.1.r"]>
                            <#list relatedCode2List as relatedCode2 >
                                <#include "/e2b_r3/relatedtocode/C.1.9.1.r.ftl">
                            </#list>
                        </#if>
<#if topValueMap['C.1.3']?exists>
                    <subjectOf2 typeCode="SUBJ">
                        <investigationCharacteristic classCode="OBS" moodCode="EVN">
                            <code code="1" codeSystem="2.16.840.1.113883.3.989.2.1.1.23" codeSystemVersion="1.0"
                                  displayName="ichReportType"/>
                            <value xsi:type="CE" code="${topValueMap['C.1.3']?if_exists?xml}"
                                   codeSystem="2.16.840.1.113883.3.989.2.1.1.2" codeSystemVersion="1.0"/>
								<#if isDebug == 1><!-- C.1.3 --></#if>
                        </investigationCharacteristic>
                    </subjectOf2>
</#if>
                    <subjectOf2 typeCode="SUBJ">
                        <investigationCharacteristic classCode="OBS" moodCode="EVN">
                            <code code="2" codeSystem="2.16.840.1.113883.3.989.2.1.1.23" codeSystemVersion="1.0"
                                  displayName="otherCaseIds"/>
								<#if data["C.1.9.1.r"]?exists>
								<value xsi:type="BL" value="true"/>
                                        <#if isDebug == 1><!-- C.1.9.1 --></#if>
                                <#else>
								<value xsi:type="BL" nullFlavor="NI"/>
                                        <#if isDebug == 1><!-- C.1.9.1 nullflavor--></#if>
                                </#if>

                        </investigationCharacteristic>
                    </subjectOf2>
						<#if topValueMap["C.1.11.1"]?exists>
						<subjectOf2 typeCode="SUBJ">
                            <investigationCharacteristic classCode="OBS" moodCode="EVN">
                                <code code="3" codeSystem="2.16.840.1.113883.3.989.2.1.1.23" codeSystemVersion="1.0"
                                      displayName="nullificationAmendmentCode"/>
                                <value xsi:type="CE" code="${topValueMap["C.1.11.1"]?if_exists?xml}"
                                       codeSystem="2.16.840.1.113883.3.989.2.1.1.5" codeSystemVersion="1.0"/>
								<#if isDebug == 1><!-- C.1.11.1 --></#if>
                            </investigationCharacteristic>
                        </subjectOf2>
                        </#if>
						<#if topValueMap["C.1.11.2"]?exists>
						<subjectOf2 typeCode="SUBJ">
                            <investigationCharacteristic classCode="OBS" moodCode="EVN">
                                <code code="4" codeSystem="2.16.840.1.113883.3.989.2.1.1.23" codeSystemVersion="1.0"
                                      displayName="nullificationAmendmentReason"/>
                                <value xsi:type="CE">
                                    <originalText>${topValueMap["C.1.11.2"]?if_exists?xml}</originalText>
									<#if isDebug == 1><!-- C.1.11.2 --></#if>
                                </value>
                            </investigationCharacteristic>
                        </subjectOf2>
                        </#if>
                </investigationEvent>
            </subject>
        </controlActProcess>
    </PORR_IN049016UV>
    </#list>
    <receiver typeCode="RCV">
        <device classCode="DEV" determinerCode="INSTANCE">
            <id extension="${company.batchReceiverIdentifier?if_exists?xml}" root="2.16.840.1.113883.3.989.2.1.3.14"/>
        </device>
    </receiver>
	<#if isDebug == 1><!-- N.1.4 --></#if>
    <sender typeCode="SND">
        <device classCode="DEV" determinerCode="INSTANCE">
            <id extension="${company.batchSenderIdentifier?if_exists?xml}" root="2.16.840.1.113883.3.989.2.1.3.13"/>
        </device>
    </sender>
	<#if isDebug == 1><!-- N.1.3 --></#if>
</MCCI_IN200100UV01>
