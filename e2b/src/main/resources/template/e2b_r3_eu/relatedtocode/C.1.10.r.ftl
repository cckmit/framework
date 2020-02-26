<#assign valueMap=relatedCode1.valueMap>
<outboundRelationship typeCode="SPRT">
    <relatedInvestigation classCode="INVSTG" moodCode="EVN">
        <code nullFlavor="NA"/>
        <subjectOf2 typeCode="SUBJ">
            <controlActEvent classCode="CACT" moodCode="EVN">
                <id extension="${valueMap['C.1.10.r']?if_exists?xml}" root="2.16.840.1.113883.3.989.2.1.3.2"/>
				<#if isDebug == 1><!-- C.1.10.r --></#if>
            </controlActEvent>
        </subjectOf2>
    </relatedInvestigation>
</outboundRelationship>