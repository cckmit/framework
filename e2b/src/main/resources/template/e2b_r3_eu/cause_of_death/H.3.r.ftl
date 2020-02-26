<#assign valueMap=diagnose.valueMap>
<component1 typeCode="COMP">
    <observationEvent classCode="OBS" moodCode="EVN">
        <code code="15" codeSystem="2.16.840.1.113883.3.989.2.1.1.19" codeSystemVersion="1.1" displayName="diagnosis"/>
        <#if valueMap["H.3.r.1b"]?exists>
        <value xsi:type="CE" code="${valueMap["H.3.r.1b"]?if_exists?xml}" codeSystem="2.16.840.1.113883.6.163" codeSystemVersion="${valueMap["H.3.r.1a"]?if_exists?xml}"/>
		<#if isDebug == 1><!-- H.3.r.1b --></#if> <#if isDebug == 1><!-- H.3.r.1a --></#if>
        </#if>
        <author typeCode="AUT">
            <assignedEntity classCode="ASSIGNED">
                <code code="1" codeSystem="2.16.840.1.113883.3.989.2.1.1.21" codeSystemVersion="1.0" displayName="sender"/>
            </assignedEntity>
        </author>
    </observationEvent>
</component1>
