<#assign valueMap=attachment2.valueMap>
<#assign nullflavorValueMap=attachment2.nullflavorValueMap>
<reference typeCode="REFR">
	<document classCode="DOC" moodCode="EVN">
		<code code="2" codeSystem="2.16.840.1.113883.3.989.2.1.1.27" codeSystemVersion="1.0" displayName="literatureReference"/>
		<text mediaType="${valueMap["C.4.r.100"]?if_exists?xml}" representation="B64">${valueMap["C.4.r.2"]?if_exists?xml}</text>
		<#if isDebug == 1><!-- C.4.r.2 --></#if>
		<#if valueMap["C.4.r.1"]?has_content>
		<bibliographicDesignationText>${valueMap["C.4.r.1"]?if_exists?xml}</bibliographicDesignationText>
		<#if isDebug == 1><!-- C.4.r.1 --></#if>
		<#elseif nullflavorValueMap["C.4.r.1"]?has_content>
			<bibliographicDesignationText nullFlavor='${nullflavorValueMap["C.4.r.1"]?if_exists?xml}'></bibliographicDesignationText>
		<!-- C.4.r.1 nullflavor-->
		</#if>

	</document>
</reference>
