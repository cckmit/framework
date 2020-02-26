<#assign valueMap = research.valueMap>
<authorization typeCode="AUTH">
    <studyRegistration classCode="ACT" moodCode="EVN">
        <#if valueMap['C.5.1.r.1']?exists>
        <id extension="${valueMap['C.5.1.r.1']?if_exists?xml}" root="2.16.840.1.113883.3.989.2.1.3.6"/>
		<#if isDebug == 1><!-- C.5.1.r.1 --></#if>
        </#if>
        <#if valueMap['C.5.1.r.2']?exists>
        <author typeCode="AUT">
            <territorialAuthority classCode="TERR">
                <governingPlace classCode="COUNTRY" determinerCode="INSTANCE">
                    <code code="${valueMap['C.5.1.r.2']?if_exists?xml}" codeSystem="1.0.3166.1.2.2"/>
					<#if isDebug == 1><!-- C.5.1.r.2 --></#if>
                </governingPlace>
            </territorialAuthority>
        </author>
        </#if>
    </studyRegistration>
</authorization>
