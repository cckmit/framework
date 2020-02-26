<#assign valueMap=drugHistory.valueMap>
<#assign nullflavorValueMap=drugHistory.nullflavorValueMap>
<#if valueMap['D.8.r.7b']?has_content>
<component typeCode="COMP">
    <substanceAdministration classCode="SBADM" moodCode="EVN">
        <effectiveTime xsi:type="IVL_TS">
            <#if valueMap['D.8.r.4']?exists>
                <low value="${valueMap['D.8.r.4']?if_exists?xml}"/>
                    <#if isDebug == 1><!-- D.8.r.4 --></#if>
            <#elseif nullflavorValueMap['D.8.r.4']?has_content>
                <low nullFlavor="${nullflavorValueMap['D.8.r.4']?if_exists?xml}"/>
                    <#if isDebug == 1><!-- D.8.r.4 nullflavor --></#if>
            </#if>
            <#if valueMap['D.8.r.5']?exists>
                <high value="${valueMap['D.8.r.5']?if_exists?xml}"/>
                    <#if isDebug == 1><!-- D.8.r.5 --></#if>
            <#elseif nullflavorValueMap['D.8.r.5']?has_content>
                <high nullFlavor="${nullflavorValueMap['D.8.r.5']?if_exists?xml}"/>
                    <#if isDebug == 1><!-- D.8.r.5 nullflavor --></#if>
            </#if>
        </effectiveTime>
        <#if valueMap['D.8.r.1']?exists>
        <consumable typeCode="CSM">
            <instanceOfKind classCode="INST">
                <kindOfProduct classCode="MMAT" determinerCode="KIND">
                    <!--<code code="D.8.r.2b" codeSystem="TBD-MPID" codeSystemVersion="D.8.r.2a"/>-->
					<#if isDebug == 1><!-- D.8.r.2b --></#if> <#if isDebug == 1><!-- D.8.r.2a --></#if>
                    <name>${valueMap['D.8.r.1']?if_exists?xml}</name>
					<#if isDebug == 1><!-- D.8.r.1 --></#if>
                </kindOfProduct>
            </instanceOfKind>
        </consumable>
        <#elseif nullflavorValueMap['D.8.r.1']?has_content>
        <consumable typeCode="CSM">
            <instanceOfKind classCode="INST">
                <kindOfProduct classCode="MMAT" determinerCode="KIND">
                    <!--<code code="D.8.r.2b" codeSystem="TBD-MPID" codeSystemVersion="D.8.r.2a"/>-->
                    <name nullFlavor="${nullflavorValueMap['D.8.r.1']?if_exists?xml}"/>
                    <#if isDebug == 1><!-- D.8.r.1 nullflavor --></#if>
                </kindOfProduct>
            </instanceOfKind>
        </consumable>

        </#if>
        <#if valueMap['D.8.r.6b']?exists>
        <outboundRelationship2 typeCode="RSON">
            <observation classCode="OBS" moodCode="EVN">
                <code code="19" codeSystem="2.16.840.1.113883.3.989.2.1.1.19" codeSystemVersion="1.1" displayName="indication"/>
                <value xsi:type="CE" code="${valueMap['D.8.r.6b']?if_exists?xml}" codeSystem="2.16.840.1.113883.6.163" codeSystemVersion="${valueMap['D.8.r.6a']?if_exists?xml}"/>
				<#if isDebug == 1><!-- D.8.r.6b --></#if> <#if isDebug == 1><!-- D.8.r.6a --></#if>
            </observation>
        </outboundRelationship2>
        </#if>
        <#if valueMap['D.8.r.7b']?exists>
        <outboundRelationship2 typeCode="CAUS">
            <observation classCode="OBS" moodCode="EVN">
                <code code="29" codeSystem="2.16.840.1.113883.3.989.2.1.1.19" codeSystemVersion="1.1" displayName="reaction"/>
                <value xsi:type="CE" code="${valueMap['D.8.r.7b']?if_exists?xml}" codeSystem="2.16.840.1.113883.6.163" codeSystemVersion="${valueMap['D.8.r.7a']?if_exists?xml}"/>
				<#if isDebug == 1><!-- D.8.r.7b --></#if> <#if isDebug == 1><!-- D.8.r.7a --></#if>
            </observation>
        </outboundRelationship2>
        </#if>
    </substanceAdministration>
</component>
</#if>
