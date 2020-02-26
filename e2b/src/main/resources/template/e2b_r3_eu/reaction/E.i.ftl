<#assign valueMap=reaction.valueMap>
<#assign nullflavorValueMap=reaction.nullflavorValueMap>
<subjectOf2 typeCode="SBJ">
    <observation classCode="OBS" moodCode="EVN">
        <id root="${valueMap['E.i.fileEventId']?if_exists?xml}"/>
        <#if isDebug == 1><!--fileEventId --></#if>
        <code code="29" codeSystem="2.16.840.1.113883.3.989.2.1.1.19" codeSystemVersion="1.1" displayName="reaction"/>
        <#if valueMap['E.i.format'] != '2COMP'>
        <effectiveTime xsi:type="IVL_TS">
            <#if valueMap['E.i.4']?exists && valueMap['E.i.4'] !="" >
            <low value="${valueMap['E.i.4']?if_exists?xml}"/>
                    <#if isDebug == 1><!-- E.i.4 --></#if>
            <#elseif nullflavorValueMap['E.i.4']?has_content>
            <low nullFlavor="${nullflavorValueMap['E.i.4']?if_exists?xml}"/>
                    <#if isDebug == 1><!-- E.i.4 nullFlavor--></#if>
            </#if>
            <#if valueMap['E.i.5']?exists && valueMap['E.i.5'] !="" >
                <high value="${valueMap['E.i.5']?if_exists?xml}"/>
                    <#if isDebug == 1><!-- E.i.5 --></#if>
            <#elseif nullflavorValueMap['E.i.5']?has_content>
                <high nullFlavor="${nullflavorValueMap['E.i.5']?if_exists?xml}"/>
                    <#if isDebug == 1><!-- E.i.5 nullFlavor--></#if>
            </#if>
            <#if valueMap['E.i.6a']?exists && valueMap['E.i.6a'] !="" && valueMap['E.i.6b']?has_content>
                        <width value="${valueMap['E.i.6a']?if_exists?xml}" unit="${valueMap['E.i.6b']?if_exists?xml}"/>
                    <#if isDebug == 1><!-- E.i.6a --></#if> <#if isDebug == 1><!-- E.i.6b --></#if>
            </#if>
        </effectiveTime>
        <#else>
         <effectiveTime xsi:type="SXPR_TS">
             <comp xsi:type="IVL_TS">
            <#if valueMap['E.i.4']?exists && valueMap['E.i.4'] !="" >
                <low value="${valueMap['E.i.4']?if_exists?xml}"/>
                    <#if isDebug == 1><!-- E.i.4 --></#if>
            <#elseif nullflavorValueMap['E.i.4']?has_content>
                <high nullFlavor="${nullflavorValueMap['E.i.4']?if_exists?xml}"/>
                    <#if isDebug == 1><!-- E.i.4 nullflavor --></#if>
            </#if>
            <#if valueMap['E.i.5']?exists && valueMap['E.i.5'] !="" >
                <high value="${valueMap['E.i.5']?if_exists?xml}"/>
                    <#if isDebug == 1><!-- E.i.5 --></#if>
            <#elseif nullflavorValueMap['E.i.5']?has_content>
            <high nullFlavor="${nullflavorValueMap['E.i.5']?if_exists?xml}"/>
                    <#if isDebug == 1><!-- E.i.5 nullflavor--></#if>
            </#if>
             </comp>
             <comp xsi:type="IVL_TS" operator="A">
                        <width value="${valueMap['E.i.6a']?if_exists?xml}" unit="${valueMap['E.i.6b']?if_exists?xml}"/>
                    <#if isDebug == 1><!-- E.i.6a --></#if> <#if isDebug == 1><!-- E.i.6b --></#if>
             </comp>
         </effectiveTime>
        </#if>

        <#if valueMap['E.i.2.1b']?exists>
            <value xsi:type="CE" code="${valueMap['E.i.2.1b']?if_exists?xml}" codeSystem="2.16.840.1.113883.6.163"
                   codeSystemVersion="${valueMap['E.i.2.1a']?if_exists?xml}">
            <#if isDebug == 1><!-- E.i.2.1b --></#if> <#if isDebug == 1><!-- E.i.2.1a --></#if>

            <#if valueMap['E.i.1.1a']?exists>
                        <originalText language="${valueMap['E.i.1.1b']?if_exists?xml}">${valueMap["E.i.1.1a"]?if_exists?xml}</originalText>
                    <#if isDebug == 1><!-- E.i.1.1b --></#if><#if isDebug == 1><!-- E.i.1.1a --></#if>
            </#if>
            </value>
        </#if>
        <#if valueMap['E.i.9']?exists>
                <location typeCode="LOC">
                    <locatedEntity classCode="LOCE">
                        <locatedPlace classCode="COUNTRY" determinerCode="INSTANCE">
                            <code code="${valueMap["E.i.9"]?if_exists?xml}" codeSystem="1.0.3166.1.2.2"/>
                            <#if isDebug == 1><!-- E.i.9 --></#if>
                        </locatedPlace>
                    </locatedEntity>
                </location>
        </#if>
        <#if valueMap['E.i.1.2']?exists>
                <outboundRelationship2 typeCode="PERT">
                    <observation classCode="OBS" moodCode="EVN">
                        <code code="30" codeSystem="2.16.840.1.113883.3.989.2.1.1.19" codeSystemVersion="1.1"
                              displayName="reactionForTranslation"/>
                        <value xsi:type="ED">${valueMap['E.i.1.2']?if_exists?xml}</value>
                        <#if isDebug == 1><!-- E.i.1.2 --></#if>
                    </observation>
                </outboundRelationship2>
        </#if>
        <#if valueMap['E.i.3.1']?exists>
                <outboundRelationship2 typeCode="PERT">
                    <observation classCode="OBS" moodCode="EVN">
                        <code code="37" codeSystem="2.16.840.1.113883.3.989.2.1.1.19" codeSystemVersion="1.1"
                              displayName="termHighlightedByReporter"/>
                        <value xsi:type="CE" code="${valueMap["E.i.3.1"]?if_exists?xml}"
                               codeSystem="2.16.840.1.113883.3.989.2.1.1.10"
                               codeSystemVersion="2.0"/>
                        <#if isDebug == 1><!-- E.i.3.1 --></#if>
                    </observation>
                </outboundRelationship2>
        </#if>
        <outboundRelationship2 typeCode="PERT">
            <observation classCode="OBS" moodCode="EVN">
                <code code="34" codeSystem="2.16.840.1.113883.3.989.2.1.1.19" codeSystemVersion="1.1"
                      displayName="resultsInDeath"/>
                <#if valueMap['E.i.3.2a']?exists>
                    <value xsi:type="BL" value="${valueMap['E.i.3.2a']?if_exists?xml}"/>
                        <#if isDebug == 1><!-- E.i.3.2a --></#if>
                <#else>
                    <value xsi:type="BL" nullFlavor="NI"/>
                        <#if isDebug == 1><!-- E.i.3.2a nullflavor--></#if>
                </#if>


            </observation>
        </outboundRelationship2>
        <outboundRelationship2 typeCode="PERT">
            <observation classCode="OBS" moodCode="EVN">
                <code code="21" codeSystem="2.16.840.1.113883.3.989.2.1.1.19" codeSystemVersion="1.1"
                      displayName="isLifeThreatening"/>
                <#if valueMap['E.i.3.2b']?exists>
                    <value xsi:type="BL" value="${valueMap['E.i.3.2b']?if_exists?xml}"/>
                        <#if isDebug == 1><!-- E.i.3.2b --></#if>
                <#else>
                    <value xsi:type="BL" nullFlavor="NI"/>
                        <#if isDebug == 1><!-- E.i.3.2b nullflavor --></#if>
                </#if>

            </observation>
        </outboundRelationship2>
        <outboundRelationship2 typeCode="PERT">
            <observation classCode="OBS" moodCode="EVN">
                <code code="33" codeSystem="2.16.840.1.113883.3.989.2.1.1.19" codeSystemVersion="1.1"
                      displayName="requiresInpatientHospitalization"/>
                <#if valueMap['E.i.3.2c']?exists>
                    <value xsi:type="BL" value="${valueMap['E.i.3.2c']?if_exists?xml}"/>
                        <#if isDebug == 1><!-- E.i.3.2c --></#if>
                <#else>
                    <value xsi:type="BL" nullFlavor="NI"/>
                        <#if isDebug == 1><!-- E.i.3.2c nullflavor--></#if>
                </#if>

            </observation>
        </outboundRelationship2>
        <outboundRelationship2 typeCode="PERT">
            <observation classCode="OBS" moodCode="EVN">
                <code code="35" codeSystem="2.16.840.1.113883.3.989.2.1.1.19" codeSystemVersion="1.1"
                      displayName="resultsInPersistentOrSignificantDisability"/>
                <#if valueMap['E.i.3.2d']?exists>
                    <value xsi:type="BL" value="${valueMap['E.i.3.2d']?if_exists?xml}"/>
                        <#if isDebug == 1><!-- E.i.3.2d --></#if>
                <#else>
                    <value xsi:type="BL" nullFlavor="NI"/>
                        <#if isDebug == 1><!-- E.i.3.2d nullflavor--></#if>
                </#if>

            </observation>
        </outboundRelationship2>
        <outboundRelationship2 typeCode="PERT">
            <observation classCode="OBS" moodCode="EVN">
                <code code="12" codeSystem="2.16.840.1.113883.3.989.2.1.1.19" codeSystemVersion="1.1"
                      displayName="congenitalAnomalyBirthDefect"/>
                <#if valueMap['E.i.3.2e']?exists>
                    <value xsi:type="BL" value="${valueMap['E.i.3.2e']?if_exists?xml}"/>
                        <#if isDebug == 1><!-- E.i.3.2e --></#if>
                <#else>
                    <value xsi:type="BL" nullFlavor="NI"/>
                        <#if isDebug == 1><!-- E.i.3.2e nullflavor--></#if>
                </#if>

            </observation>
        </outboundRelationship2>
        <outboundRelationship2 typeCode="PERT">
            <observation classCode="OBS" moodCode="EVN">
                <code code="26" codeSystem="2.16.840.1.113883.3.989.2.1.1.19" codeSystemVersion="1.1"
                      displayName="otherMedicallyImportantCondition"/>
                <#if valueMap['E.i.3.2f']?exists>
                    <value xsi:type="BL" value="${valueMap['E.i.3.2f']?if_exists?xml}"/>
                        <#if isDebug == 1><!-- E.i.3.2f --></#if>
                <#else>
                    <value xsi:type="BL" nullFlavor="NI"/>
                        <#if isDebug == 1><!-- E.i.3.2f nullflavor --></#if>
                </#if>

            </observation>
        </outboundRelationship2>
        <#if valueMap['E.i.7']?has_content>
        <outboundRelationship2 typeCode="PERT">
            <observation classCode="OBS" moodCode="EVN">
                <code code="27" codeSystem="2.16.840.1.113883.3.989.2.1.1.19" codeSystemVersion="1.1"
                      displayName="outcome"/>
                <value xsi:type="CE" code="${valueMap['E.i.7']?if_exists?xml}" codeSystem="2.16.840.1.113883.3.989.2.1.1.11"
                       codeSystemVersion="2.0"/>
					   <#if isDebug == 1><!-- E.i.7 --></#if>
            </observation>
        </outboundRelationship2>
        </#if>
        <#if valueMap['E.i.8']?exists>
                <outboundRelationship2 typeCode="PERT">
                    <observation classCode="OBS" moodCode="EVN">
                        <code code="24" codeSystem="2.16.840.1.113883.3.989.2.1.1.19" codeSystemVersion="1.1"
                              displayName="medicalConfirmationByHealthProfessional"/>
                        <value xsi:type="BL" value="${valueMap['E.i.8']?if_exists?xml}"/>
                        <#if isDebug == 1><!-- E.i.8 --></#if>
                    </observation>
                </outboundRelationship2>
        </#if>
    </observation>
</subjectOf2>


										