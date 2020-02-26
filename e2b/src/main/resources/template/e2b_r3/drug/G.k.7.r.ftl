<#assign valueMap2=reason.valueMap>
<#assign nullflavorValueMap2=reason.nullflavorValueMap>
<#if valueMap2['G.k.7.r.2b']?has_content>
<inboundRelationship typeCode="RSON">
    <observation classCode="OBS" moodCode="EVN">
        <code code="19" codeSystem="2.16.840.1.113883.3.989.2.1.1.19" codeSystemVersion="1.1" displayName="indication"/>
        <value xsi:type="CE" code="${valueMap2['G.k.7.r.2b']?if_exists?xml}" codeSystem="2.16.840.1.113883.6.163" codeSystemVersion="${valueMap2['G.k.7.r.2a']?if_exists?xml}">
		<#if isDebug == 1><!-- G.k.7.r.2b --></#if> <#if isDebug == 1><!-- G.k.7.r.2a --></#if>
            <#if valueMap2['G.k.7.r.1']?exists && valueMap2['G.k.7.r.1']!=''>
            <originalText>${valueMap2['G.k.7.r.1']?if_exists?xml}</originalText>
                    <#if isDebug == 1><!-- G.k.7.r.1 --></#if>
            <#elseif nullflavorValueMap2['G.k.7.r.1']?has_content>
            <originalText nullFlavor="${nullflavorValueMap2['G.k.7.r.1']}"/>
                    <#if isDebug == 1><!-- G.k.7.r.1 nullflavor--></#if>
            </#if>
        </value>
        <performer typeCode="PRF">
            <assignedEntity classCode="ASSIGNED">
                <code code="3" codeSystem="2.16.840.1.113883.3.989.2.1.1.21" codeSystemVersion="1.0" displayName="sourceReporter"/>
            </assignedEntity>
        </performer>
    </observation>
</inboundRelationship>
</#if>