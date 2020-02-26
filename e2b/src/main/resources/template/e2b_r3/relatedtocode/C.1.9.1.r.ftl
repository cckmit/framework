<#assign valueMap=relatedCode2.valueMap>
<subjectOf1 typeCode="SUBJ">
    <controlActEvent classCode="CACT" moodCode="EVN">
        <id assigningAuthorityName="${valueMap['C.1.9.1.r.1']?if_exists?xml}" extension="${valueMap['C.1.9.1.r.2']?if_exists?xml}" root="2.16.840.1.113883.3.989.2.1.3.3"/>
		<#if isDebug == 1><!-- C.1.9.1.r.1 --></#if>
		<#if isDebug == 1><!-- C.1.9.1.r.2 --></#if>
    </controlActEvent>
</subjectOf1>

