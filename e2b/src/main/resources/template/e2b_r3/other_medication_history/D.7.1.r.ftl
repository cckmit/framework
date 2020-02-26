<#assign valueMap=medicationHistory.valueMap>
<#assign nullflavorValueMap=medicationHistory.nullflavorValueMap>
<#if valueMap['D.7.1.r.1b']?has_content>
<component typeCode="COMP">
    <observation classCode="OBS" moodCode="EVN">
        <#if valueMap['D.7.1.r.1b']?exists>
        <code code="${valueMap['D.7.1.r.1b']?if_exists?xml}" codeSystem="2.16.840.1.113883.6.163" codeSystemVersion="${valueMap['D.7.1.r.1a']?if_exists?xml}"/>
		<#if isDebug == 1><!-- D.7.1.r.1b --></#if> <#if isDebug == 1><!-- D.7.1.r.1a --></#if>
        </#if>
        <effectiveTime xsi:type="IVL_TS">
<#if valueMap['D.7.1.r.2']?exists>
            <low value="${valueMap['D.7.1.r.2']?if_exists?xml}"/>
			<#if isDebug == 1><!-- D.7.1.r.2 --></#if>
<#elseif nullflavorValueMap['D.7.1.r.2']?has_content>
             <low nullFlavor="${nullflavorValueMap['D.7.1.r.2']?if_exists?xml}"/>
        <#if isDebug == 1><!-- D.7.1.r.2 nullflavor--></#if>
</#if>
<#if valueMap['D.7.1.r.4']?exists>
            <high value="${valueMap['D.7.1.r.4']?if_exists?xml}"/>
			<#if isDebug == 1><!-- D.7.1.r.4 --></#if>
<#elseif nullflavorValueMap['D.7.1.r.4']?has_content>
            <high nullFlavor="${nullflavorValueMap['D.7.1.r.4']?if_exists?xml}"/>
        <#if isDebug == 1><!-- D.7.1.r.4 nullflavor--></#if>
</#if>
        </effectiveTime>
<#if valueMap['D.7.1.r.5']?exists>
        <outboundRelationship2 typeCode="COMP">
            <observation classCode="OBS" moodCode="EVN">
                <code code="10" codeSystem="2.16.840.1.113883.3.989.2.1.1.19" codeSystemVersion="1.1" displayName="comment"/>
                <value xsi:type="ED">${valueMap['D.7.1.r.5']?if_exists?xml}</value>
				<#if isDebug == 1><!-- D.7.1.r.5 --></#if>
            </observation>
        </outboundRelationship2>
</#if>
<#if valueMap['D.7.1.r.6']?exists>
        <outboundRelationship2 typeCode="EXPL">
            <observation classCode="OBS" moodCode="EVN">
                <code code="38" codeSystem="2.16.840.1.113883.3.989.2.1.1.19" codeSystemVersion="1.1" displayName="familyHistory"/>
                <value xsi:type="BL" value="${valueMap['D.7.1.r.6']?if_exists?xml}"/>
			<#if isDebug == 1><!-- D.7.1.r.6 --></#if>	
            </observation>
        </outboundRelationship2>
</#if>
<#if valueMap['D.7.1.r.3']?exists>
        <inboundRelationship typeCode="REFR">
            <observation classCode="OBS" moodCode="EVN">
                <code code="13" codeSystem="2.16.840.1.113883.3.989.2.1.1.19" codeSystemVersion="1.1" displayName="continuing"/>
                <value xsi:type="BL" value="${valueMap['D.7.1.r.3']?if_exists?xml}"/>
				<#if isDebug == 1><!-- D.7.1.r.3 --></#if>
            </observation>
        </inboundRelationship>
<#elseif nullflavorValueMap['D.7.1.r.3']?has_content>
        <inboundRelationship typeCode="REFR">
            <observation classCode="OBS" moodCode="EVN">
                <code code="13" codeSystem="2.16.840.1.113883.3.989.2.1.1.19" codeSystemVersion="1.1" displayName="continuing"/>
                <value xsi:type="BL" nullFlavor="${nullflavorValueMap['D.7.1.r.3']?if_exists?xml?xml}"/>
                <#if isDebug == 1><!-- D.7.1.r.3 nullflavor--></#if>
            </observation>
        </inboundRelationship>
</#if>
    </observation>
</component>
</#if>
