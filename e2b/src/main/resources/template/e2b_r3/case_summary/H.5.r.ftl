<#assign valueMap = caseSummary.valueMap>
<component typeCode="COMP">
    <observationEvent classCode="OBS" moodCode="EVN">
        <code code="36" codeSystem="2.16.840.1.113883.3.989.2.1.1.19" codeSystemVersion="1.1" displayName="summaryAndComment"/>
        <value xsi:type="ED" language="${valueMap['H.5.r.1b']?if_exists?xml}">${valueMap['H.5.r.1a']?if_exists?xml}</value>
        <#if isDebug == 1><!-- H.5.r.1a --></#if> <#if isDebug == 1><!-- H.5.r.1b --></#if>
        <author typeCode="AUT">
            <assignedEntity classCode="ASSIGNED">
                <code code="2" codeSystem="2.16.840.1.113883.3.989.2.1.1.21" codeSystemVersion="1.0" displayName="reporter"/>
            </assignedEntity>
        </author>
    </observationEvent>
</component>
