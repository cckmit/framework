<#assign valueMap2=causality.valueMap>
<#if valueMap2['G.k.9.i.4']?exists>
<outboundRelationship2 typeCode="PERT">
    <observation classCode="OBS" moodCode="EVN">
        <code code="31"
              codeSystem="2.16.840.1.113883.3.989.2.1.1.19"
              codeSystemVersion="1.1"
              displayName="recurranceOfReaction"/>
        <value xsi:type="CE" code="${valueMap2['G.k.9.i.4']?if_exists?xml}"
               codeSystem="2.16.840.1.113883.3.989.2.1.1.16"
               codeSystemVersion="1.0"/>
        <#if isDebug == 1><!-- G.k.9.i.4 --></#if>
        <outboundRelationship1 typeCode="REFR">
            <actReference classCode="OBS" moodCode="EVN">
                <id root="${valueMap2['reference.fileEventId']?if_exists?xml}"/>
                <#if isDebug == 1><!-- fileEventId --></#if>
            </actReference>
        </outboundRelationship1>
    </observation>
</outboundRelationship2>
</#if>