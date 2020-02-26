<#assign valueMap=attachment1.valueMap>
<reference typeCode="REFR">
    <document classCode="DOC" moodCode="EVN">
        <code code="1" codeSystem="2.16.840.1.113883.3.989.2.1.1.27" codeSystemVersion="1.0" displayName="documentsHeldBySender"/>
        <title>${valueMap["C.1.6.1.r.1"]?if_exists?xml}</title>
		<#if isDebug == 1><!-- C.1.6.1.r.1 --></#if>
        <text mediaType="${valueMap['C.1.6.1.r.100']?if_exists?xml}" representation="B64">${valueMap["C.1.6.1.r.2"]?if_exists?xml}</text>
    </document>
</reference>