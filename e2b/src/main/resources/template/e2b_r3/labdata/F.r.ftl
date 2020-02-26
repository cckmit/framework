<#assign valueMap=labdata.valueMap>
<#assign nullflavorValueMap=labdata.nullflavorValueMap>
<#if valueMap['F.r.2.2b']?exists>
<component typeCode="COMP">
    <observation classCode="OBS" moodCode="EVN">
        <#if valueMap['F.r.2.2b']?exists || valueMap['F.r.2.1']?exists>
            <code code="${valueMap['F.r.2.2b']?if_exists?xml}" codeSystem="2.16.840.1.113883.6.163"
                  codeSystemVersion="${valueMap['F.r.2.2a']?if_exists?xml}">
                <#if isDebug == 1><!-- F.r.2.2b --></#if> <#if isDebug == 1><!-- F.r.2.2a --></#if>
                <#if valueMap['F.r.2.1']?exists>
                <originalText>${valueMap['F.r.2.1']?if_exists?xml}</originalText>
                        <#if isDebug == 1><!-- F.r.2.1 --></#if>
                </#if>
            </code>
        </#if>
        <#if valueMap['F.r.1']?exists>
            <effectiveTime value="${valueMap['F.r.1']?if_exists?xml}"/>
		    <#if isDebug == 1><!-- F.r.1 --></#if>
        <#elseif nullflavorValueMap['F.r.1']?has_content>
            <effectiveTime nullFlavor="${nullflavorValueMap['F.r.1']?if_exists?xml}"/>
                <#if isDebug == 1><!-- F.r.1 nullflavor --></#if>
        </#if>
<#if valueMap['F.r.3.4']?exists>
        <value xsi:type="ED">${valueMap['F.r.3.4']?if_exists?xml}</value>
		<#if isDebug == 1><!-- F.r.3.4 --></#if>
</#if>
<#if valueMap['F.r.3.2']?exists && valueMap['F.r.3.2.format']?exists>
    <#if valueMap['F.r.3.2.format'] == 'eq'>
            <value xsi:type="IVL_PQ">
                <center value="${valueMap['F.r.3.2']?if_exists?xml}" unit="${valueMap['F.r.3.3']?if_exists?xml}"/>
                <#if isDebug == 1><!-- F.r.3.2 --></#if>
            </value>
    </#if>
    <#if valueMap['F.r.3.2.format'] == 'gt'>
            <value xsi:type="IVL_PQ" >
                <low value="${valueMap['F.r.3.2']?if_exists?xml}" unit="${valueMap['F.r.3.3']?if_exists?xml}" inclusive="false"/>
                <high nullFlavor="PINF"/>
                <#if isDebug == 1><!-- F.r.3.2 --></#if>
            </value>
    </#if>
    <#if valueMap['F.r.3.2.format'] == 'ge'>
            <value xsi:type="IVL_PQ" >
                <low value="${valueMap['F.r.3.2']?if_exists?xml}" unit="${valueMap['F.r.3.3']?if_exists?xml}" inclusive="true"/>
                <high nullFlavor="PINF"/>
                <#if isDebug == 1><!-- F.r.3.2 --></#if>
            </value>
    </#if>
    <#if valueMap['F.r.3.2.format'] == 'lt'>
            <value xsi:type="IVL_PQ" >
                <low nullFlavor="NINF"/>
                <high value="${valueMap['F.r.3.2']?if_exists?xml}" unit="${valueMap['F.r.3.3']?if_exists?xml}" inclusive="false"/>
                <#if isDebug == 1><!-- F.r.3.2 --></#if>
            </value>
    </#if>
    <#if valueMap['F.r.3.2.format'] == 'le'>
            <value xsi:type="IVL_PQ" >
                <low nullFlavor="NINF"/>
                <high value="${valueMap['F.r.3.2']?if_exists?xml}" unit="${valueMap['F.r.3.3']?if_exists?xml}" inclusive="true"/>
                <#if isDebug == 1><!-- F.r.3.2 --></#if>
            </value>
    </#if>
</#if>
<#if valueMap['F.r.3.1']?exists>
        <interpretationCode code="${valueMap['F.r.3.1']?if_exists?xml}"
                            codeSystem="2.16.840.1.113883.3.989.2.1.1.12"
                            codeSystemVersion="1.0"/>
        <#if isDebug == 1><!-- F.r.3.1 --></#if>
</#if>
<#if valueMap['F.r.4']?exists>
        <referenceRange typeCode="REFV">
            <observationRange classCode="OBS" moodCode="EVN.CRT">
                <value xsi:type="PQ" value="${valueMap['F.r.4']?if_exists?xml}" unit="${valueMap['F.r.4.1']?if_exists?xml}"/>
				<#if isDebug == 1><!-- F.r.4 --></#if> <#if isDebug == 1><!-- F.r.4.1 --></#if>
                <interpretationCode code="L"
                                    codeSystem="2.16.840.1.113883.5.83"/>
            </observationRange>
        </referenceRange>
</#if>
<#if valueMap['F.r.5']?exists>
        <referenceRange typeCode="REFV">
            <observationRange classCode="OBS" moodCode="EVN.CRT">
                <value xsi:type="PQ" value="${valueMap['F.r.5']?if_exists?xml}" unit="${valueMap['F.r.5.1']?if_exists?xml}"/>
				<#if isDebug == 1><!-- F.r.5 --></#if> <#if isDebug == 1><!-- F.r.5.1 --></#if>
                <interpretationCode code="H"
                                    codeSystem="2.16.840.1.113883.5.83"/>
            </observationRange>
        </referenceRange>
</#if>
<#if valueMap['F.r.6']?exists>
        <outboundRelationship2 typeCode="PERT">
            <observation classCode="OBS" moodCode="EVN">
                <code code="10"
                      codeSystem="2.16.840.1.113883.3.989.2.1.1.19"
                      codeSystemVersion="1.1" displayName="comment"/>
                <value xsi:type="ED">${valueMap['F.r.6']?if_exists?xml}</value>
				<#if isDebug == 1><!-- F.r.6 --></#if>
            </observation>
        </outboundRelationship2>
</#if>
<#if valueMap['F.r.7']?exists>
        <outboundRelationship2 typeCode="REFR">
            <observation classCode="OBS" moodCode="EVN">
                <code code="25"
                      codeSystem="2.16.840.1.113883.3.989.2.1.1.19"
                      codeSystemVersion="1.1"
                      displayName="moreInformationAvailable"/>
                <value xsi:type="BL" value="${valueMap['F.r.7']?if_exists?xml}"/>
				<#if isDebug == 1><!-- F.r.7 --></#if>
            </observation>
        </outboundRelationship2>
</#if>
    </observation>

</component>
</#if>


										