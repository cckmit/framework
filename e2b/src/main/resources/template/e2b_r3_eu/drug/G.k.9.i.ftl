<#assign valueMap2=causality.valueMap>
<#if valueMap2['G.k.9.i.3.1a']?exists>
<outboundRelationship1 typeCode="SAS">
    <pauseQuantity value="${valueMap2['G.k.9.i.3.1a']?if_exists?xml}" unit="${valueMap2['G.k.9.i.3.1b']?if_exists?xml}"/>
	<#if isDebug == 1><!-- G.k.9.i.3.1a --></#if> <#if isDebug == 1><!-- G.k.9.i.3.1b --></#if>
    <actReference classCode="OBS" moodCode="EVN">
        <id root="${valueMap2['reference.fileEventId']?if_exists?xml}"/>
        <#if isDebug == 1><!-- fileEventId --></#if>
    </actReference>
</outboundRelationship1>
</#if>
<#if valueMap2['G.k.9.i.3.2a']?exists>
<outboundRelationship1 typeCode="SAE">
    <pauseQuantity value="${valueMap2['G.k.9.i.3.2a']?if_exists?xml}" unit="${valueMap2['G.k.9.i.3.2b']?if_exists?xml}"/>
	<#if isDebug == 1><!-- G.k.9.i.3.2a --></#if> <#if isDebug == 1><!-- G.k.9.i.3.2b --></#if>

    <actReference classCode="OBS" moodCode="EVN">
        <id root="${valueMap2['reference.fileEventId']?if_exists?xml}"/>
        <#if isDebug == 1><!-- fileEventId --></#if>
    </actReference>
</outboundRelationship1>
</#if>